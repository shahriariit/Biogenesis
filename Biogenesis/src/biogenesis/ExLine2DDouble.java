
package biogenesis;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class ExLine2DDouble extends Line2D.Double {
	private static final long serialVersionUID = Utils.FILE_VERSION;

	public Point2D.Double getIntersection(Line2D.Double l)
	{
		double x,y;
		double d;
		double t;
		d = (l.x1-l.x2)*(y2-y1)+(x2-x1)*(l.y2-l.y1);
		if (d < Utils.tol) {
			// Parallel or the same straigh line
			return new Point2D.Double((x1+x2+l.x1+l.x2)/4d,(y1+y2+l.y1+l.y2)/4d);
		}
		t = ((l.x1-x1)*(y2-y1)+(x2-x1)*(y1-l.y1))/d;
		x = l.x1 + t*(l.x2-l.x1);
		y = l.y1 + t*(l.y2-l.y1);

		return new Point2D.Double(x,y);
	}
}
