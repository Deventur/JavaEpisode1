package com.company;

import java.util.*;

public class Lesson1 {

    public static void main(String[] args) {

        try {

            while (true) {

                new Lesson1().renderMenu();
                String input = new Lesson1().inputString();

                if ("exit".equals(input.toLowerCase())) {
                    System.out.println("Exit!");
                    System.exit(0);
                }

                Matrix m1 = null;
                Matrix m2 = null;
                Matrix rm = null;

                switch (Integer.parseInt(input)) {
                    case 1:

                        System.out.print("Input linesNum: ");
                        Integer linesNum = Integer.parseInt(new Lesson1().inputString());

                        System.out.print("Input columsNum: ");
                        Integer columsNum = Integer.parseInt(new Lesson1().inputString());

                        m1 = new Matrix(linesNum, columsNum);
                        m1.printMatrix();
                        System.out.println("Determinant: " + m1.getDeterminant());
                        break;
                    case 2:
                        m1 = new Matrix(3, 4);
                        System.out.println("Input matrix1: ");
                        m1.inputMatrix();
                        System.out.println("==============");
                        m1.printMatrix();
                        System.out.println("==============");
                        m2 = new Matrix(4, 3);
                        System.out.println("Input matrix2: ");
                        m2.inputMatrix();
                        System.out.println("==============");
                        m2.printMatrix();
                        System.out.println("==============");

                        rm = m1.multiply(m2);
                        System.out.println("Result matrix: ");
                        rm.printMatrix();
                        break;
                    case 3:
                        m1 = new Matrix(3, 3);
                        System.out.println("Input matrix1: ");
                        m1.inputMatrix();
                        System.out.println("==============");
                        m1.printMatrix();
                        System.out.println("==============");
                        m2 = new Matrix(3, 3);
                        System.out.println("Input matrix2: ");
                        m2.inputMatrix();
                        System.out.println("==============");
                        m2.printMatrix();
                        System.out.println("==============");

                        rm = m1.add(m2);
                        System.out.println("Result matrix: ");
                        rm.printMatrix();
                        break;


                    default:
                        new Lesson1().renderMenu();
                }

                new Lesson1().promptEnterKey();
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void clearConsole() {
        for (int clear = 0; clear < 50; clear++) {
            System.out.println("\b");
        }
    }

    private void renderMenu() {
        clearConsole();
        System.out.println("Mathematical module for working with matrices!");

        System.out.println("\b");

        System.out.print("1. Determinant.\n");
        System.out.print("2. Multiplication.\n");
        System.out.print("3. Addition\n");
        System.out.print("Write 'exit' for exit.\n");

        System.out.println("\b");

        System.out.print("Enter your choice : ");
    }

    private String inputString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    Double inputDouble() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextDouble();
    }

    private void promptEnterKey() {
        System.out.println("Press \"ENTER\" to continue...");
        new Lesson1().inputString();
    }

}
