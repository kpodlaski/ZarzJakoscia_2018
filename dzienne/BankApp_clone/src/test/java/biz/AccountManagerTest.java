package biz;

import db.dao.DAO;
import model.Account;
import model.Operation;
import model.User;
import model.exceptions.OperationIsNotAllowedException;
import model.exceptions.UserUnnkownOrBadPasswordException;
import model.operations.Payment;
import model.operations.PaymentIn;
import model.operations.Withdraw;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by Krzysztof Podlaski on 26.03.2018.
 */
public class AccountManagerTest {
    //Obiekt do testowania
    AccountManager am;
    @Mock
    DAO dao;
    @Mock
    BankHistory history;
    @Mock
    AuthenticationManager auth;
    @Mock
    InterestOperator interestOperator;
    @Mock
    User loggedUser;

    //Niezbędne do storzenia powyższych mocków
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Before
    public void setUp() throws Exception {
        am= new AccountManager();
        //Udajmy że dao jest prywatnym polem
        Field f = AccountManager.class.getDeclaredField("dao");
        f.setAccessible(true);
        f.set(am,dao);
        //am.dao = dao;
        am.history=history;
        am.auth=auth;
        am.interestOperator=interestOperator;
        am.loggedUser=loggedUser;
    }

    @Test
    public void logInSucess() throws Exception {
        when(
                auth.logIn(
                        anyString(),
                        any((new char[2]).getClass())
                )
        ).thenReturn(loggedUser);
        boolean result = am.logIn("Adam","Adam".toCharArray());
        assertTrue(result);
        verify(auth,atLeast(1))
                .logIn("Adam", "Adam".toCharArray());
    }

    @Test
    public void logInFailed1() throws Exception {
        when(
                auth.logIn(
                        anyString(),
                        any((new char[2]).getClass())
                )
        ).thenReturn(null);
        boolean result = am.logIn("Adam","Adam".toCharArray());
        assertFalse(result);
        verify(auth,atLeast(1))
                .logIn("Adam", "Adam".toCharArray());
    }

    @Test(expected =UserUnnkownOrBadPasswordException.class )
    public void logInFailedwithException() throws Exception {
        when(
                auth.logIn(
                        anyString(),
                        any((new char[2]).getClass())
                )
        ).thenThrow(new UserUnnkownOrBadPasswordException("bad passwd"));
        boolean result = am.logIn("Adam","Adam".toCharArray());
        assertFalse(result);
        verify(auth,atLeast(1))
                .logIn("Adam", "Adam".toCharArray());
    }

    @Test
    public void paymentIn() throws SQLException {
        User u = mock(User.class);
        Account account = mock(Account.class);
        when(dao.findAccountById(134))
                .thenReturn(account);
        when(dao.updateAccountState(account))
                .thenReturn(true)
                .thenReturn(false);
        when(account.income(1234.65)).thenReturn(true);
        //Positive test
        boolean result = am.paymentIn(
                u,1234.65,"opis",134);
        assertTrue(result);
        //NegativeTest
        result = am.paymentIn(
                u,1234.65,"opis",134);
        assertFalse(result);
        verify(history,atLeastOnce())
                .logOperation(any(PaymentIn.class),eq(true));
        verify(history,atLeastOnce())
                .logOperation(any(PaymentIn.class),eq(false));
    }

    @Test
    public void paymentOutSuccess() throws SQLException, OperationIsNotAllowedException {
        Account account = mock(Account.class);
        User user = mock(User.class);
        when(dao.findAccountById(anyInt()))
                .thenReturn(account);
        when(auth.canInvokeOperation(
                any(Withdraw.class),
                any(User.class)))
                .thenReturn(true);
        when(account.outcome(anyDouble()))
                .thenReturn(true);
        when(dao.updateAccountState(any(Account.class)))
                .thenReturn(true);
        double ammount=1234.65;
        String desc= "desc";
        int accountId=133;
        boolean result = am.paymentOut(
                user,
                ammount,
                desc,
                accountId);
        assertTrue(result);
        verify(auth,atLeastOnce())
           .canInvokeOperation(
                   any(Withdraw.class),eq(user));
        verify(dao,atLeastOnce())
           .findAccountById(accountId);
        verify(history,times(1))
           .logOperation(
                   any(Withdraw.class),eq(true));
        verify(account, times(1))
           .outcome(ammount);
        verify(dao,times(1))
           .updateAccountState(account);
        verify(history,never())
            .logOperation(
                    any(Withdraw.class),eq(false));
    }

    @Test(expected=OperationIsNotAllowedException.class)
    public void paymentOutUnauthorized() throws Exception {
        Account account = mock(Account.class);
        User user = mock(User.class);
        when(dao.findAccountById(anyInt()))
                .thenReturn(account);
        when(auth.canInvokeOperation(
                any(Withdraw.class),
                any(User.class)))
                .thenReturn(false);
        double ammount=1234.65;
        String desc= "desc";
        int accountId=133;
        try {
            boolean result = am.paymentOut(
                user,
                ammount,
                desc,
                accountId);
            assertFalse(result);
        }
        catch(Exception e) {
            verify(auth, atLeastOnce())
                    .canInvokeOperation(
                            any(Withdraw.class), eq(user));
            verify(dao, atLeastOnce())
                    .findAccountById(accountId);
            verify(history, never())
                    .logOperation(
                            any(Withdraw.class), eq(true));
            verify(account, never())
                    .outcome(ammount);
            verify(dao, never())
                    .updateAccountState(account);
            verify(history, times(1))
                    .logUnauthorizedOperation(
                            any(Withdraw.class), eq(false));
            throw e;
        }
    }

    @Test
    public void internalPayment() throws OperationIsNotAllowedException, SQLException {
        User user = mock(User.class);
        Account sourceAccount = mock(Account.class);
        Account destAccount = mock(Account.class);
        when(dao.findAccountById(12))
                .thenReturn(sourceAccount);
        when(dao.findAccountById(21))
                .thenReturn(destAccount);
        when(auth.canInvokeOperation(any(Operation.class),eq(user)))
                .thenReturn(true);
        when(sourceAccount.outcome(2400)).thenReturn(true);
        when(destAccount.income(2400)).thenReturn(true);
        when(dao.updateAccountState(sourceAccount)).thenReturn(true);
        when(dao.updateAccountState(destAccount)).thenReturn(true);

        boolean success = am.internalPayment(user,
                2400,"desc",
                12,21);
        assertTrue(success);
        verify(history,times(1))
                .logOperation(any(Withdraw.class),eq(true));
        verify(history,times(2))
               .logOperation(any(Payment.class),eq(true));
        verify(sourceAccount,times(1))
                .outcome(2400);
        verify(destAccount,times(1))
                .income(2400);
        verify(destAccount,never())
                .outcome(anyDouble());
        verify(sourceAccount,never())
                .income(anyDouble());
    }

}