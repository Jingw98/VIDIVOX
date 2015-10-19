package main;

import java.awt.EventQueue;
import javax.swing.UIManager;

import com.jtattoo.plaf.mint.MintLookAndFeel;
import editor.VideoEditor;
import player.VideoPlayer;

public class VideoEditorMain {

	private static VideoEditor videoEditor;
	private static VideoPlayer videoPlayer;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//set feel and look
					UIManager.setLookAndFeel(new MintLookAndFeel());
					//initialize video editor
					videoPlayer = new VideoPlayer();
					videoEditor = new VideoEditor(videoPlayer);
					videoEditor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//getter and setter
	public static VideoEditor getVideoEditor() {
		return videoEditor;
	}

	public static VideoPlayer getVideoPlayer() {

		return videoPlayer;
	}

}
