package org.wit.rpt.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import edu.stanford.smi.protege.widget.AbstractTabWidget;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import javax.swing.JInternalFrame;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.JTabbedPane;
import java.awt.ScrollPane;
import java.awt.Panel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.border.BevelBorder;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFormattedTextField;
import javax.swing.ImageIcon;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.jdesktop.swingx.decorator.Filter;
import org.wit.rpt.model.CapabilityModel;
import org.wit.rpt.model.ContextState;
import org.wit.rpt.model.GraphPlanning;
import org.wit.rpt.model.Capability;
import org.wit.rpt.model.Agent;
import org.wit.rpt.model.OntView;

import javax.swing.JScrollBar;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Canvas;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.CardLayout;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import javax.swing.JProgressBar;
import org.eclipse.wb.swing.FocusTraversalOnArray;

/*
 * 主界面类
 */
public class CMETab extends AbstractTabWidget {
	private static final DefaultTreeModel model = null;
	JTextField txtField;
	private JPanel contentPane;
	public static DefaultMutableTreeNode currTreeNode = null;
	public String cnname;
	public static JTree tree;
	private JTextField rename;
	OntModel m;
	JPanel panel_1;
	JPanel panel_0;
	static JList inclist;
	static JList actionlist;
	static JList outclist;
	static JList inilist;
	static JList goallist;
	public static DefaultListModel inc = new DefaultListModel();
	public static DefaultListModel outc = new DefaultListModel();
	public static DefaultListModel act = new DefaultListModel();
	public static DefaultListModel ini = new DefaultListModel();
	public static DefaultListModel goal = new DefaultListModel();
	JDialog dialog;
	InCSEditor dialog1;
	OntView otv;
	JTabbedPane tabbedPane;
	private JTextField clabel;
	public ComboBoxModel elements;
	private JTextField textField;
	
	public CMETab() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		//左侧面板
		JPanel panel_left = new JPanel();
		panel_left.setBorder(null);
		panel_left.setBackground(SystemColor.menu);
		add(panel_left);
		panel_left.setLayout(null);

		JScrollPane spTree = new JScrollPane();
		spTree.setBounds(10, 55, 227, 720);
		panel_left.add(spTree);
		
		otv = new OntView();
		tree = new JTree(otv.formOWLtoAll());
		tree.setFont(new Font("Gisha", Font.PLAIN, 12));
		tree.addTreeSelectionListener(new SelItem());
//		tree.addMouseListener(new EditAction());   
		spTree.setViewportView(tree);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(SystemColor.menu);
		toolBar.setBounds(0, 28, 217, 27);
		panel_left.add(toolBar);
		
		JButton btnAddagent = new JButton("+ Agent ");
		btnAddagent.setFont(new Font("Arial", Font.PLAIN, 12));
		btnAddagent.setBackground(SystemColor.menu);
		toolBar.add(btnAddagent);
		btnAddagent.addActionListener(new AddAgentInd());
		
		JButton btnAddaction = new JButton("+Capability");
		btnAddaction.setFont(new Font("Arial", Font.PLAIN, 12));
		btnAddaction.setBackground(SystemColor.menu);
		toolBar.add(btnAddaction);
		btnAddaction.addActionListener(new AddCapabilityInd());
		
		JButton btnDelete= new JButton(" Delete ");
		btnDelete.setFont(new Font("Arial", Font.PLAIN, 12));
		btnDelete.setBackground(SystemColor.menu);
		toolBar.add(btnDelete);
		//by Lishuang  恢复了删除节点功能
		btnDelete.addActionListener(new DelItem());
		
//		JButton btnRename = new JButton("Rename");
//		btnRename.setFont(new Font("Arial", Font.PLAIN, 12));
//		btnRename.setBackground(SystemColor.menu);
//		toolBar.add(btnRename);
//		btnRename.addActionListener(new Rename());
		//中间构件编辑面板
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(SystemColor.menu);
		tabbedPane.setToolTipText("Capability");
		tabbedPane.setBounds(246, 10, 247, 765);
		panel_left.add(tabbedPane);
		{	
		panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(SystemColor.menu);
		tabbedPane.addTab("ICEditor", null, panel_1, null);
			
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 32, 227, 688);
		panel_1.add(scrollPane_2);
			
