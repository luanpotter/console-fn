package xyz.luan.console.fn;

import xyz.luan.console.parser.Application;
import xyz.luan.console.parser.Console;
import xyz.luan.console.parser.Context;
import xyz.luan.console.parser.call.CallResult;

public class FnApplication<T extends Context> extends Application {

	private Console console;
	private T context;
	
	public FnApplication(Console console, T context) {
		this.console = console;
		this.context = context;
	}
	
	@Override
	public Console createConsole() {
		return console;
	}

	@Override
	public Context createContext() {
		return context;
	}

	@Override
	public String startMessage() {
		return "Ohayou!";
	}

	@Override
	public String endMessage() {
		return "Bye bye...";
	}

	@Override
	public CallResult emptyLineHandler() {
		return CallResult.QUIT;
	}

}
