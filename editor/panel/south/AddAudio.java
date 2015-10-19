package editor.panel.south;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;
import player.VideoPlayer;
import editor.VideoEditor;
import main.VideoEditorMain;
import function.FileOperation;
import function.NameInput;
import function.process.videoProcess;

public class AddAudio extends JPanel {

	/**
	 * AddAudio contains the adding audio to video function. The time of start
	 * point of audio can be decided by user.
	 */
	private static final long serialVersionUID = -1128931928816985103L;
	private JTextField mp3Path;
	private File mp3File;
	private JButton setCurrent;
	private JButton btnOverlay;
	private JButton btnOverlap;
	private File videoFile;
	private VideoEditor videoEditor;
	private VideoPlayer videoPlayer;
	private JFormattedTextField time;
	@SuppressWarnings("unused")
	private String videoPath;
	@SuppressWarnings("unused")
	private String audioPath;
	private String path;
	private String selectedTime;

	public AddAudio() throws IOException, ParseException {
		// set up the panel layout
		this.setPreferredSize(new Dimension(1000, 150));
		// set up file chooser
		final JFileChooser fileSelector = new JFileChooser();
		File defaultPath = new File(System.getProperty("user.dir"));
		fileSelector.setCurrentDirectory(defaultPath);

		// set up start time components
		JPanel startTime = new JPanel();
		BorderLayout b = new BorderLayout();
		b.setHgap(165);
		startTime.setLayout(b);
		JLabel start = new JLabel("Start Time :");

		// set up format for video time
		MaskFormatter formatter = new MaskFormatter("##:##:##");
		formatter.setPlaceholderCharacter('0');
		time = new JFormattedTextField(formatter);
		time.setPreferredSize(new Dimension(80, 30));

		setCurrent = new JButton("Current Time");
		setCurrent.setPreferredSize(new Dimension(125, 30));
		startTime.add(start, BorderLayout.WEST);
		startTime.add(time);
		startTime.add(setCurrent, BorderLayout.EAST);

		// set up mp3 file chooser components
		JButton change = new JButton("Change MP3");
		change.setPreferredSize(new Dimension(125, 30));
		JLabel mpath = new JLabel("MP3 File Path :        ");
		mp3Path = new JTextField();
		mp3Path.setPreferredSize(new Dimension(345, 30));
		mp3Path.setEditable(false);
		JPanel mp3Panel = new JPanel();
		mp3Panel.add(mpath);
		mp3Panel.add(mp3Path);
		mp3Panel.add(change);
		
		// set up buttons in panel
		BorderLayout C = new BorderLayout();
		C.setHgap(120);
		JPanel butPanel = new JPanel();
		butPanel.setLayout(C);
		btnOverlay = new JButton("Overlay");
		btnOverlap = new JButton("Overlap");
		butPanel.add(btnOverlay, BorderLayout.WEST);
		butPanel.add(btnOverlap, BorderLayout.EAST);
		add(startTime, BorderLayout.NORTH);
		add(mp3Panel, BorderLayout.CENTER);
		add(butPanel, BorderLayout.SOUTH);
		// disable the button when there is no video
		disableAddAudio();

		setCurrent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				time.setValue(videoPlayer.getPlayTime());
			}
		});

		// listeners below
		// change mp3 file listener
		change.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// choose mp3 file
				mp3File = FileOperation.chooseFile(fileSelector,
						new FileNameExtensionFilter("MP3", "mp3"),
						JFileChooser.FILES_ONLY, "audio file");
				if (mp3File != null) {
					mp3Path.setText(mp3File.getAbsolutePath());
				}
			}
		});

		// overlay listener, overlay the audio to video
		btnOverlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mp3Path.getText().length() == 0) {
					// check mp3 file
					JOptionPane.showMessageDialog(null,
							"File Path Can Not Be Empty!", null,
							JOptionPane.WARNING_MESSAGE);
				} else {
					if (videoFile.exists()) {
						// check video file
						videoPath = videoFile.getAbsolutePath();
						audioPath = mp3File.getAbsolutePath();
						// get name from user
						String name = NameInput.name();
						if (name != null && !name.equals("")) {
							name = name + ".mp4";
							path = videoEditor.getSavePath() + "/" + name;
							selectedTime = time.getText();
							// call video process to generate new video
							videoProcess vp = new videoProcess(
									"ffmpeg -y -i "
											+ videoFile
											+ " -itsoffset "
											+ selectedTime
											+ " -i  "
											+ mp3File
											+ " -map 0:0 -map 1:0 -c:v copy  -async 1  "
											+ path, path, null);
							if (mp3File.exists()) {
								int i = JOptionPane
										.showConfirmDialog(
												null,
												"Audio length and video length might be different, Are you sure to merge?",
												null, JOptionPane.YES_NO_OPTION);
								if (i == JOptionPane.OK_OPTION) {
									vp.execute();
								}
							}
						}
					}
				}
			}
		});

		// overlap listener, overlap the audio to video
		btnOverlap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// check mp3 file
				if (mp3Path.getText().length() == 0) {
					JOptionPane.showMessageDialog(null,
							"File Path Can Not Be Empty!", null,
							JOptionPane.WARNING_MESSAGE);

				} else {
					if (videoFile.exists()) {
						// check video file
						videoPath = videoFile.getAbsolutePath();
						audioPath = mp3File.getAbsolutePath();
						String name = NameInput.name();
						if (name != null && !name.equals("")) {
							name = name + ".mp4";
							path = videoEditor.getSavePath() + "/" + name;
							selectedTime = time.getText();
							// call video process to generate new video
							videoProcess vp = new videoProcess(
									"ffmpeg -y -i "
											+ videoFile
											+ " -itsoffset "
											+ selectedTime
											+ " -i  "
											+ mp3File
											+ " -map 0:0 -map 1:0 -c:v copy  -async 1 -filter_complex amix=inputs=2 "
											+ path, path, null);
							vp.execute();
						}
					}
				}
			}
		});
	}

	public void enableAddAudio(File video) {
		//enable the buttons, when there is video in editor
		upDateVideoEditor();
		time.setEnabled(true);
		setCurrent.setEnabled(true);
		btnOverlay.setEnabled(true);
		btnOverlap.setEnabled(true);
		videoFile = video;

	}

	public void disableAddAudio() {
		//disable buttons when there is no video
		time.setEnabled(false);
		setCurrent.setEnabled(false);
		btnOverlay.setEnabled(false);
		btnOverlap.setEnabled(false);
	}

	private void upDateVideoEditor() {
		//update the video editor reference
		videoEditor = VideoEditorMain.getVideoEditor();
		videoPlayer = VideoEditorMain.getVideoPlayer();
	}

}
