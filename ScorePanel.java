import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ScorePanel extends JPanel {
    // Label Declaration
    private JLabel moneyLabel;
    private JLabel packagesLabel;
    private JLabel timerLabel;

    //TextField Declaration
    private JTextField moneyField;
    private JTextField timerField;


    private Color color;
    GridLayout gridLayout;
    private static int money;
    private static int packages;
    private int width;
    private int height;

    private BufferedImage coin;
    private BufferedImage delivered0;
    private BufferedImage delivered1;
    private BufferedImage delivered2;
    private BufferedImage delivered3;
    private BufferedImage image;

    public ScorePanel(){
        Initialize();
        createPanel();
    }


    public void Initialize(){
        money = 0;
        packages = 0;
        width = 100;
        height = 100;

        //Loading Images
        image = new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB);
        coin = ImageManager.loadBufferedImage("images/coin.png");
        delivered0 = ImageManager.loadBufferedImage("images/0delivered.png");
        delivered1 = ImageManager.loadBufferedImage("images/1delivered.png");
        delivered2 = ImageManager.loadBufferedImage("images/2delivered.png");
        delivered3 = ImageManager.loadBufferedImage("images/3delivered.png");

        //Initialize Labels
        moneyLabel = new JLabel("Bank Balance:");
        packagesLabel = new JLabel("Packages Collected:");
        timerLabel = new JLabel("Timer");

        //Intialize Textfield
        moneyField  = new JTextField(25);
        timerField = new JTextField(25);

        moneyField.setEditable(false);
        timerField.setEditable(false);

        color = new Color(186, 205, 146);
        moneyField.setBackground(color);

        moneyField.setText("0");
        timerField.setText("1:50");
    }

    public void createPanel(){
        gridLayout = new GridLayout(3,2);
        this.setLayout(gridLayout);
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
        g2.drawImage(coin,160,35,40,40, null );
        
        packagesLabel.setText("Undelivered Packages");
        if(packages == 0){
            g2.drawImage(delivered0,190,70,185,45, null );
        }
        else if(packages == 1){
            g2.drawImage(delivered1,193,70,185,45, null );
        }
        else if(packages == 2){
            g2.drawImage(delivered2,190,70,185,45, null );
        }
        else if(packages == 3){
            g2.drawImage(delivered3,190,70,185,45, null );
        }
        g.dispose();
    }

    

}
