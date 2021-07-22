package edu.szyrek.roybatty;

import edu.szyrek.roybatty.hotkey.Assignments;
import edu.szyrek.roybatty.recorder.Recorder;
import edu.szyrek.roybatty.screens.SwingFrame;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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
    public static String CONFIG_PATH = "./roy_batty.conf";

    @Setter
    private static JLabel statusBar;
    @Getter
    private static final Assignments assignments = new Assignments();
    @Getter
    private static final Recorder macroRecorder = new Recorder();
    @Getter
    private static final Set<String> availableMacros = Collections.synchronizedSet(new HashSet<>());

    public static void registerMacro(final String name)
    {
        availableMacros.add(name);
    }

    public static void main(String[] args)
    {
        for (int i = 0; i < args.length; i++)
        {
            if (args[i].equals("-c"))
            {
                i+=1;
                CONFIG_PATH = args[i];
            }
        }

        if (!Files.exists(Paths.get(RoyBattyConfig.getConfig().getMacrosPath())))
        {
            try
            {
                Files.createDirectories(Paths.get(RoyBattyConfig.getConfig().getMacrosPath()));
            }
            catch (IOException e)
            {
                RoyBatty.logException("Error while creating macros dir: ", e);
            }
        }

        try
        {
            Files.list(Paths.get(RoyBattyConfig.getConfig().getMacrosPath())).forEach(path->
            {
                registerMacro(path.getFileName().toString());
            });
        }
        catch (IOException e)
        {
            RoyBatty.logException("Problem loading macros list from [" + RoyBattyConfig.getConfig().getMacrosPath() + "]: ", e);
        }

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
