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
public class MoveScript extends Object3DScript{
    
    int count = 0;
    final double amt = 1000;
    boolean right = true;
    
    public MoveScript(Object3D o) {
        super(o);
    }
    
    public void execute() {
        if (right) {
            obj.translate(0.001, 0,0);
        } else {
            obj.translate(-0.001,0,0);
        }
        
        if (count == amt) {
            right = !right;
            count = 0;
        }
        
        count++;
    }
}
