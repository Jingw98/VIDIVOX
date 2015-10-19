package function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class CallBash {
	/**
	 * Call the bash from the java.
	 * And get inputStream from bash.
	 * 
	 * @param cmd
	 * @return
	 * @throws IOException
	 */
	
	// Execute bash command and return the standard output
	public static  BufferedReader callBashBuffer(String cmd) throws IOException{
		ProcessBuilder builder =new ProcessBuilder("/bin/bash", "-c", cmd);
		Process process = builder.start();
		InputStream stdout = process.getInputStream();
		BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
				return stdoutBuffered;
	}
	
	// Execute bash command and return the process
	public static Process bashProcess(String cmd) throws IOException{
		ProcessBuilder builder =new ProcessBuilder("/bin/bash", "-c", cmd);
		Process process = builder.start();
		InputStream stdout = process.getInputStream();
		process.getErrorStream();
		new BufferedReader(new InputStreamReader(stdout));
		return process;
	}
	
	// Execute bash command and return nothing
	public static void callBashVoid(String cmd) throws IOException{
		ProcessBuilder builder =new ProcessBuilder("/bin/bash", "-c", cmd);
		builder.start();
	}
	
	// Execute bash command  and wait for the process done
	public static Scanner callBashVoidWait(String cmd) throws IOException, InterruptedException{
		ProcessBuilder builder =new ProcessBuilder("/bin/bash", "-c", cmd);
		Process process = builder.start();
		InputStream stdout = process.getInputStream();
		new BufferedReader(new InputStreamReader(stdout));
		Scanner sc = new Scanner(process.getErrorStream());
		
		process.waitFor();
		return sc;
		
	}
	
	

}
