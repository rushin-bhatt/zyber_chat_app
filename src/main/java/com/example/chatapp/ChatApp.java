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
import java.net.ServerSocket;
import java.net.Socket;

public class ChatApp extends Application{

    private TextArea textArea;
    private DataOutputStream dos;

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
        primaryStage.setTitle("Server Chat App");
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();

        new Thread(this::startServer).start();
    }

    private void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(3001);
            Platform.runLater(() -> textArea.appendText("Server is running\n"));

            Socket clientSocket = serverSocket.accept();
            Platform.runLater(() -> textArea.appendText("Connection established\n"));

            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            dos = new DataOutputStream(clientSocket.getOutputStream());

            while (true) {
                String receivedMessage = dis.readUTF();
                if (receivedMessage.equals("exit")) {
                    Platform.runLater(() -> textArea.appendText("Client ended chat\n"));
                    break;
                }
                Platform.runLater(() -> textArea.appendText("Client: " + receivedMessage + "\n"));
            }

            clientSocket.close();
            dis.close();
            dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        if (!message.isEmpty()) {
            try {
                dos.writeUTF("Server: " + message);
                FileWriter fileWriter = new FileWriter("D:/PROJECTS/ZyberChat/ChatApp/ChatApp/src/main/java/com/example/chatapp/logs/serverlog.txt",true);
                fileWriter.write(message + System.lineSeparator());
                fileWriter.close();
                dos.flush();
                Platform.runLater(() -> textArea.appendText("You: " + message + "\n"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}