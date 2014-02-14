package ma.ensat.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ma.ensat.model.om.Etudiant;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class EtudiantDaoTest {

	private static EtudiantDao etudiantDao = null;
	private static Connection connection = null;
	private static Etudiant etudiantRechercherEtudiantParIdExistant = null;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	static {
		try {
			connection = ConnectionMysql.getInstance("jdbc:mysql://localhost:3306/ecole_test?user=root&password=").getConnection();
			etudiantDao = new EtudiantDao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * on pr�pare la base de donn�es pour que nos test marche
	 */
	
	// Code ex�cut� avant l'ex�cution du premier test (et de la m�thode @Before) 
	@BeforeClass
	public static void setup() {
		etudiantRechercherEtudiantParIdExistant = new Etudiant();
		etudiantRechercherEtudiantParIdExistant.setId(999);
		etudiantRechercherEtudiantParIdExistant.setNom("XYZ");
		etudiantRechercherEtudiantParIdExistant.setPrenom("ABC");
		etudiantRechercherEtudiantParIdExistant.setDateNaissance(new Date());
		etudiantRechercherEtudiantParIdExistant.setOption("HHHH");
		try {
			Statement s = connection.createStatement();
			s.execute("truncate table etudiant");//
			String sqlRequest = "insert into etudiant (id, nom, prenom, date_naissance, `option`) values ("
					+ etudiantRechercherEtudiantParIdExistant.getId() + ",'" + etudiantRechercherEtudiantParIdExistant.getNom() + "', '"
					+ etudiantRechercherEtudiantParIdExistant.getPrenom() + "', '"
					+ sdf.format(etudiantRechercherEtudiantParIdExistant.getDateNaissance()) + "', '"
					+ etudiantRechercherEtudiantParIdExistant.getOption() + "')";
			// System.out.println(sqlRequest);
			s.execute(sqlRequest);

			sqlRequest = "insert into etudiant (id, nom, prenom, date_naissance, `option`) values (" + 1000 + ",'"
					+ etudiantRechercherEtudiantParIdExistant.getNom() + "_2', '" + etudiantRechercherEtudiantParIdExistant.getPrenom() + "_2', '"
					+ sdf.format(etudiantRechercherEtudiantParIdExistant.getDateNaissance()) + "', '"
					+ etudiantRechercherEtudiantParIdExistant.getOption() + "_2')";
			// System.out.println(sqlRequest);
			s.execute(sqlRequest);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Code ex�cut� apr�s l'ex�cution de tous les tests
	@AfterClass
	public static void teardown() throws SQLException {
		Statement s = connection.createStatement();
		s.execute("truncate table etudiant");
	}
	
	@Test
	public void rechercherTousEtudiants() throws SQLException {
		List<Etudiant> listeEtudiant = etudiantDao.tousLesEtudiants();
		Assert.assertEquals(2, listeEtudiant.size());
		Etudiant e1 = listeEtudiant.get(0);
		Etudiant e2 = listeEtudiant.get(1);
		
		//assert sur etudiant 1
		Assert.assertNotNull(e1);
		Assert.assertEquals(etudiantRechercherEtudiantParIdExistant.getId(), e1.getId());
		Assert.assertEquals(etudiantRechercherEtudiantParIdExistant.getNom(), e1.getNom());
		Assert.assertEquals(etudiantRechercherEtudiantParIdExistant.getPrenom(), e1.getPrenom());
		Assert.assertEquals(sdf.format(etudiantRechercherEtudiantParIdExistant.getDateNaissance()), sdf.format(e1.getDateNaissance()));
		Assert.assertEquals(etudiantRechercherEtudiantParIdExistant.getOption(), e1.getOption());
		
		//assert sur etudiant 2
		Assert.assertNotNull(e2);
		Assert.assertEquals((Integer)(etudiantRechercherEtudiantParIdExistant.getId() + 1), e2.getId());
		Assert.assertEquals(etudiantRechercherEtudiantParIdExistant.getNom() + "_2", e2.getNom());
		Assert.assertEquals(etudiantRechercherEtudiantParIdExistant.getPrenom() + "_2", e2.getPrenom());
		Assert.assertEquals(sdf.format(etudiantRechercherEtudiantParIdExistant.getDateNaissance()), sdf.format(e2.getDateNaissance()));
		Assert.assertEquals(etudiantRechercherEtudiantParIdExistant.getOption() + "_2", e2.getOption());
		
	}

	@Test
	public void ajouterEtudiantTestCas1() throws SQLException {
		Etudiant e = new Etudiant();
		e.setNom("BOUHAMIDI");
		e.setPrenom("imad");
		e.setOption("R�seaux et syst�me");
		Calendar dateNaissance = Calendar.getInstance();
		dateNaissance.set(1989, 1, 15);
		e.setDateNaissance(dateNaissance.getTime());
		Boolean result = etudiantDao.ajouterEtudiant(e);
		Assert.assertTrue(result);
	}

	@Test
	public void ajouterEtudiantTestCas2() throws SQLException {
		Etudiant e = null;
		Boolean result = etudiantDao.ajouterEtudiant(e);
		Assert.assertFalse(result);
	}

	@Test
	public void rechercherEtudiantParIdInexistant() throws SQLException {
		Etudiant e = etudiantDao.rechercherEtudiantParId(123);
		Assert.assertNull(e);
	}

	@Test
	public void rechercherEtudiantParIdExistant() throws SQLException {
		Etudiant e = etudiantDao.rechercherEtudiantParId(999);
		Assert.assertNotNull(e);
		Assert.assertEquals(etudiantRechercherEtudiantParIdExistant.getId(), e.getId());
		Assert.assertEquals(etudiantRechercherEtudiantParIdExistant.getNom(), e.getNom());
		Assert.assertEquals(etudiantRechercherEtudiantParIdExistant.getPrenom(), e.getPrenom());
		Assert.assertEquals(sdf.format(etudiantRechercherEtudiantParIdExistant.getDateNaissance()), sdf.format(e.getDateNaissance()));
		Assert.assertEquals(etudiantRechercherEtudiantParIdExistant.getOption(), e.getOption());
	}

	

}
