package com.company;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Matrix {

    private Integer columsNum;
    private Integer linesNum;
    private Double determinant;
    private List<List<Double>> matrix;

    Matrix() {
        setColumsNum(3);
        setLinesNum(3);
        matrix = new ArrayList<>();
    }

    Matrix(Integer linesNum, Integer columsNum) {
        setLinesNum(linesNum);
        setColumsNum(columsNum);
        matrix = new ArrayList<>();
    }

    Integer getColumsNum() {
        return columsNum;
    }

    Integer getLinesNum() {
        return linesNum;
    }

    void setColumsNum(Integer columsNum) {
        this.columsNum = columsNum;
    }

    void setLinesNum(Integer linesNum) {
        this.linesNum = linesNum;
    }

    Double getElement(Integer line, Integer colum) {
        return matrix.get(line).get(colum);
    }

    private void inputElement(Integer line, Integer column, Double value) {

        if (matrix.size() - (line - 1) == 1) { //Если у нас всего на одну строку меньше

            List<Double> l = new ArrayList<>();
            l.add(value);
            matrix.add(l); //Добавляем новую строку

        } else if (matrix.get(line).size() - (column - 1) == 1) {//Если у нас индекс крайнего элемента от вставляемого отличается на единицу

            matrix.get(line).add(value);//Добавляем элемент в эту строку

        } else if ((matrix.size() - (line - 1) > 0) && (matrix.get(line).size() - (column - 1) > 0)) {

            matrix.get(line).set(column, value);//Иначе заменяем элемент

        }
    }

    void inputMatrix() {

        try {
            Scanner scanner = new Scanner(System.in);
            for (int i = 0; i < linesNum; i++) {
                System.out.print("Input " + i + " line: ");
                for (int j = 0; j < columsNum; j++) {
                    Double a = scanner.nextDouble();//new Lesson1().inputDouble();
                    inputElement(i, j, a);
                }
            }
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }

    }

    private Double calcDeterminant() {
        if (this.matrix.size() == 0) inputMatrix();
        if (!this.getColumsNum().equals(this.getLinesNum())) {
            throw new IllegalArgumentException("This matrix is not square!!!");
        } else if (this.getLinesNum() == 2)
            determinant = calcDeterminantSecondOrder(this.getLinesNum(), this.getColumsNum(), this.matrix);
        else
            determinant = calcDeterminant(this.getLinesNum(), this.getColumsNum(), this.matrix);
        return determinant;
    }

    private Double calcDeterminant(Integer linesNum, Integer columsNum, List<List<Double>> matrix) {
        Double det = 0.0;
        if (!linesNum.equals(columsNum))
            return null;
        else if (linesNum == 2)

            det = calcDeterminantSecondOrder(linesNum, columsNum, matrix);

        else

            for (int i = 0; i < columsNum; i++) {
                List<List<Double>> minore = createMinoreMatrix(0, i, matrix);
                Double curElement = matrix.get(0).get(i);
                Double inc = curElement * calcDeterminant(linesNum - 1, columsNum - 1, minore);
                det += i % 2 == 0 ? (-1) * inc : inc;

            }

        return det;
    }

    private Double calcDeterminantSecondOrder(Integer linesNum, Integer columsNum, List<List<Double>> matrix) {

        if (!linesNum.equals(columsNum) || columsNum != 2) {
            return null;
        }

        return matrix.get(0).get(0) * matrix.get(1).get(1) - matrix.get(0).get(1) * matrix.get(1).get(0);
    }

    Double getDeterminant() {

        try {
            if (determinant == null) {
                calcDeterminant();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return determinant;
    }


    private List<List<Double>> createMinoreMatrix(Integer linesNum, Integer columsNum, List<List<Double>> matrix) {

        List<List<Double>> matrixMinore = new ArrayList<>();

        for (int i = 0; i < matrix.size(); i++) {
            if (i == linesNum) continue;
            List<Double> l = new ArrayList<>();
            for (int j = 0; j < matrix.get(linesNum).size(); j++) {
                if (j == columsNum) continue;
                l.add(matrix.get(i).get(j));
            }
            matrixMinore.add(l);
        }

        return matrixMinore;
    }

    void printMatrix() {
        if (this.matrix.size() == 0) return;

        for (int i = 0; i < linesNum; i++) {
            for (int j = 0; j < columsNum; j++) {
                System.out.print(this.matrix.get(i).get(j) + "\t");
            }
            System.out.println();
        }
    }

    private void printMatrix(List<List<Double>> matrix) {
        if (matrix.size() == 0) return;

        for (List<Double> aMatrix : matrix) {
            for (int j = 0; j < matrix.get(0).size(); j++) {
                System.out.print(aMatrix.get(j) + "\t");
            }
            System.out.println();
        }
    }

    Matrix add(Matrix matrix2) {
        Matrix resultMatrix = new Matrix(columsNum, linesNum);
        if (this.matrix == null || matrix2 == null) {
            return null;
        } else if (!this.columsNum.equals(matrix2.getColumsNum()) || !this.linesNum.equals(matrix2.linesNum)) {
            return null;
        } else {

            for (int i = 0; i < linesNum; i++) {
                for (int j = 0; j < columsNum; j++) {
                    resultMatrix.inputElement(i, j, this.getElement(i, j) + matrix2.getElement(i, j));
                }
            }
        }
        return resultMatrix;
    }

    Matrix multiply(Matrix matrix2) {
        if (!this.getColumsNum().equals(matrix2.getLinesNum())) return null;

        Matrix resultMatrix = new Matrix(this.getLinesNum(), matrix2.getColumsNum());
        for (int i = 0; i < this.getLinesNum(); i++) {
            for (int j = 0; j < matrix2.getColumsNum(); j++) {
                Double r = 0.0;
                for (int k = 0; k < matrix2.getLinesNum(); k++) {
                    r += this.matrix.get(i).get(k) * matrix2.matrix.get(k).get(j);
                }
                resultMatrix.inputElement(i, j, r);
            }
        }
        return resultMatrix;
    }
}
