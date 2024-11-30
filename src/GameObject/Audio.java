/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameObject;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

    private String soundHit = "/res/sound/hit.wav";
    private String soundJump = "/res/sound/jump.wav";
    private String soundPoint = "/res/sound/point.wav";

    private void playSound(String sound) {

        // Try to load and play sound
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(sound));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf("Count not load %s.wav!\n", sound);
        }
    }

    /**
     * Public method for bird jump sound
     */
    public void jump() {
        playSound(soundJump);
    }

    /**
     * Public method for point sound
     */
    public void point() {
        playSound(soundPoint);
    }

    /**
     * Public method for collision/death sound
     */
    public void hit() {
        playSound(soundHit);
    }
}
