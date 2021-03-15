//Autore: Giulio Sorrentino
package org.numerone.altervista.lpicsimulator;
import java.util.Scanner;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
public class Main {
	     public static void main(String[] args) {
	 	  try {
            // Set cross-platform Java L&F (also called "Metal")
        UIManager.setLookAndFeel(
            UIManager.getCrossPlatformLookAndFeelClassName());
    } 
    catch (UnsupportedLookAndFeelException e) {
       // handle exception
    }
    catch (ClassNotFoundException e) {
       // handle exception
    }
    catch (InstantiationException e) {
       // handle exception
    }
    catch (IllegalAccessException e) {
       // handle exception
    }
	    	 LPICFrame f=new LPICFrame();
	    	 f.setVisible(true);
	}
}
