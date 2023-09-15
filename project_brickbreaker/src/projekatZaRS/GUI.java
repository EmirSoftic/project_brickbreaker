package projekatZaRS;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class GUI {
	public int mapa[][];
	public int sirina_cigle;
	public int visina_cigle;
	public GUI(int redovi, int kolone) {
		mapa = new int[redovi][kolone];
		for(int i = 0; i < mapa.length; i++) {
			for(int j = 0; j < mapa[0].length; j++) {
				//1 znaci da je cigla na poziciji i,j prisutna, te na ovaj nacin odrzavam koje cigle nisu jos srusene
				mapa[i][j] = 1;
			}
		}
		sirina_cigle = 540/kolone;
		visina_cigle = 150/redovi;
	}
	public void crtaj(Graphics2D g) {
		for(int i = 0; i < mapa.length; i++) {
			for(int j = 0; j < mapa[0].length; j++) {
				if(mapa[i][j] > 0) {
					g.setColor(Color.gray);
					g.fillRect(j*sirina_cigle + 80, i * visina_cigle + 50 , sirina_cigle, visina_cigle);
					
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j*sirina_cigle + 80, i * visina_cigle + 50 , sirina_cigle, visina_cigle);
				}
			}
		}
	}
	public void staviCiglu(int vrijednost, int red, int kolona) {
		mapa[red][kolona] = vrijednost;
	}
}
