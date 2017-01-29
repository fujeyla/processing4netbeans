

import processing.core.*;

/**
 * This is the template of your java Processing sketch
 * You can use the Processing API now. 
 * Don't forget that in java, Processing color type doesn't exist, 
 * use int instead
 * e.g. int red = color(255,0,0);
 * @author Jerome
 */

// created by ${user}
public class MySketch extends PApplet {

    private PImage p3Img;
    
    @Override
    public void settings() {
        size(256, 256);
        p3Img = loadImage("data/processing3-logo.png");
    }

    @Override
    public void setup() {
        fill(120, 50, 240);
    }

    @Override
    public void draw() {
        image(p3Img, 0, 0);
        ellipse(width / 2, height / 2, second(), second());
    }

    
    
    public static void main(String[] args) {
        PApplet.main("MySketch");
    }
}
