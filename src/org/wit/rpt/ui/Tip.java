package org.wit.rpt.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.wit.rpt.model.CapabilityModel;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Tip extends JDialog {

	private final JPanel contentPanel = new JPanel();
	JLabel tipLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Tip dialog = new Tip();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Tip() {
		setBounds(100, 100, 358, 213);
		getContentPane().setLayout(null);
		contentPanel.setBounds(319, 0, 20, 131);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 131, 339, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);

			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					try {
						CapabilityModel.saveModel();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dispose();
				}
			});
			okButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent arg0) {
					setVisible(false);
				}
			});
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);

			JLabel lblNewLabel = new JLabel("Do you want to delete this item?");
			lblNewLabel.setBounds(42, 58, 263, 18);
			getContentPane().add(lblNewLabel);

			JButton cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			// buttonPane.add(cancelButton);
		}
	}

	public void setLable(String s) {
		// tipLabel = new JLabel("Please choose one of \"Agent\" node.");
		tipLabel = new JLabel(s);
		tipLabel.setBounds(63, 55, 212, 33);
		getContentPane().add(tipLabel);
	}
}
