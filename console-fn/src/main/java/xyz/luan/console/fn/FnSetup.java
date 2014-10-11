package xyz.luan.console.fn;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.reflections.Reflections;

import xyz.luan.console.parser.Aliases;
import xyz.luan.console.parser.Application;
import xyz.luan.console.parser.Console;
import xyz.luan.console.parser.Context;
import xyz.luan.console.parser.Controller;
import xyz.luan.console.parser.Parser;
import xyz.luan.console.parser.actions.InvalidAction;
import xyz.luan.console.parser.actions.InvalidHandler;
import xyz.luan.console.parser.call.Caller;
import xyz.luan.console.parser.callable.Callable;
import xyz.luan.console.parser.config.ConfigController;
import xyz.luan.console.parser.config.HelpController;

/**
 * The way presented, this class can easily help you build your application.
 * It can be overwritten as pleased to insert custom behavior.
 * The most common methods to be overwritten have Javadocs explaining their functionality.
 * @author Luan Nico
 *
 * @param <T> your context
 */
public class FnSetup<T extends Context> {

	protected String controllersPackage;
	
	public FnSetup(String controllersPackage) {
		this.controllersPackage = controllersPackage;
	}
	
    public Application setupApplication(final T context) {
        final Console console = new FnConsole();

        Caller caller = defaultCaller(context, console);
        Parser parser = getParser();
        context.setup(parser, caller);

        return createApplication(console, context);
    }

    /**
     * Creates the custom application using the pre-built console and text
     * @param console the console to be used
     * @param context the context to be used
     * @return the Application created
     */
    protected Application createApplication(Console console, T context) {
    	return new FnApplication<T>(console, context);
    }
    
    /**
     * Should read from file or return defaultParser()
     * @return the parser
     */
	protected Parser getParser() {
		return defaultParser();
	}

	/**
	 * Should return a list of aliases
	 * @return the list of aliases
	 */
	protected Aliases defaultAliases() {
	    return Aliases.createAliasesWithDefaults();
	}

    protected Parser defaultParser() {
        return new Parser(defaultAliases(), defaultCallables());
    }

    protected Caller defaultCaller(T context, Console console) {
    	Caller caller = new Caller();
    	forEachController(c -> {
			try {
				Controller<T> controller = c.newInstance();
				controller.setup(context, console);
				caller.registerClass(c.getSimpleName(), controller);
			} catch (IllegalAccessException | InvalidHandler | InvalidAction | InstantiationException e) {
				throw new RuntimeException(e);
			}
    	});
    	return caller;
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void forEachController(Consumer<Class<? extends Controller<T>>> consumer) {
		final Reflections reflections = new Reflections(controllersPackage);
    	Set<Class<? extends Controller>> classes = reflections.getSubTypesOf(Controller.class);
    	for (Class<? extends Controller> controller : classes) {
    		consumer.accept((Class<? extends Controller<T>>) controller);
    	}
	}

	protected ArrayList<Callable> defaultCallables() {
        ArrayList<Callable> callables = new ArrayList<>();

        forEachController(c -> {
			try {
				Method defaultCallables = c.getMethod("defaultCallables", String.class, List.class);
				defaultCallables.invoke(null, c.getSimpleName(), callables);
			} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
        });

        ConfigController.defaultActions("config", callables);
        HelpController.defaultActions("help", callables);

        return callables;
    }
}