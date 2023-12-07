import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("HangMan");
        String word = GameWord();
        StringBuilder hiddenWord = MakeGameWordHidden(word.length());


    }


    //metod för att välja ord att spela med
    private static String GameWord() {
        System.out.print("Ange vilket ord du vill spela med: ");
        String word = scanner.nextLine().toLowerCase();
        return word;
    }

    //metod för att göra det valda ordet man ska spela med "hidden" med streck
    private static StringBuilder MakeGameWordHidden(int length) {
        StringBuilder hiddenWord = new StringBuilder();
        for (int i = 0; i < length; i++) {
            hiddenWord.append("-");
        }
        return hiddenWord;
    }
}