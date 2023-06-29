import javax.swing.*;

public class Frame extends JFrame {
    Frame(){
        this.setVisible(true);
        this.setResizable(false);
    }
    public void createPanel(int scrMode){
        Panel myPanel ;
        myPanel = new Panel(scrMode);
        this.add(myPanel);
        this.pack();
    }
}
