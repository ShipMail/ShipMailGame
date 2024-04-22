import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PirateAnimationManager {

    Animation animation;
    Animation animation1;
    Animation animation2;
    Animation animation3;

    Rectangle2D.Double[] animHitBoxes;
    Rectangle2D.Double[] anim1HitBoxes;
    Rectangle2D.Double[] anim2HitBoxes;
    Rectangle2D.Double[] anim3HitBoxes;

    Image[] animFrames;
    Image[] anim1Frames;
    Image[] anim2Frames;
    Image[] anim3Frames;

    int numAnimFrames;
    int numAnim1Frames;
    int numAnim2Frames;
    int numAnim3Frames;

	private int x;		// x position of animation1
	private int y;		// y position of animation1
    private String type;

    private NinjaAnimationManager ninjaAnimationManager;
    private LootManager lootManager;
    private MailPackage[] mailPackages;

	private int width;
	private int height;

	private int dx;		// increment to move along x-axis
	private int dy;		// increment to move along y-axis

    Animation currentAnimation;
    AffineTransform fall;
    GamePanel panel;

    boolean dead = false;


    public PirateAnimationManager(GamePanel p, int xPos, int yPos, String type, NinjaAnimationManager ninja, LootManager loot, MailPackage[] mailPackages) {

        panel = p;
        dx = 5;		// increment to move along x-axis
        dy = 5;	// increment to move along y-axis
        x = xPos;
        y = yPos;
        this.type = type;

        ninjaAnimationManager = ninja;
        lootManager = loot;
        this.mailPackages = mailPackages;

        animation = new Animation(true);

        Image animImage1 = ImageManager.loadImage("images/pirate-left (1).png");
        Image animImage2 = ImageManager.loadImage("images/pirate-left (2).png");
        Image animImage3 = ImageManager.loadImage("images/pirate-left (3).png");
        Image animImage4 = ImageManager.loadImage("images/pirate-left (4).png");
        Image animImage5 = ImageManager.loadImage("images/pirate-left (5).png");

        //pirate left animation
        animation.addFrame(animImage1, 100);
        animation.addFrame(animImage2, 100);
        animation.addFrame(animImage3, 100);
        animation.addFrame(animImage4, 100);
        animation.addFrame(animImage5, 100);

        animFrames = new Image[5];
        animFrames[0] = animImage1;
        animFrames[1] = animImage2;
        animFrames[2] = animImage3;
        animFrames[3] = animImage4;
        animFrames[4] = animImage5;

        numAnimFrames = 5;

        animation1 = new Animation(true);

        Image animImage6 = ImageManager.loadImage("images/pirate-right (1).png");
        Image animImage7 = ImageManager.loadImage("images/pirate-right (2).png");
        Image animImage8 = ImageManager.loadImage("images/pirate-right (3).png");
        Image animImage9 = ImageManager.loadImage("images/pirate-right (4).png");
        Image animImage10 = ImageManager.loadImage("images/pirate-right (5).png");

        //pirate right animation
        animation1.addFrame(animImage6, 100);
        animation1.addFrame(animImage7, 100);
        animation1.addFrame(animImage8, 100);
        animation1.addFrame(animImage9, 100);
        animation1.addFrame(animImage10, 100);

        anim1Frames = new Image[5];
        anim1Frames[0] = animImage6;
        anim1Frames[1] = animImage7;
        anim1Frames[2] = animImage8;
        anim1Frames[3] = animImage8;
        anim1Frames[4] = animImage9;

        numAnim1Frames = 5;


        animation2 = new Animation(false);

        Image animImage11 = ImageManager.loadImage("images/pirate-sword-left (1).png");
        Image animImage12 = ImageManager.loadImage("images/pirate-sword-left (2).png");
        Image animImage13 = ImageManager.loadImage("images/pirate-sword-left (3).png");
        Image animImage14 = ImageManager.loadImage("images/pirate-sword-left (4).png");

        //pirate aims sword left animation
        animation2.addFrame(animImage11, 100);
        animation2.addFrame(animImage12, 100);
        animation2.addFrame(animImage13, 100);
        animation2.addFrame(animImage14, 100);

        anim2Frames = new Image[4];
        anim2Frames[0] = animImage11;
        anim2Frames[1] = animImage12;
        anim2Frames[2] = animImage13;
        anim2Frames[3] = animImage14;

        numAnim2Frames = 4;


        animation3 = new Animation(false);
                    
        Image animImage15 = ImageManager.loadImage("images/pirate-sword-right (1).png");
        Image animImage16 = ImageManager.loadImage("images/pirate-sword-right (2).png");
        Image animImage17 = ImageManager.loadImage("images/pirate-sword-right (3).png");
        Image animImage18 = ImageManager.loadImage("images/pirate-sword-right (4).png");

        //pirate aims sword right animation
        animation3.addFrame(animImage15, 25);
        animation3.addFrame(animImage16, 25);
        animation3.addFrame(animImage17, 25);
        animation3.addFrame(animImage18, 75);

        anim3Frames = new Image[5];
        anim3Frames[0] = animImage15;
        anim3Frames[1] = animImage16;
        anim3Frames[2] = animImage17;
        anim3Frames[3] = animImage18;

        numAnim3Frames = 4;

       animHitBoxes = new Rectangle2D.Double[numAnimFrames];
       anim1HitBoxes = new Rectangle2D.Double[numAnim1Frames];
       anim2HitBoxes = new Rectangle2D.Double[numAnim2Frames];
       anim3HitBoxes = new Rectangle2D.Double[numAnim3Frames];

       currentAnimation = getAnimation();

       fall = new AffineTransform(); //for pirate to fall when it dies
	}


    public void start() {
        currentAnimation.start();
	}

	
	public void update() {

        //activate attack animation based on direction of pirate when collision occurred
        if(collidesWithNinja(ninjaAnimationManager) && currentAnimation.equals(animation) && !dead && !ninjaAnimationManager.isDead()){
            currentAnimation = animation2;
            currentAnimation.start();

            if(!ninjaAnimationManager.isAttacking()){
                ninjaAnimationManager.killNinja(true);
            }
        }
        
        //kill the ninja if pirate collides with it
        if(collidesWithNinja(ninjaAnimationManager) && currentAnimation.equals(animation1) && !dead && !ninjaAnimationManager.isDead()){
            currentAnimation = animation3;
            currentAnimation.start();

            if(!ninjaAnimationManager.isAttacking()){
                ninjaAnimationManager.killNinja(true);
            }
        }

        //revert to original animations
        if(currentAnimation.equals(animation2) && !currentAnimation.isStillActive() && !dead){
            currentAnimation = animation;
        }

        if(currentAnimation.equals(animation3) && !currentAnimation.isStillActive() && !dead){
            currentAnimation = animation1;
        }

        //stop the animation if the ninja strikes the pirate
        if(ninjaAnimationManager.isAttacking() && collidesWithNinja(ninjaAnimationManager) && !dead){
            currentAnimation.stop();

            width = currentAnimation.getImage().getWidth(null);
            height = currentAnimation.getImage().getHeight(null);

            fall.translate(0, height); // Translate to position the sprite correctly on the screen
            fall.rotate(Math.toRadians(-90), x + width / 2, y + height / 2); // Rotate by -90 degrees (to fall backwards)
            
            dead = true;
        }
        


        //pirate chasing ninja effect
        if(x < ninjaAnimationManager.getX() && !ninjaAnimationManager.isDead() && !dead){
            currentAnimation = animation1;

            if(!currentAnimation.isStillActive())
                currentAnimation.start();
            
            x = x + dx;
        }

        else if(x > ninjaAnimationManager.getX() && !ninjaAnimationManager.isDead() && !dead){
            currentAnimation = animation;

            if(!currentAnimation.isStillActive())
                currentAnimation.start();
            
            x = x - dx;
        }


        /*add logic to implement pirates getting near to packages*/
        if(ninjaAnimationManager.isDead()){
            for(int i=0; i < mailPackages.length; i++){
                while(x < mailPackages[i].getX() && !mailPackages[i].collected()){
                    currentAnimation = animation1;

                    if(!currentAnimation.isStillActive())
                        currentAnimation.start();
                    
                        x = x + dx;
                    //x = mailPackages[i].getX() - mailPackages[i].getWidth();
                }
        
                while(x > mailPackages[i].getX() && !mailPackages[i].collected()){
                    currentAnimation = animation;
        
                    if(!currentAnimation.isStillActive())
                        currentAnimation.start();
                    
                    x = x - dx;
                    //x = mailPackages[i].getX() - mailPackages[i].getWidth();
                }
            }
        }
        
    
        if(ninjaAnimationManager.isDead()){
            for(int i=0; i < mailPackages.length; i++){
                if(collidesWithPackage(mailPackages[i]))
                    mailPackages[i].update();
            }
        }

       
        


        if(y < ninjaAnimationManager.getY() && !ninjaAnimationManager.isDead())
            y = y + dy;

        else if(y > ninjaAnimationManager.getY() && !ninjaAnimationManager.isDead())
            y = y - dy;

        if(x > 650)
          x = 0;

        if(x < 0)
          x = 650;

        if(y > 350)
            y = 350;


        if(!dead)    
            currentAnimation.update();

        if (!currentAnimation.isStillActive()){
            return;
        }
	}


	public void draw(Graphics2D g2) {

        AffineTransform origAT = g2.getTransform(); // save original transform
        
        if (!currentAnimation.isStillActive()){

            if(!dead)
                g2.drawImage(currentAnimation.getImage(), x, y, (int)(currentAnimation.getImage().getWidth(null)*2.5), (int)(currentAnimation.getImage().getHeight(null)*2.5), null);
            else{
               g2.transform(fall);
            }
        }

        g2.drawImage(currentAnimation.getImage(), x, y, (int)(currentAnimation.getImage().getWidth(null)*2.5), (int)(currentAnimation.getImage().getHeight(null)*2.5), null);

    
        g2.setTransform(origAT); // restore original transform

        //draw dead pirate's loot for ninja to collect
        if(dead){
            lootManager.draw(g2);

            //make loot disappear only when ninja collects it
            lootManager.update();
        }
    }


    public Rectangle2D.Double[] getBoundingRectangles(){
        if(currentAnimation.equals(animation))
            return getAnimFrameBounds();
        else if(currentAnimation.equals(animation1))
            return getAnim1FrameBounds();
        else if(currentAnimation.equals(animation2))
            return getAnim2FrameBounds();
        else
            return getAnim3FrameBounds();
    }

    
    public Rectangle2D.Double[] getAnimFrameBounds() {

        //get bounding rectangles of each frame in an animation
        for(int i=0; i<numAnimFrames; i++){
            width = animFrames[i].getWidth(null);
            height = animFrames[i].getHeight(null);
            animHitBoxes[i] = new Rectangle2D.Double (x, y, width, height);
        }

        return animHitBoxes;
    }


    public Rectangle2D.Double[] getAnim1FrameBounds() {

        for(int i=0; i<numAnim1Frames; i++){
            width = anim1Frames[i].getWidth(null);
            height = anim1Frames[i].getHeight(null);
            anim1HitBoxes[i] = new Rectangle2D.Double (x, y, width, height);
        }

        return anim1HitBoxes;
    }


    public Rectangle2D.Double[] getAnim2FrameBounds() {
        for(int i=0; i<numAnim2Frames; i++){
            width = anim2Frames[i].getWidth(null);
            height = anim2Frames[i].getHeight(null);
            anim2HitBoxes[i] = new Rectangle2D.Double (x, y, width, height);
        }

        return anim2HitBoxes;
    }


    public Rectangle2D.Double[] getAnim3FrameBounds() {
        for(int i=0; i<numAnim3Frames; i++){
            width = anim3Frames[i].getWidth(null);
            height = anim3Frames[i].getHeight(null);
            anim3HitBoxes[i] = new Rectangle2D.Double (x, y, width, height);
        }

        return anim3HitBoxes;
	}


    public Animation getAnimation(){
        if(type.equals("left"))
            return animation;
        else if(type.equals("right"))
            return animation1;
        else if(type.equals("sword-left"))
            return animation2;
        else
            return animation3;
    }

    
    public boolean collidesWithNinja(NinjaAnimationManager ninjaAnimationManager){
        Rectangle2D.Double[] ninjaHitBoxes = ninjaAnimationManager.getBoundingRectangles();
        int numNinjaAnimFrames; 
        numNinjaAnimFrames = ninjaHitBoxes.length;
        animHitBoxes = getAnimFrameBounds();
        anim1HitBoxes = getAnim1FrameBounds();

        //pirate going left
        for(int i=0; i<numAnimFrames; i++){

            for(int j=0; j<numNinjaAnimFrames; j++){
                if(animHitBoxes[i].intersects(ninjaHitBoxes[j]))
                    return true;
            }
        }

        //pirate going right
        for(int i=0; i<numAnim1Frames; i++){

            for(int j=0; j<numNinjaAnimFrames; j++){
                if(anim1HitBoxes[i].intersects(ninjaHitBoxes[j]))
                    return true;
            }
        }

        return false;
    }


    public boolean collidesWithPackage(MailPackage mailPackage){
        Rectangle2D.Double[] pirateRects = getAnimFrameBounds();
        Rectangle2D.Double[] pirateRects1 = getAnim1FrameBounds();

        for(int i=0; i < numAnimFrames; i++){
            Rectangle2D.Double packageRect = new Rectangle2D.Double(mailPackage.getX(), mailPackage.getY(), mailPackage.getWidth(), mailPackage.getHeight());
            if(packageRect.intersects(pirateRects[i]))
                return true;
        }

        for(int i=0; i < numAnim1Frames; i++){
            Rectangle2D.Double packageRect = new Rectangle2D.Double(mailPackage.getX(), mailPackage.getY(), mailPackage.getWidth(), mailPackage.getHeight());
            if(packageRect.intersects(pirateRects1[i]))
                return true;
        }
        
        return false;
    }



    public boolean checkLife(){
        return dead;
    }
}
