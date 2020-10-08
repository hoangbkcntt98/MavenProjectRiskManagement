package gui.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.collections.Predicate;

import algorithms.pert.Pert;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;
import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.AbstractEdgePaintFunction;
import edu.uci.ics.jung.graph.decorators.AbstractVertexShapeFunction;
import edu.uci.ics.jung.graph.decorators.DefaultToolTipFunction;
import edu.uci.ics.jung.graph.decorators.EdgeStrokeFunction;
import edu.uci.ics.jung.graph.decorators.NumberEdgeValue;
import edu.uci.ics.jung.graph.decorators.NumberVertexValue;
import edu.uci.ics.jung.graph.decorators.UserDatumNumberVertexValue;
import edu.uci.ics.jung.graph.decorators.VertexAspectRatioFunction;
import edu.uci.ics.jung.graph.decorators.VertexPaintFunction;
import edu.uci.ics.jung.graph.decorators.VertexShapeFunction;
import edu.uci.ics.jung.graph.decorators.VertexSizeFunction;
import edu.uci.ics.jung.graph.decorators.VertexStringer;
import edu.uci.ics.jung.graph.decorators.VertexStrokeFunction;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.SparseVertex;
import edu.uci.ics.jung.visualization.PluggableRenderer;
import edu.uci.ics.jung.visualization.SettableVertexLocationFunction;
import edu.uci.ics.jung.visualization.VertexLocationFunction;
import edu.uci.ics.jung.visualization.VertexShapeFactory;
import gui.jung.config.PopupGraphMousePlugin;
import gui.panels.NetPanel.RandomVertexLocationDecorator;
import model.Project;
import model.dimension.Dimension;
import model.task.Task;
import utils.Utils;

public class TaskNetPanel extends NetPanel {
	private List<Task> tasks;
	private Map<Task, Vertex> vertexTaskMap;
	private Map<Vertex, Task> taskVertexMap;
	private Project pj;
	private Vertex[] vertex;
	private VertexDisplayPredicate show_vertex;

	/**
	 * Launch the application.
	 */

	public TaskNetPanel(Project pj) {
		super();
		this.pj = pj;
		this.tasks = pj.getTasks();
		

		
		String probStr = Utils.round(pj.getProb()*100);
		String critPathStr = "";
		double totalTimeP =0;
		for(Task t:pj.getCriticalPath()) {
			critPathStr = critPathStr+t.getName()+" -->";
			totalTimeP+=t.getExpectedTime();
			
		}
		String projectName = "Project";
		if(pj instanceof Dimension) {
			Dimension d = (Dimension) pj;
			projectName = "Dimension "+d.getName();
			
			
		}
		JLabel projectLabel = new JLabel(projectName+" probability: " +probStr+"% ,");
		projectLabel.setFont(new Font("Arial", Font.ITALIC, 17));
		JLabel duration = new JLabel("Duration: " + Utils.round(totalTimeP));
		duration.setFont(new Font("Arial", Font.ITALIC, 17));
		drawGraph();

		vv.add(projectLabel);
		vv.add(duration);
		vv.revalidate();
		vv.repaint();
//		setVisible(true);
	}

	public Vertex getVertexByTask(Task task) {
		return vertexTaskMap.get(task);
	}

	public Task getTaskByVertex(Vertex v) {
		return taskVertexMap.get(v);
	}

	@Override

	public void setMousePlugin() {
		super.setMousePlugin();
		PopupGraphMousePlugin plugin = new PopupGraphMousePlugin(taskVertexMap, null);
		plugin.setShowId(PopupGraphMousePlugin.SHOW_TASK_INFO);
		graphMouse.add(plugin);
	}

	@Override
	/**
	 * Set label for vertex
	 */
	public void setVertexLable() {
		pr.setVertexStringer(new VertexStringer() {
			public String getLabel(ArchetypeVertex v) {
				Vertex v1 = (Vertex) v;
				return getTaskByVertex(v1).getName();
			}
		});
	}

