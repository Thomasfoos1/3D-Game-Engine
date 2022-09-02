/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreasy;

import dreasy.dreasy3D.BoxCanvas;
import dreasy.dreasy3D.Canvas3D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Dreasy {
    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Canvas3D c = new Canvas3D();
        frame.setContentPane(c);
        frame.addKeyListener(c);
        frame.addMouseMotionListener(c);
        frame.addMouseListener(c);
        frame.setSize(1920,1080);
        if (c.isLooped()) {
            while (true) {
                c.repaint();
            }
        }
    }
}
