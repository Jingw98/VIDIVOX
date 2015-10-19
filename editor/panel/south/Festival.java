package editor.panel.south;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import function.CallBash;
import function.FileOperation;

public class Festival extends JPanel {
	/**
	 * Festival text-to-speech function, using festival open source software.
	 * The out put mp3 file can be save to specific folder.
	 */
	private static final long serialVersionUID = 8312530327715607306L;
	private JTextField path;
	private JButton save;
	private JButton festival;
	private JTextField textField;

	public Festival(JTextField path) throws IOException {
		this.path = path;
		// initialize file chooser
		final JFileChooser choose = new JFileChooser();
		File defaultPath = new File(path.getText() + "/audio.mp3");
		choose.setSelectedFile(defaultPath);

		// initialize the component in the panel
		festival = new JButton("Listen");
		festival.setPreferredSize(new Dimension(100, 35));
		save = new JButton("Save");
		save.setPreferredSize(new Dimension(100, 35));
		// button panel
		BorderLayout b = new BorderLayout();
		b.setHgap(120);
		JPanel butpanel = new JPanel();
		butpanel.setLayout(b);
		butpanel.add(festival, BorderLayout.WEST);
		butpanel.add(save, BorderLayout.EAST);

		// text input panel
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(600, 35));
		textField.setText("Please Enter text here for festival speech...");
		final JButton empty = new JButton("Empty");
		JPanel text = new JPanel();
		text.setPreferredSize(new Dimension(800, 70));
		text.add(textField);
		text.add(empty);

		// add components to panel
		add(text);
		add(butpanel);

		festival.setEnabled(false);
		save.setEnabled(false);
		// listener below
		// test field listener, clean the field when is is focused
		textField.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textField.setText("");
				festival.setEnabled(true);
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		// festival listener, speak the text input
		festival.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = textField.getText();
				try {
					speak(input);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				textField.selectAll();
			}
		});
		// after one time input, select all text is easier to change the input
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = textField.getText();
				try {
					speak(input);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				textField.selectAll();
			}
		});

		// save the mp3 fileF
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveProcess();
			}
		});

		// empty the text field
		empty.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				festival.setEnabled(false);
				save.setEnabled(false);
			}
		});

	}

	// speak process in the festival
	private void speak(String input) throws IOException {
		try {
			// check input length
			int word = input.length();
			if (word <= 40 && word > 0) {
				// speak the text
				CallBash.callBashVoid("echo \"" + input
						+ "\" |  festival --tts");
				CallBash.callBashVoid("rm -rfv ./.soundFile/*");
				// save to a hidden folder
				CallBash.callBashVoid("echo \""
						+ input
						+ "\" |  text2wave -o ./.soundFile/audio.wav; ffmpeg -i ./.soundFile/audio.wav -f mp3 ./.soundFile/audio.mp3");
				save.setEnabled(true);
			} else {
				if (word == 0) {
					JOptionPane.showMessageDialog(null,
							"Input can not be empty!", null,
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null,
							"Input can not be over 40 words!", null,
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// save the mp3 that produced by festival
	private void saveProcess() {
		// get name
		String name = (String) JOptionPane.showInputDialog(null,
				"MP3 file name：\n", "Save", JOptionPane.PLAIN_MESSAGE, null,
				null, "audio");

		File now = new File(getPath() + "/" + name + ".mp3");
		// check name
		if (name == null) {
		} else if (name.trim()
				.replaceAll("[a-z]*[A-Z]*[0-9]*\\d*-*_*\\s* *", "").length() != 0
				|| name.trim().equals("")) {
			// special symbol
			JOptionPane.showMessageDialog(null,
					"Incorrect file name! Please try again", null,
					JOptionPane.INFORMATION_MESSAGE);
			name = "";
			saveProcess();
		} else if (now.exists()) {
			// file name exist
			int i = JOptionPane.showConfirmDialog(null,
					"Exist MP3 file, over write it?", null,
					JOptionPane.YES_NO_OPTION);
			if (i == JOptionPane.OK_OPTION) {
				FileOperation.saveMP3(getPath() + "/" + name + ".mp3");
				textField.setText("");
				festival.setEnabled(false);
				save.setEnabled(false);
			}
		} else {
			// save file
			FileOperation.saveMP3(getPath() + "/" + name + ".mp3");
			textField.setText("");
			festival.setEnabled(false);
			save.setEnabled(false);
		}
	}

	// getter
	private String getPath() {
		return path.getText();
	}
}
