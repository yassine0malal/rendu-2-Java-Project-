package com.example.JavaFx.graphics;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AiAssistancePage extends GridPane {
    private TextArea responseArea;
    private TextArea conversationArea;
    private TextField inputField;
    private Button submitButton;


    public AiAssistancePage() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #DEDEDE;");
        this.setStyle("-fx-background-radius: 15px; -fx-border-radius: 15px; -fx-padding: 20;");
        this.setStyle("-fx-border-color: #f9f9f9; -fx-border-width: 5px;");
        this.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 12, 0, 4, 4);");

        handleUIElements();
    }

    private void handleUIElements() {
        responseArea = new TextArea();
        responseArea.setPrefSize(800, 200);
        responseArea.setEditable(false);
        responseArea.setStyle("-fx-font-size: 16px; -fx-font-family: monospace;");
        responseArea.setStyle(
                "-fx-background-color: #f9f9f9; -fx-border-radius: 15px; -fx-background-radius: 15px; -fx-padding: 20;");

        conversationArea = new TextArea();
        conversationArea.setPrefSize(800, 300);
        conversationArea.setEditable(false);
        conversationArea.setStyle("-fx-font-size: 14px; -fx-font-family: monospace;");
        conversationArea.setStyle(
                "-fx-background-color: #f9f9f9; -fx-border-radius: 15px; -fx-background-radius: 15px; -fx-padding: 20;");

        inputField = new TextField();
        inputField.setPromptText("Ask a question...");
        inputField.setStyle("-fx-font-size: 14px; -fx-border-radius: 10px;");

        submitButton = new Button("Submit");
        submitButton.setStyle("-fx-font-size: 14px; -fx-background-color: linear-gradient(to right,rgb(72, 183, 83), #50B3A2); -fx-text-fill: white; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-font-weight: bold;");
        submitButton.setOnAction(event -> handleSubmit());

        this.add(conversationArea, 0, 0, 2, 1);
        this.add(inputField, 0, 1);
        this.add(submitButton, 1, 1);
        // this.add(responseArea, 0, 2, 2, 1);
    }

    private void handleSubmit() {
        String userInput = inputField.getText();
        if (userInput != null && !userInput.trim().isEmpty()) {
            conversationArea.appendText("You: " + userInput + "\n");
            String aiResponse = getAIResponse(userInput);
            aiResponse = filterOffensiveContent(aiResponse); // Filter offensive content
            responseArea.setText(aiResponse);
            conversationArea.appendText("AI Assistance: " + aiResponse + "\n");
        }
        inputField.clear();
    }

    public String getAIResponse(String prompt) {
        // Replace with your Eden AI API key
        String apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiY2QwMDQxZjAtYmExZS00ZTcxLTg1ZjktODY3YTdjZDM1OGFhIiwidHlwZSI6ImFwaV90b2tlbiJ9.-mc20tyeS_5l8umcU50mLUPzH7yuArv5lobeTUX25OM"; 
        // Eden AI endpoint for text generation
        String endpoint = "https://api.edenai.run/v2/text/generation";
        String response = "";
    
        HttpURLConnection connection = null;
    
        try {
            // Prepare the HTTP connection
            URL url = new URL(endpoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
    
            // Build the request body
            String requestBody = String.format("{\"text\":\"%s\", \"providers\":[\"openai\"]}", prompt);
    
            // Send the request
            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestBody.getBytes("UTF-8"));
            }
    
            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Parse the response
                try (Scanner scanner = new Scanner(connection.getInputStream())) {
                    StringBuilder sb = new StringBuilder();
                    while (scanner.hasNext()) {
                        // if (scanner.equals()) {
                            
                        // }
                        sb.append(scanner.nextLine());
                    }
                    response = sb.toString();
                }
            } else {
                response = "Error: Received HTTP " + responseCode + " from Eden AI.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = "Failed to get AI response: " + e.getMessage();
        } finally {
            // Close the connection
            if (connection != null) {
                connection.disconnect();
            }
        }
    
        return response;
    }
    
    
    private String filterOffensiveContent(String response) {
      
        response = response.split("\"")[5];
        response = removeNonAlphanumericCharacters(response);
        return response;
    }

    public  String removeNonAlphanumericCharacters(String text) { // Remove all non-alphanumeric characters
        return text.replaceAll("\\n", " ");
    }
}
