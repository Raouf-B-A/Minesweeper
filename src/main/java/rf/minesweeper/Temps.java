package rf.minesweeper;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Raouf BEN AOUISSI on 12/02/2010.
 */
public class Temps extends Thread {
	AudioClip c;
	Temps() {

//		c=Applet.newAudioClip(getClass().getClassLoader().getResource("sound/time.wav"));

	}

	public void run() {
		while (InterfaceUser.debutJeu == false) ;

		while (InterfaceUser.finJeu == false) {
			try {
				//c.play();
				InterfaceUser.tempsPasse++;
				InterfaceUser.lblChrono.setText("" + InterfaceUser.tempsPasse);
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
