import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;


public class Main extends Application {
    /**
     * This is where all the attributes will be defined to be used to calibrate the rendering in start()
     */

    private final int windowSize = 500;

    private TextArea teller = new TextArea();
    private Hero player = new Hero("Hiro", 50, 20, 8, 5, 10, 10, 5); //arbitrary stat lineups
    private Enemy mob = new Enemy("Happy Suit", 20, 10, 7, 15, 5, 2, 35, 15);
    private javafx.scene.control.Button attackBtn = new javafx.scene.control.Button("Attack");
    private javafx.scene.control.Button specialBtn = new javafx.scene.control.Button("Special");
    private javafx.scene.control.Button scanBtn = new javafx.scene.control.Button("Scan");
    private Button backBtn = new Button("Back");
    private String displayedText = "You Encountered " + mob.getName()+ "!";
    private ArrayList<String> textStorage = new ArrayList<String>();
    private ArrayList<Button> specialMoves = new ArrayList<Button>();
    private ImageView enemySp = new ImageView("media/Enemy.png");


    private Pane actionMenu = new Pane();
    private Pane backGround = new Pane();
    private HBox actionButtons = new HBox();
    private HBox stats = new HBox();
    private HBox enemy = new HBox();
    private BorderPane windowLayout = new BorderPane();
    private Label health = new Label("HP: " + player.getHp());
    private Label magic = new Label("MP: " + player.getMp());
    private Box death = new Box();
    private HBox specialMenu = new HBox();
    private HBox specialList = new HBox();
    private HBox menuLeft = new HBox();
    private HBox menuRight = new HBox();

    private ColorInput effect = new ColorInput(0, 0, 500, 290, Paint.valueOf("gray"));
    private ColorInput urgency = new ColorInput(0, 0, 500, 290, Paint.valueOf("pink"));
    //These are all the animations that can be played in this application.
    private Timeline backgroundFX = new Timeline(
                new KeyFrame(Duration.seconds(1.5), new KeyValue(effect.paintProperty(), Paint.valueOf("darkgray"))),
            new KeyFrame(Duration.seconds(3), new KeyValue(effect.paintProperty(), Paint.valueOf("gray"))),
            new KeyFrame(Duration.seconds(3), new KeyValue(effect.paintProperty(), Paint.valueOf("black"))),
            new KeyFrame(Duration.seconds(3), new KeyValue(effect.paintProperty(), Paint.valueOf("gray"))),
            new KeyFrame(Duration.seconds(1.5), new KeyValue(effect.paintProperty(), Paint.valueOf("darkgray"))));
    private Timeline attack = new Timeline(
            new KeyFrame(Duration.seconds(.1), new KeyValue(enemySp.yProperty(), -35)),
            new KeyFrame(Duration.seconds(.3), new KeyValue(enemySp.yProperty(), 5)),
            new KeyFrame(Duration.seconds(.3), new KeyValue(enemySp.yProperty(), 10)),
            new KeyFrame(Duration.seconds(.3), new KeyValue(enemySp.yProperty(), 20))
            );
    private Timeline scanned = new Timeline(
            new KeyFrame(Duration.seconds(.5), new KeyValue(effect.paintProperty(), Paint.valueOf("darkgray"))),
            new KeyFrame(Duration.seconds(.3), new KeyValue(effect.paintProperty(), Paint.valueOf("yellow"))),
            new KeyFrame(Duration.seconds(.3), new KeyValue(effect.paintProperty(), Paint.valueOf("gold"))));
    private Timeline defeat = new Timeline(
            new KeyFrame(Duration.seconds(.2), new KeyValue(enemySp.opacityProperty(), 0)));
    private Timeline almostDead = new Timeline(
            new KeyFrame(Duration.seconds(.4), new KeyValue(urgency.paintProperty(), Paint.valueOf("pink"))),
            new KeyFrame(Duration.seconds(.8), new KeyValue(urgency.paintProperty(), Paint.valueOf("red"))),
            new KeyFrame(Duration.seconds(1.0), new KeyValue(urgency.paintProperty(), Paint.valueOf("pink"))));

    /**
     * This is constructing magic moves for the player to access in the battle through the Hero subclass
     */
    public void loadChar() {
        player.addSpecial("FireBall", 2+player.getSpAtt(), 4+player.getSpAtt(), "hurt",  4);
        player.addSpecial("Heal", 25, 35, "heal",  8);
        player.addBuffer("Sap", 10, 0, 5);
        player.addBuffer("Agility UP", 0, 10, 8);
    }

