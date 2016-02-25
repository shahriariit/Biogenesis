package biogenesis.cell;

import biogenesis.Utils;

public class RandomComputation {
	
	private static final long serialVersionUID = Utils.FILE_VERSION;
	
	public int randomMirror() {
		return Utils.random.nextInt(2);
	} 
	
	public int randomSymmetry() {
		return Utils.random.nextInt(8)+1;
	}

	
	public int numSegment(int MIN_SEGMENTS,int MAX_SEGMENTS,int _symmetry) {
		
		int nSegments = MIN_SEGMENTS + Utils.random.nextInt(MAX_SEGMENTS-MIN_SEGMENTS+1); // 4 - 64
		if (nSegments % _symmetry != 0)
		    nSegments += (_symmetry - (nSegments % _symmetry));
		 return nSegments / _symmetry;
	 	
	}
	
	public boolean randomDisperseChildren() {
		return  Utils.random.nextBoolean();
	}
	
	public int calculateReproduceEnergy(int _length, int _symmetry) {
		return 40 + 3 * _length * _symmetry;
	} 
	
	public void randomGene(Gene gene){		
		gene= new Gene();
		gene.randomize();		
	} 
	
	public int multiply(int x,int y){
		   return x*y;
	} 
	
	public int increaseSegment(int nGenes,int symmetry,int MAX_SEGMENTS){
		
		if (nGenes * symmetry >= MAX_SEGMENTS){
			return nGenes;
		}	
		else {
			return nGenes + 1;
		}
	}

	public int decreaseSegment(int nGenes,int symmetry,int MIN_SEGMENTS){
		
		if (nGenes * symmetry <= MIN_SEGMENTS){
			return nGenes;
		}	
		else {
			return nGenes - 1;
		}
	}
}
