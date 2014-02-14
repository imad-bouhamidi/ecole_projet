package ma.ensat.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ma.ensat.model.om.Etudiant;

/**
 * DAO : Data Access Object
 * @author imad BOUHAMIDI (imad.bouhamidi@gmail.com)
 *
 */
public class EtudiantDao {
	private Connection connection = null;

	// execution du constructeur en instanciant une connection
	public EtudiantDao() throws ClassNotFoundException, SQLException {
		this.connection = ConnectionMysql.getInstance().getConnection();
	}

	public Boolean ajouterEtudiant(Etudiant e) throws SQLException {
		if (e == null) {
			return false;
		}
		PreparedStatement preparedStatment = connection
				.prepareStatement("insert into etudiant (nom, prenom, date_naissance, `option`) values (?, ?, ?, ?)");
		// d�but du mapping objet vers relationnel
		// remplacer le premier ? qui est le nom		
		preparedStatment.setString(1, e.getNom());
		// remplacer le deuxieme ? qui est le prenom
		preparedStatment.setString(2, e.getPrenom());
		// remplacer le troisieme ? qui est la date de naissance il est
		// necessaire de convertir de java.util.Date to java.sql.Date
		java.sql.Date dateNaissance = null;
		if (e.getDateNaissance() != null) {
			dateNaissance = new java.sql.Date(e.getDateNaissance().getTime());
		}
		preparedStatment.setDate(3, dateNaissance);
		// remplacer le quatrieme ? qui est l'option
		preparedStatment.setString(4, e.getOption());
		
		// fin du mapping objet vers relationnel
		// executer maintenant la requete
		preparedStatment.executeUpdate();
		return true;
	}

	public Boolean modifierEtudiant(Etudiant e) throws SQLException {
		if (e == null || e.getId() == null) {
			return false;
		}
		PreparedStatement preparedStatment = connection
				.prepareStatement("update etudiant set nom = ?, prenom = ?, date_naissance = ?, `option` = ? where id = ?");
		// remplacer le premier ? qui est le nom
		preparedStatment.setString(1, e.getNom());
		// remplacer le deuxieme ? qui est le prenom
		preparedStatment.setString(2, e.getPrenom());
		// remplacer le troisieme ? qui est la date de naissance il est
		// necessaire de convertir de java.util.Date to java.sql.Date
		java.sql.Date dateNaissance = null;
		if (e.getDateNaissance() != null) {
			dateNaissance = new java.sql.Date(e.getDateNaissance().getTime());
		}
		preparedStatment.setDate(3, dateNaissance);
		// remplacer le quatrieme ? qui est l'option
		preparedStatment.setString(4, e.getOption());

		// remplacer le cinqieme ? qui est l'id
		preparedStatment.setInt(5, e.getId());

		// executer maintenant la requete
		preparedStatment.executeUpdate();
		return true;
	}

	public Etudiant rechercherEtudiantParId(Integer id) throws SQLException {
		// creation d'un statment a partir de la connection
		Statement statement = connection.createStatement();
		// execution de la requete sur la table etudiant en filtrant par l'id
		// fourni en entr�
		// le resultSet contient un tableau qu'il faut convertir a un objet
		// (mapping relationnel objet)
		ResultSet resultSet = statement.executeQuery("select * from etudiant where id = " + id);

		// pour faire le mapping j'ai fait une methode g�n�rique pour tout les
		// cas de la table etudiant
		List<Etudiant> listeEtudiant = mappingResultSetToEtudiant(resultSet);
		if (!listeEtudiant.isEmpty()) { // si la liste n'est pas vide donc il
										// faut y avoir seulement un seul
										// etudiant car id est la cl� primaire
										// est unique
			return listeEtudiant.get(0);
		}
		return null;
	}

	public List<Etudiant> rechercherEtudiantParNomOuPrenom(String nom, String prenom) throws SQLException {
		String sqlCondition = "1=1 ";
		if(nom != null && !"".equals(nom)) {
			sqlCondition += "and nom like '" + nom + "%' ";
		}
		if(prenom != null && !"".equals(prenom)) {
			sqlCondition += "and prenom like '" + prenom + "%'";
		}

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from etudiant where " + sqlCondition);

		return mappingResultSetToEtudiant(resultSet);
	}

	public Boolean supprimerEtudiant(Integer id) throws SQLException {
		PreparedStatement preparedStatment = connection.prepareStatement("delete from etudiant where id = ?");
		preparedStatment.setInt(1, id);
		preparedStatment.executeUpdate();
		return true;
	}

	public List<Etudiant> tousLesEtudiants() throws SQLException {
		// creation d'un statment a partir de la connection
		Statement statement = connection.createStatement();

		ResultSet resultSet = statement.executeQuery("select * from etudiant");

		return mappingResultSetToEtudiant(resultSet);
	}

	// fonction utilitaire pour le mapping
	private List<Etudiant> mappingResultSetToEtudiant(ResultSet r) throws SQLException {
		List<Etudiant> listeEtudiant = new ArrayList<Etudiant>();

		while (r.next()) {
			Etudiant etudiant = new Etudiant();
			etudiant.setId(r.getInt("id"));
			etudiant.setNom(r.getString("nom"));
			etudiant.setPrenom(r.getString("prenom"));
			etudiant.setDateNaissance(r.getDate("date_naissance"));
			etudiant.setOption(r.getString("option"));
			listeEtudiant.add(etudiant);
		}

		return listeEtudiant;

	}
}