    /**
     * This updates the visuals so the text and labels will display correct values after turns
     */
    public void updateDisplay() {
        teller.setText(displayedText);
        health.setText("HP: " + player.getHp() + " ");
        magic.setText("MP: " + player.getMp() + " ");

    }

    /**
     * Because the player can have x amount of magic moves, it has to be loaded dynamically using for loops from
     * a list that stores those moves. Through this, the menu will correctly render the right amount of buttons with respected functionality.
     */

    public void loadSpecialMoves() {
        int i = 0;

        for (Special move: player.getSpecialMoves()) {
            specialMoves.add(new javafx.scene.control.Button(move.getName() + " " + move.getCost()));
            specialMoves.get(i).setOnAction(e->specialTurn(move));
            i++;
        }

    }

    /**
     * This is the functionality of the player scanning the enemy. It simply adds in text messages with enemy values onto
     * the dialogue storage list.
     */

    public void scanMove() {
        textStorage.add(player.getName() + " took a closer look at " + mob.getName()+"\n");
        textStorage.add("HP: " + mob.getHp() + "\n");
        textStorage.add("ATT: " + mob.getMp()+"\n");
        textStorage.add("ATT: " + mob.getAtt()+"\n");
        textStorage.add("DEF: " + mob.getDef()+"\n");
        textStorage.add("SPD: " + mob.getSpd()+"\n");
        textStorage.add("SPATT: " + mob.getSpAtt()+"\n");
        textStorage.add("SPDEF: " + mob.getSpDef()+"\n");
    }

    /**
     * Checks if either the player is dead or enemy is dead using the checkAlive instance method.
     * @return boolean
     */
    public boolean isGameOver() {
        if (!player.checkAlive()) {
            return true;

        } else if (!mob.checkAlive()) {
            return true;
        }
        return false;
    }

    /**
     * if the player dies, player will be booted out of the application using the alert to message gameover
     */
    public void loseScrn() {
        almostDead.stop();
        Alert gameover = new Alert(Alert.AlertType.ERROR);
        gameover.setTitle("GAME OVER");
        gameover.setContentText("YOU DIED");
        textStorage.add(" You took fatal damage!\n");
        backGround.setStyle("-fx-background-color: red");
        attackBtn.setDisable(true);
        specialBtn.setDisable(true);
        scanBtn.setDisable(true);
        updateGameStates();
        gameover.showAndWait();
        System.exit(0);
    }

    /**
     * if the player wins, the game is over and will gain "exp" and "gold" though everything will be disabled
     */

    public void winScrn() {
        almostDead.stop();
        attack.stop();
        backgroundFX.stop();
        defeat.play();
        enemySp.setOpacity(0);
        textStorage.add(" You've Won!\n");
        textStorage.add(" You got " + mob.getExp() + " exp.\n");
        textStorage.add(" You got " + mob.getGold() + " gold\n");
        textStorage.add(" Thanks for playing!");
        attackBtn.setDisable(true);
        specialBtn.setDisable(true);
        scanBtn.setDisable(true);
        updateGameStates();
    }

    /**
     * checks if the player is faster than the enemy
     * @return boolean
     */

    public boolean checkSpd() {
        return player.getSpd() > mob.getSpd();
    }

    /**
     * reads from the dialogue storage and updates the text output
     */
    public void updateGameStates() {
        for (String e: textStorage) {
            displayedText = displayedText + e;
        }
        if ((player.getHp() < (player.getMaxHp()*.3)) && mob.getHp() >0) {
            ohNo();
        }
        updateDisplay();

    }

    /**
     * This is a turn if the player chooses to simply attack. Spd and other stats will affect the outcome. Spd in particular will decide which Entity
     * will take action first. Players/Enemies can die before they can take action.
     */

    public void attackTurn() {
        textStorage.clear();
        displayedText = "";
        if (!checkSpd()) {
            attackPlayer("normal");
            if (isGameOver()) {
                loseScrn();
            }
            else {
                attackEnemy("normal", null);
                if (isGameOver()) {
                    winScrn();
                }
                else {
                    updateGameStates();
                }
            }

        }
        else {
            attackEnemy("normal", null);
            if (isGameOver()) {
                winScrn();
            }
            else {
                attackPlayer("normal");
                if (isGameOver()) {
                    loseScrn();
                }
                else {
                    updateGameStates();
                }
            }
        }
}

