import javax.swing.JPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;


public class GamePanel extends JPanel
		       implements Runnable {
   
	
	private SoundManager soundManager;
	private Ship ship;

	private boolean isRunning;
	private boolean isPaused;
	private Thread gameThread;
	private BufferedImage image;
	public Background background;
	public Backgroundd background2;

	private int level;
	private int width;
	private int height;

	private NinjaAnimationManager ninja;
	private PirateAnimationManager[] pirates;
	private LootManager loot1;
	private LootManager loot2;
	private MailPackage[] mailPackages;
	private Floor floor;
	private Mailman mailman;
	private Dog dog;


	private Random random;
	private ScorePanel scorePanel;


	public GamePanel (ScorePanel scorePanel) {
		isRunning = false;
		isPaused = false;
		soundManager = SoundManager.getInstance();

		//Sprite Declarations
		ship = null;

		//Panel Dimensions
		width = 900;
		height = 500;

		gameThread = null;
		level = 2;
	
		random = new Random();
		this.scorePanel = scorePanel;
        
		image = new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB);
	}


	public void createGameEntities() {
		background = new Background(this, "images/beach.jpg", 96);
		background2 = new Backgroundd(this, "images/Suburbs.jpeg", 110);

		ship = new Ship(this, 0, 0);
		ninja = new NinjaAnimationManager(this);
		
		loot1 = new LootManager(random.nextInt(0, 850), random.nextInt(350, 450), ninja);
		loot2 = new LootManager(random.nextInt(0, 850), random.nextInt(350, 450), ninja);

		mailPackages = new MailPackage[3];
		mailPackages[0] = new MailPackage(this, random.nextInt(100, 600), 350, ninja);
		mailPackages[1] = new MailPackage(this, random.nextInt(100, 600), 350, ninja);
		mailPackages[2] = new MailPackage(this, random.nextInt(100, 600), 350, ninja);

		pirates = new PirateAnimationManager[2];
		pirates[0] = new PirateAnimationManager(this, 90, 290, "right", ninja, loot1, mailPackages);
		pirates[1] = new PirateAnimationManager(this, random.nextInt(100, 450), random.nextInt(300, 400), "right", ninja, loot2, mailPackages);



		floor = new Floor(this);
		dog = new Dog();
		mailman = new Mailman(this,floor,dog);
	}


	public void run () {
		try {
			isRunning = true;
			while (isRunning) {
				if (!isPaused)
					gameUpdate();
				gameRender();
				scorePanel.ScoreRender();
				Thread.sleep (50);	
			}
		}
		catch(InterruptedException e) {}
	}


	public void gameUpdate() {
		ship.move();
		ninja.update();

		if(level == 1){
		for(int i=0; i < pirates.length; i++)
			pirates[i].update();
		}

		//for(int i=0; i < mailPackages.length; i++)
		//	mailPackages[i].update();

		if(level == 2){
			mailman.update();
			dog.update();
		}
	}


	public void updatePlayer (int direction) {

		if (isPaused)
			return;

		if(level == 1)
			ninja.move(direction);

		else if(level == 2){
				if (background != null && mailman != null ) {
				
					int mailManMovement = background2.move(direction);
					mailman.setDirections(mailManMovement);
					background2.setDirections(mailman.move(direction));
	
				}
				
			}
		
	}


	public void gameRender() {

		// draw the game objects on the image

		Graphics2D imageContext = (Graphics2D) image.getGraphics();

		if(level == 1){
			background.draw(imageContext);

			if (ship != null) {
				ship.draw(imageContext);
			}

			if(ninja != null){
			ninja.draw(imageContext);
			}

			for(int i=0; i < pirates.length; i++){
				if(pirates[i] != null){
					pirates[i].draw(imageContext);
				}
			}


			if(ninja.isDead()){
				for(int i=0; i < mailPackages.length; i++)
					mailPackages[i].draw(imageContext); //packages for pirates to steal
			}

			//end game when pirates steal all packages or ninja collects all loot
			if(allPackagesStolen())
				endGame();

			if(allLootCollected())
				endGame();
		}
		
		//~~~~~~~LEVEL TWO~~~~~~~~~~~~~~~~~
		else if(level == 2){
			background2.draw(imageContext);
			mailman.draw(imageContext);

			if (floor != null) {
				floor.draw(imageContext);
			}

			if (dog != null) {
				dog.draw (imageContext);
			}
		}
		
		

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


	public void startGame() {				// initialise and start the game thread 

		if (gameThread == null) {
			//soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new Thread (this);			
			gameThread.start();
		}

		if(level == 1){
			for(int i=0; i < pirates.length; i++){
				if(pirates[i] != null){
					pirates[i].start();
				}
			}
		}
		else if(level == 2){
			if (dog != null) {
				dog.start();
			}
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

		if(level == 1){
			for(int i=0; i < pirates.length; i++){
				if(pirates[i] != null){
					pirates[i].start();
				}
			}
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

    //checks that pirates stole all packages
	public boolean allPackagesStolen(){
		for(int i=0; i < mailPackages.length; i++){
			if(!mailPackages[i].collected())
				return false;
		}

		return true;
	}

    //checks that ninja collected all loot
	public boolean allLootCollected(){
		return loot1.collected() && loot2.collected();
	}


	public int getLevel(){
		return level;
	}
}
