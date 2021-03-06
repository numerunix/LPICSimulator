package org.numerone.altervista.lpicsimulator;

public class Domanda {
	private int id;
	private String domanda, risposte[];
	private int risposta;
	
	
	
	public Domanda(int id, String d, String r0, String r1, String r2, String r3, String ris) {
		this.id=id;
		domanda=d;
		risposte=new String [4];
		risposte[0]=r0;
		risposte[1]=r1;
		risposte[2]=r2;
		risposte[3]=r3;
		switch (ris.trim()) {
		case "a": 
		case "A": risposta=0; break;
		case "b": 
		case "B": risposta=1; break;
		case "c": 
		case "C": risposta=2; break;
		case "d": 
		case "D": risposta=3; break;
		}
		
	}
	
	
	public String toString() {
		String s = "Domanda numero: "+id+"\n"+domanda+"\n";
	int i;
	for (i=0; i<4; i++)
		s=s+i+": "+risposte[i]+"\n";
	return s;
}

public boolean checkRisposta(int c) {
	return c==risposta;
}

public String[] getRisposte() { 
	return risposte;
}

public String getRisposta() {
	return risposte[risposta];
}
public String getDomanda() {
	return domanda;
}

public int getID() {
	return id;
}
}