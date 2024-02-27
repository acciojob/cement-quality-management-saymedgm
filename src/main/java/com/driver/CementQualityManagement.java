package com.driver;

import java.util.*;


import java.util.*;
import java.util.*;

public class CementQualityManagement<T extends Number> {
    private Map<String, Map<String, List<T>>> store = new HashMap<>();

    public String addCementBrand() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Brand Name: ");
        String brandName = scanner.nextLine();

        Map<String, List<T>> brandData = new HashMap<>();

        while (true) {
            System.out.print("Enter Quality Parameter Name (or 'exit' to finish): ");
            String paramName = scanner.nextLine();

            if ("exit".equalsIgnoreCase(paramName)) {
                break;
            }

            System.out.print("Enter Quality Parameter Value: ");
            T paramValue = parseInput(scanner.nextLine());

            brandData.computeIfAbsent(paramName, k -> new ArrayList<>()).add(paramValue);
        }

        store.put(brandName, brandData);
        return "Brand " + brandName + " added successfully!";
    }

    public void fetchQualityParameters(String brandName) {
        Map<String, List<T>> brandData = store.get(brandName);
        if (brandData != null) {
            for (Map.Entry<String, List<T>> entry : brandData.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
        } else {
            throw new BrandNotFoundException("Brand not found: " + brandName);
        }
    }

    public double computeAverageValue(String brandName, String paramName) {
        List<T> values = Optional.ofNullable(store.get(brandName))
                .map(data -> data.get(paramName))
                .orElseThrow(() -> new ParameterNotFoundException("Parameter not found: " + paramName));

        return values.stream().mapToDouble(Number::doubleValue).average().orElse(0);
    }

    private T parseInput(String input) {
        try {
            return (T) Double.valueOf(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric input: " + input, e);
        }
    }

    public static class BrandNotFoundException extends RuntimeException {
        BrandNotFoundException(String message) {
            super(message);
        }
    }

    static class ParameterNotFoundException extends RuntimeException {
        ParameterNotFoundException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        CementQualityManagement<Double> cementManager = new CementQualityManagement<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Scenario: Adding a Cement Brand");
        System.out.println(cementManager.addCementBrand());

        System.out.println("\nScenario: Fetching Quality Parameters of a Brand");
        System.out.print("Enter Brand Name: ");
        String brandName = scanner.nextLine();
        cementManager.fetchQualityParameters(brandName);

        System.out.println("\nScenario: Computing Average Value of a Quality Parameter");
        System.out.print("Enter Brand Name: ");
        brandName = scanner.nextLine();
        System.out.print("Enter Quality Parameter Name: ");
        String paramName = scanner.nextLine();
        double averageValue = cementManager.computeAverageValue(brandName, paramName);
        System.out.println("Average value of " + paramName + " for brand " + brandName + ": " + averageValue);

        scanner.close();
    }

    public void addCementBrandWithQualityParameters(String brandName, String string, int i) {
        // TODO Auto-generated method stub

    }
}


