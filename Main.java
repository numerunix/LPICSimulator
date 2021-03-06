	import java.sql.*;
	import java.util.zip.*;
	import java.io.*;
	import org.hsqldb.jdbcDriver;
	import java.util.*;
	import java.util.Scanner;
public class Main {
	     public static void main(String[] args) {
	          jdbcDriver j = new jdbcDriver(); //Instantiate the jdbcDriver from HSQL
	          Connection con = null; //Database objects
	          Statement com = null;
	          ResultSet rec = null;
	          ZipFile file = null; //For handeling zip files
	          ZipEntry ent = null;
	          Enumeration en = null; //For the entries in the zip file
	          BufferedOutputStream out = null; //For the output from the zip class
	          InputStream in = null; //for reading buffers from the zip file
	          File f = null; //Used to get a temporary file name, not actually used for anything
	          int len; //General length counter for loops
	          List v = new ArrayList(); //Stores list of unzipped file for deletion at end of program
   		   	Scanner s=new Scanner (System.in);       
	          //Unzip zip file, via info from
	          //http://www.devx.com/getHelpOn/10MinuteSolution/20447
	          
	          try
	          {
	               //Open the zip file that holds the OO.Org Base file
	               file = new ZipFile("/home/numerone/LPI1.odb");
	               
	               //Create a generic temp file. I only need to get the filename from
	               //the tempfile to prefix the extracted files for OO Base
	               f = File.createTempFile("ooTempDatabase", "tmp");
	               f.deleteOnExit();
	               
	               //Get file entries from the zipfile and loop through all of them
	               en = file.entries();
	               Random ran=new Random();
	               while (en.hasMoreElements())
	               {
	                    //Get the current element
	                    ent = (ZipEntry)en.nextElement();
	                    
	                    //If the file is in the database directory, extract it to our
	                    //temp folder using the temp filename above as a prefix
	                    if (ent.getName().startsWith("database/"))
	                    {
	                         System.out.println("Extracting File: " + ent.getName());
	                         byte[] buffer = new byte[1024];
	                    
	                         //Create an input stream file the file entry
	                         in = file.getInputStream(ent);
	                         
	                         //Create a output stream to write out the entry to, using the
	                         //temp filename created above
	                         out = new BufferedOutputStream(new FileOutputStream("/tmp/" + f.getName() + "." + ent.getName().substring(9)));
	                         
	                         //Add the newly created temp file to the tempfile vector for deleting
	                         //later on
	                         v.add("/tmp/" + f.getName() + "." + ent.getName().substring(9));
	                         
	                         //Read the input file into the buffer, then write out to
	                         //the output file
	                         while((len = in.read(buffer)) >= 0)
	                         out.write(buffer, 0, len);
	                         
	                         //close both the input stream and the output stream
	                         out.close();
	                         in.close();
	                    }
	               }
	               //Close the zip file since the temp files have been created
	               file.close();
	               
	               //Create our JDBC connection based on the temp filename used above
	               con = DriverManager.getConnection("jdbc:hsqldb:file:/tmp/" + f.getName(), "SA", "");
        		   String ris="";
        		   System.out.println("1. 101 - Architettura di sistema");
        		   System.out.println("2. 102 - Installazione di linux e gestione dei pacchetti");
        		   System.out.print("Su quale topic vuoi esercitarti? ");
        		   int i=s.nextInt();
        		   String Tabella="";
        		   switch (i) {
        		   case 1: Tabella="Capitolo1"; break;
        		   case 2: Tabella="Capitolo2"; break;
        		   default: System.out.println("Errore");
        		   System.exit(1);
        		   }
	               do {
	            	   i=Math.abs(ran.nextInt() %88)+1;
	            	   System.out.println("Domanda "+i);
	            	   //Create a command object and execute, storing the results in the rec object
	            	   com = con.createStatement();
	            	   rec = com.executeQuery("SELECT \"Domande\".\"Domanda\", \"Risposte\".\"AffermazioneA\", \"Risposte\".\"AffermazioneB\", \"Risposte\".\"AffermazioneC\", \"Risposte\".\"AffermazioneD\", \"Risposte\".\"Risposta\" FROM \""+Tabella+"\" LEFT JOIN \"Domande\" ON ( \""+Tabella+"\".\"ID_DOMANDA\" = \"Domande\".\"ID\" ) LEFT JOIN \"Risposte\" ON ( \""+Tabella+"\".\"ID_RISPOSTE\" = \"Risposte\".\"ID\" ) WHERE ID = "+i);
	            	   //GO through the resultset, and output the results
	            	   while (rec.next()) {
	            		   System.out.println(rec.getString("Domanda"));
	            		   System.out.println("A: " + rec.getString("AffermazioneA"));
	            		   System.out.println("B: " +rec.getString("AffermazioneB"));
	            		   System.out.println("C: " + rec.getString("AffermazioneC"));
	            		   System.out.println("D: "+ rec.getString("AffermazioneD"));
	            		   System.out.println("E: esci");
	            		   System.out.print("Indicare la risposta: ");

	            		   ris=s.next();
	            		   if (ris.trim().compareTo((rec.getString("Risposta").trim()))==0) {
	            			   System.out.println("La risposta è corretta");
	            		   } else {
	            			   System.out.println("La risposta "+ris+" è sbagliata. La risposta corretta era la "+rec.getString("Risposta"));
	            		   }
	            	   }
	               } while (ris.compareTo("e")!=0);
	            	   //Close all the database objects
	            	   rec.close();
	            	   com.close();
	            	   con.close();
	            	
	               //Delete the temporary files, which file names are stored in the v vector
	               for (len = 0; len!= v.size(); len++)
	               (new File((String)v.get(len))).delete();
	          }
	          catch (Exception e)
	          {
	               e.printStackTrace();
	          }
	     }
	}

