import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class Crow implements Motion {
	

	private static final int width = 50;
	private static final int height = 50;
   	private static final int XOFFSET = 20;

  	private JPanel panel;
	private int xAxis = 100;
   	private int x = 0;
  	private int y = 0;
 	private int dx = 7;
	private int dy = 2;
	Animation animation;

	private double amplitudeFactor = 30;	// increase value to increase amplitude (increase height of curve)
	private double frequencyFactor = 2.0;	// increase value to increase frequency of curve (decrease width of curve)

 	private boolean active;

	public Crow (JPanel p) {
		animation = new Animation(true);
		panel = p;
		active = true;

		Image frame1 = ImageManager.loadImage("images/crow1.png");
		Image frame2 = ImageManager.loadImage("images/crow2.png");
		Image frame3 = ImageManager.loadImage("images/crow3.png");
		Image frame4 = ImageManager.loadImage("images/crow4.png");

		animation.addFrame(frame1, 80);
		animation.addFrame(frame2, 80);
		animation.addFrame(frame3, 80);
		animation.addFrame(frame4, 80);
		
	}


	public boolean isActive() {
		return active;
	}

	public void activate() {
		active = true;
	}
	

	public void deActivate() {
		active = false;
	}

	public void start() {
		x = 5;
        	y = 400;
		animation.start();
	}

   	public void update () {  

      		if (!panel.isVisible ()) return;
			  animation.update();

			if(!animation.isStillActive())
				animation.start();
     
      		x += dx;

	
      		double radians = (2*x / 500.0) * Math.PI * frequencyFactor;

      		if (radians > (7 * Math.PI)) {		
	   		x = 0;
          		radians = 0.0;
      		}

     		y = (int) (Math.sin (radians) * amplitudeFactor);
		y = xAxis - y;
   	}
	
	   public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, width, height);
	}

   	public void draw (Graphics2D g2) { 
		if (!active)
			return;
			g2.drawImage(animation.getImage(),x+XOFFSET, y, width, height,null);
      		
   	}

}
