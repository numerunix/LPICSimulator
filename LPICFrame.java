package org.numerone.altervista.lpicsimulator;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class LPICFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 295490139477179212L;
	private JLabel numeroDomanda, domanda;
	private JRadioButton jrb[];
	private DomandaDAO dao;
	private Domanda d;
	private String[] risposte;
	public LPICFrame()  {
		super("LPICSimulator 0.1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p=new JPanel(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		SelezionaCapitolo sc=new SelezionaCapitolo(this);
		sc.setVisible(true);
	  	dao=new DomandaDAO(sc.path(), sc.getCapitolo());
	  	d=dao.getDomanda();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=0;
		p.add(new JLabel("Numero della domanda: "), c);
		c.gridx=1;
		numeroDomanda=new JLabel(""+d.getID());
		addWindowListener(new WindowAdapter()
		{
		    @Override
		    public void windowClosing(WindowEvent e)
		    {
		        super.windowClosing(e);
		        // Do your disconnect from the DB here.
		        dao.exit();
		    }
		});
		p.add(numeroDomanda, c);
		c.gridx=0;
		c.gridwidth=2;
		c.gridy++;
		domanda=new JLabel(d.getDomanda());
		p.add(domanda, c);
		risposte=d.getRisposte();
		int i;
		ButtonGroup b=new ButtonGroup();
		jrb=new JRadioButton[4];
		for (i=0; i<4; i++) {
			c.gridy++;
			jrb[i]=new JRadioButton(risposte[i]);
			b.add (jrb[i]);
			p.add(jrb[i], c);
		}
		final String messaggio;
		c.gridy++;
		c.gridwidth=1;
		JButton Ok=new JButton("OK");
		Ok.addActionListener(this);
		JButton Esci=new JButton("Esci");
		Esci.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}});
		p.add(Ok,c);
		c.gridx=1;
		p.add(Esci, c);
 	   	add(p);
		setSize(400,400);
 	   	pack();
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int risposta=-1;
		if (jrb[0].isSelected())
			risposta=0;
		else if (jrb[1].isSelected())
			risposta=1;
		else if (jrb[2].isSelected())
			risposta=2;
		else if (jrb[3].isSelected())
			risposta=3;
		if (risposta==-1)
			return;
		if (d.checkRisposta(risposta))
			JOptionPane.showMessageDialog(this, "Hai indovinato", "Hai Indovinato", JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(this,  "Mi spiace, la risposta corretta era "+d.getRisposta(), "Errore", JOptionPane.ERROR_MESSAGE);
		d=dao.getDomanda();
		numeroDomanda.setText(""+d.getID());
		domanda.setText(d.getDomanda());
		int i;
		risposte=d.getRisposte();
		for (i=0;i<4;i++) {
			jrb[i].setText(risposte[i]);
			jrb[i].setSelected(false);
		}
	}
	
}
