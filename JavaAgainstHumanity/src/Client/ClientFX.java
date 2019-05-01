package Client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ClientFX extends Application {
    private Button rock;
    private Button scissors;
    private Button paper;
    private Button lizard;
    private Button spock;
    private Button playAgain;
    private Button quit;
    private Button submitfirstinput;
    private Button challenge;
    private Button submitsecondinput;
    private Button closeWindow;

    private Scene play;
    private Scene firstwindow;
    private Scene challenges;
    private Scene challengedeclined;

    private String clientPick;
    private String ipAddress;
    private String portAddress;

    private int portAddresses;

    private Text welcomeMessage;
    private Text rulesMessage;
    private Text startMessage;
    private Text clientMessage;
    private Text hallenbeckMessage;
    private Text closingMessage;

    private boolean isServer = false;

    private NetworkConnectionClient conn;

    private TextArea messages = new TextArea();

    private VBox createContent() {
        messages.setPrefHeight(550);
        TextField input = new TextField();

        input.setOnAction(event -> {
            String message = isServer ? "Server: " : "Client: ";
            message += input.getText();
            input.clear();

            messages.appendText(message + "\n");
            try {
                conn.send(message);
            }
            catch(Exception e) {

            }

        });

        VBox root = new VBox(20, messages);
        root.setPrefSize(100, 100);
        root.setAlignment(Pos.CENTER_LEFT);

        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Rock - Paper - Scissors - Lizard - Spock");

        welcomeMessage = new Text("Welcome to Rock Paper Scissors Lizard Spock!");
        welcomeMessage.setFont(Font.font("", FontPosture.ITALIC, 18));
        rulesMessage = new Text(
                "Scissors cuts paper, paper covers rock, rock crushes lizard, lizard poisons Spock, Spock smashes " +
                        "scissors, scissors decapitates lizard, lizard eats paper, paper disproves Spock, Spock " +
                        "vaporizes rock, and rock crushes scissors.");
        startMessage = new Text("Please make your selection below:");
        startMessage.setFont(Font.font("", FontPosture.ITALIC, 12));
        clientMessage = new Text("If you are Client 3 or greater then you can use the Challenge button.");

        Image rockImage = new Image("rock.png");
        Image paperImage = new Image("paper.png");
        Image scissorsImage = new Image("scissors.png");
        Image lizardImage = new Image("lizard.png");
        Image spockImage = new Image("spock.png");

        // NEW ADDITION
        ImageView hallenbeckImage = new ImageView("hallenbeck.jpg");
        hallenbeckImage.setX(300);
        hallenbeckImage.setY(300);
        hallenbeckImage.setFitHeight(180);
        hallenbeckImage.setFitWidth(157);
        hallenbeckImage.setPreserveRatio(true);

        hallenbeckMessage = new Text("Challenge declined!!! Better Luck next time!!!");
        hallenbeckMessage.setFont(Font.font("", FontPosture.ITALIC, 18));

        closingMessage = new Text("You may now hit the button to " +
                "close the window. Thanks for playing RPSLS which is presented by Mark Hallenbeck and the UIC staff.");
        closingMessage.setFont(Font.font("", FontPosture.ITALIC, 18));

        VBox h5 = new VBox();
        h5.getChildren().addAll(hallenbeckMessage,closingMessage);
        h5.setAlignment(Pos.CENTER);

        Group root = new Group(hallenbeckImage);

        closeWindow = new Button("Close Window");

        HBox h7 = new HBox();
        h7.getChildren().add(closeWindow);
        h7.setAlignment(Pos.BOTTOM_CENTER);

        VBox h6 = new VBox(5);
        h6.getChildren().addAll(root,h5,h7);
        h6.setAlignment(Pos.CENTER);
        h6.setPadding(new Insets(15, 15, 15, 15));

        BorderPane border6 = new BorderPane();
        border6.setCenter(h6);
        // NEW ADDITION ENDS

        rock = new Button("  Rock  ");
        rock.setGraphic(new ImageView(rockImage));
        paper = new Button("  Paper  ");
        paper.setGraphic(new ImageView(paperImage));
        scissors = new Button("   Scissors   ");
        scissors.setGraphic(new ImageView(scissorsImage));
        lizard = new Button("   Lizard   ");
        lizard.setGraphic(new ImageView(lizardImage));
        spock = new Button("   Spock   ");
        spock.setGraphic(new ImageView(spockImage));
        playAgain = new Button("Play Again");
        quit = new Button("Quit");
        challenge = new Button("Challenge");

        VBox vbox = new VBox();
        vbox.getChildren().addAll(welcomeMessage, rulesMessage,clientMessage, startMessage);
        vbox.setAlignment(Pos.CENTER);

        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(rock, paper, scissors, lizard, spock);
        hbox1.setAlignment(Pos.CENTER);
        hbox1.setPadding(new Insets(5, 5, 5, 5));
        hbox1.setSpacing(15);

        HBox hbox3 = new HBox();
        hbox3.getChildren().addAll(playAgain,challenge,quit);
        hbox3.setAlignment(Pos.BOTTOM_CENTER);
        hbox3.setSpacing(15);

        VBox main = new VBox(5);
        main.getChildren().addAll(vbox, hbox1, createContent(), hbox3);
        main.setAlignment(Pos.CENTER);
        main.setPadding(new Insets(15, 15, 15, 15));

        BorderPane border = new BorderPane();
        border.setCenter(main);

        // Creating the first window for ip address and port number
        VBox firstWindow = new VBox(5);
        submitfirstinput = new Button("Connect to Server");
        TextField ip = new TextField();
        Label ipAdd = new Label("IP Address: ");
        ipAdd.setLabelFor(ip);
        ipAdd.setAlignment(Pos.TOP_CENTER);
        TextField port = new TextField();
        Label portAdd = new Label("Port: ");
        portAdd.setLabelFor(port);
        portAdd.setAlignment(Pos.BOTTOM_CENTER);
        firstWindow.getChildren().addAll(ip,port);
        firstWindow.setAlignment(Pos.CENTER);
        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(submitfirstinput);
        hbox2.setAlignment(Pos.BOTTOM_CENTER);
        TilePane r = new TilePane();
        r.getChildren().addAll(ipAdd,portAdd);
        r.setAlignment(Pos.CENTER_LEFT);
        VBox first = new VBox(5);
        first.getChildren().addAll(firstWindow,r,hbox2);
        first.setAlignment(Pos.CENTER);

        BorderPane border2 = new BorderPane();
        border2.setCenter(first);


        // NEW ADDITION
        VBox secondWindow = new VBox(5);
        submitsecondinput = new Button("Submit");
        TextField whichclient = new TextField();
        Label clients = new Label("Which client do you want to challenge?");
        clients.setLabelFor(whichclient);
        clients.setAlignment(Pos.CENTER);
        secondWindow.getChildren().add(whichclient);
        secondWindow.setAlignment(Pos.CENTER);
        HBox hbox4 = new HBox();
        hbox4.getChildren().add(submitsecondinput);
        hbox4.setAlignment(Pos.BOTTOM_CENTER);
        TilePane r4 = new TilePane();
        r4.getChildren().add(clients);
        r4.setAlignment(Pos.CENTER_LEFT);
        VBox v4 = new VBox(5);
        v4.getChildren().addAll(secondWindow,r4,hbox4);
        v4.setAlignment(Pos.CENTER);

        BorderPane border4 = new BorderPane();
        border4.setCenter(v4);

        firstwindow = new Scene(border2,600,600);

        play = new Scene(border, 6000, 720);

        challenges = new Scene(border4,600,600); // NEW ADDITION

        challengedeclined = new Scene(border6,600,600); // NEW ADDITION

        rock.setOnAction(e -> {
            clientPick = "Rock";
            try {
                conn.send(clientPick);
            }
            catch(Exception rock) {

            }
        });

        paper.setOnAction(e -> {
            clientPick = "Paper";
            try {
                conn.send(clientPick);
            }
            catch(Exception p) {

            }
        });

        scissors.setOnAction(e -> {
            clientPick = "Scissors";
            try {
                conn.send(clientPick);
            }
            catch(Exception s) {

            }
        });

        lizard.setOnAction(e -> {
            clientPick = "Lizard";
            try {
                conn.send(clientPick);
            }
            catch(Exception l) {

            }
        });

        spock.setOnAction(e -> {
            clientPick = "Spock";
            try {
                conn.send(clientPick);
            }
            catch(Exception sp) {

            }
        });

        submitfirstinput.setOnAction(e -> {
            ipAddress = ip.getText();
            portAddress = port.getText();
            portAddresses = Integer.parseInt(portAddress);
            conn = createClient(ipAddress,portAddresses);
            try {
                conn.startConn();
            } catch (Exception connection) {

            }
            primaryStage.setScene(play);
        });

        playAgain.setOnAction(e -> {
            clientPick = "Play Again";
            try {
                conn.send(clientPick);
            } catch (Exception playAgain) {

            }
        });

        challenge.setOnAction(e -> {
            primaryStage.setScene(challenges);
        }); // NEW ADDITION

        submitsecondinput.setOnAction(e -> {
            primaryStage.setScene(challengedeclined);
        }); // NEW ADDITION

        closeWindow.setOnAction(e -> {
            Platform.exit();
            try {
                conn.closeConn();
            } catch (Exception close) {

            }
        }); // NEW ADDITION

        quit.setOnAction(e -> {
            clientPick = "Quit";
            try {
                conn.send(clientPick);
            } catch (Exception quit) {

            }
            Platform.exit();
            try {
                conn.closeConn();
            } catch (Exception close) {

            }
        });

        primaryStage.setScene(firstwindow);
        primaryStage.show();

    }

    private Client createClient(String ipA,Integer x) {
        return new Client(ipA,x, data -> {
            Platform.runLater(() -> {
                messages.appendText(data.toString() + "\n");
            });
        });


    }
}

