package rf.minesweeper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Created by Raouf BEN AOUISSI on 12/02/2010.
 */
public class MeilleurScores extends javax.swing.JDialog {
	final static String NOM_BD="bddemineur";
	final static String ServeurBD="localhost";
	final static String NOM_USER="root";
	final static String MP="";
	private String DBurl="jdbc:mysql://"+ServeurBD+"/"+NOM_BD;
	private Connection con;
	private JButton btnEff;
	private JLabel lblDebutant;
	private JLabel lblExp;
	private JLabel lblT2;
	private JLabel lblT3;
	private JButton btnOK;
	private JLabel lblN3;
	private JLabel lblN2;
	private JLabel lblN1;
	private JLabel lblT1;
	private JLabel lblInter;
	
	public MeilleurScores(JFrame frame) {
		super(frame);
		if((chargerPilote())&&(etablirConnexion())){
	      initGUI();
	      scores();
	    }else{
	      dispose();
		  JOptionPane.showMessageDialog(this, "Impossible d'afficher les scores pour le moment\nessayez plu tard");
	    }
	}
	
	private void initGUI() {
		try {
			getContentPane().setLayout(null);
			{
				lblDebutant = new JLabel();
				getContentPane().add(lblDebutant);
				lblDebutant.setText("D�butant  :");
				lblDebutant.setBounds(12, 19, 87, 14);
			}
			{
				lblInter = new JLabel();
				getContentPane().add(lblInter);
				lblInter.setText("Interm�diaire :");
				lblInter.setBounds(12, 51, 93, 14);
			}
			{
				lblExp = new JLabel();
				getContentPane().add(lblExp);
				lblExp.setText("Expert :");
				lblExp.setBounds(12, 81, 73, 14);
			}
			{
				lblT1 = new JLabel();
				getContentPane().add(lblT1);
				lblT1.setBounds(111, 19, 102, 14);
			}
			{
				lblT2 = new JLabel();
				getContentPane().add(lblT2);
				lblT2.setBounds(111, 51, 102, 14);
			}
			{
				lblT3 = new JLabel();
				getContentPane().add(lblT3);
				lblT3.setBounds(111, 81, 102, 16);
			}
			{
				lblN1 = new JLabel();
				getContentPane().add(lblN1);
				lblN1.setBounds(247, 19, 104, 14);
			}
			{
				lblN2 = new JLabel();
				getContentPane().add(lblN2);
				lblN2.setBounds(247, 51, 104, 14);
			}
			{
				lblN3 = new JLabel();
				getContentPane().add(lblN3);
				lblN3.setBounds(247, 81, 104, 16);
			}
			{
				btnOK = new JButton();
				getContentPane().add(btnOK);
				btnOK.setText("OK");
				btnOK.setBounds(240, 103, 55, 21);
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						dispose();
					}
				});
			}
			{
				btnEff = new JButton();
				getContentPane().add(btnEff);
				btnEff.setText("Effacer tous les scores");
				btnEff.setBounds(30, 103, 180, 21);
				btnEff.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						effacerTout();
					}
				});
			}
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private boolean chargerPilote(){
		try{
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			return(true);
		}catch(Exception e){
			return(false);
		}	
	}//fin chargerPilote
	
	private boolean etablirConnexion(){
		try{
			con=DriverManager.getConnection(DBurl,NOM_USER,MP);
			return(true);
		}catch(SQLException e){
			return(false);
		}
	}//fin etablirConnexion
	private void scores() {
		try{
			Statement st=con.createStatement();
			ResultSet r= st.executeQuery("select *from tabscores;");
		    while(r.next()){
		    	String nom,niveau;
		    	int sc;
		    	nom=r.getString("nom");
		    	niveau=r.getString("niveau");
		    	sc=r.getInt("score");
		    	if(niveau.equals("Debutant")){
		    		lblN1.setText(nom);
		    		lblT1.setText(sc+" Secondes");
		    	}else{
		    		if(niveau.equals("Intermediaire")){
		    			lblN2.setText(nom);
		    			lblT2.setText(sc+" Secondes");
		    		}else{
		    			if(niveau.equals("Expert")){
		    				lblN3.setText(nom);
		    				lblT3.setText(sc+" Secondes");
		    			}
		    		}
		    	}
		    }
		}catch(Exception e){}		
	}
	
	private void effacerTout() {
		try{
			Statement st=con.createStatement();
			  st.executeUpdate("update tabscores set nom='Anonyme' ;");
			  st.executeUpdate("update tabscores set score=999 ;");
			  scores();
		}catch(Exception e){}
	}

}