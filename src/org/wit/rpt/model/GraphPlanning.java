package org.wit.rpt.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Resource;

import Test.Proba;

public class GraphPlanning {
	private static ArrayList<ContextState> initialstates;
	private static ArrayList<ContextState> goalstates;
	private ArrayList agents;
	public static String modelURI= "http://www.owl-ontologies.com/AGVs.owl#";
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		CapabilityModel cm=new CapabilityModel();
		OntModel m=cm.getModel();
		initialstates = new ArrayList<ContextState>();
		ContextState s1 = new ContextState(cm.getModel(), "cart_in_cartPickup");
		ContextState s2 = new ContextState(cm.getModel(), "AGV_hasstate_free");
		ContextState s3 = new ContextState(cm.getModel(), "currentPath_hasstate_smooth");
//		ContextState s1 = new ContextState(cm.getModel(), "AGV_in_elevatorSensorArea");
//		ContextState s2 = new ContextState(cm.getModel(), "elevator_hasState_wait");
		initialstates.add(s1);
		initialstates.add(s2);
		initialstates.add(s3);

		goalstates = new ArrayList<ContextState>();
//		ContextState s11 = new ContextState(cm.getModel(), "elevator_hasState_free");
//		ContextState s13 = new ContextState(cm.getModel(), "AGVfloor_same_destinationfloor");
		ContextState s11 = new ContextState(cm.getModel(), "AGV_hasstate_free");
		ContextState s13 = new ContextState(cm.getModel(), "cartPosition_sameposition_destinationPosition");
		goalstates.add(s11);
		goalstates.add(s13);
		toStart(initialstates,goalstates);
	}
	public static void toStart(ArrayList<ContextState> initialstates, ArrayList<ContextState> goalstates) throws IOException{
		File f = new File("E://CapabilityModel//testfile.gv");
		//若文件存在，清空文件内容
		if (f.exists()) {
            f.delete();
        }
//		FileWriter f = new FileWriter("E://CapabilityModel//testfile.gv", false);
		OutputStream out = new FileOutputStream(f,true);
		//设置.gv文件的格式
		out.write(new String("").getBytes());
		out.write("digraph a{".getBytes());
		out.write('\r'); // \r\n表示换行
		out.write('\n'); 
		out.write("rankdir = LR;".getBytes());
		out.write('\r'); 
		out.write('\n'); 
		out.write("encoding=\"UTF-8\";".getBytes());
		out.write('\r'); 
		out.write('\n'); 
		String s="size="+"\"9.6,200\""+";";
		out.write(s.getBytes());
		out.write('\r'); 
		out.write('\n'); 
		out.write("compound=true;".getBytes());
		out.write('\r'); 
		out.write('\n'); 
		out.write("rank=same;".getBytes());
		out.write('\r'); 
		out.write('\n'); 
		
		ArrayList<Capability> calist=getNextCapability(initialstates);
		for(int i=0;i<calist.size();i++){
			System.out.println(calist.get(i).getLabel().getLocalName());}
		ArrayList<ContextState> inisList=getNextinitialstates(calist,initialstates);
		iniToGoal(inisList,calist,goalstates);
		//设置.gv文件的结束标志
		out.write("}".getBytes());
		out.close();
		Proba p = new Proba();
		 p.start2();
}
	//获取能力的OutConstraints，并加入初始状态列表中
	public static ArrayList<ContextState> getNextinitialstates(ArrayList<Capability> calist,ArrayList<ContextState> initialstates) throws IOException{
		CapabilityModel cm=new CapabilityModel();
		OntModel m=cm.getModel();
		ArrayList<ContextState> outlist = new ArrayList<ContextState>();
		for(int i=0;i<calist.size();i++){
			Capability ca=calist.get(i);
//			System.out.println(ca.name.toString());
			ArrayList outList=ca.getOutConstraints();
			File f = new File("E://CapabilityModel"+File.separator+"testfile.gv");
			//用FileOutputSteam包装文件，并设置文件可追加
			OutputStream out1 = new FileOutputStream(f,true);
			//遍历能力的OutConstraints
			for(int j=0;j<outList.size();j++){
				String a=outList.get(j).toString();
				ContextState outCon = new ContextState(m, a);
				String[] c={ca.getLabel().getLocalName()+"[shape = invtriange,color=\"blue\"]",ca.getLabel().getLocalName()+"->"+outCon.getLabel().getLocalName()};
				writeToFile(c);
	    		outlist.add(outCon);
	    		//判断初始状态是否存在与initialstates中，若不存在则加入
	    		if(!initialstates.contains(outCon)){
	    			initialstates.add(outCon);
	    		}
			}
		}
		return outlist;
	}
		//根据给定的初始状态集合，获取能力的InConstraints为该状态集合子集的能力，
		public static ArrayList<Capability> getNextCapability(List initialstates) throws IOException{
			CapabilityModel cm=new CapabilityModel();
			OntModel m=cm.getModel();
			OntClass Capability = cm.getModel().getOntClass(CapabilityModel.CapabilityURI);
			ArrayList<Capability> inlist1 = new ArrayList<Capability>();
			File f = new File("E://CapabilityModel"+File.separator+"testfile.gv");
			//用FileOutputSteam包装文件，并设置文件可追加
			OutputStream out2 = new FileOutputStream(f,true);
	//			//遍历能力
				for(Iterator p=Capability.listInstances();p.hasNext();){
					Individual ii = (Individual) p.next();
					String iin = ii.getLocalName();
					Capability ca=new Capability(m,iin);
					ArrayList inList=ca.getInConstraints();
					//遍历当前能力的前置条件
					for(int i=0;i<inList.size();i++)
					{
						String a=inList.get(i).toString();
		    			ContextState s = new ContextState(m, a);
		    			//判断初始状态集合中是否某个能力的所有InConstrains
						if(!initialstates.contains(s))
						{
							break;
						}
						else if(!inlist1.contains(ca))
						{
							inlist1.add(ca);
						}
						String[] c={ca.getLabel().getLocalName()+"[shape = invtriange,color=\"blue\"]",s.getLabel().getLocalName()+"->"+ca.getLabel().getLocalName()};
						writeToFile(c);
					}
				}
		return inlist1;	
		}
		//根据每一步给定的状态集合，获取能力的InConstraints为该状态集合子集的能力
		public static ArrayList<Capability> getNextCapability1(List initialstates,List calist) throws IOException{
			CapabilityModel cm=new CapabilityModel();
			OntModel m=cm.getModel();
			OntClass Capability = cm.getModel().getOntClass(CapabilityModel.CapabilityURI);
			ArrayList<Capability> inlist1 = new ArrayList<Capability>();
//				//遍历能力
				for(Iterator p=Capability.listInstances();p.hasNext();){
					Individual ii = (Individual) p.next();
					String iin = ii.getLocalName();
					Capability ca=new Capability(m,iin);
					ArrayList inList=ca.getInConstraints();
					//遍历当前能力的前置条件
					for(int i=0;i<inList.size();i++){
						String a=inList.get(i).toString();
		    			ContextState s = new ContextState(m, a);
		    			//判断初始状态集合中是否某个能力的所有InConstrains
						if(!initialstates.contains(s)){
							break;
						}
//							System.out.println(iin+"符合");
						else if(!inlist1.contains(ca)&&!calist.contains(ca)){
							inlist1.add(ca);
						}
					}
				} 
		return inlist1;	
	}
		//规划算法
		public static void iniToGoal(ArrayList<ContextState> ini,ArrayList<Capability> cap,ArrayList<ContextState> goalstates) throws IOException{
			CapabilityModel cm=new CapabilityModel();
			OntModel m=cm.getModel();
			ArrayList<Capability> calist1=getNextCapability1(ini,cap);
			File f = new File("E://CapabilityModel"+File.separator+"testfile.gv");
			OutputStream out2 = new FileOutputStream(f,true);
			for(int i=0;i<calist1.size();i++){
				System.out.println(calist1.get(i).getLabel().getLocalName()); 
				for(int j=0;j<calist1.get(i).getInConstraints().size();j++){
					String cap1=calist1.get(i).getInConstraints().get(j).toString();
					ContextState s = new ContextState(m, cap1);
					String[] c={calist1.get(i).getLabel().getLocalName()+"[shape = invtriange,color=\"blue\"]",s.getLabel().getLocalName()+"->"+calist1.get(i).getLabel().getLocalName()};
					writeToFile(c);
				}
			} 
			ArrayList<ContextState> inisList1=getNextinitialstates(calist1,ini);
//			for(int i=0;i<inisList1.size();i++){
//				System.out.println("s_"+i+":初始状态有"+inisList1.get(i).getName());
//			}
//			for(int j=0;j<initialstates.size();j++){
//				System.out.println("初始状态有_"+j+":"+initialstates.get(j).getName());}
			if(!goalstates.containsAll(inisList1)){
				iniToGoal(inisList1,calist1,goalstates);
			}
		}
		//将获取的States和Capability以.gv文件格式写入
		public static void writeToFile(String s[]) throws IOException{
			File f = new File("E://CapabilityModel"+File.separator+"testfile.gv");
			//用FileOutputSteam包装文件，并设置文件可追加
			OutputStream out = new FileOutputStream(f,true);
			for(int i =0; i<s.length; i++){
				out.write(s[i].getBytes()); //向文件中写入数据
				out.write(";".getBytes());
				out.write('\r'); // \r\n表示换行
				out.write('\n');
				
		}
			
		}
}
	    


