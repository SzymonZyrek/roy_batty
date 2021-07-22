package edu.szyrek.roybatty.macro;

import java.util.ArrayList;

import edu.szyrek.roybatty.Keys;
import edu.szyrek.roybatty.RoyBatty;
import edu.szyrek.roybatty.screens.RecorderScreen;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.*;

public class Recorder implements NativeKeyListener, NativeMouseListener, NativeMouseWheelListener, NativeMouseMotionListener {
    private volatile boolean recording = false;
    private Long lastEventTime;
    private ArrayList<MacroEntry> entries;
    private Macro macro;

    public Recorder(final RecorderScreen gui) {
        GlobalScreen.addNativeKeyListener(this);
        GlobalScreen.addNativeMouseMotionListener(this);
        GlobalScreen.addNativeMouseWheelListener(this);
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
        recording = (true);
        lastEventTime = System.currentTimeMillis();
    }

    public void stopRecording() {
        macro = new Macro(entries);
        recording = (false);
        macro.printMacro();
        RoyBatty.logInfo("MACRO IN MEMORY");
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        if (recording)
        {
            final Long nowTime = System.currentTimeMillis();
            this.entries.add(new MouseMacro(e.getX(), e.getY(), (int)(nowTime-lastEventTime)));
            lastEventTime = nowTime;
        }
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
        if (recording)
        {
            final Long nowTime = System.currentTimeMillis();
            entries.add(new MouseMacro(e.getX(), e.getY(), (int)(nowTime-lastEventTime)));
            lastEventTime = nowTime;
        }
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        if (recording)
        {
            final Long nowTime = System.currentTimeMillis();
            int code = Keys.jnativeToAwtCodes(e.getKeyCode());
            if (code != -1)
            {
                entries.add(new KeyPressMacro(code, (int)(nowTime-lastEventTime)));
                lastEventTime = nowTime;
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        if (recording)
        {
            final Long nowTime = System.currentTimeMillis();
            int code = Keys.jnativeToAwtCodes(e.getKeyCode());
            if (code != -1)
            {
                entries.add(new KeyReleaseMacro(code, (int)(nowTime-lastEventTime)));
                lastEventTime = nowTime;
            }
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {

    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        if (recording)
        {
            final Long nowTime = System.currentTimeMillis();
            if (e.getButton() == NativeMouseEvent.BUTTON1)
            {
                entries.add(new LeftClickMacro(e.getX(), e.getY(), (int)(nowTime-lastEventTime)));
            }
            else if (e.getButton() == NativeMouseEvent.BUTTON2)
            {
                entries.add(new RightClickMacro(e.getX(), e.getY(), (int)(nowTime-lastEventTime)));
            }
            lastEventTime = nowTime;
        }
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        if (recording)
        {
            final Long nowTime = System.currentTimeMillis();
            if (e.getButton() == NativeMouseEvent.BUTTON1)
            {
                entries.add(new LeftReleaseMacro(e.getX(), e.getY(), (int)(nowTime-lastEventTime)));
            }
            else if (e.getButton() == NativeMouseEvent.BUTTON2)
            {
                entries.add(new RightReleaseMacro(e.getX(), e.getY(), (int)(nowTime-lastEventTime)));
            }
            lastEventTime = nowTime;
        }
    }

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
        if (recording)
        {
            final Long nowTime = System.currentTimeMillis();
            entries.add(new ScrollMacro(-e.getWheelRotation(), (int)(nowTime-lastEventTime)));
            lastEventTime = nowTime;
        }
    }
}
