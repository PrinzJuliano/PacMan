import processing.core.PGraphics;
import processing.core.PVector;
import processing.data.StringDict;
import ptmx.Ptmx;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Integer.parseInt;
import static processing.core.PConstants.CENTER;

public class Map {

    public static final int GROUND = 0,
            WALLS = 1,
            COLLECTABLES = 2,
            SPAWNS = 3,
            NODES = 4;

    private Ptmx rawMap;

    private ArrayList<Path> paths;
    private ArrayList<Collectable> collectables;

    private ArrayList<Node> nodes;

    public Map(Ptmx map) {
        this.rawMap = map;
        nodes = new ArrayList<>();
        paths = new ArrayList<>();

        for (StringDict dict : map.getObjects(NODES)) {
            String type = dict.get("type");

            if (!type.equalsIgnoreCase("Node") && !type.equalsIgnoreCase("Portal")) {
                continue;
            }

            String id = dict.get("name");
            String left = dict.get("left");
            String right = dict.get("right");
            String top = dict.get("top");
            String bottom = dict.get("bottom");

            int x = parseInt(dict.get("x"));
            int y = parseInt(dict.get("y"));
            PVector pos = new PVector(x, y);

            nodes.add(new Node(id, pos, left, top, right, bottom, type.equalsIgnoreCase("Portal")));
        }

        collectables = new ArrayList<>();
        for (StringDict dict : map.getObjects(COLLECTABLES)) {
            collectables.add(new Collectable(dict));
        }

        if (PacMan.DEBUG) {
            System.out.println("Node Count: " + nodes.size());
        }

        for (Node n : nodes) {
            if (PacMan.DEBUG) {
                System.out.println(n);
            }

            if (!n.getLeft().equalsIgnoreCase("-1")) {
                if (findPathByStartId(n.getLeft()) == null) {
                    paths.add(new Path(getNodeById(n.getLeft()), n));
                    if (PacMan.DEBUG) {
                        System.out.println("Expected Node:L: " + getNodeById(n.getLeft()));
                        System.out.println("New Path: " + paths.get(paths.size() - 1));
                    }
                }
            }
            if (!n.getTop().equalsIgnoreCase("-1")) {
                if (findPathByStartId(n.getTop()) == null) {
                    paths.add(new Path(getNodeById(n.getTop()), n));
                    if (PacMan.DEBUG) {
                        System.out.println("Expected Node:T: " + getNodeById(n.getTop()));
                        System.out.println("New Path: " + paths.get(paths.size() - 1));
                    }
                }
            }
            if (!n.getRight().equalsIgnoreCase("-1")) {
                if (findPath(n.getId(), n.getRight()) == null) {
                    paths.add(new Path(n, getNodeById(n.getRight())));
                    if (PacMan.DEBUG) {
                        System.out.println("Expected Node:R: " + getNodeById(n.getRight()));
                        System.out.println("New Path: " + paths.get(paths.size() - 1));
                    }
                }
            }
            if (!n.getBottom().equalsIgnoreCase("-1")) {
                if (findPath(n.getId(), n.getBottom()) == null) {
                    paths.add(new Path(n, getNodeById(n.getBottom())));
                    if (PacMan.DEBUG) {
                        System.out.println("Expected Node:B: " + getNodeById(n.getBottom()));
                        System.out.println("New Path: " + paths.get(paths.size() - 1));
                    }
                }
            }
        }
    }

    public Node getNextNode(Node reference, Direction dir) {
        if (dir == null || reference == null)
            return null;
        switch (dir) {
            case UP:
                if (!Objects.equals(reference.getTop(), "-1")) {
                    return getNodeById(reference.getTop());
                }
                break;
            case LEFT:
                if (!Objects.equals(reference.getLeft(), "-1")) {
                    return getNodeById(reference.getLeft());
                }
                break;
            case RIGHT:
                if (!Objects.equals(reference.getRight(), "-1")) {
                    return getNodeById(reference.getRight());
                }
                break;
            case DOWN:
                if (!Objects.equals(reference.getBottom(), "-1")) {
                    return getNodeById(reference.getBottom());
                }
                break;
            default:
                return null;
        }
        return null;
    }

