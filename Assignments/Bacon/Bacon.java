
import java.io.*;
import java.util.*;

public class Bacon {

 public static Scanner in = new Scanner (System.in);

 public static void main (String[] args) {

   try
   {
	 //declaring variables
       String[] results = new String [1000000];
       String[] names = new String[1000000];
       String answer = null;
       String name = null;
       int ncount = 0;
       int ycounter = 0;
       int ncounter = 0;
       int count = 0;
       BufferedReader infile = null;
       BufferedReader namesin = null;
       BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
       infile = new BufferedReader(new InputStreamReader(new FileInputStream("bacon.txt")));
       namesin = new BufferedReader(new InputStreamReader(new FileInputStream("names.txt")));

     //reads in bacon.txt and names.txt if they're not empty
	   while ((results[count] = infile.readLine()) != null)
	   {
			   System.out.println(results[count]);
			   count++;
	   }

	   while ((names[ncount] = namesin.readLine()) != null)
	   {
		       System.out.println(names[ncount]);
		       ncount++;
	   }

     //closes files
       infile.close();
       namesin.close();

     //prompts user for name; gracefully ends program if user has already answered
       System.out.print("Please enter your name:  ");
       name = kb.readLine();
       for (int i=0; i < ncount; i++)
       {
              if (name.equalsIgnoreCase(names[i]))
              {
                 System.out.println("You've already answered.");
                 System.exit(0);
			  }
	   }

     //prompts user for yes or no answer if they haven't answered, refusing all other answers
	   while (true)
		 {
			System.out.print ("Do you enjoy the taste of bacon? Please answer yes or no:  ");
			answer = kb.readLine();
				if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("no")) break;
		 }

	   if (answer.equalsIgnoreCase("yes"))
	   	   ycounter++;
	   if (answer.equalsIgnoreCase("no"))
	       ncounter++;

     //creating outfile and namesout to write answers and names to respectively
	   PrintWriter outfile = new PrintWriter(new FileOutputStream("bacon.txt"));
	   PrintWriter namesout = new PrintWriter(new FileOutputStream("names.txt"));

     //prints results to bacon.txt
	   for(int i=0; i < count; i++)
	   {
	   	  outfile.println(results[i]);
	   }

	 //prints names entered to names.txt
	   for(int i=0; i < ncount; i++)
	   {
		  namesout.println(names[i]);
	   }

     //raises counters for yes and no answers
	   for(int i = 0; i < count; i++)
	   {
	  	  if (results[i].equalsIgnoreCase("yes"))
	  	  	  ycounter++;

	  	  if (results[i].equalsIgnoreCase("no"))
	  	  	  ncounter++;
	   }

     //lets user know results are written and how many of each there are
	   System.out.println("Wrote "+(count+1)+" result(s).");
	   System.out.println("There are "+ycounter+" yes results and "+ncounter+" no results.");

     //closes both files, printing out the answers and names
	   outfile.println(answer);
	   outfile.close();
	   namesout.println(name);
	   namesout.close();
   }

   catch (Exception e)
   {

	    //lets user know if an error has occured and what said error is
	      System.out.println("There has been an error in writing to the file:  "+e);
   }
 }
}