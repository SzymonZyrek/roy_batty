package edu.szyrek.roybatty.screens;

import edu.szyrek.roybatty.RoyBatty;
import edu.szyrek.roybatty.RoyBattyConfig;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.dispatcher.SwingDispatchService;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
public class SwingFrame extends JFrame implements WindowListener
{

    public SwingFrame()
    {
        GlobalScreen.setEventDispatcher(new SwingDispatchService());

        setTitle(RoyBatty.APPLICATION_NAME + ":" + RoyBatty.APPLICATION_VERSION);
        setBounds(RoyBattyConfig.getConfig().getWindowStartX(), RoyBattyConfig.getConfig().getWindowStartY(), RoyBattyConfig.getConfig().getWindowWidth(), RoyBattyConfig.getConfig().getWindowHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(this);
        setVisible(true);

        final MainScreen mainScreen = new MainScreen();
        setContentPane(mainScreen);
    }

    @Override
    @SneakyThrows
    public void windowOpened(WindowEvent e)
    {
        try
        {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex)
        {
            RoyBatty.logException("There was a problem registering the native hook.", ex);
            Thread.sleep(5000);
            System.exit(1);
        }
        silenceLoggers();
    }

    @Override
    public void windowClosed(WindowEvent e)
    {
        try
        {
            GlobalScreen.unregisterNativeHook();
        }
        catch (NativeHookException e1)
        {
            e1.printStackTrace();
        }
        System.runFinalization();
        System.exit(0);
    }

    @Override public void windowClosing(WindowEvent e) {/* Unimplemented */}
    @Override public void windowIconified(WindowEvent e) {/* Unimplemented */}
    @Override public void windowDeiconified(WindowEvent e) {/* Unimplemented */}
    @Override public void windowActivated(WindowEvent e) {/* Unimplemented */}
    @Override public void windowDeactivated(WindowEvent e) {/* Unimplemented */}

    private void silenceLoggers()
    {
        final Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        final Handler[] handlers = Logger.getLogger("").getHandlers();
        for (int i = 0; i < handlers.length; i++)
        {
            handlers[i].setLevel(Level.OFF);
        }
    }

}
