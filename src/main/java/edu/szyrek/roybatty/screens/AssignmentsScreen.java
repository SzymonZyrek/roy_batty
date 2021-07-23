package edu.szyrek.roybatty.screens;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.szyrek.roybatty.KeyMappings;
import edu.szyrek.roybatty.RoyBatty;
import edu.szyrek.roybatty.RoyBattyConfig;
import edu.szyrek.roybatty.hotkey.*;
import edu.szyrek.roybatty.lookandfeel.Styled;
import edu.szyrek.roybatty.macro.Macro;

import lombok.Setter;
import lombok.SneakyThrows;
import org.jnativehook.GlobalScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AssignmentsScreen extends JPanel
{
    @Setter
    private Set<Integer> activatedCodes = new HashSet<>();
    @Setter
    private Set<Integer> activeCodes = new HashSet<>();
    private List<JButton> hotkeysMapped = new ArrayList<>();
    private List<JComboBox> macrosMapped = new ArrayList<>();
    private List<JCheckBox> repeastsMapped = new ArrayList<>();
    private List<JCheckBox> activesMapped = new ArrayList<>();

    @SneakyThrows
    private void loadAssignments()
    {
        final Path filePath = Paths.get(RoyBattyConfig.getConfig().getAssignmentsPath());
        if (Files.exists(filePath))
        {
            final ObjectMapper om = new ObjectMapper();
            for (final AssignmentEntry entry: om.readValue(
                    filePath.toFile(),
                    new TypeReference<List<AssignmentEntry>>() {}
            ))
            {
                addAction(entry.getHotkey(), entry.getMacroName(), entry.isRepeat(), entry.isActive());
                if (entry.isActive())
                {
                    RoyBatty.getAssignments().assign(entry.getHotkey(), new MacroAssignment(Macro.loadMacroFile(entry.getMacroName()), entry.isRepeat()));
                }
            }
        }
    }

    public AssignmentsScreen()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.add(createAddButton());
        buttonsPanel.add(createSaveButton());
        add(buttonsPanel);
        loadAssignments();
    }

    public JButton createAddButton()
    {
        JButton addButton = Styled.newStyledButton(RoyBattyConfig.getConfig().getAddLabel());
        addButton.addActionListener(e ->
        {
            addAction();
        });
        return addButton;
    }

    public JButton createSaveButton()
    {
        JButton saveButton = Styled.newStyledButton(RoyBattyConfig.getConfig().getSaveLabel());
        saveButton.addActionListener(e ->
        {
            saveAction();
        });
        saveButton.setSize(158, 25);
        return saveButton;
    }

    @SneakyThrows
    private void saveAction()
    {
        final List<AssignmentEntry> entries = new ArrayList<>();
        for (int i = 0; i < hotkeysMapped.size(); i++)
        {
            JButton hotkeyButton = hotkeysMapped.get(i);
            JComboBox macroField = macrosMapped.get(i);
            JCheckBox repeatField = repeastsMapped.get(i);
            JCheckBox activeField = activesMapped.get(i);
            if (new Hotkey(hotkeyButton.getText()).isValid())
            {
                hotkeyButton.setForeground(Color.BLACK);
                entries.add(new AssignmentEntry(new Hotkey(hotkeyButton.getText()), (String)macroField.getSelectedItem(),   repeatField.isSelected(), activeField.isSelected()));
            }
            else
            {
                hotkeyButton.setForeground(Color.RED);
                hotkeyButton.setText("???");
                return;
            }
        }

        final ObjectMapper om = new ObjectMapper();
        try (PrintWriter writer = new PrintWriter(Paths.get(RoyBattyConfig.getConfig().getAssignmentsPath()).toFile()))
        {
            writer.write(om.writeValueAsString(entries));
            RoyBatty.logInfo("Saved macro assignments to "+RoyBattyConfig.getConfig().getAssignmentsPath());
        }
        catch (FileNotFoundException e)
        {
            RoyBatty.logException("Failed saving assignments", e);
        }
    }

    private void addAction()
    {
        addAction(null, null, false, false);
    }


    private void addAction(Hotkey hotkey, String macroName, boolean repeat, boolean active)
    {
        JPanel assignmentPanel = new JPanel();
        assignmentPanel.setLayout(new BoxLayout(assignmentPanel, BoxLayout.X_AXIS));

        JCheckBox assignedCheckbox = new JCheckBox("active");


        JButton keyButton = Styled.newStyledButton(hotkey!=null?hotkey.toString():"*");
        hotkeysMapped.add(keyButton);
        CompletableFuture<Integer> future = new CompletableFuture<>();
        final HotkeyListener listener;

        listener = new HotkeyListener(activatedCodes, activeCodes, keyButton);
        listener.setFuture(future);

        keyButton.addItemListener(e -> {
            RoyBatty.getAssignments().unassign(new Hotkey(keyButton.getText()));
            assignedCheckbox.setSelected(false);
        });
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
        macrosMapped.add(fileField);
        if (macroName != null)
        {
            fileField.setSelectedItem(macroName);
        }
        assignmentPanel.add(fileField);
        fileField.addItemListener(e -> {
            RoyBatty.getAssignments().unassign(new Hotkey(keyButton.getText()));
            assignedCheckbox.setSelected(false);
        });

        JCheckBox repeatCheckbox = new JCheckBox("repeat");
        repeatCheckbox.addItemListener(e -> {
            RoyBatty.getAssignments().unassign(new Hotkey(keyButton.getText()));
            assignedCheckbox.setSelected(false);
        });
        repeastsMapped.add(repeatCheckbox);
        repeatCheckbox.setSelected(repeat);
        assignmentPanel.add(repeatCheckbox);

        activesMapped.add(assignedCheckbox);
        assignedCheckbox.setSelected(active);
        assignedCheckbox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED)
            {
                RoyBatty.getAssignments().assign(
                        new Hotkey(keyButton.getText()),
                        new MacroAssignment(Macro.loadMacroFile((String)fileField.getSelectedItem()), repeatCheckbox.isSelected())
                );

                listener.setActivatedCodes(activatedCodes);
            }
            else
            {
                RoyBatty.getAssignments().unassign(new Hotkey(keyButton.getText()));
            }
        });

        assignmentPanel.add(assignedCheckbox);

        JButton removeButton = Styled.newStyledButton("Remove");
        removeButton.addActionListener(e ->
        {
            if (assignedCheckbox.isSelected())
            {
                RoyBatty.getAssignments().unassign(new Hotkey(keyButton.getText()));
            }
            hotkeysMapped.remove(keyButton);
            macrosMapped.remove(fileField);
            repeastsMapped.remove(repeatCheckbox);
            activesMapped.remove(assignedCheckbox);
            remove(assignmentPanel);
            repaint();
            revalidate();
        });
        assignmentPanel.add(removeButton);

        add(assignmentPanel);
        repaint();
        revalidate();
    }
}
