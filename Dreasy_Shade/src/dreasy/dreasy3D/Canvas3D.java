/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreasy.dreasy3D;

import dreasy.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author tdog6
 */
public class Canvas3D extends Canvas implements KeyListener, MouseMotionListener, MouseListener{
    
    private BufferedImage img;
    private Object3D refCube;

    //Position
    private double ary = 0, arx=0, scale = 200,
                   x3D = 0, y3D = 0, z3D = 0;
    private Point mousePt = MouseInfo.getPointerInfo().getLocation();
    
    //Physics
    private double velocity, initVelocity = 5;
    private long oldTime = 0;
    private boolean jump = false, startJump = false;
    
    ArrayList<Object3D> objects = new ArrayList<>();
    ArrayList<Object3DScript> scripts = new ArrayList();
    
    //lighting
    Vector3 light = new Vector3(0,-0.5,0.86);
    Color background = new Color(109, 188, 252);
    Color groundColor = new Color(58, 156, 47);
    Color blockColor = Color.WHITE;
    
    //Place blocks
    private boolean mouse = false;
    
    public Canvas3D() {
        loop = true;
        
        drawGround();
        refCube = cubeFactory(0,-1,0, Color.WHITE);
        objects.add(refCube);
        objects.add(cubeFactory(2,-1,0, Color.BLUE));
        objects.add(cubeFactory(4,-1,0, Color.RED));
        objects.add(cubeFactory(-2,-1,0, Color.GREEN));
        objects.add(triangleFactory(2,-1,4, Color.MAGENTA));
    }
    
    public void drawGround() {
        for (int i=0; i<48; i++)  {
            for (int j=0; j<48; j++) {
                Cube3D o = new Cube3D(1);
                o.addVertex(0, 0, 0);
                o.addVertex(0, 0, 1);
                o.addVertex(1, 0, 0);
                o.addVertex(1, 0, 1);
                
                o.addFace(0, 1, 2, groundColor);
                o.addFace(3, 2, 1, groundColor);
                o.translate(i - 9, -1, j - 9);
                objects.add(o);
            }
        }
    
    }
    
    
    Object3D triangleFactory(double x, double y, double z, Color c) {
        Object3D o = new Object3D();
        o.addVertex(-0.5, 0, -0.43);
        o.addVertex(0.5, 0, -0.43);
        o.addVertex(0, 0, 0.43);
        o.addVertex(0, 1, 0.1);
        
        o.addFace(2, 1, 0, c);
        o.addFace(0, 3, 1, c);
        o.addFace(2, 3, 0, c);
        o.addFace(1, 3, 2, c);
        
        o.translate(x, y, z);
        scripts.add(new RotateScript(o));
        return o;
    }
    
    Object3D cubeFactory(double x, double y, double z, Color c) {
        Cube3D o = new Cube3D();
        o.addVertex(0, 0, 0);
        o.addVertex(1, 0, 0);
        o.addVertex(0, 0, 1);
        o.addVertex(1, 0, 1);
        o.addVertex(0, 1, 0);
        o.addVertex(1, 1, 0);
        o.addVertex(0, 1, 1);
        o.addVertex(1, 1, 1);
        o.addFace(0, 1, 2, c, CubeFace.BOTTOM);
        o.addFace(3, 2, 1, c, CubeFace.BOTTOM);
        
        o.addFace(6, 5, 4, c, CubeFace.TOP);
        o.addFace(5, 6, 7, c, CubeFace.TOP);
        
        o.addFace(1, 0, 5, c, CubeFace.NORTH);
        o.addFace(0, 4, 5, c, CubeFace.NORTH);
        
        o.addFace(1, 5, 3, c, CubeFace.EAST);
        o.addFace(3, 5, 7, c, CubeFace.EAST);
        
        o.addFace(4, 0, 2, c, CubeFace.WEST);
        o.addFace(2, 6, 4, c, CubeFace.WEST);
        
        o.addFace(2, 3, 7, c, CubeFace.SOUTH);
        o.addFace(7, 6, 2, c, CubeFace.SOUTH);
        
        o.translateAbsolute(x, y, -z);
        o.absoluteRotate(-ary, -arx);
        
        return o;
    }

