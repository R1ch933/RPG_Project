import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;


public class Main extends Application {


    private TextArea teller = new TextArea();
    private Hero player = new Hero("Hiro", 50, 20, 15, 5, 10, 10, 5);
    private Enemy mob = new Enemy("Suit Man", 35, 30, 10, 0, 15, 5, 2);
    private javafx.scene.control.Button attackBtn = new javafx.scene.control.Button("Attack");
    private javafx.scene.control.Button specialBtn = new javafx.scene.control.Button("Special");
    private javafx.scene.control.Button scanBtn = new javafx.scene.control.Button("Scan");
    private Button backBtn = new Button("<--");
    private String displayedText = "You Encountered a Monster!!";
    private ArrayList<String> textStorage = new ArrayList<String>();
    private ArrayList<Button> specialMoves = new ArrayList<Button>();

    private Pane actionMenu = new Pane();
    private Pane backGround = new Pane();
    private HBox actionButtons = new HBox();
    private HBox stats = new HBox();
    private Box enemy = new Box(150, 250, 10);
    private BorderPane windowLayout = new BorderPane();
    private Label health = new Label("HP: " + player.getHp());
    private Label magic = new Label("MP: " + player.getMp());
    private Box death = new Box();
    private HBox specialMenu = new HBox();
    private HBox specialList = new HBox();

    public void loadChar() {
        player.addSpecial("FireBall", 3, 9, 0, "hurt", 4);
        player.addSpecial("Heal", 25, 35, 0, "heal", 8);
        player.addSpecial("Sap", 0,0, 10, "debuff", 5);

    }

    public void updateDisplay() {
        teller.setText(displayedText);
        health.setText("HP: " + player.getHp() + " ");
        magic.setText("MP: " + player.getMp() + " ");

    }

    public void loadSpecialMoves() {
        int i = 0;

        for (Special move: player.getSpecialMoves()) {
            specialMoves.add(new javafx.scene.control.Button(move.getName() + " " + move.getCost()));
            specialMoves.get(i).setOnAction(e->specialTurn(move));
            i++;
        }

    }

    public void scanMove() {
        textStorage.add(player.getName() + " took a closer look at the enemy!\n");
        textStorage.add("Enemy Name: " + mob.getName()+"\n");
        textStorage.add("HP: " + mob.getHp() + "\n");
        textStorage.add("ATT: " + mob.getMp()+"\n");
        textStorage.add("ATT: " + mob.getAtt()+"\n");
        textStorage.add("DEF: " + mob.getDef()+"\n");
        textStorage.add("SPD: " + mob.getSpd()+"\n");
        textStorage.add("SPATT: " + mob.getSpAtt()+"\n");
        textStorage.add("SPDEF: " + mob.getSpDef()+"\n");
    }
    public void isGameOver() {
        Alert gameover = new Alert(Alert.AlertType.ERROR);
        gameover.setTitle("GAME OVER");
        gameover.setContentText("YOU DIED");


        if (!player.checkAlive()) {
            displayedText = displayedText + " You took fatal damage!";
            backGround.setStyle("-fx-background-color: red");
            attackBtn.setDisable(true);
            specialBtn.setDisable(true);
            scanBtn.setDisable(true);
            updateDisplay();
            gameover.showAndWait();
            System.exit(0);

        }
    }

    public boolean checkSpd() {
        return player.getSpd() > mob.getSpd();
    }

    public void attackTurn() {
        textStorage.clear();
        displayedText = "";
        if (checkSpd()) {
            attackPlayer("normal");
            attackEnemy("normal", null);

        }
        else {
            attackEnemy("normal", null);
            attackPlayer("normal");
        }
        for (String e: textStorage) {
            displayedText = displayedText + e;
        }
        updateDisplay();
        isGameOver();
}

    public void specialTurn(Special move) {
        textStorage.clear();
        displayedText = "";

        if (player.getMp() >= move.getCost()) {

            if (checkSpd()) {
                attackPlayer("normal");
                attackEnemy(move.getType(), move.getName());

            } else {
                attackEnemy(move.getType(), move.getName());
                attackPlayer("normal");
            }
            for (String e : textStorage) {
                displayedText = displayedText + e;
            }
            isGameOver();
            updateDisplay();
        } else {
            Alert noMP = new Alert(Alert.AlertType.INFORMATION);
            noMP.setTitle("NO MP");
            noMP.setContentText("Not Enough MP To Cast!");
            noMP.showAndWait();
        }
    }

