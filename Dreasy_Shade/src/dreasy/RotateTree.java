/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreasy;

import java.awt.Graphics;

public class RotateTree extends Canvas{
    
    final double startWidth = 10;
    final double widthScale = 0.75;
    final double startLen = 200;
    final double lenScale = 0.5;
    final double numIterations = 9;
    double angle = 2*PI;
    double angleRate = 0.001;
    
    public RotateTree() {
        loop = true;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        reset(g);
        translate(250, 500);
        rotate(PI);
        
        drawTree(g, startLen, startWidth, numIterations);
        
        angle = (angle+angleRate) % (2*PI);
    }
    
    void drawTree(Graphics g, double l, double w, double i) {
        if (i == 0) {return;}
        //rectangle(g, -w/2, 0, w, l);
        line(g,0,0,0,l);
        translate(0,l);
        rotate(angle);
        drawTree(g, l * lenScale, w * widthScale, i-1);
        rotate(-2*angle);
        drawTree(g, l * lenScale, w * widthScale, i-1);
        rotate(angle);
        translate(0,-l);
    }
}
