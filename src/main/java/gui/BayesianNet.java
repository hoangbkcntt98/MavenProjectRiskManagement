package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.AbstractVertexShapeFunction;
import edu.uci.ics.jung.graph.decorators.ConstantVertexAspectRatioFunction;
import edu.uci.ics.jung.graph.decorators.ConstantVertexSizeFunction;
import edu.uci.ics.jung.graph.decorators.DefaultToolTipFunction;
import edu.uci.ics.jung.graph.decorators.EdgeShape;
import edu.uci.ics.jung.graph.decorators.VertexStringer;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.SparseGraph;
import edu.uci.ics.jung.graph.impl.SparseVertex;
import edu.uci.ics.jung.graph.impl.UndirectedSparseEdge;
import edu.uci.ics.jung.visualization.AbstractLayout;
import edu.uci.ics.jung.visualization.DefaultSettableVertexLocationFunction;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.GraphElementAccessor;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.PluggableRenderer;
import edu.uci.ics.jung.visualization.ShapePickSupport;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

public class BayesianNet extends JFrame {

	private JPanel contentPane;
	Graph graph;
	AbstractLayout layout;
	PluggableRenderer pr;
	VisualizationViewer vv;
	DefaultSettableVertexLocationFunction vertexLocations;
	String instructions = "<html>" +

			"<h3>Picking Mode:</h3>" + "<ul>" + "<li>Mouse1 on a Vertex selects the vertex"
			+ "<li>Mouse1 elsewhere unselects all Vertices"
			+ "<li>Mouse1+Shift on a Vertex adds/removes Vertex selection"
			+ "<li>Mouse1+drag on a Vertex moves all selected Vertices"
			+ "<li>Mouse1+drag elsewhere selects Vertices in a region"
			+ "<li>Mouse1+Shift+drag adds selection of Vertices in a new region"
			+ "<li>Mouse1+CTRL on a Vertex selects the vertex and centers the display on it" + "</ul>"
			+ "<h3>Transforming Mode:</h3>" + "<ul>" + "<li>Mouse1+drag pans the graph"
			+ "<li>Mouse1+Shift+drag rotates the graph" + "<li>Mouse1+CTRL(or Command)+drag shears the graph" + "</ul>"
			+ "</html>";
	public void drawGraph() {
		generateGraph();
		generateVisualizationView();
		drawToolsBar();

	}
	public void generateGraph() {
		graph = new SparseGraph();
		Vertex[] v = createVertices();
		createEdges(v);
	}
	public void setContent() {
		setContentPane(contentPane);
	}
	public void generateVisualizationView() {
		pr = new PluggableRenderer();
		this.layout = new FRLayout(graph);
		vertexLocations = new DefaultSettableVertexLocationFunction();
		layout.initialize(new Dimension(600, 600));
		vv = new VisualizationViewer(layout, pr);
		vv.setBackground(Color.white);
		vv.setPickSupport(new ShapePickSupport());
		pr.setVertexLabelCentering(true);
		pr.setEdgeShapeFunction(new EdgeShape.Line());
		pr.setVertexStringer(new VertexStringer() {
			public String getLabel(ArchetypeVertex v) {
				return v.toString();
			}
		});
		generateVertexShape(pr);
	}

	public void generateVertexShape(PluggableRenderer pr) {
		// change size of vertex
		pr.setVertexShapeFunction(new AbstractVertexShapeFunction(new ConstantVertexSizeFunction(40),
				new ConstantVertexAspectRatioFunction(1.0f)) {
			public Shape getShape(Vertex v) {
				// TODO Auto-generated method stub
				return factory.getEllipse(v);
			}
		});
	}
	

	public void drawToolsBar() {
		vv.setToolTipFunction(new DefaultToolTipFunction());
		final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
		contentPane.add(panel);
		final EditingModalGraphMouse graphMouse = new EditingModal();
		graphMouse.add(new PopupGraphMousePlugin());
		graphMouse.setVertexLocations(vertexLocations);
		vv.setGraphMouse(graphMouse);
		graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);

