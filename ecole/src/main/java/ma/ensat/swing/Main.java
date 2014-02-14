package ma.ensat.swing;

import java.awt.EventQueue;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new ConfigDatabase().start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
