package datastructures;

import org.ejml.simple.SimpleMatrix;

public class FitData {

    private SimpleMatrix theta;
    private int degree;

    public FitData(SimpleMatrix th, int deg) {
        theta = th;
        degree = deg;
    }

    public SimpleMatrix getTheta() {
        return theta;
    }

    public void setTheta(SimpleMatrix theta) {
        this.theta = theta;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public String toString() {
        return "Degree: " + degree + "\nTheta: \n" + theta.toString();
    }
}
