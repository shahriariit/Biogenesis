/* Copyright (C) 2006-2010  Joan Queralt Molina
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */
package biogenesis.cell;



import java.awt.Color;
import java.io.Serializable;

import biogenesis.Utils;




public class Gene implements Cloneable, Serializable {

	private static final long serialVersionUID = Utils.FILE_VERSION;
	private double _length = 0;
	private double _theta = 0;
	
	private Color _color;
	private int _back = 0; 
	
	public int getBack() {
		return _back;
	}
	public void setBack(int back) {
		_back = back;
	}
	public double getLength() {
		return _length;
	}
	public double getTheta() {
		return _theta;
	}
	public Color getColor() {
		return _color;
	}
	public void setColor(Color color) {
		_color = color;
	}
	public void setLength(double length) {
		_length = length;
	}
	public void setTheta(double theta) {
		_theta = theta;
	}  
	
	
	
	public Gene() {
	} 
	
	
	public Gene(double length, double theta, Color color) {
		_length = length;
		_theta = theta;
		_color = color;
	}
	
	public void randomize() {
		
		_length = 2.0+Utils.random.nextDouble()*16.0;
		_theta = Utils.random.nextDouble()*2.0*Math.PI; 
		
		int max_prob = Utils.RED_PROB + Utils.GREEN_PROB + Utils.BLUE_PROB +
			Utils.CYAN_PROB + Utils.WHITE_PROB + Utils.GRAY_PROB + Utils.YELLOW_PROB; 
		int prob = Utils.random.nextInt(max_prob); 
		
		int ac_prob = Utils.RED_PROB;
		if (prob < ac_prob) {
			_color = Color.RED;
			return;
		}
		ac_prob += Utils.GREEN_PROB;
		if (prob < ac_prob) {
			_color = Color.GREEN;
			return;
		}
		ac_prob += Utils.BLUE_PROB;
		if (prob < ac_prob) {
			_color = Color.BLUE;
			return;
		}
		ac_prob += Utils.CYAN_PROB;
		if (prob < ac_prob) {
			_color = Color.CYAN;
			return;
		}
		ac_prob += Utils.WHITE_PROB;
		if (prob < ac_prob) {
			_color = Color.WHITE;
			return;
		}
		ac_prob += Utils.GRAY_PROB;
		if (prob < ac_prob) {
			_color = Color.GRAY;
			return;
		}
		_color = Color.YELLOW;
		switch (Utils.random.nextInt(10)) {
		case 8:
			_back = -1;
			break;
		case 9:
			_back = -2;
			break;
		}
	} 
	
	@Override
	public Object clone() {
		Gene newGen = null; 
		
		try {
			newGen = (Gene) super.clone(); 
			
		} catch (CloneNotSupportedException e) {// We should never reach this 
			
		}
		return newGen;
	} 
	
	

}
