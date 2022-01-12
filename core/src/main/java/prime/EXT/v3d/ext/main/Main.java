package prime.EXT.v3d.ext.main;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import prime.EXT.v3d.CSG;
import prime.EXT.v3d.Cube;
import prime.EXT.v3d.FileUtil;
import prime.EXT.v3d.Sphere;
import prime.EXT.v3d.Transform;
import prime.EXT.v3d.Vector3d;

public class Main {

	public static void main(String[] args) {
		// we use cube and sphere as base geometries
		CSG cube = new Cube(2).toCSG();
		CSG sphere = new Sphere(Vector3d.ZERO, 1.25, 32, 16).toCSG();

		// perform union, difference and intersection
/*		CSG cubePlusSphere = cube.union(sphere);
		CSG cubeMinusSphere = cube.difference(sphere);
		CSG cubeIntersectSphere = cube.intersect(sphere);*/

		// translate geometries to prevent overlapping 
		CSG union = cube.
		        union(sphere.transformed(Transform.unity().translate(3.4, 0, 0)))/*.
		        union(cubePlusSphere.transformed(Transform.unity().translate(6, 0, 0))).
		        union(cubeMinusSphere.transformed(Transform.unity().translate(9, 0, 0))).
		        union(cubeIntersectSphere.transformed(Transform.unity().translate(12, 0, 0)))*/;

		// save union as stl
		try {
		    FileUtil.write(
		            Paths.get("sample.stl"),
		            union.toStlString()
		    );
		} catch (IOException ex) {
		    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
