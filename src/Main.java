import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("HangMan");

    }


    //metod för att välja ord att spela med
    private static String GameWord() {
        System.out.print("Ange vilket ord du vill spela med: ");
        String word = scanner.nextLine().toLowerCase();
        return word;
    }
}