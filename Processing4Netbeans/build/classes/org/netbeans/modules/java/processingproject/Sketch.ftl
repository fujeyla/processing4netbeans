import processing.core.*;

/**
 * This is the template of your java Processing sketch
 * You can use the Processing API now. 
 *
 * Don't forget that in java, Processing color type doesn't exist, 
 * use int instead
 * e.g. int red = color(255,0,0);
 */

public class ${name} extends PApplet {

    private PImage p3Img;
    
    @Override
    public void settings() {
        //generated size: replace this settings with yours
        size(256, 256);
    }

    @Override
    public void setup() {
        //replace this setup below with yours
        p3Img = loadImage("data/processing3-logo.png");
        fill(120, 50, 240);
    }

    @Override
    public void draw() {
        //replace this rendering below with yours 
        image(p3Img, 0, 0);
        ellipse(width / 2, height / 2, second(), second());
    }


    public static void main(String[] args) {
        PApplet.main("${name}");
    }

}
