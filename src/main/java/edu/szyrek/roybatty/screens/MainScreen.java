package edu.szyrek.roybatty.screens;

import edu.szyrek.roybatty.RoyBatty;

import javax.swing.*;

public class MainScreen extends JPanel {
    private JLabel statusBar;

    private JLabel createStatusBar()
    {
        JLabel sb = new JLabel("");
        sb.setSize(300, 16);
        return sb;
    }

    private void createGUI()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        final ImageIcon icon = new ImageIcon("test.png");

        JTabbedPane tabbedPane = new JTabbedPane();
        final RecorderScreen recorderScreen = new RecorderScreen();
        tabbedPane.addTab("Record", icon, recorderScreen,
                "Record and play macros");
        final AssignmentsScreen assignmentsScreen = new AssignmentsScreen();
        tabbedPane.addTab("Hotkeys", icon, assignmentsScreen,
                "Assign macros to hotkeys");
        final ConfigScreen configScreen = new ConfigScreen();
        tabbedPane.addTab("Config", icon, configScreen,
                "Tweak configuration");
        add(tabbedPane);

        statusBar = createStatusBar();
        add(statusBar);
    }

    public MainScreen()
    {
        createGUI();
        RoyBatty.setStatusBar(statusBar);
    }
}
