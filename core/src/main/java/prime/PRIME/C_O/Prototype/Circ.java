package prime.PRIME.C_O.Prototype;

import java.io.Serializable;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

public class Circ implements Serializable, Shape2D {

	public Vector2 origin;
	public float radius;

	public Circ(float x, float y, float radius) {
		this.origin = new Vector2(x, y);
		this.radius = radius;

	}

	@Override
	public boolean contains(Vector2 point) {

		float d = this.origin.cpy().sub(point.cpy()).len();
		float D = Math.abs(d);
		if (D <= radius)
			return true;
		else
			return false;
	}

	@Override
	public boolean contains(float x, float y) {
		return this.contains(new Vector2(x, y));
	}

	public boolean contains(Circ c) {
		final float radiusDiff = radius - c.radius;
		if (radiusDiff < 0f)
			return false; // Can't contain bigger circle
		final float dx = this.origin.x - c.origin.x;
		final float dy = this.origin.y - c.origin.y;
		final float dst = dx * dx + dy * dy;
		final float radiusSum = radius + c.radius;
		return (!(radiusDiff * radiusDiff < dst) && (dst < radiusSum * radiusSum));
	}

	public boolean overlaps(Circ c) {
		float dx = this.origin.x - c.origin.x;
		float dy = this.origin.y - c.origin.y;
		float distance = dx * dx + dy * dy;
		float radiusSum = this.radius + c.radius;
		return distance < radiusSum * radiusSum;
	}

}
