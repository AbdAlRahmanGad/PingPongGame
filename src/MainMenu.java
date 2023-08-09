import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements ActionListener {
    private JButton myButton = new JButton("Play Again");
    private int WIDTH = 400;
    private int HEIGHT = 300;

    private JRadioButton darkMode;
    private JRadioButton lightMode;
    private JRadioButton singlePlayer;
    private JRadioButton multiPlayer;
    private GameFrame game = new GameFrame();
    private JLabel scrLabel;
    private JLabel playerModeLabel;
    private JLabel empty1;
    private JLabel empty2;
    private JLabel empty3;
    JLabel scoreLabel;

    MainMenu(){
        game.setVisible(false);
        scoreLabel =  new JLabel();
        this.setTitle("Main Menu");
        this.setSize(WIDTH,HEIGHT);
        myButton.setSize(WIDTH,50 );
        darkMode = new JRadioButton("Dark Mode");
        lightMode = new JRadioButton("Light Mode");
        scrLabel = new JLabel(" Screen Mode");
        playerModeLabel = new JLabel("Player Mode");
        singlePlayer = new JRadioButton("1 player");
        multiPlayer = new JRadioButton("2 players");
        empty1 = new JLabel("");
        empty2 = new JLabel("");
        empty3 = new JLabel("");
        ButtonGroup mode = new ButtonGroup();
        ButtonGroup playerMode = new ButtonGroup();
        mode.add(darkMode);
        mode.add(lightMode);
        playerMode.add(singlePlayer);
        playerMode.add(multiPlayer);

        myButton.addActionListener(this);
        darkMode.setSelected(true);
        singlePlayer.setSelected(true);


        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    public void makeFrame(int winner){
        if(winner == 1 )
            scoreLabel.setText("Winner Is Player 1");
        else
            scoreLabel.setText("Winner Is Player 2");

        scoreLabel.setForeground(Color.BLACK);
        if(winner == -1){
            myButton.setText("Play");
            this.setLayout(new GridLayout(3,1));

        }else{
            this.setLayout(new GridLayout(4,1));
            this.add(empty1,1).setVisible(false);
            this.add(scoreLabel,2);
            this.add(empty2,3).setVisible(false);
        }
        this.setVisible(true);
        this.add(scrLabel);
        this.add(darkMode);
        this.add(lightMode);
        this.add(playerModeLabel);
        this.add(singlePlayer);
        this.add(multiPlayer);
        this.add(empty3).setVisible(false);
        this.add(myButton);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
        this.setVisible(false);
        game.setVisible(true);
        int screenMode = darkMode.isSelected()?1:2;
        int playerMode = singlePlayer.isSelected()?1:2;
        game.createPanel(screenMode,playerMode);

    }
}
