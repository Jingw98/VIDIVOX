package function.process;

import javax.swing.SwingWorker;

import function.CallBash;
import function.gui.Inco;

public class ShotProcess extends SwingWorker<Void, Void> {
	/**
	 * Using swing worker to call ffmpeg in bash to generate video.
	 */

	String command;
	String fileName;
	boolean show;

	public ShotProcess(String cmd, String fileName, boolean show) {
		command = cmd;
		this.fileName = fileName;
		this.show = show;

	}

	@Override
	protected Void doInBackground() throws Exception {
		//call bash
		CallBash.callBashVoidWait(command);
		return null;
	}

	@Override
	protected void done() {
		//show the screen shot after finished
		if (show) {
			new Inco(fileName);
		}
	}
}
