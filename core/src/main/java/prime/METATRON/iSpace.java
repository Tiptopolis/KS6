package prime.METATRON;

import com.badlogic.gdx.math.Vector3;

public interface iSpace {

	public default Vector3 getUnit()
	{
		return new Vector3(1,1,1);
	}
	public default Vector3 getSize(){
		return new Vector3(Float.POSITIVE_INFINITY,Float.POSITIVE_INFINITY,Float.POSITIVE_INFINITY);
	}
}
