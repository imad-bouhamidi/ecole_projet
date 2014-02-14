package ma.ensat.model.om;

public enum Option {

	INFORMATIQUE("Informatique"), RESEAUX("Réseaux et système"), INDUS("Génie industriel");
	
	private String value;

	Option(String option) {
		this.value = option;
	}
	
	public String getValue() {
		return this.value;
	}

	public static Option[] getOptions() {
		Option[] values = Option.values();
		return values;
	}

	public String toString() {
		return value;
	}

	public static Option getOption(Object value) {
		Option[] options = Option.values();
		for (Option o : options) {
			if (o.getValue().equals(value)) {
				return o;
			}
		}
		return null;
	}

}
