package xyz.luan.console.fn;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;

import jline.console.ConsoleReader;

import org.fusesource.jansi.Ansi.Color;

import xyz.luan.console.parser.Console;

public class FnConsole implements Console {
	
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
            console.println(ansi().fg(Color.GREEN).a(m).reset().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	@Override
    public void result(Object r) {
        try {
            console.print(ansi().fg(Color.GREEN).a("< ").reset().toString());
            console.println(r.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


	@Override
	public void error(Object e) {
        try {
            console.print(ansi().fg(Color.RED).a("< ").reset().toString());
            console.println(e.toString());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
	}
	
	@Override
    public String read() {
        try {
            final String readMessage = ansi().fg(Color.GREEN).a("> ").reset().toString();
			return console.readLine(readMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	

	@Override
	public char[] readPassword() {
        try {
        	final String readPassMessage = ansi().fg(Color.YELLOW).a("> ").reset().toString();
        	return console.readLine(readPassMessage, '*').toCharArray(); //TODO creating string with pass...
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
