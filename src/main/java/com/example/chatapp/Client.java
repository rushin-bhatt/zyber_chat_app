package com.example.chatapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

public class Client extends Application {

    private DataInputStream dis;
    private DataOutputStream dos;
    private TextArea textArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        textArea = new TextArea();
        textArea.setEditable(false);

        TextField textField = new TextField();
        textField.setPromptText("Type your message...");

        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> sendMessage(textField.getText()));
        textField.setOnAction(event -> sendMessage(textField.getText()));

        VBox vbox = new VBox(10, textArea, textField, sendButton);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Client Chat App");
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();

        new Thread(this::connectToServer).start();
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 3001);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            while (true) {
                String receivedMessage = dis.readUTF();
                Platform.runLater(() -> textArea.appendText(  receivedMessage + "\n"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        try {
            dos.writeUTF(message);
            FileWriter fileWriter = new FileWriter("D:/PROJECTS/ZyberChat/ChatApp/ChatApp/src/main/java/com/example/chatapp/logs/clientlog.txt",true);
            fileWriter.write(message + System.lineSeparator());
            fileWriter.close();
            dos.flush();
            if (message.equals("exit")) {
                dos.close();
                dis.close();
                Platform.exit();
                System.exit(0);
            }
            textArea.appendText("You: " + message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}