    public void scanTurn() {
        textStorage.clear();
        displayedText = "";
        if (checkSpd()) {
            scanMove();
            attackPlayer("normal");

        }
        else {
            attackPlayer("normal");
            scanMove();
        }
        for (String e: textStorage) {
            displayedText = displayedText + e;
        }
        isGameOver();
        updateDisplay();
    }

    public void attackEnemy(String type, String move) {
        Special chosenMove;

        if (type == "hurt") {
            chosenMove = player.getSpecial(move);
            player.setMp(player.getMp() - chosenMove.getCost());
            mob.takeSpDmg(chosenMove.getMinDmg(), chosenMove.getMaxDmg());
            textStorage.add(player.getName() + " casted " + player.getSpecial(move).getName() + ": It did " +mob.getSpDmgNum()+ " damage!\n");
        } else if (type == "normal"){
            mob.takeDmg(player.getAtt());
            textStorage.add("You attacked the enemy: You did " + mob.getSpDmgNum() + " Damage!\n");
        } else if (type == "heal") {
            chosenMove = player.getSpecial(move);
            player.setMp(player.getMp() - chosenMove.getCost());
            player.healSelf(chosenMove.getMinDmg(), chosenMove.getMaxDmg());
            textStorage.add(player.getName() + " casted " + move + ": " + "They healed " + player.getSpDmgNum() + " HP!\n");
        } else {
            chosenMove = player.getSpecial(move);
            player.setMp(player.getMp() - chosenMove.getCost());
            mob.takeDebuff(chosenMove.getDebuff());
            textStorage.add(player.getName() + " casted " + move + ": " + "Enemy def/sp dropped by: " + player.getSpDmgNum() + "!\n");
        }

        specialMenu.setVisible(false);

    }

    public void attackPlayer(String type) {
        player.takeDmg(mob.getAtt());
        textStorage.add("The enemy attacked: You took " + player.getSpDmgNum() + " Damage!\n");
    }

    /** Set up a maven run profile in intellij or use maven from the command-line.
        Use the javafx:run argument to start the javafx application.
        Update all code and comments in this tempalte to suit your own project.
     */
    @Override
    public void start(Stage stage) {
        //setting textarea
        //teller.setDisable(true);
        teller.setText(displayedText);
        loadChar();
        loadSpecialMoves();

        teller.setStyle("-fx-font-size: 18;-fx-text-alignment: justify");
        //setting action buttons
        attackBtn.setPrefHeight(70);
        attackBtn.setPrefWidth(166);
        attackBtn.setStyle("-fx-font-size: 20");
        attackBtn.setOnAction(e -> attackTurn());
        specialBtn.setPrefHeight(70);
        specialBtn.setPrefWidth(166);
        specialBtn.setStyle("-fx-font-size: 20");
        specialBtn.setOnAction(e -> specialMenu.setVisible(true));
        scanBtn.setPrefHeight(70);
        scanBtn.setPrefWidth(166);
        scanBtn.setStyle("-fx-font-size: 20");
        scanBtn.setOnAction(e -> scanTurn());
        backBtn.setPrefHeight(20);
        backBtn.setPrefWidth(35);
        backBtn.setOnAction(e->specialMenu.setVisible(false));

        //setting up visuals

        specialMenu.setPrefSize(500,120);
        specialMenu.setStyle("-fx-background-color: red");
        specialMenu.setVisible(false);
        specialMenu.getChildren().add(backBtn);
        specialMenu.getChildren().add(specialList);

        specialList.relocate(35,120);
        specialList.setStyle("-fx-background-color: green");
        specialList.setPrefSize(465,120);
        for (Button e: specialMoves) {
            specialList.getChildren().add(e);
        }

        stats.getChildren().add(health);
        stats.getChildren().add(magic);
        stats.setStyle("-fx-background-color: blue");
        stats.setPrefSize(500, 25);
        stats.setStyle("-fx-font-size: 16");

        backGround.setStyle("-fx-background-color: black;");
        backGround.setPrefSize(200,200);
        backGround.getChildren().add(enemy);
        enemy.relocate(150,25);

        teller.setPrefSize(200,100);


        actionButtons.setPrefSize(500,15);
        actionButtons.getChildren().add(attackBtn);
        actionButtons.getChildren().add(specialBtn);
        actionButtons.getChildren().add(scanBtn);

        actionMenu.setPrefSize(200,70);
        actionMenu.getChildren().add(stats);
        actionMenu.getChildren().add(actionButtons);
        actionMenu.getChildren().add(specialMenu);
        actionButtons.relocate(0, 25);




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

