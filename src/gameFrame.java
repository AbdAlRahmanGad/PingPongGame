import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gameFrame extends JFrame implements ActionListener {
    private JButton myButton = new JButton("Play Again");
    private int WIDTH = 400;
    private int HEIGHT = 300;

    private JRadioButton darkMode;
    private JRadioButton lightMode;
    private Frame game = new Frame();
    private JLabel scrLabel;
    private JLabel empty1;
    private JLabel empty2;
    private JLabel empty3;
    JLabel scoreLabel;

    gameFrame(){
        scoreLabel =  new JLabel();
        this.setTitle("Main Menu");
        this.setSize(WIDTH,HEIGHT);
        myButton.setSize(WIDTH,50 );
        darkMode = new JRadioButton("Dark Mode");
        lightMode = new JRadioButton("Light Mode");
        scrLabel = new JLabel(" Screen Mode");
        empty1 = new JLabel("");
        empty2 = new JLabel("");
        empty3 = new JLabel("");
        ButtonGroup mode = new ButtonGroup();
        mode.add(darkMode);
        mode.add(lightMode);

        myButton.addActionListener(this);
        darkMode.setSelected(true);


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
            this.setLayout(new GridLayout(2,1));

        }else{
            this.setLayout(new GridLayout(3,1));
            this.add(empty1).setVisible(false);
            this.add(scoreLabel);
            this.add(empty2).setVisible(false);
        }
        this.setVisible(true);
        this.add(scrLabel);
        this.add(darkMode);
        this.add(lightMode);

        this.add(empty3).setVisible(false);
        this.add(myButton);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
        this.setVisible(false);
        int screenMode = darkMode.isSelected()?1:2;
        game.createPanel(screenMode);

    }
}
