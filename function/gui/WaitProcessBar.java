package function.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class WaitProcessBar extends JFrame {

	private JPanel contentPane;
	protected JProgressBar progressBar; 

	/**
	 * Create the frame for waiting prograss bar.
	 */
	public WaitProcessBar() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(350, 300, 500, 120);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//set up label
		JLabel lblPleaseWaitWhile = new JLabel("Please wait while your new file is producing...");
		lblPleaseWaitWhile.setBounds(10, 24, 365, 19);
		lblPleaseWaitWhile.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		//set up prograss bar
		progressBar = new JProgressBar();
		progressBar.setBounds(10, 43, 464, 31);
		contentPane.add(lblPleaseWaitWhile);
		contentPane.add(progressBar);
		setVisible(true);
	}
	
	// Allow the process to update the progress bar
	public void updateProgress(int i) {
		progressBar.setValue(i);
	}
}
