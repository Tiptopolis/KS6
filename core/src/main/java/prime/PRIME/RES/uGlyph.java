package prime.PRIME.RES;

import static prime.PRIME.uAppUtils.*;
import static prime.PRIME.uSketcher.*;
import static prime.PRIME.DefaultResources.*;
import static prime.PRIME.C_O.VectorUtils.*;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;

import prime.PRIME.C_O.Prototype.Rect;




public class uGlyph extends Glyph {

	public Color fill = Color.BLUE;
	public Color stroke = Color.LIGHT_GRAY;
	public Color glyph = Color.WHITE;

	public boolean doStroke = true;
	public boolean doFill = true;
	public boolean doGlyph = true;
	public int strokeWeight = 1;

	public Rect body;

	public boolean nominalBorderT = true;
	public boolean nominalBorderN = true;

	public uGlyph(GlyphSheet from, char using) {
		super(from, using);
		this.body = new Rect(0, 0, this.data.x, this.data.y);
		// turn Stroke off if using a multi-char thingy
	}

	public void draw(Batch batch) {
		Sprite S = this.Spr.S;
		S.draw(batch);
	}

	public void draw(Batch batch, float x, float y) {

		Sprite S = this.Spr.S;
		this.body.centerAt(new Vector2(x, y));
		S.setCenter(x, y);
		S.setOrigin(x - (this.data.x / 2), y - (this.data.y / 2));
		this.fill();
		this.stroke();
		S.draw(batch);
	}

	public void draw(float x, float y) {
		Sprite S = this.Spr.S;
		this.body.centerAt(new Vector2(x, y));
		S.setCenter(x, y);
		S.setOrigin((int) x - (this.data.x / 2), (int) y - (this.data.y / 2));
		this.fill();
		this.stroke();
		S.draw(Sketcher.getBatch());
	}

	public void draw(Batch batch, float x, float y, boolean fill, boolean stroke) {

		Sprite S = this.Spr.S;
		this.body.centerAt(new Vector2(x, y));
		S.setCenter(x, y);
		S.setOrigin(x - (this.data.x / 2), y - (this.data.y / 2));
		if (fill)
			this.fill();
		if (stroke)
			this.stroke();
		S.draw(batch);
	}

	protected void fill() {
		Sketcher.setColor(this.fill);
		Sketcher.Drawer.filledRectangle(this.body.getRectangle());
	}

	protected void stroke() {
		Sketcher.setColor(this.stroke);
		Sketcher.setLineWidth(this.strokeWeight);
		Vector2 origin = this.body.getOrigin();
		float tOff = 0.5f;
		if (this.nominalBorderT)
			tOff = (int) 0;
		Vector2 bl = (new Vector2(origin.x + tOff, origin.y + 0.5f));
		Vector2 tr = (new Vector2(origin.x - tOff, origin.y - 0.5f).cpy().add(this.body.getSize()));
		Vector2 br = (new Vector2(tr.x, bl.y));
		Vector2 tl = (new Vector2(bl.x, tr.y));

		// Sketcher.Drawer.line(bl.x, bl.y, br.x, br.y, 1, true, this.stroke,
		// this.stroke);
		// Sketcher.Drawer.line(tl.x, tl.y, tr.x, tr.y, 1, true, this.stroke,
		// this.stroke);
		Sketcher.Drawer.line(bl, br);
		Sketcher.Drawer.line(tl, tr);
		Sketcher.Drawer.line(bl, tl);
		Sketcher.Drawer.line(br, tr);
		Sketcher.setLineWidth(1);
	}

}
