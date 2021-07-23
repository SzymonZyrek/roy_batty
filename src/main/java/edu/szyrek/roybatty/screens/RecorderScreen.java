package edu.szyrek.roybatty.screens;

import edu.szyrek.roybatty.RoyBatty;
import edu.szyrek.roybatty.RoyBattyConfig;
import edu.szyrek.roybatty.hotkey.Hotkey;
import edu.szyrek.roybatty.lookandfeel.Styled;
import edu.szyrek.roybatty.macro.Macro;
import edu.szyrek.roybatty.player.Player;

import lombok.Setter;
import org.jnativehook.keyboard.NativeKeyEvent;

import javax.crypto.Mac;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

    JCheckBox recordMovement = new JCheckBox("moves");
    JCheckBox recordClicks = new JCheckBox("clicks");
    JCheckBox recordWheel = new JCheckBox("wheel");
    JCheckBox recordKeys = new JCheckBox("keys");

    public JButton createRecordButton()
    {
        JButton theRecordButton = Styled.newStyledButton(RoyBattyConfig.getConfig().getRecLabel()+" (" + RoyBattyConfig.getConfig().getRecordButton() + ")");
        theRecordButton.addActionListener(e ->
        {
            recStopAction();
        });
        return theRecordButton;
    }

    public JButton createPlayButton()
    {
        JButton thePlayButton = Styled.newStyledButton(RoyBattyConfig.getConfig().getPlayLabel() +" (" + RoyBattyConfig.getConfig().getPlayButton() + ")");
        thePlayButton.addActionListener(e ->
        {
            playStopAction();
        });
        return thePlayButton;
    }

    public JButton createSaveButton()
    {
        JButton saveButton = Styled.newStyledButton(RoyBattyConfig.getConfig().getSaveLabel());
        saveButton.addActionListener(e ->
        {
            if (!RoyBatty.getMacroRecorder().isRecording())
            {
                if (fileName.getText().equals(RoyBattyConfig.getConfig().getMacroName()))
                {
                    RoyBatty.logError("Set macro name first!");
                    fileName.setForeground(Color.GRAY);
                    return;
                }
                if (RoyBatty.getMacroRecorder().getMacro() == null)
                {
                    RoyBatty.logError("Record or load something first!");
                    fileName.setForeground(Color.GRAY);
                    return;
                }
                RoyBatty.getMacroRecorder().getMacro().saveMacroFile(fileName.getText());
                RoyBatty.getMacroRecorder().setMacro(Macro.loadMacroFile(fileName.getText()));
                fileName.setForeground(Color.BLACK);
            }
        });
        return saveButton;
    }

    public JButton createLoadButton()
    {
        JButton loadButton = Styled.newStyledButton(RoyBattyConfig.getConfig().getLoadLabel());
        loadButton.addActionListener(e ->
        {
            if (RoyBatty.getMacroRecorder().isRecording())
            {
                RoyBatty.logError("Stop recording first!");
                fileName.setForeground(Color.GRAY);
                return;
            }
            final Macro macro = Macro.loadMacroFile(fileName.getText());
            if (macro != null)
            {
                RoyBatty.getMacroRecorder().setMacro(macro);
                fileName.setForeground(Color.BLACK);
            }
            else
            {
                fileName.setForeground(Color.RED);
            }

        });
        return loadButton;
    }

    private void craeteGUI()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JPanel theMacroFilePanel = new JPanel();
        theMacroFilePanel.setLayout(new BoxLayout(theMacroFilePanel, BoxLayout.X_AXIS));
        fileName = new JTextField(RoyBattyConfig.getConfig().getMacroName());
        fileName.setForeground(Color.GRAY);
        fileName.setMaximumSize(new Dimension(200,25));
        fileName.setSize(new Dimension(200,25));
        fileName.setMinimumSize(new Dimension(200,25));

        fileName.addFocusListener(new FocusListener()
        {
            private boolean firstTime = true;
            @Override
            public void focusGained(final FocusEvent e)
            {
                if (firstTime)
                {
                    firstTime = false;
                    return;
                }
                if (fileName.getText().equals(RoyBattyConfig.getConfig().getMacroName()))
                {
                    fileName.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e)
            {
                if (fileName.getText().equals(""))
                {
                    fileName.setText(RoyBattyConfig.getConfig().getMacroName());
                }
            }
        });

        theMacroFilePanel.add(Styled.getHorizontalSeparator());
        theMacroFilePanel.add(fileName);
        theMacroFilePanel.add(Styled.getHorizontalSeparator());
        theMacroFilePanel.add(createLoadButton());
        theMacroFilePanel.add(createSaveButton());
        theMacroFilePanel.add(Styled.getHorizontalSeparator());
        add(theMacroFilePanel);

        JPanel theRecordPanel = new JPanel();
        theRecordPanel.setLayout(new BoxLayout(theRecordPanel, BoxLayout.X_AXIS));
        JLabel theRecordLabel = new JLabel("Record:");
        theRecordPanel.add(theRecordLabel);

        JPanel theCheckboxesPanel = new JPanel();
        theCheckboxesPanel.setLayout(new BoxLayout(theCheckboxesPanel, BoxLayout.Y_AXIS));
        theCheckboxesPanel.add(recordMovement);
        theCheckboxesPanel.add(recordClicks);
        theCheckboxesPanel.add(recordWheel);
        theCheckboxesPanel.add(recordKeys);
        theRecordPanel.add(theCheckboxesPanel);
        add(theRecordPanel);

        JPanel recPlayPanel = new JPanel();
        recPlayPanel.setLayout(new BoxLayout(recPlayPanel, BoxLayout.X_AXIS));
        recordButton = createRecordButton();
        recPlayPanel.add(Styled.getHorizontalSeparator());
        recPlayPanel.add(recordButton);
        playButton = createPlayButton();
        recPlayPanel.add(playButton);
        recPlayPanel.add(Styled.getHorizontalSeparator());
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
            fileName.setForeground(Color.GRAY);
        }
        else
        {
            macroPlayer = null;
            recordButton.setText(RoyBattyConfig.getConfig().getStopLabel());
            RoyBatty.getMacroRecorder().startRecording(recordMovement.isSelected(), recordClicks.isSelected(), recordWheel.isSelected(), recordKeys.isSelected());
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