	@Override
	/**
	 * Create vertices base on tasks list info
	 * 
	 * @return vertices
	 */

	public Vertex[] createVertices() {
		Vertex[] v = new Vertex[tasks.size()];
		vertexTaskMap = new HashMap<Task, Vertex>();
		taskVertexMap = new HashMap<Vertex, Task>();
		int i = 0;
		for (Task t : tasks) {
			v[i] = graph.addVertex(new SparseVertex());

			vertexTaskMap.put(t, v[i]);
			taskVertexMap.put(v[i], t);
			i++;
		}
//		vertexLocations.setLocation(v[0],new  Point2D.Double());
		return v;
	}

	@Override
	/**
	 * create edges for this demo graph
	 * 
	 * @param v an array of Vertices to connect
	 */
	public void createEdges(Vertex[] v) {
		for (Task t : tasks) {
			if (t.getPredecessor() != null) {
				for (Task par : t.getPredecessor()) {

					graph.addEdge(new DirectedSparseEdge(getVertexByTask(par), getVertexByTask(t)));
				}

			}

		}
	}

	@Override
	public void setConfigJung() {
		pr.setVertexPaintFunction(new MyVertexPaintFunction(pj));
		pr.setVertexShapeFunction(new MyVertexShapeFunction());
		pr.setVertexStrokeFunction(new MyVertexStrokeFunction());

		pr.setEdgePaintFunction(new MyEdgePaintFunction());
		pr.setEdgeStrokeFunction(new MyEdgeWeightStrokeFunction());
		show_vertex = new VertexDisplayPredicate(false, 0, taskVertexMap);
		pr.setVertexIncludePredicate(show_vertex);
		vv.setToolTipFunction(new MyTips());

	}

	@Override
	public void setLayout() {
		java.awt.Dimension d = new java.awt.Dimension(1000,600);
		layout.initialize(new java.awt.Dimension(700, 600), new MyVertexLocationFunction(d));

	}

	boolean isBlessed(Edge e) {
		Vertex v1 = (Vertex) e.getEndpoints().getFirst();
		Vertex v2 = (Vertex) e.getEndpoints().getSecond();
		Task start = getTaskByVertex(v1);
		Task end = getTaskByVertex(v2);

		List<Task> critPath = Pert.excute(pj.getTasks());
		Task[] path = critPath.toArray(new Task[0]);
		for (int i = 0; i < path.length - 1; i++) {
			if (path[i] == start && path[i + 1] == end)
				return true;
		}
		return false;
	}

	public class MyEdgePaintFunction extends AbstractEdgePaintFunction {

		/**
		 * @see edu.uci.ics.jung.graph.decorators.EdgePaintFunction#getDrawPaint(edu.uci.ics.jung.graph.Edge)
		 */

		public Paint getDrawPaint(Edge e) {
			if (isBlessed(e)) {
				return Color.red;
			}
			return Color.black;
		}
	}

	public class MyVertexPaintFunction implements VertexPaintFunction {

		private Project pj;

		public MyVertexPaintFunction(Project pj) {
			this.pj = pj;
		}

		public Paint getDrawPaint(Vertex v) {
			return Color.black;
		}

		public Paint getFillPaint(Vertex v) {
			return Color.cyan;
		}

	}

	private final class MyVertexShapeFunction extends AbstractVertexShapeFunction
			implements VertexSizeFunction, VertexAspectRatioFunction {

		public MyVertexShapeFunction() {
			setSizeFunction(this);
			setAspectRatioFunction(this);
		}

		@Override
		public Shape getShape(Vertex v) {
			if (v.getOutEdges().size() == 0 || v.getInEdges().size() == 0)
				return factory.getRegularPolygon(v, 5);
			return factory.getEllipse(v);
		}

		@Override
		public float getAspectRatio(Vertex v) {
			return 1.0f;
		}

		@Override
		public int getSize(Vertex v) {
			// TODO Auto-generated method stub
			return 40;
		}

	}

