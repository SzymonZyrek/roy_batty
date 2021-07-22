package edu.szyrek.roybatty.recorder;

import java.util.ArrayList;

import edu.szyrek.roybatty.Keys;
import edu.szyrek.roybatty.RoyBatty;
import edu.szyrek.roybatty.macro.*;
import edu.szyrek.roybatty.macro.entry.*;
import edu.szyrek.roybatty.screens.RecorderScreen;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.*;

public class Recorder implements NativeKeyListener, NativeMouseListener, NativeMouseWheelListener, NativeMouseMotionListener {
    @Getter
    private volatile boolean recording = false;
    @Getter
    @Setter
    private Macro macro;
    private Long lastEventTime;
    private ArrayList<MacroEntry> entries;

    public Recorder(final RecorderScreen gui) {
        GlobalScreen.addNativeKeyListener(this);
        GlobalScreen.addNativeMouseMotionListener(this);
        GlobalScreen.addNativeMouseWheelListener(this);
        GlobalScreen.addNativeMouseListener(this);
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
            this.entries.add(new MouseEntry(e.getX(), e.getY(), (int)(nowTime-lastEventTime)));
            lastEventTime = nowTime;
        }
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
        if (recording)
        {
            final Long nowTime = System.currentTimeMillis();
            entries.add(new MouseEntry(e.getX(), e.getY(), (int)(nowTime-lastEventTime)));
            lastEventTime = nowTime;
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (recording)
        {
            final Long nowTime = System.currentTimeMillis();
            int code = Keys.jnativeToAwtCodes(e.getKeyCode());
            if (code != -1)
            {
                entries.add(new KeyPressEntry(code, (int)(nowTime-lastEventTime)));
                lastEventTime = nowTime;
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (recording)
        {
            final Long nowTime = System.currentTimeMillis();
            int code = Keys.jnativeToAwtCodes(e.getKeyCode());
            if (code != -1)
            {
                entries.add(new KeyReleaseEntry(code, (int)(nowTime-lastEventTime)));
                lastEventTime = nowTime;
            }
        }
    }

    @Override
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
                entries.add(new LeftClickEntry(e.getX(), e.getY(), (int)(nowTime-lastEventTime)));
            }
            else if (e.getButton() == NativeMouseEvent.BUTTON2)
            {
                entries.add(new RightClickEntry(e.getX(), e.getY(), (int)(nowTime-lastEventTime)));
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
                entries.add(new LeftReleaseEntry(e.getX(), e.getY(), (int)(nowTime-lastEventTime)));
            }
            else if (e.getButton() == NativeMouseEvent.BUTTON2)
            {
                entries.add(new RightReleaseEntry(e.getX(), e.getY(), (int)(nowTime-lastEventTime)));
            }
            lastEventTime = nowTime;
        }
    }

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
        if (recording)
        {
            final Long nowTime = System.currentTimeMillis();
            entries.add(new ScrollEntry(-e.getWheelRotation(), (int)(nowTime-lastEventTime)));
            lastEventTime = nowTime;
        }
    }
}
