package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class Polynomial {
    private String notation;
    private ArrayList<Float> coefficients;

    public Polynomial() {
        ArrayList<Float> zeroCoefficient = new ArrayList<>();
        zeroCoefficient.add(1f);
        this.coefficients = zeroCoefficient;
    }

    public Polynomial(ArrayList<Float> coefficients) {
        this.coefficients = coefficients;
    }

    public float evaluateAt(float x) {
        int runningSum = 0;
        for (int i = 0; i <= coefficients.size() - 1; i++) {
            runningSum += coefficients.get(i) * Math.pow(x, i);
        }
        return runningSum;
    }

    public Polynomial addPolynomialTo(Polynomial p) {
        Float[] newCoefficients;
        ArrayList<Float> coefficientsToAdd;

        if (this.coefficients.size() >= p.coefficients.size()) {
            newCoefficients = this.coefficients.toArray(new Float[0]);
            coefficientsToAdd = p.coefficients;
        } else {
            newCoefficients = p.coefficients.toArray(new Float[0]);
            coefficientsToAdd = this.coefficients;
        }

        for (int i = 0; i < coefficientsToAdd.size(); i++) {
            if (newCoefficients[i] == null) {
                newCoefficients[i] = 0.0f;
            }
            newCoefficients[i] += coefficientsToAdd.get(i);
        }

        ArrayList<Float> temp = new ArrayList<>(Arrays.asList(newCoefficients));
        return new Polynomial(temp);
    }

    public Polynomial multiplyByPolynomial(Polynomial p) {
        Float[] newCoefficients = new Float[this.coefficients.size() + p.coefficients.size() - 1];

        for (int i = 0; i < p.coefficients.size(); i++) {
            for (int j = 0; j < this.coefficients.size(); j++) {
                if (newCoefficients[i+j] == null) {
                    newCoefficients[i+j] = 0.0f;
                }
                newCoefficients[i+j] += p.coefficients.get(i) * this.coefficients.get(j);
            }
        }

        ArrayList<Float> temp = new ArrayList<>(Arrays.asList(newCoefficients));
        return new Polynomial(temp);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < coefficients.size(); i++) {
//            String roundedCoefficient = String.format("%.2f", coefficients.get(i));
            String roundedCoefficient = coefficients.get(i).toString();

            s.append(roundedCoefficient).append("x^").append(i);
            if (i + 1 != coefficients.size()) {
                s.append(" + ");
            }
        }
        return s.toString();
    }


}
