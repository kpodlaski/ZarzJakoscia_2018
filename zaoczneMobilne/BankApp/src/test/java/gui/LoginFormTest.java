
        package gui;

        import org.junit.Ignore;
        import org.junit.Test;

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

    @Ignore //Do poprawy
    @Test
    public void logInTest() throws NoSuchFieldException, IllegalAccessException {
        Application app = mock(Application.class);
        LoginForm form = new LoginForm(app);
        Field fbutton = form.getClass().getDeclaredField("loginStartButton");
        fbutton.setAccessible(true);
        JButton button = (JButton) fbutton.get(form);

        ActionEvent ae =
                new ActionEvent(button,ActionEvent.ACTION_PERFORMED,"click" );
        button.dispatchEvent(ae);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(ae);
        //Test czy wywołano operację logInOperation() na mockupie
        verify(app).logInOperation();
    }
}
