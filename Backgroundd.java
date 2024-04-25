import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashSet;

public class Backgroundd {
  	private Image bgImage; 
  	
	private int bgImageWidth;
	private int bgImageHeight;

	private GamePanel panel;
	private int panelWidth, panelHeight;

 	private int bgX;			// X-coordinate of "actual" position

	private int bg1X;			// X-coordinate of background
	private int bgDX;			// size of horizontal background move (in pixels)

	private int bg1Y;			// Y-coordinate of background
	private int bgDY;			// size of vertical background move (in pixels)

	private HashSet directions;

  public Backgroundd(GamePanel panel, String imageFile, int bgDX) {
    

	directions = new HashSet<>(3);		// stores values corresponding to the directions
															// in which the background should not scroll to 
															// allow the player to move
	this.panel = panel;
    this.bgImage = ImageManager.loadImage(imageFile);
    bgImageWidth = bgImage.getWidth(null);		// get width of the background
   	bgImageHeight = bgImage.getHeight(null);	// get width of the background

	

	panelWidth = panel.getWidth();
	panelHeight = panel.getHeight();

	

    this.bgDX = 20;			// move factor along x-axis
	this.bgDY = 0;			// move factor along y-axis

	bg1X = ((bgImageWidth - panelWidth) / 2) * -1;	// sets the image's x to the intial negative x coordinate on the right hand side so the game starts in the middle
	bg1Y = ((bgImageHeight - panelHeight) / 2) * -1; 

  }


  public int move (int direction) {

	if (direction == 1)
		return moveRight();
	else
	if (direction == 2)
		return moveLeft();
	else return 0;
  }


  public int moveLeft() {

	// if bat is able to move left or right, don't update background 
	if( !directions.contains(Integer.valueOf(-2)) && !directions.contains(Integer.valueOf(-1))){	
		bg1X = bg1X - bgDX;
	}

	if (lCon()) {		
		//System.out.println ("Can't scroll right further.");
		bg1X = panelWidth - bgImageWidth;
		return 2;	// the bat can move right
	}
	else
	return -2;		//bat can't move right, background scrolling
  }


  public int moveRight() {

	// if bat is able to move left or right, don't update background
	if( !directions.contains(Integer.valueOf(-1)) && !directions.contains(Integer.valueOf(-2))){
					
		bg1X = bg1X + bgDX;
		
	}

	if (rCon()) {
		//System.out.println ("Can't scroll left further.");
		bg1X = 0;
		return 1;		//bat can move left
	}
	return -1;		//bat can't move left, background scrolling

   }




   public boolean rCon(){
	if(bg1X > 0){
		return true;
	}
	return false;
   }

   public boolean lCon(){
	if(bg1X + bgImageWidth < panelWidth){
		return true;
	}
	return false;
   }
 


   public void setDirections(int direction){

	if(direction<0){	// stop background in given direction
		directions.add(Integer.valueOf(direction));
		//System.out.println("Background Hashet added " +direction);
	}
	else if (direction > 0){	// restart background movement in given direction
		if(direction >= 10){	// corresponds to 2 directions
			directions.remove(Integer.valueOf(-direction/10));
			directions.remove(Integer.valueOf((-direction/10 -1)));
			//System.out.println("In bg removed (10s): " + (-direction/10));
			//System.out.println("In bg removed (10s): " + (-direction/10 -1));
		}

		directions.remove(Integer.valueOf(-direction));		// update so background can move
	    //System.out.println("Background Hashset removed: " + (-direction));
	}
   }

  public void draw (Graphics2D g2) {
	g2.drawImage(bgImage, bg1X, bg1Y, null);
  }

}
