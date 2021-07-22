import org.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;

public class RoyBatty {
    public final static int RECORD_BUTTON = NativeKeyEvent.VC_F11;
    public final static int PLAY_BUTTON = NativeKeyEvent.VC_F12;
    public final static String FILE_ENCODING = "UTF-8";
    public final static String APPLICATION_NAME = "roy_batty";
    public final static String APPLICATION_VERSION = "0.0.1";
    public final static String MACRO_FILE_NAME = "Macro.txt";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecorderGUI());
    }

    private static JLabel statusBar;

    public static void setStatusBar(final JLabel sb) {
        statusBar = sb;
    }

    public static void logError(final String msg) {
        if (statusBar != null)
            statusBar.setText(msg);
        System.err.println(msg);
    }

    public static void logInfo(final String msg) {
        if (statusBar != null)
            statusBar.setText(msg);
        System.out.println(msg);
    }

}
