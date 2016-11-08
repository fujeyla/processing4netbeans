import processing.core.*;

public class ${sketchclassname} extends PApplet {


    private PImage p3Img;
    
    @Override
    public void settings() {
        size(256, 256);
    }

    @Override
    public void setup() {
        p3Img = loadImage("data/processing3-logo.png");
        fill(120, 50, 240);
    }

    @Override
    public void draw() {
        image(p3Img, 0, 0);
        ellipse(width / 2, height / 2, second(), second());
    }


    public static void main(String[] args) {
        PApplet.main("${sketchclassname}");
    }

}