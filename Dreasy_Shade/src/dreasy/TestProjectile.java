/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreasy;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author tdog6
 */
public class TestProjectile extends Canvas{
    
    
    public TestProjectile() {
        loop = false;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        reset(g);
        translate(getWidth()/2, getHeight()/2);
        
        Polygon p = new Polygon();
        p.addPoint(10, 10);
        p.addPoint(10, 50);
        p.addPoint(50, 10);
        
        g.fillPolygon(p);
    }
    
}
