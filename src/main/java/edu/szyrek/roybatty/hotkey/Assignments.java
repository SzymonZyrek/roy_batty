package edu.szyrek.roybatty.hotkey;

import edu.szyrek.roybatty.RoyBatty;
import lombok.Getter;
import lombok.Setter;
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
    private Map<Hotkey, Assignment> assignmentMap = new HashMap<>();
    private Map<Hotkey, Assignment> assignmentsPlaying = new HashMap<>();
    @Getter
    @Setter
    private boolean active = true;

    public Assignments()
    {
        GlobalScreen.addNativeKeyListener(this);
    }

    public void assign(final Hotkey hotkey, final Assignment assignment)
    {
        assignmentMap.put(hotkey, assignment);
    }

    public void unassign(final Hotkey hotkey)
    {
        assignmentMap.remove(hotkey);
    }

    @Override public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {/* Unimplemented */}
    @Override public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {/* Unimplemented */}
    @Override
    public void nativeKeyReleased(final NativeKeyEvent event)
    {
        final Set<Integer> combinationCodes = new HashSet<>();
        combinationCodes.add(event.getKeyCode());
        final Hotkey combination = new Hotkey(combinationCodes);

        if (active && assignmentMap.containsKey(combination))
        {
            Assignment assignment = assignmentMap.get(combination);
            if (MacroAssignment.class.isAssignableFrom(assignment.getClass()))
            {
                final MacroAssignment macroAssignment = (MacroAssignment) assignment;
                if (assignmentsPlaying.containsKey(combination))
                {
                    macroAssignment.stop();
                    if (assignmentsPlaying.containsKey(combination))
                    {
                        assignmentsPlaying.remove(combination);
                    }
                    return;
                }
                else
                {
                    assignmentsPlaying.put(combination, assignment);
                }
                new Thread(()->
                {
                    try
                    {
                        macroAssignment.getFuture().get();
                        if (assignmentsPlaying.containsKey(combination))
                        {
                            assignmentsPlaying.remove(combination);
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
