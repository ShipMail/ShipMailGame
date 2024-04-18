import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class NinjaAnimationManager {

    Animation animation;
    Animation animation1; //right front flip
    Animation animation2; //move right
    Animation animation3; //right sword strike
    Animation animation4; //right sword chop
    Animation animation5; //move left
    Animation animation6; //left sword strike
    Animation animation7; //left sword chop
    Animation animation8; //left front flip
    AffineTransform fall;

	private int x;		// x position of animation1
	private int y;		// y position of animation1
    GamePanel panel;

	private int width;
	private int height;

	private int dx;		// increment to move along x-axis
	private int dy;		// increment to move along y-axis
    private boolean dead = false;

    int numAnim1Frames = 5;
    int numAnim2Frames = 3;
    int numAnim3Frames = 4;
    int numAnim4Frames = 4;
    int numAnim5Frames = 1;
    int numAnim6Frames = 4; //left sword strike
    int numAnim7Frames = 4; //left sword chop
    int numAnim8Frames = 5; //left front flip

    Rectangle2D.Double[] anim1HitBoxes;
    Rectangle2D.Double[] anim2HitBoxes;
    Rectangle2D.Double[] anim3HitBoxes;
    Rectangle2D.Double[] anim4HitBoxes;
    Rectangle2D.Double[] anim5HitBoxes;
    Rectangle2D.Double[] anim6HitBoxes;
    Rectangle2D.Double[] anim7HitBoxes;
    Rectangle2D.Double[] anim8HitBoxes;

    Image animImage0;
    Image animImage1;
    Image animImage2;
    Image animImage3;
    Image animImage4;
    Image animImage5;
    Image animImage6;
    Image animImage7;
    Image animImage8;
    Image animImage9;
    Image animImage10;
    Image animImage11;
    Image animImage12;
    Image animImage13;
    Image animImage14;
    Image animImage15;
    Image animImage16;
    
    //left sword strike
    Image animImage17;
    Image animImage18;
    Image animImage19;
    Image animImage20;

    //left sword chop
    Image animImage21;
    Image animImage22;
    Image animImage23;
    Image animImage24;

    //left front flip
    Image animImage25;
    Image animImage26;
    Image animImage27;
    Image animImage28;
    Image animImage29;

    //array of frames for each animation
    Image[] anim1Frames;
    Image[] anim2Frames;
    Image[] anim3Frames;
    Image[] anim4Frames;
    Image[] anim5Frames;

    Image[] anim6Frames; //left sword strike
    Image[] anim7Frames; //left sword chop
    Image[] anim8Frames; //left front flip


    public NinjaAnimationManager(GamePanel p){

        panel = p;

        //create animation objects
        animation1 = new Animation(false);	// loop continuously
        animation2 = new Animation(false);	// loop continuously
        animation3 = new Animation(false);	// loop continuously
        animation4 = new Animation(false);	// loop continuously
        animation5 = new Animation(false);	// loop continuously
        animation6 = new Animation(false);	// loop continuously
        animation7 = new Animation(false);	// loop continuously
        animation8 = new Animation(false);	// loop continuously
        fall = new AffineTransform();

        dx = 5;	// increment to move along x-axis
        dy = 5;		// increment to move along y-axis
        x = 100;
        y = 350;

        // load images for animations

        //animation 1
        animImage0 = ImageManager.loadImage("images/ninja0.png");
        animImage1 = ImageManager.loadImage("images/ninja1.png");
		animImage2 = ImageManager.loadImage("images/ninja2.png");
		animImage3 = ImageManager.loadImage("images/ninja3.png");
		animImage4 = ImageManager.loadImage("images/ninja4.png");
        animImage5 = ImageManager.loadImage("images/ninja5.png");
        animImage6 = ImageManager.loadImage("images/ninja6.png");
		animImage7 = ImageManager.loadImage("images/ninja7.png");
        animImage8 = ImageManager.loadImage("images/ninja8.png");
		animImage9 = ImageManager.loadImage("images/ninja9.png");
        animImage10 = ImageManager.loadImage("images/ninja10.png");
        animImage11 = ImageManager.loadImage("images/ninja11.png");
        animImage12 = ImageManager.loadImage("images/ninja12.png");
		animImage13 = ImageManager.loadImage("images/ninja13.png");
		animImage14 = ImageManager.loadImage("images/ninja14.png");
        animImage15 = ImageManager.loadImage("images/ninja15.png");

        //animation 5
        animImage16 = ImageManager.loadImage("images/ninja16.png");

        //animation 6
        animImage17 = ImageManager.loadImage("images/ninja17.png");
        animImage18 = ImageManager.loadImage("images/ninja18.png");
        animImage19 = ImageManager.loadImage("images/ninja19.png");
        animImage20 = ImageManager.loadImage("images/ninja20.png");

        //animation 7
        animImage21 = ImageManager.loadImage("images/ninja21.png");
        animImage22 = ImageManager.loadImage("images/ninja22.png");
        animImage23 = ImageManager.loadImage("images/ninja23.png");
        animImage24 = ImageManager.loadImage("images/ninja24.png");

        //animation 8
        animImage25 = ImageManager.loadImage("images/ninja25.png");
        animImage26 = ImageManager.loadImage("images/ninja26.png");
        animImage27 = ImageManager.loadImage("images/ninja27.png");
        animImage28 = ImageManager.loadImage("images/ninja28.png");
        animImage29 = ImageManager.loadImage("images/ninja29.png");

	
		// create and insert frames
        anim1Frames = new Image[numAnim1Frames];
        anim1Frames[0] = animImage0;
        anim1Frames[1] = animImage1; 
        anim1Frames[2] = animImage2;
        anim1Frames[3] = animImage3;
        anim1Frames[4] = animImage4;


        anim2Frames = new Image[numAnim2Frames];
        anim2Frames[0] = animImage5;
        anim2Frames[1] = animImage9;
        anim2Frames[2] = animImage13;

        anim3Frames = new Image[numAnim3Frames];
        anim3Frames[0] = animImage5;
        anim3Frames[1] = animImage6;
        anim3Frames[2] = animImage7;
        anim3Frames[3] = animImage8;

        anim4Frames = new Image[numAnim4Frames];
        anim4Frames[0] = animImage9;
        anim4Frames[1] = animImage10;
        anim4Frames[2] = animImage11;
        anim4Frames[3] = animImage12;
        
        anim5Frames = new Image[numAnim5Frames];
        anim5Frames[0] = animImage16;

        anim6Frames = new Image[numAnim6Frames];
        anim6Frames[0] = animImage17;
        anim6Frames[1] = animImage18;
        anim6Frames[2] = animImage19;
        anim6Frames[3] = animImage20;

        anim7Frames = new Image[numAnim7Frames];
        anim7Frames[0] = animImage21;
        anim7Frames[1] = animImage22;
        anim7Frames[2] = animImage23;
        anim7Frames[3] = animImage24;

        anim8Frames = new Image[numAnim8Frames];
        anim8Frames[0] = animImage25;
        anim8Frames[1] = animImage26;
        anim8Frames[2] = animImage27;
        anim8Frames[3] = animImage28;
        anim8Frames[4] = animImage29;


        //bounding rectangles for each frame in an animation
        anim1HitBoxes = new Rectangle2D.Double[numAnim1Frames];
        anim2HitBoxes = new Rectangle2D.Double[numAnim2Frames];
        anim3HitBoxes = new Rectangle2D.Double[numAnim3Frames];
        anim4HitBoxes = new Rectangle2D.Double[numAnim4Frames];
        anim5HitBoxes = new Rectangle2D.Double[numAnim5Frames];
        anim6HitBoxes = new Rectangle2D.Double[numAnim5Frames];
        anim6HitBoxes = new Rectangle2D.Double[numAnim6Frames];
        anim7HitBoxes = new Rectangle2D.Double[numAnim7Frames];
        anim8HitBoxes = new Rectangle2D.Double[numAnim8Frames];


        //right front flip
		for(int i=0; i<numAnim1Frames; i++){
            animation1.addFrame(anim1Frames[i], 100);
        }

        //walk right
        for(int i=0; i<numAnim2Frames; i++){
            animation2.addFrame(anim2Frames[i], 100);
        }

        //right sword strike
        for(int i=0; i<numAnim3Frames; i++){
            animation3.addFrame(anim3Frames[i], 100);
        }

        //right sword chop
        for(int i=0; i<numAnim4Frames; i++){
            animation4.addFrame(anim4Frames[i], 100);
        }

        //walk left
        for(int i=0; i<numAnim5Frames; i++){
            animation5.addFrame(anim5Frames[i], 100);
        }


        for(int i=0; i < anim6Frames.length; i++){
            animation6.addFrame(anim6Frames[i], 100);
        }

        for(int i=0; i < anim7Frames.length; i++){
            animation7.addFrame(anim7Frames[i], 100);
        }

        for(int i=0; i < anim8Frames.length; i++){
            animation8.addFrame(anim8Frames[i], 100);
        }


        animation = animation2;
    }


	public void move(int direction){

        if(!dead){
            if(direction == 1){

                //change the animation based on key pressed
                animation = animation5;  // turn right
                x = x - dx;
            }
    
            else if(direction == 2){
                animation = animation2; // turn left
                animation.start();
                x = x + dx;
            }

            else if(direction == 3) { 
                y = y - dy;
            }

            else if(direction == 4){
                y = y + dy;
            }
    
            else if(direction == 5){
    
                if(animation.equals(animation2))
                    animation = animation1; //right front flip
                else
                    animation = animation8; //left front flip
                animation.start();
            }
    
            else if(direction == 6){
                if(animation.equals(animation2))
                    animation = animation3; //sword right strike
                else
                    animation = animation6; //sword left strike
                animation.start();
            }
    
            else if(direction == 7){
                if(animation.equals(animation2))
                    animation = animation4; // sword right chop
                else
                    animation = animation7; //sword left chop
                animation.start();
            }

            if(x <= 0)
                x = 0;

            if(x >= panel.getWidth()-width)
                x = panel.getWidth()-width;

            if(y <= 350)
                y = 350;

            if(y >= panel.getHeight() - height)
                y = panel.getHeight() - height;
    
        }

	}

	
	public void update() {

        if(animation != null){
            if (!animation.isStillActive()) {
                return;
            }

            if(!dead)
                animation.update();

        }
		

		//x = x + dx;
		//y = y + dy;

		//if (x > 400)
			//x = 5;
	}


	public void draw(Graphics2D g2) {

        AffineTransform origAT = g2.getTransform(); // save original transform
		if (!animation.isStillActive()) {
            if(!dead)
                g2.drawImage(animation.getImage(), x, y, (animation.getImage().getWidth(null)), (animation.getImage().getHeight(null)), null);
            else
                g2.transform(fall);
		}

		g2.drawImage(animation.getImage(), x, y, (animation.getImage().getWidth(null)), (animation.getImage().getHeight(null)), null);
        g2.setTransform(origAT); // restore original transform
    }


    public Rectangle2D.Double[] getBoundingRectangles() {

        if(animation == animation1)
          return getAnim1FrameBounds();
        else if(animation == animation2)
          return getAnim2FrameBounds();
        else if(animation == animation3)
          return getAnim3FrameBounds();
        else if(animation == animation4)
          return getAnim4FrameBounds();
        else if(animation == animation5)
          return getAnim5FrameBounds();
        else if(animation == animation6)
          return getAnim6FrameBounds();
        else if(animation == animation7)
          return getAnim7FrameBounds();
        else
          return getAnim8FrameBounds();

    }

    public Rectangle2D.Double[] getAnim5FrameBounds() {

        //get bounding rectangles of each frame in an animation
        for(int i=0; i<numAnim5Frames; i++){
            width = anim5Frames[i].getWidth(null);
            height = anim5Frames[i].getHeight(null);
            anim5HitBoxes[i] = new Rectangle2D.Double (x, y, width, height);
        }

        return anim5HitBoxes;
    }


    public Rectangle2D.Double[] getAnim2FrameBounds() {

        for(int i=0; i<numAnim2Frames; i++){
            width = anim2Frames[i].getWidth(null);
            height = anim2Frames[i].getHeight(null);
            anim2HitBoxes[i] = new Rectangle2D.Double (x, y, width, height);
        }

        return anim2HitBoxes;
    }


    public Rectangle2D.Double[] getAnim1FrameBounds() {

        for(int i=0; i<numAnim1Frames; i++){
            width = anim1Frames[i].getWidth(null);
            height = anim1Frames[i].getHeight(null);
            anim1HitBoxes[i] = new Rectangle2D.Double (x, y, width, height);
        }

        return anim1HitBoxes;
    }


    public Rectangle2D.Double[] getAnim3FrameBounds() {
        for(int i=0; i<numAnim3Frames; i++){
            width = anim3Frames[i].getWidth(null);
            height = anim3Frames[i].getHeight(null);
            anim3HitBoxes[i] = new Rectangle2D.Double (x, y, width, height);
        }

        return anim3HitBoxes;
    }


    public Rectangle2D.Double[] getAnim4FrameBounds() {
        for(int i=0; i<numAnim4Frames; i++){
            width = anim4Frames[i].getWidth(null);
            height = anim4Frames[i].getHeight(null);
            anim4HitBoxes[i] = new Rectangle2D.Double (x, y, width, height);
        }

        return anim4HitBoxes;
	}

    public Rectangle2D.Double[] getAnim6FrameBounds() {
        for(int i=0; i<anim6Frames.length; i++){
            width = anim6Frames[i].getWidth(null);
            height = anim6Frames[i].getHeight(null);
            anim6HitBoxes[i] = new Rectangle2D.Double (x, y, width, height);
        }

        return anim6HitBoxes;
	}


    public Rectangle2D.Double[] getAnim7FrameBounds() {
        for(int i=0; i < anim7Frames.length; i++){
            width = anim7Frames[i].getWidth(null);
            height = anim7Frames[i].getHeight(null);
            anim7HitBoxes[i] = new Rectangle2D.Double (x, y, width, height);
        }

        return anim7HitBoxes;
	}


    public Rectangle2D.Double[] getAnim8FrameBounds() {
        for(int i=0; i<anim8Frames.length; i++){
            width = anim8Frames[i].getWidth(null);
            height = anim8Frames[i].getHeight(null);
            anim8HitBoxes[i] = new Rectangle2D.Double (x, y, width, height);
        }

        return anim8HitBoxes;
	}

    public Animation getAnimation(){
        return animation;
    }

    public boolean isAttacking(){
        //ninja is in attacking stance when striking, chopping or flipping
        return animation == animation1 || animation == animation3 || animation == animation4 || animation == animation6 || animation == animation7 || animation == animation8;
    }

    public void killNinja(boolean killed){

        if(!dead){
            width = animation.getImage().getWidth(null);
            height = animation.getImage().getHeight(null);

            fall.translate(0, height); // Translate to position the sprite correctly on the screen
            fall.rotate(Math.toRadians(-90), x + width / 2, y + height / 2); // Rotate by -90 degrees (to fall backwards)

            dead = killed;
        }

    }


    public boolean isDead(){
        return dead;
    }


    public int getX(){
        return x;
    }


    public int getY(){
        return y;
    }

}

