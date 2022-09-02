/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreasy;

import java.awt.Graphics;

public class MyCanvas extends Canvas {
    @Override
    protected void paintComponent(Graphics g){
        reset(g);
        translate(200,400);
        rotate(PI);
        
        drawRandTree(g, 100);
    }
    
    void drawRandTree(Graphics g, double len) {
        if (len < 1) {
            return;
        }
        
        line(g,0,0,0, (int) len);
        translate(0,(int) len);
        
        final int maxBranches = 5;
        
        int branches = (int)(Math.random() * maxBranches-1) + 1;
        for (int i=0; i<branches; i++) {
            double a = Math.random() * (PI) - PI/2;
            double scale = Math.random() * (0.3) + 0.5;
            
            rotate(a);
            drawRandTree(g, len * scale);
            rotate(-a);
        }
        
        translate(0,(int) -len);
    }
    
    void drawTree(Graphics g, double len) {
        if (len < 1) {
            return;
        }
        line(g,0,0,0, (int) len);
        translate(0, (int) len);
        rotate(PI/4);
        drawTree(g, len*0.5);
        rotate(-PI/2);
        drawTree(g, len*0.5);
        rotate(PI/4);
        translate(0, (int) -len);
    }
}
