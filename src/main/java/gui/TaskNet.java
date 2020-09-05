package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.DefaultToolTipFunction;
import edu.uci.ics.jung.graph.decorators.VertexStringer;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.SparseVertex;
import edu.uci.ics.jung.graph.impl.UndirectedSparseEdge;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;

import model.task.Task;

public class TaskNet extends BayesianNet {

	private JPanel contentPane;
	private List<Task> tasks;
	private Map<Task,Vertex> vertexTaskMap;
	private Map<Vertex,Task> taskVertexMap;

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskNet frame = new TaskNet(tasks);
					frame.setTitle("Task Net");
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
