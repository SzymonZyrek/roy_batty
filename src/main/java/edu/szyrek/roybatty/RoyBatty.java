package edu.szyrek.roybatty;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.szyrek.roybatty.hotkey.Assignments;
import edu.szyrek.roybatty.recorder.Recorder;
import edu.szyrek.roybatty.screens.SwingFrame;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.keyboard.NativeKeyEvent;

import javax.crypto.Mac;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class RoyBatty
{
    public final static String APPLICATION_NAME = "roy_batty";
    public final static String APPLICATION_VERSION = "0.0.1";

    public final static int WINDOW_WIDTH = RoyBattyConfig.getConfig().getWindowWidth();
    public final static int WINDOW_HEIGHT = RoyBattyConfig.getConfig().getWindowHeight();
    public final static int WINDOW_STARTX = RoyBattyConfig.getConfig().getWindowStartX();
    public final static int WINDOW_STARTY = RoyBattyConfig.getConfig().getWindowStartX();
    public final static int RECORD_BUTTON = RoyBattyConfig.getConfig().getRecordButton();
    public final static int PLAY_BUTTON = RoyBattyConfig.getConfig().getPlayButton();
    public final static String FILE_ENCODING = RoyBattyConfig.getConfig().getFileEncoding();
    public final static String MACRO_FILE_NAME = RoyBattyConfig.getConfig().getMacroName();
    public final static String RECORD_LABEL = RoyBattyConfig.getConfig().getRecLabel();
    public final static String ADD_LABEL = RoyBattyConfig.getConfig().getAddLabel();
    public final static String PLAY_LABEL = RoyBattyConfig.getConfig().getPlayLabel();
    public final static String STOP_LABEL = RoyBattyConfig.getConfig().getStopLabel();
    public final static String SAVE_LABEL = RoyBattyConfig.getConfig().getSaveLabel();
    public final static String LOAD_LABEL = RoyBattyConfig.getConfig().getLoadLabel();
    public final static String MACROS_PATH = RoyBattyConfig.getConfig().getMacrosPath();

    public final static String CONFIG_PATH = "./roy_batty.json";

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
    @Getter
    private static final Set<String> availableMacros = Collections.synchronizedSet(new HashSet<>());

    static
    {
        try
        {
            Files.list(Paths.get(RoyBatty.MACROS_PATH)).forEach(path->
            {
                registerMacro(path.getFileName().toString());
            });
        }
        catch (IOException e)
        {
            RoyBatty.logException("Problem loading macros list from [" + MACROS_PATH + "]: ", e);
        }
    }

    public static void registerMacro(final String name)
    {
        availableMacros.add(name);
    }

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
