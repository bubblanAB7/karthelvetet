import java.awt.*;
import javax.swing.*;

public class List extends JPanel {
	private DefaultListModel categoryList = new DefaultListModel();
	JList list = new JList(categoryList);
	
	
	public List() {
		categoryList.addElement("Bus");
		categoryList.addElement("Underground");
		categoryList.addElement("Train");
		setLayout(new BorderLayout());
		setMaximumSize(new Dimension (200,200));
		setAlignmentX(LEFT_ALIGNMENT);
		list.setSelectedValue(null, false);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setPreferredSize(new Dimension(140, 200));
		add(list);
	}
	
	public String getSelected() {
		String s = (String) list.getSelectedValue();
		if (s == null)
			s = "None";
		return s;
	}
	
	public JList getJList() {
		return this.list;
	}
	
	
}
	
