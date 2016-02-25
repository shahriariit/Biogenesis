
package biogenesis;

public class Vector2D {
	protected double _modulus = 0;
	protected double _theta = 0;
	
	public Vector2D() {
	}
	
	public double getX() {
		return _modulus*Math.cos(_theta);
	}
	
	public void setPolar(double modulus, double theta) {
		_modulus = modulus;
		_theta = theta; 
	}
	
	public void setModulus(double modulus) {
		_modulus = modulus;
	}
	
	public void setTheta(double theta) {
		_theta = theta;
	}
	
	public double getY() {
		return _modulus*Math.sin(_theta);
	}
	
	public double getModulus() {
		return _modulus;
	}
	
	public double getTheta() {
		return _theta;
	}
	
	public void addDegree(double theta) {
		_theta+=theta;
	}
	
	public void invertX() {
		_theta=Math.PI-_theta;
	}
	
	public void invertY() {
		_theta=2.0*Math.PI-_theta;
	}
}
