import javax.swing.*;
import java.awt.*;

public class ScoreLabel extends JLabel {
    public ScoreLabel(){
        this.setLayout(null);
        this.setForeground(Color.red);
        this.setBounds(532,50,122,50);
        this.setFont(new Font("Times new roman",Font.BOLD,25));
        this.setBorder(BorderFactory.createLineBorder(Color.red));
        this.setBackground(Color.black);
        this.setForeground(Color.white);
        this.setOpaque(true);
        this.setVisible(true);

    }
}
