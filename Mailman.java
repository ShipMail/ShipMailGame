import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JPanel;
import java.awt.Image;

public class Mailman {

	private JPanel panel;
	private int x;
	private int y;
	private int width;
	private int height;

	private int dx;
	private int dy;
	private int leftClick;
	private int rightClick;
 
	private Dog dog;
	private Image mailmanImage;
	
	private Image mmLeftImage1;
	private Image mmLeftImage2;
	private Image mmLeftImage3;
	private Image mmLeftImage4;
	private Image mmLeftImage5;
	private Image mmLeftImage6;


	private Image mmRightImage1;
	private Image mmRightImage2;
	private Image mmRightImage3;
	private Image mmRightImage4;
	private Image mmRightImage5;
	private Image mmRightImage6;
	
	private boolean jumping;
	private boolean falling;
	private int timeElapsed;
	private int startY;
	private int initialVelocity;
	private Floor floor;

	private HashSet directions;

	public Mailman (JPanel p, Floor floor, Dog dog) {
		panel = p;
		directions = new HashSet<>(3);
		leftClick = 0;
		rightClick = 0;

		x = 190;
		y = 360;

		dx = 15;				// set to zero since background moves instead
		dy = 8;				// size of vertical movement

		width = 80;
		height = 100;
		this.dog = dog;
		this.floor = floor;
		jumping = false;
		falling = false;

		mmLeftImage1 = ImageManager.loadImage ("images/mailman_walkleft1.png");
		mmLeftImage2 = ImageManager.loadImage ("images/mailman_walkleft2.png");
		mmLeftImage3 = ImageManager.loadImage ("images/mailman_walkleft3.png");
		mmLeftImage4 = ImageManager.loadImage ("images/mailman_walkleft4.png");
		mmLeftImage5 = ImageManager.loadImage ("images/mailman_walkleft5.png");
		mmLeftImage6 = ImageManager.loadImage ("images/mailman_walkleft6.png");

		mmRightImage1 = ImageManager.loadImage ("images/mailman_walkright1.png");
		mmRightImage2 = ImageManager.loadImage ("images/mailman_walkright2.png");
		mmRightImage3 = ImageManager.loadImage ("images/mailman_walkright3.png");
		mmRightImage4 = ImageManager.loadImage ("images/mailman_walkright4.png");
		mmRightImage5 = ImageManager.loadImage ("images/mailman_walkright5.png");
		mmRightImage6 = ImageManager.loadImage ("images/mailman_walkright6.png");

		mailmanImage = mmRightImage1;
	}


	public void draw (Graphics2D g2) {

		g2.drawImage(mailmanImage, x, y, width, height, null);
		

	}