		actionlist = new JList();
		actionlist.setFont(new Font("Arial", Font.PLAIN, 11));
		actionlist.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		actionlist.setBounds(10, 56, 232, 61);
		scrollPane_2.setViewportView(actionlist);
			
		JLabel lblIncludeCapabilities = new JLabel("include capabilities");
		lblIncludeCapabilities.setFont(new Font("Arial", Font.PLAIN, 12));
		lblIncludeCapabilities.setBounds(10, 12, 98, 15);
		panel_1.add(lblIncludeCapabilities);
			
		JButton btnaddaction = new JButton("+Capa");
		btnaddaction.setFont(new Font("Arial", Font.PLAIN, 12));
		btnaddaction.setBackground(SystemColor.menu);
		btnaddaction.setBounds(143, 8, 47, 19);
		panel_1.add(btnaddaction);
		btnaddaction.addActionListener(new SelectCapabilityInd());
			
		JButton btndelaction = new JButton("-Capa");
		btndelaction.setFont(new Font("Arial", Font.PLAIN, 12));
		btndelaction.setBackground(SystemColor.menu);
		btndelaction.setBounds(190, 8, 47, 19);
		panel_1.add(btndelaction);
		btndelaction.addActionListener(new DeleteCapabilityInd());
		}
		{
		panel_0 = new JPanel();
		panel_0.setBackground(SystemColor.menu);
		tabbedPane.addTab("CapaEditor", null, panel_0, null);
		panel_0.setLayout(null);
			
		JLabel lblLabel = new JLabel("lable");
		lblLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLabel.setBounds(10, 10, 36, 15);
		panel_0.add(lblLabel);
			
		clabel = new JTextField();
		clabel.setBounds(48, 7, 184, 21);
		panel_0.add(clabel);
		clabel.setColumns(10);
			
		JLabel lblpre = new JLabel("InConstraints:");
		lblpre.setFont(new Font("Arial", Font.PLAIN, 12));
		lblpre.setBounds(10, 54, 97, 15);
		panel_0.add(lblpre);
			
		JScrollPane scrollPpre = new JScrollPane();
		scrollPpre.setBounds(10, 75, 222, 304);
		panel_0.add(scrollPpre);
			
		inclist = new JList();
		inclist.setFont(new Font("Arial", Font.PLAIN, 11));
		inclist.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		inclist.setBounds(10, 75, 222, 261);
		scrollPpre.setViewportView(inclist);
			
		JLabel lbleff = new JLabel("OutConstraints:");
		lbleff.setFont(new Font("Arial", Font.PLAIN, 12));
		lbleff.setBounds(10, 410, 94, 15);
		panel_0.add(lbleff);
			
		JScrollPane scrollPeff = new JScrollPane();
		scrollPeff.setBounds(10, 436, 222, 284);
		panel_0.add(scrollPeff);
		
		outclist = new JList();
		outclist.setFont(new Font("Arial", Font.PLAIN, 11));
		outclist.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		outclist.setBounds(10, 463, 220, 257);
		scrollPeff.setViewportView(outclist);
			
		JButton addpre1 = new JButton("+InC");
		addpre1.setFont(new Font("Arial", Font.PLAIN, 12));
		addpre1.setBackground(SystemColor.menu);
		addpre1.setBounds(142, 50, 50, 23);
		panel_0.add(addpre1);
		addpre1.addActionListener(new AddInConstraints());
			
		JButton btndelete = new JButton("-InC");
		btndelete.setFont(new Font("Arial", Font.PLAIN, 12));
		btndelete.setBackground(SystemColor.menu);
		btndelete.setBounds(184, 50, 50, 23);
		panel_0.add(btndelete);
		btndelete.addActionListener(new DelInC());
			
				
		JButton addeff1 = new JButton("+OutC");
		addeff1.setFont(new Font("Arial", Font.PLAIN, 12));
		addeff1.setBackground(SystemColor.menu);
		addeff1.setBounds(142, 406, 48, 23);
		addeff1.setHorizontalAlignment(SwingConstants.LEFT);
		panel_0.add(addeff1);
		addeff1.addActionListener(new AddOutC());
				
		JButton btndelete1 = new JButton("-OutC");
		btndelete1.setFont(new Font("Arial", Font.PLAIN, 12));
		btndelete1.setBackground(SystemColor.menu);
		btndelete1.setBounds(184, 406, 47, 23);
		btndelete1.setHorizontalAlignment(SwingConstants.LEFT);
		panel_0.add(btndelete1);
		btndelete1.addActionListener(new DelOutC());
		}
		//右侧图规划面板
		JLabel TotalLabel = new JLabel("From IC capability model");
		TotalLabel.setFont(new Font("Arial", Font.BOLD, 12));
		TotalLabel.setBounds(10, 10, 168, 15);
		panel_left.add(TotalLabel);
	
		JPanel panel_right = new JPanel();
		panel_right.setBounds(507, 0, 913, 788);
		panel_left.add(panel_right);
		panel_right.setBackground(SystemColor.menu);
		panel_right.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		panel.setBounds(10, 10, 750, 360);
		panel_right.add(panel);
		panel.setLayout(null);
		
		JLabel l1 = new JLabel("Capability planning steps");
		l1.setFont(new Font("Arial", Font.PLAIN, 12));
		l1.setBounds(20, 10, 201, 15);
		panel.add(l1);
		
		JLabel l2 = new JLabel("Choose the available agents");
		l2.setFont(new Font("Arial", Font.PLAIN, 12));
		l2.setBounds(142, 37, 221, 15);
		panel.add(l2);
		
		JButton s1btn = new JButton("Step 1:");
		s1btn.setBackground(SystemColor.menu);
		s1btn.setFont(new Font("Arial", Font.PLAIN, 12));
		s1btn.setHorizontalAlignment(SwingConstants.CENTER);
		s1btn.setBounds(20, 35, 120, 25);
		panel.add(s1btn);
		
		JButton s2btn = new JButton("Step 2:");
		s2btn.setBackground(SystemColor.menu);
		s2btn.setFont(new Font("Arial", Font.PLAIN, 12));
		s2btn.setHorizontalAlignment(SwingConstants.CENTER);
		s2btn.setBounds(20, 97, 120, 25);
		panel.add(s2btn);
		
		JLabel l3 = new JLabel("Input initial and goal states ");
		l3.setFont(new Font("Arial", Font.PLAIN, 12));
		l3.setBounds(142, 99, 221, 15);
		panel.add(l3);
		
		
		JButton s3btn = new JButton("Step 3:Run");
		s3btn.setBackground(SystemColor.menu);
		s3btn.setFont(new Font("Arial", Font.PLAIN, 12));
		s3btn.setHorizontalAlignment(SwingConstants.CENTER);
		s3btn.setBounds(20, 302, 120, 25);
		panel.add(s3btn);
		s3btn.addActionListener(new graphPlanning());
		
		JLabel l4 = new JLabel("     Run  the  program    ");
		l4.setFont(new Font("Arial", Font.PLAIN, 12));
		l4.setBounds(142, 305, 221, 15);
		panel.add(l4);
		
		
