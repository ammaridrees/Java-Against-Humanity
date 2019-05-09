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
    private Button playAgain;
    private Button quit;
    private Button submitfirstinput;
    private Button challenge;
    private Button submitsecondinput;
    private Button closeWindow;
    private Button submitthirdinput;

    private Scene play;
    private Scene firstwindow;
    private Scene challenges;
    private Scene challengedeclined;

    private String clientPick;
    private String ipAddress;
    private String portAddress;
    private String answer;

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
        primaryStage.setTitle("Java Against Humanity");

        welcomeMessage = new Text("Welcome to Java Against Humanity!");
        welcomeMessage.setFont(Font.font("", FontPosture.ITALIC, 18));
        rulesMessage = new Text(
                "The rules for this game are simple. Ask a question and choose the best answer. The first client to ask" +
                        "will be Client 1 but after that it will be the client that has the best answer.");
        startMessage = new Text("When choosing a client please just enter Client # (# stands for the number you want to choose).");
        clientMessage = new Text("Use the Challenge button at your own risk.");

        // NEW ADDITION
        ImageView hallenbeckImage = new ImageView("hallenbeck.jpg");
        hallenbeckImage.setX(300);
        hallenbeckImage.setY(300);
        hallenbeckImage.setFitHeight(180);
        hallenbeckImage.setFitWidth(157);
        hallenbeckImage.setPreserveRatio(true);

        ImageView hallenbeckImage2 = new ImageView("hallenbeck2.jpg");
        hallenbeckImage2.setX(300);
        hallenbeckImage2.setY(300);
        hallenbeckImage2.setFitHeight(180);
        hallenbeckImage2.setFitWidth(157);
        hallenbeckImage2.setPreserveRatio(true);

        ImageView hallenbeckImage3 = new ImageView("hallenbeck3.jpg");
        hallenbeckImage3.setX(300);
        hallenbeckImage3.setY(300);
        hallenbeckImage3.setFitHeight(180);
        hallenbeckImage3.setFitWidth(157);
        hallenbeckImage3.setPreserveRatio(true);

        hallenbeckMessage = new Text("You think you are so great by pressing that challenge button. Think again, freedom " +
                "isn't freedom, two players aren't two players.");
        hallenbeckMessage.setFont(Font.font("", FontPosture.ITALIC, 18));

        closingMessage = new Text("You may now hit the button to " +
                "close the window. Thanks for playing Java Against Humanity which is presented by Mark Hallenbeck " +
                "and the UIC staff.");
        closingMessage.setFont(Font.font("", FontPosture.ITALIC, 18));

        VBox h5 = new VBox();
        h5.getChildren().addAll(hallenbeckMessage,closingMessage);
        h5.setAlignment(Pos.CENTER_LEFT);

        Group root = new Group(hallenbeckImage);

        Group root2 = new Group(hallenbeckImage2);

        Group root3 = new Group(hallenbeckImage3);

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

        VBox vbox7 = new VBox();
        submitthirdinput = new Button("Submit your question or answer!");
        TextField quesanswer = new TextField();
        Label gameplay = new Label("Ask or Answer!");
        gameplay.setLabelFor(quesanswer);
        gameplay.setAlignment(Pos.CENTER);
        vbox7.getChildren().addAll(quesanswer,submitthirdinput);

        // NEW ADDITION ENDS
        playAgain = new Button("Play Again");
        quit = new Button("Quit");
        challenge = new Button("Challenge");

        VBox vbox = new VBox();
        vbox.getChildren().addAll(root2,welcomeMessage,rulesMessage,startMessage,clientMessage);
        vbox.setAlignment(Pos.CENTER);

        HBox hbox3 = new HBox();
        hbox3.getChildren().addAll(playAgain,challenge,quit);
        hbox3.setAlignment(Pos.BOTTOM_CENTER);
        hbox3.setSpacing(15);

        VBox main = new VBox(5);
        main.getChildren().addAll(vbox,vbox7,createContent(), hbox3);
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
        firstWindow.getChildren().addAll(root3,ip,port);
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
        Label clients = new Label("Ask a question.");
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

        submitthirdinput.setOnAction(e -> {
            answer = quesanswer.getText();
            quesanswer.clear();
            try {
                conn.send(answer);
            } catch (Exception submitthirdinput) {

            }
        } );

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

