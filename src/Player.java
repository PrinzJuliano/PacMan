import processing.core.PGraphics;
import processing.core.PVector;
import processing.data.StringDict;

import java.util.ArrayList;

import static java.lang.Float.parseFloat;
import static processing.core.PConstants.CENTER;

class Player {

    private PVector pos;
    private int playerWidth;
    private int playerHeight;

    private Direction direction;
    private Node currentNode;
    private Node nextNode;
    private ArrayList<Direction> path;

    private PacMan main;

    public Player(PacMan main) {
        this.main = main;
        this.path = new ArrayList<>();
        this.direction = null;

        this.playerWidth = 12;
        this.playerHeight = 12;

        StringDict spawnData = main.getMap().getObjectByName(Map.NODES, "Player");
        float x = parseFloat(spawnData.get("x"));
        float y = parseFloat(spawnData.get("y"));

        currentNode = main.getMap().getNodeById(
                spawnData.get("name")
        );

        this.pos = new PVector(x, y);
    }

    public void update() {
        if (direction != null) {
            if (currentNode.getNodeIdFromDirection(direction).equalsIgnoreCase("-1")) {
                direction = null;
            } else {
                if (nextNode != null) {

                    int x = 0;
                    int y = 0;

                    switch (direction) {
                        case UP:
                            y = -1;
                            break;
                        case DOWN:
                            y = 1;
                            break;
                        case LEFT:
                            x = -1;
                            break;
                        case RIGHT:
                            x = 1;
                            break;
                    }

                    pos.add(x, y);
                }

                nextNode = main.getMap().getNextNode(currentNode, direction);

                if (nextNode != null && pos.dist(nextNode.getPosition()) < 2) {
                    pos = nextNode.getPosition().copy();
                    currentNode = nextNode;
                    if (path.size() > 0) {
                        direction = path.get(0);
                        this.shiftLeft();
                    } else {
                        nextNode = null;
                    }
                }
            }


            if (currentNode.getNodeIdFromDirection(direction).equalsIgnoreCase("-1")) {
                path = new ArrayList<>();
                direction = null;
            }
        }

    }

    private void shiftLeft() {
        for (int i = 0; i < path.size() - 1; i++) {
            if (i + 1 < path.size()) {
                path.set(i, path.get(i + 1));
            }
        }
    }

    private void insertIntoPath(Direction d) {
        ArrayList<Direction> newPath = new ArrayList<>();
        newPath.add(d);
        newPath.addAll(path);
        path = newPath;
    }


    public void draw(PGraphics gfx) {
        gfx.beginDraw();
        gfx.pushMatrix();
        gfx.ellipseMode(CENTER);


        gfx.fill(255, 255, 0);
        gfx.noStroke();
        gfx.ellipse(pos.x, pos.y, playerWidth, playerHeight);

        if (PacMan.DEBUG) {
            gfx.stroke(255, 255, 0);
            Node current = nextNode;

            if (nextNode != null) {
                gfx.line(currentNode.getPosition().x, currentNode.getPosition().y, nextNode.getPosition().x, nextNode.getPosition().y);
            }

            if (path.size() > 0) {
                for (Direction d : path) {
                    Node next = main.getMap().getNextNode(current, d);
                    if (next != null) {
                        gfx.line(current.getPosition().x, current.getPosition().y, next.getPosition().x, next.getPosition().y);
                        current = next;
                    } else {
                        break;
                    }
                }
            }
        }

        gfx.popMatrix();

        gfx.endDraw();
    }

    public void setDirection(Direction nextDir) {
        if (direction == null) {
            direction = nextDir;
        } else {
            if (nextDir == direction)
                return;

            if (Direction.isOpposite(direction, nextDir)) {
                currentNode = main.getMap().getNextNode(currentNode, direction);
                nextNode = null;
                path.clear();
                direction = nextDir;
                return;
            }

            Node next = main.getMap().getNextNode(nextNode, nextDir);
            if (next != null) {
                path.clear();
                path.add(nextDir);
                return;
            }

            next = main.getMap().getNextNode(currentNode, direction);
            if (next == null) {
                direction = null;
                path.clear();
                nextNode = null;
                return;
            }

            path = new ArrayList<>();
            while (true) {
                if (next == null) {
                    return;
                }
                if (!next.getNodeIdFromDirection(nextDir).equalsIgnoreCase("-1")) {
                    path.add(nextDir);
                    break;
                } else {

                    path.add(direction);
                    next = main.getMap().getNextNode(next, direction);
                    System.out.println("+" + direction);
                    if (next != null && next.getNodeIdFromDirection(nextDir).equalsIgnoreCase("-1") && next.getNodeIdFromDirection(direction).equalsIgnoreCase("-1")) {
                        path.add(null);
                        break;
                    }
                }
            }


        }
    }
}