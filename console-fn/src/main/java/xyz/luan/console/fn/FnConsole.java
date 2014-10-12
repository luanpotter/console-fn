package xyz.luan.console.fn;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;

import jline.console.ConsoleReader;

import org.fusesource.jansi.Ansi.Color;

import xyz.luan.console.parser.TabbedConsole;

public class FnConsole extends TabbedConsole {
	
	private static ConsoleReader console;

    public FnConsole() {
        try {
            console = new ConsoleReader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	@Override
    public void message(Object m) {
        try {
            console.print(start(Color.GREEN, " "));
            console.println(ansi().fg(Color.GREEN).a(m.toString()).reset().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String start(Color color, String token) {
        return ansi().fg(color).a(multiply(token + " ", tabLevel + 1)).reset().toString();
    }

	@Override
    public void result(Object r) {
        try {
            console.print(start(Color.GREEN, "<"));
            console.println(r.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


	@Override
	public void error(Object e) {
        try {
            console.print(start(Color.RED, "<"));
            console.println(e.toString());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
	}
	
	@Override
    public String read() {
        try {
			return console.readLine(start(Color.GREEN, ">"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	

	@Override
	public char[] readPassword() {
        try {
        	return console.readLine(start(Color.GREEN, ">"), '*').toCharArray(); //TODO creating string with pass...
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}
    
	@Override
    public void exit() {
        try {
            console.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
