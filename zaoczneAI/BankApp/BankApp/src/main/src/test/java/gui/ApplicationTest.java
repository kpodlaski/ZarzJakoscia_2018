package gui;

import biz.AccountManager;
import model.User;
import model.exceptions.UserUnnkownOrBadPasswordException;
import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.swing.*;

import java.lang.reflect.Field;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Krzysztof Podlaski on 24.03.2018.
 */
public class ApplicationTest {
    Application app;
    JButton signInButton;
    @Mock
    AccountManager accountManager;
    @Mock
    JLabel userInfo;
    @Mock
    JFrame additionalWindow;
    @Mock
    LoginForm loginForm;
    @Mock
    JTextField userNameTextField;
    @Mock
    JPasswordField passwordField;


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @org.junit.Before
    public void setUp() throws Exception {
        app = new Application();
        signInButton = mock(JButton.class);

        app.loginForm = loginForm;
        Field f = app.getClass().getDeclaredField("signInButton");
        f.setAccessible(true);
        f.set(app,signInButton);
        f = app.getClass().getDeclaredField("accountManager");
        f.setAccessible(true);
        f.set(app,accountManager);
        f = app.getClass().getDeclaredField("userInfo");
        f.setAccessible(true);
        f.set(app,userInfo);
        f = app.getClass().getDeclaredField("additionalWindow");
        f.setAccessible(true);
        f.set(app,additionalWindow);
        loginForm.passwordField=passwordField;
        loginForm.userNameTextField=userNameTextField;

    }

    @org.junit.Test
    public void sucessLogInOperation() throws Exception {
        when(passwordField.getPassword())
                .thenReturn("Adam".toCharArray());
        when(userNameTextField.getText())
                .thenReturn("Adam");
        when(accountManager
                .logIn("Adam","Adam".toCharArray()))
                .thenReturn(true);
        User u = mock(User.class);
        when(accountManager.getLoggedUser())
                .thenReturn(u);
        when(u.getName()).thenReturn("Adam");
        app.logInOperation();
        verify(accountManager,atLeast(1))
                .logIn("Adam","Adam".toCharArray());

        verify(additionalWindow).setVisible(false);
        verify(additionalWindow).dispose();
        verify(signInButton).setText("Logout");
        verify(userInfo).setText("Adam");

    }

    @org.junit.Test
    public void failedLogInOperation() throws Exception {
        when(passwordField.getPassword())
                .thenReturn("A".toCharArray());
        when(userNameTextField.getText())
                .thenReturn("Adam");
        when(accountManager
                .logIn("Adam","A".toCharArray()))
                .thenThrow(
            new UserUnnkownOrBadPasswordException("Bad Password"));
        app.logInOperation();
        verify(additionalWindow,never()).setVisible(false);
        verify(additionalWindow,never()).dispose();
        verify(signInButton,never()).setText(anyString());
        verify(userInfo,never()).setText(anyString());
    }

}