    public Node getNodeById(String id) {
        for (Node n : nodes) {
            if (n.getId().equalsIgnoreCase(id)) {
                return n;
            }
        }
        return null;
    }

    public Path findPath(String startId, String endId) {
        for (Path p : paths) {
            if (p.getStart().getId().equalsIgnoreCase(startId) &&
                    p.getEnd().getId().equalsIgnoreCase(endId) ||
                    p.getEnd().getId().equalsIgnoreCase(startId) && p.getStart().getId().equalsIgnoreCase(endId)
                    ) {
                return p;
            }
        }
        return null;
    }

    public Path findPathByStartId(String id) {
        for (Path p : paths) {
            if (p.getStart().getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    public Ptmx getRawMap() {
        return rawMap;
    }

    public StringDict[] getDataFromLayer(int layer) {
        return this.rawMap.getObjects(layer);
    }

    public StringDict[] getObjectsByType(int layer, String type) {
        StringDict[] allObjs = this.getDataFromLayer(layer);
        ArrayList<StringDict> filteredResults = new ArrayList<StringDict>();

        for (StringDict dict : allObjs) {
            if (dict.hasKey("type") && dict.get("type").equalsIgnoreCase(type)) {
                filteredResults.add(dict);
            }
        }

        return (StringDict[]) filteredResults.toArray();
    }

    public StringDict getObjectByName(int layer, String name) {
        StringDict[] allObjs = this.getDataFromLayer(layer);
        for (StringDict dict : allObjs) {
            if (dict.hasKey("name") && dict.get("name").equalsIgnoreCase(name)) {
                return dict;
            }
        }
        return null;
    }

    public PVector getPixelPerfectSize() {
        PVector mapSize = rawMap.getMapSize().copy();
        mapSize.x *= rawMap.getTileSize().x;
        mapSize.y *= rawMap.getTileSize().y;

        return mapSize;
    }

    public PVector tileCoordinateToPosition(int x, int y) {
        PVector pos = new PVector(x, y);
        pos.x *= rawMap.getTileSize().x;
        pos.y *= rawMap.getTileSize().y;
        return pos;
    }

    public PVector positionToTileCoordinate(float x, float y) {
        PVector tileCoord = new PVector();
        tileCoord.x = (int) (x / rawMap.getTileSize().x);
        tileCoord.y = (int) (y / rawMap.getTileSize().y);
        return tileCoord;
    }

    public PVector positionToTileCoordinate(PVector pos) {
        return positionToTileCoordinate(pos.x, pos.y);
    }


    public void draw(PGraphics gfx) {
        gfx.beginDraw();
        gfx.background(0);
        gfx.endDraw();

        rawMap.draw(gfx, 0, 0);

        gfx.beginDraw();

        for (Collectable c : collectables)
            c.draw(gfx);

        gfx.endDraw();

        if (PacMan.DEBUG) {
            gfx.beginDraw();
            gfx.stroke(255, 100);
            for (Path p : paths) {
                PVector start = p.getStart().getPosition();
                PVector end = p.getEnd().getPosition();
                gfx.line(start.x, start.y, end.x, end.y);
            }

            for (Node n : nodes) {
                PVector pos = n.getPosition();
                gfx.rectMode(CENTER);
                gfx.fill(255, 100);
                gfx.noStroke();
                gfx.rect(pos.x, pos.y, 4, 4);
            }

            gfx.endDraw();


        }
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Path> getPaths() {
        return paths;
    }

    public Collectable getClosestCollectable(PVector pos) {
        float bestDist = collectables.get(0).position.dist(pos);
        float dist;
        int bestID = 0;
        for (int i = collectables.size() - 1; i >= 0; i--) {
            if((dist = collectables.get(i).position.dist(pos)) < bestDist){
                bestDist = dist;
                bestID = i;
            }
        }
        return collectables.get(bestID);
    }

    public ArrayList<Collectable> getCollectables() {
        return collectables;
    }
}