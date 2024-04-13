import javax.swing.JPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
   A component that displays all the game entities
*/

public class GamePanel extends JPanel
		       implements Runnable {
   
	private static int NUM_ALIENS = 3;
	//private SoundManager soundManager;
	private Ship ship;
	private NinjaRollAnimation ninjaRoll;

	private boolean isRunning;
	private boolean isPaused;

	private Thread gameThread;

	private BufferedImage image;

	public Background background;

	private int width;
	private int height;

	public GamePanel () {
		//ship = null;
		isRunning = false;
		isPaused = false;
		//soundManager = SoundManager.getInstance();
		width = 826;
		height = 465;


		image = new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB);
	}


	public void createGameEntities() {
		background = new Background(this, "images/ocean.png", 96);

		ship = new Ship(this, 10, 5);
		ninjaRoll = new NinjaRollAnimation();
		ninjaRoll.start();
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
		ninjaRoll.update();
	}


	public void updateBat (int direction) {

		if (isPaused)
			return;

		ninjaRoll.move(direction);

	}


	public void gameRender() {

		// draw the game objects on the image

		Graphics2D imageContext = (Graphics2D) image.getGraphics();

		background.draw(imageContext);

		if (ship != null) {
			ship.draw(imageContext);
		}


		if(ninjaRoll != null){
		   ninjaRoll.draw(imageContext);
		}

		Graphics2D g2 = (Graphics2D) getGraphics();	// get the graphics context for the panel
		g2.drawImage(image, 0, 0, width, height, null);

		imageContext.dispose();
		g2.dispose();
	}


	public void startGame() {				// initialise and start the game thread 

		if (gameThread == null) {
			//soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new Thread (this);			
			gameThread.start();
		}

	}


	public void startNewGame() {				// initialise and start a new game thread 

		isPaused = false;

		if (gameThread == null || !isRunning) {
			//soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new Thread (this);			
			gameThread.start();
		}
	}


	public void pauseGame() {				// pause the game (don't update game entities)
		if (isRunning) {
			if (isPaused)
				isPaused = false;
			else
				isPaused = true;
		}
	}


	public void endGame() {					// end the game thread
		isRunning = false;
		//soundManager.stopClip ("background");
	}
}