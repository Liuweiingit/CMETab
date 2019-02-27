package org.wit.rpt.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.wit.rpt.model.Agent;
import org.wit.rpt.model.CapabilityModel;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;

public class SCapabilityDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public static DefaultListModel capabilitys = new DefaultListModel();
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

	public SCapabilityDialog() {

		setTitle("Select a Capability");
		setBounds(100, 100, 320, 360);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.setBackground(SystemColor.menu);

		al = new ArrayList();
		addList();
		capabilitys.removeAllElements();
		for (int i = 0; i < al.size(); i++) {
			capabilitys.addElement(al.get(i));
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 284, 269);
		contentPanel.add(scrollPane);

		list = new JList();
		list.setBounds(10, 10, 284, 269);
		list.setModel(capabilitys);
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
						/**
						 * 添加能力后，显示在窗体中,并保存到本体中
						 */
						CMETab.addActList(currentElement);
						String agentname = CMETab.currTreeNode.toString();
						String caname = currentElement;
						Agent ag = new Agent(m, agentname);
						try {
							ag.addasCapability(agentname, caname);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							CapabilityModel.saveModel();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
		OntClass p = m.getOntClass(CapabilityModel.CapabilityURI);
		for (Iterator lp = p.listInstances(false); lp.hasNext();) {
			Individual ii = (Individual) lp.next();
			String iin = ii.getLocalName();
			al.add(iin);
		}
	}

	public void remove() {
		capabilitys.removeAllElements();
		list.setModel(capabilitys);
	}
}
