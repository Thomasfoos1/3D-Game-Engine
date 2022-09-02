/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreasy.dreasy3D;

class Vector3 extends Vector2{
    private double z;
    
    public Vector3(double x, double y, double z) {
        super(x,y);
        this.z = z;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
    
    public double magnitude() {
        return Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2) + Math.pow(this.getZ(), 2));
    }
    
    public static double dotProduct(Vector3 v1, Vector3 v2) {
        return (v1.getX() * v2.getX()) + (v1.getY() * v2.getY()) + (v1.getZ() * v2.getZ());
    }
    
    public static Vector3 difference(Vector3 a, Vector3 b) {
        return new Vector3(a.getX() - b.getX(), a.getY() - b.getY(), a.getZ() - b.getZ());
    }
    
    public static Vector3 crossProduct(Vector3 a, Vector3 b) {
        double x = a.getY() * b.getZ() - a.getZ() * b.getY();
        double y = a.getZ() * b.getX() - a.getX() * b.getZ();
        double z = a.getX() * b.getY() - a.getY() * b.getX();
        return new Vector3(x,y,z);
    }
    
    public static Vector3 normalize(Vector3 v) {
        double m = v.magnitude();
        return new Vector3(v.getX()/m, v.getY()/m, v.getZ()/m);
    }
    
    public static double angle(Vector3 a, Vector3 b) {
        double t = dotProduct(a,b)/(a.magnitude() * b.magnitude());
        return (Math.acos(t));
    }
    
    public static Vector3 average(Vector3 a, Vector3 b, Vector3 c) {
        double x = (a.getX() + b.getX() + c.getX())/3;
        double y = (a.getY() + b.getY() + c.getY())/3;
        double z = (a.getZ() + b.getZ() + c.getZ())/3;
        return new Vector3(x,y,z);
    }
}
