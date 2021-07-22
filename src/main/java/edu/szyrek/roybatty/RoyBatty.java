package edu.szyrek.roybatty;

import edu.szyrek.roybatty.hotkey.Assignments;
import edu.szyrek.roybatty.recorder.Recorder;
import edu.szyrek.roybatty.screens.SwingFrame;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class RoyBatty
{
    public final static int WINDOW_WIDTH = 450;
    public final static int WINDOW_HEIGHT = 200;
    public final static int WINDOW_STARTX = 100;
    public final static int WINDOW_STARTY = 100;
    public final static int RECORD_BUTTON = NativeKeyEvent.VC_F11;
    public final static int PLAY_BUTTON = NativeKeyEvent.VC_F12;
    public final static String FILE_ENCODING = "UTF-8";
    public final static String APPLICATION_NAME = "roy_batty";
    public final static String APPLICATION_VERSION = "0.0.1";
    public final static String MACRO_FILE_NAME = "MyMacro";
    public final static String RECORD_LABEL = "Rec";
    public final static String ADD_LABEL = "Add";
    public final static String PLAY_LABEL = "Play";
    public final static String STOP_LABEL = "Stop";
    public final static String SAVE_LABEL = "Save";
    public final static String LOAD_LABEL = "Load";
    public final static String MACROS_PATH = "./macros";

    static
    {
        if (!Files.exists(Paths.get(MACROS_PATH)))
        {
            try
            {
                Files.createDirectories(Paths.get(MACROS_PATH));
            }
            catch (IOException e)
            {
                RoyBatty.logException("Error while creating macros dir: ", e);
            }
        }
    }

    @Setter
    private static JLabel statusBar;
    @Getter
    private static final Assignments assignments = new Assignments();
    @Getter
    private static final Recorder macroRecorder = new Recorder();


    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new SwingFrame());
    }

    public static void logException(final String msg, final Exception ex)
    {
        logError(msg);
        log.error(ex.getMessage());
        ex.printStackTrace();
    }

    public static void logError(final String msg)
    {
        if (statusBar != null)
        {
            statusBar.setText(msg);
            statusBar.setForeground(Color.RED);
        }
        log.error(msg);
    }

    public static void logInfo(final String msg)
    {
        if (statusBar != null)
        {
            statusBar.setText(msg);
            statusBar.setForeground(Color.BLACK);
        }
        log.info(msg);
    }

}
