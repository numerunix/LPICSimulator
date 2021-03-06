package org.numerone.altervista.lpicsimulator;

import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class SelezionaCapitolo extends JDialog implements ActionListener {

		/**
	 * 
	 */
	private static final long serialVersionUID = 3397416245728178223L;
		private JTextField path;
		private JComboBox<String> jcb;
		
		public SelezionaCapitolo(JFrame f) {
			super(f, "Opzioni", Dialog.ModalityType.DOCUMENT_MODAL);
			JPanel p=new JPanel(new GridBagLayout());
			GridBagConstraints c=new GridBagConstraints();
			c.fill=GridBagConstraints.HORIZONTAL;
			c.gridx=0;
			c.gridy=0;
			jcb=new JComboBox<String>();
			jcb.addItem("101 - System Architecture");
			jcb.addItem("102 - Linux Installation and package management");
			jcb.addItem("103 - GNU and UNIX commands");
			jcb.addItem("104 - Devices, Linux FIlesystems, FIlesystem Hierarchy Standard");
			jcb.addItem("Prova intercorso");
			p.add(new JLabel("Su quale capitolo ti vuoi allenare?"), c);
			c.gridy=1;
			p.add(jcb);
			c.gridx=0;
			c.gridy=1;
			p.add(new JLabel("Path del database: "), c);
			path=new JTextField("/home/numerone/LPI1.odb");
			c.gridx=1;
			p.add(path, c);
			JButton OK=new JButton("OK");
			OK.addActionListener(this);
			c.gridy=2;
			c.gridx=0;
			p.add(OK, c);
			c.gridy=3;
			c.gridx=0;
			c.gridwidth=2;
			p.add(new JLabel("Di Giulio Sorrentino <gsorre84@gmail.com> Copyright 2021."),c);
			c.gridy=4;
			p.add(new JLabel("Concesso sotto licenza GPL v3 o, secondo la tua opinione, qualsiasi versione successiva."), c);
			c.gridy=5;
			p.add(new JLabel("Dedicato a quella bella vocina che in data 13/3/2021 ha preso il mio ordinativo al Birdy's Bakery a chiaia via telefono."),c);
			c.gridy=6;
			p.add(new JLabel("Se il software ti piace considera una donazione via paypal."), c);
			c.gridy=6;
			add(p);
			setSize(400,400);
			pack();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				setVisible(false);
			
		}
	

		public String path() {
			return path.getText();
		}
		
		public String getCapitolo() {
			int i=jcb.getSelectedIndex()+1;
			return "Capitolo"+i;
		}
		
}
