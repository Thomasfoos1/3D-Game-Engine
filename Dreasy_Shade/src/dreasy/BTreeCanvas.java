/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreasy;

import java.awt.Graphics;

public class BTreeCanvas extends Canvas {
    
    final int levels = 3;
    final int branch = 75;
    
    @Override
    protected void paintComponent(Graphics g) {
        reset(g);
        translate(200,10);
        drawTree(g, 3, 3*PI/8);
    }
    
    void drawTree(Graphics g, int l, double a) {
        circle(g,0,0,10);
        
        if (l == 0) {return;}
        
        rotate(a);
        line(g,0,10,0,branch-10);
        translate(0,branch);
        rotate(-a);
        drawTree(g, l-1, a * 0.5);
        rotate(a);
        translate(0,-branch);
        rotate(-2*a);
        
        line(g,0,10,0,branch-10);
        translate(0,branch);
        rotate(a);
        drawTree(g, l-1, a * 0.5);
        rotate(-a);
        translate(0,-branch);
        rotate(a);
        
    }
}
