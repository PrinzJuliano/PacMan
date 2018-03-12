import processing.core.PVector;

public class Node {
    private PVector position;
    private String id;
    private String left;
    private String right;
    private String top;
    private String bottom;

    public Node(String id, PVector pos, String left, String top, String right, String bottom) {
        this.id = id;
        this.position = pos;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public PVector getPosition() {
        return position;
    }

    public void setPosition(PVector position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    @Override
    public String toString() {
        return String.format("Node: %s: L: %s; T: %s; R: %s; B: %s", getId(), getLeft(), getTop(), getRight(), getBottom());
    }

    public String getNodeIdFromDirection(Direction direction) {
        if(direction == null)
            return "-1";

        switch(direction){
            case UP:
                return getTop();
            case DOWN:
                return getBottom();
            case LEFT:
                return getLeft();
            case RIGHT:
                return getRight();
            default:
                return null;
        }
    }
}