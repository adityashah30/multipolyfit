package linear;

import org.ejml.simple.SimpleMatrix;

public class LinearRegression {

    SimpleMatrix X;
    SimpleMatrix Y;
    SimpleMatrix theta;

    public LinearRegression(double[][] x, double[][] y) {
        double[][] xdash = new double[x.length][1];
        for (int i = 0; i < xdash.length; i++) {
            xdash[i][0] = 1;
        }
        X = new SimpleMatrix(xdash);
        X = X.combine(0, 1, new SimpleMatrix(x));
        Y = new SimpleMatrix(y);
        theta = null;
    }

    public LinearRegression(SimpleMatrix x, SimpleMatrix y) {
        double[][] xdash = new double[x.numRows()][1];
        for (int i = 0; i < xdash.length; i++) {
            xdash[i][0] = 1;
        }
        X = new SimpleMatrix(xdash).combine(0, 1, x);
        Y = y;
        theta = null;
    }

    public SimpleMatrix fit() {
        theta = (X.transpose().mult(X)).pseudoInverse().mult(X.transpose()).mult(Y);
        return theta;
    }

    public SimpleMatrix predict(double[][] x) {
        if (theta == null) {
            fit();
        }
        double[][] y = new double[x.length][1];
        for (int i = 0; i < x.length; i++) {
            y[i][0] = 0;
            for (int j = 0; j < x[0].length; j++) {
                y[i][0] += (x[i][j] * theta.get(j + 1, 0));
            }
            y[i][0] += theta.get(0, 0);
        }
        return new SimpleMatrix(y);
    }

    public SimpleMatrix getCoefficients() {
        return theta;
    }
}
