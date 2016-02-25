
package biogenesis.cell;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.List;
import biogenesis.Utils;
import biogenesis.Vector2D;

public class GeneticCode implements Cloneable, Serializable {

	private static final long serialVersionUID = Utils.FILE_VERSION;

	RandomComputation compute = new RandomComputation();

	static final int MAX_SEGMENTS = 64;

	static final int MIN_SEGMENTS = 4;

	protected Gene[] _genes;

	public int _symmetry;

	public int _mirror;

	public boolean _disperseChildren;

	public int _reproduceEnergy;

	public int _max_age;

	public int getSymmetry() {
		return _symmetry;
	}

	public int getMirror() {
		return _mirror;
	}

	public boolean getDisperseChildren() {
		return _disperseChildren;
	}

	public int getReproduceEnergy() {
		return _reproduceEnergy;
	}

	public int getMaxAge() {
		return _max_age;
	}

	public Gene getGene(int i) {
		return _genes[i];
	}

	public int getNGenes() {
		return _genes.length;
	}

	private void randomGenes() {
		int nGenes = compute.numSegment(MIN_SEGMENTS, MAX_SEGMENTS, _symmetry);
		_genes = new Gene[nGenes];
		for (int i = 0; i < nGenes; i++) {
			_genes[i] = new Gene();
			_genes[i].randomize();
		}
	}

	public GeneticCode() {
		_mirror = compute.randomMirror();
		_symmetry = compute.randomSymmetry();
		randomGenes();
		_disperseChildren = compute.randomDisperseChildren();
		_reproduceEnergy = compute.calculateReproduceEnergy(_genes.length, _symmetry);
		_max_age = Utils.MAX_AGE;
	}

	public GeneticCode(List<Gene> genes, int symmetry, int mirror, boolean disperseChildren) {
		int nGenes = genes.size();
		_genes = new Gene[nGenes];
		genes.toArray(_genes);
		_max_age = Utils.MAX_AGE;
		_mirror = mirror;
		_symmetry = symmetry;
		_disperseChildren = disperseChildren;
		_reproduceEnergy = compute.calculateReproduceEnergy(_genes.length, _symmetry);
	}

	public GeneticCode(GeneticCode parentCode) {
		int i, j;
		int addedGene = -1;
		int removedGene = -1;
		int nGenes;

		if (Utils.randomMutation()) {
			// change symmetry

			_mirror = compute.randomMirror();
			_symmetry = compute.randomSymmetry();
			_disperseChildren = compute.randomDisperseChildren();
			nGenes = parentCode.getNGenes();

			if (Utils.random.nextBoolean()) {

				nGenes = compute.increaseSegment(parentCode.getNGenes(), parentCode.getSymmetry(), MAX_SEGMENTS);

				if (nGenes == parentCode.getNGenes() + 1) {
					addedGene = Utils.random.nextInt(nGenes);
				}

			} else {

				nGenes = compute.decreaseSegment(parentCode.getNGenes(), parentCode.getSymmetry(), MIN_SEGMENTS);

				if (nGenes == parentCode.getNGenes() - 1) {
					removedGene = Utils.random.nextInt(parentCode.getNGenes());
				}

			}

		} else {
			// keep symmetry
			_mirror = parentCode.getMirror();
			_symmetry = parentCode.getSymmetry();
			_disperseChildren = parentCode.getDisperseChildren();
			nGenes = parentCode.getNGenes();
		}
		// Create genes
		_genes = new Gene[nGenes]; 
		
		for (i = 0, j = 0; i < nGenes; i++, j++) { 
			
			_genes[i] = new Gene();
			
			if (removedGene == j) {
				i--;
				continue;
			}
			if (addedGene == i) {
				_genes[i].randomize();
				j--;
				continue;
			}
			if (Utils.randomMutation()) {
				_genes[i].randomize();
			} else
				_genes[i] = parentCode.getGene(j);
		}

		_reproduceEnergy = compute.calculateReproduceEnergy(_genes.length, _symmetry);
		_max_age = Utils.MAX_AGE;
	}

	@Override
	public Object clone() {
		GeneticCode newCode = null;
		try {
			newCode = (GeneticCode) super.clone();
			newCode._genes = new Gene[_genes.length];
			for (int i = 0; i < _genes.length; i++)
				newCode._genes[i] = (Gene) _genes[i].clone();
		} catch (CloneNotSupportedException e) {// We should never reach this
		}
		return newCode;
	}

	public void draw(Graphics g, int width, int height) {
		int[][] x0 = new int[_symmetry][_genes.length];
		int[][] y0 = new int[_symmetry][_genes.length];
		int[][] x1 = new int[_symmetry][_genes.length];
		int[][] y1 = new int[_symmetry][_genes.length];
		int maxX = 0;
		int minX = 0;
		int maxY = 0;
		int minY = 0;
		double scale = 1.0;
		Vector2D v = new Vector2D();
		Graphics2D g2 = (Graphics2D) g;

		for (int i = 0; i < _symmetry; i++) {
			for (int j = 0; j < _genes.length; j++) {
				v.setModulus(_genes[j].getLength());
				if (j == 0) {
					x0[i][j] = y0[i][j] = 0;
					if (_mirror == 0 || i % 2 == 0)
						v.setTheta(_genes[j].getTheta() + i * 2 * Math.PI / _symmetry);
					else {
						v.setTheta(_genes[j].getTheta() + (i - 1) * 2 * Math.PI / _symmetry);
						v.invertX();
					}
				} else {
					x0[i][j] = x1[i][j - 1];
					y0[i][j] = y1[i][j - 1];
					if (_mirror == 0 || i % 2 == 0)
						v.addDegree(_genes[j].getTheta());
					else
						v.addDegree(-_genes[j].getTheta());
				}

				x1[i][j] = (int) Math.round(v.getX() + x0[i][j]);
				y1[i][j] = (int) Math.round(v.getY() + y0[i][j]);

				maxX = Math.max(maxX, Math.max(x0[i][j], x1[i][j]));
				maxY = Math.max(maxY, Math.max(y0[i][j], y1[i][j]));
				minX = Math.min(minX, Math.min(x0[i][j], x1[i][j]));
				minY = Math.min(minY, Math.min(y0[i][j], y1[i][j]));
			}
		}

		g2.translate(width / 2, height / 2);
		if (maxX - minX > width)
			scale = (double) width / (double) (maxX - minX);
		if (maxY - minY > height)
			scale = Math.min(scale, (double) height / (double) (maxY - minY));
		g2.scale(scale, scale);

		for (int i = 0; i < _symmetry; i++) {
			for (int j = 0; j < _genes.length; j++) {
				x0[i][j] += (-minX - maxX) / 2;
				x1[i][j] += (-minX - maxX) / 2;
				y0[i][j] += (-minY - maxY) / 2;
				y1[i][j] += (-minY - maxY) / 2;
				g2.setColor(_genes[j].getColor());
				g2.drawLine(x0[i][j], y0[i][j], x1[i][j], y1[i][j]);
			}
		}
	}
}
