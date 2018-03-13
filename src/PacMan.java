import processing.core.*;

import ptmx.*;

public class PacMan extends PApplet {

    public static final boolean DEBUG = true;

    private float lastTime = 0;
    private float delta = 0;

    private static PacMan ref;
    private Map map;
    private Player player;
    private HUD hud;
    private PGraphics gameGFX, hudGFX;

    public static PacMan getRef() {
        return ref;
    }

    public void setup() {
        ref = this;
        surface.setResizable(false);
        frame.setLocationRelativeTo(null);

        map = new Map(new Ptmx(this, "data/map.tmx"));

        PVector mapSize = map.getPixelPerfectSize();

        player = new Player();
        hud = new HUD(this);

        gameGFX = createGraphics((int) mapSize.x, (int) mapSize.y);
        gameGFX.noSmooth();
        hudGFX = createGraphics(width, height);
        hudGFX.smooth();
    }

    private void scaleImageUp() {
        PVector mapSize = map.getPixelPerfectSize();
        //calculate scaling factor for pixel perfect scaling
        float scaleX = width / mapSize.x;
        float scaleY = height / mapSize.y;
        float smallestScale = min(scaleX, scaleY);

        int actualWidth = (int) (mapSize.x * smallestScale);
        int actualHeight = (int) (mapSize.y * smallestScale);

        float offsetX = (width - actualWidth) / 2.0f;
        float offsetY = (height - actualHeight) / 2.0f;

        image(gameGFX, offsetX, offsetY, actualWidth, actualHeight);
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
        map.draw(gameGFX);

        hud.draw(hudGFX);

        player.update();
        player.draw(gameGFX);

        //Scaling
        scaleImageUp();

        image(hudGFX, 0, 0);
        lastTime = millis();


    }

    public Map getMap() {
        return map;
    }

    public void settings() {
        size(448, 546, P2D);
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

    public HUD getHUD() {
        return hud;
    }
}
