import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    private TextArea teller = new TextArea();
    private javafx.scene.control.Button attackBtn = new javafx.scene.control.Button("Attack");
    private javafx.scene.control.Button specialBtn = new javafx.scene.control.Button("Special");
    private javafx.scene.control.Button counterBtn = new javafx.scene.control.Button("Counter");



    /** Set up a maven run profile in intellij or use maven from the command-line.
        Use the javafx:run argument to start the javafx application.
        Update all code and comments in this tempalte to suit your own project.
     */
    @Override
    public void start(Stage stage) {
        Label l = new Label("CS252 JavaFX Starter Template");


        HBox actionMenu = new HBox();
        Pane backGround = new Pane();
        BorderPane windowLayout = new BorderPane();
        Image enemySprite = new Image("/Enemy.png");

        backGround.setStyle("-fx-background-color: black;");
        backGround.setPrefSize(200,200);


        teller.setPrefSize(200,100);

        actionMenu.setPrefSize(200,70);
        actionMenu.getChildren().add(attackBtn);
        actionMenu.getChildren().add(specialBtn);
        actionMenu.getChildren().add(counterBtn);



        windowLayout.setBottom(actionMenu);
        windowLayout.setCenter(backGround);
        windowLayout.setTop(teller);


        Scene scene = new Scene(windowLayout, 500, 500);
        // locks screen at 500 x 500
        stage.setHeight(500);
        stage.setWidth(500);
        stage.setMaxHeight(500);
        stage.setMinHeight(500);
        stage.setMaxWidth(500);
        stage.setMinWidth(500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
