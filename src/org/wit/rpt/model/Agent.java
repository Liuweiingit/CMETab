package org.wit.rpt.model;

import java.io.IOException;
import java.util.ArrayList;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class Agent {
	String type;
	String name;
	static OntModel m;
	Property aha;
	/**
	 * @author Lishuang
	 */
	public static String modelURI = "http://www.owl-ontologies.com/Capability.owl#";
	public static String AgentURI = modelURI + "Agent";
	public static String AHCURI = modelURI + "agent-has-capability";
	public static String AGVURI = modelURI + "AGV";
	public static String CapabilityURI = modelURI + "Capability";
	public static String PredicateURI = modelURI + "Predicate";
	public static String ContextStateURI = modelURI + "ContextState";

	public Agent(OntModel om, String aname) {
		m = om;
		name = aname;

	}

	public ArrayList getCapabilities() {
		ArrayList capabilitylist = new ArrayList();
		Individual aname = m.getIndividual(CapabilityModel.modelURI + name);
		aha = m.getProperty(CapabilityModel.AHCURI);
		for (NodeIterator as = m.listObjectsOfProperty(aname, aha); as.hasNext();) {
			Resource r = (Resource) as.next();
			capabilitylist.add(r.getLocalName());
			// System.out.println(r.getLocalName());
		}
		return capabilitylist;
	}

	/**
	 * ��ĳ��Agent��������󱣴浽����
	 */
	public void addasCapability(String agentname, String actionname) throws IOException {
		Individual agenti = m.getIndividual(modelURI + agentname);
		Individual aci = m.getIndividual(modelURI + actionname);
		ObjectProperty hasCapability = m.getObjectProperty(AHCURI);
		agenti.addProperty(hasCapability, aci);
		// saveModel();
	}

	/**
	 * ɾ��Agent��ĳ������
	 */
	public static void deleteCapability(String agentname, String actionname) throws IOException {
		Individual agenti = m.getIndividual(modelURI + agentname);
		Individual aci = m.getIndividual(modelURI + actionname);
		ObjectProperty hasCapability = m.getObjectProperty(AHCURI);
		agenti.removeProperty(hasCapability, aci);
	}

}