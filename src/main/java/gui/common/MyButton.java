package gui.common;

import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import gui.infomation.ProjectInformation;

public class MyButton extends JButton{
	
	public MyButton(String name,int fontSize) {
		super(name);
		setFont(new Font("Arial", Font.PLAIN, fontSize));
		setMargin(new Insets(0, 0, 0, 0));
	}

	public MyButton(String name, int fontSize, ImageIcon icon) {
		// TODO Auto-generated constructor stub
		super(name);
		setFont(new Font("Arial", Font.PLAIN, fontSize));
		setMargin(new Insets(0, 0, 0, 0));
		setIcon(icon);
	}
}
