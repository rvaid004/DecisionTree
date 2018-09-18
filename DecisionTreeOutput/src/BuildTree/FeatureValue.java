package BuildTree;

/*
 * Andrew Castillo, Dwayne Thomas, Rishabh Vaidya
 * PID: 5164207, 5454691, 5918963
 * 6/12/18
 * RVAA Online
 */

public class FeatureValue {
	private String name;
	private int occurences;

	// constructor
	public FeatureValue(String name) {
		this.name = name;
	}

	// get method for occurrences
	public int getOccurences() {
		return occurences;
	}

	// set method for occurrences
	public void setOccurences(int occurences) {
		this.occurences = occurences;
	}

	// int method that returns hasCode location of name of feature
	public int hashCode() {
		return name.hashCode();
	}

	// get method for name
	public String getName() {
		return name;
	}

	// equals method to check if two objects are equal
	public boolean equals(Object object) {
		boolean returnValue = true;
		if (object == null || (getClass() != object.getClass()))
			returnValue = false;
		if (name == null)
			if (((FeatureValue) object).name != null)
				returnValue = false;
			else if (!name.equals(((FeatureValue) object).name))
				returnValue = false;
		return returnValue;

	}
	
	//toString method that returns name
	public String toString() {
		return name;
	}
}
