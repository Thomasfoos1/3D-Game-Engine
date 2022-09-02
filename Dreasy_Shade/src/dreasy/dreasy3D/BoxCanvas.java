/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreasy.dreasy3D;

import dreasy.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BoxCanvas extends Canvas implements KeyListener{
    
    public BoxCanvas() {
        loop = true;
    }
    
    double ry = 0;
    double ax=0, ay=0, az=0;
    
    double[][] vert = {
        {-0.5, 0.5, 0.5,-0.5,-0.5, 0.5, 0.5,-0.5},
        {-0.5,-0.5, 0.5, 0.5,-0.5,-0.5, 0.5, 0.5},
        {-0.5,-0.5,-0.5,-0.5, 0.5, 0.5, 0.5, 0.5}
    };
    
    double[][] translate = {
        {0},
        {0},
        {0}
    };
    
    double distance = 0;
    
    @Override
    protected void paintComponent(Graphics g) {
        reset(g);
        translate(this.getWidth()/2, this.getHeight()/2);
        
        //rotate around internal axis
        double[][] m = vert;
        m = matRotateX(m, ax);
        m = matRotateY(m, ay);
        m = matRotateZ(m, az);
        
        //translate
        for (int i=0; i<m[0].length; i++) {
            m[2][i] += translate[2][0];
        }
        
        //rotate around global axis
        m = matRotateY(m, ry);
        
        //Project
        double[][] f = new double[2][m[0].length];
        for (int i=0; i<m[0].length; i++) {
            double[][] p3 = new double[3][1];
            p3[0][0] = m[0][i];
            p3[1][0] = m[1][i];
            p3[2][0] = m[2][i];
            
            double z = 1/(distance - p3[2][0]);
            double[][] proj = {
                {z, 0, 0},
                {0, z, 0}
            };
            
            double[][] p2 = matMul(proj, p3);
            
            f[0][i] = p2[0][0];
            f[1][i] = p2[1][0];
        }
        
        
        Vector2[] pts = matrixToPoints(matScale(f,200));
        
        for (int i=0; i<4; i++) {
            line(g, pts[i].getX(), pts[i].getY(), pts[(i+1)%4].getX(), pts[(i+1)%4].getY());
            line(g, pts[i+4].getX(), pts[i+4].getY(), pts[((i+1)%4)+4].getX(), pts[((i+1)%4)+4].getY());
            line(g, pts[i].getX(), pts[i].getY(), pts[i+4].getX(), pts[i+4].getY());
        }
        
        ax += 0.001;
        ay += 0.001;
        az += 0.001;
    }
    
    double[][] matRotateX(double[][] m, double a) {
        double[][] r = {
            {1, 0, 0},
            {0, Math.cos(a), -Math.sin(a)},
            {0, Math.sin(a), Math.cos(a)}
        };
        return matMul(r, m);
    }
    
    double[][] matRotateZ(double[][] m, double a) {
        double[][] r = {
            {Math.cos(a), -Math.sin(a), 0},
            {Math.sin(a), Math.cos(a), 0},
            {0, 0, 1}
        };
        return matMul(r, m);
    }
    
    double[][] matRotateY(double[][] m, double a) {
        double[][] r = {
            {Math.cos(a), 0, Math.sin(a)},
            {0, 1, 0},
            {-Math.sin(a), 0, Math.cos(a)}
        };
        return matMul(r, m);
    }
    
    double [][] matScale(double[][] m, double s) {
        for (int i=0; i<m.length; i++) {
            for (int j=0; j<m[0].length; j++) {
                m[i][j]*=s;
            } 
        }
        return m;
    }
    
    double[][] matMul(double[][] a, double[][] b) {
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
    
    Vector2[] matrixToPoints(double[][] m) {
        if (m.length != 2){
            return null;
        }
        Vector2[] v = new Vector2[m[0].length];
        
        for (int i=0; i<m[0].length; i++) {
            v[i] = new Vector2(m[0][i], m[1][i]);
        }
        return v;
    }
    
    void printMatrix(double[][] m) {
        System.out.println("Matrix:");
        for (int i=0; i<m.length; i++) {
            System.out.print("|");
            for (int j=0; j<m[i].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println("|");
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {  
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            //distance -= 0.1;
            translate[2][0] += 0.1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            //distance += 0.1;
            translate[2][0] -= 0.1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            ry -= 0.1;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            ry += 0.1;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println(ry + " " + translate[2][0]);
        }
    }  
    @Override
    public void keyReleased(KeyEvent e) {  
        
    }  
    @Override
    public void keyTyped(KeyEvent e) {  
        
    }  
}