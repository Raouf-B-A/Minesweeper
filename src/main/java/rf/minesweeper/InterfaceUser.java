package rf.minesweeper;

import java.applet.Applet;
import java.applet.AudioClip;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

/**
 * Created by Raouf BEN AOUISSI on 12/02/2010.
 */
public class InterfaceUser extends JFrame {
	final static String NOM_BD="bddemineur";
	final static String ServeurBD="localhost";
	final static String NOM_USER="root";
	final static String MP="";
	static String DBurl="jdbc:mysql://"+ServeurBD+"/"+NOM_BD;
	static Connection con;
	public static int tempsPasse = 0;
	public static boolean finJeu = false;
	public static boolean debutJeu = false;
	public boolean etatTemps = false;
	private static boolean activerSon=false;
	private int score = 0;
	private int nbMines = 10;
	private int nbMinesF=10;
	private int nbCol, nbLig;
	public int longueur = 10;
	public int largeur = 10;
	private int selection = 1;
	private RfButtons tableBtn[];
	private JButton btnRejouer;
	private JMenuBar menuPrincipale;
	private JMenuItem itNouveau;
	private JCheckBoxMenuItem checkSon;
	private JMenuItem mnQuitter;
	private JMenuItem menuAidePropos;
	private JSeparator menuAideSeparateur1;
	private JMenuItem menuAideSommaire;
	private JMenu menuAide;
	private ButtonGroup btnGroupeNiveau;
	private JSeparator jSeparator4;
	private JLabel lblTous;
	private JLabel lbl2;
	private JLabel lbl3;
	public static JLabel lblChrono;
	private JSeparator jSeparator3;
	private JSeparator jSeparator2;
	private JMenuItem mnMeilleurTemps;
	private JSeparator jSeparator1;
	public JRadioButtonMenuItem rdPersonnalise;
	private JRadioButtonMenuItem rdExpert;
	private JRadioButtonMenuItem rbIntermediaire;
	private JRadioButtonMenuItem rdDebutant;
	private JMenu mnPartie;
	public Temps t = new Temps();

	public InterfaceUser(int nbCol, int nbLig, int nbMines) {
		super();
		score = 0;
		this.nbLig = nbLig;
		this.nbCol = nbCol;
		this.nbMines = nbMines;
		this.nbMinesF=nbMines;
		largeur = nbCol * 15 + 30;
		longueur = nbLig * 15 + 110;
		initGUI();
		ImageIcon img =new ImageIcon(getClass().getClassLoader().getResource("img/4.gif"));
		btnRejouer.setIcon(img);
		tempsPasse = 0;
		debutJeu = false;
		finJeu = false;
	}

