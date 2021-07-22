import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.dispatcher.SwingDispatchService;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SwingFrame extends JFrame implements WindowListener {

    public SwingFrame()
    {
        GlobalScreen.setEventDispatcher(new SwingDispatchService());
        setTitle(RoyBatty.APPLICATION_NAME + ":" + RoyBatty.APPLICATION_VERSION);
        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(this);
        setVisible(true);

        MainScreen mainScreen = new MainScreen();
        setContentPane(mainScreen);
    }


    public void windowOpened(WindowEvent e) {
        // Initialze native hook.
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            RoyBatty.logError("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            System.exit(1);
        }


        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        Handler[] handlers = Logger.getLogger("").getHandlers();
        for (int i = 0; i < handlers.length; i++) {
            handlers[i].setLevel(Level.OFF);
        }
    }

    public void windowClosed(WindowEvent e) {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e1) {
            e1.printStackTrace();
        }
        System.runFinalization();
        System.exit(0);
    }

    public void windowClosing(WindowEvent e) {
        /* Unimplemented */
    }

    public void windowIconified(WindowEvent e) {
        /* Unimplemented */
    }

    public void windowDeiconified(WindowEvent e) {
        /* Unimplemented */
    }

    public void windowActivated(WindowEvent e) {
        /* Unimplemented */
    }

    public void windowDeactivated(WindowEvent e) {
        /* Unimplemented */
    }
}
