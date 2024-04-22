import javax.sound.sampled.AudioInputStream;		// for playing sound clips
import javax.sound.sampled.*;
import java.io.*;
import java.util.HashMap;				// for storing sound clips

public class SoundManager {				// a Singleton class
	HashMap<String, Clip> clips;

	private static SoundManager instance = null;	// keeps track of Singleton instance

	private float volume;

	//https://pixabay.com/sound-effects/search/ninja/
	//https://www.voicy.network/search/knife-sound-effects
	//https://www.tunepocket.com/fighting-sound-effects/
	
	private SoundManager () {
		clips = new HashMap<String, Clip>();

		Clip clip = loadClip("sounds/ninja-scream.wav");	
		clips.put("ninjascream", clip);

		clip = loadClip("sounds/ninja-watah.wav");	//
		clips.put("watah", clip);

		clip = loadClip("sounds/machete-swing.wav");	// played
		clips.put("swing", clip);

		clip = loadClip("sounds/knife-stab.wav");	// played when
		clips.put("stab", clip);

		volume = 1.0f;
	}


	public static SoundManager getInstance() {	// class method to retrieve instance of Singleton
		if (instance == null)
			instance = new SoundManager();
		
		return instance;
	}		


    	public Clip loadClip (String fileName) {	// gets clip from the specified file
 		AudioInputStream audioIn;
		Clip clip = null;

		try {
    			File file = new File(fileName);
    			audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL()); 
    			clip = AudioSystem.getClip();
    			clip.open(audioIn);
		}
		catch (Exception e) {
 			System.out.println ("Error opening sound files: " + e);
		}
    		return clip;
    	}


	public Clip getClip (String title) {

		return clips.get(title);
	}


    	public void playClip(String title, boolean looping) {
		Clip clip = getClip(title);
		if (clip != null) {
			clip.setFramePosition(0);
			if (looping)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			else
				clip.start();
		}
    	}


    	public void stopClip(String title) {
		Clip clip = getClip(title);
		if (clip != null) {
			clip.stop();
		}
    	}

}
