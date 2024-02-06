package com.example.chatapp;

import java.util.Scanner;
import java.util.ArrayList;

public class kDEA {
    private static String[] dimensions;
    private static ArrayList<Integer> path;
    private static String message;

    public static void generateKey() {
        Scanner in = new Scanner(System.in);
        path = new ArrayList<>();

        boolean continueEntering = true;

        System.out.println("Enter the dimensions(x y) of matrix, separated by spaces: ");
        String d_input = in.nextLine();

        dimensions = d_input.split(" ");

        if (dimensions.length != 2) {
            System.out.println("Please enter Valid Dimension, check the condition.");
        }

        int firstElement = Integer.parseInt(dimensions[0]);
        int secondElement = Integer.parseInt(dimensions[1]);

        System.out.println("The Dimensions of Matrix(the first private key; remember it): " + firstElement + ", " + secondElement);

        int[][] matrix = new int[firstElement][secondElement];
        int counter = 1;

        for (int i = 0; i < firstElement; i++) {
            for (int j = 0; j < secondElement; j++) {
                matrix[i][j] = counter;
                counter++;
            }
        }

        for (int i = 0; i < firstElement; i++) {
            for (int j = 0; j < secondElement; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Choose any number from the Matrix printed(the public key): ");
        int publicKey = in.nextInt();

        System.out.println("Your public key is: " + publicKey);

        System.out.println("Now, for the second private key, you will be required to enter a path to reach the public key.");
        System.out.println("For declaring your unique path, enter the numbers selected (separated by spaces) and enter 'done' to stop entering the numbers.");

        while (continueEntering) {
            String pathNumbers = in.next();

            if (pathNumbers.equalsIgnoreCase("done")) {
                continueEntering = false;
            } else {
                try {
                    int number = Integer.parseInt(pathNumbers);
                    path.add(number);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            }
        }

        System.out.print("The second private key is: ");
        for (Integer number : path) {
            System.out.print(number + " ");
        }

        System.out.println(" ");
        System.out.println("Enter your message: ");
        message = in.next();
        System.out.println("The message has been locked.");

        // Close the local Scanner when you're done with it


    }

    public static void accessLocker() {
        Scanner in1 = new Scanner(System.in);
        ArrayList<Integer> dynamicArray = new ArrayList<>();

        // Create dynamicArray here
        // System.out.println("Please enter two elements for the array:");
        System.out.println("Enter the Matrix Dimensions: ");

        int[] userArray = new int[2];

        for (int i = 0; i < 2; i++) {
            System.out.print("Element " + (i + 1) + ": ");
            userArray[i] = in1.nextInt();
        }

        // Compare userArray with dimensions
        if (userArray.length != dimensions.length) {
            System.out.println("Error: Matrix Dimensions Mis-matched");
        } else {
            boolean areEqual = true;
            for (int i = 0; i < userArray.length; i++) {
                if (userArray[i] != Integer.parseInt(dimensions[i])) {
                    areEqual = false;
                    break;  // Exit the loop if an inequality is found
                }
            }

            if (areEqual) {
                // System.out.println("userArray and dimensions are equal.");

                System.out.println("Enter the Unique Path (enter 'done' to finish):");

                while (true) {
                    String input = in1.next();

                    if (input.equalsIgnoreCase("done")) {
                        break; // Exit the loop if the user enters 'done'
                    }

                    try {
                        int number = Integer.parseInt(input);
                        dynamicArray.add(number); // Add the number to the dynamic array
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number or 'done'.");
                    }
                }

                // Compare dynamicArray with path
                if (dynamicArray.size() != path.size()) {
                    System.out.println("Error: The Private key is not Valid.");
                } else {
                    boolean areEqualPath = true;
                    for (int i = 0; i < dynamicArray.size(); i++) {
                        if (!dynamicArray.get(i).equals(path.get(i))) {
                            areEqualPath = false;
                            break;  // Exit the loop if an inequality is found
                        }
                    }

                    if (areEqualPath) {
                        System.out.println("The locker has been accessed.");
                        System.out.println("The message is: " +message);
                    } else {
                        System.out.println("Error: The Private key is Invalid.");
                    }
                }
            } else {
                System.out.println("Error: Matrix Dimensions invalid.");
            }
        }

        // Close the local Scanner when you're done with it

    }

    public static void main(String[] args) {
        Scanner in2 = new Scanner(System.in);

        boolean is_on = true;

        System.out.println("----- Welcome to SysLoc -----");
        while (is_on) {
            System.out.println("1.) Generate key");
            System.out.println("2.) Access the locker");
            System.out.println("3.) Exit");
            System.out.println("Enter your choice: ");
            int a = in2.nextInt();

            if (a == 1) {
                generateKey();
            } else if (a == 2) {
                accessLocker();
            } else if (a == 3) {
                is_on = false;
            } else {
                System.out.println("Invalid input.");
            }
        }

        // Close the Scanner for System.in after the program is done

    }
}