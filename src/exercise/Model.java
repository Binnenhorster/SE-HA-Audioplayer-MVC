package exercise;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Model{
	private Clip clip;
	private int framePosition;
	private AudioInputStream audioInputStream;
	private File file;
	private ModelListener listener;
	
	
	
	public Model(ModelListener listener) {
		super();
		this.listener = listener;
	}
	public void openAudioInputStream(final File file) {
		Thread thread = new Thread(new Runnable(){
			public void run(){
				try {
					openAudioClip(file);
					listener.decodingDone();
				} catch (Exception e) {
					listener.decodingError(e.getMessage());
				}
			}
		});
		thread.start();
	}
	private void openAudioClip(File file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.file = file;
		audioInputStream = AudioSystem.getAudioInputStream(file);
		if (isEncoded()){
			AudioFormat baseFormat = audioInputStream.getFormat();
			AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
				baseFormat.getSampleRate(),
				16,
				baseFormat.getChannels(),
				baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(),
				false);
			audioInputStream = AudioSystem.getAudioInputStream(decodedFormat, audioInputStream);
		}
		framePosition = 0;
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);
	}


	public boolean isEncoded(){
		return audioInputStream.getFormat().getEncoding() != Encoding.PCM_SIGNED 
			&& audioInputStream.getFormat().getEncoding() != Encoding.PCM_UNSIGNED;
	}
		
	public void setLoopEnabled(boolean enabled){
		if (enabled == true){
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	public void start(){
		clip.setFramePosition(framePosition);
		clip.start();
	}
	public void stop(){
		clip.stop();
		framePosition = 0;
	}
	public void pause(){
		clip.stop();
		framePosition = clip.getFramePosition();
	}
	
	public AudioFormat getFormat() {
		return audioInputStream.getFormat();
	}
	public String getName(){
		return file.getName();
	}
}