//		JProgressBar progressBar = new JProgressBar();
//		progressBar.setBackground(SystemColor.menu);
//		progressBar.setForeground(Color.RED);
//		progressBar.setBounds(124, 306, 464, 15);
//		panel.add(progressBar);
		
//		JLabel l4 = new JLabel("Begin");
//		l4.setFont(new Font("Arial", Font.PLAIN, 12));
//		l4.setBounds(84, 304, 221, 15);
//		panel.add(l4);
//		
		JButton s4btn = new JButton("Step 4:Show");
		s4btn.setBackground(SystemColor.menu);
		s4btn.setFont(new Font("Arial", Font.PLAIN, 12));
		s4btn.setHorizontalAlignment(SwingConstants.CENTER);
		s4btn.setBounds(20, 335, 120, 25);
		panel.add(s4btn);
		s4btn.addActionListener(new showGraph());
		
		JLabel l5 = new JLabel(" Show the planning graph   ");
		l5.setFont(new Font("Arial", Font.PLAIN, 12));
		l5.setBounds(142, 338, 221, 15);
		panel.add(l5);

		textField = new JTextField();
		textField.setBounds(142, 65, 464, 21);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel ll = new JLabel("Initial states:");
		ll.setFont(new Font("Arial", Font.PLAIN, 12));
		ll.setBounds(20, 126, 93, 15);
		panel.add(ll);
		
		/**
		 * 添加"Initial states”的添加和删除按钮
		 */
		JButton btnaddstate = new JButton("+");
		btnaddstate.setFont(new Font("Arial", Font.PLAIN, 12));
		btnaddstate.setBackground(SystemColor.menu);
		btnaddstate.setBounds(210, 126, 40, 15);
		panel.add(btnaddstate);
		btnaddstate.addActionListener(new AddIniStates());

		JButton btndelstate = new JButton("-");
		btndelstate.setFont(new Font("Arial", Font.PLAIN, 12));
		btndelstate.setBackground(SystemColor.menu);
		btndelstate.setBounds(250, 126, 40, 15);
		panel.add(btndelstate);
		btndelstate.addActionListener(new DelIniStates());
		
		JLabel lblAvailableAgents = new JLabel("Available agents:");
		lblAvailableAgents.setFont(new Font("Arial", Font.PLAIN, 12));
		lblAvailableAgents.setBounds(20, 65, 109, 15);
		panel.add(lblAvailableAgents);
		
		JScrollPane sp1 = new JScrollPane();
		sp1.setBounds(20, 149, 282, 143);
		panel.add(sp1);
		
		inilist = new JList();
		inilist.setFont(new Font("Arial", Font.PLAIN, 11));
		inilist.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		sp1.setViewportView(inilist);
		
		JLabel rl = new JLabel("Goal states:");
		rl.setFont(new Font("Arial", Font.PLAIN, 12));
		rl.setBounds(326, 126, 93, 15);
		panel.add(rl);
		
		JButton btnaddgoal = new JButton("+");
		btnaddgoal.setFont(new Font("Arial", Font.PLAIN, 12));
		btnaddgoal.setBackground(SystemColor.menu);
		btnaddgoal.setBounds(516, 126, 40, 15);
		panel.add(btnaddgoal);
		btnaddgoal.addActionListener(new AddGoalStates());

		JButton btndelgoal = new JButton("-");
		btndelgoal.setFont(new Font("Arial", Font.PLAIN, 12));
		btndelgoal.setBackground(SystemColor.menu);
		btndelgoal.setBounds(556, 126, 40, 15);
		panel.add(btndelgoal);
		btndelgoal.addActionListener(new DelGoalStates());
		
		JScrollPane sp2 = new JScrollPane();
		sp2.setBounds(326, 149, 282, 143);
		panel.add(sp2);
		
		goallist = new JList();
		goallist.setFont(new Font("Arial", Font.PLAIN, 11));
		goallist.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		sp2.setViewportView(goallist);
		
