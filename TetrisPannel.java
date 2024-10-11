
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class TetrisPannel extends JPanel {
    private Clip lineClearClip;
    private int boardRows;
    private int boardColumns;
    private int boardCellSize;
    private TetrominoBlocks tetrominoShapes;
    private Color backgroundArray[][];
    private TetrominoBlocks allTetrominoShapes[];
    
    public TetrisPannel(int columns){
        this.setBounds(0,0,500,700);
        this.setBackground(Color.black);
        boardColumns = columns;
        boardCellSize = this.getBounds().width/boardColumns;
        boardRows = this.getBounds().height/boardCellSize;
        backgroundArray = new Color[boardRows][boardColumns];


       allTetrominoShapes = new TetrominoBlocks[]{
                new TetrominoBlocks(new int[][]{{1,1,1}},new Color(0,255,255))
                ,new TetrominoBlocks(new int[][]{{0,1},{0,1},{1,1}}, Color.blue)
                ,new TetrominoBlocks(new int[][]{{1,0},{1,0},{1,1}}, new Color(255,165,0)),
                new TetrominoBlocks(new int[][]{{1,1},{1,1}}, new Color(255,255,0)) ,
                new TetrominoBlocks(new int[][]{{0,1,1},{1,1,0}}, Color.green),
                new TetrominoBlocks(new int[][]{{1,1,1},{0,1,0}}, new Color(255,0,255)),
                new TetrominoBlocks(new int[][]{{1,1,0},{0,1,1}}, Color.red)};
              accessLineClearSound();
    }
    private void accessLineClearSound() {
        try {
            File scoreSoundFile = new File("score.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(scoreSoundFile);
            lineClearClip = AudioSystem.getClip();
            lineClearClip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    private void playLineClearSound() {
        if (lineClearClip != null) {
            lineClearClip.setFramePosition(0);
            lineClearClip.start();
        }
    }

    public void spawnTetromino() {
        Random r = new Random();
        TetrominoBlocks previousTetromino = tetrominoShapes;
        TetrominoBlocks newTetromino;

        do {
            newTetromino = allTetrominoShapes[r.nextInt(allTetrominoShapes.length)];
        } while (newTetromino == previousTetromino);

        tetrominoShapes = newTetromino;
        tetrominoShapes.spawn(boardColumns);
    }

    public boolean TetrominoOutOfBoundChecker(){
        if(tetrominoShapes.getY_axis() < 0) {
            tetrominoShapes = null;
            return true;
        }
        return false;
    }

    public boolean moveTetrominoDown(){
        if( !bottomEndChecker() ) {
            return false;
        }
        tetrominoShapes.moveDownward();
        repaint();
        return true;
    }

    public void moveTetrominoRight(){
        if(tetrominoShapes == null){
            return;
        }
        if( !rightEndChecker() )
            return;
        tetrominoShapes.moveRightward();
        repaint();
    }

    public void moveTetrominoLeft(){
        if(tetrominoShapes == null){
            return;
        }
        if( !leftEndChecker() )
            return;
        tetrominoShapes.moveLeftward();
        repaint();
    }

    public void dropTetrominoInstantly(){
        if(tetrominoShapes == null){
            return;
        }
        while(bottomEndChecker()) {
            tetrominoShapes.moveDownward();
        }
        repaint();
    }

    public void rotateTetromino(){
        if(tetrominoShapes == null){
            return;
        }
        tetrominoShapes.rotate();

        if(tetrominoShapes.getLeftEdgeOfGameArea() < 0){
            tetrominoShapes.setX_axis(0);
        }
        if(tetrominoShapes.getRightEdgeOfGameArea() >= boardColumns){
            tetrominoShapes.setX_axis(boardColumns - tetrominoShapes.getWidth());
        }
        if(tetrominoShapes.getBottomEdgeOfGameArea() >= boardRows){
            tetrominoShapes.setY_axis(boardRows - tetrominoShapes.getHeight());
        }

        repaint();
    }

    public boolean bottomEndChecker() {
        if ((tetrominoShapes.getY_axis() + tetrominoShapes.getHeight()) == boardRows) {
            return false;
        }

        for (int col = 0; col < tetrominoShapes.getWidth(); col++) {
            for (int row = tetrominoShapes.getHeight() - 1; row >= 0; row--) {
                if (tetrominoShapes.getShape()[row][col] != 0) {
                    int x = col + tetrominoShapes.getX_axis();
                    int y = row + tetrominoShapes.getY_axis() + 1;

                    if (y < 0 || y >= backgroundArray.length || x < 0 || x >= backgroundArray[0].length)
                        break;

                    if (backgroundArray[y][x] != null)
                        return false;
                    break;
                }
            }
        }
        return true;
    }


    public boolean rightEndChecker() {
        if (tetrominoShapes.getRightEdgeOfGameArea() == boardColumns) {
            return false;
        }

        for (int row = 0; row < tetrominoShapes.getHeight(); row++) {
            for (int col = tetrominoShapes.getWidth() - 1; col >= 0; col--) {
                if (tetrominoShapes.getShape()[row][col] != 0) {
                    int x = col + tetrominoShapes.getX_axis() + 1;
                    int y = row + tetrominoShapes.getY_axis();

                    if (y < 0 || y >= backgroundArray.length || x < 0 || x >= backgroundArray[0].length)
                        break;

                    if (backgroundArray[y][x] != null)
                        return false;
                    break;
                }
            }
        }
        return true;
    }


    public boolean leftEndChecker() {
        if (tetrominoShapes.getLeftEdgeOfGameArea() == 0) {
            return false;
        }

        for (int row = 0; row < tetrominoShapes.getHeight(); row++) {
            for (int col = 0; col < tetrominoShapes.getWidth(); col++) {
                if (tetrominoShapes.getShape()[row][col] != 0) {
                    int x = col + tetrominoShapes.getX_axis() - 1;
                    int y = row + tetrominoShapes.getY_axis();

                    if (y < 0 || y >= backgroundArray.length || x < 0 || x >= backgroundArray[0].length)
                        break;

                    if (backgroundArray[y][x] != null)
                        return false;
                    break;
                }
            }
        }
        return true;
    }


    public void drawBoardSquare(Graphics x,Color color,int x_axis,int y_axis ){
        x.setColor(color);
        x.fillRect(x_axis,y_axis,boardCellSize,boardCellSize);
        x.setColor(Color.BLACK);
        x.fillRect(x_axis + 13,y_axis + 13,boardCellSize-26,boardCellSize-26);
        x.setColor(Color.BLACK);
        x.drawRect(x_axis,y_axis,boardCellSize,boardCellSize);
    }

    private void drawTetromino(Graphics x){
        for (int row = 0; row < tetrominoShapes.getHeight(); row++) {
            for (int column = 0; column < tetrominoShapes.getWidth(); column++) {

                int x_axis = (column+tetrominoShapes.getX_axis())*boardCellSize;
                int y_axis = (row+tetrominoShapes.getY_axis())*boardCellSize;

                if(tetrominoShapes.getShape()[row][column]==1){
                    drawBoardSquare(x,tetrominoShapes.getColor(),x_axis,y_axis);
                }
            }
        }
    }

    public void drawBackground(Graphics db){
        Color color;
        for (int row = 0; row < boardRows; row++) {
            for (int col = 0; col < boardColumns; col++) {
                color = backgroundArray[row][col];

                if(color != null){
                    int x = col*boardCellSize;
                    int y = row*boardCellSize;
                    drawBoardSquare(db,color,x,y);
                }
            }
        }
    }

    public void moveTetrominoToBackground(){
        int shape[][] = tetrominoShapes.getShape();
        int height = tetrominoShapes.getHeight();
        int width = tetrominoShapes.getWidth();
        int xPosition = tetrominoShapes.getX_axis();
        int yPosition = tetrominoShapes.getY_axis();
        Color color = tetrominoShapes.getColor();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (shape[row][col] == 1) {
                    int backgroundRow = row + yPosition;
                    int backgroundCol = col + xPosition;

                    if (backgroundRow >= 0 && backgroundRow < boardRows && backgroundCol >= 0 && backgroundCol < boardColumns) {
                        backgroundArray[backgroundRow][backgroundCol] = color;
                    }
                }
            }
        }    }

    public int clearCompleteHorizontalLines(){
        boolean filledLineCheck;
        int numberOfClearedLines = 0;

        for (int row = boardRows - 1; row >= 0 ; row--) {
            filledLineCheck = true;
            for (int col = 0; col < boardColumns; col++) {
                if(backgroundArray[row][col] == null) {
                    filledLineCheck = false;
                    break;
                }
            }

            if(filledLineCheck){
                numberOfClearedLines++;
                lineClearer(row);
                shiftLineDownward(row);
                lineClearer(0);
                row++;
            playLineClearSound();
                repaint();
            }
        }
        return numberOfClearedLines;
    }

    private void lineClearer(int row){
        for (int i = 0; i < boardColumns; i++) {
            backgroundArray[row][i] = null;
        }
    }


    private void shiftLineDownward(int r){
        for (int row = r; row > 0 ; row--) {
            for (int col = 0; col < boardColumns; col++) {
                backgroundArray[row][col] = backgroundArray[row - 1][col];
            }
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawTetromino(g);
    }
}


