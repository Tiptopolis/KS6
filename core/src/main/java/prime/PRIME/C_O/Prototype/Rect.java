package prime.PRIME.C_O.Prototype;

import static prime.PRIME.uAppUtils.*;
import static prime.PRIME.uSketcher.*;
import java.io.Serializable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import prime.PRIME.C_O.Maths;
import prime.PRIME.C_O.VectorUtils;

public class Rect  implements Serializable, Shape2D {

	public float minX;
	public float minY;

	public float maxX;
	public float maxY;

	public float width;
	public float height;

	public Rect() {
		
		this(0, 0, 0, 0);
	}

	public Rect(float x, float y, float width, float height) {
		// bottom-left
		
		this.minX = x;
		this.minY = y;
		this.width = width;
		this.height = height;
		this.maxX = x + width;
		this.maxY = y + height;
	}

	public Rect(Vector2 position, Vector2 size) {
		// bottom-left
		this(position.x, position.y, size.x, size.y);
	}

	public Rect(Rectangle r) {
		this(r.x, r.y, r.width, r.height);
	}

	public Rect set(float x, float y, float width, float height) {
		this.minX = x;
		this.minY = y;
		this.width = width;
		this.height = height;
		this.maxX = x + width;
		this.maxY = y + height;
		return this;
	}

	public Rect set(Vector2 position, Vector2 size) {

		return this.set(position.x, position.y, size.x, size.y);
	}

	public void setAt(Vector2 at, Vector2 size) {
		this.width = size.x;
		this.height = size.y;
		this.set(at.x - (this.width / 2), at.y - (this.height / 2), this.width, this.height);
	}
	
	public Vector2 getSize()
	{
		return new Vector2(this.width, this.height);
	}

	public Vector2 getCenter() {
		float x = this.minX + (this.width / 2);
		float y = this.minY + (this.height / 2);
		return new Vector2(x, y);
	}
	
	public Vector2 getOrigin()
	{
		return new Vector2(this.minX,this.minY);
	}

	public void centerAt(Vector2 at) {
		this.set(at.x - (this.width / 2), at.y - (this.height / 2), this.width, this.height);
	}

	public void centerAt(Vector3 at) {
		this.set(at.x - (this.width / 2), at.y - (this.height / 2), this.width, this.height);
	}

	public Rect centeredAt(Vector3 at) {
		this.centerAt(at);
		return this;
	}

	public Vector2 getUV(Vector2 uv)
	{
		//range of 0-1
		float u = Maths.map(uv.x, 0, 1, 0, this.width);
		float v = Maths.map(uv.y, 0, 1, 0, this.height);
		return new Vector2(u,v);
		
		
	}
	
	@Override
	public boolean contains(Vector2 point) {

		return this.contains(point.x, point.y);
	}

	@Override
	public boolean contains(float x, float y) {
		//return this.minX <= x && this.minX + this.width >= x && this.minY <= y && this.minY + this.height >= y;
		return (this.minX <= x) && (this.maxX >= x) && (this.minY <= y) && (this.maxY >= y);
	}

	public Rect scl(float w, float h) {
		Vector2 ctr = this.getCenter();
		this.set(this.minX, this.minY, this.width / w, this.height * h);
		this.centerAt(ctr);
		return this;
	}

	public Rect scl(Vector2 scalar) {
		return this.scl(scalar.x, scalar.y);
	}

	public Matrix4 getMatrix() {
		return new Matrix4().setToOrtho2D(this.minX, this.minY, this.width, this.height);
	}

	public Rectangle getRectangle() {
		return new Rectangle(this.minX, this.minY, this.width, this.height);
	}

	
	
	public Rect extendBy(boolean dir, Vector3 by)
	{
		return this.extendBy(dir, new Vector2(by.x,by.y));
	}
	
	public Rect extendBy(boolean dir, Vector2 by)
	{
		Rect R = this.cpy();
		Rect E;
		if(dir)
		{
			E = new Rect(R.minX-by.x, R.minY-by.y, R.width+(by.x*2), R.height+(by.y*2));
		}
		else
		{
			E = new Rect(R.minX, R.minY, R.width+by.x, R.height+by.y);
		}
		
		return E;
	}
	
	public Rect cpy()
	{
		return new Rect(this.minX, this.minY,this.width,this.height);
	}

	@Override
	public String toString() {

		return "<Rect>: @" + new Vector2(this.minX, this.minY)+ " >" + new Vector2(this.maxX,this.maxY) + " #" + VectorUtils.IntVector2(this.width, this.height) ;
	}

	public String toLog() {
		String log = "";
		log += "["+this.width +","+this.height+"]";
		log += "\n*-* ("+this.minX + ","+this.minY+")";
		log += "\n*X*   >=>";
		log += "\n*-* ("+this.maxX + ","+this.maxY+")";
		log += "\n";
		return log;
	}
}
