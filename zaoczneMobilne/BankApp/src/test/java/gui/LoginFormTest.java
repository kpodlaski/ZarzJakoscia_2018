
        package gui;

        import org.junit.Before;
        import org.junit.Ignore;
        import org.junit.Rule;
        import org.junit.Test;
        import org.mockito.Mock;
        import org.mockito.junit.MockitoJUnit;
        import org.mockito.junit.MockitoRule;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.lang.reflect.Field;

        import static org.junit.Assert.*;
        import static org.mockito.Mockito.mock;
        import static org.mockito.Mockito.verify;

/**
 * Created by Krzysztof Podlaski on 17.03.2018.
 */
public class LoginFormTest {
    LoginForm loginForm;
    private Application parent;

    @Mock
    private JButton loginStartButton;
    @Mock
    private JPasswordField passwordField;
    @Mock
    public  JTextField userNameTextField;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        parent = mock(Application.class); //To samo co @Mock
        loginForm = new LoginForm(parent);
        Field field =
                loginForm.getClass()
                .getDeclaredField("loginStartButton");
        field.setAccessible(true);
        field.set(loginForm,loginStartButton);
        field =
                loginForm.getClass()
                        .getDeclaredField("passwordField");
        field.setAccessible(true);
        field.set(loginForm,passwordField);
        field =
                loginForm.getClass()
                        .getDeclaredField("userNameTextField");
        field.setAccessible(true);
        field.set(loginForm,userNameTextField);
    }

    @Ignore
    @Test
    public void logInTest() throws NoSuchFieldException, IllegalAccessException {


        ActionEvent ae =
                new ActionEvent(loginStartButton,ActionEvent.ACTION_PERFORMED,"click" );
        loginStartButton.dispatchEvent(ae);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(ae);
        //Test czy wywołano operację logInOperation() na mockupie
        verify(parent).logInOperation();
    }
}
