
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    private String word;
    private String playerName;
    private StringBuilder hiddenWord;
    private int tries;
    private Label hiddenWordLabel;
    private Label triesLabel;
    private TextField guessTextField;
    private Group figure;

    // map för att koppla scener med motsvarande Main-instanser
    private final Map<Stage, Main> multiplayerGames = new HashMap<>();
// en lista med ord
private final List<String> listOfWords = Arrays.asList( "hangman", "java", "computer");

    public static void main(String[] args) {
        // Starta
        launch(args);
    }

    @Override
    // Startmetod för JavaFX-applikationen
    public void start(Stage primaryStage) {
        showInitialScreen(primaryStage);
    }

    private Scene createInitialScene(Stage primaryStage) {
        Label welcomeLabel = new Label("Welcome to Hangman!");
        Button singlePlayerButton = new Button("Single Player");
        Button multiplayerButton = new Button("Multiplayer");
        Button exitButton = new Button("Exit");

        // Setta aktion för event handlers
        singlePlayerButton.setOnAction(e -> handleSinglePlayerClick());
        multiplayerButton.setOnAction(e -> handleMultiplayerClick());
        exitButton.setOnAction(e -> primaryStage.close());

        // skapa vbox layout
        VBox initialLayout = createVBoxWithChildren(welcomeLabel, singlePlayerButton,
                multiplayerButton, exitButton);
        initialLayout.setStyle("-fx-background-color: orange;");

        // skapa och retura första scenen
        return new Scene(initialLayout, 300, 250);
    }

    private void showInitialScreen(Stage primaryStage) {
        Scene initialScene = createInitialScene(primaryStage);

        primaryStage.setScene(initialScene);
        primaryStage.setTitle("Hangman Game");
        primaryStage.show();
    }

    private void handleSinglePlayerClick() {
        Stage singlePlayerStage = new Stage();

        // Random ord från list
        int randomIndex = (int) (Math.random() * listOfWords.size());
        word = listOfWords.get(randomIndex);

        // Initialize spel variabler
        hiddenWord = new StringBuilder("-".repeat(word.length()));
        tries = 10;
        playerName = "Single Player";

        // visa spelet skärm
        VBox gameLayout = showGameMenu(singlePlayerStage);
        Scene scene = new Scene(gameLayout, 300, 200);
        singlePlayerStage.setScene(scene);
        singlePlayerStage.setTitle("Hangman Game - Single Player");

        singlePlayerStage.setX(300);
        singlePlayerStage.setY(100);
        singlePlayerStage.show();
    }

    private void handleMultiplayerClick() {
        int windowWidth = 300;
        int windowHeight = 200;
        int spacing = 10;

        for (int i = 0; i < 6; i++) {
            Stage multiplayerStage = new Stage();

            // Skapa en ny instans av Main för varje multiplayer-spel
            Main multiplayerGame = new Main();
            multiplayerGame.initMultiplayerGame(multiplayerStage, i + 1);

            VBox gameLayout = multiplayerGame.showGameMenu(multiplayerStage);
            Scene scene = new Scene(gameLayout, 300, 200);
            multiplayerStage.setScene(scene);
            multiplayerStage.setTitle("Hangman Game - Multiplayer Player " + (i + 1));
            // Placera scenen baserat på index i loopen
            if (i < 3) {
                multiplayerStage.setX(i * (windowWidth + spacing));
                multiplayerStage.setY(100);
            } else {
                multiplayerStage.setX((i - 3) * (windowWidth + spacing));
                multiplayerStage.setY(130 + windowHeight + spacing);
            }
            // Spara data mellan scenen och Main-instansen
            // varje stage har egen Main-instansen
            multiplayerGames.put(multiplayerStage, multiplayerGame);
            // Visa scenen
            multiplayerStage.show();
        }
    }

         //En ny metod initMultiplayerGame användes för att
         // initiera inställningar specifika för varje multiplayer-spel och
         // för att koppla scenen med den nya instansen av Main.
    private void initMultiplayerGame(Stage stage, int playerNumber) {
        playerName = "Multiplayer Player " + playerNumber;
        multiplayerGames.put(stage, this);
    }


    private VBox showGameMenu(Stage currentStage) {
        // Skapa UI-element för spelsidan
        Label welcomeLabel = new Label("Hangman Game by IronPants group!");
        Label multiplayerLabel = new Label("Good luck!");

        // Textfält för att ange spelarens namn i spelmenyn
        TextField playerNameTextField = new TextField("Enter Your Name");

        playerNameTextField.setOnMouseClicked(e -> {
            if ("Enter Your Name".equals(playerNameTextField.getText())) {
                playerNameTextField.clear();
            }
        });

        Button startButton = new Button("Spela");
        startButton.setOnAction(e -> showStartScreen(currentStage, playerNameTextField.getText()));

        VBox gameMenuLayout = new VBox(10);
        gameMenuLayout.setAlignment(Pos.CENTER);
        gameMenuLayout.getChildren().add(welcomeLabel);
        gameMenuLayout.getChildren().add(multiplayerLabel);
        gameMenuLayout.getChildren().add(playerNameTextField);
        gameMenuLayout.getChildren().add(startButton);
        gameMenuLayout.setStyle("-fx-background-color: #00FA9A;");

        return gameMenuLayout;
    }


    private void showStartScreen(Stage primaryStage, String playerName) {
        // Skapa UI-element för startsidan
        Label wordLabel = new Label("Skriv in ett ord, eller om du vill att spelet välja ordet tryck starta!");
        PasswordField wordTextField = new PasswordField();
        Button startButton = new Button("Starta");
        startButton.setOnAction(e -> handleStartButtonClick(wordTextField.getText().toLowerCase(), primaryStage, playerName));

        // Skapa layout för startsidan
        VBox startLayout = createVBoxWithChildren(wordLabel, wordTextField, startButton);
        Scene startScene = new Scene(startLayout, 450, 300);
        startLayout.setStyle("-fx-background-color: #00FA9A;");

        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    private void showGameScreen(Stage primaryStage) {
        // Skapa UI-element för spelsidan
        hiddenWordLabel = new Label(hiddenWord.toString());
        triesLabel = new Label(playerName + " har " + tries + " försök kvar"); // Display player name and tries
        Label guessLabel = new Label("Skriv in en bokstav:");
        guessTextField = new TextField();
        Button guessButton = new Button("Gissa");
        guessButton.setOnAction(e -> handleGuessButtonClick(guessTextField.getText().toLowerCase(), primaryStage));
        figure = new Group();

        // Skapa layout för spelsidan
        GridPane gameLayout = createGridPaneWithChildren(new Insets(10),
                hiddenWordLabel, guessLabel, guessTextField, guessButton, triesLabel); // Remove playerNameTextField
        gameLayout.setStyle("-fx-background-color:#00FA9A;");

        // Skapa en AnchorPane för att överlagra Line ovanpå GridPane
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(gameLayout, figure);
        // Fäst GridPane till kanterna
        AnchorPane.setTopAnchor(gameLayout, 0.0);
        AnchorPane.setRightAnchor(gameLayout, 0.0);
        AnchorPane.setBottomAnchor(gameLayout, 0.0);
        AnchorPane.setLeftAnchor(gameLayout, 0.0);

        // Ordna hur element ska visas
        GridPane.setRowIndex(hiddenWordLabel, 0);
        GridPane.setRowIndex(guessLabel, 1);
        GridPane.setRowIndex(guessTextField, 3);
        GridPane.setRowIndex(guessButton, 4);
        GridPane.setRowIndex(triesLabel, 5);

        // Skapa en scen för spelet
        Scene gameScene = new Scene(anchorPane, 300, 200);

        // Sätt spelscenen
        primaryStage.setScene(gameScene);
        // Set stage title with player name
        primaryStage.setTitle("Hangman Game - " + playerName);
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

    private void updateGameScreen() {
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

    private void handleStartButtonClick(String inputWord, Stage primaryStage, String playerName) {
        // Hantera startknappen
        if (inputWord.isEmpty()) {
            // välja ord från list
            word = getRandomWordFromList();
        } else if (inputWord.matches("[a-z]+")) {
            // multiplayer
            word = inputWord;
        } else {
            showAlert("Ogiltig inmatning", "Ange ett giltigt ord (endast små bokstäver).");
            return;
        }

        //initialization single-player och multiplayer
        hiddenWord = new StringBuilder("-".repeat(word.length()));
        tries = 10;
        this.playerName = playerName;  // Set the class-level playerName variable

        // visa spelet skärm
        showGameScreen(primaryStage);
    }

    private String getRandomWordFromList() {
        int randomIndex = (int) (Math.random() * listOfWords.size());
        return listOfWords.get(randomIndex);
    }


    private void handleGuessButtonClick(String inputGuess, Stage primaryStage) {
        // Hantera gissningsknappen och uppdatera spelstatusen
        if (inputGuess.matches("[a-z]")) {
            char guess = inputGuess.charAt(0);
            boolean correctGuess = updateHiddenWord(guess);
            updateGameScreen(); // uppdatera skärm innan stänga skärmen
            if (!correctGuess) {
                showNextPenalty(tries);
                tries--;
                triesLabel.setText(playerName + " har " + tries + " försök kvar");
            }
            updateGameScreen();
            if (isWordGuessed()) {
                showAlert("Grattis!", "Du gissade rätt!");
                primaryStage.close();
            } else if (tries == 0) {
                showAlert("Spelet slut", "Inga fler försök. Ordet var: " + word);
                primaryStage.close();
            }

            // Ta bort gissad bokstav
            guessTextField.clear();
        } else {
            showAlert("Ogiltig inmatning", "Ange en giltig bokstav (endast små bokstäver).");
        }
    }

    private VBox createVBoxWithChildren(Node... children) {
        // Skapa en vertikal box (VBox)
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(children);
        return vbox;
    }

    private GridPane createGridPaneWithChildren(Insets insets, Node... children) {
        // Skapa en GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.getChildren().addAll(children);
        return gridPane;
    }

    private void showNextPenalty(int tries) {
        Line line = null;
        Circle circle = null;
        switch (tries) {
            case 10 -> line = new Line(225, 95, 280, 95);
            case 9 -> line = new Line(253, 95, 253, 25);
            case 8 -> line = new Line(253, 25, 210, 25);
            case 7 -> line = new Line(210, 25, 210, 45);
            case 6 -> circle = new Circle(210, 55, 10);
            case 5 -> line = new Line(210, 65, 210, 80);
            case 4 -> line = new Line(210, 72, 200, 70);
            case 3 -> line = new Line(210, 72, 220, 70);
            case 2-> line = new Line(210, 80, 200, 87);
            case 1 -> line = new Line(210, 80, 220, 87);
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
