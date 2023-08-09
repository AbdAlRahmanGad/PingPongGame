import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
        this.setVisible(true);
        this.setResizable(false);
    }
    public void createPanel(int scrMode,int playerMode){
        GamePanel myPanel ;
        myPanel = new GamePanel(scrMode,playerMode);
        this.add(myPanel);
        this.pack();
    }
}
