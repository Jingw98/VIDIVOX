package function;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
	/**
	 * Calculate the player time issues. Convert between String and int(in
	 * seconds).
	 * 
	 * @param time
	 * @return
	 */
	// Transfer time from second format(s) to runtime format(hh:mm:ss)
	public static String secondToRuntime(int time) {
		StringBuffer buffer = new StringBuffer();
		if (time >= 3600) {
			//greater than one hour
			if (time / 3600 > 10) {
				buffer.append(Integer.toString(time / 3600) + ":");
			} else {
				buffer.append("0" + Integer.toString(time / 3600) + ":");
			}
			time = time - 3600 * (time / 3600);
		} else {
			buffer.append("00:");
		}

		if (time >= 60) {
			//greater than one minutes
			if (time / 60 > 10) {
				buffer.append(Integer.toString(time / 60) + ":");
			} else {
				buffer.append("0" + Integer.toString(time / 60) + ":");
			}
			time = time - 60 * (time / 60);
		} else {
			buffer.append("00:");
		}

		if (time >= 10) {
			buffer.append(Integer.toString(time));
		} else {
			buffer.append("0" + Integer.toString(time));
		}
		return buffer.toString();
	}

	// Transfer time from runtime format(hh:mm:ss) to second format(s)
	public static int runtimeToSecond(String str) {
		String[] hms = str.trim().split(":");
		if (hms.length != 3) {
			return 0;
		}
		int time = (int) (Double.parseDouble(hms[0]) * 60 * 60
				+ Double.parseDouble(hms[1]) * 60 + Double.parseDouble(hms[2]));
		return (int) time;
	}

	// Get the total time of video file
	public static String setTotalTime(int time) {
		int t = time / 1000;
		int hours = t / 3600;
		int minutes = t / 60 - 60 * hours;
		int seconds = (t % 3600) % 60;
		String m = Integer.toString(minutes), s = Integer.toString(seconds);
		if (minutes < 10) {
			m = "0" + Integer.toString(minutes);
		}
		if (seconds < 10) {
			s = "0" + Integer.toString(seconds);
		}

		return "0" + hours + ":" + m + ":" + s;

	}

	public static String getCurrentTime() {
		//get the current time and convert to Sting in dd-MM-yyyy_HH:mm:ss
		Date now = new Date();
		DateFormat f = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
		return f.format(now);

	}
}
