import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.dispatcher.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecorderGUI extends JFrame implements WindowListener, NativeKeyListener {
    private final Recorder recorder = new Recorder(this);
    private Player macroPlayer;
    public JButton btnStartRecording;
    public JLabel statusBar;

    public RecorderGUI() {
        GlobalScreen.setEventDispatcher(new SwingDispatchService());
        GlobalScreen.addNativeKeyListener(this);

        setTitle(RoyBatty.APPLICATION_NAME+":"+RoyBatty.APPLICATION_VERSION);
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

        JTextField fileName =  new JTextField(RoyBatty.MACRO_FILE_NAME);
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
        btnNewButton.addActionListener(e -> {
            if (!recorder.isRecording()) {
                recorder.setMacro(Macro.loadMacroFile(fileName.getText()));
            }
        });
        btnNewButton.setBounds(254, 201, 121, 25);
        getContentPane().add(btnNewButton);


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
        getContentPane().add(btnSaveMacro);

        statusBar = new JLabel("");
        statusBar.setBounds(112, 13, 300, 16);
        getContentPane().add(statusBar);
        RoyBatty.setStatusBar(statusBar);

        JLabel lblPresssTo = new JLabel("Press \""+ NativeKeyEvent.getKeyText(RoyBatty.RECORD_BUTTON) +"\" to start/stop recording.");
        lblPresssTo.setBounds(12, 81, 400, 16);
        getContentPane().add(lblPresssTo);

        JLabel lblPressdTo = new JLabel("Press \""+ NativeKeyEvent.getKeyText(RoyBatty.PLAY_BUTTON) +"\" to start/stop a playing.");
        lblPressdTo.setBounds(12, 111, 400, 16);
        getContentPane().add(lblPressdTo);

        addWindowListener(this);
        setVisible(true);
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
            RoyBatty.logError("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            System.exit(1);
        }



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

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == RoyBatty.RECORD_BUTTON && !recorder.isRecording()) {
            recorder.startRecording();
        } else if (e.getKeyCode() == RoyBatty.RECORD_BUTTON) {
            recorder.stopRecording();
        }
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
}
