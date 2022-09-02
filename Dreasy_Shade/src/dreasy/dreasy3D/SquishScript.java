/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreasy.dreasy3D;

/**
 *
 * @author tdog6
 */
public class SquishScript extends Object3DScript{
    
    int count = 0;
    final double scale = 0.999, amt = 1000;
    boolean right = true;
    
    public SquishScript(Object3D o) {
        super(o);
        o.scale(2, 0.5, 2);
    }
    
    public void execute() {
        if (right) {
            obj.scale(scale, 1/scale, scale);
        } else {
            obj.scale(1/scale, scale, 1/scale);
        }
        
        if (count == amt) {
            right = !right;
            count = 0;
        }
        
        count++;
    }
}
