package prime.METATRON._UTIL;

import static prime.PRIME.uAppUtils.*;
import static prime.PRIME.uSketcher.*;

import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventException;
import org.w3c.dom.events.EventListener;

import com.badlogic.gdx.InputProcessor;





public class mConsoleLogger {

	public boolean active = true;
	public static mConsoleLogger DefaultLogger;
	private static Collection<String> pending = new ArrayList<String>();
	private static Collection<String> toLog = new ArrayList<String>();
	
	InputProcessor owner;
	public mConsoleLogger(InputProcessor owner)
	{
		DefaultLogger = this;
		this.owner = owner;
		
	}
	
	public static void logOut(String log)
	{
		
		Log(log);
	}
	
	public static void logOut()
	{
		for(String p : pending)
		{
			toLog.add(p);
		}
		for(String s : toLog)
		{
			Log(s);			
		}
		toLog.clear();
	}
	
	public static void toLog(String to)
	{
		
		
		pending.add(to);
			//scan toLog list for Commands
		
	}
	
	public static void toLog(Object o)
	{
		pending.add(o.toString());
	}
	
	public static void toLog(Object[] os)
	{
		String res = "";
		for(int i =0; i < os.length; i++)
		{
			res+= "["+i+"]: "+os[i].toString()+"\n";
		}
		pending.add(res);
	}
	
	public static void toLog(Collection<String> to)
	{
		for(String s : to)
		{
			pending.add(s);
		}
	}

	


	
}
