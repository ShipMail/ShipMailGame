import java.awt.Image;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class NinjaRollAnimation {

    Animation animation;
    Animation animation2;

	private int x;		// x position of animation
	private int y;		// y position of animation

	private int width;
	private int height;

	private int dx;		// increment to move along x-axis
	private int dy;		// increment to move along y-axis

    Image animImage0;
    Image animImage1;
	Image animImage2;
	Image animImage3;
	Image animImage4;


    public NinjaRollAnimation(){
        animation = new Animation(true);	// loop continuously

        dx = 10;	// increment to move along x-axis
        dy = 0;		// increment to move along y-axis

        // load images for animation

        Image animImage0 = ImageManager.loadImage("images/ninja0.png");
        Image animImage1 = ImageManager.loadImage("images/ninja1.png");
		Image animImage2 = ImageManager.loadImage("images/ninja2.png");
		Image animImage3 = ImageManager.loadImage("images/ninja3.png");
		Image animImage4 = ImageManager.loadImage("images/ninja4.png");

	
		// create animation object and insert frames

		animation.addFrame(animImage0, 100);
        animation.addFrame(animImage1, 100);
		animation.addFrame(animImage2, 100);
		animation.addFrame(animImage3, 100);
		animation.addFrame(animImage4, 100);
    }


    public void start() {
		x = 296;
        y = 280;
	}


	public void move(int direction){
		if(direction == 1){
			x = x - dx;
		}

		else if(direction == 2){
			x = x + dx;
		}

        else if(direction == 3)
            animation.start();
	}

	
	public void update() {

		if (!animation.isStillActive()) {
			return;
		}

		animation.update();

		//x = x + dx;
		//y = y + dy;

		//if (x > 400)
			//x = 5;
	}


	public void draw(Graphics2D g2) {

		if (!animation.isStillActive()) {
			g2.drawImage(animation.getImage(), x, y, (int)(animation.getImage().getWidth(null)/1.5), (int)(animation.getImage().getHeight(null)/1.5), null);
		}

		g2.drawImage(animation.getImage(), x, y, (int)(animation.getImage().getWidth(null)/1.5), (int)(animation.getImage().getHeight(null)/1.5), null);
	}


}
