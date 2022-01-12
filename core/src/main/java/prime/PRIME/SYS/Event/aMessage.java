package prime.PRIME.SYS.Event;

import com.badlogic.gdx.utils.Pool.Poolable;


public class aMessage implements iSignal, Poolable {

	public String memo;
	public Object data;

	public aMessage(String memo) {
		this.memo = memo;
		this.data = memo;
	}

	public aMessage(String memo, Object data) {
		this.memo = memo;
		this.data = data;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
