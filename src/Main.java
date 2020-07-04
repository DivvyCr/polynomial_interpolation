package com.company;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Point> points = new ArrayList<>();

        boolean exit = false;
        int pointCounter = 0;
        while (!exit) {
            pointCounter++;
            System.out.println("Point #" + pointCounter);
            System.out.print("\tEnter X-Value: ");
            int x = scanner.nextInt();
            System.out.print("\tEnter Y-Value: ");
            int y = scanner.nextInt();
            points.add(new Point(x, y));
            System.out.println("Point added.");

            System.out.println("Is that all? (Y/N)");
            if (scanner.next().toLowerCase().equals("y")) {
                exit = true;
            }
            System.out.println();
        }
        System.out.println("===========================");
        System.out.println("Created polynomial: ");

        Point[] a = points.toArray(new Point[0]);
        System.out.print(interpolate(a));

    }

    private static Polynomial interpolate(Point[] points) {
        if (areXValuesDistinct(points)) {
            if (points.length != 0) {
                ArrayList<Polynomial> polynomialCoefficients = findPolynomialCoefficients(points);
                return sumPolynomialCoefficients(polynomialCoefficients);
            } else {
                return new Polynomial();
            }
        } else {
            return new Polynomial();
        }
    }

    private static ArrayList<Polynomial> findPolynomialCoefficients(Point[] points) {
        ArrayList<Polynomial> polynomialCoefficients = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            Polynomial polynomialCoefficient = findSingleTerm(points, i);
            polynomialCoefficients.add(polynomialCoefficient);
        }
        return polynomialCoefficients;
    }

    private static Polynomial findSingleTerm(Point[] points, int i) {
        Polynomial currentTerm = new Polynomial();
        float xi = points[i].getX();
        float yi = points[i].getY();

        for (int j = 0; j < points.length; j++) {
            if (j != i) {
                float xj = points[j].getX();
                currentTerm = currentTerm.multiplyByPolynomial(calculatePolynomialMultiplier(xi, xj));
            }
        }

        return multiplyPolynomialByNumber(currentTerm, yi);
    }

    private static Polynomial sumPolynomialCoefficients(ArrayList<Polynomial> coefficients) {
        Polynomial finalPolynomial = new Polynomial(new ArrayList<>());
        for (Polynomial polynomialCoefficient : coefficients) {
            finalPolynomial = finalPolynomial.addPolynomialTo(polynomialCoefficient);
        }
        return finalPolynomial;
    }

    private static Polynomial calculatePolynomialMultiplier(float xi, float xj) {
        ArrayList<Float> multiplier = new ArrayList<>();
        multiplier.add((-xj) / (xi - xj));
        multiplier.add(1.0f  / (xi - xj));
        return new Polynomial(multiplier);
    }

    private static Polynomial multiplyPolynomialByNumber(Polynomial polynomial, float number) {
        ArrayList<Float> singleCoefficient = new ArrayList<>();
        singleCoefficient.add(number);

        return polynomial.multiplyByPolynomial(new Polynomial(singleCoefficient));
    }

    private static boolean areXValuesDistinct(Point[] points) {
        ArrayList<Float> xValues = new ArrayList<>();
        for (Point p : points) {
            xValues.add(p.getX());
        }

        Set<Float> xValuesSet = new HashSet<>(xValues);
        return xValuesSet.size() == xValues.size();
    }
}
