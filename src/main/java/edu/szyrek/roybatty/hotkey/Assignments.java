package edu.szyrek.roybatty.hotkey;

import edu.szyrek.roybatty.RoyBatty;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class Assignments  implements NativeKeyListener
{
    private Map<Integer, Assignment> assignmentMap = new HashMap<>();
    private Map<Integer, Assignment> assignmentsPlaying = new HashMap<>();


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
    public void nativeKeyReleased(final NativeKeyEvent event)
    {
        if (assignmentMap.containsKey(event.getKeyCode()))
        {
            Assignment assignment = assignmentMap.get(event.getKeyCode());
            if (MacroAssignment.class.isAssignableFrom(assignment.getClass()))
            {
                final MacroAssignment macroAssignment = (MacroAssignment) assignment;
                if (assignmentsPlaying.containsKey(event.getKeyCode()))
                {
                    macroAssignment.stop();
                    if (assignmentsPlaying.containsKey(event.getKeyCode()))
                    {
                        assignmentsPlaying.remove(event.getKeyCode());
                    }
                    return;
                }
                else
                {
                    assignmentsPlaying.put(event.getKeyCode(), assignment);
                }
                new Thread(()->
                {
                    try
                    {
                        macroAssignment.getFuture().get();
                        if (assignmentsPlaying.containsKey(event.getKeyCode()))
                        {
                            assignmentsPlaying.remove(event.getKeyCode());
                        }
                    }
                    catch (InterruptedException|ExecutionException ex)
                    {
                        RoyBatty.logException("Error while waiting for macro to finish", ex);
                    }
                }).start();

            }
            assignment.run();
        }
    }

}
