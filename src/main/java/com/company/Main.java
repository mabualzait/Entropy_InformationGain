package com.company;


import com.company.model.Feature;

import java.io.*;
import java.util.*;

public class Main {

    private static int NUMBER_OF_FEATURES = 0;
    private static int SAMPLE_COUNT = 0;
    static ArrayList<Feature> featuresList;
    static Feature sample;
    static List<String[]> lines = new ArrayList<>();

    public static void main(String[] args) {
        try {
            readCsvFile();
            String[][] array = new String[lines.size()][0];
            lines.toArray(array);
            NUMBER_OF_FEATURES = array[0].length - 2; // 1 id and 1 result column
            SAMPLE_COUNT = array.length - 1; // 1 header row
            getSampleData(array);
            System.out.println(Arrays.toString(array[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Database connection using H2 driver
       /* Class.forName ("org.h2.Driver");
        Connection conn = DriverManager.getConnection ("jdbc:h2:~/test", "sa","");
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT * FROM CLASSIFICATION");
        System.out.println(resultSet);
        resultSet.getString(1);
        conn.close();*/

     }

    private static void readCsvFile() throws IOException {
        String line = "";
        String splitBy = ",";
        BufferedReader br = new BufferedReader(new FileReader("classification.csv"));
        while ((line = br.readLine()) != null)   //returns a Boolean value
        {
            lines.add(line.split(splitBy));
        }
    }

    private static void printing() {
        System.out.println("Nummber of Features: " + NUMBER_OF_FEATURES);

        for (int i = 0; i < NUMBER_OF_FEATURES; i++) {
            System.out.println("Feature " + i + "= " + featuresList.get(i).getName());
        }
    }

    private static void getSampleData(String[][] array) {
        HashSet<String> types = new HashSet<>();
        for (int i = 1; i < SAMPLE_COUNT; i++) {
            types.add(array[i][NUMBER_OF_FEATURES + 1]);
        }
        int typesCount = types.size();
        String []arr = new String[typesCount];
        int counter = 0;
        for (String ele : types) {
            arr[counter++] = ele;
        }
        int positiveCount = 0;
        int negativeCount = 0;
        for (int j = 0; j < arr.length; j++) {
            for (int i = 1; i <= SAMPLE_COUNT; i++) {
                if (arr[j].equalsIgnoreCase(array[i][NUMBER_OF_FEATURES + 1]))
                    if (j == 0)
                        positiveCount++;
                    else
                        negativeCount++;
            }
        }

        for (String type : types) {
            System.out.println(type);
        }
        System.out.println("Positive = "+ positiveCount);
        System.out.println("Negative = "+ negativeCount);
       sample = new Feature("Sample Data", positiveCount, negativeCount);
    }


    private static void getFeaturesData(int i) {
        Feature feature = new Feature();
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter Feature " + i + " Name");
        feature.setName(myObj.nextLine());
        System.out.println("How many options Feature " + feature.getName() + " has:-");
        feature.setNumberOfOptions(myObj.nextInt());
        List<Feature> optionsList = new ArrayList<>();
        myObj.nextLine();
        for (int count = 0; count < feature.getNumberOfOptions(); count++) {
            Feature option = new Feature();
            System.out.println("Enter Option " + (count + 1) + " name");
            String optionName = myObj.nextLine();
            option.setName(optionName);
            System.out.println("Enter Option " + (count + 1) + " Positive count");
            option.setPositive(Integer.parseInt(myObj.nextLine()));
            System.out.println("Enter Option " + (count + 1) + " Negative count");
            option.setNegative(Integer.parseInt(myObj.nextLine()));
            optionsList.add(option);
        }

        feature.setOptions(optionsList);
        featuresList.add(feature);
    }

    private static void getNumberOfFeatures() {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter Number of features");
        NUMBER_OF_FEATURES = Integer.parseInt(myObj.nextLine());
    }

}
