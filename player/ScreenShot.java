package player;

import java.io.IOException;
import javax.swing.JFrame;
import main.VideoEditorMain;
import editor.VideoEditor;
import function.*;
import function.process.ShotProcess;

public class ScreenShot extends JFrame {
	/**
	 * Produce the screen shot of the playing video at current time.
	 */

	private static final long serialVersionUID = 2360853935604785190L;
	private VideoEditor videoEditor;
	
	public ScreenShot() throws IOException {
		videoEditor = VideoEditorMain.getVideoEditor();
		String str = Time.getCurrentTime();
		
		//call shot process to produce screenshot
		ShotProcess shot = new ShotProcess("ffmpeg -i "
				+ videoEditor.getSelectedFile().getAbsolutePath()
				+ " -y -f image2 -ss "
				+ videoEditor.getVideoPlayer().getPlayTime()
				+ " -t 0.001 ./ScreenShot/ScreenShot_" + str + ".jpg",
				"./ScreenShot/ScreenShot_" + str + ".jpg", true);
		shot.execute();
	
	}

}
