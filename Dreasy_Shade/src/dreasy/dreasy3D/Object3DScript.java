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
public abstract class Object3DScript {
    protected Object3D obj;
    
    public Object3DScript(Object3D o) {
        obj = o;
    }
    
    public abstract void execute();
}
