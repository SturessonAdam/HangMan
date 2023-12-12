import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Main extends Application {

    private String word;
    private StringBuilder hiddenWord;
    private int tries;

    private Label hiddenWordLabel;
    private Label triesLabel;
    private TextField guessTextField;
    private Group figure;

    public static void main(String[] args) {
        // Starta
        launch(args);
    }

    @Override
    // Startmetod för JavaFX-applikationen
    public void start(Stage primaryStage) {
        // Ställ in den initiala scenen
        primaryStage.setTitle("Hangman Game");

        // Skapa UI-element för startsidan
        Button playButton = new Button("To game menu.");
        playButton.setOnAction(e -> showGameMenu(primaryStage));

        // välkommen meddelande
        Label welcomeLabel = new Label("Welcome to Hangman Game by IronPants group!");

        // Skapa layout för startsidan
        //"Video screen" användes som ett allmänt begrepp för att hänvisa till den initiala skärmen i JavaFX-applikation.
        VBox firstScreenLayout = createVBoxWithChildren(10, Pos.CENTER, playButton, welcomeLabel);
        Scene firstScreenScene = new Scene(firstScreenLayout, 400, 300);

        // Ange startsidan
        primaryStage.setScene(firstScreenScene);
        primaryStage.show();
    }

    private void showGameMenu(Stage primaryStage) {
        // Skapa UI-element för spelsidan
        Button startButton = new Button("Spela");
        startButton.setOnAction(e -> showStartScreen(primaryStage));

        // Skapa layout för spelsidan
        VBox gameMenuLayout = createVBoxWithChildren(10, Pos.CENTER, startButton);
        Scene gameMenuScene = new Scene(gameMenuLayout, 300, 200);

        // Ange spelsidan
        primaryStage.setScene(gameMenuScene);
        primaryStage.show();
    }

    private void showStartScreen(Stage primaryStage) {
        // Skapa UI-element för startsidan
        Label wordLabel = new Label("Skriv in ett ord:");
        TextField wordTextField = new TextField();
        Button startButton = new Button("Starta");
        startButton.setOnAction(e -> handleStartButtonClick(wordTextField.getText().toLowerCase(), primaryStage));

        // Skapa layout för startsidan
        VBox startLayout = createVBoxWithChildren(10, Pos.CENTER, wordLabel, wordTextField, startButton);
        Scene startScene = new Scene(startLayout, 300, 200);

        // Ange startsidan
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    private void showGameScreen(Stage primaryStage) {
        // Skapa UI-element för spelsidan
        hiddenWordLabel = new Label(hiddenWord.toString());
        triesLabel = new Label("Försök kvar: " + tries);
        Label guessLabel = new Label("Skriv in en bokstav:");
        guessTextField = new TextField();
        Button guessButton = new Button("Gissa");
        guessButton.setOnAction(e -> handleGuessButtonClick(guessTextField.getText().toLowerCase(), primaryStage));
        figure = new Group();

        // Skapa layout för spelsidan
        GridPane gameLayout = createGridPaneWithChildren(10, 10, 10, new Insets(10), Pos.CENTER,
                hiddenWordLabel, triesLabel, guessLabel, guessTextField, guessButton);

        // Create an AnchorPane to overlay the Line on top of the GridPane
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(gameLayout, figure);
        // Anchor the GridPane to the edges of the AnchorPane
        AnchorPane.setTopAnchor(gameLayout, 0.0);
        AnchorPane.setRightAnchor(gameLayout, 0.0);
        AnchorPane.setBottomAnchor(gameLayout, 0.0);
        AnchorPane.setLeftAnchor(gameLayout, 0.0);


        // Ordna hur element ska visas
        GridPane.setRowIndex(hiddenWordLabel, 0);
        GridPane.setRowIndex(triesLabel, 1);
        GridPane.setRowIndex(guessLabel, 2);
        GridPane.setRowIndex(guessTextField, 3);
        GridPane.setRowIndex(guessButton, 4);

        // Skapa en scen för spelet
        Scene gameScene = new Scene(anchorPane, 300, 200);

        // Sätt spelscenen
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    private boolean updateHiddenWord(char guess) {
        // Uppdatera dolt ord med gissade bokstaven
        boolean correctGuess = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess && hiddenWord.charAt(i) == '-') {
                hiddenWord.setCharAt(i, guess);
                correctGuess = true;
            }
        }
        return correctGuess;
    }

    private boolean isWordGuessed() {
        // Kolla om ordet har gissat
        return !hiddenWord.toString().contains("-");
    }

    private void updateGameScreen(Stage primaryStage) {
        // Uppdatera spelet med dolt ord
        hiddenWordLabel.setText(hiddenWord.toString());
    }

    private void showAlert(String title, String message) {
        // Visa en varning med angivet titel och meddelande
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void handleStartButtonClick(String inputWord, Stage primaryStage) {
        // Hantera startknappen
        if (inputWord.matches("[a-z]+")) {
            word = inputWord;
            hiddenWord = new StringBuilder("-".repeat(word.length()));
            tries = 9;
            showGameScreen(primaryStage);
        } else {
            showAlert("Ogiltig inmatning", "Ange ett giltigt ord (endast små bokstäver).");
        }
    }

    private void handleGuessButtonClick(String inputGuess, Stage primaryStage) {
        // Hantera gissningsknappen och uppdatera spelstatusen
        if (inputGuess.matches("[a-z]")) {
            char guess = inputGuess.charAt(0);
            boolean correctGuess = updateHiddenWord(guess);
            if (!correctGuess) {
                showNextPenalty(tries, primaryStage);
                tries--;
                triesLabel.setText("Försök kvar: " + tries);
            }
            updateGameScreen(primaryStage);
            if (isWordGuessed()) {
                showAlert("Grattis!", "Du gissade rätt!");
                primaryStage.close();
            } else if (tries == -1) {
                showAlert("Spelet slut", "Inga fler försök. Ordet var: " + word);
                primaryStage.close();
            }

            // Ta bort gissad bokstav
            guessTextField.clear();
        } else {
            showAlert("Ogiltig inmatning", "Ange en giltig bokstav (endast små bokstäver).");
        }
    }

    private VBox createVBoxWithChildren(double spacing, Pos alignment, Node... children) {
        // Skapa en vertikal box (VBox)
        VBox vbox = new VBox(spacing);
        vbox.setAlignment(alignment);
        vbox.getChildren().addAll(children);
        return vbox;
    }

    private GridPane createGridPaneWithChildren(double hgap, double vgap, double padding,
                                                Insets insets, Pos alignment, Node... children) {
        // Skapa en GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(hgap);
        gridPane.setVgap(vgap);
        gridPane.setPadding(new Insets(padding));
        gridPane.setAlignment(alignment);
        gridPane.getChildren().addAll(children);
        return gridPane;
    }

    private void showNextPenalty(int tries, Stage primaryStage) {
        System.out.println("tries:" + tries);
        Line line = null;
        Circle circle = null;
        switch (tries) {
            case 9 -> line = new Line(225, 95, 280, 95);
            case 8 -> line = new Line(253, 95, 253, 25);
            case 7 -> line = new Line(253, 25, 210, 25);
            case 6 -> line = new Line(210, 25, 210, 45);
            case 5 -> circle = new Circle(210, 55, 10);
            case 4 -> line = new Line(210, 65, 210, 80);
            case 3 -> line = new Line(210, 72, 200, 70);
            case 2 -> line = new Line(210, 72, 220, 70);
            case 1 -> line = new Line(210, 80, 200, 87);
            case 0 -> line = new Line(210, 80, 220, 87);
        }

        if (line != null) {
            line.setStroke(Color.BLUE);
            figure.getChildren().add(line);
        }
        if (circle != null) {
            circle.setFill(Color.TRANSPARENT);       // Set fill color
            circle.setStroke(Color.GREEN);    // Set stroke color
            circle.setStrokeWidth(1);
            figure.getChildren().add(circle);
        }
    }
}
