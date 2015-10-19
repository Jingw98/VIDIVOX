package player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JSlider;
import javax.swing.plaf.metal.MetalSliderUI;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import function.Time;

public class moveSlider extends JSlider {
	/**
	 * The progress slider in the video player
	 */
	private static final long serialVersionUID = 734094849177769411L;
	@SuppressWarnings("unused")
	private String playTime;

	public moveSlider(final Boolean sliderSkip,
			final EmbeddedMediaPlayerComponent mediaPlayerComponent,
			String playTime) {
		this.playTime = playTime;
		setValue(0);
		setEnabled(true);
		
		//add mouse listener to control slider by click
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setUI(new MetalSliderUI() {
					protected void scrollDueToClickInTrack(int direction) {
						if (mediaPlayerComponent.getMediaPlayer() != null) {
							int moveValue = getValue();
							if (getOrientation() == JSlider.HORIZONTAL) {
								moveValue = this
										.valueForXPosition(getMousePosition().x);
							}
							setValue(moveValue);
							mediaPlayerComponent.getMediaPlayer().setTime(
									moveValue);
						}
					}
				});
			}

			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (mediaPlayerComponent.getMediaPlayer() != null) {
					mediaPlayerComponent.getMediaPlayer().setTime(getValue());
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		
		//add mouse listener to control slider by slide
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent a) {
				mediaPlayerComponent.getMediaPlayer().setTime(getValue());
				setPlayTime(Time.secondToRuntime(Integer.parseInt(Long
						.toString(mediaPlayerComponent.getMediaPlayer()
								.getTime())) / 1000));
			}
		});

	}

	//setter
	private void setPlayTime(String playTime) {
		this.playTime = playTime;

	}

}
