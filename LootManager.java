import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class LootManager implements ImageFX {

    private int x;
    private int y;

    private int width;
    private int height;

    private BufferedImage copy;			// copy of image
    int time, timeChange;				// to control when the image is grayed
	  int alpha, alphaChange;				// alpha value (for alpha transparency byte)


    private BufferedImage coin;
    private BufferedImage chest;
    private BufferedImage compass;
    private BufferedImage rum;
    private BufferedImage key;
    private BufferedImage lootImage;

    private String[] lootTypes = {"coin", "chest", "compass", "key", "rum"};
    private Random random;
    private int lootNum;

    private NinjaAnimationManager ninjaAnimationManager;
    

    public LootManager(int xPos, int yPos, NinjaAnimationManager ninja){
      x = xPos;
      y = yPos;

      ninjaAnimationManager = ninja;

      width = 50;
      height = 50;

      time = 0;				// range is 0 to 10
      timeChange = 1;				// set to 1

      alpha = 255;				// set to 255 (fully opaque)
      alphaChange = 51;			// how to update alpha in game loop

      coin = ImageManager.loadBufferedImage("images/pirate-coin.png");
      chest = ImageManager.loadBufferedImage("images/pirate-chest.png");
      compass = ImageManager.loadBufferedImage("images/pirate-rum.png");
      rum = ImageManager.loadBufferedImage("images/pirate-coin.png");
      key = ImageManager.loadBufferedImage("images/pirate-key.png");

      //generate random loot
      random = new Random();
      lootNum = random.nextInt(0, 4);
      lootImage = getLootImage(lootNum);
      copy = ImageManager.copyImage(lootImage);	//  make a copy of the original image
    }



    public BufferedImage getLootImage(int lootNum) {
        if(lootTypes[lootNum].equals("chest")){
          return chest;
        }
  
        else if(lootTypes[lootNum].equals("coin")){
          return coin;
        }
  
        else if(lootTypes[lootNum].equals("compass")){
          return compass;
        }
  
        else if(lootTypes[lootNum].equals("key")){
          return key;
        }
  
        else {
          return rum;
        }
    }


    public void draw(Graphics2D g2) {
        int imWidth = copy.getWidth();
		    int imHeight = copy.getHeight();

    		int [] pixels = new int[imWidth * imHeight];
    		copy.getRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);

    		int a, red, green, blue, newValue;

		for (int i=0; i<pixels.length; i++) {

			a = (pixels[i] >> 24);
			red = (pixels[i] >> 16) & 255;
			green = (pixels[i] >> 8) & 255;
			blue = pixels[i] & 255;

/*
			newValue = blue | (green << 8) | (red << 16) | (alpha << 24);
			pixels[i] = newValue;
*/

				
			if (a != 0) {
				newValue = blue | (green << 8) | (red << 16) | (alpha << 24);
				pixels[i] = newValue;
			}


		}
  
    	copy.setRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);	
      g2.drawImage(copy, x, y, width, height, null);
    }



    public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, width, height);
	}


    public boolean collidesWithNinja(NinjaAnimationManager ninja){
        Rectangle2D.Double[] ninjaRects = ninja.getBoundingRectangles();
        return ninjaRects[0].intersects(getBoundingRectangle());
    }


	public void update() {				// modify time and change the effect if necessary
        if(collidesWithNinja(ninjaAnimationManager) && alpha > 50)
        {
            alpha = alpha - alphaChange;

		    //if (alpha < 10)			
			//    alpha = 255;
        }
		
	}

  //loot has disappeared when ninja collected it
  public boolean collected() {
    return alpha == 0;
  }

}
