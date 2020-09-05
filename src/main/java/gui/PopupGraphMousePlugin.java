package gui;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;

import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.visualization.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import model.risk.Risk;
import model.task.Task;

public class PopupGraphMousePlugin extends AbstractPopupGraphMousePlugin implements MouseListener {
	Map<Vertex,Task> taskVertexMap;
	Map<Vertex,Risk> riskVertexMap;
	private int showId ;
	public static final int SHOW_TASK_INFO = 1;
	public static final int SHOW_RISK_INFO = 2;
	
	public PopupGraphMousePlugin(Map<Vertex,Task> taskVertexMap,Map<Vertex,Risk> riskVertexMap) {
		this(MouseEvent.BUTTON3_MASK);
		this.taskVertexMap = taskVertexMap;
		this.riskVertexMap = riskVertexMap;
	}
	public void setShowId(int id) {
		this.showId = id;
	}
	public PopupGraphMousePlugin() {
		this(MouseEvent.BUTTON3_MASK);
	}

	public PopupGraphMousePlugin(int modifiers) {
		super(modifiers);
	}
	public void showInformation(Vertex pickV) {
		if(showId == SHOW_TASK_INFO) {
			TaskInfomation taskInfo = new TaskInfomation(taskVertexMap.get(pickV));
			taskInfo.run();
		}
		if(showId == SHOW_RISK_INFO) {
			RiskInformation riskInfo = new RiskInformation(riskVertexMap.get(pickV));
			riskInfo.run();
		}
		
	}
	/**
	 * If this event is over a station (vertex), pop up a menu to allow the user to
	 * perform a few actions; else, pop up a menu over the layout/canvas
	 *
	 * @param e
	 */
	@SuppressWarnings("unchecked")
	protected void handlePopup(MouseEvent e) {
		final VisualizationViewer vv = (VisualizationViewer) e.getSource();
		final Point2D p = e.getPoint();
		final Point2D ivp = p;
		JPopupMenu popup = new JPopupMenu();

		System.out.println("mouse event!");

		GraphElementAccessor pickSupport = vv.getPickSupport();
		System.out.println("GraphElementAccessor!");
		if (pickSupport != null) {

			final Vertex pickV = pickSupport.getVertex(ivp.getX(), ivp.getY());

			if (pickV != null) {
				System.out.println("pickVisnotNull");

				System.out.println(pickV.toString());
				popup.add(new AbstractAction("Show Node Information") {
					public void actionPerformed(ActionEvent e) {
						System.out.println("person added");
//						Home home = new Home();
//						home.setVisible(true);
						showInformation(pickV);

//                   
					}
				});
				popup.show(vv, e.getX(), e.getY());// new abstraction

			}
		} /// if picksupport
		else {
			System.out.println(" Null vertexs");
		}

	}// handlePopup(MouseEvent e)
}// PopupGraphMousePlugin