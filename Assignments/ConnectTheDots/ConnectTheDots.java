
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

class ConnectDots extends java.applet.Applet implements MouseListener, KeyListener
	{
		list theList = new list();

		public void init ()
		{
			setBackground(Color.black);
			addMouseListener(this);
			addKeyListener(this);
		}

       //tells list to paint itself
		public void paint (Graphics g)
		{
			theList.paint(g);

			g.setColor(Color.green);
			g.drawString("Hit 'h' for help menu.",20,40);
		}

	   //all sorts of mousepressed stuff
		public void mouseExited(MouseEvent me) {}
		public void mouseEntered(MouseEvent me) {}
		public void mousePressed(MouseEvent me) {}
		public void mouseReleased(MouseEvent me) {}
		public void mouseClicked(MouseEvent me)
		{
			int x = me.getX();
			int y = me.getY();
			theList.addDot(x,y);
			repaint();
		}

       //all sorts of keypressed stuff
		public void keyPressed(KeyEvent ke) {}
		public void keyReleased(KeyEvent ke) {}
		public void keyTyped(KeyEvent ke)
		{
			System.out.println(ke.getKeyChar());
			if (ke.getKeyChar() == 'l')
			{
				theList.deleteLast();
				repaint();
			}

			if (ke.getKeyChar() == 'f')
			{
				theList.deleteFirst();
				repaint();
			}

			if (ke.getKeyChar() == 'c')
			{
				theList.close();
				repaint();
			}

			if (ke.getKeyChar() == 'o')
			{
				theList.open();
				repaint();
			}

			if (ke.getKeyChar() == 'x')
			{
				theList.clear();
				repaint();
			}

			if (ke.getKeyChar() == 'h')
			{
				popup.show();
				popup.window();
			}

			if (ke.getKeyChar() == 's')
			{
				theList.save();
			}
		}
	}

   //magic code to run applet as application
	public class ConnectTheDots
	{
		public static void main(String[] args)
		{
			ConnectDots myApplet = new ConnectDots();
			Frame myFrame = new Frame("SUPER CONNECT THE DOTS 9000!!!");

			myApplet.init();

			myFrame.add(myApplet,BorderLayout.CENTER);
			myFrame.pack();
			myFrame.setBounds(0,0,700,600);
			myFrame.setVisible(true);
		}
	}

   //single dot class
	class dot
	{
		private int x,y;

       //accessor for x point
		public int myX()
		{
			return x;
		}

	   //accessor for y point
		public int myY()
		{
			return y;
		}

       //paints individual dot
		public void paint (Graphics g)
		{
			g.setColor(new Color((int)(256*Math.random()),(int)(256*Math.random()),(int)(256*Math.random())));
			g.fillOval(x,y,8,8);
		}

	   //constructor
		public dot (int a, int b)
		{
			x=a;
			y=b;
		}
	}

   //container class for list of dots
	class list
	{
		boolean open=true;
		private static int count;
		private static dot theData[] = new dot[10000];

	   //adds dot to list
		public void addDot(int a, int b)
		{
				if (count < 1000)
				{
					theData[count] = new dot(a,b);
					count++;
				}
		}

	   //deletes first dot
		public static void deleteFirst()
		{
			for (int i=1; i < count; i++)
			{
				theData[i-1] = theData[i];
			}

			if (count > 0)
				count--;
		}

       //deletes most recent dot
		public static void deleteLast()
		{
			if (count > 0)
				count--;
		}

	   //paints list based on open/closed mode
		public void paint(Graphics g)
		{
			for(int i=0; i < count; i++)
			{
				theData[i].paint(g);
			}

			for(int i=1; i < count; i++)
			{
				g.drawLine(theData[i].myX()+4, theData[i].myY()+4, theData[i-1].myX()+4, theData[i-1].myY()+4);
			}

			if(!open && count!=0)
				g.drawLine(theData[0].myX()+4, theData[0].myY()+4, theData[count-1].myX()+4, theData[count-1].myY()+4);
		}

       //switches to closed lines
		public void close ()
		{
			open=false;
		}

	   //switches to open lines
		public void open()
		{
			open=true;
		}

	   //clears all dots
		public void clear()
		{
			count=0;
		}

		public void save()
		{
			try
			{
				PrintWriter outfile = new PrintWriter(new FileOutputStream("DotsSnL.txt"));
					for (int i=0; i < count; i++)
					{
						outfile.println(theData[i].myX());
						outfile.println(theData[i].myY());
					}
				outfile.close();

			}
			catch (Exception e)
			{
				System.out.println("Error in writing to file:  "+e);
			}
		}
	}

     //sets up popup help window..called in main class
	class popup
	{
		static boolean bacon=false;

    	public static void window()
    	{
			JFrame help = new JFrame("Help");
       	 	help.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       	 	JLabel emptyLabel = new JLabel("'c' switches to closed mode, 'o' switches to open mode.'f' deletes first dot, 'l' deletes last dot. 'x' deletes all dots.");
		    emptyLabel.setPreferredSize(new Dimension(650, 20));
		    help.getContentPane().add(emptyLabel, BorderLayout.CENTER);

		    help.pack();
		    help.setVisible(bacon);
		}

		public static void show ()
		{
			bacon=true;
		}
	}
