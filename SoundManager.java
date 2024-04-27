// for playing sound clips
import javax.sound.sampled.*;
import java.io.*;
import java.util.HashMap;				// for storing sound clips

public class SoundManager {				// a Singleton class
	HashMap<String, Clip> clips;

	private static SoundManager instance = null;	// keeps track of Singleton instance

	private float volume;
	
	private SoundManager () {
		clips = new HashMap<String, Clip>();

		//Load Sounds 
        
        Clip clip = loadClip("sounds/dog.wav");
        clips.put("dog", clip);

        clip = loadClip("sounds/crow.wav");
        clips.put("crow", clip);

        clip = loadClip("sounds/delivered.wav");
        clips.put("delivered", clip);

        clip = loadClip("sounds/MI.wav");
        clips.put("background2", clip);


        clip = loadClip("sounds/win.wav");
        clips.put("win", clip);

		clip = loadClip("sounds/lose.wav");
        clips.put("lose", clip);

		clip = loadClip("sounds/battle-ship.wav"); // played during level 1
		clips.put("battle", clip);

		clip = loadClip("sounds/ninja-scream.wav"); // played when ninja or pirate dies
		clips.put("scream", clip);

		clip = loadClip("sounds/ninja-watah.wav");	// played when ninja flips
		clips.put("watah", clip);

		clip = loadClip("sounds/machete-swing.wav");	// played when sword is swung by ninja or pirate
		clips.put("swing", clip);

		clip = loadClip("sounds/knife-stab.wav");	// played when ninja uses his sword
		clips.put("stab", clip);

		clip = loadClip("sounds/treasure-coin.wav");	// played when ninja collects loot
		clips.put("coincollect", clip);

		clip = loadClip("sounds/small-metal-object-drop.wav");	// played when pirate drops loot
		clips.put("lootdrop", clip);

		clip = loadClip("sounds/pirate-arr.wav");	// played when pirate kills ninja
		clips.put("arr", clip);

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


		public void setVolume (String title, float volume) {
            Clip clip = getClip(title);
    
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
    
            gainControl.setValue(gain);
        }
}
