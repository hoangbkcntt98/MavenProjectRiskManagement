package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.VertexPaintFunction;
import edu.uci.ics.jung.graph.decorators.VertexStringer;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.SparseVertex;
import model.Project;
import model.task.Task;

public class TaskNetPanel extends NetPanel {
	private List<Task> tasks;
	private Map<Task, Vertex> vertexTaskMap;
	private Map<Vertex, Task> taskVertexMap;
	private Project pj;
	Vertex[] vertex;

	/**
	 * Launch the application.
	 */

	public TaskNetPanel(Project pj) {
		super();
		this.pj = pj;
		this.tasks = pj.getTasks();
//		setTitle("Task Net");
		drawGraph();

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
		return v;
	}
	@Override
	public void setLocationVertex() {
		// TODO Auto-generated method stub
		super.setLocationVertex();
//		System.out.println(graph.getVertices().toString());
//		
//		System.out.println(vertex.length);
//		System.out.println(vertexLocations.getLocation(vertex[0]));
//		
//		vertexLocations.setLocation(vertex[0], new Point2D.Double(0,0));
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
	public void setVertexPaintFunction() {
		// TODO Auto-generated method stub
		super.setVertexPaintFunction();
		pr.setVertexPaintFunction(new MyVertexPaintFunction(pj));
	}

	/**
	 * @author danyelf
	 */
	public class MyVertexPaintFunction implements VertexPaintFunction {

		private Project pj;

		public MyVertexPaintFunction(Project pj) {
			this.pj = pj;
		}

		public Paint getDrawPaint(Vertex v) {
			return Color.black;
		}

		public Paint getFillPaint(Vertex v) {
			if (pj.getCriticalPath().contains(taskVertexMap.get(v))) {
				if(v.getOutEdges().size()==0) return Color.red;
				if(v.getInEdges().size()==0) return Color.green;
				return Color.white;
			}

			return Color.gray;
		}

	}

}
