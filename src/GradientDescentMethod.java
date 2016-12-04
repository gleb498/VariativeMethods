import java.util.Scanner;

public class GradientDescentMethod {
    private Matrix A;
    private int amountStrings;
    private double[] b;
    private double[] x1;
    private double[] x2;
    private double countOfIterations;
    private double e;
    private double[] rk;

    public GradientDescentMethod() {
        amountStrings = 3;
        A = new Matrix(amountStrings, amountStrings);
        b = new double[amountStrings];
        x1 = new double[amountStrings];
        x2 = new double[amountStrings];
        rk = new double[amountStrings];
        e = 0.00001;
        countOfIterations = 1;
    }

    private void fillB(double[][] mtr) {
        Scanner sc = new Scanner(System.in);
        double[] temp = new double[amountStrings];
        System.out.println("b: ");
        for (int i = 0; i < amountStrings; i++) {
            temp[i] = sc.nextDouble();
        }
        for (int i = 0; i < mtr.length; i++) {
            for (int k = 0; k < mtr.length; k++) {
                b[i] += mtr[i][k] * temp[k];
            }
        }
    }

    private void printArray(double[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.printf("%-15f", a[i]);
        }
        System.out.println();
        System.out.println();
    }

    private double scalarMultiply(double[] a, double[] b) {
        double result = 0;
        int length = a.length;
        for (int i = 0; i < length; i++) {
            result += a[i] * b[i];
        }
        return result;
    }

    private double[] multiplyMatrix_Array(double[][] m, double[] a) {
        double[] result = new double[amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                result[i] += m[i][j] * a[j];
            }
        }
        return result;
    }

    private double[] multiplyNumber_Array(double n, double[] a) {
        double[] result = new double[amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            result[i] += n * a[i];
        }
        return result;
    }

    private double normArray(double[] arr) {
        double max = Math.abs(arr[0]);
        for (int i = 1; i < amountStrings; i++) {
            if (Math.abs(arr[i]) > max) {
                max = Math.abs(arr[i]);
            }
        }
        return max;

    }

    private double[] subtractArrays(double[] a, double[] b) {
        double[] result = new double[a.length];
        for (int i = 0; i < amountStrings; i++) {
            result[i] = a[i] - b[i];
        }
        return result;
    }

    private void method() {
        double coef;
        for (int i = 0; i < amountStrings; i++) {
            x2[i] = b[i] / A.matrix[i][i];
        }
        do {
            for (int i = 0; i < amountStrings; i++) {
                x1[i] = x2[i];
                x2[i] = 0;
            }
            rk = subtractArrays(multiplyMatrix_Array(A.matrix, x1), b);
            coef = scalarMultiply(rk, rk) / scalarMultiply(multiplyMatrix_Array(A.matrix, rk), rk);
            x2 = subtractArrays(x1, multiplyNumber_Array(coef, rk));
            countOfIterations++;
        } while (normArray(subtractArrays(multiplyMatrix_Array(A.matrix, x2), b)) > e);
    }

    private void printSolution() {
        System.out.println("X: ");
        printArray(x2);
    }

    private void checkEqualization() {
        double[] r = new double[amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                r[i] += A.matrix[i][j] * x2[j];
            }
            r[i] -= b[i];
        }
        System.out.println("r = A * x - f: ");
        for (double item : r) {
            System.out.printf("%E", item);
            System.out.println();
        }
        System.out.println("||r|| = " + normArray(r));
    }

    public void solve() {
        A.input();
        fillB(A.toSymmetric());
        System.out.println("Matrix a: ");
        A.print();
        System.out.println("Array b: ");
        printArray(b);
        method();
        printSolution();
        System.out.println("Number of iterations: " + countOfIterations);
        checkEqualization();
    }
}
