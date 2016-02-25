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
package biogenesis;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import biogenesis.cell.GeneticCodePanel;
import biogenesis.digital_organism.Organism;
import biogenesis.virtual_world.VisibleWorld;

public class InfoToolbar extends JToolBar {
	private static final long serialVersionUID = Utils.FILE_VERSION;

	protected Organism _selOrganism;
	protected JLabel _lEnergy, _lID, _lGeneration, _lAge, _lChildren, _lKills, _lInfected, _lMass;
	protected JButton _buttonGenes;
	protected GeneticCodePanel _geneticCodePanel;
	static private NumberFormat _nf = NumberFormat.getInstance();
	protected VisibleWorld _visibleWorld;
	
	public void setSelectedOrganism(Organism selectedOrganism) {
		_selOrganism = selectedOrganism;
		_lID.setText(_selOrganism!=null?_nf.format(_selOrganism.getID()):"-1");
		_lGeneration.setText(_selOrganism!=null?_nf.format(_selOrganism.getGeneration()):"0");
		recalculate();
		changeNChildren();
		changeNKills();
		changeNInfected();
		_buttonGenes.setEnabled(_selOrganism != null);
		_geneticCodePanel.setGeneticCode(_selOrganism!=null?_selOrganism.getGeneticCode():null);
		_geneticCodePanel.repaint();
		setVisible(_selOrganism != null);
	}
	
	// Recalculate continuously changing parameters
	public void recalculate() {
		_lEnergy.setText(_selOrganism!=null?_nf.format(_selOrganism.getEnergy()):"0"); //$NON-NLS-1$
		_lAge.setText(_selOrganism!=null?_nf.format(_selOrganism.getAge()>>8):"0"); //$NON-NLS-1$
		_lMass.setText(_selOrganism!=null?_nf.format(_selOrganism.getMass()):"0"); //$NON-NLS-1$
	}
	
	// Notify panel of important events
	public void changeNChildren() {
		_lChildren.setText(_selOrganism!=null?_nf.format(_selOrganism.getTotalChildren()):"0"); //$NON-NLS-1$
	}
	
	public void changeNKills() {
		_lKills.setText(_selOrganism!=null?_nf.format(_selOrganism.getTotalKills()):"0"); //$NON-NLS-1$
	}
	
	public void changeNInfected() {
		_lInfected.setText(_selOrganism!=null?_nf.format(_selOrganism.getTotalInfected()):"0"); //$NON-NLS-1$
	}
	
