package function.gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Inco extends JFrame {
	/**
	 * A pop up frame, shows the Screen shot file.
	 */
	
	@SuppressWarnings("deprecation")
	public Inco(String fileName) {
		//set title
		super("Preview");
		//get file
		ImageIcon ic = new ImageIcon(fileName);
		setSize(ic.getIconWidth() + 100, ic.getIconHeight() + 100);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//set icons
		JPanel panel = new JPanel();
		JLabel label = new JLabel();
		label.setIcon(ic);
		panel.add(label);
		setContentPane(panel);
		show();

	}
}
