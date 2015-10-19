package function.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class WaterMarkPic extends TimerTask{
	/**
	 * Using timer to draw the selected image to panel
	 */
	private Image img;
	private int width;
	private int height;
	private File imgFile;
	private int w;
	private int h;
	private JPanel panel;
	
	
public WaterMarkPic(File imgFile,int w,int h,JPanel panel) throws IOException{
	this.imgFile=imgFile;
	this.h=h;
	this.w=w;
	this.panel=panel;
}

//change the image size to smaller
private void changeSize(){
	 width = (int) (Float.parseFloat(String.valueOf(width)) * 0.8);
    height = (int) (Float.parseFloat(String.valueOf(height)) * 0.8);
}

//draw the image in constant time period
@Override
public void run() {
	if(panel.isShowing()){
	try {
		img = ImageIO.read(imgFile);
	} catch (IOException e) {
		e.printStackTrace();
	}
	   width = img.getWidth(null);
   height = img.getHeight(null);
    while(width>w||height>h){
 	   changeSize();
    }
  
Graphics g = panel.getGraphics();
g.setColor(panel.getBackground());
g.fillRect(30, 0,150, 100);
g.drawImage(img, 30, 0, width,height, Color.LIGHT_GRAY, null);
g.dispose();
	}
}
}
