package org.numerone.altervista.lpicsimulator;
import java.util.Scanner;
public class Main {
	     public static void main(String[] args) {
	 	   	Scanner s=new Scanner (System.in);       
	 	   	DomandaDAO dao=new DomandaDAO("/home/numerone/LPI1.odb", "Capitolo1");
	 	   	Domanda d=dao.getDomanda();
	        System.out.print(d.toString());
	        int ris=s.nextInt();
	        if (d.checkRisposta(ris))
	          System.out.println("La risposta è corretta");
	        else 
	          System.out.println("La risposta "+ris+" è sbagliata. La risposta corretta era la "+d.getRisposta());
	}
}