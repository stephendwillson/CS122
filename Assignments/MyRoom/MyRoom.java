import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Room extends java.applet.Applet implements MouseMotionListener, MouseListener, KeyListener
{
	final int SIZE = 20;
	int[][] room = new int[SIZE][SIZE];
	int hx;
	int hy;
	int ccount;
	int hcount=1;
	int zcount;
	int max;
	int min;
	double avg;

	public void init()
	{
		addMouseMotionListener(this);
		addMouseListener(this);
		addKeyListener(this);
		setBackground(Color.black);

		setBase();
		setWalls();
		setWindow();
		room[4][4]=200;

		tempCalc();
	}

	public void paint(Graphics g)
	{
		for(int x=0; x < SIZE; x++)
		{
			for(int y=0; y < SIZE; y++)
			{
				if(room[x][y] < 50)
					g.setColor(Color.blue);
				else if(room[x][y] >= 50 && room[x][y] <= 75)
					g.setColor(Color.green);
				else if(room[x][y] > 75 && room[x][y] <= 90)
					g.setColor(Color.orange);
				else if(room[x][y] > 90)
					g.setColor(Color.red);
				g.drawString(""+room[x][y],x*30+30,y*30+30);
			}
		}
	}

	public void mouseExited(MouseEvent me){}
	public void mouseEntered(MouseEvent me){}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){}

	public void mouseClicked(MouseEvent me)
	{
		hx = me.getX()-30;
		hy = me.getY();
		newHeat();
		tempCalc();
		ccount++;
	}

	public void mouseMoved(MouseEvent me){}
	public void mouseDragged(MouseEvent me)
	{
	}

	public void keyReleased(KeyEvent ke){}
	public void keyPressed(KeyEvent ke){}
	public void keyTyped(KeyEvent ke)
	{
		if(ke.getKeyChar() == 'h')
		{
			popup.show();
			popup.window();
		}

		if(ke.getKeyChar() == 'r')
		{
			resetRoom();
		}
	}

	public void setWalls()
	{
		for(int y=0; y < SIZE; y++)
		{
			room[0][y]=20;
			room[SIZE-1][y]=20;
		}

		for(int x=1; x < SIZE; x++)
		{
			room[x][0]=20;
			room[x][SIZE-1]=20;
		}
	}

	public void setWindow()
	{
		for(int y=3; y < 8; y++)
			room[0][y]=10;
	}

	public void setBase()
	{
		for(int x=0; x < SIZE; x++)
			for(int y=0; y < SIZE; y++)
				room[x][y]=0;
	}

	public void newHeat()
	{
		if(ccount > 0 && hx/30 > 0 && hy/30 > 0 && hx/30 < SIZE-1 && hy/30 < SIZE-1 && room[hx/30][hy/30]!=200)
		{
			room[hx/30][hy/30]=200;
			hcount++;
		}
		repaint();
		System.out.println("There are "+hcount+" heater(s). Watch the energy bill, hoss.");
	}

	public void tempCalc()
	{
		zcount=0;
		for(int i=0; i < 10000; i++)
			for(int x=1; x < SIZE-1; x++)
				for(int y=1; y < SIZE-1; y++)
				{
					if(room[x][y]!=200)
					room[x][y] = (room[x][y+1]+room[x][y-1]+room[x+1][y]+room[x-1][y])/4;
				}

		repaint();

		for(int x=0; x < SIZE; x++)
			for(int y=0; y < SIZE; y++)
				if(room[x][y]==0)
					zcount++;

		if (zcount > 0)
		{
			System.out.println("There are "+zcount+" zeros.");
			System.out.println("It's frickin' cold! Toss in another heater.");
		}
		averageCalc();
		maxCalc();
		minCalc();
	}

	public void averageCalc()
	{

		avg=0.0;
		for(int x=1; x < SIZE-1; x++)
		{
			for(int y=1; y < SIZE-1; y++)
			{
				avg+=room[x][y];
			}
		}
		avg /= ((SIZE-2)*(SIZE-2));
		System.out.println("Average temperature = "+avg+".");
	}

	public void maxCalc()
	{
		max=0;
		for(int x=1; x < SIZE-1; x++)
		{
			for(int y=1; y < SIZE-1; y++)
			{
				if(room[x][y] > max && room[x][y]!=200)
					max=room[x][y];
			}
		}
		System.out.println("Maximum temperature = "+max+".");
	}

	public void minCalc()
	{
		min=200;
		for(int x=1; x < SIZE-1; x++)
		{
			for(int y=1; y < SIZE-1; y++)
			{
				if(room[x][y] < min)
					min=room[x][y];
			}
		}
		System.out.println("Minimum temperature = "+min+".");
	}

	public void resetRoom()
	{
		setBase();
		setWalls();
		setWindow();
		room[4][4]=200;
		hcount=1;
		System.out.println("There are "+hcount+" heater(s). Watch the energy bill, hoss.");

		tempCalc();

		repaint();
	}
}

public class MyRoom
{
	public static void main(String[] args)
	{
		Room myApplet = new Room();
		Frame myFrame = new Frame ("Frank Zappa's Moustache");

		myApplet.init();

		myFrame.add(myApplet, BorderLayout.CENTER);
		myFrame.pack();
		myFrame.setBounds(0,0,650,650);
		myFrame.setVisible(true);
	}
}

class popup
{
	static boolean bacon=false;

	public static void window()
	{
		JFrame help = new JFrame("Help!");
		help.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JLabel emptyLabel = new JLabel("Click to drop a heater on any given cell. Pressing 'r' resets all cells to default temperatures.");
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
