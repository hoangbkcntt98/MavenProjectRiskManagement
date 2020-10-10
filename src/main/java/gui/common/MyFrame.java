package gui.common;

import javax.swing.JFrame;

public class MyFrame extends JFrame{
	public MyFrame() {
		super();
		setIconImage(IconGenerator.getRiskIcon(100, 100).getImage());
	}
	public MyFrame(String name) {
		super(name);
		setIconImage(IconGenerator.getRiskIcon(100, 100).getImage());
	}
}
