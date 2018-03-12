import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import ptmx.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class PacMan extends PApplet {

    public static final boolean DEBUG = false;

    private float lastTime = 0;
    private float delta = 0;

    private Map map;
    private Player player;
    private PGraphics gfx;

    public void setup() {
        surface.setResizable(true);
        frame.setLocationRelativeTo(null);

        map = new Map(new Ptmx(this, "data/map.tmx"));

        PVector mapSize = map.getPixelPerfectSize();

        player = new Player(this);

        gfx = createGraphics((int) mapSize.x, (int) mapSize.y);
        gfx.noSmooth();
    }

    private void scaleImageUp() {
        PVector mapSize = map.getPixelPerfectSize();
        //calculate scaling factor for pixel perfect scaling
        float scaleX = width / mapSize.x;
        float scaleY = height / mapSize.y;
        float smalestScale = min(scaleX, scaleY);

        int actualWidth = (int) (mapSize.x * smalestScale);
        int actualHeight = (int) (mapSize.y * smalestScale);

        float offsetX = (width - actualWidth) / 2.0f;
        float offsetY = (height - actualHeight) / 2.0f;

        image(gfx, offsetX, offsetY, actualWidth, actualHeight);
    }

    public void keyPressed() {
        if (key == 'W' || key == 'w') {
            player.setDirection(Direction.UP);
        }
        if (key == 'A' || key == 'a') {
            player.setDirection(Direction.LEFT);
        }
        if (key == 'S' || key == 's') {
            player.setDirection(Direction.DOWN);
        }
        if (key == 'D' || key == 'd') {
            player.setDirection(Direction.RIGHT);
        }
    }

    public void draw() {
        delta = millis() - lastTime;
        background(0);

        //Rendering
        map.draw(gfx);

        player.update();
        player.draw(gfx);

        //Scaling
        scaleImageUp();
        lastTime = millis();


    }

    public Map getMap() {
        return map;
    }

    public void settings() {
        size(448, 496, P2D);
    }

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"PacMan"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }

    public float getDelta() {
        return delta;
    }
}
