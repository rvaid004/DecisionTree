package GenerateInfoGain;

/*
 * Andrew Castillo, Dwayne Thomas, Rishabh Vaidya
 * PID: 5164207, 5454691, 5918963
 * 6/12/18
 * RVAA Online
 */

public class FeatureValue {
	private String name;
	private int occurences;

	public FeatureValue(String name) {
		this.name = name;
	}

	public int getOccurences() {
		return occurences;
	}

	public void setOccurences(int occurences) {
		this.occurences = occurences;
	}

	public int hasCode() {
		return name.hashCode();
	}

	public String getName() {
		return name;
	}

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
}
