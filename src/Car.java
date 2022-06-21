import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

public class Car {
    private Image image;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    private static int y = 0;
    public double speed = 60+(level-1)*40;
    public static int level=1;

    public Car() {
        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;
    }

    public void setImage(Image i) {
        image = i;

        width = i.getWidth();
        height = i.getHeight();
    }

    public void plusSpeed() {
        velocityY = 2 * speed;
    }


    public void plusSpeed2() {
        velocityY =2.5* speed;
    }




    ;

    public void setSpeed() {
        velocityY = speed;
    }


    public void setBackground(double yP) {
        this.setVelocity(0, this.speed*1.1);
        this.setPosition(0, yP);


    }

    public double getY() {
        return positionY;
    }


    public void spawnCar() {
        double px = 265 + 355 * Math.random();
        double py = -y * 100 - 1000 * Math.random();
        if (y <= 4) {
            y += 1;
        } else {
            y = 0;
        }
        this.setVelocity(0, this.speed);
        this.setPosition(px, py);
    }

    public void setImage(String filename) {
        Image i = new Image(filename);

        setImage(i);
    }

    public void setPosition(double x, double y) {
        positionX = x;
        positionY = y;
    }

    public void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = y;
    }

    public void addVelocity(double x, double y) {
        velocityX += x;
        velocityY += y;
    }

    public void update(double time) {
        double x = positionX + velocityX * time;
        if (x > 265 && x < 620) {
            positionX += velocityX * time;
            positionY += velocityY * time;

        }
    }

    public void update2(double time) {

        positionX += velocityX * time;
        positionY += velocityY * time;
        if (positionY >= 960) {positionY=-960;}
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, positionX, positionY);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean intersects(Car s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public String toString() {
        return " Position: [" + positionX + "," + positionY + "]"
                + " Velocity: [" + velocityX + "," + velocityY + "]";
    }
}