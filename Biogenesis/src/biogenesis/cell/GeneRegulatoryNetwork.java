package biogenesis.cell;

import java.awt.Rectangle;

import biogenesis.Utils;
import biogenesis.Vector2D;
import biogenesis.digital_organism.OrganismComputation;
import biogenesis.digital_organism.Organism_Util;

public class GeneRegulatoryNetwork {
	
	OrganismComputation computation = new OrganismComputation();
	public int _mass;
	public int _I;
	public Rectangle _sizeRect = new Rectangle();
	public double[] _m;
	public double[] _m1;
	public double[] _m2;
	
	private static transient Vector2D v = new Vector2D();
	
	public void symmetric(int _symmetry,int _mirror,int _segments,double _geneticLength,int[] _startPointX
			,int[] _startPointY,int[] _endPointX,int[] _endPointY,double _geneticTheta) {
		
		int i,j,segment=0;
		int symmetry = _symmetry;
		int mirror = _mirror;
		int sequence = _segments / symmetry;
		int left=0, right=0, top=0, bottom=0;
		int centerX, centerY;
		double cx, cy;

		for (i=0; i<symmetry; i++) {
			for (j=0; j<sequence; j++,segment++) {
				
				v.setModulus(_geneticLength/Utils.scale[Organism_Util._growth_rate-1]);
				if (j==0) {
					_startPointX[segment] = 0;
					_startPointY[segment] = 0;
					if (mirror == 0 || i%2==0)
						
						v.setTheta(_geneticTheta+i*2*Math.PI/symmetry);
					else {
						v.setTheta(_geneticTheta+(i-1)*2*Math.PI/symmetry);
						v.invertX();
					}
				} else {
					_startPointX[segment] = _endPointX[segment - 1];
					_startPointY[segment] = _endPointY[segment - 1]; 
					
					if (mirror == 0 || i%2==0)
						v.addDegree(_geneticTheta);
					else
						v.addDegree(-_geneticTheta);
				}
		
				
				_endPointX[segment] = (int) computation.Round(v.getX() + _startPointX[segment]);
				_endPointY[segment] = (int) computation.Round(v.getY() + _startPointY[segment]);
			
				
				left = computation.MinVal(left, _endPointX[segment]);
			    right = computation.MaxVal(right, _endPointX[segment]);
			    top = computation.MinVal(top, _endPointY[segment]);
			    bottom =computation.MaxVal(bottom, _endPointY[segment]);
			}
		}
		_sizeRect.setBounds(left, top, right-left+1, bottom-top+1);
		// image center
		centerX = (left+right)>>1;
		centerY = (top+bottom)>>1;
		
		_mass=Organism_Util._mass;
		_I= Organism_Util._I; 
		
		for (i=0; i<_segments; i++) {
			// express points relative to the image center
			_startPointX[i]-=centerX;
			_startPointY[i]-=centerY;
			_endPointX[i]-=centerX;
			_endPointY[i]-=centerY;
					
			_m1[i] = computation.SqrtVal(_startPointX[i],_startPointY[i]);
			_m2[i] =computation.SqrtVal(_endPointX[i],_endPointY[i]);
			
			_m[i] =  computation.SqrtVal(computation.PowVal(_endPointX[i]-_startPointX[i],2) , computation.PowVal(_endPointY[i]-_startPointY[i],2)); 
			
			_mass += _m[i];
			
			
			cx = computation.MidPoint(_startPointX[i] , _endPointX[i]);
			cy = computation.MidPoint(_startPointY[i] , _endPointY[i]);
			
			
			_I += computation.PowVal(_m[i],3)/12d +
				_m[i] * cx*cx + cy*cy;// mass * length^2 (center is at 0,0)
		}
	}

	

}
