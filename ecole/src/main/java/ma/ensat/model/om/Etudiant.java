package ma.ensat.model.om;

import java.util.Date;

public class Etudiant {
	// c'est l'entete du tableau utilisé dans swing
	public static final String[] ENTETE = { "Id", "Nom", "Prénom", "Date de naissance", "Option" };
	private Integer id;
	private String nom;
	private String prenom;
	private Date dateNaissance;
	private String option;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String toString() {
		return "" + id + "|" + nom + "|" + prenom + "|" + dateNaissance + "|" + option;
	}	

	public Object[] toTableau() {
		return new Object[] { id, nom, prenom, dateNaissance, option };
	}

}