    /**
     * This is a turn if the player chooses to cast magic, the function will take a Special param accordingly for the specific move
     * If the player does not have enough MP however, they will be alerted and nothing will happen. Like with attack, Spd and other stats will affect
     * the outcome
     * @param move being a Special object
     */
    public void specialTurn(Special move) {
        textStorage.clear();
        displayedText = "";

        if (player.getMp() >= move.getCost()) {

            if (!checkSpd()) {
                attackPlayer("normal");
                if (isGameOver()) {
                    loseScrn();
                }
                else {
                    attackEnemy(move.getType(), move.getName());
                    if (isGameOver()) {
                        winScrn();
                    }
                    else {
                        updateGameStates();
                    }
                }

            } else {
                attackEnemy(move.getType(), move.getName());
                if (isGameOver()) {
                    winScrn();
                }
                else {
                    attackPlayer("normal");
                    if (isGameOver()) {
                        loseScrn();
                    } else {
                        updateGameStates();
                    }
                }

            }
        } else {
            Alert noMP = new Alert(Alert.AlertType.INFORMATION);
            noMP.setTitle("NO MP");
            noMP.setContentText("Not Enough MP To Cast!");
            noMP.showAndWait();
        }
    }

    /**
     * This is if the player chooses the scan move. It will scan for enemy stats while the enemy still attacks. This consumes a turn and players
     * can still die after enemy attack. It is made distinct with its yellow flash.
     */

    public void scanTurn() {
        scanned.play();

        textStorage.clear();
        displayedText = "";
        if (checkSpd()) {
            scanMove();
            attackPlayer("normal");
            if (isGameOver()) {
                loseScrn();
            }
            else { updateGameStates();}

        }
        else {
            attackPlayer("normal");
            if (isGameOver()) {
                loseScrn();
            }
            else { scanMove();updateGameStates();}
        }
        for (String e: textStorage) {
            displayedText = displayedText + e;
        }
        isGameOver();

    }

    /**
     * This function will be called when the player is at low health (30% of max). The backgroundFX will flash red instead of its black and gray
     */

    public void ohNo() {
        backgroundFX.stop();
        almostDead.setCycleCount(Animation.INDEFINITE);
        backGround.setEffect(urgency);
        almostDead.play();
    }

