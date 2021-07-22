package edu.szyrek.roybatty.screens;

import edu.szyrek.roybatty.RoyBatty;

import javax.swing.*;

public class AssignmentsScreen extends JPanel
{


    public AssignmentsScreen()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

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

    }
}
