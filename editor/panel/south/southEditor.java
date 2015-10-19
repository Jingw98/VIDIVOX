package editor.panel.south;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import main.VideoEditorMain;
import editor.VideoEditor;

public class southEditor extends JPanel {
	/**
	 * The panel that is in the south of the editor. It contains all the editing
	 * function panels. And a output path chooser.
	 */
	
	private static final long serialVersionUID = -5197305860475362344L;
	private VideoEditor videoEditor;
	private Festival festival;
	private AddAudio audio;
	private CovertVideoFormat convertor;
	private Gif gif;
	private Extractor extractor;
	private WaterMark waterMark;
	private JTabbedPane tabPane;
	private JPanel out;
	private JLabel outPut;
	private JTextField path;
	private JButton change;

	public southEditor(String savePath) throws IOException, ParseException {
		setLayout(new BorderLayout());
		// set up file chooser
		final JFileChooser choose = new JFileChooser();
		File defaultPath = new File(savePath);
		choose.setSelectedFile(defaultPath);
		choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// set up out put path components
		out = new JPanel();
		outPut = new JLabel("Out Put Path:     ");
		path = new JTextField();
		path.setPreferredSize(new Dimension(500, 30));
		path.setEditable(false);
		path.setText(savePath);
		change = new JButton("Change");

		out.add(outPut);
		out.add(path);
		out.add(change);

		// set up tab panel
		tabPane = new JTabbedPane(JTabbedPane.TOP);
		tabPane.setPreferredSize(new Dimension(800, 300));
		festival = new Festival(path);
		tabPane.addTab("  Festival  ", festival);
		audio = new AddAudio();
		tabPane.addTab("   Audio   ", audio);
		convertor = new CovertVideoFormat(path);
		tabPane.addTab(" Convert Format ", convertor);
		gif = new Gif();
		tabPane.addTab("    Gif     ", gif);
		extractor = new Extractor();
		tabPane.addTab("  Extractor ", extractor);
		waterMark = new WaterMark();
		tabPane.addTab(" Water Mark ", waterMark);

		// add components to panel
		add(tabPane, BorderLayout.CENTER);
		add(out, BorderLayout.SOUTH);

		// change out put path listener, change the out put path
		change.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				videoEditor = VideoEditorMain.getVideoEditor();
				int returnVal = choose.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					videoEditor.setSavePath(choose.getSelectedFile().getPath());
					path.setText(videoEditor.getSavePath());
				}
			}
		});

	}

	public void enableSouthEditor(File selectedFile) {
		// enable the panels, when there is video in editor
		audio.enableAddAudio(selectedFile);
		gif.enableGif();
		convertor.enableConvertor(selectedFile);
		extractor.enableExtractor(selectedFile);
		waterMark.enableWaterMark();
	}

	public void disableSouthEditor() {
		// disable panels when there is no video
		audio.disableAddAudio();
		convertor.disableFormate();
		gif.disableGif();
		extractor.disableExtractor();
		waterMark.disableWaterMark();
	}

	// getter
	public Extractor getExtractor() {
		return extractor;

	}

}
