package editor.panel.east;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import main.VideoEditorMain;
import editor.VideoEditor;
import function.gui.MyCellRenderer;
import player.VideoPlayer;

public class PlayList extends JPanel {
	/**
	 * PlayList is the JPanle that contains a List in it. And all the added
	 * video is shown with a small screen shot. that has been generated
	 * automatically. The play list also has the functions of play and delete.
	 */

	private static final long serialVersionUID = 1L;
	private JList<File> list;
	private VideoPlayer videoPlayer;
	private VideoEditor videoEditor;
	private DefaultListModel<File> listModel = null;
	private ArrayList<Icon> icons = new ArrayList<Icon>();
	private VideoListShot ls;
	private File selectedFile;

	@SuppressWarnings({ "serial", "unchecked" })
	public PlayList() {
		// Initialize video list shot for icons
		ls = new VideoListShot();
		// initialize JList with list model
		listModel = new DefaultListModel<File>();
		list = new JList<File>(listModel) {
			@Override
			public int locationToIndex(Point location) {
				int index = super.locationToIndex(location);
				if (index != -1
						&& !getCellBounds(index, index).contains(location)) {
					return -1;
				} else {
					return index;
				}
			}
		};
		// set up the JList model with a icon and a file name
		list.setCellRenderer((new MyCellRenderer(icons)));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// set up the pop up menu to JList
		final JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem play = new JMenuItem("Play");
		JMenuItem delete = new JMenuItem("Delete");
		popupMenu.add(play);
		popupMenu.add(delete);
		popupMenu.setPopupSize(90, 50);
		// add JList to the panel
		JScrollPane scp = new JScrollPane(list);
		scp.setPreferredSize(new Dimension(300, 500));
		add(scp);

		// listeners below
		// play listener, play the video selected
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedFile = list.getSelectedValue();
				try {
					int index = (int) listModel.indexOf(selectedFile);
					videoPlayer.playVideo(listModel.get(index));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// delete, listener, delete the video selected
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				upDateVideoEditor();
				// check if the video is player, and ask user
				if (selectedFile == videoEditor.getVideoFile()) {
					int a = JOptionPane
							.showOptionDialog(
									videoEditor,
									"Selected video file is playing," +
									" Are you sure to delete it?",
									null, JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE, null, null,
									null);
					if (a == JOptionPane.YES_OPTION) {
						// delete the video from video editor
						removeFile();
					}
				} else {
					removeFile();
				}
			}
		});

		// list listener, check the action of user
		list.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				upDateVideoEditor();
				if (list.locationToIndex(e.getPoint()) == -1) {
					list.clearSelection();
				} else if (e.getClickCount() == 2) {
					// double click for play video
					list.setSelectedIndex(-1);
					selectedFile = list.getSelectedValue();
					try {
						int index = (int) listModel.indexOf(selectedFile);
						videoPlayer.playVideo(listModel.get(index));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else if (e.isMetaDown()) {
					// right mouse button for pop up menu
					list.setSelectedIndex(-1);
					if (!list.getSelectedValue().equals(null)) {
						popupMenu.show(list, e.getX(), e.getY());
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			});
	}

	public void addPlayFile(File video) throws InterruptedException {
		// show the information to user is too many video is added
		if (listModel.size() > 20) {
			JOptionPane.showMessageDialog(null, "Too Many Video added!", null,
					JOptionPane.WARNING_MESSAGE);
		} else {
			if (!listModel.contains(video)) {
				// check if the video list is already contains the video
				// produce the icons in the play list
				ls.produceShot(video);
				// give time to produce the shot and load the video
				Thread.sleep(500);
                //add to corresponding list to update JList
				icons.add(ls.getSelectedIcon(video));
				listModel.add(listModel.getSize(), video);
			}
		}
	}

	private void removeFile() {
		//remove the video from the JList
		int index = listModel.indexOf(list.getSelectedValue());
		videoPlayer.stopVideo();
		listModel.remove(index);
		icons.remove(index);
		
	}
	private void upDateVideoEditor() {
		// update the video editor reference
		videoEditor = VideoEditorMain.getVideoEditor();
		videoPlayer = VideoEditorMain.getVideoPlayer();
	}
}
