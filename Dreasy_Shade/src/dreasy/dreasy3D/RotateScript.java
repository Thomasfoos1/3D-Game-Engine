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
public class RotateScript extends Object3DScript {
    
    public RotateScript(Object3D o) {
        super(o);
    }
    
    public void execute() {
        obj.rotateY(0.001);
    }
}
