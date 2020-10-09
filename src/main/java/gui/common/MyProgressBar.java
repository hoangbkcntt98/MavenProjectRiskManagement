package gui.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JProgressBar;


public class MyProgressBar extends JProgressBar{

    private static final long serialVersionUID = 1L;
    private boolean isStringToBePainted = false;
    private int fontSize;
    private double prob;
    public MyProgressBar(int i, int j,int fontSize) {
    	
    	super(i,j);
    	this.fontSize = fontSize;
		// TODO Auto-generated constructor stub
	}

	public MyProgressBar(int i, int j, int k, double prob) {
		// TODO Auto-generated constructor stub
		super(i,j);
		this.fontSize=k;
		this.prob = prob;
		if (prob < 0.4) {
			setForeground(Color.red);
		} else if (prob >= 0.4 && prob < 0.7) {
			setForeground(Color.yellow);
		} else {
			setForeground(Color.green);
		}
	}

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(isStringToBePainted ){
            java.awt.Dimension size = MyProgressBar.this.getSize();
            int x = (int)( size.width/2-fontSize);
            int height = g.getFontMetrics().getHeight();
            int d = g.getFontMetrics().getDescent();
            int y = (size.height + height)/2-d;
            String text = getString();
            g.setColor(Color.BLACK );
            g.setFont(new Font("Arial",Font.PLAIN,fontSize));
            g.drawString(text, x, fontSize);
        }
    }

    @Override
    public void setStringPainted(boolean b) {
        // don't do super.setStringPainted(b);
        //super.setStringPainted(b);
        isStringToBePainted=b;
    }
}