    /**
     * This is the code of how enemy will receive damage, or how the player will heal or deal debuffs and expend MP. Afterwards, if player
     * was in the magic menu, they will be booted out after selecting a move.
     * @param type being a string that referrs to attack type; healing is also included
     * @param move the name of the move if the player chooses magic to cast
     */

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
            if (player.getHp() >= player.getMaxHp()) {
                textStorage.add(player.getName() + " casted " + move + ": " + "They're HP has maxed out!\n");
            } else {
                textStorage.add(player.getName() + " casted " + move + ": " + "They healed " + player.getSpDmgNum() + " HP!\n");
            }
        }else if (type == "buff") {
            chosenMove = player.getSpecial(move);
            player.setMp(player.getMp() - chosenMove.getCost());
            player.takeSpdBuff(chosenMove.getBuff());
            textStorage.add(player.getName() + " casted " + move + ": " + "Spd increased by " + player.getSpDmgNum() + "!\n");
        }
         else {
            chosenMove = player.getSpecial(move);
            player.setMp(player.getMp() - chosenMove.getCost());
            mob.takeDebuff(chosenMove.getDebuff());
            if (mob.getDef() == 0 && mob.getSpDef() == 0) {
                textStorage.add(player.getName() + " casted " + move + ": The Enemy's defenses are all gone!\n");
            } else {
                textStorage.add(player.getName() + " casted " + move + ": " + "Enemy def/sp dropped by " + mob.getSpDmgNum() + "!\n");
            }
        }attack.play();
        specialMenu.setVisible(false);

    }

    /**
     *For the sake of avoiding over-complexity, enemies will merely attack the player with basic attacks.
     * @param type this will always be normal but can theoretically function similar to attackEnemy
     */

    public void attackPlayer(String type) {
        player.takeDmg(mob.getAtt());
        textStorage.add(mob.getName() + " attacked: " + player.getName() + " took " + player.getSpDmgNum() + " Damage!\n");

    }

    /** Start will **launch Maven** with the respected code that renders the battle screen appropriately. All visuals are calibrated here using private
     * attributes defined in the very beginning of this file.
     */
    @Override
    public void start(Stage stage) {
        Boolean putLeft = true;

        loadChar();
        loadSpecialMoves();

        //setting textarea
        teller.setText(displayedText);
        teller.setStyle("-fx-background-color: black");
        teller.setEditable(false);
        teller.setPrefSize(200,100);
        teller.setStyle("-fx-font-size: 18;");

        //setting action buttons
        attackBtn.setPrefHeight(70);
        attackBtn.setPrefWidth(166);
        attackBtn.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-background-color:linear-gradient(to top, gray, gray, gray, lightgray)");
        attackBtn.setOnAction(e -> attackTurn());
        specialBtn.setPrefHeight(70);
        specialBtn.setPrefWidth(166);
        specialBtn.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-background-color:linear-gradient(to top, gray, gray, gray, lightgray)");
        specialBtn.setOnAction(e -> specialMenu.setVisible(true));
        scanBtn.setPrefHeight(70);
        scanBtn.setPrefWidth(166);
        scanBtn.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-background-color:linear-gradient(to top, gray, gray, gray, lightgray)");
        scanBtn.setOnAction(e -> scanTurn());
        backBtn.setStyle("-fx-font-size: 10");
        backBtn.setPrefHeight(70);
        backBtn.setPrefWidth(35);
        backBtn.setOnAction(e->specialMenu.setVisible(false));

        //setting up visuals
        menuLeft.setPrefSize(232, 120);
        menuLeft.setStyle("-fx-background-color: linear-gradient(to top, gray, gray, gray, lightgray);");
        menuRight.setPrefSize(232, 120);
        menuRight.setStyle("-fx-background-color: linear-gradient(to top, gray, gray, gray, lightgray);");

        specialMenu.setPrefSize(500,120);
        specialMenu.setStyle("-fx-background-color: white");
        specialMenu.setVisible(false);
        specialMenu.getChildren().add(backBtn);
        specialMenu.getChildren().add(specialList);

        specialList.relocate(35,120);
        specialList.setStyle("-fx-background-color: white");
        specialList.setPrefSize(465,120);
        specialList.getChildren().add(menuLeft);
        specialList.getChildren().add(menuRight);

        //dynamically adding in magic move buttons
        for (Button e: specialMoves) {
            e.setStyle("-fx-pref-width: 100;");
            if (putLeft) {
                menuLeft.getChildren().add(e);

                putLeft = false;
            } else {
                menuRight.getChildren().add(e);

                putLeft = true;
            }

        }

        //further rendering visuals
        health.setStyle("-fx-text-fill: white");
        magic.setStyle("-fx-text-fill: white");
        stats.getChildren().add(health);
        stats.getChildren().add(magic);
        stats.setPrefSize(500, 25);
        stats.setStyle("-fx-font-size: 16;");
        backGround.setStyle("-fx-background-color: linear-gradient(to top, white, gray, black, gray);");
        backGround.setPrefSize(200,200);
        backGround.setEffect(effect);
        backgroundFX.setCycleCount(Animation.INDEFINITE);
        backgroundFX.play();

        actionButtons.setPrefSize(500,15);
        actionButtons.getChildren().add(attackBtn);
        actionButtons.getChildren().add(specialBtn);
        actionButtons.getChildren().add(scanBtn);

        actionMenu.setPrefSize(200,70);
        actionMenu.getChildren().add(stats);
        actionMenu.getChildren().add(actionButtons);
        actionMenu.getChildren().add(specialMenu);
        actionMenu.setStyle("-fx-background-color: linear-gradient(to top, darkgray, gray, gray, lightgray);");
        actionButtons.relocate(0, 25);

        windowLayout.setBottom(actionMenu);
        windowLayout.setCenter(backGround);
        windowLayout.setTop(teller);

        //adding enemy sprite
        windowLayout.getChildren().add(enemySp);
        enemySp.setFitHeight(200);
        enemySp.setFitWidth(200);
        enemySp.relocate(150,150);
        enemySp.toFront();

        Scene scene = new Scene(windowLayout, windowSize, windowSize);

        // locks screen at 500 x 500
        stage.setHeight(windowSize);
        stage.setWidth(windowSize);
        stage.setMaxHeight(windowSize);
        stage.setMinHeight(windowSize);
        stage.setMaxWidth(windowSize);
        stage.setMinWidth(windowSize);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();

    }

}

