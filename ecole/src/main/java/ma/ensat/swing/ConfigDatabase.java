package ma.ensat.swing;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ma.ensat.model.dao.ConnectionMysql;

public class ConfigDatabase extends JFrame {
	JLabel urlConnectionLabel;
	JTextField urlConnection;
	JLabel message;
	String urlExample = "jdbc:mysql://{HOST}:{PORT}/{DATABASE}?user={LOGIN}&password={PASSWORD}";
	Box box;
	JButton submit;

	public ConfigDatabase() { // configuration de l'interface
		setTitle("Configuration de la connection vers la base MySQL");
		setSize(600, 200);
		setLayout(new GridLayout(2, 2));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		box = Box.createVerticalBox();
		urlConnectionLabel = new JLabel("Url de connexion MySQL : ");
		message = new JLabel("example : " + urlExample);
		urlConnection = new JTextField(
				"jdbc:mysql://localhost:3306/ecole?user=root&password=", 100);
		urlConnection.setHorizontalAlignment(JTextField.CENTER);
		submit = new JButton("Valider");
		box.add(urlConnectionLabel);
		box.add(urlConnection);
		box.add(submit);
		box.add(message);
		add(box);
		setVisible(true);
	}

	public void start() {
		// ajout d'un listner sur le clique du bouton submit, si la connexion
		// s'etabli on passe au formulaire d'etudiant sinon on affiche l'erreur
		// dans le block catch
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stringURL = urlConnection.getText();
				if (stringURL != null && stringURL.length() > 0) {
					try {
						// creation d'une instance de connexion
						ConnectionMysql.getInstance(stringURL);
						setVisible(false);
						GererEtudiant gererEtudiant = new GererEtudiant();
						gererEtudiant.setVisible(true);
					} catch (Exception e1) {
						message.setText(e1.getMessage());
						e1.printStackTrace();
					}
				} else {
					message.setText("url invalide");
				}
			}
		});

	}

	public static void main(String args[]) {
		new ConfigDatabase().start();
	}

}
