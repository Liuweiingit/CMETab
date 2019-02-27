package org.wit.rpt.ui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.wit.rpt.model.CapabilityModel;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;

import edu.stanford.smi.protege.util.Tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SetCapabilityNaDialog extends JDialog {

	private static final String newName = null;
	private final JPanel contentPanel = new JPanel();
	private TreeNode root;
	private DefaultMutableTreeNode newNode;
	public static JTextField nametxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SetCapabilityNaDialog dialog = new SetCapabilityNaDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SetCapabilityNaDialog() {
		setBounds(100, 100, 392, 106);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{

			JPanel buttonPane = new JPanel();
			contentPanel.setLayout(null);
			JLabel lblNewLabel = new JLabel("name:");
			lblNewLabel.setBounds(10, 13, 30, 15);
			contentPanel.add(lblNewLabel);

			nametxt = new JTextField();
			nametxt.setBounds(50, 10, 316, 21);
			contentPanel.add(nametxt);
			nametxt.setColumns(10);

			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						System.out.println("新输入的名字：" + nametxt.getText());
						addActionInd();
						dispose();
					}
				});
				{
					JButton cancelButton = new JButton("Cancel");
					cancelButton.setActionCommand("Cancel");
					buttonPane.add(cancelButton);
					cancelButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent arg0) {
							setVisible(false);
						}
					});
				}
			}
		}
	}

	public void addActionInd() {
		// CMETab icc = new CMETab();
		// JTree tree=icc.getTree();
		// DefaultTreeModel model=icc.getModel();
		// DefaultMutableTreeNode currTreeNode = icc.getCurrNode();
		JTree tree = CMETab.getTree();
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode currTreeNode = CMETab.getCurrNode();
		String cname = currTreeNode.getUserObject().toString();
		System.out.println("当前节点名字：" + cname);
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(nametxt.getText());
		currTreeNode.add(newNode);
		model.nodeStructureChanged(currTreeNode);
		String newname = newNode.getUserObject().toString();
		CapabilityModel om = new CapabilityModel();
		/**
		 * by Lishuang 以下两句没什么作用，注释了
		 */
		// DefaultMutableTreeNode pn = (DefaultMutableTreeNode)
		// currTreeNode.getParent();
		// String pnn = pn.toString();
		OntClass oc = om.getModel().getOntClass(CapabilityModel.modelURI + cname);
		om.createActionInd(newname);
	}
}
