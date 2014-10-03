package xyz.luan.console.fn;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;

import jline.console.ConsoleReader;

import org.fusesource.jansi.Ansi.Color;

public class Main {

    private static ConsoleReader console;

    public static void main(String[] args) {
        start("Welcome to the sample program! This is an interactive shell. Type help for help.");

        loop: while (true) {
            String a = read();
            switch (a) {
            case "help":
                result("Sorry bud, no help for you!");
                break;
            case "dück":
                result("This is a duck!");
                break;
            case "colorful":
                result(ansi().a("What if this string was ").fg(Color.CYAN).a("colorful").reset().a(" too?"));
                break;
            case "exit":
                break loop;
            default:
                result("Not sure what was that... type help for help.");
            }
        }

        exit("Bye bye!");
    }

    public static void message(Object m) {
        try {
            console.println(ansi().fg(Color.GREEN).a(m).reset().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void result(Object r) {
        try {
            console.print(ansi().fg(Color.GREEN).a("< ").reset().toString());
            console.println(r.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void start(Object m) {
        try {
            console = new ConsoleReader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        message(m);
    }

    public static String read() {
        try {
            return console.readLine(ansi().fg(Color.GREEN).a("> ").reset().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void exit(Object m) {
        message(m);
        try {
            console.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
