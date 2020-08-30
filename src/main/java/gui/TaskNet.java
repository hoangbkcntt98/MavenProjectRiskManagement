package gui;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.SparseVertex;
import edu.uci.ics.jung.graph.impl.UndirectedSparseEdge;
import model.task.Task;

public class TaskNet extends BayesianNet {

	private JPanel contentPane;
	private List<Task> tasks;
	private Map<Task,Vertex> vertexTaskMap;

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskNet frame = new TaskNet(tasks);
					frame.drawGraph();
					frame.setContent();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public TaskNet(List<Task> tasks) {
		super();
		this.tasks = tasks;
	}
	public Vertex getVertexFromTask(Task task) {
		return vertexTaskMap.get(task);
	}
	/**
	 * Create vertices base on tasks list info
	 * @return vertices
	 */
	@Override
	public Vertex[] createVertices() {
		Vertex[] v = new Vertex[tasks.size()];
		vertexTaskMap = new HashMap<Task,Vertex>();
		int i = 0;
		for(Task t:tasks) {
			v[i] = graph.addVertex(new SparseVertex());
			vertexTaskMap.put(t,v[i]);
			i++;	
		}
		return v;
	}
	@Override
	/**
	 * create edges for this demo graph
	 * 
	 * @param v an array of Vertices to connect
	 */
	public void createEdges(Vertex[] v) {
		for(Task t:tasks) {
			if(t.getPredecessor()!=null)
			{
				for(Task par: t.getPredecessor()) {
					
					graph.addEdge(new DirectedSparseEdge(getVertexFromTask(par), getVertexFromTask(t)));
				}
				
			}
			
		}
	}

}
