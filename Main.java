/**
 * Created by markbeussink && Cameron Niccum 9/30/16.
 */

import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        String input;
        char c;
        Scanner scanner = new Scanner(System.in);

        do {
            input = "";
            System.out.println("Enter a for loop header.");
            System.out.println("i.e \"for (int i = 0; i < count ; i++)\"");
            input = scanner.nextLine();

            Parser prsr = new Parser(input);
            try {
                prsr.parse();
            }
            catch (Parser.ParseError e){
                System.out.println();
            }

            System.out.println("Do you want to try another input? (y | n)");
            c = scanner.nextLine().charAt(0);
        } while (c != 'n');
    }
}
