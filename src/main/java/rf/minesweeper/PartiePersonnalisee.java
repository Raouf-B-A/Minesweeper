package rf.minesweeper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Created by Raouf BEN AOUISSI on 12/02/2010.
 */
public class PartiePersonnalisee extends javax.swing.JDialog {

	private JLabel lblHauteur;
	private JTextField txtHauteur;
	private JTextField txtMines;
	private JButton btnAnnuler;
	private JButton btnOk;
	private JTextField txtLargeur;
	private JLabel lblMines;
	private JLabel lblLargeur;
	private InterfaceUser i;

	public PartiePersonnalisee(InterfaceUser i) {
		super(i);
		this.i = i;
		initGUI();
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			{
				lblHauteur = new JLabel();
				getContentPane().add(lblHauteur);
				lblHauteur.setText("hauteur :");
				lblHauteur.setBounds(19, 28, 50, 14);
			}
			{
				lblLargeur = new JLabel();
				getContentPane().add(lblLargeur);
				lblLargeur.setText("largeur :");
				lblLargeur.setBounds(19, 59, 50, 14);
			}
			{
				lblMines = new JLabel();
				getContentPane().add(lblMines);
				lblMines.setText("mines :");
				lblMines.setBounds(19, 91, 50, 14);
			}
			{
				txtHauteur = new JTextField();
				getContentPane().add(txtHauteur);
				txtHauteur.setBounds(81, 25, 42, 21);
			}
			{
				txtLargeur = new JTextField();
				getContentPane().add(txtLargeur);
				txtLargeur.setBounds(81, 56, 42, 21);
			}
			{
				txtMines = new JTextField();
				getContentPane().add(txtMines);
				txtMines.setBounds(81, 88, 42, 21);
			}
			{
				btnOk = new JButton();
				getContentPane().add(btnOk);
				btnOk.setText("Ok");
				btnOk.setBounds(7, 124, 81, 21);
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						ok();
					}
				});
			}
			{
				btnAnnuler = new JButton();
				getContentPane().add(btnAnnuler);
				btnAnnuler.setText("Annuler");
				btnAnnuler.setBounds(93, 124, 87, 21);
				btnAnnuler.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						annuler();
					}
				});
			}
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void annuler() {
		dispose();
	}

	private void ok() {
		try {
			int col, lig, mines;
			col = Integer.parseInt(txtHauteur.getText());
			lig = Integer.parseInt(txtLargeur.getText());
			mines = Integer.parseInt(txtMines.getText());
			if (col > 50)
				col = 50;
			else if (col < 9)
				col = 9;
			if (lig > 40)
				lig = 40;
			else if (lig < 9)
				lig = 9;
			if (mines > col * lig - 10)
				mines = col * lig - 10;
			else if (mines < 10)
				mines = 10;
			i.t.stop();
			InterfaceUser inst = new InterfaceUser(col, lig, mines);
			inst.setSize(inst.largeur, inst.longueur);
			inst.setTitle("Minesweeper");
			inst.setLocationRelativeTo(null);
			inst.setVisible(true);
			inst.setResizable(false);
			inst.rdPersonnalise.setSelected(true);
			i.dispose();
			dispose();
		} catch (Exception e) {
			txtHauteur.setText("");
			txtLargeur.setText("");
			txtMines.setText("");
			JOptionPane.showMessageDialog(this,"Les valeurs des champs Largeur, Hauteur et Mines doivent etre numeriques !!!");
		}
	}
	
}

