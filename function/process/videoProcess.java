package function.process;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import main.VideoEditorMain;
import player.VideoPlayer;
import editor.VideoEditor;
import function.Time;
import function.gui.WaitProcessBar;

;

public class videoProcess extends SwingWorker<Void, Integer> {
	/**
	 * Using swing worker to call ffmpeg in bash to generate video.
	 * And using new thread to update the progress bar at the same time
	 */
	private WaitProcessBar wait;
	private VideoPlayer videoPlayer;
	private VideoEditor videoEditor;
	private String command;
	private String videoFile;
	private boolean play = true;
	private int prograss;
	private String specialTime = null;

	public videoProcess(String command, String videoFile, String specialTime) {
		this.command = command;
		this.videoFile = videoFile;
		if (command.endsWith(".mp3")) {
			play = false;
		}
		if (specialTime != null) {
			//set special total time for extractor
			this.specialTime = specialTime;
		}
	}

	@Override
	protected Void doInBackground() throws Exception {
		videoEditor = VideoEditorMain.getVideoEditor();
		videoPlayer = VideoEditorMain.getVideoPlayer();
//show progress bar
		wait = new WaitProcessBar();
		wait.setVisible(true);
		wait.setAlwaysOnTop(true);
		
		//call bash
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", command);
		final Process process = builder.start();
		
		//initial thread to get input stream
		new Thread() {
			private Scanner sc;
			public void run() {
				sc = new Scanner(process.getErrorStream());
				sc.hasNextLine();
				while (sc.hasNext()) {
					String t = sc.next();
					if (t.contains("time=")) {
						String[] test = t.split("=");
						//calculate the process for progress bar
						if (specialTime != null) {
							prograss = (int) (Time.runtimeToSecond(test[1])
									* 100 / Time.runtimeToSecond(specialTime) * 100) / 100;
						} else {
							prograss = (int) (Time.runtimeToSecond(test[1])
									* 100
									/ Time.runtimeToSecond(videoPlayer
											.getTotalTime()) * 100) / 100;
						}
						//update
						publish(prograss);
					}
				}
			}
		}.start();
		process.waitFor();
		return null;
	}

	@Override
	protected void done() {
	//ask user if play the video after finish
		// dispose progress bar
		wait.updateProgress(100);
		wait.setVisible(false);
		wait.dispose();
		if (play == true) {
			File video = new File(videoFile);
			if (video.exists()) {
				
				int i = JOptionPane
						.showConfirmDialog(
								null,
								"The video has been geneated successfully! Play it now?",
								null, JOptionPane.YES_NO_OPTION);
				if (i == JOptionPane.YES_OPTION) {// play video
					try {
						videoEditor.getPlayList().addPlayFile(video);
						videoPlayer.playVideo(video);
					} catch (InterruptedException | IOException e) {

						e.printStackTrace();
					}
				}
			} else {
				//show error message
				JOptionPane.showMessageDialog(null,
						"Sorry, Something goes wrong. Please try again.");
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"The audio has been geneated successfully!", null,
					JOptionPane.PLAIN_MESSAGE);
		}
	}

	//update progress bar
	@Override
	protected void process(List<Integer> chunks) {
		for (Integer i : chunks) {
			wait.updateProgress(i);
		}
	}

}
