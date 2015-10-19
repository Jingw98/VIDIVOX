package editor;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Help extends JFrame {
	/**
	 * Generate a pop up frame to help the user, when the 'Help->Tips' is
	 * clicked. Some basic description is shown in the help frame.
	 * 
	 */
	private static final long serialVersionUID = -6387736493965688400L;

	public Help() {
		// Set up the basic gui for frame
		setBounds(50, 50, 350, 250);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// The main body of help frame
		JLabel text = new JLabel();
		// the information that shown on the frame
		text.setText("<Html>  Help : <p><p>*  The VIDIVOX has six editing functions on the bottom of the main screen."
				+ "<p><p>*  The detaild informarion of videos is on the right hand side of the editor."
				+ "<p><p>*  If no buttons shown in the player, please check the res folder in the working directory.");
		// set up size and font of text field
		text.setPreferredSize(new Dimension(300, 200));
		text.setFont(new Font("Tahoma", Font.PLAIN, 17));

		add(text);
		// show frame
		setVisible(true);
	}
}
