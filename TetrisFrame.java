import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TetrisFrame extends JFrame {
    TetrisPannel tp;
    ScoreLabel score = new ScoreLabel();
    LevelLabel level = new LevelLabel();
    private boolean isTetrisFrameClosed = false;
    private TetrisGameThread gameThread;

    public TetrisFrame() {
        ImageIcon tetrisImage = new ImageIcon("tetris.png");
        this.setSize(700, 740);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Tetris");
        tp = new TetrisPannel(10);
        this.add(tp);
        this.getContentPane().setBackground(new Color(54, 69, 79));
        score.setText(" Score: 0");
        this.add(score);
        this.add(level);

        Button mainMenu = new Button("Main Menu");
        mainMenu.setFont(new Font("Times new Romans", Font.BOLD, 25));
        mainMenu.setBounds(518, 350, 150, 50);
        mainMenu.setBackground(Color.BLUE);
        mainMenu.setForeground(Color.white);
        this.add(mainMenu);
        mainMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mainMenu.setBackground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mainMenu.setBackground(Color.BLUE);
            }
        });
        mainMenu.addActionListener(e -> {
            isTetrisFrameClosed = true;
            dispose();
        });

        this.setIconImage(tetrisImage.getImage());
        userControls();
        startGame();
    }
    public TetrisFrame(int x, int y){
        ImageIcon tetrisImage=new ImageIcon("tetris.png");
        this.setSize(x,y);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Tetris");

        JButton start=new JButton("Start");
        JButton quit= new JButton("Quit");
        JButton leaderBoard= new JButton("Leader Board");

        leaderBoard.setBackground(new Color(40,42,120));
        leaderBoard.setForeground(Color.red);
        leaderBoard.setFocusPainted(false);
        leaderBoard.setBounds(177,260,125,30);

        quit.setBackground(new Color(40,42,120));
        quit.setForeground(Color.GREEN);
        quit.setFocusPainted(false);
        quit.setBounds(210,225,65,30);

        start.setBackground(new Color(40,42,120));
        start.setForeground(Color.MAGENTA);
        start.setFocusPainted(false);

        leaderBoard.addActionListener(e -> {
            Leaderboard lb= new Leaderboard();

        });

        start.setBounds(210,190,65,30);

        quit.addActionListener(e -> dispose());

        start.addActionListener(e -> {
            TetrisFrame tt= new TetrisFrame();
            tt.setVisible(true);
        });
        this.setIconImage(tetrisImage.getImage());
        this.setContentPane(new JLabel(tetrisImage));
        this.add(quit);
        this.add(start);
        this.add(leaderBoard);
        this.setVisible(true);
    }
    public boolean isTetrisFrameClosed() {
        return isTetrisFrameClosed;
    }

    public void startGame() {
        gameThread = new TetrisGameThread(tp, this);
        gameThread.start();
    }

    public void updateScore(int playerScore) {
        score.setText(" Score: " + playerScore);
    }

    public void updateLevel(int playerLevel) {
        level.setText(" Level: " + playerLevel);
    }

    public void userControls() {
        InputMap inputMap = this.getRootPane().getInputMap();
        ActionMap actionMap = this.getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "left");
        inputMap.put(KeyStroke.getKeyStroke("UP"), "up");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "down");

        actionMap.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tp.moveTetrominoRight();
            }
        });
        actionMap.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tp.moveTetrominoLeft();
            }
        });
        actionMap.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tp.rotateTetromino();
            }
        });
        actionMap.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tp.dropTetrominoInstantly();
            }
        });
    }
}
