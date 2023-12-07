import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("HangMan");
        String word = gameWord();
        StringBuilder hiddenWord = makeGameWordHidden(word.length());
        int tries = 0;

        while (hiddenWord.toString().contains("-") && tries < 9) { //loopar sålänge ordet fortfarande innehåller '-' och det är under 9 försök kvar
            printCurrentState(hiddenWord, tries);
            char guess = userGuess();
            boolean isCorrect = updateHiddenWord(word, hiddenWord, guess);

            if (!isCorrect) {
                tries++; //om man gissar fel så ökar vi tries med ett, som i sin tur tar bort ett försök från ursprungliga 9 i metoden "printCurrentState"
            }
            if (isWordGuessed(hiddenWord)) {
                System.out.println("Grattis! Du gissade rätt ord: " + word + " och vann!");
                break;
            }
        }

        if (tries >= 9) { //om tries går upp till 9 eller högre så förlorar man (eftersom man subtraherar antalet tries med ursprungliga 9 i "printCurrentState"
            System.out.println("Tyvärr, du har förlorat. Rätt ord: " + word);
        }
>>>>>>> Adam
    }

    //metod för att välja ord att spela med
    private static String gameWord() {
        System.out.print("Ange vilket ord du vill spela med: ");
        String word = scanner.nextLine().toLowerCase();
        return word;
    }

    //metod för att göra det valda ordet man ska spela med "hidden" med streck
    private static StringBuilder makeGameWordHidden(int length) {
        StringBuilder hiddenWord = new StringBuilder();
        for (int i = 0; i < length; i++) {
            hiddenWord.append("-");
        }
        return hiddenWord;
    }

    //metod för visa vilka bokstäver man gissat på och hur många försök kvar
    private static void printCurrentState(StringBuilder hiddenWord, int tries) {
        System.out.println("Gissade bokstäver: " + hiddenWord);
        System.out.println("Antal kvarvarande försök: " + (9 - tries));
    }

    //metod för att användaren ska gissa en bokstav
    private static char userGuess() {
        System.out.print("Gissa en bokstav: ");
        return scanner.nextLine().toLowerCase().charAt(0);
    }

    //metod för att uppdatera hidden word om användaren gissar på en bokstav som finns i ordet eller inte.
    private static boolean updateHiddenWord(String word, StringBuilder hiddenWord, char guess) {
        boolean isCorrect = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess && hiddenWord.charAt(i) == '-') { //Går igenom varje tecken i det angivna ordet 'word' och kontrollerar om 'guess' finns.
                hiddenWord.setCharAt(i, word.charAt(i)); // uppdaterar den plats där gissningen matchar från '-' till rätt bokstav
                isCorrect = true;
            }
        }
        return isCorrect;
    }

    //metod för att kontrollera om ordet man gissat på är rätt, alltså inte innehåller "-" längre
    private static boolean isWordGuessed(StringBuilder hiddenWord) {
        return !hiddenWord.toString().contains("-");
    }

}