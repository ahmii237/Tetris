import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TetrisGameThread extends Thread {

    LocalDateTime dateAndTime= LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy   HH:mm");
    String formattedDateTime = dateAndTime.format(format);
    private TetrisPannel tp;
    TetrisFrame tf;
    private int playerScore;
    private int playerLevel = 1;
    private int scorePerLevel = 5;
    private int currentGameSpeed = 700;
    private int speedup = 50;
   private File playerData;
    public TetrisGameThread(TetrisPannel tp, TetrisFrame tf) {
        this.tp = tp;
        this.tf = tf;
        playerData= new File("playerData.txt");
    }

    @Override
    public void run() {
        while (true) {

            if (tf.isTetrisFrameClosed()) {
                break;
            }
                tp.spawnTetromino();
            while (tp.moveTetrominoDown()) {
                try {
                    Thread.sleep(currentGameSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


            if (tp.TetrominoOutOfBoundChecker()) {
                try {

                    File gameOverSound = new File("gameoverrr.wav");
                    Clip soundclip = AudioSystem.getClip();
                    soundclip.open(AudioSystem.getAudioInputStream(gameOverSound));

                    File gameOverSoundEffect = new File("ga.wav");
                    Clip effectClip = AudioSystem.getClip();
                    effectClip.open(AudioSystem.getAudioInputStream(gameOverSoundEffect));
                    effectClip.start();
                    soundclip.start();
                } catch (LineUnavailableException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedAudioFileException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {

                    String s = JOptionPane.showInputDialog(null,
                            "Game Over!!!\nPlease Enter Your Name",
                            "Bss kar Bhai kitna khelay ga!!!", JOptionPane.PLAIN_MESSAGE);
                    savePlayerData(s, playerScore, dateAndTime);
                    break;
                }
            }


            tp.moveTetrominoToBackground();
            playerScore += tp.clearCompleteHorizontalLines();
            tf.updateScore(playerScore);
            int level = playerScore / scorePerLevel + 1;
            if (level > playerLevel) {
                playerLevel = level;
                tf.updateLevel(playerLevel);
                currentGameSpeed -= speedup;
                if (currentGameSpeed < 150) {
                    currentGameSpeed = 150;
                }
            }

    }
    }

    private void savePlayerData(String playerName, int score,LocalDateTime dateAndTime) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(playerData, true))) {
            writer.write(playerName + "," + score +","+formattedDateTime);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            tf.dispose();
        }
    }
}
