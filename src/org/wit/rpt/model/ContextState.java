package org.wit.rpt.model;

import javax.swing.JDialog;

import org.wit.rpt.ui.OutCSEditor;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class ContextState {
	private String csname;
	private Individual csi;
	private Individual predicate;
	private Individual argu1;
	private Individual argu2;
	OntModel m;
	Property label;

	public static void main(String[] args) {
		CapabilityModel cm = new CapabilityModel();
		// ContextState cs = new ContextState(cm.getModel(),
		// "AGVPosition_sameposition_destinationPosition");
		// ContextState csed = new ContextState(cm.getModel(),
		// "AGVPosition_sameposition_destinationPosition");
		ContextState cs = new ContextState(cm.getModel(), "cartPosition_is_ready");
		ContextState csed = new ContextState(cm.getModel(), "cartInfo_is_ready");
		// System.out.println(completeMatchwith(cs, csed));
		// System.out.println(partMatchwith(cs, csed));

	}

	public ContextState(OntModel om, String name) {
		m = om;
		csname = name;
		csi = m.getIndividual(CapabilityModel.modelURI + csname);
	}

	public String getName() {
		return csname;
	}

	public Individual getIndividual() {
		return argu1;

	}

	OntResource getPredicate() {
		Property predicate = m.getProperty(CapabilityModel.modelURI + "propertyPredicate");
		NodeIterator ni = csi.listPropertyValues(predicate);
		OntResource predicatei = null;
		while (ni.hasNext()) {
			predicatei = (OntResource) ni.next();
		}

		return predicatei;
	}

	OntResource getargu1() {
		Property argument1 = m.getProperty(CapabilityModel.modelURI + "argument1");
		NodeIterator ni = csi.listPropertyValues(argument1);
		OntResource argument1i = null;
		while (ni.hasNext()) {
			argument1i = (OntResource) ni.next();
		}
		return argument1i;
	}

	OntResource getargu2() {
		Property argument2 = m.getProperty(CapabilityModel.modelURI + "argument2");
		NodeIterator ni = csi.listPropertyValues(argument2);
		OntResource argument2i = null;
		while (ni.hasNext()) {
			argument2i = (OntResource) ni.next();
		}
		return argument2i;
	}

	public void setPropertyPredicate(String property) {
		ObjectProperty hasPredicate = m.getObjectProperty(CapabilityModel.modelURI + "propertyPredicate");
		Individual propertyi = m.getIndividual(CapabilityModel.modelURI + property);
		csi.addProperty(hasPredicate, propertyi);
	}

	public void setArgument1(String argu1) {
		ObjectProperty hasargument1 = m.getObjectProperty(CapabilityModel.modelURI + "argument1");
		Individual argui1 = m.getIndividual(CapabilityModel.modelURI + argu1);
		csi.addProperty(hasargument1, argui1);
	}

	public void setArgument2(String argu2) {
		ObjectProperty hasargument2 = m.getObjectProperty(CapabilityModel.modelURI + "argument2");
		Individual argui2 = m.getIndividual(CapabilityModel.modelURI + argu2);
		csi.addProperty(hasargument2, argui2);
	}

	/**
	 * @author Lishuang ÷ÿ–¥equals∑Ω∑®
	 */
	public boolean equals(Object obj) {
		if (obj instanceof ContextState) {
			ContextState t = (ContextState) obj;
			return this.csname.equals(t.csname);
		}
		return super.equals(obj);
	}

	/**
	 * @author Lishuang
	 * @return
	 */
	OntResource getLabel() {
		Property label = m.getProperty(CapabilityModel.modelURI + "Label");
		NodeIterator ni = csi.listPropertyValues(label);
		OntResource label2i = null;
		while (ni.hasNext()) {
			label2i = (OntResource) ni.next();
		}
		return label2i;
	}
}
