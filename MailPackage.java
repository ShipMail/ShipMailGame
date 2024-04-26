import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class MailPackage {

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

    public MailPackage(GamePanel p, int xPos, int yPos, NinjaAnimationManager ninja){
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



    public void update() {				// modify time and change the effect if necessary
        if(alpha > 50)
        {
            alpha = alpha - alphaChange; //pirate collects a package and it disappears
        }

        //if(alpha == 0)
        //    alpha = 255;
		
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

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

}
