package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.VertexStringer;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.SparseVertex;
import model.risk.Risk;
import model.task.Task;

public class RiskNet extends BayesianNet{

	private JPanel contentPane;
	private List<Risk> risks;
	private Map<Risk,Vertex> vertexRiskMap;
	private Map<Vertex,Risk> riskVertexMap;

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RiskNet frame = new RiskNet(risks);
					frame.setTitle("Risk Model");
					frame.drawGraph();
					frame.setContent();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public RiskNet(List<Risk> risks) {
		super();
		this.risks = risks;
	}
	public Vertex getVertexByRisk(Risk risk) {
		return vertexRiskMap.get(risk);
	}
	public Risk getRiskByVertex(Vertex v) {
		return riskVertexMap.get(v);
	}
	@Override
	
	public void setMousePlugin() {
		PopupGraphMousePlugin plugin = new PopupGraphMousePlugin(null,riskVertexMap);
		plugin.setShowId(PopupGraphMousePlugin.SHOW_RISK_INFO);
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
				return "Risk "+getRiskByVertex(v1).getId();
			}
		});
	}
	@Override
	/**
	 * Create vertices base on tasks list info
	 * @return vertices
	 */
	
	public Vertex[] createVertices() {
		Vertex[] v = new Vertex[risks.size()];
		vertexRiskMap = new HashMap<Risk,Vertex>();
		riskVertexMap =  new HashMap<Vertex,Risk>();
		int i = 0;
		for(Risk t:risks) {
			v[i] = graph.addVertex(new SparseVertex());
			vertexRiskMap.put(t,v[i]);
			riskVertexMap.put(v[i],t);
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
		for(Risk t:risks) {
			if(t.getParentRisk()!=null)
			{
				for(Risk par: t.getParentRisk()) {
					if(par.getId()!=t.getId())
					graph.addEdge(new DirectedSparseEdge(getVertexByRisk(par), getVertexByRisk(t)));
				}
				
			}
			
		}
	}

	/**
	 * Create the frame.
	 */
	public RiskNet() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
