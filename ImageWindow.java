import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class ImageWindow extends JPanel {
	private ImageIcon image;
	private JLabel bildContainer;
	private JScrollPane scrollBild;
	private JPanel bildPanel;
	
	ImageWindow(String s){
		image = new ImageIcon(s);
		int w = image.getIconWidth();
		int h = image.getIconHeight();
		bildContainer = new JLabel(image);
		setPreferredSize(new Dimension(w, h));
		setMaximumSize(new Dimension(w, h));
		add(bildContainer);
		setLayout(null);
		bildContainer.setBounds(0, 0, w, h);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image.getImage(), 0, 0, null);
	}
	
	public JPanel getPanel() {
		return this.bildPanel;
	}
	
	public JScrollPane getScroll() {
		return this.scrollBild;
	}
	
	public JLabel getBildContainer() {
		return this.bildContainer;
	}
	
	public void addPlace(Place p) {
		bildContainer.add(p);
	}
	
	public Dimension getDimension() {
		Dimension d = new Dimension(image.getIconWidth(), image.getIconHeight());
		return d;
	}
	
	public boolean inMap(Position p) {
		if(p.getX() <= image.getIconWidth() && p.getY() <= image.getIconHeight())
			return true;
		else
			return false;
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(image.getIconWidth(), image.getIconHeight());
	}
	
	public ImageIcon getImageIcon() {
		return this.image;
	}

}