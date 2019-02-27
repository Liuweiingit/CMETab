package org.wit.rpt.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class Capability {
	// String rdfsURI= "http://www.w3.org/2000/01/rdf-schema#";
	String name;
	static OntModel m;
	Property ahp;
	Property ahe;
	Property label;
	private Individual csi;

	public Capability(OntModel om, String sname) {
		m = om;
		name = sname;
		csi = m.getIndividual(CapabilityModel.modelURI + name);

	}

	/**
	 * by Lishuang
	 */
	public OntResource getLabel() {
		Property label = m.getProperty(CapabilityModel.modelURI + "Label");
		NodeIterator ni = csi.listPropertyValues(label);
		OntResource label2i = null;
		while (ni.hasNext()) {
			label2i = (OntResource) ni.next();
		}
		return label2i;
	}

	public ArrayList getInConstraints() {
		ArrayList InConstraints = new ArrayList();
		Individual aname = m.getIndividual(CapabilityModel.modelURI + name);
		System.out.println(aname.toString());
		ahp = m.getProperty(CapabilityModel.modelURI + "hasInConstraints");
		for (NodeIterator pcs = m.listObjectsOfProperty(aname, ahp); pcs.hasNext();) {
			Resource r = (Resource) pcs.next();
			InConstraints.add(r.getLocalName());
			// System.out.println(r.getLocalName());
		}
		return InConstraints;
	}

	public ArrayList getOutConstraints() {
		ArrayList OutConstraints = new ArrayList();
		Individual aname = m.getIndividual(CapabilityModel.modelURI + name);
		ahe = m.getProperty(CapabilityModel.modelURI + "hasOutConstraints");
		for (NodeIterator pcs = m.listObjectsOfProperty(aname, ahe); pcs.hasNext();) {
			Resource r = (Resource) pcs.next();
			OutConstraints.add(r.getLocalName());
			// System.out.println(r.getLocalName());
		}
		return OutConstraints;
	}

	public void setInConstraints(ArrayList InConstraints) {

	}

	public void setOutConstraints(ArrayList OutConstraints) {

	}

	public static void main(String[] args) {
		// Capability c=new Capability(m,"deliverCall");
		// System.out.println(c.getCapabilitys());
	}

	/**
	 * @author Lishuang ÷ÿ–¥equals∑Ω∑®
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Capability) {
			Capability t = (Capability) obj;
			return this.name.equals(t.name);
		}
		return super.equals(obj);
	}

}