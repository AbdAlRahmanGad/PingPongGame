import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
        this.setVisible(true);
        this.setResizable(false);
    }
    public void createPanel(int scrMode){
        GamePanel myPanel ;
        myPanel = new GamePanel(scrMode);
        this.add(myPanel);
        this.pack();
    }
}
