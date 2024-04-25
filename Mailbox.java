import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;

public class Mailbox {
  	private Image bgImage; 
  	
	private int bgImageWidth;
	private int bgImageHeight;

	private GamePanel panel;
	private int panelWidth, panelHeight;
	private Backgroundd b;

 	private int bgX;			// X-coordinate of "actual" position

	private int bg1X;			// X-coordinate of background
	private int bgDX;			// size of horizontal background move (in pixels)

	private int bg1Y;			// Y-coordinate of background
	private int bgDY;			// size of vertical background move (in pixels)

	private HashSet<Integer> directions;
	private Mailman mm;
	private int deliveryStatus;
	int  x;
	

  public Mailbox(GamePanel panel, int imageFile, Backgroundd b, int X, Mailman mailman) {
    
	this.x = X;
    this.b = b;
	this.mm = mailman;
	directions = new HashSet<Integer>(3);		
															 														
	this.panel = panel;
	panelWidth = panel.getWidth();
	panelHeight = panel.getHeight();

    this.bgDX = 20;			// move factor along x-axis
	this.bgDY = 0;			// move factor along y-axis

	
	bg1X = X;
	bg1Y = 380; 

  }


  public int move (int direction) {

	if (direction == 1)
		return moveRight();
	else
	if (direction == 2){
		return moveLeft();
	}

       
	return 0;
  }


  public int moveLeft() {
	if( !directions.contains(Integer.valueOf(-2)) && !directions.contains(Integer.valueOf(-1))){	
		bg1X = bg1X - bgDX;
	}

	if (b.lCon()) 
	{		
		bg1X = bg1X;
		return 0;	
	}
	else
	return -2;		
  }




  public int moveRight() {
	if(!directions.contains(Integer.valueOf(-1)) && !directions.contains(Integer.valueOf(-2))){			
		bg1X = bg1X + bgDX;
		}

	if (b.rCon()) {
		bg1X = bg1X;
		return 0;		
	}
	return -1;		
   }




   public void setDirections(int direction){

	if(direction<0){	// stop background in given direction
		directions.add(Integer.valueOf(direction));
	}
	else if (direction > 0){	// restart background movement in given direction
		if(direction >= 10){	// corresponds to 2 directions
			directions.remove(Integer.valueOf(-direction/10));
			directions.remove(Integer.valueOf((-direction/10 -1)));
		}

		directions.remove(Integer.valueOf(-direction));		// update so background can move
	}
   }





public void loadImage(int i){
	if(i == 0){
	this.bgImage = ImageManager.loadImage("images/postbox.png");
}
else if (i == 1){
	this.bgImage = ImageManager.loadImage("images/postbox_mail.png");
}
 deliveryStatus = i;
 System.out.println("Delivery Status :" + i);
}




  public void draw (Graphics2D g2) {
	g2.drawImage(bgImage, bg1X, bg1Y,30,30,null);
  }




  public Rectangle2D.Double getBoundingRectangle() {
	return new Rectangle2D.Double (bg1X, bg1Y, 30, 30);
}




public boolean collidesWithMailman(){
	Rectangle2D.Double mailBoxRect = getBoundingRectangle();
	Rectangle2D.Double mmRect = mm.getBoundingRectangle();

	return mailBoxRect.intersects(mmRect);
}




public int getDeliveryStatus(){
	return deliveryStatus;
}

public void setDeliveryStatus(int i){
	deliveryStatus = i;
}

}
