package editor.panel.south;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import function.process.videoProcess;

public class CovertVideoFormat extends JPanel {
	/**
	 * This panel provide the function of convert the video file format. Four
	 * format available, mp4, wmv, avi and flv.
	 */
	private static final long serialVersionUID = -5389093218387621553L;
	final String labels[] = { "mp4", "wmv", "avi", "flv" };
	static File videoFile;
	private DefaultComboBoxModel<String> model;
	private JComboBox<String> box;
	private JLabel current;
	private JButton save;
	private JTextField path;

	public CovertVideoFormat(JTextField path) {
		this.path = path;

		// initialize the component in panels
		JPanel panelbox = new JPanel();
		panelbox.setPreferredSize(new Dimension(1000, 40));
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1000, 30));
		JLabel label1 = new JLabel("Current Format : ");
		label1.setPreferredSize(new Dimension(250, 20));

		current = new JLabel("No Video");
		current.setPreferredSize(new Dimension(200, 20));
		JLabel label = new JLabel("Please Choose The Format :        ");

		model = new DefaultComboBoxModel<String>(labels);
		box = new JComboBox<String>(model);
		box.setEditable(true);
		box.setPreferredSize(new Dimension(300, 30));
		box.setEditable(false);
		save = new JButton("Save");
		// add components to panel
		panel.add(label1);
		panel.add(current);
		panelbox.add(label);
		panelbox.add(box);
		add(panel);
		add(panelbox);
		add(save, BorderLayout.SOUTH);
		disableFormate();

		// save listener, save the new format file.
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// shoe information of same format
				if (current.getText().equals(box.getSelectedItem())) {
					JOptionPane.showMessageDialog(null, "Same File Format!");
				} else {
					// call video process to generate new video
					String str = videoFile.getName();
					String name = str.substring(0, str.indexOf("."));
					videoProcess vp = new videoProcess(
							"ffmpeg -i "
									+ videoFile
									+ " -ab 128 -acodec libmp3lame -ac 1 -ar 22050 -r 29.97 -qscale 6 -y "
									+ getPath() + "/" + name + "."
									+ box.getSelectedItem(), getPath() + "/"
									+ name + "." + box.getSelectedItem(), null);

					vp.execute();
				}

			}
		});

	}

	public void enableConvertor(File videoFile) {
		// enable the panel with video
		String str = videoFile.getName();
		current.setText(str.substring(str.indexOf(".") + 1));
		CovertVideoFormat.videoFile = videoFile;
		save.setEnabled(true);

	}

	public void disableFormate() {
		// disable the panel without the video
		current.setText("No Video");
		save.setEnabled(false);
	}

	// getter
	private String getPath() {
		return path.getText();
	}

}
