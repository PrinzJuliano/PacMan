public class Path {
    private Node start;
    private Node end;

    public Path(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }
}