		final ScalingControl scaler = new CrossoverScalingControl();
		JButton plus = new JButton("+");
		plus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scaler.scale(vv, 1.1f, vv.getCenter());
			}
		});
		JButton minus = new JButton("-");
		minus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scaler.scale(vv, 1 / 1.1f, vv.getCenter());
			}
		});

		JButton help = new JButton("Help");
		help.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(vv, instructions);
			}
		});
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		JPanel controls = new JPanel();
		controls.add(plus);
		controls.add(minus);
		JComboBox modeBox = graphMouse.getModeComboBox();
		controls.add(modeBox);
		controls.add(help);
		controls.add(back);
		contentPane.add(controls, BorderLayout.SOUTH);
	}
	public void createMainMenu() {
		JMenu menu = new JMenu("File");
		menu.add(new AbstractAction("Make Image") {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int option = chooser.showSaveDialog(BayesianNet.this);
				if (option == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					writeJPEGImage(file);
				}
			}
		});

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menu);
		setJMenuBar(menuBar);
	}
	/**
	 * create some vertices
	 * 
	 * @param count how many to create
	 * @return the Vertices in an array
	 */
	public Vertex[] createVertices() {
		int count = 6;
		Vertex[] v = new Vertex[count];
		for (int i = 0; i < count; i++) {
			v[i] = graph.addVertex(new SparseVertex());
		}
		return v;
	}

	/**
	 * create edges for this demo graph
	 * 
	 * @param v an array of Vertices to connect
	 */
	public void createEdges(Vertex[] v) {
		graph.addEdge(new DirectedSparseEdge(v[0], v[1]));
		graph.addEdge(new DirectedSparseEdge(v[0], v[2]));
		graph.addEdge(new DirectedSparseEdge(v[2], v[3]));
		graph.addEdge(new DirectedSparseEdge(v[1], v[3]));
		graph.addEdge(new DirectedSparseEdge(v[1], v[4]));
		graph.addEdge(new UndirectedSparseEdge(v[4], v[5]));
		graph.addEdge(new UndirectedSparseEdge(v[4], v[3]));
	}

	/**
	 * copy the visible part of the graph to a file as a jpeg image
	 * 
	 * @param file
	 */
	public void writeJPEGImage(File file) {
		int width = vv.getWidth();
		int height = vv.getHeight();

		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = bi.createGraphics();
		vv.paint(graphics);
		graphics.dispose();

		try {
			ImageIO.write(bi, "jpeg", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int print(java.awt.Graphics graphics, java.awt.print.PageFormat pageFormat, int pageIndex)
			throws java.awt.print.PrinterException {
		if (pageIndex > 0) {
			return (Printable.NO_SUCH_PAGE);
		} else {
			java.awt.Graphics2D g2d = (java.awt.Graphics2D) graphics;
			vv.setDoubleBuffered(false);
			g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

			vv.paint(g2d);
			vv.setDoubleBuffered(true);

			return (Printable.PAGE_EXISTS);
		}
	}

	/**
	 * Create the frame.
	 */
	public BayesianNet() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		setSize((int) width - 200, (int) height - 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * a driver for this demo
	 */

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BayesianNet frame = new BayesianNet();
					frame.drawGraph();
					frame.setContent();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private class EditingModal extends EditingModalGraphMouse {
		public EditingModal() {
			super();
		}

		/**
		 * @return Returns the modeBox.
		 */
		public JComboBox getModeComboBox() {
			if (modeBox == null) {
				modeBox = new JComboBox(new Mode[] { Mode.TRANSFORMING, Mode.PICKING });
				modeBox.addItemListener(getModeListener());
			}
			modeBox.setSelectedItem(mode);
			return modeBox;
		}

	}

	protected class PopupGraphMousePlugin extends AbstractPopupGraphMousePlugin implements MouseListener {

		public PopupGraphMousePlugin() {
			this(MouseEvent.BUTTON3_MASK);
		}

		public PopupGraphMousePlugin(int modifiers) {
			super(modifiers);
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
						/**
						* 
						*/

						public void actionPerformed(ActionEvent e) {
							System.out.println("person added");
							Home home = new Home();
							home.setVisible(true);

//	                   
						}
					});
					popup.show(vv, e.getX(), e.getY());// new abstraction

				}
			} /// if picksupport

		}// handlePopup(MouseEvent e)
	}// PopupGraphMousePlugin
}
