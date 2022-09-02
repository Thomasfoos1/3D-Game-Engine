/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreasy;

import java.awt.Graphics;

/**
 *
 * @author tdog6
 */
public class TestCanvas extends Canvas {
    @Override
    protected void paintComponent(Graphics g) {
        g.draw3DRect (25, 10, 50, 75, true);
        g.draw3DRect (25, 110, 50, 75, false);
        g.fill3DRect (100, 10, 50, 75, true);
        g.fill3DRect (100, 110, 50, 75, false);
    }
}
