import java.util.Scanner;

public class LowerRelaxationMethod {
    private Matrix A;
    private int amountStrings;
    private double[] b;
    private double[] x1;
    private double[] x2;
    private double countOfIterations;
    private double w;
    private double e;

    public LowerRelaxationMethod() {
        amountStrings = 3;
        A = new Matrix(amountStrings, amountStrings);
        b = new double[amountStrings];
        x1 = new double[amountStrings];
        x2 = new double[amountStrings];
        w = 0.9;
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
        for (int i = 0; i < amountStrings; i++) {
            x2[i] = b[i] / A.matrix[i][i];
        }
        do {
            for (int i = 0; i < amountStrings; i++) {
                x1[i] = x2[i];
                x2[i] = 0;
            }
            for (int i = 0; i < amountStrings; i++) {
                for (int j = 0; j < amountStrings; j++) {
                    if (j < i) {
                        x2[i] += -w * A.matrix[i][j] / A.matrix[i][i] * x2[j];
                    } else if (j > i) {
                        x2[i] += -w * A.matrix[i][j] / A.matrix[i][i] * x1[j];
                    }
                }
                x2[i] += (1 - w) * x1[i];
                x2[i] += w * b[i] / A.matrix[i][i];
            }
            countOfIterations++;
        } while (normArray(subtractArrays(x2, x1)) > e * w);
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
