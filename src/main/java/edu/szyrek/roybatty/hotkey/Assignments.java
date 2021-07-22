package edu.szyrek.roybatty.hotkey;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.HashMap;
import java.util.Map;

public class Assignments  implements NativeKeyListener
{
    private Map<Integer, Assignment> assignmentMap = new HashMap<>();

    public Assignments()
    {
        GlobalScreen.addNativeKeyListener(this);
    }

    public void assign(final Integer keyCode, final Assignment assignment)
    {
        assignmentMap.put(keyCode, assignment);
    }

    public void unassign(final Integer keyCode)
    {
        assignmentMap.remove(keyCode);
    }

    @Override public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {/* Unimplemented */}
    @Override public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {/* Unimplemented */}
    @Override
    public void nativeKeyReleased(final NativeKeyEvent e)
    {
        if (assignmentMap.containsKey(e.getKeyCode()))
        {
            assignmentMap.get(e.getKeyCode()).run();
        }
    }

}
