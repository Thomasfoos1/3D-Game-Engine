/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreasy.dreasy3D;

import java.awt.Color;

/**
 *
 * @author tdog6
 */
public class Cube3D extends Object3D{
    
    public Cube3D() {
        super();
    }
    
    public Cube3D(int l) {
        super(l);
    }
    
    public void addFace(int v1, int v2, int v3, Color c, int side) {
        faces.add(new CubeFace(v1,v2,v3,c,side));
    }
}
