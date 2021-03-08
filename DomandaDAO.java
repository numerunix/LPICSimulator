package org.numerone.altervista.lpicsimulator;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.hsqldb.jdbcDriver;

public class DomandaDAO {
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
    List v; //Stores list of unzipped file for deletion at end of program
	Random r;
	String Tabella;
	int max;
	Vector<Boolean> usciti;
	public DomandaDAO(String path, String tabella) {
		v	= new ArrayList();
	
    //Unzip zip file, via info from
    //http://www.devx.com/getHelpOn/10MinuteSolution/20447
    
    try
    {
         //Open the zip file that holds the OO.Org Base file
         file = new ZipFile(path);
         
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
       //  do {
      	   //System.out.println("Domanda "+i);
      	   //Create a command object and execute, storing the results in the rec object
      	   com = con.createStatement();
      	   //GO through the resultset, and output the results
      	   /*while (rec.next()) {
      		   System.out.println();
      		   System.out.println("A: " + );
      		   System.out.println("B: " +r);
      		   System.out.println("C: " + );
      		   System.out.println("D: "+ );
      		   System.out.println("E: esci");*/
      	   r=new Random();
      	   Tabella=tabella;
      	   char temp=tabella.charAt(tabella.length() - 1);
      	   switch (temp) {
      	   case '1': max=88; break;
      	   case '2': max=89; break;
      	   case '3': max=88; break;
      	   }
      		 
    }         
    catch (Exception e)
    {
         e.printStackTrace();
    }
}
	
	Domanda getDomanda() {
		Domanda d=null;
		int i=(Math.abs((r.nextInt()))%max)+1;
		try {
			rec = com.executeQuery("SELECT \"Domande\".\"Domanda\", \"Risposte\".\"AffermazioneA\", \"Risposte\".\"AffermazioneB\", \"Risposte\".\"AffermazioneC\", \"Risposte\".\"AffermazioneD\", \"Risposte\".\"Risposta\" FROM \""+Tabella+"\" LEFT JOIN \"Domande\" ON ( \""+Tabella+"\".\"ID_DOMANDA\" = \"Domande\".\"ID\" ) LEFT JOIN \"Risposte\" ON ( \""+Tabella+"\".\"ID_RISPOSTE\" = \"Risposte\".\"ID\" ) WHERE ID = "+i);
		    rec.next();
		    d=new Domanda(i, rec.getString("Domanda"), rec.getString("AffermazioneA"), rec.getString("AffermazioneB"), rec.getString("AffermazioneC"), rec.getString("AffermazioneD"), rec.getString("Risposta"));	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}
	
	public void exit() {
 	   try {
		rec.close();
		com.close();
	 	con.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 	 
    //Delete the temporary files, which file names are stored in the v vector
    for (len = 0; len!= v.size(); len++)
    (new File((String)v.get(len))).delete();

	}
	
}
    
