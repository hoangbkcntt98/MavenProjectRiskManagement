package gui.common;

import java.awt.Image;

import javax.swing.ImageIcon;

import config.Configuaration;

public abstract class IconGenerator{
	private static ImageIcon icon ;
	
	public static ImageIcon getIcon(String path,int width,int height) {
		icon = new ImageIcon(path);
		Image image = icon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		icon =  new ImageIcon(newimg);
		return icon;
	}
	public static ImageIcon getRiskIcon(int width,int height) {
		return getIcon(Configuaration.imagesPath+"icon.jpg", width, height);
	}
	public static ImageIcon getExcel(int width,int height) {
		return getIcon(Configuaration.imagesPath+"excel.png", width, height);
	}
	public static ImageIcon getImportIcon(int width,int height) {
		return getIcon(Configuaration.imagesPath+"import.png", width, height);
	}
}
