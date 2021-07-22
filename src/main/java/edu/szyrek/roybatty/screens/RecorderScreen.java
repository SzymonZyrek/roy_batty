import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class RecorderScreen extends JPanel implements NativeKeyListener {
    private final Recorder recorder = new Recorder(this);
    private Player macroPlayer;
    public JButton btnStartRecording;
    public JLabel statusBar;

    public RecorderScreen() {
        GlobalScreen.addNativeKeyListener(this);
        setLayout(new BorderLayout());

        btnStartRecording = new JButton("Start Recording");
        btnStartRecording.addActionListener(e -> {
            if (recorder.isRecording()) {
                btnStartRecording.setText("Start Recording");
                recorder.stopRecording();
            } else {
                btnStartRecording.setText("Stop Recording");
                recorder.startRecording();
            }
        });
        btnStartRecording.setBounds(12, 163, 158, 25);
        add(btnStartRecording);

        JTextField fileName = new JTextField(RoyBatty.MACRO_FILE_NAME);
        fileName.setBounds(254, 240, 121, 25);
        add(fileName);

        JButton btnNewButton = new JButton("Load Macro");
        btnNewButton.addActionListener(e -> {
            if (!recorder.isRecording()) {
                recorder.setMacro(Macro.loadMacroFile(fileName.getText()));
            }
        });
        btnNewButton.setBounds(254, 201, 121, 25);
        add(btnNewButton);

        JButton btnSaveMacro = new JButton("Save Macro");
        btnSaveMacro.addActionListener(e -> {
            if (!recorder.isRecording()) {
                if (recorder.getMacro() == null) {
                    RoyBatty.logError("Record or load something first!");
                }
                recorder.getMacro().saveMacroFile(fileName.getText());
            }
        });
        btnSaveMacro.setBounds(254, 163, 121, 25);
        add(btnSaveMacro);

        statusBar = new JLabel("");
        statusBar.setBounds(112, 13, 300, 16);
        add(statusBar);
        RoyBatty.setStatusBar(statusBar);

        JLabel lblPresssTo = new JLabel("Press \"" + NativeKeyEvent.getKeyText(RoyBatty.RECORD_BUTTON) + "\" to start/stop recording.");
        lblPresssTo.setBounds(12, 81, 400, 16);
        add(lblPresssTo);

        JLabel lblPressdTo = new JLabel("Press \"" + NativeKeyEvent.getKeyText(RoyBatty.PLAY_BUTTON) + "\" to start/stop a playing.");
        lblPressdTo.setBounds(12, 111, 400, 16);
        add(lblPressdTo);
    }

    public Player getMacroPlayer() {
        return macroPlayer;
    }

    public void setMacroPlayer(Player macroPlayer) {
        this.macroPlayer = macroPlayer;
    }


    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {

    }

    private void handleRecordHotkey(final NativeKeyEvent e) {
        if (e.getKeyCode() == RoyBatty.RECORD_BUTTON && !recorder.isRecording()) {
            btnStartRecording.setText("Stop Recording");
            recorder.startRecording();
        } else if (e.getKeyCode() == RoyBatty.RECORD_BUTTON) {
            btnStartRecording.setText("Start Recording");
            recorder.stopRecording();
        }
    }

    private void handlePlayHotkey(final NativeKeyEvent e) {
        if (e.getKeyCode() == RoyBatty.PLAY_BUTTON && recorder.isRecording()) {
            RoyBatty.logError("Stop recording first!");
        } else if (e.getKeyCode() == RoyBatty.PLAY_BUTTON && !recorder.isRecording()) {
            if (macroPlayer != null) {
                macroPlayer.running = !macroPlayer.running;
            }
            if (macroPlayer == null || macroPlayer.running) {
                if (recorder.getMacro() == null) {
                    RoyBatty.logError("Record or load something first!");
                }
                macroPlayer = new Player(recorder.getMacro());
                macroPlayer.running = true;
                Thread t1 = new Thread(macroPlayer, "T1");
                setMacroPlayer(macroPlayer);
                t1.start();
            } else {
                macroPlayer.stop();
            }
        }
    }

    @Override
    public void nativeKeyReleased(final NativeKeyEvent e) {
        handleRecordHotkey(e);
        handlePlayHotkey(e);
    }
}