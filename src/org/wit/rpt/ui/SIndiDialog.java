package org.wit.rpt.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.PopupMenu;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JTree;
import javax.swing.JLabel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.border.EtchedBorder;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.wit.rpt.model.CapabilityModel;
import org.wit.rpt.model.OntView;
import org.wit.rpt.ui.CMETab.SelItem;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import java.awt.event.ActionEvent;

public class SIndiDialog extends JDialog {
	private static final Color lightBlue = new Color(153, 204, 255);
	private final JPanel contentPanel = new JPanel();
	public DefaultMutableTreeNode currTreeNode = null;
	public String currentListElement = null;
	public DefaultListModel ind = new DefaultListModel();
	JTree tree;
	JList list;
	OntView otv;
	ArrayList al = new ArrayList();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SIndiDialog dialog = new SIndiDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SIndiDialog() {
		setTitle("Select the resources");
		setBounds(100, 100, 450, 351);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(216, 37, 208, 233);
		contentPanel.add(scrollPane1);
		list = new JList();
		list.setBounds(216, 37, 208, 233);
		scrollPane1.setViewportView(list);

		otv = new OntView();
		tree = new JTree(otv.formOWLtoClasses());
		tree.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tree.setBounds(10, 37, 196, 233);
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// ?ÎÞ·¨Ë¢ÐÂ
				// ind.removeAllElements();
				// list.setModel(ind);
				removeList();
				currTreeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				String className = currTreeNode.toString();
				OntModel m = otv.getOntModel();
				OntClass oc = m.getOntClass(CapabilityModel.modelURI + className);
				// System.out.println(oc.getLocalName());
				for (Iterator p = oc.listInstances(false); p.hasNext();) {
					Individual ii = (Individual) p.next();
					String iin = ii.getLocalName();
					al.add(iin);
				}
				for (int i = 0; i < al.size(); i++) {
					ind.addElement(al.get(i));
				}
				list.setModel(ind);

			}
		});

		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				currentListElement = list.getSelectedValue().toString();
				System.out.println(currentListElement);
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 37, 196, 233);
		scrollPane.setViewportView(tree);
		contentPanel.add(scrollPane);

		JLabel lblNewLabel = new JLabel("Allowed Classes");
		lblNewLabel.setForeground(Color.GRAY);
		lblNewLabel.setBounds(10, 16, 132, 15);
		contentPanel.add(lblNewLabel);

		String[] petStrings = { "Direct asserted instances", "All asserted instances" };
		JComboBox comboBox = new JComboBox(petStrings);
		comboBox.setBounds(216, 10, 151, 21);
		// comboBox.setSelectedIndex(2);
		contentPanel.add(comboBox);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeList();
			}
		});
		btnNewButton.setBounds(375, 12, 49, 19);
		contentPanel.add(btnNewButton);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						OutCSEditor.appendTextArea(currentListElement);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public void removeList() {
		ind.removeAllElements();
		list.setModel(ind);
	}

	Vector getContentofList() {
		Vector individuals = new Vector();
		individuals.addElement("kk");
		individuals.addElement("ww");
		return individuals;
	}
}