	private void initGUI() {
		int x, y;
		x = 10;
		y = 50;
		tableBtn = new RfButtons[nbCol * nbLig];
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			getContentPane().setBackground(new java.awt.Color(255, 255, 255));
			this.setVisible(false);
			{
				lblTous = new JLabel();
				getContentPane().add(lblTous);
				lblTous.setText("");
				lblTous.setBounds(2, 5, largeur - 10, longueur - 60);
				lblTous.setBackground(new java.awt.Color(192, 192, 192));
				lblTous.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createEtchedBorder(BevelBorder.LOWERED),
						BorderFactory.createEmptyBorder(0, 0, 0, 0)));
			}
			{
				lbl2 = new JLabel();
				getContentPane().add(lbl2);
				lbl2.setText("");
				lbl2.setBounds(6, 8, largeur - 18, 38);
				lbl2.setBorder(BorderFactory.createCompoundBorder(BorderFactory
						.createEtchedBorder(BevelBorder.LOWERED), BorderFactory
						.createEmptyBorder(0, 0, 0, 0)));
			}
			{
				lblChrono = new JLabel();
				getContentPane().add(lblChrono);
				lblChrono.setText("0");
				lblChrono.setBounds(largeur - 48, 14, 30, 25);
				lblChrono.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createEtchedBorder(BevelBorder.LOWERED),
						BorderFactory.createEmptyBorder(0, 0, 0, 0)));
				lblChrono.setBackground(new java.awt.Color(0, 0, 0));
				lblChrono.setForeground(new java.awt.Color(255, 0, 0));

			}
			{
				lbl3 = new JLabel();
				getContentPane().add(lbl3);
				lbl3.setText("" + nbMines);
				lbl3.setVerticalTextPosition(SwingConstants.CENTER);
				lbl3.setHorizontalTextPosition(SwingConstants.CENTER);
				lbl3.setBounds(10, 14, 30, 25);
				lbl3.setBorder(BorderFactory.createCompoundBorder(BorderFactory
						.createEtchedBorder(BevelBorder.LOWERED), BorderFactory
						.createEmptyBorder(0, 0, 0, 0)));
				lbl3.setBackground(new java.awt.Color(0, 0, 0));
				lbl3.setForeground(new java.awt.Color(255, 0, 0));

			}
			{
				btnRejouer = new JButton();
				getContentPane().add(btnRejouer);
				btnRejouer.setBounds((largeur - 24) / 2 , 14, 20, 20);
				btnRejouer.setBorder(new LineBorder(
						new java.awt.Color(0, 0, 0), 1, false));
				btnRejouer.setText("");
				btnRejouer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						rejouer();
					}
				});
			}
			{
				menuPrincipale = new JMenuBar();
				setJMenuBar(menuPrincipale);
				{
					mnPartie = new JMenu();
					menuPrincipale.add(mnPartie);
					menuPrincipale.add(getMenuAide());
					mnPartie.setText("Partie");
					{
						itNouveau = new JMenuItem();
						mnPartie.add(itNouveau);
						itNouveau.setText("Nouveau");
						itNouveau.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								nouveau();
							}
						});
					}
					{
						rdDebutant = new JRadioButtonMenuItem();
						mnPartie.add(rdDebutant);
						rdDebutant.setText("Debutant");
						rdDebutant.setSelected(true);
						rdDebutant.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								debutant();
							}
						});
						getBtnGroupeNiveau().add(rdDebutant);
						{
							jSeparator1 = new JSeparator();
							rdDebutant.add(jSeparator1);
						}
					}
					{
						rbIntermediaire = new JRadioButtonMenuItem();
						mnPartie.add(rbIntermediaire);
						rbIntermediaire.setText("Intermédiaire");
						rbIntermediaire.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								intermediaire();
							}
						});
						getBtnGroupeNiveau().add(rbIntermediaire);
					}
					{
						rdExpert = new JRadioButtonMenuItem();
						mnPartie.add(rdExpert);
						rdExpert.setText("Expert");
						rdExpert.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								expert();
							}
						});
						getBtnGroupeNiveau().add(rdExpert);
					}
					{
						rdPersonnalise = new JRadioButtonMenuItem();
						mnPartie.add(rdPersonnalise);
						rdPersonnalise.setText("Personnaliser...");
						rdPersonnalise.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								personnalisee();
							}
						});
						getBtnGroupeNiveau().add(rdPersonnalise);
					}
					{
						checkSon = new JCheckBoxMenuItem();
						mnPartie.add(checkSon);
						checkSon.setText("Son");
						if(activerSon)
							checkSon.setSelected(true);
						checkSon.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								actionSon();
							}
						});
						{
							jSeparator2 = new JSeparator();
							checkSon.add(jSeparator2);
						}
					}
					{
						mnMeilleurTemps = new JMenuItem();
						mnPartie.add(mnMeilleurTemps);
						mnMeilleurTemps.setText("Meilleur temps");
						mnMeilleurTemps.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								meilleurTemps();
							}
						});
						{
							jSeparator4 = new JSeparator();
							mnMeilleurTemps.add(jSeparator4);
						}
					}
					{
						mnQuitter = new JMenuItem();
						mnPartie.add(mnQuitter);
						mnQuitter.setText("Quitter");
						mnQuitter.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								quitter();
							}
						});
						{
							jSeparator3 = new JSeparator();
							mnQuitter.add(jSeparator3);
						}
					}
				}
			}
			int k;
			for (int i = 0; i < nbLig; i++) {
				for (int j = 0; j < nbCol; j++) {
					k = i * nbCol + j;
					tableBtn[k] = new RfButtons();
					getContentPane().add(tableBtn[k]);
					tableBtn[k].setBounds(x, y, 15, 15);
					tableBtn[k].setPos(k);
					tableBtn[k].setBackground(new java.awt.Color(100, 100, 100));
					tableBtn[k].setBorder(BorderFactory.createCompoundBorder(
							null, new LineBorder(new java.awt.Color(150, 150,
									150), 1, false)));
					tableBtn[k].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							if (etatTemps == false) {
								t = new Temps();
								t.start();
								t.setPriority(1);
								etatTemps = true;
							}
							debutJeu = true;
							testClick(evt);
						}
					});
					tableBtn[k].addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							marquerBouton(evt);
						}
					});
					tableBtn[k].addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent evt) {
							mouseReleased1();
						}
						public void mousePressed(MouseEvent evt) {
							mousePressed1();
						}
					});
					x += 15;
				}
				x = 10;
				y += 15;
			}
			// pack();
			// this.setSize(327, 282);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTabMines();
	}

	private void initTabMines() {
		int j, k;
		int tabMines[] = new int[nbMines];
		boolean test;
		String s;
		for (int i = 0; i < tabMines.length; i++) {
			do {
				s = "";
				s += Math.round(nbCol * nbLig * Math.random());
				k = Integer.parseInt(s);
				test = true;
				if (k == nbCol * nbLig)
					k--;
				for (j = 0; j < i; j++) {
					if (tabMines[j] == k)
						test = false;
				}
			} while (test == false);
			tabMines[i] = k;
			tableBtn[k].setContientMine();
		}
	}

	private void afficherMines(boolean n) {
		ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource("img/2.gif"));
		ImageIcon img2=new ImageIcon(getClass().getClassLoader().getResource("img/2.gif"));
		if (n == true)
			img = new ImageIcon(getClass().getClassLoader().getResource("img/mine.gif"));
		for (int i = 0; i < tableBtn.length; i++) {
			if ((tableBtn[i].contientMine == true)&&(tableBtn[i].marque==false)) {
				tableBtn[i].setIcon(img);
			}else{
				if((tableBtn[i].contientMine == false)&&(tableBtn[i].marque==true)){
					tableBtn[i].setIcon(img2);
				}
			}
		}
	}

	private int nbrMines(int pos) {
		int nb = 0;
		int i;
		i = pos + 1;
		if ((i < tableBtn.length) && (i >= 0) && ((pos + 1) % nbCol != 0)
				&& (tableBtn[i].contientMine == true))
			nb++;

		i = pos - 1;
		if ((i < tableBtn.length) && (i >= 0) && (pos % nbCol != 0)
				&& (tableBtn[i].contientMine == true))
			nb++;

		i = pos + nbCol;
		if ((i < tableBtn.length) && (i >= 0)
				&& (tableBtn[i].contientMine == true))
			nb++;

		i = pos - nbCol;
		if ((i < tableBtn.length) && (i >= 0)
				&& (tableBtn[i].contientMine == true))
			nb++;

		i = pos + nbCol + 1;
		if ((i < tableBtn.length) && (i >= 0) && ((pos + 1) % nbCol != 0)
				&& (tableBtn[i].contientMine == true))
			nb++;

		i = pos + nbCol - 1;
		if ((i < tableBtn.length) && (pos % nbCol != 0) && (i >= 0)
				&& (tableBtn[i].contientMine == true))
			nb++;

		i = pos - nbCol - 1;
		if ((i < tableBtn.length) && (i >= 0) && (pos % nbCol != 0)
				&& (tableBtn[i].contientMine == true))
			nb++;

		i = pos - nbCol + 1;
		if ((i < tableBtn.length) && (i >= 0) && ((pos + 1) % nbCol != 0)
				&& (tableBtn[i].contientMine == true))
			nb++;

		return (nb);
	}

	private void testClick(ActionEvent evt) {
		Object btn;
		RfButtons rf;
		btn = evt.getSource();
		int pos;
		rf = (RfButtons) btn;
		pos = rf.pos;
		if(rf.marque==false)
		  jouer(pos);
	}

	private void jouer(int pos) {
		if (finJeu == false) {
			if (tableBtn[pos].contientMine) {
				ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource("img/6.gif"));
				btnRejouer.setIcon(img);
	            afficherMines(true);
				finJeu = true;
				if(activerSon){
				  playLooseSound();
				}
			} else {
				int pile[] = new int[tableBtn.length * 2];
				int p = 0;
				int i, nb;
				pile[0] = pos;
				while (p >= 0) {
					pos = pile[p];
					tableBtn[pos].setBorder(new SoftBevelBorder(
							BevelBorder.LOWERED, null, null, null, null));
					tableBtn[pos].setBackground(new java.awt.Color(255, 255,
							255));
					if (tableBtn[pos].inc == false) {
						tableBtn[pos].setInc();
						score++;
					}
					nb = nbrMines(pos);
					if (nb > 0) {
						tableBtn[pos].setText("" + nb);
						switch (nb) {
						case 1:
							tableBtn[pos].setForeground(new java.awt.Color(0,
									0, 255));
							break;
						case 2:
							tableBtn[pos].setForeground(new java.awt.Color(0,
									128, 0));
							break;
						case 3:
							tableBtn[pos].setForeground(new java.awt.Color(255,
									0, 0));
							break;
						case 4:
							tableBtn[pos].setForeground(new java.awt.Color(0,
									0, 128));
							break;
						case 5:
							tableBtn[pos].setForeground(new java.awt.Color(128,
									0, 0));
							break;
						case 6:
							tableBtn[pos].setForeground(new java.awt.Color(0,
									255, 0));
							break;
						case 7:
							tableBtn[pos].setForeground(new java.awt.Color(128,
									128, 0));
							break;
						case 8:
							tableBtn[pos].setForeground(new java.awt.Color(0,
									128, 128));
							break;
						}
					}
					p--;
					if (nb == 0) {
						i = pos + 1;
						if ((i < tableBtn.length)
								&& (i >= 0)
								&& ((pos + 1) % nbCol != 0)
								&& (tableBtn[i].contientMine == false)
								&&(tableBtn[i].marque==false)
								&& (tableBtn[i].isEnabled() && (tableBtn[i].dsTable == false))) {
							p++;
							pile[p] = tableBtn[i].pos;
							tableBtn[i].setDansTable();
						}
						i = pos - 1;
						if ((i < tableBtn.length) && (i >= 0)
								&& (pos % nbCol != 0)
								&& (tableBtn[i].contientMine == false)
								&& (tableBtn[i].isEnabled())
								&&(tableBtn[i].marque==false)
								&& (tableBtn[i].dsTable == false)) {
							p++;
							pile[p] = tableBtn[i].pos;
							tableBtn[i].setDansTable();
						}
						i = pos + nbCol;
						if ((i < tableBtn.length) && (i >= 0)
								&& (tableBtn[i].contientMine == false)
								&& (tableBtn[i].isEnabled())
								&&(tableBtn[i].marque==false)
								&& (tableBtn[i].dsTable == false)) {
							p++;
							pile[p] = tableBtn[i].pos;
							tableBtn[i].setDansTable();
						}
						i = pos - nbCol;
						if ((i < tableBtn.length) && (i >= 0)
								&& (tableBtn[i].contientMine == false)
								&& (tableBtn[i].isEnabled())
								&&(tableBtn[i].marque==false)
								&& (tableBtn[i].dsTable == false)) {
							p++;
							pile[p] = tableBtn[i].pos;
							tableBtn[i].setDansTable();
						}

						i = pos + nbCol + 1;
						if ((i < tableBtn.length) && (i >= 0)
								&& ((pos + 1) % nbCol != 0)
								&& (tableBtn[i].contientMine == false)
								&& (tableBtn[i].isEnabled())
								&&(tableBtn[i].marque==false)
								&& (tableBtn[i].dsTable == false)) {
							p++;
							pile[p] = tableBtn[i].pos;
							tableBtn[i].setDansTable();
						}

						i = pos + nbCol - 1;
						if ((i < tableBtn.length) && (pos % nbCol != 0)
								&& (i >= 0)
								&& (tableBtn[i].contientMine == false)
								&& (tableBtn[i].isEnabled())
								&&(tableBtn[i].marque==false)
								&& (tableBtn[i].dsTable == false)) {
							p++;
							pile[p] = tableBtn[i].pos;
							tableBtn[i].setDansTable();
						}

						i = pos - nbCol - 1;
						if ((i < tableBtn.length) && (i >= 0)
								&& (pos % nbCol != 0)
								&& (tableBtn[i].contientMine == false)
								&& (tableBtn[i].isEnabled())
								&&(tableBtn[i].marque==false)
								&& (tableBtn[i].dsTable == false)) {
							p++;
							pile[p] = tableBtn[i].pos;
							tableBtn[i].setDansTable();
						}

						i = pos - nbCol + 1;
						if ((i < tableBtn.length) && (i >= 0)
								&& ((pos + 1) % nbCol != 0)
								&& (tableBtn[i].contientMine == false)
								&& (tableBtn[i].isEnabled())
								&&(tableBtn[i].marque==false)
								&& (tableBtn[i].dsTable == false)) {
							p++;
							pile[p] = tableBtn[i].pos;
							tableBtn[i].setDansTable();
						}
					}// fin if
				}// fin while
				if (score >= nbCol * nbLig - nbMinesF) {
					afficherMines(false);
					finJeu = true;
					lbl3.setText("0");
					ImageIcon img=new ImageIcon(getClass().getClassLoader().getResource("img/5.gif"));
					btnRejouer.setIcon(img);
					if((rdPersonnalise.isSelected()==false)&&(chargerPilote())&&(etablirConnxion())){
					  try{
						int sc=Integer.parseInt(lblChrono.getText());
						String niveau="Debutant";
				        if(rdExpert.isSelected())
				    	  niveau="Expert";
				        else
				    	  if(rbIntermediaire.isSelected())
				    	 	niveau="Intermediaire";
						Statement st=con.createStatement();
						ResultSet r=st.executeQuery("select * from tabscores where score>"+sc+" and niveau='"+niveau+"';");
						if(r.next()){
					        String nom=JOptionPane.showInputDialog("Entrer votre nom :");
						    lancerRequete(nom,niveau,sc);
						 }
					  }catch(Exception e){
						  
					  }
					}
				}
			}
		}
	}

	private boolean marquerBouton(MouseEvent evt){
		if(evt.getButton()!=3)
			return(false);
		RfButtons b;
		b = (RfButtons) evt.getSource();
		if((finJeu==false)&&(b.inc==false)){
			ImageIcon img=new ImageIcon(getClass().getClassLoader().getResource("img/2.gif"));
			if(b.marque){
				b.setIcon(null);
				nbMines++;
				lbl3.setText(""+nbMines);
				b.marque=false;
			}
			else{
			  b.setIcon(img);
			  nbMines--;
			  lbl3.setText(""+nbMines);
			  b.marque=true;
			}
		}
		return(true);
	}

	private void nouveau() {
		if (etatTemps == true) {
			t.stop();
			etatTemps = false;
		}
		tempsPasse = 0;
		lblChrono.setText("0");
		debutJeu = false;
		score = 0;
		finJeu = false;
		this.nbMines=this.nbMinesF;
		lbl3.setText(""+nbMines);
		for (int i = 0; i < tableBtn.length; i++) {
			tableBtn[i].setEnabled(true);
			tableBtn[i].setBackground(new java.awt.Color(100, 100, 100));
			tableBtn[i].setText("");
			tableBtn[i].setBorder(BorderFactory
					.createCompoundBorder(null, new LineBorder(
							new java.awt.Color(150, 150, 150), 1, false)));
			tableBtn[i].contientMine = false;
			tableBtn[i].dsTable = false;
			tableBtn[i].inc = false;
			tableBtn[i].marque=false;
			tableBtn[i].setIcon(null);
		}
		initTabMines();
	}

	@SuppressWarnings("deprecation")
	private void debutant() {
		t.stop();
		dispose();
		InterfaceUser inst = new InterfaceUser(9, 9, 10);
		inst.setSize(inst.largeur, inst.longueur);
		inst.setTitle("MinesWeeper");
		inst.setLocationRelativeTo(null);
		inst.setVisible(true);
		inst.setResizable(false);
		inst.rdDebutant.setSelected(true);
		inst.selection = 1;
	}

	@SuppressWarnings("deprecation")
	private void intermediaire() {
		t.stop();
		dispose();
		InterfaceUser inst = new InterfaceUser(16, 16, 40);
		inst.setSize(inst.largeur, inst.longueur);
		inst.setTitle("MinesWeeper");
		inst.setLocationRelativeTo(null);
		inst.setVisible(true);
		inst.setResizable(false);
		inst.rbIntermediaire.setSelected(true);
		inst.selection = 2;
	}

	@SuppressWarnings("deprecation")
	private void expert() {
		t.stop();
		dispose();
		InterfaceUser inst = new InterfaceUser(30, 16, 99);
		inst.setSize(inst.largeur, inst.longueur);
		inst.setTitle("MinesWeeper");
		inst.setLocationRelativeTo(null);
		inst.setVisible(true);
		inst.setResizable(false);
		inst.rdExpert.setSelected(true);
		inst.selection = 3;
	}

	private void personnalisee() {
		PartiePersonnalisee inst = new PartiePersonnalisee(this);
		inst.setLocationRelativeTo(null);
		inst.setModal(true);
		inst.setSize(190, 180);
		inst.setVisible(true);
		inst.setTitle("Champs personnalisé");
		inst.setResizable(false);
		if (selection == 1)
			rdDebutant.setSelected(true);
		else {
			if (selection == 2)
				rbIntermediaire.setSelected(true);
			else
				rdExpert.setSelected(true);
		}
	}

	private void rejouer() {
		if (etatTemps == true) {
			t.stop();
			etatTemps = false;
		}
		ImageIcon img =new ImageIcon(getClass().getClassLoader().getResource("img/4.gif"));
		btnRejouer.setIcon(img);
		tempsPasse = 0;
		lblChrono.setText("0");
		debutJeu = false;
		score = 0;
		finJeu = false;
		this.nbMines=this.nbMinesF;
		lbl3.setText(""+nbMines);
		for (int i = 0; i < tableBtn.length; i++) {
			tableBtn[i].setEnabled(true);
			tableBtn[i].setBackground(new java.awt.Color(100, 100, 100));
			tableBtn[i].setText("");
			tableBtn[i].dsTable = false;
			tableBtn[i].setBorder(BorderFactory
					.createCompoundBorder(null, new LineBorder(
							new java.awt.Color(150, 150, 150), 1, false)));
			tableBtn[i].setIcon(null);
			tableBtn[i].inc = false;
			tableBtn[i].marque=false;
		}
	}

	private void quitter() {
		System.exit(0);
	}

	private ButtonGroup getBtnGroupeNiveau() {
		if (btnGroupeNiveau == null) {
			btnGroupeNiveau = new ButtonGroup();
		}
		return btnGroupeNiveau;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				InterfaceUser inst = new InterfaceUser(9, 9, 10);
				inst.setSize(inst.largeur, inst.longueur);
				inst.setTitle("Minesweeper");
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.setResizable(false);
			}
		});
	}

	private JMenu getMenuAide() {
		if(menuAide == null) {
			menuAide = new JMenu();
			menuAide.setText("Aide");
			menuAide.add(getMenuAideSommaire());
			menuAide.add(getMenuAidePropos());
		}
		return menuAide;
	}

	private JMenuItem getMenuAideSommaire() {
		if(menuAideSommaire == null) {
			menuAideSommaire = new JMenuItem();
			menuAideSommaire.setText("Sommaire");
		}
		return menuAideSommaire;
	}

	private JMenuItem getMenuAidePropos() {
		if(menuAidePropos == null) {
			menuAidePropos = new JMenuItem();
			menuAidePropos.setText("About MinesWeeper");
			menuAidePropos.add(getMenuAideSeparateur1());
		}
		return menuAidePropos;
	}

	private JSeparator getMenuAideSeparateur1() {
		if(menuAideSeparateur1 == null) {
			menuAideSeparateur1 = new JSeparator();
		}
		return menuAideSeparateur1;
	}
	private boolean chargerPilote(){
		try{
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			return(true);
		}catch(Exception e){
			return(false);
		}	
	}//fin chargerPilote

	public boolean etablirConnxion(){
		try{
			con=DriverManager.getConnection(DBurl,NOM_USER,MP);
			return(true);
		}catch(SQLException e){
			return(false);
		}
	}//fin etablirConnexion

	public static void lancerRequete(String nom,String niveau,int sc){
		try{
			Statement st=con.createStatement();
			  st.executeUpdate("delete from tabscores where niveau='"+niveau+"';");
			  st.executeUpdate("insert into tabscores(nom,niveau,score) values('"+nom+"','"+niveau+"',"+sc+");");
		}catch(Exception e){}
	}

	private void actionSon() {
		if(checkSon.isSelected())
			activerSon=true;
		else
			activerSon=false;
	}
	
	private void meilleurTemps() {
		MeilleurScores inst = new MeilleurScores(this);
		inst.setModal(true);
		inst.setSize(410, 160);
		inst.setResizable(false);
		inst.setTitle("Best MinesWeepers");
		inst.setLocationRelativeTo(this);
		inst.setVisible(true);	
		
	}
	private void mouseReleased1(){
		if(finJeu==false){
		  ImageIcon img=new ImageIcon(getClass().getClassLoader().getResource("img/4.gif"));
		  btnRejouer.setIcon(img);
		}
	}
	private void mousePressed1(){
		if(finJeu==false){
		  ImageIcon img=new ImageIcon(getClass().getClassLoader().getResource("img/7.gif"));
		  btnRejouer.setIcon(img);
		}
	}

	private void playLooseSound(){
		try {
			File soundFile = new File(getClass().getClassLoader().getResource("sound/loose.wav").getFile());
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(soundFile));
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Impossible d'activer les effets sonores");
		}
	}
}