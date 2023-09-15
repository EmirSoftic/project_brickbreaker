
package projekatZaRS;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame prozor = new JFrame();
		Logika igra = new Logika();
		prozor.setSize(700,600);
		prozor.setTitle("Brick breaker");
		prozor.setResizable(false);
		prozor.setVisible(true);
		prozor.setLocationRelativeTo(null);
		prozor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		prozor.add(igra);
		}

}
