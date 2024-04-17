import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Package {

    private int x;
    private int y;
    private int width;
    private int height;
    
    private BufferedImage copy;			// copy of image
    int time, timeChange;				// to control when the image is grayed
	int alpha, alphaChange;				// alpha value (for alpha transparency byte)

    private BufferedImage packageImage;
    private NinjaAnimationManager ninjaAnimationManager;
    private PirateAnimationManager[] pirateAnimationManagers;
    private GamePanel panel;

    public Package(GamePanel p, int xPos, int yPos, PirateAnimationManager[] pirates, NinjaAnimationManager ninja){
        x = xPos;
        y = yPos;
        panel = p;

        width = 50;
        height = 50;

        time = 0;				// range is 0 to 10
        timeChange = 1;				// set to 1
  
        alpha = 255;				// set to 255 (fully opaque)
        alphaChange = 51;			// how to update alpha in game loop

        packageImage = ImageManager.loadBufferedImage("images/package.png");
        pirateAnimationManagers = pirates;
        ninjaAnimationManager = ninja;
        copy = ImageManager.copyImage(packageImage);	//  make a copy of the original image
    }

    public void draw(Graphics2D g2){
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

      if(ninjaAnimationManager.isDead())
        g2.drawImage(copy, x, y, width, height, null);
    }


    public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, width, height);
	}


    //check if ninja collides with package
    public boolean collidesWithNinja(NinjaAnimationManager ninja){
        Rectangle2D.Double[] ninjaRects = ninja.getBoundingRectangles();
        return ninjaRects[0].intersects(getBoundingRectangle());
    }


    public boolean collidesWithPirate(PirateAnimationManager[] pirates){

        Rectangle2D.Double[] pirateRects = new Rectangle2D.Double[pirates.length];
        //check if any frame of a pirate animation collides with the package
        for(int i=0; i < pirates.length; i++){
            pirateRects = pirates[i].getBoundingRectangles();

            for(int j=0; j < pirateRects.length; j++){
                if(pirateRects[j].intersects(getBoundingRectangle())){
                    return true;
                }
            }
        }
        
        return false;
    }



    public void update() {				// modify time and change the effect if necessary
        if(collidesWithPirate(pirateAnimationManagers) && alpha > 50 && ninjaAnimationManager.isDead())
        {
            alpha = alpha - alphaChange; //pirate collects a package and it disappears
        }
		
	}

    //package has disappeared when pirate collected it
    public boolean collected() {
      return alpha == 0;
    }


    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

}
