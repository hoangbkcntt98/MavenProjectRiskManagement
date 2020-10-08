package gui.common;

import java.awt.Font;

import javax.swing.JLabel;

public class MyLabel extends JLabel{
	public MyLabel(String name,int fontSize) {
		super(name);
		setFont(new Font("Arial", Font.PLAIN, fontSize));
	}

}
