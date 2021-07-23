package edu.szyrek.roybatty.hotkey;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.szyrek.roybatty.RoyBatty;

import edu.szyrek.roybatty.RoyBattyConfig;
import edu.szyrek.roybatty.macro.Macro;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class Assignments  implements NativeKeyListener
{
    @Getter
    private Map<Hotkey, Assignment> assignmentMap = new HashMap<>();
    private Map<Hotkey, Assignment> assignmentsPlaying = new HashMap<>();

    private Set<Integer> activeKeyCodes = new HashSet<>();
    private Set<Integer> activatedKeyCodes = new HashSet<>();

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
        if (MacroAssignment.class.isAssignableFrom(assignment.getClass()))
        {
            RoyBatty.logInfo("Assigned ["+hotkey.toString()+"] to "+assignment);
        }
    }

    public void unassign(final Hotkey hotkey)
    {
        assignmentMap.remove(hotkey);
        RoyBatty.logInfo("Unassigned ["+hotkey.toString()+"]");
    }

    @Override public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {/* Unimplemented */}
    @Override public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent)
    {
        activatedKeyCodes.add(nativeKeyEvent.getKeyCode());
        activeKeyCodes.add(nativeKeyEvent.getKeyCode());
    }
    @Override
    public void nativeKeyReleased(final NativeKeyEvent event)
    {
        activeKeyCodes.remove(event.getKeyCode());
        if (activeKeyCodes.size() == 0)
        {
            final Hotkey combination = new Hotkey(activatedKeyCodes);

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
            activatedKeyCodes = new HashSet<>();
        }

    }

}
