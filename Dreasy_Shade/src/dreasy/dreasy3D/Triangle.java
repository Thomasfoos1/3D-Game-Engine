/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreasy.dreasy3D;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author tdog6
 */
public class Triangle {
    int v1, v2, v3;
    Color color;
    
    public Triangle(int v1, int v2, int v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        color = Color.WHITE;
    }
    
    public Triangle(int v1, int v2, int v3, Color c) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        color = c;
    }
    
    public Color shadeTriangle(double[][] vertices, Vector3 lightSource) {
        
        Vector3 a = new Vector3(vertices[0][v1],vertices[1][v1],vertices[2][v1]);
        Vector3 b = new Vector3(vertices[0][v2],vertices[1][v2],vertices[2][v2]);
        Vector3 c = new Vector3(vertices[0][v3],vertices[1][v3],vertices[2][v3]);
        
        Vector3 ac = Vector3.difference(a, c);
        Vector3 bc = Vector3.difference(b, c);
        
        Vector3 normal = Vector3.normalize(Vector3.crossProduct(ac, bc));
        double s = Math.cos(0.5 * Vector3.angle(normal, lightSource));
        return new Color((int)(color.getRed() * s), (int)(color.getGreen() * s), (int)(color.getBlue() * s));
    }
    
    public double distance(double[][] v) {
        Vector3 a = new Vector3(v[0][v1], v[1][v1], v[2][v1]);    
        Vector3 b = new Vector3(v[0][v2], v[1][v2], v[2][v2]);    
        Vector3 c = new Vector3(v[0][v3], v[1][v3], v[2][v3]);    
            
        Vector3 avg = Vector3.average(a, b, c);
        return Math.sqrt(Math.pow(avg.getX(),2) + Math.pow(avg.getY(),2) + Math.pow(avg.getZ(),2 ));
    }
}
