package exercise;

import java.io.File;



public class Controller implements ModelListener{
	private Model model;
	private View view;
	public Controller(){
		view = new View(this);
		model = new Model(this);
		view.setPlayEnabled(false);
		view.setPauseEnabled(false);
		view.setStopEnabled(false);
		view.setOpenEnabled(true);
	}
	public Model getModel() {
		return model;
	}	
	public void start() {
		view.setOpenEnabled(false);
		view.setPauseEnabled(true);
		view.setStopEnabled(true);
		view.setPlayEnabled(false);
		model.start();
	}
	public void stop() {
		view.setOpenEnabled(true);
		view.setPauseEnabled(false);
		view.setPlayEnabled(true);
		view.setStopEnabled(false);
		model.stop();
	}
	public void pause() {
		view.setOpenEnabled(true);
		view.setStopEnabled(false);
		view.setPlayEnabled(true);
		view.setPauseEnabled(false);
		model.pause();
	}
	public void open(File file) {
		try {
			model.openAudioInputStream(file);
			view.setPlayEnabled(true);
			view.setStopEnabled(false);
			view.setPauseEnabled(false);
		} catch (Exception e) {
			view.showStatus(e.getMessage());
			view.setPlayEnabled(false);
			view.setStopEnabled(false);
			view.setPauseEnabled(false);
		}
		
	}
	@Override
	public void decodingDone() {
		view.showStatus(model.getName());
		view.showAudioFormat();
	}
	@Override
	public void decodingError(String message) {
		view.showStatus(message);
		view.setPlayEnabled(false);
	}
}
