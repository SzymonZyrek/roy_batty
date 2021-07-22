package edu.szyrek.roybatty;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
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
    private int windowHeight = 200;
    @Getter
    @Setter
    private int windowStartX = 100;
    @Getter
    @Setter
    private int windowStartY = 100;
    @Getter
    @Setter
    private int recordButton = NativeKeyEvent.VC_F11;
    @Getter
    @Setter
    private int playButton = NativeKeyEvent.VC_F12;
    @Getter
    @Setter
    private String fileEncoding = "UTF-8";
    @Getter
    @Setter
    private String appName = "roy_batty";
    @Getter
    @Setter
    private String appVer = "0.0.1";
    @Getter
    @Setter
    private String macrosPath = "./macros";
    @Getter
    @Setter
    private String macroName = "MyMacro";
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

    private RoyBattyConfig(){}

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
                RoyBatty.logException("Error loading config from path " + RoyBatty.CONFIG_PATH + ", using default values", e);
                instance = new RoyBattyConfig();
            }
        }
    }

    public static RoyBattyConfig getConfig()
    {
        return Loader.instance;
    }
}
