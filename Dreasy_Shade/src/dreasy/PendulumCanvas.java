/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreasy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PendulumCanvas extends Canvas{
    
    final double aMax = PI/2;
    final double len = 100, len2 = 50, len3 = 25;
    final double gravity = 9.8;
    BufferedImage trail = new BufferedImage(400,400, BufferedImage.TYPE_INT_RGB);
    
    public PendulumCanvas() {
        isLooped();
    }
    
    @Override
    protected void paintComponent(Graphics g){
        reset(g);
        g.drawImage(trail, 0, 0, null);
        g.setColor(Color.BLUE);
        translate(200,25);
        
        double omega = Math.sqrt(len/gravity);
        double a = aMax * Math.cos(omega * System.currentTimeMillis()/1000);
        
        rotate(a);
        line(g,0,0,0,len);
        translate(0,(int)len);
        circle(g,0,0,10);
        rotate(-a);
        
        omega = Math.sqrt(len2/gravity);
        a = aMax * Math.cos(omega * System.currentTimeMillis()/1000);
        rotate(a);
        line(g,0,0,0,len2);
        translate(0,(int)len2);
        fillCircle(g,0,0,10);
        rotate(-a);
        
        omega = Math.sqrt(len3/gravity);
        a = aMax * Math.cos(omega * System.currentTimeMillis()/1000);
        rotate(a);
        line(g,0,0,0,len3);
        translate(0,(int)len3);
        circle(g,0,0,10);
        rotate(-a);
        
        trail.setRGB((int)getAbsoluteX(), (int)getAbsoluteY(), 0xFFFFFF);
        
    }
}
