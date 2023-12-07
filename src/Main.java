import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in); // Creating a Scanner object for user input

    public static void main(String[] args) {
        System.out.println("HangMan");
        do {
            String word = GameWord(); // anropa metoden

            StringBuilder hiddenWord = MakeGameWordHidden(word.length()); // skapa ett dolt ord

            int attempts = 6; // max försök

            while (attempts > 0) {
                System.out.println("Current Progress: " + hiddenWord);
                char guess = getValidGuess(); // anropa metoden

                if (word.contains(String.valueOf(guess))) { // kolla om boksatven finns i ordet
                    updateHiddenWord(word, hiddenWord, guess); // Updatera ordet om bokstaven rätt
                    System.out.println("Correct guess!"); //
                } else {
                    attempts--; // minus med försök
                    System.out.println("Incorrect guess. Attempts left: " + attempts);
                }

                if (word.equals(hiddenWord.toString())) { // kolla om hela ordet har gissat
                    System.out.println("Congratulations! You guessed the word: " + word);
                    break; // stäng
                }
            }

            if (attempts == 0) { // kolla om försök är 0
                System.out.println("Sorry, no more attempts. The correct word was: " + word); // Printing a message with the correct word
            }
        } while (playAgain());
        System.out.println("thanks for playing our simple game");
        scanner.close(); // stäng scanner
    }

    private static String GameWord() { // Metoden to få ordet att gissa
        System.out.print("Enter the word to start playing: ");
        return scanner.nextLine().toLowerCase(); // lowercase
    }

    private static StringBuilder MakeGameWordHidden(int length) { // Method att dölja ordet
        StringBuilder hiddenWord = new StringBuilder(); // skapa en StringBuilder att presentera ordet
        for (int i = 0; i < length; i++) { // Loopa
            hiddenWord.append("-"); // Append - att representera dolt bokstav
        }
        return hiddenWord; // Retura dolt ordet
    }

    private static char getValidGuess() { // Metoden att ha en rätt bokstav från användaren
        char guess; // Variable att lagrae den gissade bokstav
        while (true) { // Loopa till rätt bokstav
            System.out.print("Enter a letter: ");
            String input = scanner.nextLine().toLowerCase();
            if (input.length() == 1 && Character.isLetter(input.charAt(0))) { //kolla om det är bara en bokstav i taget
                guess = input.charAt(0); // Assignera den  gissade boksatv
                break; // om rätt bokstav break
            } else {
                System.out.println("Invalid input. Please enter a single letter.");
            }
        }
        return guess;
    }

    private static void updateHiddenWord(String word, StringBuilder hiddenWord, char guess) {
        // Method att uppdatera ordet beror på gissade boksatv
        for (int i = 0; i < word.length(); i++) { // Loopa igenom ordet
            if (word.charAt(i) == guess) { // kolla om bokstaven på denna platsen matcha med gissningen
                hiddenWord.setCharAt(i, guess); // Updater ordet
            }
        }
    }
    private static boolean playAgain() {// metoden att spela om innan stänga
        System.out.print("Do you want to play again? (yes/no): ");
        String response = scanner.nextLine().toLowerCase();
        return response.equals("yes");
    }
}



