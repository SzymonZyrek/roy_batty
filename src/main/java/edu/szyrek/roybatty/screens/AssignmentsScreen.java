package edu.szyrek.roybatty.screens;

import edu.szyrek.roybatty.KeyMappings;
import edu.szyrek.roybatty.RoyBatty;
import edu.szyrek.roybatty.hotkey.MacroAssignment;
import edu.szyrek.roybatty.macro.Macro;
import org.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;
import java.util.Locale;

public class AssignmentsScreen extends JPanel
{


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

        JTextField keyField = new JTextField("hotkey");
        assignmentPanel.add(keyField);
        JTextField fileField = new JTextField("/macro/file");
        assignmentPanel.add(fileField);

        JCheckBox repeatCheckbox = new JCheckBox("repeat");
        assignmentPanel.add(repeatCheckbox);

        JButton assignButton = new JButton("Assign");
        assignButton.addActionListener(e ->
        {
            RoyBatty.getAssignments().assign(
                    KeyMappings.textToJnativeCodes(keyField.getText().toUpperCase()),
                    new MacroAssignment(Macro.loadMacroFile(fileField.getText()), repeatCheckbox.isSelected())
            );
        });
        assignButton.setSize(158, 25);
        assignmentPanel.add(assignButton);

        JButton unssignButton = new JButton("Unassign");
        unssignButton.addActionListener(e ->
        {
            RoyBatty.getAssignments().unassign(
                    KeyMappings.textToJnativeCodes(keyField.getText().toUpperCase())
            );
        });
        unssignButton.setSize(158, 25);
        assignmentPanel.add(unssignButton);
        add(assignmentPanel);
        repaint();
        revalidate();
    }
}
