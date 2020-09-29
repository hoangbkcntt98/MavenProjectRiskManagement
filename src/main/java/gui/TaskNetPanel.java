package gui;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.VertexStringer;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.SparseVertex;
import model.task.Task;

public class TaskNetPanel extends NetPanel{
	private List<Task> tasks;
	private Map<Task,Vertex> vertexTaskMap;
	private Map<Vertex,Task> taskVertexMap;

	/**
	 * Launch the application.
	 */

	public TaskNetPanel(List<Task> tasks) {
		super();
		this.tasks = tasks;
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
		PopupGraphMousePlugin plugin = new PopupGraphMousePlugin(taskVertexMap,null);
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
	 * @return vertices
	 */
	
	public Vertex[] createVertices() {
		Vertex[] v = new Vertex[tasks.size()];
		vertexTaskMap = new HashMap<Task,Vertex>();
		taskVertexMap =  new HashMap<Vertex,Task>();
		int i = 0;
		for(Task t:tasks) {
			v[i] = graph.addVertex(new SparseVertex());
			vertexTaskMap.put(t,v[i]);
			taskVertexMap.put(v[i],t);
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
					
					graph.addEdge(new DirectedSparseEdge(getVertexByTask(par), getVertexByTask(t)));
				}
				
			}
			
		}
	}


}
