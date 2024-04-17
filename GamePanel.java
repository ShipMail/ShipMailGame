import javax.swing.JPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
   A component that displays all the game entities
*/

public class GamePanel extends JPanel  implements Runnable {
   
	
	private SoundManager soundManager;
	private Ship ship;
	private NinjaRollAnimation ninjaRoll;

	private boolean isRunning;
	private boolean isPaused;
	private Thread gameThread;
	private BufferedImage image;
	public Background background;
	public Background background2;

	private int level;
	private int width;
	private int height;

	public GamePanel () {
		
		isRunning = false;
		isPaused = false;
		soundManager = SoundManager.getInstance();

		//Sprite Declarations
		ship = null;

		//Panel Dimensions
		width = 900;
		height = 500;


		gameThread = null;
		level = 1;
		image = new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB);
	}


	public void createGameEntities() {
		background = new Background(this, "images/ocean.png", 96);
		background2 = new Background(this, "images/Suburbs.jpeg", 110);
		ship = new Ship(this, 10, 5);
		/* 
		ninjaRoll = new NinjaRollAnimation();
		ninjaRoll.start();

		*/
	}


	public void run () {
		try {
			isRunning = true;
			while (isRunning) {
				if (!isPaused)
					gameUpdate();
				gameRender();
				Thread.sleep (50);	
			}
		}
		catch(InterruptedException e) {}
	}


	public void gameUpdate() {
		ship.move();
		//ninjaRoll.update();
	}


	public void updatePlayer (int direction) {

		/*if (isPaused)
			return;

		ninjaRoll.move(direction);*/

		if (background != null) {
			background.move(direction);
		}


	}


	public void gameRender() {

		//Draws Game objects
		Graphics2D imageContext = (Graphics2D) image.getGraphics();
		if(level == 1){
		background.draw(imageContext);
			if (ship != null) {
				ship.draw(imageContext);
			}
	}
		
		else if(level == 2){
			background2.draw(imageContext);
		}


		/* 
		if(ninjaRoll != null){
		   ninjaRoll.draw(imageContext);
		}*/

		Graphics2D g2 = (Graphics2D) getGraphics();	// get the graphics context for the panel
		g2.drawImage(image, 0, 0, width, height, null);

		imageContext.dispose();
		g2.dispose();
	}

	public void changelevel(){
		if(level == 1){
			level = 2;
		}
		else if(level == 2){
			level = 1;
		}
	}

	public void startGame() {				
		if (gameThread == null) {
			//soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new Thread (this);			
			gameThread.start();
		}
         
	}



	public void startNewGame() {			
		isPaused = false;
		if (gameThread == null || !isRunning) {
			//soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new Thread (this);			
			gameThread.start();
		}
	}


	public void pauseGame() {				
		if (isRunning) {
			if (isPaused)
				isPaused = false;
			else
				isPaused = true;
		}
	}


	public void endGame() {				
		isRunning = false;
		//soundManager.stopClip ("background");
	}
}