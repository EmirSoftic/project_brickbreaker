package projekatZaRS;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.JPanel;

public class Logika extends JPanel implements KeyListener, ActionListener {
	private boolean igraj = false;
	private int zbir_bodova = 0;
	private int broj_cigli = 21;
	private Timer tajmer;
	private int odlaganje = 1;
	private int igrac = 310;
	private int loptica_pozicija;
	private int loptica_pozicija_y;
	private int loptica_putanja = -1;
	private int loptica_putanja_y = -2;
	private GUI gui;
	
	public int randomBrojZaX() {
		   Random rand = new Random();
		   int randomNum = rand.nextInt(292) + 200;
		   return randomNum;
		}
	public int randomBrojZaY() {
		   Random rand = new Random();
		   int randomNum = rand.nextInt(192) + 200;
		   return randomNum;
		}
	
	public Logika() {
		gui = new GUI(3,7);
		addKeyListener(this);
		setFocusable(true);
		this.loptica_pozicija = randomBrojZaX();
		this.loptica_pozicija_y = randomBrojZaY();
		setFocusTraversalKeysEnabled(false);
		tajmer = new Timer(odlaganje,this);
		tajmer.start();
		}
	
	public void paint(Graphics g) {
		//podloga
		g.setColor(Color.black);
		g.fillRect(1,1,692,592);
		gui.crtaj((Graphics2D)g);
		//3 okvira crvena, donjeg nema jer mi je to uslov kraja.
		g.setColor(Color.red);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(684, 0, 3, 592);
		
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString("Rezultat je: "+zbir_bodova, 500, 30);
		//podloga za odbijanje
		g.setColor(Color.blue);
		g.fillRect(igrac, 550, 100, 8);
		//lopta
		g.setColor(Color.red);
		g.fillOval(loptica_pozicija, loptica_pozicija_y, 20, 20);
		
		if(broj_cigli <= 0) {
			igraj = false;
			loptica_putanja = 0;
			loptica_putanja_y = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("POBIJEDILI STE", 250, 300);
			g.setFont(new Font("serif",Font.BOLD, 20));
			g.drawString("Kliknite enter za ponovnu igru", 230, 350);
		}
		
		if(loptica_pozicija_y > 570) {
			igraj = false;
			loptica_putanja = 0;
			loptica_putanja_y = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("IZGUBILI STE", 250, 300);
			g.setFont(new Font("serif",Font.BOLD, 20));
			g.drawString("Kliknite enter za ponovnu igru", 230, 350);
		}
		
		g.dispose();
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//tajmer pocinje, i stanje novo se ponovo slika nakon svake akcije
		tajmer.start();
		
		//kretanje loptice
		if(igraj) {
			if(new Rectangle(loptica_pozicija, loptica_pozicija_y, 20, 20).intersects(new Rectangle(igrac,550,100,8))) {
				loptica_putanja_y = -loptica_putanja_y;
			}
			
			A: for(int i = 0; i < gui.mapa.length; i++) {
				for(int j = 0; j < gui.mapa[0].length; j++) {
					if(gui.mapa[i][j] > 0) {
						int cigla = j*gui.sirina_cigle + 80;
						int cigla_y = i * gui.visina_cigle + 50;
						int sirina_cigle = gui.sirina_cigle;
						int visina_cigle = gui.visina_cigle;
						
						Rectangle rect = new Rectangle(cigla,cigla_y,sirina_cigle, visina_cigle);
						Rectangle loptica = new Rectangle(loptica_pozicija, loptica_pozicija_y,20,20);
						Rectangle cigla_rect = rect;
						
						if(loptica.intersects(cigla_rect)) {
							//ova cigla se nisti
							gui.staviCiglu(0,i,j);
							broj_cigli--;
							zbir_bodova += 5;
							if(loptica_pozicija + 19 <= cigla_rect.x || loptica_pozicija + 1 >= cigla_rect.x + cigla_rect.width) {
								loptica_putanja = -loptica_putanja;
							} else {
								loptica_putanja_y = -loptica_putanja_y;
							}
							//labela A je for petlja
							break A;
						}
					}
				}
			}
			
			//Diranje ivica
			loptica_pozicija += loptica_putanja;
			loptica_pozicija_y += loptica_putanja_y;
			if(loptica_pozicija < 0) {
				loptica_putanja = -loptica_putanja;
			}
			if(loptica_pozicija_y < 0) {
				loptica_putanja_y = -loptica_putanja_y;
			}
			if(loptica_pozicija > 670) {
				loptica_putanja = -loptica_putanja;
			}
		}
		
		repaint();
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(igrac >= 600) {
				//ne smije se pomjerati preko ove granice jer je to ispadanje
				igrac = 600;
			} else {
				pomjeriDesno();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(igrac <= 0) {
				//ne smije se pomjerati preko ove granice jer je to ispadanje
				igrac = 0;
			} else {
				pomjeriLijevo();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!igraj) {
				igraj = true;
				loptica_pozicija = this.randomBrojZaX();
				loptica_pozicija_y = this.randomBrojZaY();
				loptica_putanja = -1;
				loptica_putanja_y = -2;
				igrac = 310;
				zbir_bodova = 0;
				broj_cigli = 21;
				gui = new GUI(3,7);
				
				repaint();
			}
		}
	}
	
	public void pomjeriDesno() {
		//poveca se za 15, moze i vise.. ili manje jer ide desno
		igraj = true;
		igrac += 15;
	}
	public void pomjeriLijevo() {
		//smanji se za 15, moze i vise.. ili manje jer ide lijevo
		igraj = true;
		igrac -= 15;
	}
}
