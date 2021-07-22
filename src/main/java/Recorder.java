import java.util.ArrayList;

import org.jnativehook.GlobalScreen;
import org.jnativehook.dispatcher.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import org.jnativehook.mouse.NativeMouseMotionListener;

public class Recorder implements NativeKeyListener, NativeMouseListener, NativeMouseMotionListener {
    private volatile boolean recording = false;
    private RecorderGUI gui;
    private Long lastEventTime;
    private ArrayList<MacroEntry> entries;
    private Macro macro;

    public Recorder(final RecorderGUI gui) {
        this.gui = gui;
        GlobalScreen.addNativeKeyListener(this);
        GlobalScreen.addNativeMouseMotionListener(this);
        GlobalScreen.addNativeMouseListener(this);
    }

    public boolean isRecording() {
        return recording;
    }

    public Macro getMacro() {
        return this.macro;
    }

    public void setMacro(final Macro macro) {
        this.macro = macro;
    }

    public void startRecording() {
        this.entries = new ArrayList<>();
        gui.btnStartRecording.setText("Stop Recording");
        recording = (true);
        lastEventTime = System.currentTimeMillis();
    }

    public void stopRecording() {
        macro = new Macro(entries);
        gui.btnStartRecording.setText("Start Recording");
        recording = (false);
        macro.printMacro();
        RoyBatty.logInfo("MACRO IN MEMORY");
    }

    public void nativeKeyReleased(NativeKeyEvent e) {

    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        if (recording)
        {
            final Long nowTime = System.currentTimeMillis();
            this.entries.add(new MoveMacro(e.getX(), e.getY(), (int)(nowTime-lastEventTime)));
            lastEventTime = nowTime;
        }
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
        if (recording)
        {
            final Long nowTime = System.currentTimeMillis();
            macro.addEntry(new MoveMacro(e.getX(), e.getY(), (int)(nowTime-lastEventTime)));
            lastEventTime = nowTime;
        }
    }

    public void nativeKeyPressed(NativeKeyEvent e) {

    }

    public void nativeKeyTyped(NativeKeyEvent e) {

    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {

    }
}
