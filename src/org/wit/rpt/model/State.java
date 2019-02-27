package org.wit.rpt.model;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import edu.stanford.smi.protegex.owl.swrl.model.SWRLClassAtom;
import edu.stanford.smi.protegex.owl.swrl.model.impl.DefaultSWRLClassAtom;

public class State {
	String sname;
	Individual property;
	Individual predicate;
	Individual value;
	Resource state;
	String stateURI;
	OntModel m;

	State(OntModel om, String name) {
		m = om;
		sname = name;
	}

	String getName() {
		return sname;
	}

	Individual getProperty() {
		return property;
	}

	Individual getpredicate() {
		return predicate;
	}

	Individual getValue() {
		return value;
	}

	String getType() {
		Individual state = m.getIndividual(CapabilityModel.modelURI + sname);
		OntClass oc = state.getOntClass();
		String type = oc.getLocalName();
		return type;
	}

	boolean semanticEqualwith(OntClass oc, OntClass oc1) {
		for (ExtendedIterator ei = oc.listEquivalentClasses(); ei.hasNext();) {
			OntClass oct = (OntClass) ei.next();
			String octname = oct.getLocalName();
		}
		return false;
	}

	boolean semanticEqualwith(State s1) {
		String stype = this.getType();
		String stype1 = s1.getType();
		if (stype.equalsIgnoreCase(stype1)) {
			if (this.getType().equalsIgnoreCase("ClassAtom")) {
				Individual pro = this.getProperty();
				OntClass oc = pro.getOntClass();
				Individual pro1 = s1.getProperty();
				OntClass oc1 = pro1.getOntClass();
			}

		}
		return false;
	}

	boolean semanticConflictwith(State s1) {
		return false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Condition c = new Condition();
		System.out.println("hello");

	}

}