	public InfoToolbar(Organism selectedOrganism, VisibleWorld visibleWorld) {
		Dimension dimension = new Dimension(60,10);
		_selOrganism = selectedOrganism;
		_visibleWorld = visibleWorld;
		// Prepare number format
		_nf.setMaximumFractionDigits(1);
		// Create components
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints;
		// ID
		gridBagConstraints = new GridBagConstraints();
	    gridBagConstraints.gridx = 0;
	    gridBagConstraints.gridy = 0;
	    gridBagConstraints.anchor = GridBagConstraints.WEST;
	    gridBagConstraints.weightx = 1.0;
	    gridBagConstraints.gridheight=3;
	    _geneticCodePanel = new GeneticCodePanel(_selOrganism!=null?_selOrganism.getGeneticCode():null, _visibleWorld);
	    _geneticCodePanel.setPreferredSize(new Dimension(50,50));
	    add(_geneticCodePanel, gridBagConstraints);
	    
	    gridBagConstraints.gridheight=1;
	    gridBagConstraints.gridx = 1;
	    gridBagConstraints.gridy = 0;

		add(new JLabel(Messages.getString("T_ID"), JLabel.CENTER), gridBagConstraints); //$NON-NLS-1$
		_lID = new JLabel(_selOrganism!=null?_nf.format(_selOrganism.getID()):"-1",JLabel.CENTER); //$NON-NLS-1$
		_lID.setPreferredSize(dimension);
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		add(_lID, gridBagConstraints);
		// Generation
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		add(new JLabel(Messages.getString("T_GENERATION"),JLabel.CENTER), gridBagConstraints); //$NON-NLS-1$
		_lGeneration = new JLabel(_selOrganism!=null?_nf.format(_selOrganism.getGeneration()):"0",JLabel.CENTER); //$NON-NLS-1$
		_lGeneration.setPreferredSize(dimension);
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 0;
		add(_lGeneration, gridBagConstraints);
		// Age
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 0;
		add(new JLabel(Messages.getString("T_AGE"),JLabel.CENTER), gridBagConstraints); //$NON-NLS-1$
		_lAge = new JLabel(_selOrganism!=null?_nf.format(_selOrganism.getAge()>>8):"0",JLabel.CENTER); //$NON-NLS-1$
		_lAge.setPreferredSize(dimension);
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 0;
		add(_lAge, gridBagConstraints);
		// Energy
		gridBagConstraints.gridx = 7;
		gridBagConstraints.gridy = 0;
		add(new JLabel(Messages.getString("T_ENERGY"),JLabel.CENTER), gridBagConstraints); //$NON-NLS-1$
		_lEnergy = new JLabel(_selOrganism!=null?_nf.format(_selOrganism.getEnergy()):"0", JLabel.CENTER); //$NON-NLS-1$
		_lEnergy.setPreferredSize(dimension);
		gridBagConstraints.gridx = 8;
		gridBagConstraints.gridy = 0;
		add(_lEnergy, gridBagConstraints);
		// Number of sons
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		add(new JLabel(Messages.getString("T_CHILDREN"),JLabel.CENTER), gridBagConstraints); //$NON-NLS-1$
		_lChildren = new JLabel(_selOrganism!=null?_nf.format(_selOrganism.getTotalChildren()):"0",JLabel.CENTER); //$NON-NLS-1$
		_lChildren.setPreferredSize(dimension);
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		add(_lChildren, gridBagConstraints);
		// Number of killed organisms
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		add(new JLabel(Messages.getString("T_VICTIMS"), JLabel.CENTER), gridBagConstraints); //$NON-NLS-1$
		_lKills = new JLabel(_selOrganism!=null?_nf.format(_selOrganism.getTotalKills()):"0", JLabel.CENTER); //$NON-NLS-1$
		_lKills.setPreferredSize(dimension);
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 1;
		add(_lKills, gridBagConstraints);
		// Number of infected organisms
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 1;
		add(new JLabel(Messages.getString("T_INFECTED"), JLabel.CENTER), gridBagConstraints); //$NON-NLS-1$
		_lInfected = new JLabel(_selOrganism!=null?_nf.format(_selOrganism.getTotalInfected()):"0", JLabel.CENTER); //$NON-NLS-1$
		_lInfected.setPreferredSize(dimension);
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 1;
		add(_lInfected, gridBagConstraints);
		// Total mass
		gridBagConstraints.gridx = 7;
		gridBagConstraints.gridy = 1;
		add(new JLabel(Messages.getString("T_MASS"),JLabel.CENTER), gridBagConstraints); //$NON-NLS-1$
		_lMass = new JLabel(_selOrganism!=null?_nf.format(_selOrganism.getMass()):"0",JLabel.CENTER); //$NON-NLS-1$
		_lMass.setPreferredSize(dimension);
		gridBagConstraints.gridx = 8;
		gridBagConstraints.gridy = 1;
		add(_lMass, gridBagConstraints);
		// Button to view genes
		_buttonGenes = new JButton(Messages.getString("T_EXAMINE_GENES")); //$NON-NLS-1$
		_buttonGenes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	new LabWindow(_visibleWorld, _selOrganism.getGeneticCode());
            }
		});
		_buttonGenes.setEnabled(_selOrganism != null);
		// General characteristics
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		add(_buttonGenes, gridBagConstraints);
		setSize(200,200);
		setVisible(_selOrganism != null);
	}
}
