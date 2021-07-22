package edu.szyrek.roybatty;

import edu.szyrek.roybatty.screens.SwingFrame;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;

@Slf4j
public class RoyBatty {
    public final static int WINDOW_WIDTH = 450;
    public final static int WINDOW_HEIGHT = 300;
    public final static int WINDOW_STARTX = 100;
    public final static int WINDOW_STARTY = 100;
    public final static int RECORD_BUTTON = NativeKeyEvent.VC_F11;
    public final static int PLAY_BUTTON = NativeKeyEvent.VC_F12;
    public final static String FILE_ENCODING = "UTF-8";
    public final static String APPLICATION_NAME = "roy_batty";
    public final static String APPLICATION_VERSION = "0.0.1";
    public final static String MACRO_FILE_NAME = "edu.szyrek.roybatty.macro.Macro.txt";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SwingFrame());
    }

    private static JLabel statusBar;

    public static void setStatusBar(final JLabel sb) {
        statusBar = sb;
    }

    public static void logException(final String msg, final Exception ex) {
        logError(msg);
        log.error(ex.getMessage());
        ex.printStackTrace();
    }

    public static void logError(final String msg) {
        if (statusBar != null)
            statusBar.setText(msg);
        log.error(msg);
    }

    public static void logInfo(final String msg) {
        if (statusBar != null)
            statusBar.setText(msg);
        log.info(msg);
    }

}
