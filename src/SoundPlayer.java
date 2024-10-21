/**
 * SoundPlayer.java
 * @author: Gaël-Mehdi
 * @version: 1.0
 */


import javax.sound.sampled.*;
import java.io.*;

public class SoundPlayer {
    public void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}