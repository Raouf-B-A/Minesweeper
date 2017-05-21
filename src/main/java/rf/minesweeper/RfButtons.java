package rf.minesweeper;

import javax.swing.JButton;

/**
 * Created by Raouf BEN AOUISSI on 12/02/2010.
 */
public class RfButtons extends JButton {
	int pos;
	boolean contientMine = false;
	boolean dsTable = false;
	boolean inc = false;
	boolean marque=false;

	void setPos(int pos) {
		this.pos = pos;
	}

	void setContientMine() {
		contientMine = true;
	}

	void setDansTable() {
		this.dsTable = true;
	}

	void setInc() {
		inc = true;
	}
}