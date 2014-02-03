import java.io.*;

public class hash {


	final static int SIZE = 15000000;
	static int word[] = new int[SIZE];
	static int count;

	
	public static void main (String[] args)
	{

		BufferedReader infile=null;
		BufferedReader file2 = null;

		try
		{
			infile = new BufferedReader(new InputStreamReader(new FileInputStream("list1.txt")));
			while(infile != null)
			{
				String line = infile.readLine();

				word[hash(line)] = hash(line);
				
			}
			infile.close();
		}

		catch (Exception e)
		{
		}

		try
		{
			file2 = new BufferedReader(new InputStreamReader(new FileInputStream("list2.txt")));
			while(file2 != null)
			{
				String line2 = file2.readLine();

				if(hash(line2)==word[hash(line2)])
					System.out.println(line2);

			}
			file2.close();
		}
		catch (Exception e)
		{
		}
	}

	public static int hash(String a)
	{
		int value=0;

		value = Math.abs(a.hashCode()) % SIZE;
		
		return value;
	}
}