    public double getX3D() {
        return x3D;
    }

    public double getY3D() {
        return y3D;
    }

    public double getZ3D() {
        return z3D;
    }
    
    public double getRotY() {
        return ary;
    }
    
    public double getScale() {
        return scale;
    }
    
    private void render(Graphics g) {
        scale = getHeight()/2;
        reset(g);
        g.setColor(background);
        rectangle(g, 0,0,this.getWidth(),this.getHeight());
        translate(this.getWidth()/2, this.getHeight()/2);
        rotate(PI);
        
        if (jump) {
            long time = System.nanoTime() - oldTime;
            oldTime += time;
            double sec = (double)time/Math.pow(10,9);
            double dy = velocity * sec - 0.5 * 20 * Math.pow(sec, 2);
            
            y3D+= dy;
            velocity = dy/sec;
            movePlayer(0,-dy,0);
            startJump = false;
        }
        if (y3D < 0 && jump) {
            movePlayer(0,y3D,0);
            y3D = 0;
            jump = false;
        }
        
        Collections.sort(objects, new Comparator<Object3D>() {
                @Override
                public int compare(Object3D l, Object3D r) {
                    // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                    if (l.getLayer() < r.getLayer()) {
                        return 1;
                    } else if (l.getLayer() > r.getLayer()) {
                        return -1;
                    }
                    
                    double a = new Vector3(l.getX(), l.getY(), l.getZ()).magnitude();
                    double b = new Vector3(r.getX(), r.getY(), r.getZ()).magnitude();
                    
                    return a > b ? -1 : a < b ? 1 : 0;
                }
        });
        
        //Var for placing new blocks
        Vector3 newPos = null;
        int newSide = -1;
        
        for (Object3D o : objects) {
            
            Vector2[] pts = o.getPoints();
            double[][] vert = o.getFinalVertices();
            
            Collections.sort(o.faces, new Comparator<Triangle>() {
                @Override
                public int compare(Triangle lhs, Triangle rhs) {
                    // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                    return lhs.distance(vert) > rhs.distance(vert) ? -1 : (lhs.distance(vert) < rhs.distance(vert)) ? 1 : 0;
                }
            });
            
            
            for (Triangle f : o.getFaces()) {
                Vector2 p1 = pts[f.v1];
                Vector2 p2 = pts[f.v2];
                Vector2 p3 = pts[f.v3];
                
                if (drawPoly(p1,p2,p3)) {
                    Polygon p = new Polygon();
                    int neg = (p1.isFlipped()) ? -1 : 1;
                    p.addPoint((int)(p1.getX() * scale * neg + this.getAbsoluteX()), (int)(p1.getY() * scale * neg + this.getAbsoluteY()));
                    neg = (p2.isFlipped()) ? -1 : 1;
                    p.addPoint((int)(p2.getX() * scale * neg + this.getAbsoluteX()), (int)(p2.getY() * scale * neg + this.getAbsoluteY()));
                    neg = (p3.isFlipped()) ? -1 : 1;
                    p.addPoint((int)(p3.getX() * scale * neg + this.getAbsoluteX()), (int)(p3.getY() * scale * neg + this.getAbsoluteY()));
                    g.setColor(f.shadeTriangle(vert, light));
                    g.fillPolygon(p);
                    
                    if (mouse) {
                        if (p.contains(new Point(getWidth()/2, getHeight()/2))) {
                            newPos = new Vector3(o.getX(), o.getY(), o.getZ());
                            
                            if (f instanceof CubeFace) {
                                CubeFace cf = (CubeFace) f;
                                newSide = cf.getSide();
                            }
                        }
                    }
                }
            }
        }
        
        //Place block
        if (newPos != null && mouse) {
            switch(newSide) {
                case CubeFace.TOP:
                    objects.add(cubeFactory(newPos.getX(), newPos.getY() + 1, -newPos.getZ(), blockColor));
                    break;
                case CubeFace.BOTTOM:
                    objects.add(cubeFactory(newPos.getX(), newPos.getY() - 1, -newPos.getZ(), blockColor));
                    break;
                case CubeFace.NORTH:
                    objects.add(cubeFactory(newPos.getX(), newPos.getY(), -newPos.getZ() + 1, blockColor));
                    break;
                case CubeFace.SOUTH:
                    objects.add(cubeFactory(newPos.getX(), newPos.getY(), -newPos.getZ() - 1, blockColor));
                    break;
                case CubeFace.EAST:
                    objects.add(cubeFactory(newPos.getX() + 1, newPos.getY(), -newPos.getZ(), blockColor));
                    break;
                case CubeFace.WEST:
                    objects.add(cubeFactory(newPos.getX() - 1, newPos.getY(), -newPos.getZ(), blockColor));
                    break;
            }
            mouse = false;
        }
        
        
        
