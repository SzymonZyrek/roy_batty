package edu.szyrek.roybatty.screens;

import javax.swing.*;

public class MainScreen extends JTabbedPane {
    public MainScreen()
    {
        final ImageIcon icon = new ImageIcon("test.png");

        final RecorderScreen recorderScreen = new RecorderScreen();
        addTab("Record", icon, recorderScreen,
                "Record and play macros");
        final AssignmentsScreen assignmentsScreen = new AssignmentsScreen();
        addTab("Hotkeys", icon, assignmentsScreen,
                "Assign macros to hotkeys");
    }
}
