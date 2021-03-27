package com.company;


import com.company.model.Feature;

import java.io.*;
import java.util.*;

public class Main {

    private static int NUMBER_OF_FEATURES = 0;
    private static int SAMPLE_COUNT = 0;
    private static int RESULT_COLUMN = 0;
    private static ArrayList<Feature> featuresList = new ArrayList<>();
    private static Feature sample;
    private static List<String[]> lines = new ArrayList<>();
    private static HashSet<String> resultOptions;

    public static void main(String[] args) {
        try {
            readCsvFile();
            String[][] array = new String[lines.size()][0];
            lines.toArray(array);
            getTableProperties(array);
            getSampleData(array);
            for (int i = 1; i < NUMBER_OF_FEATURES; i++) {
                readFeature(array, i);
            }
            System.out.println(featuresList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getTableProperties(String[][] array) {
        NUMBER_OF_FEATURES = array[0].length - 2; // 1 id and 1 result column
        RESULT_COLUMN = NUMBER_OF_FEATURES + 1;
        SAMPLE_COUNT = array.length - 1; // 1 header row
    }

    private static void readFeature(String[][] array, int index) {
        Feature feature = new Feature();
        HashSet<String> optionsSet = new HashSet<>();
        List<Feature> optionsList = new ArrayList<>();
        for (int i = 0; i < SAMPLE_COUNT; i++) {
            System.out.println("Column -->  " + array[i][index]);
            if (i == 0) feature.setName(array[i][index]);
            else optionsSet.add(array[i][index]);
        }
        int optionsCount = optionsSet.size();
        int numberOfPositive = 0;
        int numberOfNegative = 0;
        Object[] arrResults = resultOptions.toArray();
        for (int i = 0; i < SAMPLE_COUNT; i++) {
            System.out.println("Column -->  " + array[i][index] + " and result = " + array[i][RESULT_COLUMN]);
            if (array[i][RESULT_COLUMN].equalsIgnoreCase(arrResults[0].toString())) numberOfPositive++;
            else numberOfNegative++;
        }

        System.out.println("Feature " + feature.getName() + " has +" + numberOfPositive + " and -" + numberOfNegative);
        System.out.println("Options count " + optionsCount);
        feature.setPositive(numberOfPositive);
        feature.setNegative(numberOfNegative);
        Object[] optionsTypes = optionsSet.toArray();
        for (Object optionsType : optionsTypes) {
            Feature option = new Feature();
            option.setName(optionsType.toString());
            int pos = 0;
            int neg = 0;
            for (int i = 1; i < SAMPLE_COUNT; i++) {
                System.out.println("Option -->  " + array[i][index] + " and result = " + array[i][RESULT_COLUMN]);
                if (array[i][index].equalsIgnoreCase(option.getName()) && array[i][RESULT_COLUMN].equalsIgnoreCase(arrResults[0].toString()))
                    pos++;
                else neg++;
            }
            option.setPositive(pos);
            option.setNegative(neg);
            optionsList.add(option);
            System.out.println("positive options = " + pos + " negative = " + neg);
        }
        feature.setOptions(optionsList);
        featuresList.add(feature);
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

    private static void getSampleData(String[][] array) {
        resultOptions = new HashSet<>();
        for (int i = 1; i < SAMPLE_COUNT; i++) {
            resultOptions.add(array[i][NUMBER_OF_FEATURES + 1]);
        }
        int typesCount = resultOptions.size();
        String[] arr = new String[typesCount];
        int counter = 0;
        for (String ele : resultOptions) {
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
        for (String type : resultOptions) {
            System.out.println(type);
        }
        System.out.println("Positive = " + positiveCount);
        System.out.println("Negative = " + negativeCount);
        sample = new Feature("Sample Data", positiveCount, negativeCount);
    }

}
