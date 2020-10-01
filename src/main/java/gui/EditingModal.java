package gui;

import javax.swing.JComboBox;

import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

public class EditingModal extends EditingModalGraphMouse {
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
		modeBox.setSelectedItem(Mode.TRANSFORMING);
		return modeBox;
	}

}