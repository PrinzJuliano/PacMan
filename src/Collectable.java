import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.StringDict;

import static java.lang.Integer.parseInt;

public class Collectable {

    PImage icon;
    PVector position;
    PVector size;
    int points;
    boolean visible;

    public Collectable(PVector position, int points, boolean visible, String imagePath) {
        this.position = position;
        this.points = points;
        this.visible = visible;
        this.icon = PacMan.getRef().loadImage(imagePath);
    }

    public Collectable(StringDict dict) {
        //Parse data
        //2 Types: CollectablePoint, CollectableOrb
        if (dict.hasKey("type") && dict.get("type").equalsIgnoreCase("CollectablePoint")) {
            this.icon = PacMan.getRef().loadImage("Objects/Point.png");
            this.points = 10;
            this.visible = true;
        } else {
            this.visible = false;
        }

        int x = parseInt(dict.get("x"));
        int y = parseInt(dict.get("y"));

        int w = parseInt(dict.get("width"));
        int h = parseInt(dict.get("height"));

        this.size = new PVector(w, h);
        this.position = new PVector(x, y);
        this.position = this.getCenterPosition();
    }

    public void draw(PGraphics gfx) {
        if (visible) {
            gfx.pushMatrix();

            gfx.imageMode(PConstants.CENTER);
            gfx.image(this.icon, this.position.x, this.position.y, size.x, size.y);

            gfx.popMatrix();
        }
    }

    public PVector getCenterPosition() {
        PVector p = this.position.copy();
        p.x += size.x / 2;
        p.y -= size.y / 2;
        return p;
    }

    public void pickUp(){
        this.visible = false;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
