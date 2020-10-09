package gui.common;

public class ThreadSimple extends Thread {
	private String name;

	public ThreadSimple(String name) {
		this.name = name;
	}

	public void run() {
		MyProgress demo = new MyProgress();
		demo.showProgressBarDemo(name);

	}

}