	public class MyTips extends DefaultToolTipFunction {

		public String getToolTipText(Vertex v) {
			Task t = getTaskByVertex(v);
			String message = "<html>" + "Name: " + t.getName() + "<br>Probability :" + Utils.round(t.getProb())
					+ "<br>Duration :" + Utils.round(t.getMostlikely());
			String predecessor = "<br> Parent: ";
			String successor = "<br> Child: ";
			if (t.getPredecessor() != null) {
				for (Task t1 : t.getPredecessor()) {
					predecessor = predecessor + " " + t1.getName();
				}
			}
			if (t.getSuccessor() != null) {
				for (Task t1 : t.getSuccessor()) {
					successor = successor + " " + t1.getName();
				}
			}
			message = message + predecessor + successor + "</html>";
			return message;
		}

		public String getToolTipText(Edge e) {
			String message = "";
			Vertex v1 = (Vertex) e.getEndpoints().getFirst();
			Vertex v2 = (Vertex) e.getEndpoints().getSecond();
			message = "(" + getTaskByVertex(v1).getName() + "," + getTaskByVertex(v2).getName() + ")";
			return message;
		}
	}

	public class MyVertexStrokeFunction implements VertexStrokeFunction {
		protected Stroke heavy;
		protected Stroke light;
		protected Stroke dotted = PluggableRenderer.DOTTED;

		public MyVertexStrokeFunction() {
			this.heavy = new BasicStroke(3);
			this.light = new BasicStroke(1);
		}

		/**
		 * @see edu.uci.ics.jung.graph.decorators.VertexStrokeFunction#getStroke(edu.uci.ics.jung.graph.Vertex)
		 */
		public Stroke getStroke(Vertex v) {
			if (getTaskByVertex(v).getSlack() == 0) {
				if (getTaskByVertex(v).getSlack() == 0)
					return heavy;
				return new BasicStroke(1);
			}

			return dotted;
		}

	}

	private class MyEdgeWeightStrokeFunction implements EdgeStrokeFunction {
		protected Stroke basic = new BasicStroke(1);
		protected Stroke heavy = new BasicStroke(2);
		protected Stroke dotted = PluggableRenderer.DOTTED;

		protected boolean weighted = false;
		protected NumberEdgeValue edge_weight;

		public MyEdgeWeightStrokeFunction() {

		}

		public Stroke getStroke(Edge e) {
			if (isBlessed(e)) {
				return heavy;
			} else
				return dotted;
		}

	}

	public void showVertex(boolean isSet, int opt) {
		show_vertex.showCritical(isSet, opt);
	}

	public class DefaultSettableVertexLocationFunction implements SettableVertexLocationFunction {
		protected Map v_locations;
		protected boolean normalized;

		public DefaultSettableVertexLocationFunction() {
			v_locations = new HashMap();
		}

		public DefaultSettableVertexLocationFunction(VertexLocationFunction vlf) {
			v_locations = new HashMap();
			for (Iterator iterator = vlf.getVertexIterator(); iterator.hasNext();) {
				ArchetypeVertex v = (ArchetypeVertex) iterator.next();
				System.out.println(vlf.getLocation(v));
				v_locations.put(v, vlf.getLocation(v));
			}
		}

		public void setLocation(ArchetypeVertex v, Point2D location) {
			v_locations.put(v, location);
		}

		public Point2D getLocation(ArchetypeVertex v) {
			return (Point2D) v_locations.get(v);
		}

		public void reset() {
			v_locations.clear();
		}

		public Iterator getVertexIterator() {
			return v_locations.keySet().iterator();
		}
	}

	public class MyVertexLocationFunction implements VertexLocationFunction {
		Map v_locations = new HashMap();
		java.awt.Dimension dim;
		RandomEngine rand;

