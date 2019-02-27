package Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import com.hp.hpl.jena.ontology.AllValuesFromRestriction;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.Restriction;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class TestOWL {
	    static String fp = "file:E://model//OntoExam.owl";
	    static String fp1 = "E:\\model\\OntoExam.owl";
	    static OntModel m;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String modelURI = "http://www.owl-ontologies.com/OntologyExam.owl#"; 
		m= ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		m.read(fp);	
		
		System.out.println("before:");	
		simpleReadOntology(m);
		OntClass animal = m.getOntClass(modelURI+"Animal");
	    m.createClass(modelURI+"Carnivore");
	    System.out.println("\t");
	    System.out.println("after:");
	    simpleReadOntology(m);
	    System.out.println("\t");
	    
	    OntClass carnivore = m.getOntClass(modelURI+"Carnivore");
	    animal.addSubClass(carnivore);
	    Individual tiger = carnivore.createIndividual(modelURI+"tiger");
	    Individual lion = carnivore.createIndividual(modelURI+"lion");
    	System.out.print("instance of Carnivore:");
	    for(ExtendedIterator ei = carnivore.listInstances();ei.hasNext();){
	    	Individual i = (Individual) ei.next();
	    	System.out.print(i.getLocalName().toString()+" ");
	    }
	    System.out.println("\t");
	    m.createClass(modelURI+"Region");
	    OntClass region = m.getOntClass(modelURI+"Region");
	    Individual asia = region.createIndividual(modelURI+"asia");
	
	    ObjectProperty hasregion = m.createObjectProperty(modelURI+"hasRegion");
	    hasregion.setDomain(carnivore);
	    hasregion.setRange(region);
	    tiger.setPropertyValue(hasregion, asia);
	    
	    for(NodeIterator ni = m.listObjectsOfProperty(tiger, hasregion);ni.hasNext();){
	    	RDFNode i = ni.nextNode();
	    	System.out.println("tiger hasRegion:"+i.toString());
	    }
	    
	    System.out.println("\t");
	    OntClass leaf = m.getOntClass( modelURI + "Leaf" );
	    for (Iterator<OntClass> i = leaf.listSuperClasses(true); i.hasNext(); ) {
	      OntClass c = i.next();
	      if (c.isRestriction()) {
	        Restriction r = c.asRestriction();
	        if (r.isAllValuesFromRestriction()) {
	          AllValuesFromRestriction av = r.asAllValuesFromRestriction();
	          System.out.println( "AllValuesFrom class " +
	                              av.getAllValuesFrom().getLocalName() +
	                              " on property " + av.getOnProperty().getLocalName());
	        }
	      }
	    }
	    try {
			saveModel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	public static void simpleReadOntology(OntModel model) {
	    for(Iterator i=model.listClasses();i.hasNext();){
	    	OntClass c = (OntClass) i.next();
	    	if (!c.isAnon()) { // 如果不是匿名类，则打印类的名字
	    		System.out.print(c.getLocalName().toString()+" ");
	    }
	    }
	}
	
    public static void saveModel() throws IOException{
    	File f = new File(fp1);
    	FileOutputStream file = new FileOutputStream(f);
        m.write(file, "RDF/XML-ABBREV");
        file.close();
    }

}
