package ma.ensat.utils;

import java.util.List;

import ma.ensat.model.om.Etudiant;

public class EcoleUtils {

	public static Object[][] toTableauGrid(List<Etudiant> listeEtudiants) {
		Object[][] tableauGrid = new Object[listeEtudiants.size()][];
		int i = 0;
		for (Etudiant e : listeEtudiants) {
			tableauGrid[i] = e.toTableau();
			i++;
		}
		return tableauGrid;
	}

}
