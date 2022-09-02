/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreasy.dreasy3D;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;



public class Object3D implements Serializable{
    
    ArrayList<Vector3> vertices = new ArrayList();
    ArrayList<Triangle> faces = new ArrayList();
    Vector2[] points;
    private int layer;
    
    double[][] finalVertices;
    
    
    private double x=0, y=0, z=0,
                   rx = 0, ry=0, rz = 0, ary, arx;
    
    public Object3D() {
        layer = 0;
    }
    
    public Object3D(int layer) {
        this.layer = layer;
    }
    
    public Object3D(File f) throws FileNotFoundException {
        Scanner reader = new Scanner(f);
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            
            if (data.length() > 0) {
                if (data.startsWith("v ")) {
                    String[] coords = data.split(" ");
                    System.out.println(data);
                    addVertex(Double.parseDouble(coords[1]), Double.parseDouble(coords[2]), Double.parseDouble(coords[3]));
                }
                if (data.startsWith("f ")) {
                    String[] list = data.split(" ");
                    int[] vert = new int[list.length-1];
                    for(int i=0; i<vert.length; i++) {
                        vert[i] = Integer.parseInt(list[i+1].split("/")[0]);
                    }
                    addFace(vert[0],vert[1],vert[2]);
                }
            }
        }
    } 
    
    public int getLayer() {
        return layer;
    }
    
    public void absoluteRotate(double ay, double az) {
        ary += ay;
        arx += az;
        compute();
    }
    
    public void rotateX(double a) {
        this.rx += a;
        compute();
    }
    
    public void rotateY(double a) {
        this.ry += a;
        compute();
    }
    
    public void rotateZ(double a) {
        this.rz += a;
        compute();
    }
    
    public void translate(double x, double y, double z) {
        this.x += x * Math.cos(ary) + z * Math.sin(-ary);
        this.y += y;
        this.z += z * Math.cos(-ary) + x * Math.sin(ary);
        compute();
    }
    
    public void translateAbsolute(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        compute();
    }
    
    private void compute() {
        //new Thread(this).start();
        run();
    }
    
    public void scale(double x, double y, double z) {
        for (Vector3 v: vertices) {
            v.setX(v.getX() * x);
            v.setY(v.getY() * y);
            v.setZ(v.getZ() * z);
        }
        compute();
    }
    
    public Vector2[] getPoints() {
        if (points == null) {
            compute();
        }
        return points;
    }
    
    public ArrayList<Triangle> getFaces() {
        return faces;
    }
    
    public void addVertex(double x, double y, double z) {
        vertices.add(new Vector3(x,y,z));
    }
    
    public void addFace(int v1, int v2, int v3) {
        faces.add(new Triangle(v1,v2,v3));
    }
    
    public void addFace(int v1, int v2, int v3, Color c) {
        faces.add(new Triangle(v1,v2,v3, c));
    }
    
    //@Override
    public void run(){
        double[][] m = Matrix.Vector3ToMatrix(vertices);
        
        //Internal rotation
        m = Matrix.rotateX(m, rx);
        m = Matrix.rotateY(m, ry);
        m = Matrix.rotateZ(m, rz);
        
        //translation
        m = Matrix.translate(m, x, y, z);
        
        //Absolute Rotation
        m = Matrix.rotateY(m, ary);
        m = Matrix.rotateX(m, arx);
        
        finalVertices = m;
        
        //Get points
        points = Matrix.matrixToPoints(m);
    }

    public double[][] getFinalVertices() {
        return finalVertices;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
    
    
}
