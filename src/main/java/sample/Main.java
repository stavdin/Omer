package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {

    public Main() throws URISyntaxException {
    }

    @Override
    public void start(Stage primaryStage) throws URISyntaxException, IOException, InterruptedException {
        primaryStage.setTitle("JavaFX Welcome");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text text = new Text("Welcome");
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(text, 0, 0, 2, 1);

        Label userEmail = new Label("User Email:");
        grid.add(userEmail, 0, 1);


        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();

        AtomicReference<String> email = new AtomicReference<>("");
        AtomicReference<String> password = new AtomicReference<>("");


        // action event
        btn.setOnAction(e -> {
            //Retrieving data
            email.set(userTextField.getText());
            System.out.println(email);
            password.set(pwBox.getText());
            System.out.println(password);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("email", (String) email.get());
            jsonObj.put("password", (String) password.get());
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = null;
            HttpResponse<String> response = null;
            try {
                request = HttpRequest
                        .newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(jsonObj.toString()))
                        .uri(new URI("http://localhost:8080/userLogin"))
                        .build();
                System.out.println("success1");
            } catch (URISyntaxException uriSyntaxException) {
                uriSyntaxException.printStackTrace();
            }
            try {
                response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());
                System.out.println("success2");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
    }

    ;


    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        launch(args);
    }
}
