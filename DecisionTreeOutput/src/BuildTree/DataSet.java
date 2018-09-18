package BuildTree;

/*
 * Andrew Castillo, Dwayne Thomas, Rishabh Vaidya
 * PID: 5164207, 5454691, 5918963
 * 6/12/18
 * RVAA Online
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.IntStream;

public class DataSet {

	private String name;
	public String[][] data = null;
	private double entropy = 0;
	private HashMap<Feature, Double> infoGains = new HashMap<Feature, Double>();
	private Feature splitOnFeature = null;

	// Constructor
	public DataSet(String name, String[][] data) {
		this.data = data;
		this.name = name;
		calculateEntropy().calculateInfoGains().findSplitOnFeature();
	}

	// calculates entropy based on (-1) * p * log(p) / log(2)
	private DataSet calculateEntropy() {
		new Feature(data, data[0].length - 1).getFeatureValues().stream().forEach(
				featureValue -> entropy += minusPlog2((double) featureValue.getOccurences() / (data.length - 1)));
		return this;
	}

	// creates the data set according to the given table of tuples
	DataSet createDataSet(Feature feature, FeatureValue featureValue, String[][] data) {
		int column = getColNumb(feature.getName());
		String[][] returnData = new String[featureValue.getOccurences() + 1][data[0].length];
		returnData[0] = data[0];
		int counter = 1;
		for (int row = 1; row < data.length; row++) {
			if (data[row][column] == featureValue.getName())
				returnData[counter++] = data[row];
		}
		return new DataSet(feature.getName() + ": " + featureValue.getName(), deleteColumn(returnData, column));
	}

	// calculates information gain which helps determine best split on feature
	private DataSet calculateInfoGains() {
		IntStream.range(0, data[0].length - 1).forEach(column -> {
			Feature feature = new Feature(data, column);
			ArrayList<DataSet> dataSets = new ArrayList<DataSet>();
			feature.getFeatureValues().stream()
					.forEach(featureValue -> dataSets.add(createDataSet(feature, featureValue, data)));
			double summation = 0;
			for (int i = 0; i < dataSets.size(); i++)
				summation += ((double) (dataSets.get(i).getData().length - 1) / (data.length - 1))
						* dataSets.get(i).getEntropy();
			infoGains.put(feature, entropy - summation);
		});
		return this;
	}

	// finds feature to split on
	private DataSet findSplitOnFeature() {
		Iterator<Feature> iterator = infoGains.keySet().iterator();
		while (iterator.hasNext()) {
			Feature feature = iterator.next();
			if (splitOnFeature == null || infoGains.get(splitOnFeature) < infoGains.get(feature))
				splitOnFeature = feature;
		}
		return this;
	}

	// get method for data
	public String[][] getData() {
		return data;
	}

	// get method for entropy
	public double getEntropy() {
		return entropy;
	}

	// hash map method that retruns the information gain
	public HashMap<Feature, Double> getInfoGains() {
		return infoGains;
	}

	// returns name of feature
	public String toString() {
		return name;
	}

	// get method for split on feature
	public Feature getSplitOnFeature() {
		return splitOnFeature;
	}

	// helps calucalte entropy based on a formula
	private double minusPlog2(double p) {
		double returnValue = 0;
		if (p != 0)
			returnValue = (-1) * p * Math.log(p) / Math.log(2);
		return returnValue;
	}

	// reaches a final decision after traversing through the decision tree
	public String buysComputer(String age, String income, String student, String credit_rating) {

		String answer = "no";

		if (age.equals("youth")) {

			if (student.equals("yes")) {

				answer = "yes";
			}

		}

		else if (age.equals("middle aged")) {

			answer = "yes";

		}

		else {

			if (credit_rating.equals("fair")) {

				answer = "yes";
			}

		}

		return answer;
	}

	// deletes column from data table
	private String[][] deleteColumn(String data[][], int deleteColumn) {
		String returnData[][] = new String[data.length][data[0].length - 1];
		IntStream.range(0, data.length).forEach(row -> {
			int columnCounter = 0;
			for (int column = 0; column < data[0].length; column++)
				if (column != deleteColumn)
					returnData[row][columnCounter++] = data[row][column];
		});
		return returnData;
	}

	// get method for column number in the data table
	public int getColNumb(String colName) {
		int returnValue = -1;
		for (int column = 0; column < data[0].length - 1; column++)
			if (data[0][column] == colName) {
				returnValue = column;
				break;
			}
		return returnValue;
	}
}
