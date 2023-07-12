package game.util;

public class Geometrics {
    public static double[] rotate(double[] vector, double angle) {
        return new double[] {vector[0] * Math.cos(angle) - vector[1] * Math.sin(angle),
                vector[0] * Math.sin(angle) + vector[1] * Math.cos(angle)};
    }
    public static double getRotationAngle(double[] a, double[] b) {
        return Math.acos(dotProduct(a, b) / (getNorm(a) * getNorm(b)));
    }
    private static double getNorm(double[] vector) {
        return Math.sqrt(vector[0] * vector[0] + vector[1] * vector[1]);
    }

    public static double dotProduct (double[] a, double[] b) {
        return a[0] * b[0] + a[1] * b[1];
    }
}
