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
public class CubeFace extends Triangle {
    
    public final static int NORTH = 1, SOUTH = 2, EAST = 3, WEST = 4, TOP =5, BOTTOM = 6;
    private final int side;
    
    public CubeFace(int v1, int v2, int v3, int side) {
        super(v1,v2,v3);
        this.side = side;
    }
    
    public CubeFace(int v1, int v2, int v3, Color c, int side) {
        super(v1,v2,v3,c);
        this.side = side;
    }
    
    public int getSide() {
        return side;
    }
}
