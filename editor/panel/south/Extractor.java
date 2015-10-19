package editor.panel.south;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;
import editor.VideoEditor;
import function.NameInput;
import function.process.videoProcess;
import main.VideoEditorMain;
import player.VideoPlayer;

public class Extractor extends JPanel {
	/**
	 * Extractor is able to extract a part of video or audio from original file
	 * at specific time point.
	 */
	private static final long serialVersionUID = -458220912464450896L;
	private JFormattedTextField txtstart, txtlength;
	private JButton video, audio, current, fullLength;
	private VideoPlayer videoPlayer;
	private VideoEditor videoEditor;
	private JLabel start;
	private JLabel length;

	public Extractor() throws ParseException {
		// Initialize the components in the panel
		start = new JLabel(
				"Enter the start time            (in format hh:mm:ss) : ");
		length = new JLabel(
				"Enter the length to keep     (in format hh:mm:ss) : ");

		// set up format
		MaskFormatter formatter = new MaskFormatter("##:##:##");
		formatter.setPlaceholderCharacter('0');
		txtstart = new JFormattedTextField(formatter);
		txtlength = new JFormattedTextField(formatter);
		txtstart.setPreferredSize(new Dimension(100, 30));
		txtlength.setPreferredSize(new Dimension(100, 30));
		video = new JButton("Extract Video");
		audio = new JButton("Extract Audio");
		fullLength = new JButton("Full Video Length");
		current = new JButton("Set Current Time");

		JPanel startPanel = new JPanel();
		startPanel.setPreferredSize(new Dimension(800, 50));
		JPanel lengthPanel = new JPanel();
		lengthPanel.setPreferredSize(new Dimension(800, 50));
		JPanel butPanel = new JPanel();
		setLayout(new BorderLayout());
		startPanel.add(start);
		startPanel.add(txtstart);
		startPanel.add(current);
		lengthPanel.add(length);
		lengthPanel.add(txtlength);
		lengthPanel.add(fullLength);

		butPanel.add(video);
		butPanel.add(audio);
		// add components to panel
		add(startPanel, BorderLayout.NORTH);
		add(lengthPanel, BorderLayout.CENTER);
		add(butPanel, BorderLayout.SOUTH);
		disableExtractor();

		// listener below
		// extract video only listener, only save a part of video
		video.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				upDateVideoEditor();
				if (txtlength.getText().equals("00:00:00")) {
					// check total length
					JOptionPane.showMessageDialog(null,
							"The length can not be shorter than 1 second.");
				} else if (!isTimeAvaliable(videoPlayer.getTotalTime(),
						txtstart.getText(), txtlength.getText())) {
					JOptionPane.showMessageDialog(null,
							"The length can not be longer than video.");
				} else {
					// call video process to generate new video
					String name = NameInput.name();
					// get name
					if (name != null && !name.equals("")) {
						name = name + ".mp4";
						videoProcess extractVideo = new videoProcess(
								"ffmpeg -y -i " + videoEditor.getSelectedFile()
										+ " -ss " + txtstart.getText() + " -t "
										+ txtlength.getText() + " -an "
										+ videoEditor.getSavePath() + "/"
										+ name, videoEditor.getSavePath() + "/"
										+ name, txtlength.getText());
						extractVideo.execute();
					}
				}
			}
		});

		// audio only listener, only save part of audio
		audio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				upDateVideoEditor();
				if (txtlength.getText().equals("00:00:00")) {
					// check audio length
					JOptionPane.showMessageDialog(null,
							"The length can not be shorter than 1 second.");
				} else if (!isTimeAvaliable(videoPlayer.getTotalTime(),
						txtstart.getText(), txtlength.getText())) {
					JOptionPane.showMessageDialog(null,
							"The length can not be longer than video.");
				} else {
					// get name
					// call video process to generate new video
					String name = NameInput.name();
					if (name != null && !name.equals("")) {
						name = name + ".mp3";
						videoProcess extractVideo = new videoProcess(
								"ffmpeg -y -i " + videoEditor.getSelectedFile()
										+ " -ss " + txtstart.getText() + " -t "
										+ txtlength.getText() + " -map 0:1 "
										+ videoEditor.getSavePath() + "/"
										+ name, videoEditor.getSavePath() + "/"
										+ name, txtlength.getText());
						extractVideo.execute();
					}
				}
			}
		});

		// full length listener, set the length selected to full video length
		fullLength.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				upDateVideoEditor();
				txtlength.setText(videoPlayer.getTotalTime());
			}
		});

		// current time listener, set start time to current time
		current.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				upDateVideoEditor();
				txtstart.setText(videoPlayer.getPlayTime());
			}
		});

	}

	public void enableExtractor(File videoFile) {
		// enable the buttons, when there is video in editor
		video.setEnabled(true);
		audio.setEnabled(true);
		current.setEnabled(true);
		fullLength.setEnabled(true);
	}

	public void disableExtractor() {
		// disable buttons when there is no video
		video.setEnabled(false);
		audio.setEnabled(false);
		current.setEnabled(false);
		fullLength.setEnabled(false);
	}

	private void upDateVideoEditor() {
		// update the video editor reference
		videoEditor = VideoEditorMain.getVideoEditor();
		videoPlayer = VideoEditorMain.getVideoPlayer();
	}

	private boolean isTimeAvaliable(String total, String select, String length) {
		int totalTime = function.Time.runtimeToSecond(total);
		int selectTime = function.Time.runtimeToSecond(select);
		int lengthTime = function.Time.runtimeToSecond(length);
		if (totalTime < (selectTime + lengthTime)) {
			return false;
		} else {
			return true;
		}

	}

	public String getTotalTime() {
		return txtlength.getText();
	}
}
