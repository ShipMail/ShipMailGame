import javax.swing.*;			// need this for GUI objects
import java.awt.*;			// need this for Layout Managers
import java.awt.event.*;		// need this to respond to GUI events
	
public class GameWindow extends JFrame implements ActionListener, KeyListener, MouseListener
{
	//Label Declaration
	private JLabel score;
	private JLabel lives;
	private JLabel mouseL;
	
	//TextField Declaration
	private JTextField scoreTF;
	private JTextField livesTF;
	private JTextField mouseTF;

	//Button Declaration
	private JButton startB;
	private JButton pauseB;
	private JButton endB;
	private JButton startNewB;
	private JButton focusB;
	private JButton exitB;

	
    //Panels
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel informationPanel;
    private GamePanel gamePanel;

	private Container window;
	public Color color;

	@SuppressWarnings({"unchecked"})
	public GameWindow() {
 
		setTitle ("Mail Ninja: Sea & Surburban ");
		setSize (1000, 750);
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		//Initialize Labels
		score = new JLabel ("Score: ");
		lives = new JLabel("Lives: ");
		mouseL = new JLabel("Location of Mouse Click: ");

		//Initialize TextFields
		scoreTF = new JTextField (25);
		scoreTF.setEditable(false);
		scoreTF.setBackground(Color.WHITE);

		livesTF = new JTextField (25);
		livesTF.setEditable(false);
		livesTF.setBackground(Color.WHITE);

		mouseTF = new JTextField (25);
		mouseTF.setEditable(false);
		mouseTF.setBackground(Color.WHITE);

		//Intialize Buttons
	        startB = new JButton ("Start Game");
	        pauseB = new JButton ("Pause Game");
	        endB = new JButton ("End Game");
			startNewB = new JButton ("Start New Game");
	        focusB = new JButton ("Focus");
			exitB = new JButton ("Exit");


				//Add Listeners to Buttons
					startB.addActionListener(this);
					pauseB.addActionListener(this);
					endB.addActionListener(this);
					startNewB.addActionListener(this);
					focusB.addActionListener(this);
					exitB.addActionListener(this);




		
	// ~~~~~~~~~~~~~~~ Creating Panels ~~~~~~~~~~~~~~~~~~~~

		mainPanel = new JPanel();
		FlowLayout flowLayout = new FlowLayout();
		mainPanel.setLayout(flowLayout);

		GridLayout gridLayout;

	//Information Panel
	informationPanel = new JPanel();
	gridLayout = new GridLayout(3, 2);
	informationPanel.setLayout(gridLayout);

	
			// Information Panel Components
			informationPanel.add (score);
			informationPanel.add (scoreTF);
	
			informationPanel.add (lives);
			informationPanel.add (livesTF);		
	
			informationPanel.add (mouseL);
			informationPanel.add (mouseTF);
	
	color = new Color(219, 169, 121);
	informationPanel.setBackground(color);


	//Game Panel
		gamePanel = new GamePanel();
		color = new Color(232, 239, 207);
		gamePanel.setBackground(color);
        gamePanel.setPreferredSize(new Dimension(900, 500));


		
	//Button Panel
	buttonPanel = new JPanel();
	gridLayout = new GridLayout(2, 3);
	buttonPanel.setLayout(gridLayout);

				// Button Panel Components
					buttonPanel.add (startB);
					buttonPanel.add (pauseB);
					buttonPanel.add (endB);
					buttonPanel.add (startNewB);
					buttonPanel.add (focusB);
					buttonPanel.add (exitB);

		
	//Decorate Main Panel
	mainPanel.add(informationPanel);
	mainPanel.add(gamePanel);
	mainPanel.add(buttonPanel);
	color = new Color(236, 202, 156);
	mainPanel.setBackground(color);

		//Add Action Listeners
		gamePanel.addMouseListener(this);
		mainPanel.addKeyListener(this);

	

	//Add Main Panel to Window Surface
	window = getContentPane();
	window.add(mainPanel);

	//Set Window Properties P2
	 setVisible(true);

		
	//Set Score-Bar message
		scoreTF.setText("0");
	}



	// implement single method in ActionListener interface

	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();
		scoreTF.setText(command + " button clicked.");

		if (command.equals(startB.getText())) {
			gamePanel.startGame();
		}

		if (command.equals(pauseB.getText())) {
			gamePanel.pauseGame();
			if (command.equals("Pause Game"))
				pauseB.setText ("Resume");
			else
				pauseB.setText ("Pause Game");

		}
		
		if (command.equals(endB.getText())) {
			gamePanel.endGame();
		}

		if (command.equals(startNewB.getText()))
			gamePanel.startNewGame();

		if (command.equals(focusB.getText()))
			//gamePanel.shootCat();

		if (command.equals(exitB.getText()))
			System.exit(0);

		mainPanel.requestFocus();
	}


	// implement methods in KeyListener interface

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		String keyText = e.getKeyText(keyCode);
		livesTF.setText(keyText + " pressed.");

		if(gamePanel.getLevel() == 1) {
			if (keyCode == KeyEvent.VK_LEFT) {
				gamePanel.updatePlayer (1);
			}
	
			if (keyCode == KeyEvent.VK_RIGHT) {
				gamePanel.updatePlayer (2);
			}
	
			if (keyCode == KeyEvent.VK_UP) {
				gamePanel.updatePlayer (3);
			}
	
			if (keyCode == KeyEvent.VK_DOWN) {
				gamePanel.updatePlayer (4);
			}
	
			if (keyCode == KeyEvent.VK_K) {
				gamePanel.updatePlayer (5);
			}
	
			if (keyCode == KeyEvent.VK_C) {
				gamePanel.updatePlayer (6);
			}
	
			if (keyCode == KeyEvent.VK_S) {
				gamePanel.updatePlayer (7);
			}
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}


	// implement methods in MouseListener interface

	public void mouseClicked(MouseEvent e) {

		int x = e.getX();
		int y = e.getY();

		mouseTF.setText("(" + x +", " + y + ")");

	}


	public void mouseEntered(MouseEvent e) {
	
	}

	public void mouseExited(MouseEvent e) {
	
	}

	public void mousePressed(MouseEvent e) {
	
	}

	public void mouseReleased(MouseEvent e) {
	
	}

}