//		JPanel panel_2 = new JPanel();
//		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
//		panel_2.setBackground(Color.WHITE);
//		panel_2.setBounds(29, 380, 588, 330);
//		panel_right.add(panel_2);
//	
//		JLabel lblShowPlanningGraph = new JLabel("ShowPlanningGraph");
//		lblShowPlanningGraph.setFont(new Font("Arial", Font.PLAIN, 12));
//		panel_2.add(lblShowPlanningGraph);
//		panel_2.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblShowPlanningGraph}));
		
	}
	
	         
	/**
	 * Initialize the contents of the frame.
	 */
	class SelItem implements TreeSelectionListener{  
	    public void valueChanged(TreeSelectionEvent e){	      
	      //清空列表
	      removePreList();
	      removeEffList();
	      removeActionList();   
	      OntModel m = otv.getOntModel();
	      currTreeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	      String paName = currTreeNode.getParent().toString();
	      if(paName.equalsIgnoreCase("Capability")){
	    	tabbedPane.setSelectedIndex(1);
	    	Capability a = new Capability(m,currTreeNode.toString());
	    	clabel.setText(currTreeNode.toString());
	    	ArrayList prelist1 = a.getInConstraints();
	    	addInCList(prelist1);
	    	ArrayList efflist1 = a.getOutConstraints();
	    	addOutCList(efflist1);
	      }else if (isAgent(m,paName)){
//	    	System.out.println("Agent:CurrentNode:"+currTreeNode.toString());
	    	tabbedPane.setSelectedIndex(0); 
	    	Agent ag = new Agent(m,currTreeNode.toString());
	    	ArrayList actList = ag.getCapabilities();
	    	addActList(actList);	
	      }
}}	
	
	//执行增加Agent实例
	class AddAgentInd implements ActionListener{
	    public void actionPerformed(ActionEvent e){
			String name = currTreeNode.getUserObject().toString();
			if (name.equalsIgnoreCase("AGV") || name.equalsIgnoreCase("CartSensor") || name.equalsIgnoreCase("CartElevator")||name.equalsIgnoreCase("DecisionAgent")) {
				SetAgentNaDialog dialog = new SetAgentNaDialog();
				dialog.setTitle("Create a new agent instance");
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setResizable(false);
				dialog.setVisible(true);
			} else {
				CreateTip dialog = new CreateTip();
				dialog.setLable("Please choose one of \"Agent\" node.");
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setResizable(false);
				dialog.setVisible(true);
			}
	 }
	}
	
	//执行增加Capability实例
	class AddCapabilityInd implements ActionListener{
		   public void actionPerformed(ActionEvent e){
			   String cname = currTreeNode.getUserObject().toString();
			if(cname.equalsIgnoreCase("Capability")){
				 SetCapabilityNaDialog dialog = new SetCapabilityNaDialog();
				 dialog.setTitle("Create a new Capability instance");
				 dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				 dialog.setResizable(false);
				 dialog.setVisible(true);
			}else{
				 CreateTip dialog = new CreateTip();
				 dialog.setLable("Please choose \"Capability\" node.");
				 dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				 dialog.setResizable(false);
				 dialog.setVisible(true);
			 }
		   }
	}
	
	class SelectCapabilityInd implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			SCapabilityDialog dialog = new SCapabilityDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setResizable(false);
			dialog.setVisible(true);
		}
	
	}

	 /**
	    * 选中一项某个Agent的action，点击“-capa”，该项便从列表中删除,同时改变本体中相应内容。
	    */
	class DeleteCapabilityInd implements ActionListener{
		   public void actionPerformed(ActionEvent e){
				int cai = actionlist.getSelectedIndex();
				String aname = act.get(cai).toString();
				System.out.println(aname);
				String agent=currTreeNode.toString();
				System.out.println(agent);
				act.remove(cai);
				actionlist.setModel(act);
					try {
						Agent.deleteCapability(agent, aname);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				try {
					CapabilityModel.saveModel();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		   }
	}
		   
	//执行删除节点的操作
	class DelItem implements ActionListener{
		   public void actionPerformed(ActionEvent e){
			   DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
			   DefaultMutableTreeNode currNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			     String ctnn= currNode.toString();
			     if(currNode.isLeaf())
			     {	 
			    	 CapabilityModel om = new CapabilityModel();
			    	 DefaultMutableTreeNode pn = (DefaultMutableTreeNode) currNode.getParent();
				     String pnn = pn.toString();
				     OntClass oc = om.getModel().getOntClass(CapabilityModel.modelURI+pnn);
				     om.delteInd(oc, ctnn);
				     model.removeNodeFromParent(currNode);
			     } 
		   }	   
	}
	//执行重命名的操作
	class Rename implements ActionListener{
	    public void actionPerformed(ActionEvent e){
	    	String q = JOptionPane.showInputDialog(null,
	                "input a newname");
	    	DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
	    	String ctnn=currTreeNode.toString();
	    	tree.setEditable(true);
	    	String newName=q.toString();
	    	TreePath path = tree.getSelectionPath(); 
            currTreeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            currTreeNode.setUserObject(newName);
            model.valueForPathChanged(path, q);
            //重新命名OWL文件中的实例名比较复杂,暂不实现.
	    }
	  
	}   
	
	/**
	 * 获取输入的初始状态和目标状态作为图规划的输入,并执行图规划过程
	 */
	class graphPlanning implements ActionListener{
	    public void actionPerformed(ActionEvent e){
	    	try {
				FileWriter fileWriter = new FileWriter("E://CapabilityModel//testfile.gv", false);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} 
	    	CapabilityModel cm=new CapabilityModel();
			OntModel m=cm.getModel();
	    	ListModel listmodel1=CMETab.inilist.getModel();
	    	ArrayList<ContextState> initialstates=new ArrayList<ContextState>();
	    	for(int i=0;i<inilist.getLastVisibleIndex()+1;i++){
	    		System.out.println(listmodel1.getElementAt(i).toString());
	    		ContextState s = new ContextState(m, listmodel1.getElementAt(i).toString());
	    		initialstates.add(s);
	    	}
	    	ListModel listmodel2=CMETab.goallist.getModel();
	    	ArrayList<ContextState> goalstates=new ArrayList<ContextState>();
	    	for(int i=0;i<goallist.getLastVisibleIndex()+1;i++){
	    		System.out.println(listmodel2.getElementAt(i).toString());
	    		ContextState s1 = new ContextState(cm.getModel(),listmodel2.getElementAt(i).toString());
	    		goalstates.add(s1);
	    	}
			try {
				GraphPlanning.toStart(initialstates, goalstates);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }

	}
	//显示规划图
	class showGraph implements ActionListener{
	    public void actionPerformed(ActionEvent e){
			ShowImg img = new ShowImg();
//			img.open();
//	    	Graphics g = getGraphics();
//	    	String name = "E:/CapabilityModel/out.gif";
//            Image img = Toolkit.getDefaultToolkit().getImage(name);
//            g.drawImage(img, 538, 408,585,305, null);
//            g.dispose();
	    }
	}

	//添加InConstraints
	class AddInConstraints implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dialog = new OutCSEditor();
			dialog.setTitle("Inconstraints editor");
			dialog.setBounds(100, 100, 570, 300);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
	    } 
	}
	
	//添加initialstates
	class AddIniStates implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dialog = new IniStates();
			dialog.setTitle("InitialStates editor");
			dialog.setBounds(100, 100, 570, 300);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
	    } 
	}
	//添加目标状态
	class AddGoalStates implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dialog = new GoalStates();
			dialog.setTitle("GoalStates editor");
			dialog.setBounds(100, 100, 570, 300);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
	    } 
	}
	
	//删除initialstates和goalstates
	class DelIniStates implements ActionListener{
		   public void actionPerformed(ActionEvent e){
			int si = inilist.getSelectedIndex();
			String siname = ini.get(si).toString();
			ini.remove(si);
			inilist.setModel(ini);
		   }
	}
	class DelGoalStates implements ActionListener{
		   public void actionPerformed(ActionEvent e){
			int si = goallist.getSelectedIndex();
			String siname = goal.get(si).toString();
			goal.remove(si);
			goallist.setModel(goal);
		   }
	}
	//删除InConstraint中state实例
	class DelInC implements ActionListener{
		   public void actionPerformed(ActionEvent e){
			int si = inclist.getSelectedIndex();
			String siname = inc.get(si).toString();
			String ain=currTreeNode.toString();
			CapabilityModel.deleteInConstraints(ain, siname);
			inc.remove(si);
			inclist.setModel(inc);
			try {
				CapabilityModel.saveModel();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		   }
	}
	//添加OutConstraint
	class AddOutC implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dialog1 = new InCSEditor();
			dialog1.setTitle("Outconstraints editor");
			dialog1.setBounds(100, 100, 570, 300);
			dialog1.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog1.setVisible(true);
	    } 
	}
	//删除OutConstraint中state实例
	class DelOutC implements ActionListener{
		   public void actionPerformed(ActionEvent e){
			int si = outclist.getSelectedIndex();
			String siname = outc.get(si).toString();
			String ain=currTreeNode.toString();
			CapabilityModel.deleteOutConstraints(ain, siname);
			outc.remove(si);
			outclist.setModel(outc);
			try {
				CapabilityModel.saveModel();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		   }
	}


    public void initialize() {
		
		setLabel("CMETab");
		
	}
    
    public static void main(String[] args) {
		edu.stanford.smi.protege.Application.main(args);
	}
    
    
    public static void addInCList(ArrayList al){
      if(!inc.contains(al)){
        for(int i=0;i<al.size();i++){
  		  inc.addElement(al.get(i)); 
        }
		  inclist.setModel(inc);
      }
	}
	public static void addOutCList(ArrayList al1){
	  if(!outc.contains(al1)){
       for(int i=0;i<al1.size();i++){
      	  outc.addElement(al1.get(i));
       }
		  outclist.setModel(outc);
	  }
	}
	
	public static void addActList(ArrayList al2){
	  if(!act.contains(al2)){
        for(int i=0;i<al2.size();i++){
        	act.addElement(al2.get(i));
        }
		  actionlist.setModel(act);
	  }
	}
	
	public static void addInCList(String s){
	   if(!inc.contains(s)){
		  inc.addElement(s);
		  inclist.setModel(inc);
	  }
	}
	
	public static void addOutCList(String s){
	   if(!outc.contains(s)){
		  outc.addElement(s);
		  outclist.setModel(outc);
		}

	}
	
	public static void addIniList(ArrayList al1){
		  if(!ini.contains(al1)){
	       for(int i=0;i<al1.size();i++){
	      	  ini.addElement(al1.get(i));
	       }
			  inilist.setModel(ini);
		  }
		}
		
		public static void addGoalList(ArrayList al2){
		  if(!goal.contains(al2)){
	        for(int i=0;i<al2.size();i++){
	        	goal.addElement(al2.get(i));
	        }
			  goallist.setModel(goal);
		  }
		}
		
	public static void addIniStatesList(String s){
		   if(!ini.contains(s)){
			  ini.addElement(s);
			  inilist.setModel(ini);
		  }
		}
	
	public static void addGoalStatesList(String s){
		   if(!goal.contains(s)){
			  goal.addElement(s);
			  goallist.setModel(goal);
		  }
		}
	public static void addActList(String s){
		if(!act.contains(s)){
		   act.addElement(s);
		   actionlist.setModel(act);
		}

	}
	public void removePreList(){
		  inc.removeAllElements();
		  inclist.setModel(inc);
	}	
	public void removeEffList(){
		  outc.removeAllElements();
		  outclist.setModel(outc);
	}	
	/**
	 * @author li
	 */
	public void removeIniList(){
		  ini.removeAllElements();
		  inilist.setModel(ini);
	}	
	public void removeGoalList(){
		  goal.removeAllElements();
		  goallist.setModel(goal);
	}
	
	
	public void removeActionList(){
		  act.removeAllElements();
		  actionlist.setModel(act);
	}
	
	public boolean isAgent(OntModel m, String cnname){
		  boolean b = false;
		  OntClass agent = m.getOntClass(CapabilityModel.AgentURI);
		  for(ExtendedIterator ei = agent.listSubClasses(true);ei.hasNext();){
			  OntClass oc = (OntClass) ei.next();	
			  String ocn = oc.getLocalName();
			  if(cnname.equalsIgnoreCase(ocn)){
				  b = true;
			  }	  
		  }
		  return b;
		 
	}
		
	public static JTree getTree(){
		return tree;
	}
	
	public static DefaultMutableTreeNode getCurrNode(){
		return currTreeNode;
	}
}
