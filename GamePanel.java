import javax.swing.JPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends JPanel
		       implements Runnable {
   
	
	private SoundManager soundManager;
	private Ship ship;

	private boolean isRunning;
	private boolean isPaused;
	private Thread gameThread;
	private BufferedImage image;
	private BufferedImage winner;
	public Background background;
	public Backgroundd background2;
	

	private int level;
	private int width;
	private int height;
	private int withoutmail, withmail;

	private NinjaAnimationManager ninja;
	private PirateAnimationManager[] pirates;
	private LootManager loot1;
	private LootManager loot2;
	private MailPackage[] mailPackages;
	private Floor floor;
	private Mailman mailman;
	private Dog dog;
	private Crow crow;
	ArrayList <Mailbox> mailboxes;


	private Random random;
	private ScorePanel scorePanel;
	private static double countdown = 60000.0;
	private long startTime;
	private long elapsedTime;

	
	private double timeRemaining;
    int b;
	int temp;


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
		level = 1;
	
		random = new Random();
		this.scorePanel = scorePanel;
        
		image = new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB);
		winner = ImageManager.loadBufferedImage("images/Winner.png");

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
		pirates[0] = new PirateAnimationManager(this, 850, 320, 5, "left", ninja, loot1, mailPackages);
		pirates[1] = new PirateAnimationManager(this, 50, 320, 15, "right", ninja, loot2, mailPackages);



		floor = new Floor(this);
		dog = new Dog();
		crow = new Crow(this);
		mailman = new Mailman(this,floor,dog,crow);

		mailboxes = new ArrayList<>();
		//0 = No mail 1 = Mail
		mailboxes.add(new Mailbox(this, 0, background2,-220,mailman));
		mailboxes.add(new Mailbox(this,1,background2, 20,mailman));
		mailboxes.add(new Mailbox(this,1,background2, 310,mailman));
		mailboxes.add(new Mailbox(this, 0, background2,650,mailman));
		mailboxes.add(new Mailbox(this,1,background2, 950,mailman));
		mailboxes.add(new Mailbox(this, 0, background2,1250,mailman));

		withoutmail = 0; 
		withmail = 0;
		for(Mailbox mailbox: mailboxes){
			allocateMailBox(mailbox);
		}

	}


	public void run () {
		try {
			isRunning = true;
			while (isRunning) {
				if (!isPaused)
					gameUpdate();
				gameRender();

				if(level == 1){
					if(!ninja.isDead()){
					scorePanel.setNumPackagesCollected(pirates[0].getNumPackagesCollected());
					scorePanel.ScoreRender(); 
					}
				
				}
				
				if(level == 2){
					scorePanel.setNumPackagesCollected(pirates[0].getNumPackagesCollected()-withmail); //replace 0 with the number of packages delivered, which is initially 0
					scorePanel.ScoreRender(); 
				}
				
				if(countdown > 0 && !isPaused){
					elapsedTime = System.currentTimeMillis() - startTime;
					timeRemaining = (countdown - elapsedTime)/1000;
					System.out.println(timeRemaining);
					scorePanel.updateTimer(timeRemaining);
				}
				if (timeRemaining<= 0){
					soundManager.stopClip("battle");
					soundManager.playClip("lose",false);
					endGame();
				}

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

		if(level == 2){
			
			mailman.update();
			crow.update();
			boolean collisionC = mailman.collidesWithCrow();
			if(collisionC){
				soundManager.playClip("crow",false);
				scorePanel.update(4);
			}
			dog.update();
			boolean collision = mailman.collidesWithDog();
		 if(collision){
			soundManager.playClip("dog",false);
			mailman.setFall();
			scorePanel.update(1);
		 }

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
					if(mailman.getMailman() == 190){
						for(Mailbox mailbox: mailboxes){
							int temp =  mailbox.move(direction);
						}
					
				} 
					
					mailman.setDirections(mailManMovement);
					int h = mailman.move(direction);


					background2.setDirections(h);
					for(Mailbox mailbox: mailboxes){
						mailbox.setDirections(h);
					}
					
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

			//ninja can collect and save the packages to be shipped to mailman once all pirates are dead
			if(allPiratesDead()){
				for(int i=0; i < pirates.length; i++){
					pirates[i].collectPackages();
				}
			}

			for(int i=0; i < mailPackages.length; i++){
				if(mailPackages[i] != null){
					mailPackages[i].draw(imageContext);
				}
			}

			//end game when pirates kills ninja and steals all packages
			if(allPackagesStolen()){
			    soundManager.stopClip("battle");
			    soundManager.playClip("lose",false);
				endGame();
			}

		    //move to level 2 when ninja collects all packages (to be shipped to mailman) and loot
			if(allLootCollected() && allPackagesCollected())
				changelevel();
				if (dog != null) {
					dog.start();
				}
		}
		
		//~~~~~~~LEVEL TWO~~~~~~~~~~~~~~~~~
		else if(level == 2){
			if(scorePanel.getPackages() == 3){
				soundManager.playClip("win",false);
				endGame();
			}

			background2.draw(imageContext);
			crow.draw(imageContext);
			for(Mailbox mailbox: mailboxes){
				mailbox.draw(imageContext);
			}
			
			mailman.draw(imageContext);

			if (floor != null) {
				floor.draw(imageContext);
			}

			if (dog != null) {
				dog.draw(imageContext);
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
			startTime = System.currentTimeMillis(); //reset start time
			scorePanel.setLevel(level); 

			soundManager.stopClip("battle");
			soundManager.setVolume("background2", 0.7f);
		    soundManager.playClip("background2",true);
		}
		else if(level == 2){
			level = 1;
			startTime = System.currentTimeMillis();
			scorePanel.setLevel(level);
			
			soundManager.stopClip("background2");
			soundManager.setVolume("battle", 0.7f);
		    soundManager.playClip("battle",true);
		}
	}


	public void startGame() {				// initialise and start the game thread 

		if (gameThread == null) {
			createGameEntities();
			gameThread = new Thread (this);			
			gameThread.start();
			startTime = System.currentTimeMillis();
		}

		if(level == 1){
			soundManager.setVolume("battle", 0.7f);
			soundManager.playClip("battle", true);

			for(int i=0; i < pirates.length; i++){
				if(pirates[i] != null){
					pirates[i].start();
				}
			}
		}
		else if(level == 2){

			soundManager.stopClip("battle");

			soundManager.setVolume("background2", 0.7f);
			soundManager.playClip("background2",true);
			if (dog != null) {
				dog.start();
			}

			if (crow != null){
				crow.start();
			}
		
		}

	}


	public void startNewGame() {				// initialise and start a new game thread 

		isPaused = false;
		level = 1;
		scorePanel.resetPanel(0, level);

		if (gameThread == null || !isRunning) {
			createGameEntities();
			gameThread = new Thread (this);			
			gameThread.start();
			startTime = System.currentTimeMillis();
		}
        
		soundManager.setVolume("battle", 0.7f);
		soundManager.playClip("battle",true);
		if(level == 1){
			for(int i=0; i < pirates.length; i++){
				if(pirates[i] != null){
					pirates[i].start();
				}
			}
		}
		
		else if(level == 2){
			soundManager.setVolume("background2", 0.7f);
			soundManager.playClip("background2",true);
			if (dog != null) {
				dog.start();
			}
			if (crow != null){
				crow.start();
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
		soundManager.stopClip("battle");
		soundManager.stopClip("background2");
		isRunning = false;
	}


	
    //checks that pirates stole all packages
	public boolean allPackagesStolen(){
		for(int i=0; i < pirates.length; i++){
			if(!pirates[i].allPackagesStolen())
				return false;
		}

		return true;
	}

    //checks that ninja collected all loot
	public boolean allLootCollected(){
		return loot1.collected() && loot2.collected();
	}

	//checks that ninja collected all packages
	public boolean allPackagesCollected(){
        for(int i=0; i < pirates.length; i++){
			if(!pirates[i].allPackagesCollected())
			 return false;
		}
		return true;
	}

    //check that all pirates are dead before ninja tries to collect packages
	public boolean allPiratesDead(){ 

		for(int i=0; i < pirates.length; i++){
			if(!pirates[i].checkLife()){
				return false;
			}
		}
		return true;
	}


	public int getLevel(){
		return level;
	}


	public void allocateMailBox(Mailbox mailbox){
		int choice = (int)(Math.random() * 2); // 0 - 1

		if(choice == 0 && withoutmail != 3){
			withoutmail++;
		    mailbox.loadImage(choice);
		}
		else if(choice == 0 && withoutmail == 3){
			if(withmail < 3){
			withmail++;
			mailbox.loadImage(1);
			}
		}
		else if(choice == 1 && withmail != 3){
			withmail++;
			mailbox.loadImage(choice);
		}
		else if(choice == 1 && withmail == 3){
			if(withoutmail < 3){
				withoutmail++;
				mailbox.loadImage(0);
			}
		}
	}

	public void mailBoxCollisionUpdate(){
		for(Mailbox mailbox : mailboxes){
			boolean collision2 = mailbox.collidesWithMailman();
			if(collision2 && mailbox.getDeliveryStatus() == 1){
				mailbox.setDeliveryStatus(0);
				mailbox.loadImage(0);
				scorePanel.update(2);
				withmail--;
				System.out.println("Delivery Status: " + mailbox.getDeliveryStatus() + " Collsion");
				soundManager.playClip("delivered", false);
			}
		 }
	}


	public void updateScore(int i){
		scorePanel.update(i);
	}
}
