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
            for (int i = 1; i <= NUMBER_OF_FEATURES; i++) {
                readFeature(array, i);
            }
            setEntropyValues(sample, featuresList);
            setInformationGain();
            printResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printResults() {
        for (Feature feature : featuresList) {
            System.out.println("Feature " + feature.getName() + " |IG= " + feature.getInformationGain());
            for( Feature options: feature.getOptions()){
                System.out.println("Feature " + options.getName() + " |Entropy= " + options.getEntropyValue());

            }
        }
    }

    private static void setInformationGain() {
        for (Feature feature : featuresList) {
            double subGain = 0;
            for (Feature option : feature.getOptions()) {
                double value = (option.getPositive() + option.getNegative()) / (feature.getNegative() + feature.getPositive());
                subGain += -value * option.getEntropyValue();
            }
            feature.setInformationGain(feature.getEntropyValue() + subGain);
        }
    }

    private static void setEntropyValues(Feature sample, ArrayList<Feature> featuresList) {
        sample.setEntropyValue(calculateEntropy(sample.getPositive(), sample.getNegative()));
        for (Feature feature : featuresList) {
            feature.setEntropyValue(calculateEntropy(feature.getPositive(), feature.getNegative()));
        }
    }

    private static double calculateEntropy(double positive, double negative) {
        double PosFraction = positive / (negative + positive);
        double NegFraction = negative / (negative + positive);
        if (negative == 0 && positive == 0) return 1;
        double negativePart = NegFraction * (Math.log(NegFraction) / Math.log(2));
        if (positive == 0) return -negativePart;
        double positivePart = PosFraction * (Math.log(PosFraction) / Math.log(2));
        if (negative == 0) return -positivePart;
        return -positivePart - negativePart;
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
            if (i == 0) feature.setName(array[i][index]);
            else optionsSet.add(array[i][index]);
        }
        int numberOfPositive = 0;
        int numberOfNegative = 0;
        Object[] arrResults = resultOptions.toArray();
        for (int i = 0; i < SAMPLE_COUNT; i++) {
            if (array[i][RESULT_COLUMN].equalsIgnoreCase(arrResults[0].toString())) numberOfPositive++;
            else numberOfNegative++;
        }
        feature.setPositive(numberOfPositive);
        feature.setNegative(numberOfNegative);
        Object[] optionsTypes = optionsSet.toArray();
        for (Object optionsType : optionsTypes) {
            Feature option = new Feature();
            option.setName(optionsType.toString());
            option.setNumberOfOptions(0);
            double pos = 0;
            double neg = 0;
            for (int i = 0; i < SAMPLE_COUNT + 1; i++) {
                if (array[i][index].equalsIgnoreCase(option.getName()) && array[i][RESULT_COLUMN].equalsIgnoreCase(arrResults[0].toString())) {
                    pos++;
                    option.setNumberOfOptions(option.getNumberOfOptions() + 1);
                } else if (array[i][index].equalsIgnoreCase(option.getName()) && array[i][RESULT_COLUMN].equalsIgnoreCase(arrResults[1].toString())) {
                    neg++;
                    option.setNumberOfOptions(option.getNumberOfOptions() + 1);
                }
            }
            option.setPositive(pos);
            option.setNegative(neg);
            option.setEntropyValue(calculateEntropy(option.getPositive(), option.getNegative()));
            optionsList.add(option);
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
        sample = new Feature("Sample Data", positiveCount, negativeCount);
    }

}
