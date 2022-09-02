/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreasy.dreasy3D;

import java.util.ArrayList;

public class Matrix {
    
    public static double[][] rotateX(double[][] m, double a) {
        double[][] r = {
            {1, 0, 0},
            {0, Math.cos(a), -Math.sin(a)},
            {0, Math.sin(a), Math.cos(a)}
        };
        return multiply(r, m);
    }
    
    public static double[][] rotateZ(double[][] m, double a) {
        double[][] r = {
            {Math.cos(a), -Math.sin(a), 0},
            {Math.sin(a), Math.cos(a), 0},
            {0, 0, 1}
        };
        return multiply(r, m);
    }
    
    public static double[][] rotateY(double[][] m, double a) {
        double[][] r = {
            {Math.cos(a), 0, Math.sin(a)},
            {0, 1, 0},
            {-Math.sin(a), 0, Math.cos(a)}
        };
        return multiply(r, m);
    }
    
    public static double [][] scale(double[][] m, double s) {
        for (int i=0; i<m.length; i++) {
            for (int j=0; j<m[0].length; j++) {
                m[i][j]*=s;
            } 
        }
        return m;
    }
    
    public static double[][] multiply(double[][] a, double[][] b) {
        if (a[0].length != b.length) {
            System.out.println("Cols of A (" + a[0].length + ") must match rows of B (" + b.length + ")");
            return null;
        }
        double[][] r = new double[a.length][b[0].length];
        
        for (int i=0; i<a.length; i++) {
            for (int j=0; j<b[0].length; j++) {
                double sum = 0;
                for (int k=0; k<b.length; k++) {
                    sum += a[i][k] * b[k][j];
                }
                r[i][j] = sum;
            }
        }
        
        return r;
    }
    
    public static Vector2[] matrixToPoints(double[][] m) {
        if (m.length != 3){
            System.out.println("The matrix must be comprised of 3D vectors");
            return null;
        }
        
        Vector2[] v = new Vector2[m[0].length];
        
        for (int i=0; i<m[0].length; i++) {
            double[][] p3 = new double[3][1];
            p3[0][0] = m[0][i];
            p3[1][0] = m[1][i];
            p3[2][0] = m[2][i];
            
            boolean flip = false;
            if (p3[2][0] > 0) {
                flip = true;
            }
            
            double[][] p2; 
            //don't draw if behind player (z > 0)
//            if (p3[2][0] <= 0) {
                double z = 1/(p3[2][0]);
                double[][] proj = {
                    {z, 0, 0},
                    {0, z, 0}
                };
                p2 = multiply(proj, p3);
//            } else {
//                p2 = new double[2][1];
//                p2[0][0] = Double.POSITIVE_INFINITY;
//                p2[1][0] = Double.POSITIVE_INFINITY;
//            }
            
            v[i] = new Vector2(p2[0][0], p2[1][0], flip);
        }
        return v;
    }
    
    public static void printMatrix(double[][] m) {
        System.out.println("Matrix:");
        for (int i=0; i<m.length; i++) {
            System.out.print("|");
            for (int j=0; j<m[i].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println("|");
        }
    }
    
    public static double[][] Vector3ToMatrix(ArrayList<Vector3> v){
        double[][] m = new double[3][v.size()];
        for (int i=0; i<v.size(); i++) {
            m[0][i] = v.get(i).getX();
            m[1][i] = v.get(i).getY();
            m[2][i] = v.get(i).getZ();
        }
        return m;
    }
    
    public static double[][] translate(double[][] m, double x, double y, double z) {
        for (int i=0; i<m[0].length; i++) {
            m[0][i] += x;
            m[1][i] += y;
            m[2][i] += z;
        }
        return m;
    }
}
