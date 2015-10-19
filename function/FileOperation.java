package function;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class FileOperation {
	/**
	 * Deal with the file operations in the editor.
	 * 
	 * @param fileSelector
	 * @param filter
	 * @param mode
	 * @param fileType
	 * @return
	 */

	// Create a file chooser for choosing file
	public static File chooseFile(JFileChooser fileSelector, FileFilter filter,
			int mode, String fileType) {
		fileSelector.setDialogTitle("Please select a " + fileType);
		fileSelector.setFileFilter(filter);
		fileSelector.setSelectedFile(null);
		fileSelector.setFileSelectionMode(mode);
		int returnVal = fileSelector.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fileSelector.getSelectedFile();
		} else {
			return null;
		}
	}

	// Find the current directory
	public static File setCurrentDir() throws IOException {
		BufferedReader stdoutBuffered = CallBash.callBashBuffer("pwd");
		return new File(stdoutBuffered.readLine());

	}

	// Find the initial directory
	public static void initialPath() throws IOException {
		BufferedReader stdoutBuffered = CallBash
				.callBashBuffer("test -d ./.soundFile; echo $?");

		if (!stdoutBuffered.readLine().equals("0")) {
			CallBash.callBashVoid("mkdir ./.soundFile");

		}
		stdoutBuffered = CallBash
				.callBashBuffer("test -d ./VideoShot; echo $?");

		if (!stdoutBuffered.readLine().equals("0")) {
			CallBash.callBashVoid("mkdir ./VideoShot");

		}

		stdoutBuffered = CallBash
				.callBashBuffer("test -d ./.videoFile; echo $?");
		if (!stdoutBuffered.readLine().equals("0")) {
			CallBash.callBashVoid("mkdir ./.videoFile");
		}
		stdoutBuffered = CallBash.callBashBuffer("test -d ./OutPut; echo $?");
		if (!stdoutBuffered.readLine().equals("0")) {
			CallBash.callBashVoid("mkdir ./OutPut");
		}
		stdoutBuffered = CallBash
				.callBashBuffer("test -d ./ScreenShot; echo $?");

		if (!stdoutBuffered.readLine().equals("0")) {
			CallBash.callBashVoid("mkdir ./ScreenShot");

		}
	}

	public static void saveMP3(String path) {
		if (!(path.endsWith(".mp3"))) {
			path = path + ".mp3";
		}
		try {
			CallBash.callBashVoid("cp ./.soundFile/audio.mp3 " + path);
		} catch (IOException e1) {

			e1.printStackTrace();
		}
	}
}
