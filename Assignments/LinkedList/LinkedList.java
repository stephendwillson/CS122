import java.util.Scanner;

public class LinkedList
{
	public static void main(String args[])
	{
		LL list = new LL();
		Scanner in = new Scanner(System.in);
		System.out.println("\n\n\nCommands are: af ab df db di se ct");
		while (in.hasNext())
		{
			String command = in.next();

			if (command.equals("af"))
				list.addFront(in.nextInt());
			else if (command.equals("ab"))
				list.addBack(in.nextInt());
			else if (command.equals("df"))
				list.delFront();
			else if (command.equals("db"))
				list.delBack();
			else if (command.equals("di"))
				list.delItem(in.nextInt());
			else if (command.equals("se"))
				list.search(in.nextInt());
			else if (command.equals("ct"))
				list.countOdd();
			else if (command.equals("quit") || command.equals("exit"))
				System.exit(0);
			else
				System.out.println("Unknown command: \"" + command + "\"");
			list.print();
			System.out.println("\n\n\nCommands are: af ab df db di se ct");
		}
	}
}

class LL
{
	node head;

	public void addFront(int newnum)
	{
		node tmp = new node();
		tmp.payload = newnum;
		if (head == null)
		{
			head = tmp;
			head.next = null;
		}
		else
		{
			tmp.next = head;
			head = tmp;
		}
	}

	public void print()
	{
		if (head == null)
		{
			System.out.println("There is no list");
		}
		else
		{
			head.print();
		}
	}

	public void addBack(int newnum)
	{
		if (head == null)
			addFront(newnum);
		else
			head.addBack(newnum);
	}

	public void delBack()
	{
		if(head == null)
			return;
		if(head.next == null)
			head = head.next;
		else
			head.delBack();
	}

	public void delFront()
	{
		if(head == null)
			return;
		else
			head = head.next;
	}

	public void search(int newnum)
	{
		if(head==null)
			return;
		if(head.payload==newnum)
			System.out.println("Found "+newnum);
		else
			head.search(newnum);
	}

	public void delItem(int newnum)
	{
		if(head==null)
			return;
		if(head.payload==newnum)
			delFront();
		else
			head.delItem(newnum);
	}

	public void countOdd()
	{
		int count=0;
		if(head==null)
			return;
		if(head.payload%2==1)
			count++;
		head.countOdd(count);
	}
}

class node
{
	int payload;
	node next;

	public void print()
	{
		System.out.println("Node : " + payload);
		if (next != null)
			next.print();
	}

	public void addBack(int newnum)
	{
		if(next == null)
		{
			next = new node();
			next.payload = newnum;
		}
		else
			next.addBack(newnum);
	}

	public void delBack()
	{
		if(next == null)
			return;
		if(next.next == null)
			next = next.next;
		else
			next.delBack();
	}

	public void search(int newnum)
	{
		if(next == null)
		{
			System.out.println(newnum+" not found, brah.");
			return;
		}

		if(next.payload==newnum)
		{
			System.out.println("Found "+newnum);
			return;
		}
		else
			next.search(newnum);
	}

	public void delItem(int newnum)
	{
		if(next == null)
		{
			System.out.println(newnum+" isn't in the list, brah.");
			return;
		}

		if(next.payload == newnum)
			next = next.next;
		else
			next.delItem(newnum);
	}

	public void countOdd(int count)
	{
		if(next==null)
		{
			System.out.println("There are "+count+" odd(s).");
			return;
		}

		if(next.payload%2==1)
			count++;
		next.countOdd(count);
	}
}