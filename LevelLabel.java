import javax.swing.*;
import java.awt.*;

public class LevelLabel extends JLabel {
    public LevelLabel(){
        this.setLayout(null);
        this.setForeground(Color.red);
        this.setBounds(532,130,124,50);
        this.setFont(new Font("Times new roman",Font.BOLD,25));
        this.setText(" Level: 1");
        this.setBorder(BorderFactory.createLineBorder(Color.red));
        this.setBackground(Color.black);
        this.setForeground(Color.white);
        this.setOpaque(true);
        this.setVisible(true);
    }
}
