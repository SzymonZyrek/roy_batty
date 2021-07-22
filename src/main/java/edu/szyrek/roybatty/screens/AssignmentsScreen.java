package edu.szyrek.roybatty.screens;

import edu.szyrek.roybatty.KeyMappings;
import edu.szyrek.roybatty.RoyBatty;
import edu.szyrek.roybatty.RoyBattyConfig;
import edu.szyrek.roybatty.hotkey.Hotkey;
import edu.szyrek.roybatty.hotkey.HotkeyListener;
import edu.szyrek.roybatty.hotkey.MacroAssignment;
import edu.szyrek.roybatty.macro.Macro;
import lombok.Setter;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AssignmentsScreen extends JPanel
{
    @Setter
    private Set<Integer> activatedCodes = new HashSet<>();
    @Setter
    private Set<Integer> activeCodes = new HashSet<>();

    public AssignmentsScreen()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(createAddButton());

    }

    public JButton createAddButton()
    {
        JButton addButton = new JButton(RoyBattyConfig.getConfig().getAddLabel());
        addButton.addActionListener(e ->
        {
            addAction();
        });
        addButton.setSize(158, 25);
        return addButton;
    }

    private void addAction()
    {
        JPanel assignmentPanel = new JPanel();
        assignmentPanel.setLayout(new BoxLayout(assignmentPanel, BoxLayout.X_AXIS));

        JButton keyButton = new JButton("hotkey");

        CompletableFuture<Integer> future = new CompletableFuture<>();
        final HotkeyListener listener;

        listener = new HotkeyListener(activatedCodes, activeCodes, keyButton);
        listener.setFuture(future);

        keyButton.addActionListener(e ->
        {
            keyButton.setText("???");
            RoyBatty.getAssignments().setActive(false);
            GlobalScreen.addNativeKeyListener(listener);
            new Thread(()->
            {
                try
                {
                    listener.getFuture().get();
                    GlobalScreen.removeNativeKeyListener(listener);
                    RoyBatty.getAssignments().setActive(true);
                    listener.setFuture(new CompletableFuture<>());
                }
                catch (InterruptedException|ExecutionException ex)
                {
                    RoyBatty.logException("Problem removing listener: ", ex);
                }
            }).start();
        });
        assignmentPanel.add(keyButton);

        JComboBox<String> fileField = new JComboBox<>(RoyBatty.getAvailableMacros().toArray(new String[0]));

        assignmentPanel.add(fileField);
        fileField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e)
            {
                RoyBatty.getAssignments().setActive(false);
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                RoyBatty.getAssignments().setActive(true);
            }
        });

        JCheckBox repeatCheckbox = new JCheckBox("repeat");
        assignmentPanel.add(repeatCheckbox);

        JButton assignButton = new JButton("Assign");
        assignButton.addActionListener(e ->
        {
            Set<Integer> codes = new HashSet<>();
            for (String s: keyButton.getText().split("\\+"))
            {
                codes.add(KeyMappings.textToJnativeCodes(s));
            }

            RoyBatty.getAssignments().assign(
                    new Hotkey(codes),
                    new MacroAssignment(Macro.loadMacroFile((String)fileField.getSelectedItem()), repeatCheckbox.isSelected())
            );

            listener.setActivatedCodes(activatedCodes);
        });
        assignButton.setSize(158, 25);
        assignmentPanel.add(assignButton);

        JButton unssignButton = new JButton("Unassign");
        unssignButton.addActionListener(e ->
        {
            Set<Integer> codes = new HashSet<>();
            for (String s: keyButton.getText().split("\\+"))
            {
                codes.add(KeyMappings.textToJnativeCodes(s));
            }
            RoyBatty.getAssignments().unassign(new Hotkey(codes));
        });
        unssignButton.setSize(158, 25);
        assignmentPanel.add(unssignButton);
        add(assignmentPanel);
        repaint();
        revalidate();
    }
}
