import javafx.application.Application;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;

public class Main extends Application {

    @Override
    public void start(Stage theStage) {
        theStage.setTitle("Race!");

        Image image = new Image("file:./images/road.png");
        ImageView mv = new ImageView(image);
        Group root = new Group();
        root.getChildren().addAll(mv);
        Scene theScene = new Scene(root, 960, 960);
        theStage.setScene(theScene);
        theStage.setResizable(false);


        Canvas canvas = new Canvas(960, 960);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image road = new Image("file:./images/road.png");


        ArrayList<String> input = new ArrayList<String>();

        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        if (!input.contains(code))
                            input.add(code);
                    }
                });

        theScene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        input.remove(code);
                    }
                });


        Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
        gc.setFont(theFont);
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);


        Car background=new Car();
        background.setImage("file:./images/road.png");
        background.setBackground(0);


        Car background2=new Car();
        background2.setImage("file:./images/road.png");
        background2.setBackground(-960);



        Car racer = new Car();
        racer.setImage("file:./images/red.png");

        racer.setPosition(500, 800);

        ArrayList<Car> otherCars = new ArrayList<Car>();


        for (int i = 0; i < 6; i++) {
            Car other = new Car();
            other.setImage("file:./images/yellow.png");
            other.spawnCar();
            otherCars.add(other);


        }

        LongValue lastNanoTime = new LongValue(System.nanoTime());

        IntValue score = new IntValue(0);
        IntValue level = new IntValue(1);
        IntValue levelCount = new IntValue(0);


        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 700000000.0;
                lastNanoTime.value = currentNanoTime;

                // game logic


                racer.setVelocity(0, 0);
                if (input.contains("LEFT"))

                   racer.addVelocity(-100, 0);
                if (input.contains("RIGHT"))
                    racer.addVelocity(100, 0);
                if (input.contains("UP"))
                    for (int i =0;i<otherCars.size();i++) {otherCars.get(i).plusSpeed();}
                    background.plusSpeed2();
                    background2.plusSpeed2();





                //gc.drawImage(road,0,0);
                //gc.drawImage(road,-960,-960);

                // collision detection
                racer.update(elapsedTime);
                background.update2(elapsedTime);
                background2.update2(elapsedTime);
                Iterator<Car> carIter = otherCars.iterator();

                while (carIter.hasNext()) {
                    Car cars = carIter.next();

                    cars.update(elapsedTime);
                    cars.setSpeed();
                    if (cars.getY()>=900) {if (levelCount.value<5) {levelCount.value+=1;}  else {level.value++; levelCount.value=0;} score.value+=level.value;}
                    if (racer.intersects(cars)) {
                        stop();


                    }
                }
                background.update2(elapsedTime);
                background2.update2(elapsedTime);
                for (int i = 0; i < otherCars.size(); i++) {
                    if (otherCars.get(i).getY()>1000) {otherCars.get(i).spawnCar();  if (levelCount.value<5) {levelCount.value+=1;}  else {racer.level++; levelCount.value=0;} score.value+=racer.level++;}
                    for (int j = 0; j < otherCars.size(); j++) {
                        if (i != j && otherCars.get(i).intersects(otherCars.get(j))) {

                            otherCars.get(i).spawnCar();
                            otherCars.get(j).spawnCar();
                            break;


                        }
                    }

                }


                // render

               // gc.clearRect(0, 0, 1000, 1000);
                background.render(gc);
                background2.render(gc);
                racer.render(gc);


                for (Car moneybag : otherCars)
                    //moneybag.update(elapsedTime);
                    moneybag.render(gc);

                String pointsText = "Point: " + ( score.value);
                gc.fillText(pointsText, 5, 20);
                gc.strokeText(pointsText, 5, 20);

                String pointsText2 = "Level: " + (racer.level);
                gc.fillText(pointsText2, 5, 40);
                gc.strokeText(pointsText2, 5, 40);
            }
        }.start();

        theStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
