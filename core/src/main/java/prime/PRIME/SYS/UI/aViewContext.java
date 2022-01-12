package prime.PRIME.SYS.UI;

import static prime.PRIME.uAppUtils.*;

import com.badlogic.gdx.InputProcessor;

import prime.METATRON.iSpace;
import prime.PRIME.SYS._Shell;



public class aViewContext extends _Shell implements  iSpace, InputProcessor{

	//BufferManager
	//Camera
	
	public aViewContext(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void enter()
	{
		Log("{=>" + this.getClass().getSimpleName());
	}
	
	public void exit()
	{
		Log("<=}" + this.getClass().getSimpleName());
	}
	
	public void pause()
	{
		
	}
	
	public void resume()
	{
		
	}
	
}
