package gui;

import biz.AccountManager;
import model.User;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Krzysztof Podlaski on 12.03.2018.
 */
public class ApplicationTest {
    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void logInOperation() throws Exception {
        //Przygotowanie do testu
        Application a = new Application();
        AccountManager am =mock(AccountManager.class);
        Class amClass = a.getClass();
        Field am_field = amClass.getDeclaredField("accountManager");
        am_field.setAccessible(true);
        am_field.set(a,am); //Ustaw a obiekt mock am jako pole obiektu
        LoginForm lf = new LoginForm(a);
        Field lf_field = amClass.getDeclaredField("loginForm");
        lf_field.setAccessible(true);
        lf_field.set(a,lf);
        JPasswordField pass_mock = mock(JPasswordField.class);
        lf.passwordField = pass_mock;
        JTextField textField_mock = mock (JTextField.class);
        lf.userNameTextField=textField_mock;
        Field addWindow = amClass.getDeclaredField("additionalWindow");
        addWindow.setAccessible(true);
        addWindow.set(a,mock (JFrame.class));
        Field label = amClass.getDeclaredField("userInfo");
        label.setAccessible(true);
        label.set(a,mock(JLabel.class));
        User user = mock(User.class);
        when(am.getLoggedUser()).thenReturn(user);
        //Testujemy
        when(pass_mock.getPassword())
                .thenReturn("passwd".toCharArray());
        when(textField_mock.getText())
                .thenReturn("uname");
        when(am.logIn("uname","passwd".toCharArray()))
                .thenReturn(true);
        when(user.getName()).thenReturn("uname");
        a.logInOperation();
        //zliczać wywołanie metod przy logInOperation();
    }

}