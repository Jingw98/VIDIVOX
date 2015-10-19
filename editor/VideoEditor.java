package editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;
import player.VideoPlayer;
import editor.panel.east.EastTabPanel;
import editor.panel.east.PlayList;
import editor.panel.south.southEditor;
import function.FileOperation;

public class VideoEditor extends JFrame {
	/**
	 * The main screen in the VIDIVOX. it contains all the layout of editor,
	 * which including the size and the position of different panels. 
	 * These panels is located in different 'editor.panel' packages.
	 */

	private static final long serialVersionUID = -4028747919177264574L;
	private File currentDir;
	private String savePath;
	private File selectedFile;
	private PlayList playList;
	private JPanel contentPanel;
	private southEditor edit;
	private VideoPlayer videoPlayer;
	private JMenuBar menuBar;
	private EastTabPanel eastTabPanel;

	public VideoEditor(VideoPlayer videoPlayer) throws IOException,
			ParseException {
		// set video player
		this.videoPlayer = videoPlayer;
		// setup path
		FileOperation.initialPath();
		// Initialize working directory
		currentDir = FileOperation.setCurrentDir();
		setSavePath(currentDir + "/OutPut");

		// basic gui set up
		setBounds(50, 50, 900, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// set content panel of the editor frame
		// Border layout is used
		contentPanel = new JPanel();
		contentPanel.setSize(900, 700);
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(videoPlayer, BorderLayout.CENTER);

		// play list in the east
		eastTabPanel = new EastTabPanel();
		eastTabPanel.setPreferredSize(new Dimension(300, 500));
		contentPanel.add(eastTabPanel, BorderLayout.EAST);

		// Menu bar
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		menuBar = new JMenuBar();
		Menu.setUpMenuBar(menuBar);
		setJMenuBar(menuBar);

		// editing panel in the south
		setEdit(new southEditor(savePath));
		getEdit().setPreferredSize(new Dimension(800, 220));
		contentPanel.add(getEdit(), BorderLayout.SOUTH);
		setContentPane(contentPanel);
	}

	public void openVideo() {
		// open video function, called when new video is added in editor

		final JFileChooser fileSelector = new JFileChooser();
		fileSelector.setCurrentDirectory(currentDir);
		try {
			// choose video to add
			setSelectedFile(FileOperation.chooseFile(fileSelector,
					new FileNameExtensionFilter("Video File", "avi", "mp4"),
					JFileChooser.FILES_ONLY, "video file"));
			if (selectedFile != null) {
				// if confirm add to play list and paly it
				setPlayList(eastTabPanel.getPlayList());
				playList.addPlayFile(getSelectedFile());
				videoPlayer.playVideo(getSelectedFile());
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	// getters and setters
	public VideoPlayer getVideoPlayer() {
		return videoPlayer;
	}

	public void setVideoPlayer(VideoPlayer videoPlayer) {
		this.videoPlayer = videoPlayer;
	}

	public File getVideoFile() {
		return getSelectedFile();
	}

	public File getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public southEditor getEdit() {
		return edit;
	}

	public void setEdit(southEditor edit) {
		this.edit = edit;
	}

	public PlayList getPlayList() {
		return playList;
	}

	public void setPlayList(PlayList playList) {
		this.playList = playList;
	}

	public EastTabPanel geteastPanel() {
		return eastTabPanel;
	}

}
