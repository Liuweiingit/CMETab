package org.wit.rpt.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JDialog;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.wit.rpt.ui.CMETab;
import org.wit.rpt.ui.SetCapabilityNaDialog;
import org.wit.rpt.ui.Tip;

import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.writer.rdfxml.rdfwriter.OWLModelWriter;

import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.util.iterator.Filter;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;

public class CapabilityModel {
	static OntModel m;
	String fp = "file:E://CapabilityModel//Capability.owl";
	static String fp1 = "E:\\CapabilityModel\\Capability.owl";
	public static String modelURI = "http://www.owl-ontologies.com/AGVs.owl#";
	public static String AgentURI = modelURI + "Agent";
	public static String AHCURI = modelURI + "agent-has-capability";
	public static String AGVURI = modelURI + "AGV";
	public static String CapabilityURI = modelURI + "Capability";
	public static String PredicateURI = modelURI + "Predicate";
	public static String ContextStateURI = modelURI + "ContextState";

	public static void main(String[] args) {
		CapabilityModel cm = new CapabilityModel();
		ContextState cs1 = new ContextState(cm.getModel(), "AGVPosition_sameposition_destinationPosition");
		ContextState csed1 = new ContextState(cm.getModel(), "AGVPosition_sameposition_destinationPosition");
		ContextState cs = new ContextState(cm.getModel(), "cartPosition_is_ready");
		ContextState csed = new ContextState(cm.getModel(), "cartInfo_is_ready");
		List<ContextState> list1 = new ArrayList<ContextState>();
		list1.add(cs);
		list1.add(cs1);
		Iterator<ContextState> iter = list1.iterator();
		List<ContextState> list2 = new ArrayList<ContextState>();
		list2.add(csed);
		list2.add(csed1);
		Iterator<ContextState> iter1 = list2.iterator();
		if (cm.partMatchwith(iter, iter1)) {
			System.out.println("两个集合包含匹配");
		} else {
			System.out.println("两个集合非包含匹配");
		}
		if (cm.completeMatchwith(iter, iter1)) {
			System.out.println("两个集合完全匹配");
		} else {
			System.out.println("两个集合非完全匹配");
		}
	}

	public CapabilityModel() {
		m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		m.read(fp);
	}

	public static OntModel getModel() {
		return m;
	}

	public static void saveModel() throws IOException {
		File f = new File(fp1);
		FileOutputStream file = new FileOutputStream(f);
		m.write(file, "RDF/XML-ABBREV");
		file.close();
	}

	// ContextState的两个实例cs和csed，如果property和propertyed相同，
	// argu1和argu1ed对应的类等价，且argu2和argu2ed对应的等价，则返回true；否则返回false。
	// 如<AGVLocation,sameposition,destinationPosition><AGVPosition_sameposition_destinationPosition>
	public static boolean completeMatchwith(ContextState cs, ContextState csed) {
		boolean result = false;
		OntResource property = cs.getPredicate();
		OntResource propertyed = csed.getPredicate();
		OntResource argu1 = cs.getargu1();
		OntResource argu1ed = csed.getargu1();
		OntClass oc1 = argu1.asIndividual().getOntClass();
		OntClass oc1ed = argu1ed.asIndividual().getOntClass();
		OntResource argu2 = cs.getargu2();
		OntResource argu2ed = csed.getargu2();
		OntClass oc2 = argu2.asIndividual().getOntClass();
		OntClass oc2ed = argu2ed.asIndividual().getOntClass();
		// System.out.println(property.getLocalName().matches(propertyed.getLocalName()));
		// System.out.println(compareOntClasses(oc1,oc1ed));
		// System.out.println(compareOntClasses(oc2,oc2ed));
		if (property.getLocalName().matches(propertyed.getLocalName()) && compareOntClasses(oc1, oc1ed)
				&& compareOntClasses(oc2, oc2ed)) {
			// if(property.toString().equalsIgnoreCase(propertyed.toString())&&oc1.toString().equalsIgnoreCase(oc1ed.toString())&&oc2.toString().equalsIgnoreCase(oc2ed.toString())){
			result = true;
		}
		return result;

	}

	// ContextState的两个实例cs和csed，如果property和propertyed相同，argu2和argu2ed对应的等价
	// 且argu1对应的类语义包含于argu1ed对应的类（如Cart_position《Cart_info），则返回true；否则返回false。
	// 还有一些表示范围的语义包含，这里没有考虑
	public static boolean partMatchwith(ContextState cs, ContextState csed) {
		boolean result = false;
		OntResource property = cs.getPredicate();
		OntResource propertyed = csed.getPredicate();
		OntResource argu1 = cs.getargu1();
		OntResource argu1ed = csed.getargu1();
		OntClass oc1 = argu1.asIndividual().getOntClass();
		OntClass oc1ed = argu1ed.asIndividual().getOntClass();
		OntResource argu2 = cs.getargu2();
		OntResource argu2ed = csed.getargu2();
		OntClass oc2 = argu2.asIndividual().getOntClass();
		OntClass oc2ed = argu2ed.asIndividual().getOntClass();
		// System.out.println(property.getLocalName().matches(propertyed.getLocalName()));
		// System.out.println(compareOntClasses(oc2,oc2ed));
		// System.out.println(isSuperClass(oc1, oc1ed));
		if (property.getLocalName().matches(propertyed.getLocalName()) && compareOntClasses(oc2, oc2ed)
				&& isSuperClass(oc1, oc1ed)) {
			result = true;
		}
		return result;
	}

