import java.text.MessageFormat.Field;

import javax.swing.*;

public class CoordinatesForm extends JPanel {
	private JPanel panel;
	private JTextField xField;
	private JTextField yField;
	
	public CoordinatesForm() {
		this.panel = this;
		JLabel xLabel = new JLabel("X: ");
		xField = new JTextField(10);
		JLabel yLabel = new JLabel("Y: ");
		yField = new JTextField(10);
		panel.add(xLabel);
		panel.add(xField);
		panel.add(yLabel);
		panel.add(yField);
		
	}
	public int getXCoord() {
		return Integer.parseInt(xField.getText());
	}
	public int getYCoord() {
		return Integer.parseInt(yField.getText());
	}
}
