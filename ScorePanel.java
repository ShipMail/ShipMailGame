import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ScorePanel extends JPanel {
    // Label Declaration
    private JLabel levelLabel;
    private JLabel moneyLabel;
    private JLabel packagesLabel;
    private JLabel timerLabel;

    //TextField Declaration
    private JTextField levelField;
    private JTextField moneyField;
    private JTextField timerField;


    private Color color;
    GridLayout gridLayout;
    private String moneyS;
    private String timerS;

    private  double money;
    private  int packages;
    private  double timer;
    private int width;
    private int height;
    private int level;

    private BufferedImage coin;
    private BufferedImage collected1;
    private BufferedImage collected2;
    private BufferedImage collected3;

    private BufferedImage delivered0;
    private BufferedImage delivered1;
    private BufferedImage delivered2;
    private BufferedImage delivered3;
    private BufferedImage image;
    Color timerFieldBg;

    public ScorePanel(int level){
        this.level = level;
        Initialize();
        createPanel();
    }


    public void Initialize(){
        money = 0;
        packages = 0;
        timer = 1.50;
        width = 100;
        height = 105;

        //!!!!!!!!!!CHANGE TO SUIT LEVEL TESTING!!!!!!!!!!///
        level = 1;

        //Loading Images
        image = new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB);
        coin = ImageManager.loadBufferedImage("images/coin.png");
        collected1 = ImageManager.loadBufferedImage("images/1collected.png");
        collected2 = ImageManager.loadBufferedImage("images/2collected.png");
        collected3 = ImageManager.loadBufferedImage("images/3collected.png");

        delivered0 = ImageManager.loadBufferedImage("images/0delivered.png");
        delivered1 = ImageManager.loadBufferedImage("images/1delivered.png");
        delivered2 = ImageManager.loadBufferedImage("images/2delivered.png");
        delivered3 = ImageManager.loadBufferedImage("images/3delivered.png");

        //Initialize Labels
        levelLabel = new JLabel("Level:");
        moneyLabel = new JLabel("Bank Balance:");
        packagesLabel = new JLabel("Packages Collected:");
        timerLabel = new JLabel("Count Down");

        //Intialize Textfield
        levelField = new JTextField(5);
        moneyField  = new JTextField(25);
        timerField = new JTextField(25);

        levelField.setEditable(false);
        moneyField.setEditable(false);
        timerField.setEditable(false);

        color = new Color(186, 205, 146);
        moneyField.setBackground(color);

        levelField.setText(level + "");
        moneyField.setText("0");
        timerField.setText("READY?");
        timerFieldBg = timerField.getBackground();
    }

    public void createPanel(){
        gridLayout = new GridLayout(4,2);
        this.setLayout(gridLayout);
        this.add(levelLabel);
        this.add(levelField);
        this.add(timerLabel);
        this.add(timerField);
        this.add(moneyLabel);
        this.add(moneyField);
        this.add(packagesLabel);

        //Paint Panel
        color = new Color(219, 169, 121);
        this.setBackground(color);

    }

    public void ScoreRender(){
        draw();
    }

    public void draw(){

        Graphics g = getGraphics ();
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(coin,160,48,40,40, null );

        //draw the number of packages ninja collected to score panel
        if(level == 1){
            levelField.setText(level + "");

            if(packages == 1){
                g2.drawImage(collected1,195,81,90,20, null);
            }
            else if(packages == 2){
                g2.drawImage(collected2,195,77,178,28, null);
            }
            else if(packages == 3){
                g2.drawImage(collected3,195,77,185,35, null);
            }
            else{
                Rectangle2D.Double rect = new Rectangle2D.Double(192, 82, 185, 45);
                g2.setColor(color);
                g2.fill(rect);
            }
        }
        
        if(level == 2){
            packagesLabel.setText("Undelivered Packages");
            levelField.setText(level + "");

            //erase previous image
            Rectangle2D.Double rect = new Rectangle2D.Double(190, 82, 185, 45);
            g2.setColor(color);
            g2.fill(rect);

            if(packages == 0){
                g2.drawImage(delivered0,195,78,185,35, null );
            }
            else if(packages == 1){
                g2.drawImage(delivered1,198,78,185,35, null );
            }
            else if(packages == 2){
                g2.drawImage(delivered2,195,80,185,33, null );
            }
            else if(packages == 3){
                g2.drawImage(delivered3,195,80,185,33, null );
            }
        }
        g.dispose();
    }

    public void update(int i){
        //if the dog causes the mailman to damage the package (Dog collodes with Mailman)
        if(i == 1){
            money = money - 20.50;
            moneyS = String.valueOf(money);
            moneyField.setText(moneyS);
        }
       
        //if the package is delivered (Mailman collides with mailbox and "D is selected")
        else if(i == 2){
            money = money + 500;
            moneyS = String.valueOf(money);
            moneyField.setText(moneyS);
            if(packages <3){
                packages++;
            }
        }
          //if the package is collected (Ninja collides with the package)
        else if(i == 3){
            money = money + 2000;
            moneyS = String.valueOf(money);
            moneyField.setText(moneyS);
            if(packages <3){
                packages++;
            }
        }

        //if the crow causes the mailman to damage the package (Crow collides with Mailman)
        else if(i == 4){
            money = money - 10.50;
            moneyS = String.valueOf(money);
            moneyField.setText(moneyS);
        }
    }

    public void updateTimer(double time){
        if(time > 0.00){
           timerField.setBackground(timerFieldBg); //reset colour of timer text field when new game starts
           double min = (time / 60);
           double secs = (min - (int)min)*60;

           if(secs < 9.5)
                timerS = (int)min + ":0" + String.format("%.0f", secs);
           else
                timerS = (int)min + ":" + String.format("%.0f", secs);

           timerField.setText(timerS);
        }else {
            timerField.setBackground(Color.RED);
            timerField.setText("TIME'S UP");
        }
    }

    public void setL2Panel(int numPackages){
        level = 2;

        packages = numPackages;


        timer = 1.50;
        timerS = String.valueOf(timer);
        timerField.setText(timerS);
    }
    
    public void resetPanel(int numPackages, int level){
        money = 0;
        moneyS = String.valueOf(money);
        moneyField.setText(moneyS);

        setLevel(level);
        setNumPackagesCollected(numPackages);


        //timer = 1.50;
        //timerS = String.valueOf(timer);
        timerField.setText("GET DELIVERING");
    }

    public void setNumPackagesCollected(int numPackagesCollected){
        this.packages = numPackagesCollected;
    }

    public void setNumPackagesDelivered(int numPackagesDelivered){
        this.packages = numPackagesDelivered;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public int getPackages(){
        return packages;
    }

}

