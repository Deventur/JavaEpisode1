package com.company;

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

    private Integer getColumsNum() {
        return columsNum;
    }

    private Integer getLinesNum() {
        return linesNum;
    }

    private void setColumsNum(Integer columsNum) {
        this.columsNum = columsNum;
    }

    private void setLinesNum(Integer linesNum) {
        this.linesNum = linesNum;
    }

    private Double getElement(Integer line, Integer colum) {
        return matrix.get(line).get(colum);
    }

    /**
     * Метод ввода элемента матрицы.
     * Реализованы проверки на то, можно ли добавить элемент в указанную позицию.
     * Если указанной строки не существует, и разница между максимальным индексом и переданным больше 1 - выход.
     * Если в строке нет элемента с таким индексом, и разница между максимальным индексом и переданным больше 1 - выход.
     * В противном случае либо заменяем значение, либо добавляем его.
     * @param line - индекс позиции строки в которую надо добавить элемент.
     * @param column - индекс позиции элемента в строке куда надо добавить элемент.
     * @param value - значение элемента.
     */
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

    /**
     * Метод ввода матрицы. Матрица вводиться построчно.
     */
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

    /**
     * Обертка для метода вычисления определителья матрицы без параметров.
     * Определьтель вычисляется от матрицы - члена класса
     */
    private void calcDeterminant() {

        if (this.matrix.size() == 0) inputMatrix();

        if (!this.getColumsNum().equals(this.getLinesNum())) {

            throw new IllegalArgumentException("This matrix is not square!!!");

        } else if (this.getLinesNum() == 2)

            determinant = calcDeterminantSecondOrder(this.matrix);

        else
            determinant = calcDeterminant(this.matrix);
    }

    /**
     * Рекурсивный метод вычисления определителя матрицы
     * @param matrix матрица от которой надо вычислить определитель
     * @return определьтель матрицы
     */
    private Double calcDeterminant(List<List<Double>> matrix) {

        Double det = 0.0;

        if ((matrix.size() == 0)
                || (matrix.size() != matrix.get(0).size())) {

            throw new IllegalArgumentException("This matrix is not square!!!!");

        }
        else if (matrix.size() == 2)

            det = calcDeterminantSecondOrder(matrix);

        else

            for (int i = 0; i < matrix.get(0).size(); i++) {

                List<List<Double>> minore = createMinoreMatrix(0, i, matrix);
                Double curElement = matrix.get(0).get(i);
                Double inc = curElement * calcDeterminant(minore);
                det += i % 2 == 0 ? (-1) * inc : inc;

            }

        return det;
    }

    /**
     * Вычисление определителя матрицы второго порядка
     * @param matrix - матрица от которой необходимо вычислить определитель
     * @return определитель матрицы
     */
    private Double calcDeterminantSecondOrder(List<List<Double>> matrix) {

        if ((matrix.size() == 0)
                || (matrix.size() != matrix.get(0).size())
                || (matrix.get(0).size() != 2)) {
            throw new IllegalArgumentException("Broken matrix!");
        }

        return matrix.get(0).get(0) * matrix.get(1).get(1) - matrix.get(0).get(1) * matrix.get(1).get(0);
    }

    /**
     * Getter детерминанта
     * @return детерминант
     */
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

    /**
     * Создает матрицу-минор для переданной на вход.
     * @param linesNum Номер строки на которой находится элемент по которому вычисляется минор матрицы
     * @param columsNum Номер столбца на которой находится элемент по которому вычисляется минор матрицы
     * @param matrix Матрица от которой надо вычислить минор
     * @return Минор матрицы
     */
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

    /**
     * Распечатывает в консоль матрицу из текущего объекта класса
     */
    void printMatrix() {
        if (this.matrix.size() == 0) return;

        for (int i = 0; i < linesNum; i++) {
            for (int j = 0; j < columsNum; j++) {
                System.out.print(this.matrix.get(i).get(j) + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Распечатывает в консоль произвольную матрицу
     * @param matrix - матрица которую необходимо распечатать.
     */
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
