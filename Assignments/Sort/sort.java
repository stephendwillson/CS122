import java.util.*;
import java.io.*;

public class sort {

	public static Scanner in = new Scanner(System.in);

	public static void main (String[] args) {


   //declaring variables
	String[] state = new String[100];
	int[] pop = new int[100];
    BufferedReader infile = null;
    int count = 0;
    int popcount = 0;
    boolean switched = false;

		try
		{
		   //reads file if it's not empty and prints out what it sees
			infile = new BufferedReader(new InputStreamReader(new FileInputStream("data.txt")));
			while((state[count] = infile.readLine()) != null)
			{
			   System.out.println(state[count]);
			   count++;
			   pop[popcount] = Integer.parseInt(infile.readLine());
			   System.out.println(pop[popcount]);
			   popcount++;
	        }

      	    System.out.println("-----------");

       	   //sort 1 (bubble)
            bubble(pop, state,popcount, switched);

           //sort 2 (selection)
      	    //selection(pop, state, popcount);

           //prints out sorted stuff
	    	for(int i = 0; i < count; i++)
			{
				System.out.println(state[i]);
				System.out.println(pop[i]);
			}

           //number of comparisons
			//System.out.println(bubblecount);

           //file closey stuff
	        infile.close();

           //open new file to print to
	        PrintWriter outfile = new PrintWriter(new FileOutputStream("data2.txt"));

           //prints updated state to state.txt
	        for(int i=0; i < count+1; i++)
				   {
				   	  outfile.println(state[i]);
				   	  outfile.println(pop[i]);
				   }

           //more file closey stuff
		    outfile.close();
		}

       //prints out error in writing, if any
		catch (Exception e)
		{
			System.out.println("");
			System.out.println("The data musta got lost somewhere down the line:  "+e);
		}
    }


    public static void swap (String[] state, int a, int b)
    {

		String temp = state[a];
		state[a] = state[b];
		state[b] = temp;
	}

	public static void swap (int[] pop, int a, int b)
	{
		int temp = pop[a];
		pop[a] = pop[b];
		pop[b] = temp;
	}

	public static void selection (int[] pop, String[] state, int popcount)
	{
		int scount=0;

		for (int i = 0; i < popcount - 1; i++)
			{
				int min = i;
				for (int j = i + 1; j < popcount; j++)
				{
					scount++;
					if (pop[j] < pop[min])
					{
						min = j;
					}
				}
				if (i != min)
				{
					swap(pop, i, min);
					swap(state, i, min);
				}

			}
		System.out.println("Selection sort made "+scount+" comparisons.");

	}

	public static void bubble (int[] pop, String[] state, int popcount, boolean switched)
	{

		int bubblecount = 0;
		do
		{
			switched = false;
			for(int i = 0; i < popcount-1; i++)
				if (pop[i] > pop[i+1])
				{
					switched = true;
					swap(state, i, i+1);
					swap(pop, i, i+1);
					bubblecount++;
				}
		}
		while (switched == true);

		System.out.println("Bubble sort made "+bubblecount+" switches.");
	}
}