package editor.panel.east;

import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class EastTabPanel extends JPanel {
	/**
	 * This is the panel that located in the east of the editor. 
	 * There are two tabs in it, video list and video details.
	 */

	private static final long serialVersionUID = 8947183494514041942L;
	private PlayList playList;
	private VideoDetail detail;

	public EastTabPanel() throws IOException {
		//Initialize tab panel
		JTabbedPane tabPane = new JTabbedPane(JTabbedPane.TOP);
		tabPane.setPreferredSize(new Dimension(300, 500));
		
		//add playlist and video detail to east panel
		playList = new PlayList();
		tabPane.addTab("Video List", playList);
		detail = new VideoDetail();
		tabPane.addTab("Video Detail", detail);
        add(tabPane);
		//set east panel size
        setPreferredSize(new Dimension(300, 500));

	}

	//getters and setters
	public PlayList getPlayList() {
		return playList;
	}

	public VideoDetail getDetail() {
		return detail;
	}
}
