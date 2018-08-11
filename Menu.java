import javax.swing.*;

public class Menu extends JMenuBar{
	private JMenuBar menu;
	private JMenu archive;
	private JMenuItem newMap = new JMenuItem("New Map");
	private JMenuItem load = new JMenuItem("Load Places");
	private JMenuItem save = new JMenuItem("Save");
	private JMenuItem exit = new JMenuItem("Exit");
	JMenuItem[] archiveOptions = {newMap, load, save, exit};
	
	public Menu() {
		menu = this;
		archive = new JMenu("Archive");
		archive.add(newMap);
		archive.add(load);
		archive.add(save);
		archive.add(exit);
		menu.add(archive);
	}
	
	public JMenuItem getArchiveOption(int i) {
		return archiveOptions[i];
	}
}
