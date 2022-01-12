/**
 * Vertex.java
 *
 * Copyright 2014-2014 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY Michael Hoffer <info@michaelhoffer.de> "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Michael Hoffer <info@michaelhoffer.de> OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of Michael Hoffer <info@michaelhoffer.de>.
 */ 

package prime.EXT.v3d;

/**
 * Represents a vertex of a polygon. This class provides {@link #normal} so
 * primitives like {@link Cube} can return a smooth vertex normal, but
 * {@link #normal} is not used anywhere else.
 */
public class Vertex {

    /**
     * Vertex position.
     */
    private final Vector3d pos;

    /**
     * Normal.
     */
    private Vector3d normal;

    /**
     * Constructor. Creates a vertex.
     *
     * @param pos position
     * @param normal normal
     */
    public Vertex(Vector3d pos, Vector3d normal) {
        this.pos = pos;
        this.normal = normal;
    }

    @Override
    public Vertex clone() {
        return new Vertex(pos, normal);
    }

    /**
     * Inverts all orientation-specific data. (e.g. vertex normal).
     */
    public void flip() {
        normal = normal.negated();
    }

    /**
     * Create a new vertex between this vertex and the specified vertex by
     * linearly interpolating all properties using a parameter t.
     *
     * @param other vertex
     * @param t interpolation parameter
     * @return a new vertex between this and the specified vertex
     */
    public Vertex interpolate(Vertex other, double t) {
        return new Vertex(pos.lerp(other.pos, t),
                normal.lerp(other.normal, t));
    }

    /**
     * Returns this vertex in STL string format.
     *
     * @return this vertex in STL string format
     */
    public String toStlString() {
        return "vertex " + this.pos.toStlString();
    }

    /**
     * Returns this vertex in STL string format.
     *
     * @param sb string builder
     * @return the specified string builder
     */
    public StringBuilder toStlString(StringBuilder sb) {
        sb.append("vertex ");
        return this.pos.toStlString(sb);
    }

    /**
     * Applies the specified transform to a copy of this vertex.
     *
     * @param transform the transform to apply
     * @return a copy of this transform
     */
    public Vertex transformed(Transform transform) {
        return new Vertex(pos.transformed(transform), normal);
    }

	public Vector3d getPos() {
		return pos;
	}

	public Vector3d getNormal() {
		return normal;
	}
}
