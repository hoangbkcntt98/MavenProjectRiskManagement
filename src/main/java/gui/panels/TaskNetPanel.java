package gui.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import edu.uci.ics.jung.visualization.VertexShapeFactory;
import gui.jung.config.PopupGraphMousePlugin;
import model.Project;
import model.task.Task;
import utils.Utils;

public class TaskNetPanel extends NetPanel {
	private List<Task> tasks;
	private Map<Task, Vertex> vertexTaskMap;
	private Map<Vertex, Task> taskVertexMap;
	private Project pj;
	Vertex[] vertex;
	protected NumberVertexValue voltages = new UserDatumNumberVertexValue("order");

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
		pr.setVertexShapeFunction(new MyVertexShapeFunction());
		pr.setVertexStrokeFunction(new ConstantVertexStrokeFunction());
		
		pr.setEdgePaintFunction(new MyEdgePaintFunction());
		pr.setEdgeStrokeFunction(new MyEdgeWeightStrokeFunction());
		vv.setToolTipFunction(new MyTips());
		
	}
	@Override
	public void setEdgePaintFunction() {
		// TODO Auto-generated method stub
		super.setEdgePaintFunction();
//		pr.setEdgePaintFunction(new MyEdgePaintFunction());
	}
	boolean isBlessed( Edge e ) {
		Vertex v1= (Vertex) e.getEndpoints().getFirst()	;
		Vertex v2= (Vertex) e.getEndpoints().getSecond() ;
		Task start = getTaskByVertex(v1);
		Task end = getTaskByVertex(v2);
		Task[] path = pj.getCriticalPath().toArray(new Task[0]);
		for(int i=0;i<path.length-1;i++) {
			if(path[i]==start && path[i+1]==end) return true;
		}
		return false;
    }
	    
	public class MyEdgePaintFunction extends AbstractEdgePaintFunction {

		/**
		 * @see edu.uci.ics.jung.graph.decorators.EdgePaintFunction#getDrawPaint(edu.uci.ics.jung.graph.Edge)
		 */
		
		public Paint getDrawPaint(Edge e) {
			if(isBlessed(e)) {
				return Color.red;
			}
			return Color.black;
		}
	}


	/**
	 * @author danyelf
	 */
	public class MyVertexPaintFunction implements VertexPaintFunction{

		private Project pj;

		public MyVertexPaintFunction(Project pj) {
			this.pj = pj;
		}

		public Paint getDrawPaint(Vertex v) {
			return Color.black;
		}

		public Paint getFillPaint(Vertex v) {
			if (pj.getCriticalPath().contains(taskVertexMap.get(v))) {
				if (v.getOutEdges().size() == 0)
					return Color.red;
				if (v.getInEdges().size() == 0)
					return Color.green;
				return Color.white;
			}

			return Color.gray;
		}

	}
	
	private final class MyVertexShapeFunction extends AbstractVertexShapeFunction implements VertexSizeFunction, VertexAspectRatioFunction{
		
		public MyVertexShapeFunction() {
			setSizeFunction(this);
            setAspectRatioFunction(this);
		}
		@Override
		public Shape getShape(Vertex v) {
			if(v.getOutEdges().size()==0||v.getInEdges().size()==0) return factory.getRegularPolygon(v, 5);
			return factory.getEllipse(v);
		}

		@Override
		public float getAspectRatio(Vertex v) {
			return 1.0f;
		}

		@Override
		public int getSize(Vertex v) {
			// TODO Auto-generated method stub
			return 30;
		}
	
		
	}

	 public class MyTips extends DefaultToolTipFunction {
	        
	        public String getToolTipText(Vertex v) {
	           Task t = getTaskByVertex(v);
	           String message = "<html>" +
	           "Name: "+ t.getName()
	           + "<br>Probability :" + Utils.round(t.getProb())
	           + "<br>Duration :" + Utils.round(t.getMostlikely());
	           String predecessor = "<br> Parent: ";
	           String successor = "<br> Child: ";
	           if(t.getPredecessor()!=null) {
	        	   for(Task t1: t.getPredecessor()) {
		        	   predecessor= predecessor+" "+t1.getName();
		           }
	           }
	           if(t.getSuccessor()!=null) {
	        	   for(Task t1: t.getSuccessor()) {
		        	   successor= successor+" "+t1.getName();
		           }
	           }
	           message = message + predecessor + successor+"</html>";
	           return message;
	        }
	        public String getToolTipText(Edge e) {
	        	String message = "";
	        	Vertex v1= (Vertex) e.getEndpoints().getFirst()	;
				Vertex v2= (Vertex) e.getEndpoints().getSecond() ;
				message = "("+getTaskByVertex(v1).getName()+","+getTaskByVertex(v2).getName()+")";
	            return message;
	        }
	    }
	 public class ConstantVertexStrokeFunction implements VertexStrokeFunction
	 {
	     protected Stroke heavy;
	     protected Stroke light;
	     
	     public ConstantVertexStrokeFunction()
	     {
	         this.heavy = new BasicStroke(5);
	         this.light = new BasicStroke(1);
	     }

	    
	     /**
	      * @see edu.uci.ics.jung.graph.decorators.VertexStrokeFunction#getStroke(edu.uci.ics.jung.graph.Vertex)
	      */
	     public Stroke getStroke(Vertex v)
	     {
	    	 if(getTaskByVertex(v).getSlack()==0) {
	    		 if(v.getOutEdges().size()==0||v.getInEdges().size()==0) return heavy;
	    		 return new BasicStroke(3);
	    	 }
	    	 
	         return light;
	     }

	 }
	private class MyEdgeWeightStrokeFunction
	    implements EdgeStrokeFunction
	    {
	        protected Stroke basic = new BasicStroke(1);
	        protected Stroke heavy = new BasicStroke(2);
	        protected Stroke dotted = PluggableRenderer.DOTTED;
	        
	        protected boolean weighted = false;
	        protected NumberEdgeValue edge_weight;
	        
	        public MyEdgeWeightStrokeFunction()
	        {
	            
	        }
	        
	        public Stroke getStroke(Edge e)
	        {
	            if (isBlessed(e))
	            {
	               return heavy;
	            }
	            else
	                return dotted;
	        }
	  
	    }
}
