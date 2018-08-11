import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Buttons extends JPanel{
	
	private JButton newButton = new JButton ("New");
	private JRadioButton namedButton = new JRadioButton ("Named", true);
	private JRadioButton descButton = new JRadioButton ("Described", false);
	private ButtonGroup placeType = new ButtonGroup();
	private JTextField searchField = new JTextField (10);
	private JButton searchButton = new JButton ("Search");
	private JButton hideButton = new JButton ("Hide");
	private JButton removeButton = new JButton ("Remove");
	private JButton coordsButton = new JButton ("Coordinates");
	private JPanel typeButton = new JPanel();
	private JButton hideCategories = new JButton("Hide categories");
	private JButton[] buttons = {newButton, searchButton, hideButton, removeButton, coordsButton, hideCategories};
	

	public Buttons() {
		
//		newButton.setActionCommand("new");
//		namedButton.setActionCommand("named");
//		descButton.setActionCommand("described");
//		searchButton.setActionCommand("search");
//		hideButton.setActionCommand("hide");
//		removeButton.setActionCommand("remove");
//		coordsButton.setActionCommand("coords");
//		hideCategories.setActionCommand("hidecategories");
		
		placeType.add(namedButton);
		placeType.add(descButton);
		typeButton.add(namedButton);
		typeButton.add(descButton);
		typeButton.setLayout(new BoxLayout(typeButton, BoxLayout.PAGE_AXIS));
		add(newButton);
		add(typeButton);
		add(searchField);
		add(searchButton);
		add(hideButton);
		add(removeButton);
		add(coordsButton);
	
	
	}
	
	public String getText() {
		return searchField.getText();
	}
	
	public JButton getButton(int i) {
		return buttons[i];
	}
	
	public ButtonGroup getButtonGroup() {
		return this.placeType;
	}
	
	public JRadioButton getNamedButton() {
		return namedButton;
	}
	
	public JRadioButton getDescButton() {
		return descButton;
	}
}
