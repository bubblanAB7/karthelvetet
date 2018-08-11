import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Window extends JFrame {

	private Scanner scan;
	private ArrayList tempList;
	private JFrame frame;
	private ImageWindow img;
	private Menu menu;
	private List list;
	private Buttons buttons;
	private PositionCollection pC;
	private NameCollection nC;
	private ActionListener archListener;
	private ActionListener buttonListener;
	private MouseAdapter markerListener;
	private MouseAdapter mouseListener;
	private ListSelectionListener listListener;
	private WindowListener windowListener;
	private boolean isChanged;
	private PositionCollection marked = new PositionCollection();
	private PositionCollection bus = new PositionCollection();
	private PositionCollection underground = new PositionCollection();
	private PositionCollection train = new PositionCollection();
	private PositionCollection none = new PositionCollection();
	private JScrollPane scrollPane;
	private boolean newMode = false;

	public Window() {
		super("Kartsystem");
		frame = this;
		PositionCollection pC = new PositionCollection();
		NameCollection nC = new NameCollection();
		;

		menu = new Menu();
		setJMenuBar(menu);

		buttons = new Buttons();
		add(buttons, BorderLayout.NORTH);

		list = new List();

		JPanel east = new JPanel();
		JLabel categories = new JLabel("Categories");
		east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
		east.add(categories);
		east.add(list);
		east.add(buttons.getButton(5));

		add(east, BorderLayout.EAST);

		buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("New") && !(img == null)) {
//					img.removeMouseListener(markerListener);
					img.addMouseListener(mouseListener);
					buttons.getButton(0).setEnabled(false);
					img.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				} else if (e.getActionCommand().equals("Search")) {
					clearMarked();
					String search = buttons.getText();
					if (nC.checkName(search)) {
						HashMap temp = nC.getPlaces(search).getHashMap();
						Iterator iterator = temp.entrySet().iterator();
						while (iterator.hasNext()) {
							Map.Entry current = (Map.Entry) iterator.next();
							addMarked(((Place) current.getValue()));
						}
					}
					paint();
				} else if (e.getActionCommand().equals("Hide"))
					hideMarked();
				else if (e.getActionCommand().equals("Remove")) {
//					removePos("");
					HashMap temp = marked.getHashMap();	
					Iterator iterator = temp.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry current = (Map.Entry) iterator.next();
						Place plc = ((((Place) current.getValue())));
						img.getBildContainer().remove(plc);
						nC.removePlace(plc);
						pC.removePlace(plc.getPosition());
						switch (plc.getCategory()) {
						case "Bus":
							bus.removePlace(plc.getPosition());
							break;
						case "Underground":
							underground.removePlace(plc.getPosition());
							break;
						case "Train":
							train.removePlace(plc.getPosition());
							break;
						case "None":
							none.removePlace(plc.getPosition());
							break;
						}
					}
					marked.clear();
					paint();
					if (temp.size() > 0)
						isChanged = true;
				} else if (e.getActionCommand().equals("Coordinates")) {
					CoordinatesForm form = new CoordinatesForm();
					String[] options = { "OK", "Cancel" };
					int answer = JOptionPane.showOptionDialog(form, form, "Coordinates search", 0,
							JOptionPane.INFORMATION_MESSAGE, null, options, null);
					if (answer == 0) {
						try {
							Position p = new Position(form.getXCoord(), form.getYCoord());
							if (pC.checkPosition(p)) {
								clearMarked();
								Place plc = pC.getPlace(p);
								addMarked(plc);
								plc.setBounds();
							} else
								JOptionPane.showMessageDialog(null,
										"Det finns ingen plats p� de angivna koordinaterna");
						} catch (NumberFormatException er) {
							JOptionPane.showMessageDialog(null, "Endast till�tet med heltal");
						}
					}
					paint();
				}

				else if (e.getActionCommand().equals("Hide categories")) {
					String category = list.getSelected();
					switch (category) {
					case "Bus":
						hideAll(bus);
						break;
					case "Underground":
						hideAll(underground);
						break;
					case "Train":
						hideAll(train);
						break;
					case "None":
						hideAll(none);
						break;
					}
				}
			}
		};

		listListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent lev) {
				if (!lev.getValueIsAdjusting()) {
					String category = list.getSelected();
					switch (category) {
					case "Bus":
						addAllToMarked(bus);
						break;
					case "Underground":
						addAllToMarked(underground);
						break;
					case "Train":
						addAllToMarked(train);
						break;
					case "None":
						addAllToMarked(none);
						break;
					}
				}
			}
		};

		list.getJList().addListSelectionListener(listListener);

		archListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == menu.getArchiveOption(0)) {
					if (isChanged) {
						int confirmed = JOptionPane.showConfirmDialog(null,
								"Det finns osparade �ndringar, vill du g� vidare?", "Varning!",
								JOptionPane.YES_NO_OPTION);
						if (confirmed == JOptionPane.YES_OPTION) {
							FileChooser fc = new FileChooser();
							if (!(fc.getFilePath() == null)) {
								if (checkImage())
									removeImage();
								img = new ImageWindow(fc.getFilePath());
								addImage(img);
								marked.clear();
								pC.clear();
								nC.clear();
								bus.clear();
								underground.clear();
								train.clear();
								none.clear();
//								removeAllPos();
								isChanged = false;
							}
						}
					} else {
						FileChooser fc = new FileChooser();
						if (!(fc.getFilePath() == null)) {
							if (checkImage())
								removeImage();
							img = new ImageWindow(fc.getFilePath());
							addImage(img);
							marked.clear();
							pC.clear();
							nC.clear();
							bus.clear();
							underground.clear();
							train.clear();
							none.clear();
						}
					}
				} else if (e.getSource() == menu.getArchiveOption(1)) {
					if (isChanged) {
						int confirmed = JOptionPane.showConfirmDialog(null,
								"Det finns osparade �ndringar, vill du g� vidare?", "Varning!",
								JOptionPane.YES_NO_OPTION);
						if (confirmed == JOptionPane.YES_OPTION) {
							FileChooser fc = new FileChooser();
							if (!(fc.getFilePath() == null)) {
								try {
									HashMap temp = pC.getHashMap();
									Iterator iterator = temp.entrySet().iterator();
									while (iterator.hasNext()) {
										Map.Entry current = (Map.Entry) iterator.next();
										img.getBildContainer().remove( ((((Place) current.getValue()))) );
									}
									marked.clear();
									pC.clear();
									nC.clear();
									bus.clear();
									underground.clear();
									train.clear();
									none.clear();
									String filePath = fc.getFilePath();
									File file = new File(filePath);
									scan = new Scanner(file);
									while (scan.hasNextLine()) {
										String s = scan.nextLine();
										String[] split = s.split(",", 6);
										if (split[0].equals("Described")) {
											int x = Integer.parseInt(split[2]);
											int y = Integer.parseInt(split[3]);
											Place plc = new DescribedPlace(new Position(x, y), split[1], split[4],
													split[5]);
											pC.addPlace(plc);
											nC.addPlace(plc);
											switch (split[1]) {
											case "Bus":
												bus.addPlace(plc);
												break;
											case "Underground":
												underground.addPlace(plc);
												break;
											case "Train":
												train.addPlace(plc);
												break;
											case "None":
												none.addPlace(plc);
												break;
											}
											plc.setBounds();
											img.getBildContainer().add(plc);
										}
										if (split[0].equals("Named")) {
											int x = Integer.parseInt(split[2]);
											int y = Integer.parseInt(split[3]);
											Place plc = new NamedPlace(new Position(x, y), split[1], split[4]);
											pC.addPlace(plc);
											nC.addPlace(plc);
											switch (split[1]) {
											case "Bus":
												bus.addPlace(plc);
												break;
											case "Underground":
												underground.addPlace(plc);
												break;
											case "Train":
												train.addPlace(plc);
												break;
											case "None":
												none.addPlace(plc);
												break;
											}
											plc.setBounds();
											img.getBildContainer().add(plc);

										}
									}
									scan.close();
									showAll(pC);
									paint();
//									img.addMouseListener(markerListener);
									isChanged = false;
								} catch (FileNotFoundException er) {
									JOptionPane.showMessageDialog(null, "N�got gick fel");
								}
							}
						}
					} else {
						FileChooser fc = new FileChooser();
						if (!(fc.getFilePath() == null)) {
							try {
								HashMap temp = pC.getHashMap();
								Iterator iterator = temp.entrySet().iterator();
								while (iterator.hasNext()) {
									Map.Entry current = (Map.Entry) iterator.next();
									img.getBildContainer().remove( ((((Place) current.getValue()))) );
								}
								marked.clear();
								pC.clear();
								nC.clear();
								bus.clear();
								underground.clear();
								train.clear();
								none.clear();
								String filePath = fc.getFilePath();
								File file = new File(filePath);
								scan = new Scanner(file);
								while (scan.hasNextLine()) {
									String s = scan.nextLine();
									String[] split = s.split(",", 6);
									if (split[0].equals("Described")) {
										int x = Integer.parseInt(split[2]);
										int y = Integer.parseInt(split[3]);
										Place plc = new DescribedPlace(new Position(x, y), split[1], split[4],
												split[5]);
										pC.addPlace(plc);
										nC.addPlace(plc);
										switch (split[1]) {
										case "Bus":
											bus.addPlace(plc);
											break;
										case "Underground":
											underground.addPlace(plc);
											break;
										case "Train":
											train.addPlace(plc);
											break;
										case "None":
											none.addPlace(plc);
											break;
										}
										plc.setBounds();
										img.getBildContainer().add(plc);
									}
									if (split[0].equals("Named")) {
										int x = Integer.parseInt(split[2]);
										int y = Integer.parseInt(split[3]);
										Place plc = new NamedPlace(new Position(x, y), split[1], split[4]);
										pC.addPlace(plc);
										nC.addPlace(plc);
										switch (split[1]) {
										case "Bus":
											bus.addPlace(plc);
											break;
										case "Underground":
											underground.addPlace(plc);
											break;
										case "Train":
											train.addPlace(plc);
											break;
										case "None":
											none.addPlace(plc);
											break;
										}
										plc.setBounds();
										img.getBildContainer().add(plc);

									}
								}
								scan.close();
								showAll(pC);
								paint();
//								img.addMouseListener(markerListener);
							} catch (FileNotFoundException er) {
								JOptionPane.showMessageDialog(null, "N�got gick fel");
							}
						}
					}
				} else if (e.getSource() == menu.getArchiveOption(2)) {
					FileChooser fc = new FileChooser();
					if (!(fc.getFilePath() == null)) {
						try {
							FileWriter fw = new FileWriter(fc.getFilePath());
							PrintWriter pw = new PrintWriter(fw);
							HashMap temp = pC.getHashMap();
							Iterator iterator = temp.entrySet().iterator();
							while (iterator.hasNext()) {
								Map.Entry current = (Map.Entry) iterator.next();
								pw.println((((Place) current.getValue())));
							}
							isChanged = false;
							pw.close();
						} catch (IOException er) {
							JOptionPane.showMessageDialog(null, "N�got gick fel");
						}
					}

				} else if (e.getSource() == menu.getArchiveOption(3)) {
					if (isChanged) {
						int confirmed = JOptionPane.showConfirmDialog(null,
								"Det finns osparade �ndringar, vill du avsluta?", "Varning!",
								JOptionPane.YES_NO_OPTION);

						if (confirmed == JOptionPane.YES_OPTION)
							System.exit(0);
					} else
						System.exit(0);
				}
			}
		};
		
		class MarkerListener extends MouseAdapter {
			public void mouseClicked(MouseEvent mev) {
//				int x = mev.getX();
//				int y = mev.getY();
				Position p = ((Place) mev.getComponent()).getPosition();
				if (pC.checkPosition(p) && !newMode) {
					Place plc = pC.getPlace(p);
					if (SwingUtilities.isRightMouseButton(mev)) {
						if (plc instanceof NamedPlace) {
							String info = plc.getName() + "{" + plc.getPosition().getX() + ","
									+ plc.getPosition().getY() + "}";
							JOptionPane.showMessageDialog(null, info);
						} else if (plc instanceof DescribedPlace) {
							String[] options = { "OK" };
							Form form = new Form((DescribedPlace) plc);
							JOptionPane.showOptionDialog(form, form, "Described place", 0,
									JOptionPane.INFORMATION_MESSAGE, null, options, null);
						}
					} else if (SwingUtilities.isLeftMouseButton(mev)) {
						if (plc.isMarked()) {
							plc.setMarked(false);
							plc.setBounds();
							paint();
							marked.removePlace(plc.getPosition());
						} else if (!plc.isMarked()) {
							plc.setMarked(true);
							plc.setBounds();
							// plc.repaint();
							paint();
							marked.addPlace(plc);
						}
					}
				}
			}
		}

		class MouseListener extends MouseAdapter {
			@Override
			public void mouseClicked(MouseEvent mev) {
				newMode = true;
				int x = mev.getX();
				int y = mev.getY();
				Position p = new Position(x, y);
				if (pC.checkPosition(p))
					JOptionPane.showMessageDialog(null, "Det �r endast till�tet med en plats per position.");
				else if (!pC.checkPosition(p) && img.inMap(p)) {
					Place plc;
					String[] options = { "Ok" };
					Form form;
					if (buttons.getNamedButton().isSelected()) {
						form = new Form("Name");
						JOptionPane.showOptionDialog(form, form, "Named place", 0, JOptionPane.INFORMATION_MESSAGE,
								null, options, null);
						plc = new NamedPlace(new Position(x, y), list.getSelected(), form.getNameField().getText());
					} else {
						form = new Form("Name", "Description");
						JOptionPane.showOptionDialog(form, form, "Described place", 0, JOptionPane.INFORMATION_MESSAGE,
								null, options, null);
						plc = new DescribedPlace(new Position(x, y), list.getSelected(), form.getNameField().getText(),
								form.getDescField().getText());
					}
					pC.addPlace(plc);
					nC.addPlace(plc);
					plc.addMouseListener(markerListener);
					switch (plc.getCategory()) {
					case "Bus":
						bus.addPlace(plc);
						break;
					case "Underground":
						underground.addPlace(plc);
						break;
					case "Train":
						train.addPlace(plc);
						break;
					case "None":
						none.addPlace(plc);
						break;
					}
					plc.setBounds();
//					img.add(plc);
//					add(plc);
					img.getBildContainer().add(plc);
					paint();
					isChanged = true;
				}
				img.removeMouseListener(this);
				newMode = false;
				buttons.getButton(0).setEnabled(true);
				img.setCursor(Cursor.getDefaultCursor());
//				img.addMouseListener(markerListener);
			}
		}

		class WindowListener extends WindowAdapter {
			public void windowClosing(WindowEvent e) {
				if (isChanged) {
					int confirmed = JOptionPane.showConfirmDialog(null,
							"Det finns osparade �ndringar, vill du avsluta?", "Varning!", JOptionPane.YES_NO_OPTION);

					if (confirmed == JOptionPane.YES_OPTION) {
						setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					} else {
						setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					}
				}
			}
		}
		;

		mouseListener = new MouseListener();
		markerListener = new MarkerListener();
		windowListener = new WindowListener();
		frame.addWindowListener(windowListener);

		for (int i = 0; i < 4; i++) {
			menu.getArchiveOption(i).addActionListener(archListener);
		}

		for (int i = 0; i < 6; i++) {
			buttons.getButton(i).addActionListener(buttonListener);
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setSize(900, 620);
		setVisible(true);
	}

	public void addImage(ImageWindow i) {
		img = i;
		scrollPane = new JScrollPane(img);
		scrollPane.setVisible(true);
		add(scrollPane);
//		scrollPane.setMaximumSize(img.getDimension());
//		scrollPane.setBounds(img.getRectangle());
		setVisible(true);
	}

	public void removePos(String s) {
		if (s == "all"){
			
		}
		
	}
	
	public void clearMarked() {
		HashMap temp = marked.getHashMap();
		Iterator iterator = temp.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry current = (Map.Entry) iterator.next();
			((((Place) current.getValue()))).setMarked(false);
		}
		marked.clear();
	}

	public void addMarked(Place plc) {
		plc.setMarked(true);
		plc.setBounds();
		plc.setVisible(true);
		marked.addPlace(plc);
	}

	public void hideMarked() {
		HashMap temp = marked.getHashMap();
		Iterator iterator = temp.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry current = (Map.Entry) iterator.next();
			((((Place) current.getValue()))).setVisible(false);
		}
		marked.clear();
	}

	public void hideAll(PositionCollection posCol) {
		HashMap temp = posCol.getHashMap();
		Iterator iterator = temp.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry current = (Map.Entry) iterator.next();
			((((Place) current.getValue()))).setVisible(false);
		}
	}

	public void showAll(PositionCollection posCol) {
		HashMap temp = posCol.getHashMap();
		Iterator iterator = temp.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry current = (Map.Entry) iterator.next();
			((((Place) current.getValue()))).setVisible(true);
		}
	}


	public void paint() {
		if (img != null) {
			scrollPane.validate();
			scrollPane.repaint();
		}
	}
	
	public void removeAllPos () {
		addAllToMarked(bus);
		addAllToMarked(underground);
		addAllToMarked(train);
		addAllToMarked(none);
//		tempList = new ArrayList<Place>();
		HashMap temp = marked.getHashMap();	
		Iterator iterator = temp.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry current = (Map.Entry) iterator.next();
			Place plc = ((((Place) current.getValue())));
			img.getBildContainer().remove(plc);
			switch (plc.getCategory()) {
			case "Bus":
				bus.removePlace(plc.getPosition());
				break;
			case "Underground":
				underground.removePlace(plc.getPosition());
				break;
			case "Train":
				train.removePlace(plc.getPosition());
				break;
			case "None":
				none.removePlace(plc.getPosition());
				break;
			}
		}
		nC.clear();
		pC.clear();
		marked.clear();
		paint();
	}
	
	public void addAllToMarked(PositionCollection posCol) {
		HashMap temp = posCol.getHashMap();
		Iterator iterator = temp.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry current = (Map.Entry) iterator.next();
			addMarked((Place) current.getValue());
		}
	}

	public NameCollection getNameCollection() {
		return this.nC;
	}

	public static void main(String[] args) {
		new Window();
	}

	public boolean checkImage() {
		boolean b = false;
		if (this.img != null)
			b = true;
		return b;
	}

	public void removeImage() {
		frame.remove(img);
//		frame.getContentPane().remove(img.getScroll());
	}

}
