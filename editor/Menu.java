package editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import main.VideoEditorMain;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import com.jtattoo.plaf.mint.MintLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;

public class Menu {

	public static void setUpMenuBar(JMenuBar menuBar) {

		/**
		 * The class is used to set up all the functions in the top menu bar of
		 * the editor. There are total four menu selections, File, Skin, Video,
		 * Help. Each of them have their items added.
		 * 
		 * JTattoo look and feel used: http://www.jtattoo.net/
		 * 
		 */
		

		// initial all JMenu
		JMenu file = new JMenu("  File  ");
		JMenu theme = new JMenu("  Skin  ");
		JMenu video = new JMenu("  Video  ");
		JMenu help = new JMenu("  Help  ");

		// initial all JItem
		JMenuItem open = new JMenuItem("Open File");
		JMenuItem black = new JMenuItem("Black");
		JMenuItem gray = new JMenuItem("Gray");
		JMenuItem yellow = new JMenuItem("Yellow");
		JMenuItem mint = new JMenuItem("Mint");
		JMenuItem blue = new JMenuItem("Blue");
		JMenuItem helpm = new JMenuItem("Tips");
		final JMenuItem fullScreen = new JMenuItem("Full Screen");

		// add JItem to he corresponding JMenu
		file.add(open);
		theme.add(black);
		theme.add(yellow);
		theme.add(mint);
		theme.add(gray);
		theme.add(blue);
		video.add(fullScreen);
		help.add(helpm);

		// Set up the menu bar, add all JMenu to menu bar
		menuBar.add(file);
		menuBar.add(theme);
		menuBar.add(video);
		menuBar.add(help);

		// Listener below
		// help listener, create new Help JFrame
		helpm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Help();
			}
		});

		// full screen listener, set the video player
		// in the editor to full screen.
		fullScreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// if the current state is not full screen
				if (fullScreen.getText().equals("Full Screen")) {
					// hide video information(east panel) and editor panel
					VideoEditorMain.getVideoEditor().getEdit()
							.setVisible(false);
					VideoEditorMain.getVideoEditor().geteastPanel()
							.setVisible(false);
					fullScreen.setText("Exist Full Screen");
				} else {
					// if the current state is full screen
					// exit full screen mode
					VideoEditorMain.getVideoEditor().getEdit().setVisible(true);
					VideoEditorMain.getVideoEditor().geteastPanel()
							.setVisible(true);
					fullScreen.setText("Full Screen");
				}
			}
		});

		// different skins listeners
		// set look and feel to different colors
		// blue listener
		blue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UIManager.setLookAndFeel(new McWinLookAndFeel());
					SwingUtilities.updateComponentTreeUI(VideoEditorMain
							.getVideoEditor());

				} catch (UnsupportedLookAndFeelException e1) {
					e1.printStackTrace();
				}
			}
		});
		// black listener
		black.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UIManager.setLookAndFeel(new HiFiLookAndFeel());
					SwingUtilities.updateComponentTreeUI(VideoEditorMain
							.getVideoEditor());

				} catch (UnsupportedLookAndFeelException e1) {
					e1.printStackTrace();
				}
			}
		});
		// gray listener
		gray.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UIManager.setLookAndFeel(new AcrylLookAndFeel());
					SwingUtilities.updateComponentTreeUI(VideoEditorMain
							.getVideoEditor());

				} catch (UnsupportedLookAndFeelException e1) {

					e1.printStackTrace();
				}
			}
		});
		// mint listener
		mint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UIManager.setLookAndFeel(new MintLookAndFeel());
					SwingUtilities.updateComponentTreeUI(VideoEditorMain
							.getVideoEditor());

				} catch (UnsupportedLookAndFeelException e1) {

					e1.printStackTrace();
				}
			}
		});
		// yellow listener
		yellow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UIManager.setLookAndFeel(new BernsteinLookAndFeel());
					SwingUtilities.updateComponentTreeUI(VideoEditorMain
							.getVideoEditor());

				} catch (UnsupportedLookAndFeelException e1) {

					e1.printStackTrace();
				}
			}
		});

		// open file listener
		// call openVideo function in the video player to
		// open a new video in the editor
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VideoEditorMain.getVideoEditor().openVideo();

			}
		});

	}
}