	public int move (int direction) {

		if (!panel.isVisible ()) return 0;
		System.out.println("MAILMAN IS AT X = " + x);
      
		if (direction == 1 ) {
			leftClick++;
			int oldX = x;

			if(leftClick % 6 == 1){
				mailmanImage = mmLeftImage1;
			}
			else if (leftClick % 6 == 2){
				mailmanImage = mmLeftImage2;
			}
			else if(leftClick % 6 == 3){
				mailmanImage = mmLeftImage3;
			}
			else if(leftClick % 6 == 4){
				mailmanImage = mmLeftImage4;
			}
			else if(leftClick % 6 == 5){
				mailmanImage = mmLeftImage5;
			}
			else if(leftClick % 6 == 0){
				mailmanImage = mmLeftImage6;
			}
			
			if(directions.contains(Integer.valueOf(1))){		// can move left
				x = x - dx;			
				if(x<0)
					x= 0;
				
				
				if(x<=190 && oldX > 190){		// check if was moving left and reaches centre
					x = 190;					// reposition at centre
					directions.remove(Integer.valueOf(1));	// stop moving left
					directions.remove(Integer.valueOf(2));	// and right
					return 10;					// background can start scrolling left and right
				}

				else if(x<190)					
					return -1;		//don't move background left
			}
			
			else
				return 1;		// bat can't move left, let background scroll
		}
		else 
		if (direction == 2) {							
			int oldX = x;
			rightClick++;
			if(rightClick % 6 == 1){
				mailmanImage = mmRightImage1;
			}
			else if (rightClick % 6 == 2){
				mailmanImage = mmRightImage2;
			}
			else if (rightClick % 6 == 3){
				mailmanImage = mmRightImage3;
			}
			else if (rightClick % 6 == 4){
				mailmanImage = mmRightImage4;
			}
			else if (rightClick % 6 == 5){
				mailmanImage = mmRightImage5;
			}
			else if (rightClick % 6 == 0){
				mailmanImage = mmRightImage6;
			}


			if(directions.contains(Integer.valueOf(1))){		// can move right
				x = x + dx;			// move right
				if(x+width>panel.getWidth())
					x= panel.getWidth() - width;
				
				if(x>=190 && oldX < 190){					// check if was moving left and reaches centre
					x = 190;								// reposition at centre
					directions.remove(Integer.valueOf(1));	// stop moving left
					directions.remove(Integer.valueOf(2));	// and right
					return 10;								// background can start scrolling left and right
				}
				else if(x>190 )
					return -2;		//don't move background right
			}
			
			else
				return 2;		// bat can't move right, let background scroll
		}
		else if(direction == 5 && !jumping){
				jump();
		}
					
		return 0;	
	}


	public void jump () {  
		if (!panel.isVisible ()) return;
  
		jumping = true;
		timeElapsed = 0;
  
		startY = y;
		initialVelocity = 80;
	 }
  
  
	 private void fall() {
		falling = true;
		timeElapsed = 0;
  
		startY = y;
		initialVelocity = 0;
	 }
   
  
	 public void update () {
		
		int distance;
		int newY;
  
		timeElapsed++;
  
		if (jumping || falling) {
		 distance = (int) (initialVelocity * timeElapsed - 
							   4.9 * timeElapsed * timeElapsed);
		 newY = startY - distance;
		 y = newY;
  
		 if (collidesWithFloor()) {
		  jumping = false;
		  falling = false;
		  y = floor.getY() - height;
		 }	

		}
		
	 }


	 public boolean collidesWithFloor () {
	
		int floorX, floorY, floorRight;
	
		floorX = floor.getX();
		floorY = floor.getY();
		floorRight = floorX + floor.getWidth();	
		
		if ((y + height >= floorY) && (x + width >= floorX) && (x <= floorRight)) {
			return true;
		}
	
		return false;
	   }

	public void setDirections(int direction){
		if(direction == 1 && directions.contains(Integer.valueOf(1)))	//already moved left so can move right (back to centre)
			directions.add(Integer.valueOf(2));
		else if(direction == 2 && directions.contains(Integer.valueOf(2)))	//already moved right so can move left (back to centre)
			directions.add(Integer.valueOf(1));
		else if(direction == 3 && directions.contains(Integer.valueOf(3)))	//already moved up so can move down (back to centre)
			directions.add(Integer.valueOf(4));
		else if(direction == 4 && directions.contains(Integer.valueOf(4)))	//already moved down so can move up (back to centre)
			directions.add(Integer.valueOf(3));
		
		if(direction > 0){				// new direction the bat can move in
			directions.add(Integer.valueOf(direction));
			System.out.println("In mailman added: "+direction);
		}

	}



	public boolean isOnMailman (int x, int y) {
		Rectangle2D.Double myRectangle = getBoundingRectangle();
		return myRectangle.contains(x, y);
	}


	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, width, height);
	}

	public boolean collidesWithDog(){
		Rectangle2D.Double mailmanRect = getBoundingRectangle();
		Rectangle2D.Double dogRect = dog.getBoundingRectangle();

		return mailmanRect.intersects(dogRect);
	}

}