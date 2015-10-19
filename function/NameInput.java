package function;

import java.io.File;

import javax.swing.JOptionPane;

import main.VideoEditorMain;

public class NameInput {
	/**
	 * Get the file name from the user and check if it is available.
	 * @return
	 */
	
	public static String name() {
		//get name
		String name = (String) JOptionPane.showInputDialog(null,
				"New Video file name：\n", "Save", JOptionPane.PLAIN_MESSAGE,
				null, null, "video");
		//check name
		File now = new File(VideoEditorMain.getVideoEditor().getSavePath() + "/" + name + ".mp4");
		if (name == null) {
			return null;
		} else if (name.trim()
				.replaceAll("[a-z]*[A-Z]*[0-9]*\\d*-*_*\\s* *", "").length() != 0
				|| name.trim().equals("")) {
			//special symbol
			JOptionPane.showMessageDialog(null,
					"Incorrect file name! Please try again", null,
					JOptionPane.INFORMATION_MESSAGE);
			name = "";
			name();
		} else if (now.exists()) {
			//exist file
			int i = JOptionPane.showConfirmDialog(null,
					"Exist file, over write it?", null,
					JOptionPane.YES_NO_OPTION);
			if (i == JOptionPane.OK_OPTION) {
				return name;
			}
		} else {
			return name;
		}
		return null;
	}
	
	public static boolean checkInputName(String path){
		//check the file selected file from its path
		String[] s=path.split("/");
		String name=s[s.length-1];	
		int i=name.indexOf(".");
		name=name.substring(0,i);
		
		if(name.trim()
				.replaceAll("[a-z]*[A-Z]*[0-9]*\\d*-*_*\\s* *", "").length() != 0
				|| name.trim().equals("")){
			JOptionPane.showMessageDialog(null,
					"Incorrect input file name, might contains special symbol. Please try agin.", null,
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}
}
