package swinglib;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * A class to play audio clips.
 * @author milind
 */
public class Audio {

  private final Clip clip;

  /**
   * Creates an audio object with the given file.
   * @param path Path to audio file
   */
  public Audio(String path) {
    clip = getClip(path);
  }

  private Clip getClip(String path) {
    Clip c = null;
    try {
      c = AudioSystem.getClip();
      AudioInputStream audio = AudioSystem.getAudioInputStream(new File(path));
      c.open(audio);
    } catch (LineUnavailableException | IOException
        | UnsupportedAudioFileException e) {
      e.printStackTrace();
    }
    return c;
  }

  /**
   * Plays the audio clip. 
   */
  public void play() {
    clip.start();
    clip.setFramePosition(0);
  }

}
