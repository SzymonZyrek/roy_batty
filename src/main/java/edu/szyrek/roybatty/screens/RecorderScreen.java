package edu.szyrek.roybatty.screens;

import edu.szyrek.roybatty.RoyBatty;
import edu.szyrek.roybatty.hotkey.Assignment;
import edu.szyrek.roybatty.hotkey.Assignments;
import edu.szyrek.roybatty.macro.Macro;
import edu.szyrek.roybatty.player.Player;
import lombok.Setter;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import edu.szyrek.roybatty.recorder.Recorder;
import javax.swing.*;
import java.awt.*;

public class RecorderScreen extends JPanel
{
    @Setter
    private Player macroPlayer;
    private final Recorder recorder = new Recorder();

    private JButton recordButton;
    private JButton playButton;
    private JTextField fileName;
    private JLabel statusBar;

    public JButton createRecordButton()
    {
        JButton theRecordButton = new JButton(RoyBatty.RECORD_LABEL);
        theRecordButton.addActionListener(e ->
        {
            recStopAction();
        });
        theRecordButton.setSize(158, 25);
        return theRecordButton;
    }

    public JTextField createFileNameField()
    {
        JTextField fnf = new JTextField(RoyBatty.MACRO_FILE_NAME);
        fnf.setSize(300, 25);
        fnf.setMaximumSize(new Dimension(300, 25));
        return fnf;
    }

    public JButton createPlayButton()
    {
        JButton thePlayButton = new JButton(RoyBatty.PLAY_LABEL);
        thePlayButton.addActionListener(e ->
        {
            playStopAction();
        });
        thePlayButton.setSize(158, 25);
        return thePlayButton;
    }

    public JButton createSaveButton()
    {
        JButton saveButton = new JButton(RoyBatty.SAVE_LABEL);
        saveButton.addActionListener(e ->
        {
            if (!recorder.isRecording())
            {
                if (recorder.getMacro() == null)
                {
                    RoyBatty.logError("Record or load something first!");
                }
                recorder.getMacro().saveMacroFile(fileName.getText());
                recorder.setMacro(Macro.loadMacroFile(fileName.getText()));
            }
        });
        saveButton.setSize(121, 25);
        return saveButton;
    }

    public JButton createLoadButton()
    {
        JButton loadButton = new JButton(RoyBatty.LOAD_LABEL);
        loadButton.addActionListener(e ->
        {
            if (!recorder.isRecording())
            {
                recorder.setMacro(Macro.loadMacroFile(fileName.getText()));
            }
        });
        loadButton.setSize(121, 25);
        return loadButton;
    }

    private JLabel createStatusBar()
    {
        JLabel sb = new JLabel("");
        sb.setSize(300, 16);
        return sb;
    }

    private JPanel createInfoPanel()
    {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        JLabel lblPresssTo = new JLabel("Press \"" + NativeKeyEvent.getKeyText(RoyBatty.RECORD_BUTTON) + "\" to start/stop recording.");
        lblPresssTo.setSize(400, 16);
        infoPanel.add(lblPresssTo);

        JLabel lblPressdTo = new JLabel("Press \"" + NativeKeyEvent.getKeyText(RoyBatty.PLAY_BUTTON) + "\" to start/stop replaying.");
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

        statusBar = createStatusBar();
        add(statusBar);
        RoyBatty.setStatusBar(statusBar);
    }

    public RecorderScreen()
    {
        craeteGUI();
        RoyBatty.getAssignments().assign(RoyBatty.RECORD_BUTTON, () -> recStopAction());
        RoyBatty.getAssignments().assign(RoyBatty.PLAY_BUTTON, () -> playStopAction());
    }

    private void recStopAction()
    {
        if (recorder.isRecording())
        {
            recordButton.setText(RoyBatty.RECORD_LABEL);
            recorder.stopRecording();
        }
        else
        {
            recordButton.setText(RoyBatty.STOP_LABEL);
            recorder.startRecording();
        }
    }

    private void playStopAction()
    {
        if (recorder.isRecording())
        {
            RoyBatty.logError("Stop recording first!");
            return;
        }
        else
        {
            if (recorder.getMacro() == null)
            {
                RoyBatty.logError("Record or load something first!");
                return;
            }
            if (macroPlayer == null)
            {
                macroPlayer = new Player(recorder.getMacro());
            }
            if (!macroPlayer.isRunning())
            {
                setMacroPlayer(macroPlayer);
                macroPlayer.start();
                playButton.setText(RoyBatty.STOP_LABEL);
            }
            else
            {
                macroPlayer.stop();
                playButton.setText(RoyBatty.PLAY_LABEL);
            }
        }
    }
}