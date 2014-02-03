import java.util.*;

public class Hamurabi
{
	static int yrs=1;

	public static void main (String[] args)
	{
		year (100, 100, 0);
	}

	public static int question (String s, int limit)
	{
		Scanner in = new Scanner (System.in);
		System.out.print (s);
		int answer = in.nextInt();
		if (answer>limit || answer<0)
			return question (s, limit);
		else
			return answer;
	}

	public static void year (int pop, int food, int laboryears)
	{	
		int dead=0;
		int disaster=(int)(15*Math.random()+1);
		if (disaster==2)
		{
			pop/=2;
			System.out.println ("A plague wiped out half of your population! Sucks to be you.\n");
		}
		
		System.out.println ("YEAR "+yrs+"\nPopulation: "+pop+", Food: "+food+", Monument years: "+laboryears+"\n");
		
		int babymakers = question ("How many people should make babies? ", pop);
		int builders = question ("How many people should work on the monument? ", (pop-babymakers)); 
		int farmers = pop-builders-babymakers;
		
		int startpop=pop;
		int startfood=food;
		
		pop+=(babymakers/2);
		
		if (food < pop)
			dead=pop-food;
		pop-=dead;
		
		food-=pop;
		
		System.out.println ("\nBabymakers: "+babymakers+", Builders: "+builders+", Farmers: "+farmers+"\n");
		
		//rats
		food/=2;
		
		food+=(farmers*2);
		
		laboryears+=builders;
		
		System.out.println ("Food at start of year (before consumption): "+startfood+", Food left at end of year: "+food+"" +
				"\nPopulation at start of year: "+startpop+", " +
				"Population at end of year: "+pop+", Babies born: "+(babymakers/2)+", People starved: "+dead+"\n");
		
		yrs++;
		
		if (pop<=0)
			System.out.println ("You lose. It took you "+(yrs-1)+" year(s) to kill everyone.");
		else if (laboryears>100) 
			System.out.println ("You win! It took you "+(yrs-1)+" years.");
		else
			year (pop, food, laboryears);
	}
}