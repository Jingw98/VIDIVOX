package editor.panel.east;

import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import function.process.ShotProcess;

public class VideoListShot {
	/**
	 * Produce the the icons in the video play list with ffmpeg. Icons are saved
	 * in a folder.
	 */
	private ImageIcon selectIcon;

	public void produceShot(File videoFile) {
		// call ffmpeg to produce screen shot at third second of the video
		//the icon file is named by video file hash code
		ShotProcess shot = new ShotProcess(
				"ffmpeg -i "
						+ videoFile.getAbsolutePath()
						+ " -y -f image2 -s 100x80 -ss 00:00:03 -t 0.00001  ./VideoShot/"
						+ videoFile.hashCode() + ".jpg", "./VideoShot/"
						+ videoFile.hashCode() + ".jpg", false);
		shot.execute();
	}

	//get selected icon produced to set up play list
	public Icon getSelectedIcon(File video) {
		selectIcon = new ImageIcon("./VideoShot/" + video.hashCode() + ".jpg");
		return selectIcon;

	}

}