		public MyVertexLocationFunction(java.awt.Dimension d) {
			this.dim = d;
			this.rand = new DRand((int) (new Date().getTime()));
		}

		public void reset() {
			v_locations.clear();
		}

		public Point2D getLocation(ArchetypeVertex v) {
			Point2D location = (Point2D) v_locations.get(v);
			
			if (location == null) {
				Vertex v1 = (Vertex) v;
				if (getTaskByVertex(v1).getName().equals("B")) {
					location = new Point2D.Double(200+220.5, 441);
				}
				if (getTaskByVertex(v1).getName().equals("C")) {
					location = new Point2D.Double(200+371.0, 437.5);
				}
				if (getTaskByVertex(v1).getName().equals("D")) {
					location = new Point2D.Double(200+220.5, 161.0);
				}
				if (getTaskByVertex(v1).getName().equals("E")) {
					location = new Point2D.Double(200+260.0, 298.66666);
				}
				if (getTaskByVertex(v1).getName().equals("F")) {
					location = new Point2D.Double(200+364.0, 161.0);
				}
				if (getTaskByVertex(v1).getName().equals("G")) {
					location = new Point2D.Double(200+435.16666, 360.5);
				}
				if (getTaskByVertex(v1).getName().equals("H")) {
					location = new Point2D.Double(200+521.5, 295.16666);
				}
				if (getTaskByVertex(v1).getName().equals("I")) {
					location = new Point2D.Double(200+646.3333, 165.66667);
				}
				if (getTaskByVertex(v1).getName().equals("J")) {
					location = new Point2D.Double(200+654.5, 297.5);
				}

				if (v1.getOutEdges().size() == 0) {
					location = new Point2D.Double(dim.width, dim.height / 2);
				}
				if (v1.getInEdges().size() == 0) {
					location = new Point2D.Double(200+100, dim.height / 2);
				}
				v_locations.put(v, location);
			}
			return location;
		}

		public Iterator getVertexIterator() {
			return v_locations.keySet().iterator();
		}

	}

	private final static class VertexDisplayPredicate implements Predicate {
		protected boolean critical;
		protected int optional;
		protected final static int HIGH = 1;
		protected final static int MEDIUM = 2;
		protected final static int LOW = 3;
		protected Map<Vertex, Task> taskVertexMap;

		public VertexDisplayPredicate(boolean filter, int optional, Map<Vertex, Task> taskVertexMap) {
			this.taskVertexMap = taskVertexMap;

			this.critical = filter;
			this.optional = optional;
		}

		public void showCritical(boolean b, int opt) {
			optional = opt;
			critical = b;
		}

		public boolean evaluate(Object arg0) {

			Vertex v = (Vertex) arg0;
			Task task = taskVertexMap.get(v);
			if (critical) {
				if (optional == HIGH) {
					return (task.getSlack() == 0 && task.getProb() >= 0.7);
				}
				if (optional == MEDIUM) {
					return (task.getSlack() == 0 && task.getProb() >= 0.4 && task.getProb() < 0.7);
				}
				if (optional == LOW) {
					return (task.getSlack() == 0 && task.getProb() < 0.4);
				}
				return (taskVertexMap.get(v).getSlack() == 0);
			} else {
				if (optional == HIGH) {
					return (task.getProb() >= 0.7);
				}
				if (optional == MEDIUM) {
					return (task.getProb() >= 0.4 && task.getProb() < 0.7);
				}
				if (optional == LOW) {
					return (task.getProb() < 0.4);
				}
				return true;
			}

		}
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
			p.addPoint((int) (0 + 19 * Math.cos(i * 2 * Math.PI / 5)), (int) (0 + 19 * Math.sin(i * 2 * Math.PI / 5)));

		g2d.drawPolygon(p);
		g2d.setColor(Color.cyan);
		g2d.fillPolygon(p);
		// gets rid of the copy
		g2d.dispose();

	}

}
