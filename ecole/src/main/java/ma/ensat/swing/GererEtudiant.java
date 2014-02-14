package ma.ensat.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import ma.ensat.model.dao.EtudiantDao;
import ma.ensat.model.om.Etudiant;
import ma.ensat.model.om.Option;
import ma.ensat.utils.EcoleUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GererEtudiant extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Object[][] dataSetEtudiants = null;
	EtudiantDao etudiantDao = null;
	Etudiant etudiant = null;
	List<Etudiant> listeEtudiants = null;
	private JTextField nom;
	private Box horizontalBoxNom;
	private Box horizontalBoxPrenom;
	private JLabel labelPrenom;
	private JTextField prenom;
	private Box horizontalBoxDateNaissance;
	private JLabel labelDateNaissance;
	private Box horizontalBoxOption;
	private JLabel labelOption;
	private JButton btnAjouter;
	private Box horizontalBox;
	private JButton btnModifier;
	private JButton btnSupprimer;
	private JComboBox<Option> option;
	private JLabel lbspace1;
	private JLabel lbspace2;
	private Box horizontalBoxRecherche;
	private JLabel labelRechNom;
	private JTextField rechNom;
	private JLabel labelRechPrenom;
	private JTextField rechPrenom;
	private JButton btnRechercher;
	private JComboBox<String> dateJour;
	private JComboBox<String> dateMois;
	private JComboBox<String> dateAnnee;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private Box horizontalBoxId;
	private JLabel labelId;
	private JTextField id;
	private JButton btnViderFormulaire;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GererEtudiant frame = new GererEtudiant();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GererEtudiant() throws Exception {
		setTitle("Gestion des \u00E9tudiants de l'ENSAT");
		etudiantDao = new EtudiantDao();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 579, 515);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		listeEtudiants = etudiantDao.tousLesEtudiants();
		dataSetEtudiants = EcoleUtils.toTableauGrid(listeEtudiants);
		// initialiser le tableau avec les données de tous les etudiants et
		// l'entete a afficher
		table = new JTable(dataSetEtudiants, Etudiant.ENTETE);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		Box verticalBox = Box.createVerticalBox();
		contentPane.add(verticalBox, BorderLayout.NORTH);

		horizontalBoxId = Box.createHorizontalBox();
		verticalBox.add(horizontalBoxId);

		labelId = new JLabel("Id                                :");
		horizontalBoxId.add(labelId);

		id = new JTextField();
		id.setEditable(false);
		id.setColumns(5);
		horizontalBoxId.add(id);

		label_4 = new JLabel("   ");
		verticalBox.add(label_4);

		horizontalBoxNom = Box.createHorizontalBox();
		verticalBox.add(horizontalBoxNom);

		JLabel labelNom = new JLabel("Nom                           :");
		horizontalBoxNom.add(labelNom);

		nom = new JTextField();
		horizontalBoxNom.add(nom);
		nom.setColumns(5);

		label = new JLabel("   ");
		verticalBox.add(label);

		horizontalBoxPrenom = Box.createHorizontalBox();
		verticalBox.add(horizontalBoxPrenom);

		labelPrenom = new JLabel("Pr\u00E9nom                     :");
		horizontalBoxPrenom.add(labelPrenom);

		prenom = new JTextField();
		prenom.setColumns(5);
		horizontalBoxPrenom.add(prenom);

		label_1 = new JLabel("   ");
		verticalBox.add(label_1);

		horizontalBoxDateNaissance = Box.createHorizontalBox();
		verticalBox.add(horizontalBoxDateNaissance);

		labelDateNaissance = new JLabel("Date de naissance :");
		horizontalBoxDateNaissance.add(labelDateNaissance);

		dateJour = new JComboBox<String>();
		dateJour.setModel(new DefaultComboBoxModel(listeJours()));
		horizontalBoxDateNaissance.add(dateJour);

		dateMois = new JComboBox<String>();
		dateMois.setModel(new DefaultComboBoxModel(listeMois()));
		horizontalBoxDateNaissance.add(dateMois);

		dateAnnee = new JComboBox<String>();
		dateAnnee.setModel(new DefaultComboBoxModel(listeAnnes()));
		horizontalBoxDateNaissance.add(dateAnnee);

		label_2 = new JLabel("   ");
		verticalBox.add(label_2);

		horizontalBoxOption = Box.createHorizontalBox();
		verticalBox.add(horizontalBoxOption);

		labelOption = new JLabel("Option                        :");
		horizontalBoxOption.add(labelOption);

		option = new JComboBox<Option>();
		option.setModel(new DefaultComboBoxModel(Option.getOptions()));
		horizontalBoxOption.add(option);

		lbspace1 = new JLabel("   ");
		verticalBox.add(lbspace1);

		horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);

		btnAjouter = new JButton("Ajouter");

		// traitement en cas de clique sur ajouter
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Calendar dateNaissance = Calendar.getInstance();
				String anneeSelected = (String) dateAnnee.getSelectedItem();
				String moisSelected = (String) dateMois.getSelectedItem();
				String jourSelected = (String) dateJour.getSelectedItem();
				dateNaissance.set(new Integer(anneeSelected), new Integer(moisSelected) - 1, new Integer(jourSelected));
				etudiant = new Etudiant();
				etudiant.setNom(nom.getText());
				etudiant.setPrenom(prenom.getText());
				etudiant.setDateNaissance(dateNaissance.getTime());
				etudiant.setOption(option.getSelectedItem().toString());
				// appel au DAO pour sauvegarder l'utilisateur
				try {
					etudiantDao.ajouterEtudiant(etudiant);
					// rafraichir les données du tableau
					rafraichirTableau();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				viderFormulaire();

			}
		});
		horizontalBox.add(btnAjouter);

		btnModifier = new JButton("Modifier");
		// en cas de clique sur boutton modifier
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Calendar dateNaissance = Calendar.getInstance();
				dateNaissance.set(new Integer((String) dateAnnee.getSelectedItem()), new Integer((String) dateMois.getSelectedItem()) - 1,
						new Integer((String) dateJour.getSelectedItem()));
				etudiant = new Etudiant();
				etudiant.setId(Integer.parseInt(id.getText()));
				etudiant.setNom(nom.getText());
				etudiant.setPrenom(prenom.getText());
				etudiant.setDateNaissance(dateNaissance.getTime());
				etudiant.setOption(option.getSelectedItem().toString());
				// appel au DAO pour sauvegarder l'utilisateur
				try {
					etudiantDao.modifierEtudiant(etudiant);
					// rafraichir les données du tableau
					rafraichirTableau();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		btnModifier.setEnabled(false);
		horizontalBox.add(btnModifier);

		btnSupprimer = new JButton("Supprimer");
		btnSupprimer.setEnabled(false);
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					etudiantDao.supprimerEtudiant(Integer.parseInt(id.getText()));
					rafraichirTableau();
					viderFormulaire();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		horizontalBox.add(btnSupprimer);

		btnViderFormulaire = new JButton("Vider formulaire");
		btnViderFormulaire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viderFormulaire();

			}
		});
		horizontalBox.add(btnViderFormulaire);

		lbspace2 = new JLabel("   ");
		verticalBox.add(lbspace2);

		horizontalBoxRecherche = Box.createHorizontalBox();
		verticalBox.add(horizontalBoxRecherche);

		labelRechNom = new JLabel("Nom :");
		horizontalBoxRecherche.add(labelRechNom);

		rechNom = new JTextField();
		rechNom.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try {
					rafraichirTableau();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		rechNom.setColumns(5);
		horizontalBoxRecherche.add(rechNom);

		labelRechPrenom = new JLabel("Pr\u00E9nom :");
		horizontalBoxRecherche.add(labelRechPrenom);

		rechPrenom = new JTextField();
		rechPrenom.setColumns(5);
		horizontalBoxRecherche.add(rechPrenom);

		btnRechercher = new JButton("Rechercher");
		btnRechercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					rafraichirTableau();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		horizontalBoxRecherche.add(btnRechercher);

		label_3 = new JLabel("   ");
		verticalBox.add(label_3);

		// traitement lors de selection d'un etudiant dans le tableau ==> il
		// faut remplir les champs du formulaire
		ListSelectionModel rowSM = table.getSelectionModel();
		rowSM.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				// Ignore extra messages.
				if (e.getValueIsAdjusting())
					return;

				ListSelectionModel lsm = (ListSelectionModel) e.getSource();
				if (!lsm.isSelectionEmpty()) {
					// desactiver le boutton ajouter (il faut cliquer sur vider
					// formulaire pour l'activer)
					btnAjouter.setEnabled(false);
					btnSupprimer.setEnabled(true);
					btnModifier.setEnabled(true);
					int selectedRow = lsm.getMinSelectionIndex();
					id.setText(table.getValueAt(selectedRow, 0).toString());
					nom.setText(table.getValueAt(selectedRow, 1).toString());
					prenom.setText(table.getValueAt(selectedRow, 2).toString());
					String dateNaissance = table.getValueAt(selectedRow, 3).toString();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Calendar date = Calendar.getInstance();
					try {
						// format Date ==> String
						// parse String ==> Date
						date.setTime(sdf.parse(dateNaissance));
						String annee = new Integer(date.get(Calendar.YEAR)).toString();
						String mois = new Integer(date.get(Calendar.MONTH) + 1).toString();
						String jour = new Integer(date.get(Calendar.DAY_OF_MONTH)).toString();
						dateJour.setSelectedItem(jour);
						dateMois.setSelectedItem(mois);
						dateAnnee.setSelectedItem(annee);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					option.setSelectedItem(Option.getOption(table.getValueAt(selectedRow, 4)));

				}
			}
		});
	}

	private void rafraichirTableau() throws SQLException {
		// recharger la liste des etudiants
		listeEtudiants = etudiantDao.rechercherEtudiantParNomOuPrenom(rechNom.getText(), rechPrenom.getText());
		// convertir la liste en tableau des tableaux
		dataSetEtudiants = EcoleUtils.toTableauGrid(listeEtudiants);
		// préparer le nouveau model ajouter dans le tableau
		DefaultTableModel tableModel = new DefaultTableModel(dataSetEtudiants, Etudiant.ENTETE);
		// ajouter le nouveau model au tableau
		table.setModel(tableModel);
	}

	private void viderFormulaire() {
		id.setText("");
		nom.setText("");
		prenom.setText("");
		dateJour.setSelectedIndex(0);
		dateMois.setSelectedIndex(0);
		dateAnnee.setSelectedIndex(0);
		option.setSelectedIndex(0);
		btnAjouter.setEnabled(true);
		btnModifier.setEnabled(false);
		btnSupprimer.setEnabled(false);
	}

	private String[] listeJours() {
		String[] listeJours = new String[31];
		for (int i = 0; i < 31; i++) {
			listeJours[i] = new Integer(i + 1).toString();
		}
		return listeJours;
	}

	private String[] listeMois() {
		String[] listeMois = new String[12];
		for (int i = 0; i < 12; i++) {
			listeMois[i] = new Integer(i + 1).toString();
		}
		return listeMois;
	}

	private String[] listeAnnes() {
		String[] listeAnnes = new String[30];
		for (int i = 0; i < 30; i++) {
			listeAnnes[i] = new Integer(i + 1979).toString();
		}
		return listeAnnes;
	}

}
