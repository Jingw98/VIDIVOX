package function.gui;

import java.awt.Component;
import java.util.ArrayList;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

@SuppressWarnings("rawtypes")
public class MyCellRenderer extends JLabel implements ListCellRenderer {
	/**
	 * Helper class
	 * Play list cell render, set icon and file name to JList.
	 */
	private static final long serialVersionUID = 2355669569607288869L;
	ArrayList<Icon> icons;

	public MyCellRenderer() {
	};

	public MyCellRenderer(ArrayList<Icon> icons) {
		this.icons = icons;
	}

	//override the getListCellRendererComponent method
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		//display file name in JList
		String s = ((File) value).getName();
		setText(s);
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		//display icon in JList
		setIcon(icons.get(index));
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		setOpaque(true);
		return this;
	}

}
