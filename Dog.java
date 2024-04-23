import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;


/**
    The CatAnimation class creates a wild cat animation containing
    eight frames. 
*/
public class Dog {
	
	Animation animation;

	private int x;		// x position of animation
	private int y;		// y position of animation

	private int width;
	private int height;

	private int dx;		// increment to move along x-axis
	private int dy;		// increment to move along y-axis

	public Dog() {

		animation = new Animation(true);	// loop continuously
		width = 100;
		height = 50;

        	dx = 10;		// increment to move along x-axis
        	dy = 0;		// increment to move along y-axis

		// load images for wild cat animation

		Image animImage1 = ImageManager.loadImage("images/dog_right1.png");
		Image animImage2 = ImageManager.loadImage("images/dog_right2.png");
		Image animImage3 = ImageManager.loadImage("images/dog_right3.png");
		Image animImage4 = ImageManager.loadImage("images/dog_right4.png");
		Image animImage5 = ImageManager.loadImage("images/dog_right5.png");
		Image animImage6 = ImageManager.loadImage("images/dog_right6.png");
		Image animImage7 = ImageManager.loadImage("images/dog_right7.png");
		Image animImage8 = ImageManager.loadImage("images/dog_right8.png");
	
		// create animation object and insert frames

		animation.addFrame(animImage1, 80);
		animation.addFrame(animImage2, 80);
		animation.addFrame(animImage3, 80);
		animation.addFrame(animImage4, 80);
		animation.addFrame(animImage5, 80);
		animation.addFrame(animImage6, 80);
		animation.addFrame(animImage7, 60);
		animation.addFrame(animImage8, 80);

	}


	public void start() {
		x = 5;
        	y = 400;
		animation.start();
	}

	
	public void update() {

		if (!animation.isStillActive()) {
			return;
		}

		animation.update();

		x = x + dx;
		y = y + dy;

		if (x > 900)
			x = 5;
	}


	public void draw(Graphics2D g2) {

		if (!animation.isStillActive()) {
			return;
		}

		g2.drawImage(animation.getImage(), x, y, width, height, null);
	}


	public Rectangle2D.Double getBoundingRectangle(){
		Rectangle2D.Double doggo = new Rectangle2D.Double (x + 20, y, width - 50 , height);

		return  doggo;
	   }
}
