package edu.szyrek.roybatty.screens;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.corba.se.spi.ior.ObjectKey;
import edu.szyrek.roybatty.KeyMappings;
import edu.szyrek.roybatty.RoyBatty;
import edu.szyrek.roybatty.RoyBattyConfig;
import edu.szyrek.roybatty.hotkey.Hotkey;
import edu.szyrek.roybatty.hotkey.HotkeyListener;
import lombok.Getter;
import lombok.Setter;
import org.jnativehook.GlobalScreen;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ConfigScreen extends JPanel
{
    @Setter
    private Set<Integer> activatedCodes = new HashSet<>();
    @Setter
    private Set<Integer> activeCodes = new HashSet<>();

    private TextField windowWidthField;
    private TextField windowHeightField;
    private TextField windowStartXField;
    private TextField windowStartYField;
    private JButton recordButtonField;
    private JButton playButtonField;
    private TextField fileEncodingField;
    private TextField macrosPathField;
    private TextField assignmentsPathField;
    private TextField macroNameField;
    private TextField recLabelField;
    private TextField addLabelField;
    private TextField playLabelField;
    private TextField stopLabelField;
    private TextField saveLabelField;
    private TextField loadLabelField;
    private TextField cancelLabelField;

    private void createGUI()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel warningLabel = new JLabel("NOTE: you have to restart the app after clicking "+RoyBattyConfig.getConfig().getSaveLabel()+" for changes to take effect");


        JPanel windowWidthPanel = new JPanel();
        windowWidthPanel.setLayout(new BoxLayout(windowWidthPanel, BoxLayout.X_AXIS));
        JLabel windowWidthLabel = new JLabel("Window width");
        windowWidthPanel.add(windowWidthLabel);
        windowWidthField = new TextField(Integer.toString(RoyBattyConfig.getConfig().getWindowWidth()));
        windowWidthPanel.add(windowWidthField);
        add(windowWidthPanel);

        JPanel windowHeightPanel = new JPanel();
        windowHeightPanel.setLayout(new BoxLayout(windowHeightPanel, BoxLayout.X_AXIS));
        JLabel windoHeightLabel = new JLabel("Window height");
        windowHeightPanel.add(windoHeightLabel);
        windowHeightField = new TextField(Integer.toString(RoyBattyConfig.getConfig().getWindowHeight()));
        windowHeightPanel.add(windowHeightField);
        add(windowHeightPanel);

        JPanel windowStartXPanel = new JPanel();
        windowStartXPanel.setLayout(new BoxLayout(windowStartXPanel, BoxLayout.X_AXIS));
        JLabel widnowStartXLabel = new JLabel("Window StartX");
        windowStartXPanel.add(widnowStartXLabel);
        windowStartXField = new TextField(Integer.toString(RoyBattyConfig.getConfig().getWindowStartX()));
        windowStartXPanel.add(windowStartXField);
        add(windowStartXPanel);

        JPanel windowStartYPanel = new JPanel();
        windowStartYPanel.setLayout(new BoxLayout(windowStartYPanel, BoxLayout.X_AXIS));
        JLabel widnowStartYLabel = new JLabel("Window StartY");
        windowStartYPanel.add(widnowStartYLabel);
        windowStartYField = new TextField(Integer.toString(RoyBattyConfig.getConfig().getWindowStartY()));
        windowStartYPanel.add(windowStartYField);
        add(windowStartYPanel);

        JPanel recordButtonPanel = new JPanel();
        recordButtonPanel.setLayout(new BoxLayout(recordButtonPanel, BoxLayout.X_AXIS));
        JLabel recordButtonLabel = new JLabel(RoyBattyConfig.getConfig().getRecLabel() + " button");
        recordButtonPanel.add(recordButtonLabel);
        recordButtonField = new JButton(new Hotkey(RoyBattyConfig.getConfig().getRecordButton()).toString());
        recordButtonPanel.add(recordButtonField);
        add(recordButtonPanel);

        CompletableFuture<Integer> recBtnFuture = new CompletableFuture<>();
        final HotkeyListener recBtnListener;

        recBtnListener = new HotkeyListener(activatedCodes, activeCodes, recordButtonField);
        recBtnListener.setFuture(recBtnFuture);

        recordButtonField.addActionListener(e ->
        {
            recordButtonField.setText("???");
            RoyBatty.getAssignments().setActive(false);
            GlobalScreen.addNativeKeyListener(recBtnListener);
            new Thread(()->
            {
                try
                {
                    recBtnListener.getFuture().get();
                    GlobalScreen.removeNativeKeyListener(recBtnListener);
                    RoyBatty.getAssignments().setActive(true);
                    recBtnListener.setFuture(new CompletableFuture<>());
                }
                catch (InterruptedException| ExecutionException ex)
                {
                    RoyBatty.logException("Problem removing listener: ", ex);
                }
            }).start();
        });

        JPanel playButtonPanel = new JPanel();
        playButtonPanel.setLayout(new BoxLayout(playButtonPanel, BoxLayout.X_AXIS));
        JLabel playButtonLabel = new JLabel(RoyBattyConfig.getConfig().getPlayLabel() + " button");
        playButtonPanel.add(playButtonLabel);
        playButtonField = new JButton(new Hotkey(RoyBattyConfig.getConfig().getPlayButton()).toString());
        playButtonPanel.add(playButtonField);
        add(playButtonPanel);

        CompletableFuture<Integer> playBtnFuture = new CompletableFuture<>();
        final HotkeyListener playBtnListener;

        playBtnListener = new HotkeyListener(activatedCodes, activeCodes, playButtonField);
        playBtnListener.setFuture(playBtnFuture);

        playButtonField.addActionListener(e ->
        {
            playButtonField.setText("???");
            RoyBatty.getAssignments().setActive(false);
            GlobalScreen.addNativeKeyListener(playBtnListener);
            new Thread(()->
            {
                try
                {
                    playBtnListener.getFuture().get();
                    GlobalScreen.removeNativeKeyListener(playBtnListener);
                    RoyBatty.getAssignments().setActive(true);
                    playBtnListener.setFuture(new CompletableFuture<>());
                }
                catch (InterruptedException| ExecutionException ex)
                {
                    RoyBatty.logException("Problem removing listener: ", ex);
                }
            }).start();
        });



        JPanel fileEncodingPanel = new JPanel();
        fileEncodingPanel.setLayout(new BoxLayout(fileEncodingPanel, BoxLayout.X_AXIS));
        JLabel fileEncodingLabel = new JLabel("File encoding");
        fileEncodingPanel.add(fileEncodingLabel);
        fileEncodingField = new TextField(RoyBattyConfig.getConfig().getFileEncoding());
        fileEncodingPanel.add(fileEncodingField);
        add(fileEncodingPanel);

        JPanel macrosPathPanel = new JPanel();
        macrosPathPanel.setLayout(new BoxLayout(macrosPathPanel, BoxLayout.X_AXIS));
        JLabel macrosPathLabel = new JLabel("Macro folder");
        macrosPathPanel.add(macrosPathLabel);
        macrosPathField = new TextField(RoyBattyConfig.getConfig().getMacrosPath());
        macrosPathPanel.add(macrosPathField);
        add(macrosPathPanel);

        JPanel assignmentsPathPanel = new JPanel();
        assignmentsPathPanel.setLayout(new BoxLayout(assignmentsPathPanel, BoxLayout.X_AXIS));
        JLabel assignmentsPathLabel = new JLabel("Assignments file");
        assignmentsPathPanel.add(assignmentsPathLabel);
        assignmentsPathField = new TextField(RoyBattyConfig.getConfig().getAssignmentsPath());
        assignmentsPathPanel.add(assignmentsPathField);
        add(assignmentsPathPanel);

        JPanel macroNamePanel = new JPanel();
        macroNamePanel.setLayout(new BoxLayout(macroNamePanel, BoxLayout.X_AXIS));
        JLabel macroNameLabel = new JLabel("Macro name");
        macroNamePanel.add(macroNameLabel);
        macroNameField = new TextField(RoyBattyConfig.getConfig().getMacroName());
        macroNamePanel.add(macroNameField);
        add(macroNamePanel);

        JPanel recLabelPanel = new JPanel();
        recLabelPanel.setLayout(new BoxLayout(recLabelPanel, BoxLayout.X_AXIS));
        JLabel recLabelLabel = new JLabel(RoyBattyConfig.getConfig().getRecLabel()+" label");
        recLabelPanel.add(recLabelLabel);
        recLabelField = new TextField(RoyBattyConfig.getConfig().getRecLabel());
        recLabelPanel.add(recLabelField);
        add(recLabelPanel);

        JPanel addLabelPanel = new JPanel();
        addLabelPanel.setLayout(new BoxLayout(addLabelPanel, BoxLayout.X_AXIS));
        JLabel addLabelLabel = new JLabel(RoyBattyConfig.getConfig().getAddLabel()+" label");
        addLabelPanel.add(addLabelLabel);
        addLabelField = new TextField(RoyBattyConfig.getConfig().getAddLabel());
        addLabelPanel.add(addLabelField);
        add(addLabelPanel);
        
        JPanel playLabelPanel = new JPanel();
        playLabelPanel.setLayout(new BoxLayout(playLabelPanel, BoxLayout.X_AXIS));
        JLabel playLabelLabel = new JLabel(RoyBattyConfig.getConfig().getPlayLabel()+" label");
        playLabelPanel.add(playLabelLabel);
        playLabelField = new TextField(RoyBattyConfig.getConfig().getPlayLabel());
        playLabelPanel.add(playLabelField);
        add(playLabelPanel);

        JPanel stopLabelPanel = new JPanel();
        stopLabelPanel.setLayout(new BoxLayout(stopLabelPanel, BoxLayout.X_AXIS));
        JLabel stopLabelLabel = new JLabel(RoyBattyConfig.getConfig().getStopLabel()+" label");
        stopLabelPanel.add(stopLabelLabel);
        stopLabelField = new TextField(RoyBattyConfig.getConfig().getStopLabel());
        stopLabelPanel.add(stopLabelField);
        add(stopLabelPanel);

        JPanel saveLabelPanel = new JPanel();
        saveLabelPanel.setLayout(new BoxLayout(saveLabelPanel, BoxLayout.X_AXIS));
        JLabel saveLabelLabel = new JLabel(RoyBattyConfig.getConfig().getSaveLabel()+" label");
        saveLabelPanel.add(saveLabelLabel);
        saveLabelField = new TextField(RoyBattyConfig.getConfig().getSaveLabel());
        saveLabelPanel.add(saveLabelField);
        add(saveLabelPanel);

        JPanel loadLabelPanel = new JPanel();
        loadLabelPanel.setLayout(new BoxLayout(loadLabelPanel, BoxLayout.X_AXIS));
        JLabel loadLabelLabel = new JLabel(RoyBattyConfig.getConfig().getLoadLabel()+" label");
        loadLabelPanel.add(loadLabelLabel);
        loadLabelField = new TextField(RoyBattyConfig.getConfig().getLoadLabel());
        loadLabelPanel.add(loadLabelField);
        add(loadLabelPanel);

        JPanel cancelLabelPanel = new JPanel();
        cancelLabelPanel.setLayout(new BoxLayout(cancelLabelPanel, BoxLayout.X_AXIS));
        JLabel cancelLabelLabel = new JLabel(RoyBattyConfig.getConfig().getCancelLabel()+" label");
        cancelLabelPanel.add(cancelLabelLabel);
        cancelLabelField = new TextField(RoyBattyConfig.getConfig().getCancelLabel());
        cancelLabelPanel.add(cancelLabelField);
        add(cancelLabelPanel);

        add(createSaveButton());
        add(createReloadButton());
    }

    public ConfigScreen()
    {
        createGUI();
    }

    public JButton createSaveButton()
    {
        JButton saveButton = new JButton(RoyBattyConfig.getConfig().getSaveLabel());
        saveButton.addActionListener(e ->
        {
            RoyBattyConfig.getConfig().setWindowHeight(Integer.parseInt(windowHeightField.getText()));
            RoyBattyConfig.getConfig().setWindowWidth(Integer.parseInt(windowWidthField.getText()));
            RoyBattyConfig.getConfig().setWindowStartX(Integer.parseInt(windowStartXField.getText()));
            RoyBattyConfig.getConfig().setWindowStartY(Integer.parseInt(windowStartYField.getText()));
            RoyBattyConfig.getConfig().setRecordButton(recordButtonField.getText());
            RoyBattyConfig.getConfig().setPlayButton(playButtonField.getText());
            RoyBattyConfig.getConfig().setFileEncoding(fileEncodingField.getText());
            RoyBattyConfig.getConfig().setMacrosPath(macrosPathField.getText());
            RoyBattyConfig.getConfig().setAssignmentsPath(assignmentsPathField.getText());
            RoyBattyConfig.getConfig().setMacroName(macroNameField.getText());
            RoyBattyConfig.getConfig().setRecLabel(recLabelField.getText());
            RoyBattyConfig.getConfig().setPlayLabel(playLabelField.getText());
            RoyBattyConfig.getConfig().setStopLabel(stopLabelField.getText());
            RoyBattyConfig.getConfig().setCancelLabel(cancelLabelField.getText());
            RoyBattyConfig.getConfig().setAddLabel(addLabelField.getText());
            RoyBattyConfig.getConfig().setLoadLabel(loadLabelField.getText());
            RoyBattyConfig.getConfig().setSaveLabel(saveLabelField.getText());

            final ObjectMapper om = new ObjectMapper();
            try
            {
                om.writeValueAsString(RoyBattyConfig.getConfig());
                if (Files.exists(Paths.get(RoyBatty.CONFIG_PATH)))
                {
                    Files.delete(Paths.get(RoyBatty.CONFIG_PATH));
                }
                try (PrintWriter out = new PrintWriter(RoyBatty.CONFIG_PATH)) {
                    out.println(om.writeValueAsString(RoyBattyConfig.getConfig()));
                }
            }
            catch (IOException ex)
            {
                RoyBatty.logException("Error while saving configuration at "+RoyBatty.CONFIG_PATH, ex);
            }
        });
        saveButton.setSize(158, 25);
        return saveButton;
    }

    public JButton createReloadButton()
    {
        JButton reloadButton = new JButton(RoyBattyConfig.getConfig().getCancelLabel());
        reloadButton.addActionListener(e ->
        {
            RoyBattyConfig.reload();
            this.removeAll();
            this.createGUI();
            repaint();
            revalidate();
        });
        reloadButton.setSize(158, 25);
        return reloadButton;
    }
}
