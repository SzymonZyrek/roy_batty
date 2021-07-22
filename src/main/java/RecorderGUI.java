import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecorderGUI extends JFrame implements WindowListener {
    public final static String APPLICATION_NAME = "roy_batty";
    public final static String APPLICATION_VERSION = "0.0.1";
    public final static String MACRO_FILE_NAME = "Macro.txt";

    private final Recorder recorder = new Recorder(this);
    private Player macroPlayer;
    public JButton btnStartRecording;
    public JLabel statusBar;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecorderGUI());
    }

    public RecorderGUI() {
        setTitle(APPLICATION_NAME+":"+APPLICATION_VERSION);
        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);


        btnStartRecording = new JButton("Start Recording");
        btnStartRecording.addActionListener(e -> {
            if (recorder.isRecording()) {
                recorder.stopRecording();
            } else {
                recorder.startRecording();
            }
        });
        btnStartRecording.setBounds(12, 163, 158, 25);
        getContentPane().add(btnStartRecording);

        JTextField fileName =  new JTextField(MACRO_FILE_NAME);
        fileName.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
            }
            public void removeUpdate(DocumentEvent e) {
            }
            public void insertUpdate(DocumentEvent e) {
            }
        });
        fileName.setBounds(254, 240, 121, 25);
        getContentPane().add(fileName);

        JButton btnNewButton = new JButton("Load Macro");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!recorder.isRecording()) {
                    recorder.loadMacroFile(fileName.getText());
                }
            }
        });
        btnNewButton.setBounds(254, 201, 121, 25);
        getContentPane().add(btnNewButton);


        JButton btnSaveMacro = new JButton("Save Macro");
        btnSaveMacro.addActionListener(e -> {
            if (!recorder.isRecording()) {
                recorder.saveMacroFile(fileName.getText());
            }
        });
        btnSaveMacro.setBounds(254, 163, 121, 25);
        getContentPane().add(btnSaveMacro);

        statusBar = new JLabel("");
        statusBar.setBounds(112, 13, 300, 16);
        getContentPane().add(statusBar);

        JLabel lblPresssTo = new JLabel("Press \""+ NativeKeyEvent.getKeyText(Recorder.RECORD_BUTTON) +"\" to start/stop recording.");
        lblPresssTo.setBounds(12, 81, 400, 16);
        getContentPane().add(lblPresssTo);

        JLabel lblPressdTo = new JLabel("Press \""+ NativeKeyEvent.getKeyText(Recorder.PLAY_BUTTON) +"\" to start/stop a playing.");
        lblPressdTo.setBounds(12, 111, 400, 16);
        getContentPane().add(lblPressdTo);

        addWindowListener(this);
        setVisible(true);
    }

    public void logError(final String msg) {
        statusBar.setText(msg);
        System.err.println(msg);
    }

    public void logInfo(final String msg) {
        statusBar.setText(msg);
        System.out.println(msg);
    }

    public Player getMacroPlayer() {
        return macroPlayer;
    }

    public void setMacroPlayer(Player macroPlayer) {
        this.macroPlayer = macroPlayer;
    }

    public void windowOpened(WindowEvent e) {
        // Initialze native hook.
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            logError("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(recorder);
        GlobalScreen.addNativeMouseMotionListener(recorder);
        GlobalScreen.addNativeMouseListener(recorder);

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        Handler[] handlers = Logger.getLogger("").getHandlers();
        for (int i = 0; i < handlers.length; i++) {
            handlers[i].setLevel(Level.OFF);
        }
    }

    public void windowClosed(WindowEvent e) {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e1) {
            e1.printStackTrace();
        }
        System.runFinalization();
        System.exit(0);
    }

    public void windowClosing(WindowEvent e) {
        /* Unimplemented */ }

    public void windowIconified(WindowEvent e) {
        /* Unimplemented */ }

    public void windowDeiconified(WindowEvent e) {
        /* Unimplemented */ }

    public void windowActivated(WindowEvent e) {
        /* Unimplemented */ }

    public void windowDeactivated(WindowEvent e) {
        /* Unimplemented */ }
}
