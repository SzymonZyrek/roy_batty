package edu.szyrek.roybatty.screens;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.szyrek.roybatty.RoyBatty;
import edu.szyrek.roybatty.RoyBattyConfig;
import edu.szyrek.roybatty.hotkey.Hotkey;
import edu.szyrek.roybatty.hotkey.HotkeyListener;
import edu.szyrek.roybatty.lookandfeel.Styled;
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

    private JTextField windowWidthField;
    private JTextField windowHeightField;
    private JTextField windowStartXField;
    private JTextField windowStartYField;
    private JButton recordButtonField;
    private JButton playButtonField;
    private JTextField fileEncodingField;
    private JTextField macrosPathField;
    private JTextField assignmentsPathField;
    private JTextField macroNameField;
    private JTextField recLabelField;
    private JTextField addLabelField;
    private JTextField playLabelField;
    private JTextField stopLabelField;
    private JTextField saveLabelField;
    private JTextField loadLabelField;
    private JTextField cancelLabelField;

    private void createGUI()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel warningLabel = new JLabel("NOTE: you have to restart the app after clicking "+RoyBattyConfig.getConfig().getSaveLabel()+" for changes to take effect");


        windowWidthField = new JTextField(Integer.toString(RoyBattyConfig.getConfig().getWindowWidth()));
        add(Styled.newLabledField("Window width", windowWidthField));

        windowHeightField = new JTextField(Integer.toString(RoyBattyConfig.getConfig().getWindowHeight()));
        add(Styled.newLabledField("Window height", windowHeightField));

        windowStartXField = new JTextField(Integer.toString(RoyBattyConfig.getConfig().getWindowStartX()));
        add(Styled.newLabledField("Window StartX", windowStartXField));

        windowStartYField = new JTextField(Integer.toString(RoyBattyConfig.getConfig().getWindowStartY()));
        add(Styled.newLabledField("Window StartY", windowStartYField));

        recordButtonField = Styled.newStyledButton(new Hotkey(RoyBattyConfig.getConfig().getRecordButton()).toString());
        add(Styled.newLabledField(RoyBattyConfig.getConfig().getRecLabel() + " button", recordButtonField));

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

        playButtonField = Styled.newStyledButton(new Hotkey(RoyBattyConfig.getConfig().getPlayButton()).toString());
        add(Styled.newLabledField(RoyBattyConfig.getConfig().getPlayLabel() + " button", playButtonField));

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

        fileEncodingField = new JTextField(RoyBattyConfig.getConfig().getFileEncoding());
        add(Styled.newLabledField("File encoding", fileEncodingField));

        macrosPathField = new JTextField(RoyBattyConfig.getConfig().getMacrosPath());
        add(Styled.newLabledField("Macro folder", macrosPathField));

        assignmentsPathField = new JTextField(RoyBattyConfig.getConfig().getAssignmentsPath());
        add(Styled.newLabledField(RoyBattyConfig.getConfig().getAssignmentsPath(), assignmentsPathField));

        macroNameField = new JTextField(RoyBattyConfig.getConfig().getMacroName());
        add(Styled.newLabledField("Macro name", macroNameField));

        recLabelField = new JTextField(RoyBattyConfig.getConfig().getRecLabel());
        add(Styled.newLabledField(RoyBattyConfig.getConfig().getRecLabel()+" label", recLabelField));

        addLabelField = new JTextField(RoyBattyConfig.getConfig().getAddLabel());
        add(Styled.newLabledField(RoyBattyConfig.getConfig().getAddLabel()+" label", addLabelField));

        playLabelField = new JTextField(RoyBattyConfig.getConfig().getPlayLabel());
        add(Styled.newLabledField(RoyBattyConfig.getConfig().getPlayLabel()+" label", playLabelField));

        stopLabelField = new JTextField(RoyBattyConfig.getConfig().getStopLabel());
        add(Styled.newLabledField(RoyBattyConfig.getConfig().getStopLabel()+" label", stopLabelField));

        saveLabelField = new JTextField(RoyBattyConfig.getConfig().getSaveLabel());
        add(Styled.newLabledField(RoyBattyConfig.getConfig().getSaveLabel()+" label", saveLabelField));

        loadLabelField = new JTextField(RoyBattyConfig.getConfig().getLoadLabel());
        add(Styled.newLabledField(RoyBattyConfig.getConfig().getLoadLabel()+" label", loadLabelField));

        cancelLabelField = new JTextField(RoyBattyConfig.getConfig().getCancelLabel());
        add(Styled.newLabledField(RoyBattyConfig.getConfig().getCancelLabel()+" label", cancelLabelField));

        add(Styled.getVerticalSeparator());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.add(createSaveButton());
        buttonsPanel.add(createReloadButton());
        add(buttonsPanel);
    }

    public ConfigScreen()
    {
        createGUI();
    }

    public JButton createSaveButton()
    {
        JButton saveButton = Styled.newStyledButton(RoyBattyConfig.getConfig().getSaveLabel());
        saveButton.addActionListener(e ->
        {
            if (!new Hotkey(recordButtonField.getText()).isValid())
            {
                RoyBatty.logError("Wrong record button hotkey");
                recordButtonField.setForeground(Color.RED);
                return;
            }
            else
            {
                recordButtonField.setForeground(Color.BLACK);
            }
            if (!new Hotkey(playButtonField.getText()).isValid())
            {
                RoyBatty.logError("Wrong play button hotkey");
                playButtonField.setForeground(Color.RED);
                return;
            }
            else
            {
                playButtonField.setForeground(Color.BLACK);
            }

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
                    RoyBatty.logInfo("Saved configuration to "+RoyBatty.CONFIG_PATH);
                }
            }
            catch (IOException ex)
            {
                RoyBatty.logException("Error while saving configuration at "+RoyBatty.CONFIG_PATH, ex);
            }
        });
        return saveButton;
    }

    public JButton createReloadButton()
    {
        JButton reloadButton = Styled.newStyledButton(RoyBattyConfig.getConfig().getCancelLabel());
        reloadButton.addActionListener(e ->
        {
            RoyBattyConfig.reload();
            this.removeAll();
            this.createGUI();
            repaint();
            revalidate();
        });
        return reloadButton;
    }
}
