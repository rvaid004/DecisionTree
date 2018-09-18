package GenerateInfoGain;

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

public class Driver {
	// entering the table tuple values
	static String[][] BuysComputer = { { "age", "income", "student", "credit_rating", "buyscomputer" },
			{ "youth", "high", "no", "fair", "no" }, { "youth", "high", "no", "excellent", "no" },
			{ "middle aged", "high", "no", "fair", "yes" }, { "senior", "medium", "no", "fair", "yes" },
			{ "senior", "low", "yes", "fair", "yes" }, { "senior", "low", "yes", "excellent", "no" },
			{ "middle aged", "low", "yes", "excellent", "yes" }, { "youth", "medium", "no", "fair", "no" },
			{ "youth", "low", "yes", "fair", "yes" }, { "senior", "medium", "yes", "fair", "yes" },
			{ "youth", "medium", "yes", "excellent", "yes" }, { "middle aged", "medium", "no", "excellent", "yes" },
			{ "middle aged", "high", "yes", "fair", "yes" }, { "senior", "medium", "no", "excellent", "no" } };

	public static void main(String[] args) {
		Driver driver = new Driver();
		HashMap<String, String[][]> dataset = new HashMap<String, String[][]>();
		dataset.put("BuysComputer", BuysComputer);

		dataset.keySet().forEach(data -> {
			HashMap<Feature, Double> featuresInfoGain = new HashMap<Feature, Double>();
			DataSet ds = new DataSet(dataset.get(data));
			IntStream.range(0, dataset.get(data)[0].length - 1).forEach(column -> {
				Feature feature = new Feature(dataset.get(data), column);
				ArrayList<DataSet> dataSets = new ArrayList<DataSet>();
				feature.getValues().stream().forEach(
						featureValue -> dataSets.add(driver.createDataSet(featureValue, column, dataset.get(data))));
				double summation = 0;
				for (int i = 0; i < dataSets.size(); i++)
					summation += ((double) (dataSets.get(i).getData().length - 1) / (dataset.get(data).length - 1))
							* dataSets.get(i).getEntropy();
				featuresInfoGain.put(feature, ds.getEntropy() - summation);
			});

			System.out.println("<" + data + " DATASET>:\n" + ds);
			System.out.println(driver.generateInfoGainDisplayTable(featuresInfoGain));
			System.out
					.println("Best feature to split on is " + driver.determineSplitOnFeature(featuresInfoGain) + "\n");
			System.out.println("\n\n");

		});
	}

	DataSet createDataSet(FeatureValue featureValue, int column, String[][] data) {
		String[][] returnData = new String[featureValue.getOccurences() + 1][data[0].length];
		returnData[0] = data[0];
		int counter = 1;
		for (int row = 1; row < data.length; row++) {
			if (data[row][column] == featureValue.getName())
				returnData[counter++] = data[row];
		}
		return new DataSet(deleteColumn(returnData, column));
	}

	Feature determineSplitOnFeature(HashMap<Feature, Double> featuresInfoGain) {
		Feature splitOnFeature = null;
		Iterator<Feature> iterator = featuresInfoGain.keySet().iterator();
		while (iterator.hasNext()) {
			Feature feature = iterator.next();
			if (splitOnFeature == null)
				splitOnFeature = feature;
			if (featuresInfoGain.get(splitOnFeature) < featuresInfoGain.get(feature))
				splitOnFeature = feature;
		}
		return splitOnFeature;
	}

	StringBuffer generateInfoGainDisplayTable(HashMap<Feature, Double> featuresInfoGain) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Feature             Information Gain\n");
		IntStream.range(0, 38).forEach(i -> stringBuffer.append("-"));
		stringBuffer.append("\n");
		Iterator<Feature> iterator = featuresInfoGain.keySet().iterator();
		while (iterator.hasNext()) {
			Feature feature = iterator.next();
			stringBuffer.append(feature);
			IntStream.range(0, 21 - feature.getName().length()).forEach(i -> stringBuffer.append("-"));
			stringBuffer.append(String.format("%.8f", featuresInfoGain.get(feature)) + "\n");
		}
		return stringBuffer;
	}

	String[][] deleteColumn(String data[][], int deleteColumn) {
		String returnData[][] = new String[data.length][data[0].length - 1];
		for (int row = 0; row < data.length; row++) {
			int columnCounter = 0;
			for (int column = 0; column < data[0].length; column++)
				if (column != deleteColumn)
					returnData[row][columnCounter++] = data[row][column];
		}
		return returnData;
	}

}
