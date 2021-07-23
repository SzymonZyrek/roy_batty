package edu.szyrek.roybatty.lookandfeel;

import edu.szyrek.roybatty.RoyBattyConfig;

import javax.swing.*;
import java.awt.*;

public class Styled
{
    public static JComponent getVerticalSeparator()
    {
        return new JSeparator(SwingConstants.VERTICAL);
    }

    public static JComponent getHorizontalSeparator()
    {
        return new JSeparator(SwingConstants.HORIZONTAL);
    }

    public static JButton newStyledButton(final String label)
    {
        JButton button = new JButton(label);
        button.setSize(121, 25);
        return button;
    }

    public static JPanel newLabledField(final String label, final JComponent labeledField)
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        JLabel labelField = new JLabel(label);
        labelField.setMaximumSize(new Dimension(200, 25));
        labelField.setSize(new Dimension(200, 25));
        labeledField.setMaximumSize(new Dimension(200, 25));
        labeledField.setSize(new Dimension(200, 25));
        mainPanel.add(labelField);
        mainPanel.add(labeledField);
        return mainPanel;
    }
}
