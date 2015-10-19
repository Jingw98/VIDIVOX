package editor.panel.south;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import editor.VideoEditor;
import function.Time;
import function.process.ShotProcess;
import main.VideoEditorMain;
import player.VideoPlayer;

public class Gif extends JPanel {
	/**
	 * Gif could produce the gif movie from the videoFile. And show it to user
	 * after finished.
	 */
	private static final long serialVersionUID = -1463749805441041311L;
	private VideoPlayer videoPlayer;
	private Timer timer;
	private JButton timeButton;
	private VideoEditor videoEditor;
	private JLabel startLabel;
	private JLabel totalLabel;
	private JLabel startTimeLabel;
	private JLabel totalTimeLabel;

	public Gif() {
		// Initialize the component in the panel
		timeButton = new JButton();
		timeButton.setText("Start Here");
		startLabel = new JLabel("00:00:00");
		totalLabel = new JLabel("   0 s  ");
		startTimeLabel = new JLabel("Start Time :");
		totalTimeLabel = new JLabel("Total Time : ");
		startTimeLabel.setPreferredSize(new Dimension(200, 30));
		totalTimeLabel.setPreferredSize(new Dimension(217, 30));

		JPanel start = new JPanel();
		JPanel total = new JPanel();
		start.add(startTimeLabel);
		start.add(startLabel);
		total.add(totalTimeLabel);
		total.add(totalLabel);

		start.setPreferredSize(new Dimension(1000, 30));
		total.setPreferredSize(new Dimension(1000, 30));
		// add component to panel
		add(timeButton);
		add(start);
		add(total);
		disableGif();
		// timer to calculate the total time of gif
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				upDateVideoEditor();
				int now = Character.getNumericValue(totalLabel.getText()
						.charAt(3));
				now = now + 1;
				if (now > 7) {
					// time can not be over 7 seconds
					int n = JOptionPane.showConfirmDialog(null,
							"Gif can not be longer than 7 seconds."
									+ " Still save this 7 seconds gif ?",
							"Gif", JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {
						// call shot process to produce the gif
						ShotProcess gif = new ShotProcess("ffmpeg -i "
								+ videoEditor.getSelectedFile() + " -ss "
								+ startLabel.getText()
								+ " -s 320x180 -t 00:00:07 -r 15 "
								+ videoEditor.getSavePath() + "/"
								+ Time.getCurrentTime() + ".gif", videoEditor
								.getSavePath()
								+ "/"
								+ Time.getCurrentTime()
								+ ".gif", true);
						gif.execute();

					}
					// reset components
					timeButton.setText("Start Here");
					timer.stop();
					totalLabel.setText("   0 s  ");
				} else {

					totalLabel.setText("   " + now + " s  ");
				}
			}
		});

		//time button listener, start the timer for gif
		timeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				upDateVideoEditor();
				//start timer
				if (timeButton.getText().equals("Start Here")) {
					startLabel.setText(videoPlayer.getPlayTime());
					timer.start();
					timeButton.setText("Stop Here");
				} else if (timeButton.getText().equals("Stop Here")) {
					//stop timer
					timer.stop();
					if (Character.getNumericValue(totalLabel.getText()
							.charAt(3)) < 1) {
						//check gif total time
						JOptionPane.showMessageDialog(null,
								"Gif can not be shorter than 1 second.");

					} else {
						//call shot process to produce gif
						ShotProcess gif = new ShotProcess("ffmpeg -i "
								+ videoEditor.getSelectedFile() + " -ss "
								+ startLabel.getText()
								+ " -s 320x180 -t 00:00:"
								+ totalLabel.getText().charAt(3) + " -r 15 "
								+ videoEditor.getSavePath() + "/"
								+ Time.getCurrentTime() + ".gif", videoEditor
								.getSavePath()
								+ "/"
								+ Time.getCurrentTime()
								+ ".gif", true);
						gif.execute();
					}
					//reset
					totalLabel.setText("   0 s  ");
					timeButton.setText("Start Here");
					timer.stop();
				}

			}
		});

	}

	public void enableGif() {
		//enable the buttons, when there is video in editor
		timeButton.setEnabled(true);
	}

	public void disableGif() {
		//disable buttons when there is no video
		timeButton.setEnabled(false);
	}

	private void upDateVideoEditor() {
		//update the video editor reference
		videoEditor = VideoEditorMain.getVideoEditor();
		videoPlayer = VideoEditorMain.getVideoPlayer();
	}

}
