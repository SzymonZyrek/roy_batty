package edu.szyrek.roybatty.hotkey;

import edu.szyrek.roybatty.KeyMappings;

import lombok.Getter;
import lombok.Setter;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class HotkeyListener implements NativeKeyListener {
    static volatile Object myLock = new Object();
    @Setter
    private Set<Integer> activatedCodes;
    @Setter
    private Set<Integer> activeCodes;
    private JButton keyButton;
    @Getter
    @Setter
    private CompletableFuture<Integer> future;

    public HotkeyListener(Set<Integer> activatedCodes, Set<Integer> activeCodes, JButton keyButton)
    {
        this.activatedCodes = activatedCodes;
        this.activeCodes = activeCodes;
        this.keyButton = keyButton;
    }


    @Override public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {/* Unimplemented */}
    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent)
    {
        activatedCodes.add(nativeKeyEvent.getKeyCode());
        activeCodes.add(nativeKeyEvent.getKeyCode());
    }
    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent)
    {
        activeCodes.remove(nativeKeyEvent.getKeyCode());
        if (activeCodes.size() == 0)
        {
            final StringBuilder builder = new StringBuilder();
            for (Integer i: activatedCodes)
            {
                builder.append(KeyMappings.nativeCodesToText(i) + "+");
            }
            String result = builder.toString();
            if (result.length() > 0)
            {
                result = result.substring(0, result.length()-1);
            }
            keyButton.setText(result);
            Set<Integer> toRemove = new HashSet<>();
            for (Integer i: activatedCodes)
            {
                toRemove.add(i);
            }
            activatedCodes.removeAll(toRemove);

            future.complete(0);
        }
    }
}
