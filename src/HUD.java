import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;

import java.util.stream.IntStream;

public class HUD {

    int level;
    int score;
    int highscore;
    int lives;
    //TODO add bonuses

    PFont upheavtt;
    PImage heart;

    PacMan main;

    public HUD(PacMan main) {
        this.level = 1;
        this.lives = 3;
        this.main = main;
        this.upheavtt = main.createFont("data/fonts/upheavtt.ttf", 24);

        this.heart = main.loadImage("Objects/Heart.png");
    }

    public void draw(PGraphics gfx) {
        gfx.beginDraw();

        gfx.pushMatrix();
        gfx.pushStyle();
        gfx.fill(0);
        gfx.rect(0, 0, PacMan.getRef().width, 20);

        gfx.stroke(255);
        gfx.fill(255);

        gfx.textFont(upheavtt, 36);
        gfx.textAlign(PConstants.LEFT);
        gfx.text("UP " + level, 10, 20);
        gfx.textAlign(PConstants.CENTER);
        gfx.text(score, main.width / 2, 20);
        gfx.textAlign(PConstants.RIGHT);
        gfx.text(highscore, main.width - 10, 20);

        for (int i = 0; i < lives; i++)
            gfx.image(heart, 10 + i * 21, main.height - 22, 20, 20);

        gfx.popStyle();
        gfx.popMatrix();

        gfx.endDraw();
    }

    public void addToScore(int points) {
        this.score += points;
        if (highscore < score)
            highscore = score;
    }
}
