public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static boolean isOpposite(Direction one, Direction two) {
        return (one == Direction.UP && two == Direction.DOWN) ||
                (one == Direction.DOWN && two == Direction.UP) ||
                (one == Direction.LEFT && two == Direction.RIGHT) ||
                (one == Direction.RIGHT && two == Direction.LEFT);
    }

    public static Direction getOpposite(Direction dir){
        switch (dir) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
        }
        return null;
    }
}