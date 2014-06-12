package polynomial;

import org.ejml.simple.SimpleMatrix;
import linear.LinearRegression;
import datastructures.FitData;

public class PolynomialRegression {

    SimpleMatrix X;
    SimpleMatrix Y;
    SimpleMatrix theta;
    LinearRegression lobj1;
    int degree1 = 0;
    int degree2 = 0;
    int finaldegree = 0;

    public PolynomialRegression(SimpleMatrix x, SimpleMatrix y, int deg) {
        X = x;
        Y = y;
        degree1 = deg;
        theta = null;
    }

    public PolynomialRegression(SimpleMatrix x, SimpleMatrix y, int deg1, int deg2) {
        X = x;
        Y = y;
        degree1 = deg1;
        degree2 = deg2;
        if (degree1 > degree2) {
            int temp = degree1;
            degree1 = degree2;
            degree2 = temp;
        }
        theta = null;
    }

    public PolynomialRegression(double[][] x, double[][] y, int deg) {
        X = new SimpleMatrix(x);
        Y = new SimpleMatrix(y);
        degree1 = deg;
        theta = null;
    }

    public PolynomialRegression(double[][] x, double[][] y, int deg1, int deg2) {
        X = new SimpleMatrix(x);
        Y = new SimpleMatrix(y);
        degree1 = deg1;
        degree2 = deg2;
        if (degree1 > degree2) {
            int temp = degree1;
            degree1 = degree2;
            degree2 = temp;
        }
        theta = null;
    }

    public FitData fit() {
        if (degree2 == 0) {
            FitData fitData = fit(degree1);
            theta = fitData.getTheta();
            return fitData;
        } else {
            FitData[] thetaMatrix = new FitData[degree2 - degree1 + 1];
            double[] leastSquareError = new double[degree2 - degree1 + 1];
            for (int deg = degree1, i = 0; deg <= degree2; deg++, i++) {
                thetaMatrix[i] = fit(deg);
                SimpleMatrix ydash = Y.plus(predict(X, thetaMatrix[i].getTheta(), deg).negative());
                for (int row = 0; row < ydash.numRows(); row++) {
                    leastSquareError[i] += Math.pow(ydash.get(row, 0), 2);
                }
            }
            double min = Double.POSITIVE_INFINITY;
            int pos = 0;
            for (int i = 0; i < leastSquareError.length; i++) {
                if (leastSquareError[i] < min) {
                    min = leastSquareError[i];
                    pos = i;
                }
            }
            finaldegree = degree1 + pos;
            theta = thetaMatrix[pos].getTheta();
            return thetaMatrix[pos];
        }
    }

    private FitData fit(int degree) {
        double[][] x1 = new double[X.numRows()][X.numCols() * degree];
        for (int row = 0; row < X.numRows(); row++) {
            for (int col = 0; col < X.numCols(); col++) {
                for (int deg = 0; deg < degree; deg++) {
                    x1[row][degree * col + deg] = Math.pow(X.get(row, col), deg + 1);
                }
            }
        }
        SimpleMatrix xdash = new SimpleMatrix(x1);
        lobj1 = new LinearRegression(xdash, Y);
        theta = lobj1.fit();
        return new FitData(theta, degree);
    }

    public SimpleMatrix predict(double[][] x) {
        return predict(x, theta);
    }

    private SimpleMatrix predict(double[][] x, SimpleMatrix theta) {
        if (theta == null) {
            fit();
        }
        double[][] y = new double[x.length][1];
        for (int i = 0; i < x.length; i++) {
            y[i][0] = 0;
            for (int j = 0; j < x[0].length; j++) {
                for (int deg = 0; deg < degree1; deg++) {
                    y[i][0] += (Math.pow(x[i][j], deg + 1) * theta.get(j * degree1 + deg + 1, 0));
                }
            }
            y[i][0] += theta.get(0, 0);
        }
        return new SimpleMatrix(y);
    }

    private SimpleMatrix predict(double[][] x, SimpleMatrix theta, int degree1) {
        if (theta == null) {
            fit();
        }
        double[][] y = new double[x.length][1];
        for (int i = 0; i < x.length; i++) {
            y[i][0] = 0;
            for (int j = 0; j < x[0].length; j++) {
                for (int deg = 0; deg < degree1; deg++) {
                    y[i][0] += (Math.pow(x[i][j], deg + 1) * theta.get(j * degree1 + deg + 1, 0));
                }
            }
            y[i][0] += theta.get(0, 0);
        }
        return new SimpleMatrix(y);
    }

    private SimpleMatrix predict(SimpleMatrix x, SimpleMatrix theta) {
        if (theta == null) {
            fit();
        }
        double[][] y = new double[x.numRows()][1];
        for (int i = 0; i < x.numRows(); i++) {
            y[i][0] = 0;
            for (int j = 0; j < x.numCols(); j++) {
                for (int deg = 0; deg < degree1; deg++) {
                    y[i][0] += (Math.pow(x.get(i, j), deg + 1) * theta.get(j * degree1 + deg + 1, 0));
                }
            }
            y[i][0] += theta.get(0, 0);
        }
        return new SimpleMatrix(y);
    }

    private SimpleMatrix predict(SimpleMatrix x, SimpleMatrix theta, int degree1) {
        if (theta == null) {
            fit();
        }
        double[][] y = new double[x.numRows()][1];
        for (int i = 0; i < x.numRows(); i++) {
            y[i][0] = 0;
            for (int j = 0; j < x.numCols(); j++) {
                for (int deg = 0; deg < degree1; deg++) {
                    y[i][0] += (Math.pow(x.get(i, j), deg + 1) * theta.get(j * degree1 + deg + 1, 0));
                }
            }
            y[i][0] += theta.get(0, 0);
        }
        return new SimpleMatrix(y);
    }
}
