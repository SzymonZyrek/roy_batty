package edu.szyrek.roybatty.screens;

import edu.szyrek.roybatty.RoyBatty;
import edu.szyrek.roybatty.RoyBattyConfig;
import edu.szyrek.roybatty.hotkey.Hotkey;
import edu.szyrek.roybatty.macro.Macro;
import edu.szyrek.roybatty.player.Player;

import lombok.Setter;
import org.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RecorderScreen extends JPanel
{
    @Setter
    private Player macroPlayer;
    private JButton recordButton;
    private JButton playButton;
    private JTextField fileName;

    public JButton createRecordButton()
    {
        JButton theRecordButton = new JButton(RoyBattyConfig.getConfig().getRecLabel());
        theRecordButton.addActionListener(e ->
        {
            recStopAction();
        });
        theRecordButton.setSize(158, 25);
        return theRecordButton;
    }

    public JTextField createFileNameField()
    {
        JTextField fnf = new JTextField(RoyBattyConfig.getConfig().getMacroName());
        fnf.setSize(300, 25);
        fnf.setMaximumSize(new Dimension(300, 25));
        return fnf;
    }

    public JButton createPlayButton()
    {
        JButton thePlayButton = new JButton(RoyBattyConfig.getConfig().getPlayLabel());
        thePlayButton.addActionListener(e ->
        {
            playStopAction();
        });
        thePlayButton.setSize(158, 25);
        return thePlayButton;
    }

    public JButton createSaveButton()
    {
        JButton saveButton = new JButton(RoyBattyConfig.getConfig().getSaveLabel());
        saveButton.addActionListener(e ->
        {
            if (!RoyBatty.getMacroRecorder().isRecording())
            {
                if (RoyBatty.getMacroRecorder().getMacro() == null)
                {
                    RoyBatty.logError("Record or load something first!");
                }
                RoyBatty.getMacroRecorder().getMacro().saveMacroFile(fileName.getText());
                RoyBatty.getMacroRecorder().setMacro(Macro.loadMacroFile(fileName.getText()));
            }
        });
        saveButton.setSize(121, 25);
        return saveButton;
    }

    public JButton createLoadButton()
    {
        JButton loadButton = new JButton(RoyBattyConfig.getConfig().getLoadLabel());
        loadButton.addActionListener(e ->
        {
            if (!RoyBatty.getMacroRecorder().isRecording())
            {
                RoyBatty.getMacroRecorder().setMacro(Macro.loadMacroFile(fileName.getText()));
            }
        });
        loadButton.setSize(121, 25);
        return loadButton;
    }

    private JPanel createInfoPanel()
    {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        JLabel lblPresssTo = new JLabel("Press \"" + RoyBattyConfig.getConfig().getRecordButton() + "\" to start/stop recording.");
        lblPresssTo.setSize(400, 16);
        infoPanel.add(lblPresssTo);

        JLabel lblPressdTo = new JLabel("Press \"" + RoyBattyConfig.getConfig().getPlayButton() + "\" to start/stop replaying.");
        lblPressdTo.setSize(400, 16);
        infoPanel.add(lblPressdTo);

        return infoPanel;
    }

    private void craeteGUI()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel saveLoadPanel = new JPanel();
        saveLoadPanel.setLayout(new BoxLayout(saveLoadPanel, BoxLayout.X_AXIS));
        saveLoadPanel.add(createLoadButton());
        saveLoadPanel.add(createSaveButton());
        fileName = createFileNameField();
        add(fileName);
        add(saveLoadPanel);
        add(createInfoPanel());

        JPanel recPlayPanel = new JPanel();
        recPlayPanel.setLayout(new BoxLayout(recPlayPanel, BoxLayout.X_AXIS));
        recordButton = createRecordButton();
        recPlayPanel.add(recordButton);
        playButton = createPlayButton();
        recPlayPanel.add(playButton);
        add(recPlayPanel);
    }

    public RecorderScreen()
    {
        craeteGUI();
        RoyBatty.getAssignments().assign(new Hotkey(RoyBattyConfig.getConfig().getRecordButton()), () -> recStopAction());
        RoyBatty.getAssignments().assign(new Hotkey(RoyBattyConfig.getConfig().getPlayButton()), () -> playStopAction());
    }

    private void recStopAction()
    {
        if (RoyBatty.getMacroRecorder().isRecording())
        {
            recordButton.setText(RoyBattyConfig.getConfig().getRecLabel());
            RoyBatty.getMacroRecorder().stopRecording();
        }
        else
        {
            macroPlayer = null;
            recordButton.setText(RoyBattyConfig.getConfig().getStopLabel());
            RoyBatty.getMacroRecorder().startRecording();
        }
    }

    private void playStopAction()
    {
        if (RoyBatty.getMacroRecorder().isRecording())
        {
            RoyBatty.logError("Stop recording first!");
            return;
        }
        else
        {
            if (RoyBatty.getMacroRecorder().getMacro() == null)
            {
                RoyBatty.logError("Record or load something first!");
                return;
            }
            if (macroPlayer == null)
            {
                macroPlayer = new Player(RoyBatty.getMacroRecorder().getMacro());
            }
            if (!macroPlayer.isRunning())
            {
                setMacroPlayer(macroPlayer);
                macroPlayer.setFuture(new CompletableFuture<>());
                RoyBatty.getAssignments().unassign(new Hotkey(RoyBattyConfig.getConfig().getRecordButton()));
                macroPlayer.start();
                playButton.setText(RoyBattyConfig.getConfig().getStopLabel());
            }
            else
            {
                macroPlayer.stop();
            }
            new Thread(()->{
                try
                {
                    macroPlayer.getFuture().get();
                    playButton.setText(RoyBattyConfig.getConfig().getPlayLabel());
                    RoyBatty.getAssignments().assign(new Hotkey(RoyBattyConfig.getConfig().getRecordButton()), () -> recStopAction());
                }
                catch (InterruptedException|ExecutionException e)
                {
                    RoyBatty.logException("Failed to turn off player", e);
                }
            }).start();
        }
    }
}