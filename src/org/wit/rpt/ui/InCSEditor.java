package org.wit.rpt.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.wit.rpt.model.CapabilityModel;
import org.wit.rpt.model.ContextState;
import org.wit.rpt.swrl.IndividualPropertyAtom;
import org.wit.rpt.ui.OutCSEditor.AddIndividual;
import org.wit.rpt.ui.OutCSEditor.AddOntClass;
import org.wit.rpt.ui.OutCSEditor.AddPredicate;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;

public class InCSEditor extends JDialog {

	public JButton okButton;
	private JTextField textField;
	public JComboBox comboBox;
	static JTextArea textArea;
	SClassDialog dialogc;
	SProDialog dialogp;
	SIndiDialog dialogi;
	CapabilityModel om;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			InCSEditor dialog = new InCSEditor();
			dialog.setTitle("OutConstraints editor");
			dialog.setBounds(100, 100, 570, 300);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */

	public InCSEditor() {
		om = new CapabilityModel();
		getContentPane().setLayout(null);
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(10, 214, 524, 33);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane);

		JLabel lblNewLabel_1 = new JLabel("Choose ContextState:");
		lblNewLabel_1.setBounds(10, 10, 180, 15);
		getContentPane().add(lblNewLabel_1);

		comboBox = new JComboBox();
		comboBox.addItem("");
		OntClass ContextState = om.getModel().getOntClass(CapabilityModel.ContextStateURI);
		for (Iterator p = ContextState.listInstances(); p.hasNext();) {
			Individual ii = (Individual) p.next();
			String iin = ii.getLocalName();
			comboBox.addItem(iin);
		}

		comboBox.setBounds(207, 7, 180, 21);
		getContentPane().add(comboBox);

		JLabel lblNew = new JLabel("Create new ContextState:");
		lblNew.setBounds(10, 45, 192, 15);
		getContentPane().add(lblNew);

		// textField = new JTextField();
		// textField.setBounds(207, 41, 327, 21);
		// getContentPane().add(textField);
		// textField.setColumns(10);
		// textField.setText("s15");

		textArea = new JTextArea();
		textArea.setBounds(10, 75, 524, 96);
		getContentPane().add(textArea);
		textArea.setText("hasstate(lift,free)");

		JButton btnC = new JButton("C");
		btnC.setBounds(351, 181, 36, 23);
		getContentPane().add(btnC);
		btnC.addActionListener(new AddOntClass());

		JButton btnP = new JButton("P");
		btnP.setBounds(388, 181, 36, 23);
		getContentPane().add(btnP);
		btnP.addActionListener(new AddPredicate());

		JButton btnI = new JButton("I");
		btnI.setBounds(425, 181, 36, 23);
		getContentPane().add(btnI);
		btnI.addActionListener(new AddIndividual());

		JButton btnl = new JButton("(");
		btnl.setBounds(463, 181, 36, 23);
		getContentPane().add(btnl);
		btnl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.append("(");
				textArea.repaint();
			}
		});

		JButton btnr = new JButton(")");
		btnr.setBounds(500, 181, 36, 23);
		getContentPane().add(btnr);
		btnr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.append(")");
				textArea.repaint();
			}
		});

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				Object type1 = comboBox.getSelectedItem();
				String tn1 = type1.toString();
				if (tn1.length() == 0) {
					ContextState cs = parsercs(textArea.getText());
					String aname = cs.getName();
					String actionname = CMETab.currTreeNode.toString();
					try {
						om.addasOutConstraints(actionname, aname);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					CMETab.addOutCList(aname);
				} else {
					String actionname = CMETab.currTreeNode.toString();
					try {
						om.addasOutConstraints(actionname, tn1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					CMETab.addOutCList(tn1);
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

		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				setVisible(false);
			}
		});
	}

	private static ContextState parsercs(String txt) {
		String[] a = txt.split("\\(|\\,|\\)");
		String c = a[0];
		String propertyPre = DelChar(c);
		String b = a[1];
		String argu1 = DelChar(b);
		String d = a[2];
		String argu2 = DelChar(d);
		String csname = argu1 + "_" + propertyPre + "_" + argu2;
		ContextState cs = CapabilityModel.createContextState(csname, propertyPre, argu1, argu2);
		return cs;
	}

	private static IndividualPropertyAtom parseripa(String txt) {
		String[] a = txt.split("\\(|\\,|\\)");
		String c = a[0];
		String propertyPre = DelChar(c);
		String b = a[1];
		String arg1 = DelChar(b);
		String d = a[2];
		String arg2 = DelChar(d);
		String IPAtomname = arg1 + "_" + propertyPre + "_" + arg2;
		IndividualPropertyAtom ipa = new IndividualPropertyAtom(IPAtomname, propertyPre, arg1, arg2);
		return ipa;
	}

	public static String DelChar(String c) {
		StringTokenizer st1 = new StringTokenizer(c);
		String str = "";
		while (st1.hasMoreTokens()) {
			str = str + st1.nextToken(" ");
		}
		str = str.trim();
		return str;
	}

	public static void appendTextArea(String s) {
		textArea.append(s);
		textArea.append(" ");
		textArea.invalidate();
		textArea.repaint();
	}

	class AddOntClass implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dialogc = new SClassDialog();
			dialogc.setTitle("Choose OntClass");
			dialogc.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialogc.setVisible(true);
		}
	}

	class AddPredicate implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dialogp = new SProDialog();
			dialogp.setTitle("Choose Predicate");
			dialogp.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialogp.setVisible(true);
		}
	}

	class AddIndividual implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dialogi = new SIndiDialog();
			dialogi.setTitle("Choose Individual");
			dialogi.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialogi.setVisible(true);
		}
	}

}
