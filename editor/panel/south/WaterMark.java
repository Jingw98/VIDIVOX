package editor.panel.south;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import editor.VideoEditor;
import function.FileOperation;
import function.NameInput;
import function.gui.WaterMarkPic;
import function.process.videoProcess;
import main.VideoEditorMain;

public class WaterMark extends JPanel implements ItemListener {
	/**
	 * WaterMark is able to produce new video with selected image file as water
	 * mark. The size and the position of the water mark can be decided by
	 * users.
	 */
	private static final long serialVersionUID = -7991166287793703070L;
	private DefaultComboBoxModel<String> model1;
	private DefaultComboBoxModel<String> model2;
	private JComboBox<String> box1;
	private JComboBox<String> box2;
	private JRadioButton topLeft;
	private JRadioButton topRight;
	private JRadioButton bottomLeft;
	private JRadioButton bottomRight;
	private JFileChooser chooser;
	private VideoEditor videoEditor;
	private ButtonGroup bg;
	private JButton save, browse;
	private JTextField path;
	private String overlay = "10:10";
	private Timer timer;
	private JPanel pic;
	final String[] labels = { "5", "10", "25", "50", "100", "150", "200",
			"250", "300", "350", "400" };

	public WaterMark() {
		// set up the panel layout
		BorderLayout b = new BorderLayout();
		b.setHgap(0);
		setLayout(b);

		// Initialize the components in the panel
		browse = new JButton("Browse");
		path = new JTextField();
		path.setEditable(false);
		path.setPreferredSize(new Dimension(450, 30));
		pic = new JPanel();
		pic.setPreferredSize(new Dimension(250, 150));
		pic.setMaximumSize(new Dimension(250, 150));
		save = new JButton("Generate Video");
		JLabel dimension = new JLabel(
				"Water Mark Dimension (width X height):     ");
		JLabel x = new JLabel("x");
		model1 = new DefaultComboBoxModel<String>(labels);
		model2 = new DefaultComboBoxModel<String>(labels);

		// dimension boxes
		box1 = new JComboBox<String>(model1);
		box1.setPreferredSize(new Dimension(70, 30));
		box2 = new JComboBox<String>(model2);
		box2.setPreferredSize(new Dimension(70, 30));

		topLeft = new JRadioButton("Top Left");
		topLeft.setSelected(true);
		topRight = new JRadioButton("Top Right");
		bottomLeft = new JRadioButton("Bottom Left");
		bottomRight = new JRadioButton("Bottom Right");
		// position button group
		bg = new ButtonGroup();
		bg.add(topLeft);
		bg.add(topRight);
		bg.add(bottomLeft);
		bg.add(bottomRight);

		JPanel image = new JPanel();
		image.add(path);
		image.add(browse);

		JPanel posPanel = new JPanel();
		posPanel.setLayout(new GridLayout(2, 2));
		posPanel.add(topLeft);
		posPanel.add(topRight);
		posPanel.add(bottomLeft);
		posPanel.add(bottomRight);

		JPanel size = new JPanel();
		size.add(dimension);
		size.add(box1);
		size.add(x);
		size.add(box2);
		size.setPreferredSize(new Dimension(700, 40));

		JPanel button = new JPanel();
		button.add(size);
		button.add(save);
		button.setPreferredSize(new Dimension(750, 40));

		// add components to panel
		add(posPanel);
		add(image, BorderLayout.NORTH);
		add(button, BorderLayout.SOUTH);
		add(pic, BorderLayout.WEST);
		disableWaterMark();

		// listeners below
		// position listeners
		topLeft.addItemListener((ItemListener) this);
		topRight.addItemListener((ItemListener) this);
		bottomLeft.addItemListener((ItemListener) this);
		bottomRight.addItemListener((ItemListener) this);

		// select image file listener, choose the image file and show it in
		// panel
		browse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// choose file
				chooser = new JFileChooser();
				File defaultPath = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(defaultPath);
				File image = FileOperation.chooseFile(chooser,
						new FileNameExtensionFilter("Image File", "png", "bmp",
								"jpeg", "jpg"), JFileChooser.FILES_ONLY,
						"Image File");
				if (image != null) {
					path.setText(image.getAbsolutePath());
					try {
						// draw image to west panel
						if (timer != null) {
							timer.cancel();
						}
						timer = new Timer();
						timer.schedule(new WaterMarkPic(image, 150, 100, pic),
								10, 100);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		// save new video listener
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				upDateVideoEditor();
				// check image file
				if (path.getText().equals("")) {
					JOptionPane.showMessageDialog(null,
							"Image file can not be empty!", null,
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					logoProcess();
				}
			}
		});
	}

	// position listener, set position of water mark
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == topLeft) {
			overlay = "10:10";
		}
		if (e.getSource() == topRight) {
			overlay = "(main_w-overlay_w)-10:10";
		}
		if (e.getSource() == bottomLeft) {
			overlay = "10:(main_h-overlay_h)-10";
		}
		if (e.getSource() == bottomRight) {
			overlay = "(main_w-overlay_w)-10:(main_h-overlay_h)-10";
		}
	}

	public void disableWaterMark() {
		save.setEnabled(false);
	}

	public void enableWaterMark() {
		save.setEnabled(true);
	}

	private void logoProcess() {
		// call video process to generate new video
		if (NameInput.checkInputName(path.getText())) {
			// get name
			String name = NameInput.name();
			if (name != null && name != "") {
				name = videoEditor.getSavePath() + "/" + name + ".mp4";
				videoProcess process = new videoProcess(
						"ffmpeg -i "
								+ videoEditor.getSelectedFile()
								+ " -i "
								+ path.getText()
								+ " -filter_complex \"[1:v] scale="
								+ getScale()
								+ " [logo1]; [0:v][logo1] overlay="
								+ overlay
								+ "\" -y -b 1600k -c:v libx264 -profile high -level 4.1  "
								+ name, name, null);
				
				process.execute();
			}
		}
	}

	// getter
	private String getScale() {
		String scale = (String) box1.getSelectedItem() + ":"
				+ box2.getSelectedItem();
		return scale;
	}

	private void upDateVideoEditor() {
		// update the video editor reference
		videoEditor = VideoEditorMain.getVideoEditor();
		VideoEditorMain.getVideoPlayer();
	}
}
