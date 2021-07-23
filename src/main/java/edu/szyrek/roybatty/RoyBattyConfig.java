package edu.szyrek.roybatty;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.io.IOException;
import java.nio.file.Paths;

public class RoyBattyConfig
{
    @Getter
    @Setter
    private int windowWidth = 450;
    @Getter
    @Setter
    private int windowHeight = 600;
    @Getter
    @Setter
    private int windowStartX = 100;
    @Getter
    @Setter
    private int windowStartY = 100;
    @Getter
    @Setter
    private String recordButton = KeyMappings.nativeCodesToText(NativeKeyEvent.VC_F11);
    @Getter
    @Setter
    private String playButton = KeyMappings.nativeCodesToText(NativeKeyEvent.VC_F12);
    @Getter
    @Setter
    private String fileEncoding = "UTF-8";
    @Getter
    @Setter
    private String macrosPath = "./macros";
    @Getter
    @Setter
    private String assignmentsPath = "./assignments.conf";
    @Getter
    @Setter
    private String macroName = "Enter macro name";
    @Getter
    @Setter
    private String recLabel = "Rec";
    @Getter
    @Setter
    private String addLabel = "Add";
    @Getter
    @Setter
    private String playLabel = "Play";
    @Getter
    @Setter
    private String stopLabel = "Stop";
    @Getter
    @Setter
    private String saveLabel = "Save";
    @Getter
    @Setter
    private String loadLabel = "Load";
    @Getter
    @Setter
    private String cancelLabel = "Cancel";

    private RoyBattyConfig(){}

    @SneakyThrows
    public static void reload()
    {
        final ObjectMapper om = new ObjectMapper();
        Loader.instance = om.readValue(Paths.get(RoyBatty.CONFIG_PATH).toFile(), RoyBattyConfig.class);
    }

    private static class Loader
    {
        private static final ObjectMapper om = new ObjectMapper();
        private static RoyBattyConfig instance;

        static
        {
            try
            {
                instance = om.readValue(Paths.get(RoyBatty.CONFIG_PATH).toFile(), RoyBattyConfig.class);
            }
            catch (IOException e)
            {
                RoyBatty.logError("No configuration file at path: " + RoyBatty.CONFIG_PATH + ", using default values");
                instance = new RoyBattyConfig();
            }
        }
    }

    public static RoyBattyConfig getConfig()
    {
        return Loader.instance;
    }
}
