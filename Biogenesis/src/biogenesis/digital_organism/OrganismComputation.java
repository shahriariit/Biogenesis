package biogenesis.digital_organism;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import biogenesis.ExLine2DDouble;
import biogenesis.Utils;
import biogenesis.cell.GeneticCode;

public class OrganismComputation extends Rectangle{
	
	GeneticCode _geneticCode =new GeneticCode();  
	

	
	public double get_theta(){
		return Utils.random.nextDouble() * Math.PI * 2d;
	}
	
	public int segment(int x,int y){
		return x*y;
	}

	 public Color segColor(int i,int _geneLength){
		 return _geneticCode.getGene(i%_geneticCode.getNGenes()).getColor();
	 } 
	 
	 public double initialize_energy(double initial_energy,double get_Energy){
		 return Math.min(initial_energy,get_Energy);
	 } 
	 
	 public double inheritable_energy(int _reproduceEnergy,int _nChildren){
		 return _reproduceEnergy / (double)(_nChildren + 1);
	 } 
	 
	 public int MaxVal(int x,int y){
		 return Math.max(x,y);
	 } 
	 
	 public int MinVal(int x,int y){
		 return Math.min(x,y);
	 }
	 
	 public double SqrtVal(double x,double y){
		 return Math.sqrt(x*x+y*y);
	 }
	 
	 public double PowVal(double x,double y){
		 return Math.pow(x, y);
	 } 
	 
	 public double MidPoint(double x,double y){
		 return (x+y)/2d;
	 } 
	 
	 public double Round(double x){
		 return Math.round(x);
	 }
	 
	 public boolean isInsideWorld(int x,int y,double dx,double dy,int width,int height,int _world_width,int _world_height,double dtheta) {
			// Check it is inside the world
			if (x<0 || y<0 || x+ width>=_world_width || y+height>=_world_height) {
				// Adjust direction
				if (x < 0 || x + width >= _world_width)
					dx = -dx;
				if (y < 0 || y + height >=_world_height)
					dy = -dy;
				dtheta = 0;
				return false;
			}
			return true;
		}

	 private boolean placeRandom(double _dCenterX,double _dCenterY, int _centerX, int  _centerY,int _world_Width,
			                     int _world_Height,int _sizeRect_width, int _sizeRect_height,Organism _world_fastCheckHit,int _ID, int _world_NewId) {
			
			for (int i=12; i>0; i--) {
			
				Point origin = new Point(
					Utils.random.nextInt(_world_Width-_sizeRect_width),
					Utils.random.nextInt(_world_Height-_sizeRect_height));
				setBounds(origin.x,origin.y,_sizeRect_width,_sizeRect_height);
				_dCenterX = _centerX = origin.x + (_sizeRect_width>>1);
				_dCenterY = _centerY = origin.y + (_sizeRect_height>>1);
				// Check that the position is not occupied.
				if (_world_fastCheckHit == null) {
					// Generate an identification
					_ID = _world_NewId;
					return true;
				}
			}
			// If we get here, we haven't find a place for this organism.
			return false;
		} 
	 
	 
		/*public final boolean contact(Organism org,int _segments,int[] _m[], int[] x1,int[] y1,int[] x2,int[] y2,int _centerX,int _centerY) {
			int i,j;
			ExLine2DDouble line = new ExLine2DDouble();
			ExLine2DDouble bline = new ExLine2DDouble();
			// Check collisions for all segments
			for (i = _segments-1; i >= 0; i--) {
				// Consider only segments with modulus greater than 1
				if (_m[i]>=1) { 
					line.setLine(x1[i]+_centerX, y1[i]+_centerY, x2[i]+_centerX, y2[i]+_centerY);
					// First check if the line intersects the bounding box of the other organism
					if (org.intersectsLine(line)) {
						// Do the same for the other organism's segments.
						for (j = org._segments-1; j >= 0; j--) {
							if (org._m[j]>=1) {
								bline.setLine(org.x1[j] + org._centerX, org.y1[j] + org._centerY,
										org.x2[j] + org._centerX, org.y2[j] + org._centerY);
								if (intersectsLine(bline) && line.intersectsLine(bline)) {
									// If we found two intersecting segments, apply effects
									touchEffects(org,i,j,true);
									// Intersection point
									Point2D.Double intersec= line.getIntersection(bline);
									 touchMove needs to know which is the line that collides from the middle (not
									 * from a vertex). Try to guess it by finding the vertex nearest to the
									 * intersection point.
									 
									double dl1, dl2, dbl1, dbl2;
									dl1 = intersec.distanceSq(line.getP1());
									dl2 = intersec.distanceSq(line.getP2());
									dbl1 = intersec.distanceSq(bline.getP1());
									dbl2 = intersec.distanceSq(bline.getP2());
									// Use this to send the best choice to touchMove
									if (Math.min(dl1, dl2) < Math.min(dbl1, dbl2))
										touchMove(org,intersec,bline,false);
									else
										touchMove(org,intersec,line,true);
									// Find only one collision to speed up.
									return true;
								}
							}
						}
					}
				}
			}
			return false; 
			
		
		} */
		
		public static final int getTypeColor(Color c) {
			if (c.equals(Color.RED) || c.equals(Utils.ColorDARK_RED))
				return Organism_Util.RED;
			if (c.equals(Color.GREEN) || c.equals(Utils.ColorDARK_GREEN))
				return Organism_Util.GREEN;
			if (c.equals(Color.CYAN) || c.equals(Utils.ColorDARK_CYAN))
				return Organism_Util.CYAN;
			if (c.equals(Color.BLUE) || c.equals(Utils.ColorDARK_BLUE))
				return Organism_Util.BLUE;
			if (c.equals(Color.MAGENTA) || c.equals(Utils.ColorDARK_MAGENTA))
				return Organism_Util.MAGENTA;
			if (c.equals(Color.PINK) || c.equals(Utils.ColorDARK_PINK))
				return Organism_Util.PINK;
			if (c.equals(Color.ORANGE) || c.equals(Utils.ColorDARK_ORANGE))
				return Organism_Util.ORANGE;
			if (c.equals(Color.WHITE) || c.equals(Utils.ColorDARK_WHITE))
				return Organism_Util.WHITE;
			if (c.equals(Color.GRAY) || c.equals(Utils.ColorDARK_GRAY))
				return Organism_Util.GRAY;
			if (c.equals(Color.YELLOW) || c.equals(Utils.ColorDARK_YELLOW))
				return Organism_Util.YELLOW;
			if (c.equals(Utils.ColorBROWN))
				return Organism_Util.BROWN;
			return Organism_Util.NOCOLOR;
		} 
		
}
