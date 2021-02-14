import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class BlockBreaker {
    private int width;
    private int height;
    private ArrayList<Brick> bricks = new ArrayList<>();
    private Ball ball;
    private Paddle paddle;
    private boolean isGameOver = false;
    public BlockBreaker(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public ArrayList<Brick> getBricks() {
        return bricks;
    }
    public Ball getBall() {
        return ball;
    }
    public void setBall(Ball ball) {
        this.ball = ball;
    }
    public Paddle getPaddle() {
        return paddle;
    }
    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }
    void draw(Canvas canvas) {
        drawBorders(canvas);
        for (Brick brick : bricks) {
            brick.draw(canvas);
        }
        ball.draw(canvas);
        paddle.draw(canvas);
    }
    private void drawBorders(Canvas canvas) {
        for (int i = 0; i < width + 2; i++) {
            for (int j = 0; j < height + 2; j++) {
                canvas.setPoint(i, j, '.');
            }
        }
        for (int i = 0; i < width + 2; i++) {
            canvas.setPoint(i, 0, '-');
            canvas.setPoint(i, height + 1, '-');
        }
        for (int i = 0; i < height + 2; i++) {
            canvas.setPoint(0, i, '|');
            canvas.setPoint(width + 1, i, '|');
        }
    }
    void run() throws Exception {
        Canvas canvas = new Canvas(width, height);
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();
        while (!isGameOver) {            
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    paddle.moveLeft();                    
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    paddle.moveRight();                   
                else if (event.getKeyCode() == KeyEvent.VK_SPACE)
                    ball.start();
            }           
            move();
            checkBrickCollision();
            checkPaddleCollision();           
            checkGameOver();           
            canvas.clear();
            draw(canvas);
            canvas.print();
            Thread.sleep(300);
        }
        System.out.println("End of the fight! You lose!");
    }
    public void move() {
        ball.move();
        paddle.move();
    }
    void checkBrickCollision() {
        for (Brick brick : new ArrayList<Brick>(bricks)) {
            if (ball.intersects(brick)) {
                double angle = Math.random() * 360;
                ball.setDirection(angle);
                bricks.remove(brick);
            }
        }
    }
    void checkPaddleCollision() {
        if (ball.intersects(paddle)) {
            double angle = 90 + 20 * (Math.random() - 0.5);
            ball.setDirection(angle);
        }
    }
    void checkGameOver() {
        if (ball.getY() > height && ball.getDy() > 0)
            isGameOver = true;
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public static BlockBreaker game;
    public static void main(String[] args) throws Exception {
        game = new BlockBreaker(20, 30);
        Ball ball = new Ball(10, 29, 2, 95);
        game.setBall(ball);
        Paddle paddle = new Paddle(10, 30);
        game.setPaddle(paddle);
        game.getBricks().add(new Brick(3, 3));
        game.getBricks().add(new Brick(7, 5));
        game.getBricks().add(new Brick(12, 5));
        game.getBricks().add(new Brick(16, 3));
        game.run();
    }
}