	// 两个OntClass语义相等或者名称一致，都被认为是等价的
	static boolean compareOntClasses(OntClass oc1, OntClass oc2) {
		boolean r = false;
		if (oc1.hasEquivalentClass(oc2) || oc1.getLocalName().matches(oc2.getLocalName())) {
			r = true;
		}
		return r;
	}

	// 判断oc2是否是oc1的父类
	static boolean isSuperClass(OntClass oc1, OntClass oc2) {
		boolean r = false;
		ExtendedIterator spcIterator = oc1.listSuperClasses();
		while (spcIterator.hasNext()) {
			OntClass oc = (OntClass) spcIterator.next();
			if (oc.getLocalName().matches(oc2.getLocalName()))
				r = true;
		}
		return r;

	}

	// 暂不实现
	public boolean disjointwith(ContextState cs1) {
		return false;

	}

	/**
	 * by lishuang
	 */
	// 判断两个ContextState组的语义等价关系，
	// 如果csi和csied中的每一个ContextStates都语义等价，则csi和csied语义等价
	public static boolean completeMatchwith(Iterator<ContextState> csi, Iterator<ContextState> csied) {
		boolean r = false;
		while (csi.hasNext()) {
			ContextState str = csi.next();
			while (csied.hasNext()) {
				ContextState str1 = csied.next();
				if (!completeMatchwith(str, str1)) {
					r = false;
					return r;
				}
				break;
			}
		}
		return r;
	}

	/**
	 * by lishuang
	 */
	// 判断两个ContextState组的语义包含关系，如果csi和csied中的ContextState至少存在一对语义包含关系（包含的方向一致），
	// 则csi和csied语义包含
	public static boolean partMatchwith(Iterator<ContextState> csi, Iterator<ContextState> csied) {
		boolean r = false;
		while (csi.hasNext()) {
			ContextState str = csi.next();
			while (csied.hasNext()) {
				ContextState str1 = csied.next();
				if (partMatchwith(str, str1)) {
					r = true;
					return r;
				}
				break;
			}
		}
		return r;
	}

	// 将新增的Agent具体累的实例，添加重复名称时，owl文件不会重复添加
	// oc是实例的类，in是实例的名称
	public void createAgentInd(OntClass oc, String in) {
		String ins = modelURI + in;
		Individual newiofagent = oc.createIndividual(ins);
		try {
			saveModel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ContextState createContextState(String csname, String property, String argu1, String argu2) {
		OntClass oc = m.getOntClass(CapabilityModel.ContextStateURI);
		Individual csi = oc.createIndividual(CapabilityModel.modelURI + csname);
		ContextState cs = new ContextState(m, csname);
		cs.setPropertyPredicate(property);
		cs.setArgument1(argu1);
		cs.setArgument2(argu2);
		try {
			saveModel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cs;
	}

	public void createStatusAtom(String name, String property, String argu1) {
		try {
			saveModel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 将新增的action类写入owl文件, an是action名
	public void createActionInd(String an) {
		String ans = modelURI + an;
		OntClass oca = m.getOntClass(CapabilityURI);
		Individual newiofaction = oca.createIndividual(ans);
		try {
			saveModel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delteInd(OntClass oc, String in) {
		String inn = modelURI + in;
		Individual ii = m.getIndividual(inn);
		oc.dropIndividual(ii);
		Tip tipdialog = new Tip();
		tipdialog.setTitle("Tips");
		tipdialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		tipdialog.setResizable(false);
		tipdialog.setVisible(true);
	}

	public void addasInConstraints(String iname, String aname) throws IOException {
		Individual actioni = m.getIndividual(modelURI + iname);
		Individual atomi = m.getIndividual(modelURI + aname);
		ObjectProperty hasInConstraints = m.getObjectProperty(modelURI + "hasInConstraints");
		actioni.addProperty(hasInConstraints, atomi);
		// saveModel();
	}

	public void addasOutConstraints(String iname, String aname) throws IOException {
		Individual actioni = m.getIndividual(modelURI + iname);
		Individual atomi = m.getIndividual(modelURI + aname);
		ObjectProperty hasOutConstraints = m.getObjectProperty(modelURI + "hasOutConstraints");
		actioni.addProperty(hasOutConstraints, atomi);
		// saveModel();
	}

	public static void deleteInConstraints(String iname, String aname) {
		Individual actioni = m.getIndividual(modelURI + iname);
		Individual atomi = m.getIndividual(modelURI + aname);
		ObjectProperty hasInConstraints = m.getObjectProperty(modelURI + "hasInConstraints");
		actioni.removeProperty(hasInConstraints, atomi);
	}

	public static void deleteOutConstraints(String iname, String aname) {
		Individual actioni = m.getIndividual(modelURI + iname);
		Individual atomi = m.getIndividual(modelURI + aname);
		ObjectProperty hasOutConstraints = m.getObjectProperty(modelURI + "hasOutConstraints");
		actioni.removeProperty(hasOutConstraints, atomi);
	}

	boolean hasIndividual(OntClass oc) {
		boolean r = false;
		for (Iterator k = oc.listInstances(); k.hasNext();) {

		}
		return false;
	}

	public static void simpleReadOntology(OntModel model) {
		for (Iterator i = model.listClasses(); i.hasNext();) {
			OntClass c = (OntClass) i.next();
			if (!c.isAnon()) { // 如果不是匿名类，则打印类的名字
				System.out.println(c.getLocalName().toString());
			}
		}
	}

}
