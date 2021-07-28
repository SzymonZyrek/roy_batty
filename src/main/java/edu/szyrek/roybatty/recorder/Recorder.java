package edu.szyrek.roybatty.recorder;

import edu.szyrek.roybatty.KeyMappings;
import edu.szyrek.roybatty.RoyBatty;
import edu.szyrek.roybatty.macro.*;
import edu.szyrek.roybatty.macro.entry.*;

import lombok.Getter;
import lombok.Setter;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.*;

import java.awt.*;
import java.util.ArrayList;

public class Recorder implements NativeKeyListener, NativeMouseListener, NativeMouseWheelListener, NativeMouseMotionListener {
    @Getter
    private volatile boolean recording = false;
    @Getter
    @Setter
    private Macro macro;
    private Long lastEventTime;
    private Point lastPosition;
    private ArrayList<MacroEntry> entries;
    private boolean recordMoves, recordClicks, recordWheel, recordKeys, relative;

    public Recorder()
    {
        GlobalScreen.addNativeKeyListener(this);
        GlobalScreen.addNativeMouseMotionListener(this);
        GlobalScreen.addNativeMouseWheelListener(this);
        GlobalScreen.addNativeMouseListener(this);
    }

    public void startRecording(final boolean moves, final boolean clicks, final boolean wheel, final boolean keys, final boolean relative)
    {
        this.entries = new ArrayList<>();
        this.recording = (true);
        this.lastEventTime = System.currentTimeMillis();
        this.lastPosition = MouseInfo.getPointerInfo().getLocation();
        this.recordMoves = moves;
        this.recordClicks = clicks;
        this.recordWheel = wheel;
        this.recordKeys = keys;
        this.relative = relative;
    }

    public void startRecording()
    {
        startRecording(true, true, true, true, false);
    }

    public void stopRecording()
    {
        macro = new Macro("", entries);
        recording = (false);
        RoyBatty.logInfo("Recorded "+entries.size()+ " entries");
    }

    @Override
    public void nativeMouseMoved(final NativeMouseEvent e)
    {
        if (recording && recordMoves)
        {
            final Long nowTime = System.currentTimeMillis();
            int x,y;
            final MouseEntry entry;
            final Point current = MouseInfo.getPointerInfo().getLocation();

            if (relative)
            {
                x = (current.x - lastPosition.x);
                y = (current.y - lastPosition.y);
                lastPosition = MouseInfo.getPointerInfo().getLocation();
                entry = new RelativeMouseEntry(x, y, (int)(nowTime-lastEventTime));
            }
            else
            {
                x = current.x;
                y = current.y;
                entry = new MouseEntry(x, y, (int)(nowTime-lastEventTime));
            }
            this.entries.add(entry);
            lastEventTime = nowTime;
        }
    }

    @Override
    public void nativeMouseDragged(final NativeMouseEvent e)
    {
        if (recording && recordMoves)
        {
            final Long nowTime = System.currentTimeMillis();
            entries.add(new MouseEntry(e.getX(), e.getY(), (int)(nowTime-lastEventTime)));
            lastEventTime = nowTime;
        }
    }

    @Override public void nativeKeyTyped(NativeKeyEvent e) {/* Unimplemented */}
    @Override public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {/* Unimplemented */}
    @Override
    public void nativeKeyPressed(final NativeKeyEvent e)
    {
        if (recording && recordKeys)
        {
            final Long nowTime = System.currentTimeMillis();
            int code = KeyMappings.jnativeToAwtCodes(e.getKeyCode());
            if (code != -1)
            {
                entries.add(new KeyPressEntry(code, (int)(nowTime-lastEventTime)));
                lastEventTime = nowTime;
            }
        }
    }

    @Override
    public void nativeKeyReleased(final NativeKeyEvent e)
    {
        if (recording && recordKeys)
        {
            final Long nowTime = System.currentTimeMillis();
            int code = KeyMappings.jnativeToAwtCodes(e.getKeyCode());
            if (code != -1)
            {
                entries.add(new KeyReleaseEntry(code, (int)(nowTime-lastEventTime)));
                lastEventTime = nowTime;
            }
        }
    }

    @Override
    public void nativeMousePressed(final NativeMouseEvent e)
    {
        if (recording && recordClicks)
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
    public void nativeMouseReleased(final NativeMouseEvent e)
    {
        if (recording && recordClicks)
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
    public void nativeMouseWheelMoved(final NativeMouseWheelEvent e)
    {
        if (recording && recordWheel)
        {
            final Long nowTime = System.currentTimeMillis();
            entries.add(new ScrollEntry(-e.getWheelRotation(), (int)(nowTime-lastEventTime)));
            lastEventTime = nowTime;
        }
    }
}
