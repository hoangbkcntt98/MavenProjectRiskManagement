package gui.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;
import java.awt.Font;

public class DescriptionPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	private static final GeneralPath thePolygon = new GeneralPath();
	public DescriptionPanel() {
		setBorder(new TitledBorder(null, "Description", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setValue(100);
		progressBar_1.setForeground(Color.red);
		progressBar_1.setBounds(21, 22, 63, 14);
		add(progressBar_1);

		JProgressBar progressBar_1_1 = new JProgressBar();
		progressBar_1_1.setValue(100);
		progressBar_1_1.setForeground(Color.yellow);
		progressBar_1_1.setBounds(94, 22, 63, 14);
		add(progressBar_1_1);

		JProgressBar progressBar_1_2 = new JProgressBar();
		progressBar_1_2.setValue(100);
		progressBar_1_2.setForeground(Color.green);
		progressBar_1_2.setBounds(167, 22, 63, 14);
		add(progressBar_1_2);

		JLabel lblNewLabel_1 = new JLabel("Low");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 9));
		lblNewLabel_1.setBounds(27, 40, 46, 14);
		add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Medium");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.ITALIC, 9));
		lblNewLabel_1_1.setBounds(104, 40, 46, 14);
		add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("High");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.ITALIC, 9));
		lblNewLabel_1_2.setBounds(172, 40, 46, 14);
		add(lblNewLabel_1_2);

		JLabel lblNewLabel = new JLabel("Normal");
		lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 9));
		lblNewLabel.setBounds(27, 85, 46, 14);
		add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("Critical");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.ITALIC, 9));
		lblNewLabel_2.setBounds(125, 85, 46, 14);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Start,End");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.ITALIC, 9));
		lblNewLabel_3.setBounds(76, 167, 69, 14);
		add(lblNewLabel_3);

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();

		// set the stroke of the copy, not the original
		Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2 }, 0);
		g2d.setStroke(dashed);
		g2d.drawLine(20, 80, 70, 80);
		Stroke basic = new BasicStroke(2);
		g2d.setStroke(basic);
		g2d.setColor(Color.red);
		g2d.drawLine(120, 80, 170, 80);
		
		g2d.setColor(Color.black);
		Polygon p = new Polygon();
	    for (int i = 0; i < 5; i++)
	      p.addPoint((int) (92 + 19 * Math.cos(i * 2 * Math.PI / 5)),
	          (int) (140 +  19* Math.sin(i * 2 * Math.PI / 5)));

	    g2d.drawPolygon(p);
	    g2d.setColor(Color.cyan);
	    g2d.fillPolygon(p);
		// gets rid of the copy
		g2d.dispose();

	}
}
