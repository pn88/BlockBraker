public class Paddle extends BaseObject {
    private static int[][] matrix = {
            {1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
    };
    private double speed = 1;
    private double direction = 0;
    public Paddle(double x, double y) {
        super(x, y, 3);
    }
    void move() {
        double dx = speed * direction;
        x = x + dx;

        checkBorders(radius, BlockBreaker.game.getWidth() - radius + 1, 1, BlockBreaker.game.getHeight() + 1);
    }
    void moveLeft() {
        direction = -1;
    }
    void moveRight() {
        direction = 1;
    }

    public double getSpeed() {
        return speed;
    }
    public double getDirection() {
        return direction;
    }
    @Override
    void draw(Canvas canvas) {
        canvas.drawMatrix(x - radius + 1, y, matrix, 'M');
    }
}