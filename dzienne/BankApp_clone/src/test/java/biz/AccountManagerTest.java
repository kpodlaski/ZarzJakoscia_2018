package biz;

import db.dao.DAO;
import model.Account;
import model.User;
import model.exceptions.UserUnnkownOrBadPasswordException;
import model.operations.PaymentIn;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
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
}