        g.setColor(Color.WHITE);
        circle(g,0,0,4);
        
        executeScripts();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if (img == null) {
             img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }
        render(img.getGraphics());
        g.drawImage(img, 0, 0, null);
    }
    
    private boolean drawPoly(Vector2 a, Vector2 b, Vector2 c) {
        return (pointValid(a) && pointValid(b) && pointValid(c));
    }
    
    private boolean pointValid(Vector2 p) {
        int w = this.getWidth();
        int h = this.getHeight();
        double x = p.getX() * scale;
        double y = p.getY() * scale;
        
        return (!p.isFlipped() || (x < -w/2 || x > w/2) || (y < -h/2 || y > h/2));
    }
    
    @Override
    public void keyPressed(KeyEvent e) {  
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                movePlayer(0,0,-0.1);
                break;
            case KeyEvent.VK_S:
                movePlayer(0,0,0.1);
                break;
            case KeyEvent.VK_A:
                movePlayer(-0.1,0,0);
                break;
            case KeyEvent.VK_D:
                movePlayer(0.1,0,0);
                break;
            case KeyEvent.VK_Q:
                movePlayer(0,-0.1,0);
                break;
            case KeyEvent.VK_E:
                movePlayer(0,0.1,0);
                break;
                
            case KeyEvent.VK_LEFT:
                rotatePlayer(-0.1,0);
                break;
            case KeyEvent.VK_RIGHT:
                rotatePlayer(0.1,0);
                break;
            case KeyEvent.VK_SPACE:
                jump = true;
                oldTime = System.nanoTime();
                velocity = initVelocity;
                startJump = true;
                break;
            case KeyEvent.VK_ESCAPE:
//                JFileChooser fc = new JFileChooser("C:/");
//                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
//                    try {
//                        Object3D obj = new Object3D(fc.getSelectedFile());
//                        obj.translate(-10, 0, -10);
//                        objects.add(obj);
//                        
//                    } catch (FileNotFoundException ex) {
//                        ex.printStackTrace();
//                    }
//                }
                
                int r,g,b;
                String s = JOptionPane.showInputDialog(this,"Enter red");
                r = Integer.parseInt(s);
                s = JOptionPane.showInputDialog(this,"Enter green");
                g = Integer.parseInt(s);
                s = JOptionPane.showInputDialog(this,"Enter blue");
                b = Integer.parseInt(s);
                blockColor = new Color(r,g,b);
        }
    }
    
    public void rotatePlayer(double ay, double ax) {
        for (Object3D o : objects) {
            o.absoluteRotate(-ay, -ax);
        }
        ary += ay;
        arx += ax;
    }
    
    public void movePlayer(double x, double y, double z) {
        for (Object3D o : objects) {
            o.translate(x, y, -z);
        }
        x3D += x;
        z3D += z;
    }
    
    public void executeScripts() {
        for (Object3DScript s : scripts) {
            s.execute();
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {  
        
    }  
    @Override
    public void keyTyped(KeyEvent e) {  
        
    }  
    
    @Override
    public void mouseMoved(MouseEvent e) {
        Point newPt = e.getPoint();
        double dx = newPt.x - mousePt.x;
        double dy = newPt.y - mousePt.y;
        rotatePlayer(dx/75, -dy/75);
        
        mousePt = newPt;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        
    }
    
    public void mouseEntered(MouseEvent e){
        
    }
    public void mouseExited(MouseEvent e){
        
    }
    public void mouseReleased(MouseEvent e){
        
    }
    public void mousePressed(MouseEvent e){
        
    }
    public void mouseClicked(MouseEvent e){
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouse = true;
        }
    }

}
