package Server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerFX extends Application{

    private Button submitfirstinput;
    private Button input;
    private Scene firstwindow;
    private String portAddress;

    private int portAddresses;

    private boolean isServer = true; // CHANGE TO TRUE FOR SERVER AND FALSE FOR CLIENT

    private NetworkConnectionServer  conn;
    private TextArea messages = new TextArea();

    private Parent createContent() {
        messages.setPrefHeight(550);
        input = new Button("Turn Off Server");

        input.setOnAction(event -> {
            Platform.exit();
            try {
                conn.closeConn();
            }
            catch(Exception e) {

            }


        });

        VBox root = new VBox(20, messages, input);
        root.setPrefSize(600, 600);

        return root;



    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Creating the first window for port number
        VBox firstWindow = new VBox(5);
        submitfirstinput = new Button("Turn On Server");
        TextField port = new TextField();
        Label portAdd = new Label("Choose any port you would like to use by typing it in. ");
        portAdd.setLabelFor(port);
        portAdd.setAlignment(Pos.BOTTOM_CENTER);
        firstWindow.getChildren().addAll(port);
        firstWindow.setAlignment(Pos.CENTER);
        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(submitfirstinput);
        hbox2.setAlignment(Pos.BOTTOM_CENTER);
        TilePane r = new TilePane();
        r.getChildren().addAll(portAdd);
        r.setAlignment(Pos.CENTER_LEFT);
        VBox first = new VBox(5);
        first.getChildren().addAll(firstWindow,r,hbox2);
        first.setAlignment(Pos.CENTER);



        BorderPane border2 = new BorderPane();
        border2.setCenter(first);

        firstwindow = new Scene(border2,600,600);



        submitfirstinput.setOnAction(e -> {
            portAddress = port.getText();
            portAddresses = Integer.parseInt(portAddress);
            conn = createServer(portAddresses);
            try {
                conn.startConn();
            } catch (Exception connection) {

            }
            primaryStage.setScene(new Scene(createContent()));
        });



        // TODO Auto-generated method stub
        primaryStage.setScene(firstwindow);
        primaryStage.show();

    }

    private Server createServer(int x) {
        return new Server(x, data-> {
            Platform.runLater(()->{
                messages.appendText(data.toString() + "\n");
            });
        });
    }
}




