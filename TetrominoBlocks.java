import java.awt.*;

public class TetrominoBlocks {
    private int shape[][];
    private Color color;
    private int x_axis;
    private int y_axis;
    private int shapes[][][];
    private int currentTetrominoRotation=0;

    public TetrominoBlocks(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
        initShapes();
    }

    public void initShapes(){
        shapes = new int[4][][];

        for (int i = 0; i < 4; i++) {
            int row = shape[0].length;
            int col = shape.length;
            shapes[i] = new int[row][col];

            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    shapes[i][r][c] = shape[col - c - 1][r];
                }
            }
            shape = shapes[i];
        }
    }

    public void spawn(int boardWidth){
        currentTetrominoRotation = 0;
        shape = shapes[currentTetrominoRotation];
        y_axis = -getHeight();
        x_axis = (boardWidth-getWidth())/2;
    }

    public int[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public int getHeight(){
        return shape.length;
    }
    public int getWidth(){
        return shape[0].length;
    }

    public int getX_axis() {
        return x_axis;
    }

    public int getY_axis() {
        return y_axis;
    }

    public void moveDownward(){
        y_axis++;
    }

    public void setX_axis(int x_axis) {
        this.x_axis = x_axis;
    }

    public void setY_axis(int y_axis) {
        this.y_axis = y_axis;
    }

    public void moveLeftward(){
        x_axis--;
    }

    public void moveRightward(){
        x_axis++;
    }

    public void rotate(){
        currentTetrominoRotation++;
        if(currentTetrominoRotation>3){
            currentTetrominoRotation = 0;
        }
        shape = shapes[currentTetrominoRotation];
    }

    public int getLeftEdgeOfGameArea(){
        return getX_axis();
    }

    public int getRightEdgeOfGameArea(){
        return x_axis+getWidth();
    }

    public int getBottomEdgeOfGameArea(){
        return y_axis + getHeight();
    }
}
