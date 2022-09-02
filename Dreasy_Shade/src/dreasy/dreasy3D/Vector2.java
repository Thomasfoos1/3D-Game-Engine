/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreasy.dreasy3D;

public class Vector2 {
    private double x, y;
    private boolean flip;
    
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
        flip = false;
    }
    
    public Vector2(double x, double y, Boolean flip) {
        this.x = x;
        this.y = y;
        this.flip = flip;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public boolean isFlipped() {
        return flip;
    }
    
}
