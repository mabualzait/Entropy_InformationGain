package com.company.model;

import java.util.List;

public class Feature {
    private String name;
    private int numberOfOptions;
    private int positive;
    private int negative;
    private List<Feature> options;

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

    public int getPositive() {
        return positive;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public int getNegative() {
        return negative;
    }

    public void setNegative(int negative) {
        this.negative = negative;
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
}
