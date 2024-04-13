import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import javax.swing.JPanel;
import java.util.Random;
import java.awt.Image;

public class Ship {
    private GamePanel panel;

    private int x;
    private int y;
 
    private int width;
    private int height;
 
    private int originalX;
    private int originalY;
 
    private int dx;		// increment to move along x-axis
    private int dy;		// increment to move along y-axis

    private Color backgroundColour;
    private Dimension dimension;
    
    private Image shipImage;
    Background b;


    public Ship(GamePanel p, int xPos, int yPos) {
        panel = p;
        dimension = panel.getSize();
        backgroundColour = panel.getBackground ();
  
        width = 800;
        height = 500;

        x = xPos;
        y = yPos;
  
        dx = 5;	
        dy = 0;			
  
        shipImage = ImageManager.loadImage ("images/ship.png");
        b = panel.background;
     }


     public void draw (Graphics2D g2) {

        g2.drawImage(shipImage, x, y, width, height, null);
  
     }
  
  
     public void move() {
  
        if (!panel.isVisible ()) return;
  
        b.moveLeft();
     }
}
