import java.awt.Color;

import javax.swing.*;

public class Form extends JPanel {
	JPanel panel;
	JTextField nameField;
	JTextField descField;
	
	public Form(String n) {
		panel = this;
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		nameField = new JTextField(25);
		JLabel nameLabel = new JLabel(n+": ");
		panel.add(nameLabel);
		panel.add(nameField);
	}
	
	public Form(DescribedPlace plc) {
		panel = this;
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JLabel nameLabel = new JLabel("Name: ");
		JLabel descLabel = new JLabel("Description: ");
		JTextArea nameText = new JTextArea();
		JTextArea descText = new JTextArea();
		String info = plc.getName() + "{" + plc.getX() + "," + plc.getY() + "}";
		nameText.setText(info);
		descText.setText(plc.getDescription());
		JPanel namePanel = new JPanel();
		JPanel descPanel = new JPanel();
		namePanel.add(nameLabel);
		descPanel.add(descLabel);
		namePanel.add(nameText);
		descPanel.add(descText);
		nameText.setEditable(false);
		descText.setEditable(false);
		panel.add(namePanel);
		panel.add(descPanel);
	}
	
	public Form(String n, String d) {
		panel = this;
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JLabel nameLabel = new JLabel(n+": ");
		nameField = new JTextField(25);
		JPanel namePanel = new JPanel();
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		
		JLabel descLabel = new JLabel(d+": ");
		descField = new JTextField(25);
		JPanel descPanel = new JPanel();
		descPanel.add(descLabel);
		descPanel.add(descField);
		panel.add(namePanel);
		panel.add(descPanel);
	}
	
	public JTextField getNameField() {
		return this.nameField;
	}
	
	public JTextField getDescField() {
		return this.descField;
	}
}