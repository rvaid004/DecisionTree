package BuildTree;

/*
 * Andrew Castillo, Dwayne Thomas, Rishabh Vaidya
 * PID: 5164207, 5454691, 5918963
 * 6/12/18
 * RVAA Online
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.IntStream;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class Driver extends JFrame{
	private static final long serialVersionUID = 1L;
			//entering the table tuple values
			static String[][] BuysComputer = {
			{"age",  "income",  "student", "credit_rating", "buyscomputer"},
			{"youth", "high", "no", "fair", "no"}, 
			{"youth", "high", "no", "excellent", "no"},
			{"middle aged", "high", "no", "fair", "yes"},
			{"senior", "medium", "no", "fair", "yes"},
			{"senior", "low", "yes", "fair", "yes"},
			{"senior", "low", "yes", "excellent", "no"},
			{"middle aged", "low", "yes", "excellent", "yes"},
			{"youth", "medium", "no", "fair", "no"},
			{"youth", "low", "yes", "fair", "yes"},
			{"senior", "medium", "yes", "fair", "yes"},
			{"youth", "medium", "yes", "excellent", "yes"},
			{"middle aged", "medium", "no", "excellent", "yes"},
			{"middle aged", "high", "yes", "fair", "yes"},
			{"senior", "medium", "no", "excellent", "no"}} ; 
			
	static Map<String, String[][]> datas = Collections.unmodifiableMap(new HashMap<String, String[][]>() {
		private static final long serialVersionUID = 1L;
		{
			put("BuysComputer", BuysComputer);
		}
	});

	static String dataKey = datas.keySet().iterator().next();

	public static void main(String[] args) throws Exception {
		Driver driver = new Driver();
		JTree tree = null;
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		boolean flag = true;
		String tuple = "";
		while (flag) {
			System.out.println("> What do you want to do? (build tree, add a tuple,exit) ?");
			String command = bufferedReader.readLine();
			switch (command) {
			case "build tree":
				DataSet dataSet = new DataSet(dataKey, datas.get(dataKey));
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(dataSet.getSplitOnFeature().getName());
				driver.processDataSet(dataSet, node, "");
				if (tree != null)
					driver.remove(tree);
				tree = new JTree(node);
				driver.add(tree);
				driver.setSize(350, 350);
				driver.setTitle(dataKey + " DATASET");
				driver.setVisible(true);
				break;

			case "add a tuple":
				String age;
				String income;
				String student;
				String credit_rating;

				System.out.println("Enter age");
				age = bufferedReader.readLine();
				tuple += "{" + age + ",";

				System.out.println("Enter income");
				income = bufferedReader.readLine();
				tuple += income + ",";

				System.out.println("is this person a student");
				student = bufferedReader.readLine();
				tuple += student + ",";

				System.out.println("Enter credit_rating");
				credit_rating = bufferedReader.readLine();
				tuple += credit_rating + "}";

				DataSet set = new DataSet(dataKey, datas.get(dataKey));
				System.out.println("Given tuple: " + tuple + "\n" + "BuysComputer: "
						+ set.buysComputer(age, income, student, credit_rating));
				break;

			case "exit":
				flag = false;
				break;

			}
		}
	}

	// method to traverse down the tree based on the split on feature
	// builds the whole tree and reaches a decision for each case
	void processDataSet(DataSet dataSet, DefaultMutableTreeNode node, String featureValueName) {
		if (dataSet.toString() != null)
			System.out.println(dataSet);
		if (dataSet.getEntropy() != 0) {
			System.out.println("Next feature down the tree is: " + dataSet.getSplitOnFeature() + " "
					+ dataSet.getSplitOnFeature().getFeatureValues());
			HashMap<String, DataSet> featureDataSets = new HashMap<String, DataSet>();
			dataSet.getSplitOnFeature().getFeatureValues()
					.forEach(featureValue -> featureDataSets.put(featureValue.getName(),
							dataSet.createDataSet(dataSet.getSplitOnFeature(), featureValue, dataSet.getData())));
			processDataSets(featureDataSets, node);
		} else {
			String[][] data = dataSet.getData();
			String decision = " [" + data[0][data[0].length - 1] + " = " + data[1][data[0].length - 1] + "]";
			node.add(new DefaultMutableTreeNode(featureValueName + "   : " + decision));
			System.out.println("Decision -> " + decision);
		}
	}

	// helper method for processDataSet method above
	void processDataSets(HashMap<String, DataSet> dataSets, DefaultMutableTreeNode node) {
		dataSets.keySet().forEach(dataSet -> {
			if (dataSets.get(dataSet).getEntropy() != 0) {
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
						dataSet + "   :  [" + dataSets.get(dataSet).getSplitOnFeature().getName() + "]");
				node.add(newNode);
				processDataSet(dataSets.get(dataSet), newNode, dataSet);
			} else {
				processDataSet(dataSets.get(dataSet), node, dataSet);
			}
		});
	}

}
