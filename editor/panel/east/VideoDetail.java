package editor.panel.east;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VideoDetail extends JPanel {
	/**
	 * The videoDetail shows the information of the current editing video. 
	 * It is in the east panel of the editor. 
	 * 'No video' is displayed when no video is editing.
	 */
	private static final long serialVersionUID = 1934781951338282973L;
	private JLabel name1;
	private JLabel location1;
	private JLabel formate1;
	private JLabel size1;
	private JLabel editTime1;

	public VideoDetail() {
		// set up the lay out of the panel
		GridLayout g = new GridLayout(12, 1);
		setLayout(g);
		Dimension d = new Dimension(300, 40);
		// initialize the component in the panel
		JLabel name = new JLabel("Name :");
		JLabel location = new JLabel("Location :");
		JLabel formate = new JLabel("Format :");
		JLabel size = new JLabel("Size :");
		JLabel editTime = new JLabel("Last Edit Time :");

		name1 = new JLabel();
		name1.setPreferredSize(d);
		location1 = new JLabel();
		location.setPreferredSize(d);
		formate1 = new JLabel();
		formate1.setPreferredSize(d);
		size1 = new JLabel();
		size.setPreferredSize(d);
		editTime1 = new JLabel();
		editTime1.setPreferredSize(d);

		// add components to the panel
		add(name);
		add(name1);
		add(location);
		add(location1);
		add(formate);
		add(formate1);
		add(size);
		add(size1);
		add(editTime);
		add(editTime1);
		// set label to No video state
		disableDetail();
	}

	public void enableDetail(File video) {
		//set the label with video information
		name1.setText("    " + video.getName());
		location1.setText("    " + video.getParent());
		formate1.setText("    "
				+ video.getName().substring(video.getName().indexOf('.') + 1));
		//calculate the size of video in KB
		double size = video.length() / 1024;
		DecimalFormat df = new DecimalFormat(".##");
		size1.setText("    " + df.format(size) + " KB");
		//get last modified time
		long time = video.lastModified();
		Date date = new Date(time);
		editTime1.setText("    " + date);
	}

	public void disableDetail() {
		name1.setText("        No Video");
		location1.setText("        No Video");
		formate1.setText("        No Video");
		size1.setText("        No Video");
		editTime1.setText("        No Video");

	}
}
