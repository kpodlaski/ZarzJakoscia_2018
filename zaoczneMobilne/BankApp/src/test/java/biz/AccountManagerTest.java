package biz;

import db.dao.DAO;
import model.Account;
import model.Operation;
import model.User;
import model.exceptions.OperationIsNotAllowedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;


import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by Krzysztof Podlaski on 24.03.2018.
 */
public class AccountManagerTest {
    AccountManager accountManager;
    @Mock
    DAO dao;
    @Mock
    BankHistory history;
    @Mock
    AuthenticationManager auth;
    @Mock
    InterestOperator interestOperator;
    @Mock
    User loggedUser=null;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        accountManager = new AccountManager();
        accountManager.history=history;
        accountManager.dao=dao;
        accountManager.auth=auth;
        accountManager.interestOperator=interestOperator;
        accountManager.loggedUser=loggedUser;
    }

    @Test
    public void logIn() throws Exception {
        User u = new User();
        when(auth.logIn("Adam",
                "Adam".toCharArray()))
                .thenReturn(u);
        when(auth.logIn("Adam",
                "A".toCharArray()))
                .thenReturn(null);
        boolean result =accountManager.logIn("Adam",
        "Adam".toCharArray());
        assertTrue(result);
        assertEquals(accountManager.loggedUser,u);
        result =accountManager.logIn("Adam",
                "A".toCharArray());
        assertFalse(result);
        assertNull(accountManager.loggedUser);
        verify(auth,atLeast(2))
          .logIn(anyString(),any((new char[2]).getClass()));
    }

    @Test
    public void paymentIn() throws SQLException {
        Account account = mock(Account.class);
        when(dao.findAccountById(anyInt()))
                .thenReturn(account)
                .thenReturn(null);
        when(dao.updateAccountState(account))
                .thenReturn(true)
                .thenReturn(false);
        when(account.income(anyDouble()))
                .thenReturn(true);
        User u = mock(User.class);
        boolean result = accountManager.paymentIn(u,
                1235.6,
                "Some Operation",
                12);
        assertTrue(result);
        result = accountManager.paymentIn(u,
                1235.6,
                "Some Operation",
                12);

        assertFalse(result);
        verify(dao,atLeast(2)).findAccountById(12);
        verify(history,atMost(1))
                .logOperation(any(Operation.class),eq(true));
        verify(history,atMost(1))
                .logOperation(any(Operation.class),eq(false));
        verify(account,atMost(1))
                .income(1235.6);
    }

    @Test(expected=OperationIsNotAllowedException.class)
    public void unAuthorizedWithdraw() throws OperationIsNotAllowedException, SQLException {
        Account acc = mock(Account.class);
        when(auth.canInvokeOperation(any(Operation.class),
                any(User.class))).thenReturn(false);
        when(dao.findAccountById(anyInt()))
                .thenReturn(acc);
        accountManager.paymentOut(new User(),
                1234,
                "d",
                12);
        verify(history).logPaymentOut(
                eq(acc),
                eq(1234),
                eq(false));
    }
}