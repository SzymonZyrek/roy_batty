package edu.szyrek.roybatty.screens;

import edu.szyrek.roybatty.KeyMappings;
import edu.szyrek.roybatty.RoyBatty;
import edu.szyrek.roybatty.hotkey.MacroAssignment;
import edu.szyrek.roybatty.macro.Macro;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.HashSet;
import java.util.Set;

public class AssignmentsScreen extends JPanel
{
    private Set<Integer> activatedCodes = new HashSet<>();
    private Set<Integer> activeCodes = new HashSet<>();

    public AssignmentsScreen()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(createAddButton());

    }

    public JButton createAddButton()
    {
        JButton addButton = new JButton(RoyBatty.ADD_LABEL);
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
        keyButton.addActionListener(e ->
        {
            keyButton.setText("???");
            RoyBatty.getAssignments().setActive(false);
            GlobalScreen.addNativeKeyListener(new NativeKeyListener()
            {
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
                        RoyBatty.getAssignments().setActive(true);
                    }
                }
            });
        });
        assignmentPanel.add(keyButton);

        JTextField fileField = new JTextField("/macro/file");
        assignmentPanel.add(fileField);

        JCheckBox repeatCheckbox = new JCheckBox("repeat");
        assignmentPanel.add(repeatCheckbox);

        JButton assignButton = new JButton("Assign");
        assignButton.addActionListener(e ->
        {
            RoyBatty.getAssignments().assign(
                    KeyMappings.textToJnativeCodes(keyButton.getText().toUpperCase()),
                    new MacroAssignment(Macro.loadMacroFile(fileField.getText()), repeatCheckbox.isSelected())
            );
        });
        assignButton.setSize(158, 25);
        assignmentPanel.add(assignButton);

        JButton unssignButton = new JButton("Unassign");
        unssignButton.addActionListener(e ->
        {
            RoyBatty.getAssignments().unassign(
                    KeyMappings.textToJnativeCodes(keyButton.getText().toUpperCase())
            );
        });
        unssignButton.setSize(158, 25);
        assignmentPanel.add(unssignButton);
        add(assignmentPanel);
        repaint();
        revalidate();
    }
}
