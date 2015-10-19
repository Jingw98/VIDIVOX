package player;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import main.VideoEditorMain;
import editor.VideoEditor;
import function.Time;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class VideoPlayer extends JPanel {
	/**
	 * Video player in the video editor. It has all basic player function and
	 * screenshot function as well.
	 */

	private static final long serialVersionUID = 4073982675681560732L;
	private JPanel functionPanel;
	private JPanel volumePanel;
	private JSlider move;
	private JSlider voice;
	private JButton puse;
	private JButton forward;
	private JButton backward;
	private JButton stop;
	private JButton mute;
	private EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private JLabel videoTime;
	private JPanel southPanel;
	private Timer videoTimer;
	private JButton shot;
	private String playTime = "00:00:00";
	private String totalTime = "00:00:00";
	private boolean sliderSkip = true;
	private Timer forwardTimer;
	private Timer backwardTimer;
	int backwardSpeed = -500;
	int forwardSpeed = 500;
	private VideoEditor videoEditor;

	public VideoPlayer() {
		setLayout(new BorderLayout());
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		add(mediaPlayerComponent, BorderLayout.CENTER);

		// South function panel
		southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		// function button panel in south
		functionPanel = new JPanel();
		setUpFunctionPanel();

		// time label
		videoTime = new JLabel();
		setUpVideoTime();
		functionPanel.add(videoTime);
		southPanel.add(functionPanel, BorderLayout.WEST);

		// move slider in south
		move = new moveSlider(sliderSkip, mediaPlayerComponent, getPlayTime());
		southPanel.add(move, BorderLayout.NORTH);

		// volume
		volumePanel = new JPanel();
		setUpVolumePanel();
		southPanel.add(volumePanel, BorderLayout.EAST);
		add(southPanel, BorderLayout.SOUTH);

		// skip timer
		forwardTimer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayerComponent.getMediaPlayer().skip(forwardSpeed);
			}
		});
		backwardTimer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayerComponent.getMediaPlayer().skip(backwardSpeed);
			}
		});

		setVisible(true);

	}

	// Volume Panel
	private void setUpVolumePanel() {
		// Mute Button
		mute = new JButton();
		mute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!mediaPlayerComponent.getMediaPlayer().isMute()) {
					mediaPlayerComponent.getMediaPlayer().mute(true);
					setButton(mute, "14.png");
				} else {
					mediaPlayerComponent.getMediaPlayer().mute(false);
					setButton(mute, "6.png");
				}
			}
		});

		mute.setEnabled(false);
		setButton(mute, "6.png");

		// Volume control
		voice = new JSlider(0, 100, 0);
		voice.setOpaque(false);
		voice.setPreferredSize(new Dimension(120, 20));
		voice.setValue(50);
		//change listener in volume
		voice.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				mediaPlayerComponent.getMediaPlayer().setVolume(
						voice.getValue() * 2);
				if (mediaPlayerComponent.getMediaPlayer().getVolume() == 0) {
					setButton(mute, "14.png");
				} else if (mediaPlayerComponent.getMediaPlayer().getVolume() != 0) {
					setButton(mute, "6.png");
				}
			}
		});
		
		//screen shot button
		shot = new JButton();
		setButton(shot, "3.png");
		shot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new ScreenShot();
				} catch (IOException a) {

					a.printStackTrace();
				}
			}
		});
		shot.setPreferredSize(new Dimension(40, 30));
		shot.setEnabled(false);
		volumePanel.add(shot);
		volumePanel.add(mute);
		volumePanel.add(voice);
	}

	//Set up the play and total time label
	private void setUpVideoTime() {
		videoTime.setText(getPlayTime() + " / " + getTotalTime());
		//Refresh every 100 milliseconds
		videoTimer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Time.runtimeToSecond(getPlayTime()) >= Time
						.runtimeToSecond(getTotalTime())) {
					setPlayTime("00:00:00");
					videoTimer.stop();
					stopVideo();	
				} else {
					setPlayTime(Time.secondToRuntime(Integer.parseInt(Long
							.toString(mediaPlayerComponent.getMediaPlayer()
									.getTime())) / 1000));
					videoTime.setText(getPlayTime() + " / " + getTotalTime());
					sliderSkip = false;
					move.setValue((int) mediaPlayerComponent.getMediaPlayer()
							.getTime());
					sliderSkip = true;
				}
			}
		});
	}

	// basic function panel
	private void setUpFunctionPanel() {		
		// Stop
		stop = new JButton();
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopVideo();
			}
		});
		stop.setEnabled(false);
		
		//puse
		puse = new JButton();
		puse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//is skipping
				if (forwardTimer.isRunning()) {
					forwardTimer.stop();
					mediaPlayerComponent.getMediaPlayer().mute(false);
					mediaPlayerComponent.getMediaPlayer().play();
					setButton(puse, "4.png");
				} else if (backwardTimer.isRunning()) {
					backwardTimer.stop();
					mediaPlayerComponent.getMediaPlayer().mute(false);
					mediaPlayerComponent.getMediaPlayer().play();
					setButton(puse, "4.png");
				} else {
					//is playing
					if (mediaPlayerComponent.getMediaPlayer().isPlaying()) {
						setButton(puse, "12.png");
						mediaPlayerComponent.getMediaPlayer().pause();
						videoTimer.stop();
					} else {
						//is puse
						mediaPlayerComponent.getMediaPlayer().play();
						setButton(puse, "4.png");
						videoTimer.start();
					}
				}
			}

		});
		puse.setEnabled(false);
		
		//skip backwards
		backward = new JButton();
		backward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (forwardTimer.isRunning()) {
					forwardTimer.stop();
				}
				setButton(puse, "12.png");
				mediaPlayerComponent.getMediaPlayer().mute(true);
				if (!backwardTimer.isRunning()) {
					//skip faster
					backwardSpeed = -500;
				} else {
					if (backwardSpeed < -3000) {
						backwardSpeed = backwardSpeed - 500;
					} else {
						backwardSpeed = -500;
					}
				}
				backwardTimer.start();
				videoTimer.start();
			}
		});

		backward.setEnabled(false);

		//skip forward
		forward = new JButton();
		forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (backwardTimer.isRunning()) {
					backwardTimer.stop();
				}
				setButton(puse, "12.png");
				mediaPlayerComponent.getMediaPlayer().mute(true);

				if (!forwardTimer.isRunning()) {
					forwardSpeed = 500;
				} else {
					//skip faster
					if (forwardSpeed < 3000) {
						forwardSpeed = forwardSpeed + 500;
					} else {
						forwardSpeed = 500;
					}
				}
				forwardTimer.start();
				videoTimer.start();
			}
		});
		forward.setEnabled(false);
		setButton(puse, "12.png");
		setButton(forward, "1.png");
		setButton(backward, "9.png");
		setButton(stop, "11.png");
		functionPanel.add(stop);
		functionPanel.add(backward);
		functionPanel.add(puse);
		functionPanel.add(forward);

	}

	private void setButton(JButton b, String dir) {
		//set button picture
		ImageIcon img = new ImageIcon(System.getProperty("user.dir") + "/res/"
				+ dir);
		b.setIcon(img);
		b.setPreferredSize(new Dimension(40, 40));
		b.setBorderPainted(false);
		b.setContentAreaFilled(false);
		b.setFocusPainted(false);
		b.setOpaque(false);
	}

	private void enableButtons() {
		//enable the buttons, when there is video in player
		puse.setEnabled(true);
		setButton(puse, "4.png");
		backward.setEnabled(true);
		forward.setEnabled(true);
		mute.setEnabled(true);
		stop.setEnabled(true);
		shot.setEnabled(true);
	}

	private void disableButtons() {
		//disable buttons when there is no video
		stop.setEnabled(false);
		puse.setEnabled(false);
		backward.setEnabled(false);
		forward.setEnabled(false);
		mute.setEnabled(false);
		shot.setEnabled(false);
	}

	public void playVideo(File selectedFile) throws IOException {
		//play the selected video file
		videoEditor = VideoEditorMain.getVideoEditor();
		if (selectedFile.exists()) {
			//stop current video
			stopVideo();
			//enable panels
			videoEditor.getEdit().enableSouthEditor(selectedFile);
			videoEditor.geteastPanel().getDetail().enableDetail(selectedFile);
			//play video
			String mediaPath = selectedFile.getAbsolutePath();
			mediaPlayerComponent.getMediaPlayer().startMedia(mediaPath);
			mediaPlayerComponent.getMediaPlayer().mute(false);
			mediaPlayerComponent.getMediaPlayer().setVolume(100);
			//set components
			setPlayTime("00:00:00");
			setTotalTime(Time.setTotalTime((int) mediaPlayerComponent
					.getMediaPlayer().getLength()));
			move.setMaximum((int) (mediaPlayerComponent).getMediaPlayer()
					.getLength());
			videoTimer.start();
			enableButtons();

		}

	}

	public void stopVideo() {
		//stop playing video
		videoEditor.setTitle("");
		setButton(puse, "12.png");
		disableButtons();
		mediaPlayerComponent.getMediaPlayer().stop();
		//stop timer
		if (forwardTimer.isRunning()) {
			forwardTimer.stop();
		}
		if (backwardTimer.isRunning()) {
			backwardTimer.stop();
		}
		setTotalTime("00:00:00");
		setPlayTime("00:00:00");
		videoTime.setText(getPlayTime() + " / " + getTotalTime());
		move.setValue(0);
		//disable panels
		videoEditor.getEdit().disableSouthEditor();
	}

	//Getter and setter
	public String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
}
