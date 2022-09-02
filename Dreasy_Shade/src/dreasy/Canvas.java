/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreasy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Canvas extends JPanel{
    protected double x=0, y=0, rx=0, ry=0;
    protected double angle=0;
    protected final double PI = Math.PI;
    public boolean loop = false;
    
    protected void reset(Graphics g){
        origin();
        clear(g);
    }
    
    protected void clear(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 500, 500);
        g.setColor(Color.BLACK);
    }
    
    protected void origin() {
        angle = 0;
        rx = 0;
        ry = 0;
        x = 0;
        y = 0;
    }
    
    public boolean isLooped(){
        return loop;
    }
    
    protected void loop() {
        loop = true;
    }
    
    protected void noLoop() {
        loop = false;
    }
    
    
    protected void translate(int x, int y) {
        this.x += x * Math.cos(angle) + y * Math.sin(angle);
        this.y += y * Math.cos(angle) + x * Math.sin(angle);
    }
    
    protected void translate(double x, double y) {
        translate((int) x, (int) y);
    }
    
    protected void rotate(double a) {
        this.angle += a;
        rx = x;
        ry = y;
    }
    
    protected void line(Graphics g, double x1i, double y1i, double x2i, double y2i){
        double x1 = getRelativeX(x1i, y1i);
        double y1 = getRelativeY(x1i, y1i);
        double x2 = getRelativeX(x2i, y2i);
        double y2 = getRelativeY(x2i, y2i);
        
        g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }
    
    protected void circle(Graphics g, double xi, double yi, double r) {
        double tmp = angle;
        angle = 0;
        double x1 = getRelativeX(xi - r, yi - r);
        double y1 = getRelativeY(xi - r, yi - r);
        angle = tmp;
        
        g.drawOval((int)x1, (int)y1, (int)(2*r), (int)(2*r));
    }
    
    protected void fillCircle(Graphics g, double xi, double yi, double r) {
        double tmp = angle;
        angle = 0;
        double x1 = getRelativeX(xi - r, yi - r);
        double y1 = getRelativeY(xi - r, yi - r);
        angle = tmp;
        
        g.fillOval((int)x1, (int)y1, (int)(2*r), (int)(2*r));
    }
    
    protected void rectangle(Graphics g, double xi, double yi, double l, double w) {
        double tmp = angle;
        angle = 0;
        double x1 = getRelativeX(xi, yi);
        double y1 = getRelativeY(xi, yi);
        angle = tmp;
        
        g.fillRect((int)x1, (int)y1, (int)l, (int)w);
    }
    
    private double getRelativeX(double x, double y) {
        return rx + (this.x - rx + x) * Math.cos(angle) + (this.y - ry + y) * Math.sin(angle);
    }
    private double getRelativeY(double x, double y) {
        return ry + ((this.x - rx + x) * Math.sin(angle) + (this.y - ry + y) * Math.cos(angle));
    }
    
    public double getAbsoluteX() {
        return x;
    }
    
    public double getAbsoluteY() {
        return y;
    }
}
