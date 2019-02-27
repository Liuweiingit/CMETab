package org.wit.rpt.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JScrollPane;

import org.wit.rpt.model.CapabilityModel;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SProDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public static DefaultListModel property = new DefaultListModel();
	JList list;
	String currentElement;
	OntModel m;
	ArrayList<String> al;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SProDialog dialog = new SProDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */

	public SProDialog() {

		setTitle("Select a property");
		setBounds(100, 100, 320, 360);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.setBackground(SystemColor.menu);

		al = new ArrayList();
		addList();
		property.removeAllElements();
		for (int i = 0; i < al.size(); i++) {
			property.addElement(al.get(i));
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 284, 269);
		contentPanel.add(scrollPane);

		list = new JList();
		list.setBounds(10, 10, 284, 269);
		list.setModel(property);
		scrollPane.setViewportView(list);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				currentElement = list.getSelectedValue().toString();
				// System.out.println(currentElement);
			}
		});

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						OutCSEditor.appendTextArea(currentElement);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public void addList() {
		CapabilityModel om = new CapabilityModel();
		m = om.getModel();
		OntClass p = m.getOntClass(CapabilityModel.PredicateURI);
		for (Iterator lp = p.listInstances(false); lp.hasNext();) {
			Individual ii = (Individual) lp.next();
			String iin = ii.getLocalName();
			al.add(iin);
		}
	}

	public void remove() {
		property.removeAllElements();
		list.setModel(property);
	}
}
