package ui.combobox;

import java.awt.Color;
import java.awt.Component;
import java.util.function.Supplier;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import utils.Colors;

public class CustomComboRenderer extends DefaultListCellRenderer {
    public static final Color background = Color.WHITE;
    private static final Color defaultBackground = Color.white;
    private static final Color defaultForeground = Colors.secondary;
    private Supplier<String> highlightTextSupplier;

    public CustomComboRenderer(Supplier<String> highlightTextSupplier) {
        this.highlightTextSupplier = highlightTextSupplier;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        String text = (String) value;
        text = HtmlHighlighter.highlightText(text, highlightTextSupplier.get());
        this.setText(text);
        if (!isSelected) {
            this.setBackground(index % 2 == 0 ? background : defaultBackground);
        }
        this.setForeground(defaultForeground);
        return this;
    }
    public static String getText(String text) {
    	return text;
    }

}