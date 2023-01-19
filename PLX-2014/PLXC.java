import java.io.*;
import java.util.Scanner;

public class PLXC {
    public static PrintStream out;

    public static void main(String[] args) {
        try {
            Reader in = new InputStreamReader(System.in);
            out = System.out;

            if (args.length > 0) {
                in = new FileReader(args[0]);
            }

            if (args.length > 1) {
                out = new PrintStream(new FileOutputStream(args[1]));
            }

            parser p = new parser(new Yylex(in));
            Object result = p.parse().value;

        } catch (Exception e) {
            e.printStackTrace();
            try (Scanner sc = new Scanner(new FileReader(args[0]))){
                while (sc.hasNextLine()){
                    out.println(sc.nextLine());
                }

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }
}
