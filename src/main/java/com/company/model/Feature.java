package com.company.model;

import java.util.List;

public class Feature {
    private String name;
    private int numberOfOptions;
    private double positive;
    private double negative;
    private List<Feature> options;
    private double entropyValue;
    public Feature() {
    }

    public Feature(String name, int positive, int negative) {
        this.name = name;
        this.positive = positive;
        this.negative = negative;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Feature> getOptions() {
        return options;
    }

    public void setOptions(List<Feature> options) {
        this.options = options;
    }


    public int getNumberOfOptions() {
        return numberOfOptions;
    }

    public void setNumberOfOptions(int numberOfOptions) {
        this.numberOfOptions = numberOfOptions;
    }

    public double getEntropyValue() {
        return entropyValue;
    }

    public void setEntropyValue(double entropyValue) {
        this.entropyValue = entropyValue;
    }

    public double getNegative() {
        return negative;
    }

    public void setNegative(double negative) {
        this.negative = negative;
    }

    public double getPositive() {
        return positive;
    }

    public void setPositive(double positive) {
        this.positive = positive;
    }
}
