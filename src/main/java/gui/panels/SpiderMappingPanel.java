package gui.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import model.Project;
import model.dimension.Dimension;

public class SpiderMappingPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	private Project pj;
	private List<Dimension> dimensionList;
	public SpiderMappingPanel() {
		this.pj = pj;
		setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Sprider Mapping",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		
		setLayout(null);

	}

	public SpiderMappingPanel(List<Dimension> dimensionList) {
		// TODO Auto-generated constructor stub
		super();
		this.dimensionList = dimensionList;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke basic = new BasicStroke(2);
		Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2 }, 0);
		
		g2d.setColor(Color.black);

		Point[] points = new Point[10];
		int r = 170;
		for (int j = 5; j > 0; j--) {
			Polygon p = new Polygon();
			for (int i = 0; i < 10; i++) {
				g2d.setStroke(dashed);
				int x = (int) (250 / 2 +65+ 17 * 2 * j * Math.cos(i * 2 * Math.PI / 10));
				int y = (int) (40 + 150 + 17 * 2 * j * Math.sin(i * 2 * Math.PI / 10));
				p.addPoint(x, y);
				if (j == 5) {
					g2d.setStroke(dashed);
					g2d.drawString(dimensionList.get(0).getTasks().get(i).getName(),
							(int) (250 / 2 +65+ 18 * 2 * j * Math.cos(i * 2 * Math.PI / 10)) - 3,
							(int) (40 + 150 + 18 * 2 * j * Math.sin(i * 2 * Math.PI / 10) + 5));
//					g2d.drawString(dimensionList.get(0).getTasks().get(i).getName(),
//							(int) (250 / 2 +65+ 18 * 2 * j * Math.cos(i * 2 * Math.PI / 10)) - 3,
//							(int) (40 + 150 + 18 * 2 * j * Math.sin(i * 2 * Math.PI / 10) + 5));
					points[i] = new Point(x, y);
					g2d.drawOval(x-2, y-2, 4, 4);
				}

			}
			g2d.drawPolygon(p);

		}
		// Tam O(x0,y0)
		int x0 =(int) (points[0].x+points[5].x)/2;
		int y0 = (int) (points[0].y+points[5].y)/2;
		// draw Prob
		System.out.println("Dimension Size:"+ dimensionList.size());
		double[][] probList = new double[5][10];
		for (int i = 0; i < 5; i++) {// num of dimension
			for (int j = 0; j < 10; j++) { // num of task
				probList[i][j] = dimensionList.get(i).getTasks().get(j).getProb()*100;
				System.out.println("Prob[" + i + "][" + j + "] :" + probList[i][j]);
			}
		}
		
		Point[][] pTask = new Point[5][10];
		for (int i = 0; i < 5; i++) {// num of dimension
			for (int j = 0; j < 10; j++) { // num of task
				double tempX = 0;
				double tempY = 0;
				double deta = probList[i][j] / 100;
				tempX = x0-(x0-points[j].x)*deta;
				tempY = y0 -(y0-points[j].y)*deta;
				if (i == 4)
				{
					System.out.println("prob:"+ (double)probList[i][j]/100);
					System.out.println("deta:"+deta);
					System.out.println("temp(x,y):(" + tempX + "," + tempY + ")");
				}
					
				pTask[i][j] = new Point((int)tempX, (int)tempY);
			}
		}
		

		
		g2d.setStroke(new BasicStroke(2));
		// draw spider network
		for(int j=0;j<5;j++) {
		    if(j==0) g2d.setColor(Color.blue);
		    if(j==1) g2d.setColor(Color.red);
		    if(j==2) g2d.setColor(Color.green);
		    if(j==3) g2d.setColor(Color.yellow);
		    if(j==4) g2d.setColor(Color.cyan);
			for (int i = 0; i < 10; i++) {
				g2d.drawOval(pTask[j][i].x-2, pTask[j][i].y-2,4,4);
				if(i<9)
				{
					g2d.drawLine(pTask[j][i].x,pTask[j][i].y, pTask[j][i+1].x, pTask[j][i+1].y);
				}else{
					g2d.drawLine(pTask[j][i].x,pTask[j][i].y, pTask[j][0].x, pTask[j][0].y);
				}
			}
		}
		// gets rid of the copy
		
		g2d.setStroke(new BasicStroke(2));

		g2d.setColor(Color.black);
		for (int i = 0; i < 5; i++) {
			g2d.drawLine(points[i].x, points[i].y, points[i + 5].x, points[i + 5].y);
		}

		g2d.dispose();

	}

}
