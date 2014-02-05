import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Interface extends java.applet.Applet implements KeyListener, MouseListener
{
	Shape items[];
	int ct;
	int num=-1;
	final int SIZE=100;
	int cct=1;
	
	public void init()
	{
		items = new Shape[SIZE];
		setBackground(Color.green);
		resize(1000,650);
		addKeyListener(this);
		addMouseListener(this);
	}
	
	public void paint(Graphics g)
	{
		Polygon trangle = new Polygon();
		trangle.addPoint(60,100);
		trangle.addPoint(20,160);
		trangle.addPoint(100,160);
		g.fillPolygon(trangle);
		
		g.fillRect(30,300,50,50);
		
		g.fillRect(20,530,90,30);
		g.fillRect(50,500,30,90);
		
		g.drawLine(150,0,150,650);
		
		for(int i=0; i < SIZE; i++)
			if(items[i] != null)
			{
				items[i].paint(g);
				items[i].move(0,.1);
			}
		
		try
		{
			Thread.sleep(10);
		}
		catch(Exception e){}
		repaint();
	}
	
	public void keyPressed(KeyEvent ke) 
	{
		for(int i=0; i < SIZE; i++)
		{
			if(items[i] != null && num > -1) 
			{
				if(ke.getKeyChar()=='r')
						items[num].changeColor((int)(Math.random() *256), (int)(Math.random() *256), (int)(Math.random() *256));
						
				if(ke.getKeyChar()=='d')
					items[num]=null;

				num=-1;
			}
		}
	}
	public void keyReleased(KeyEvent ke) {}
	public void keyTyped(KeyEvent ke) {}

	public void mouseClicked(MouseEvent me) 
	{
		int X = me.getX();
		int Y = me.getY();
		
		if(cct==2 && X > 150 && X < 1000 && Y > 0 && Y < 585)
		{
			items[num].movePos(X,Y);
			
			items[num].selected(Color.black);
			cct=1;
		}
		
		if(X > 20 && X < 100 && Y > 100 && Y < 160)
		{
			items[ct] = new Triangle(400,50,Color.black);
			ct++;
		}
		
		if(X > 30 && X < 80 && Y > 300 && Y < 350)
		{
			items[ct] = new Square(500,50,Color.black);
			ct++;
		}
		
		if(X > 20 && X < 110 && Y > 500 && Y < 590)
		{
			items[ct] = new Plus(600,50,Color.black);
			ct++;
		}
		
		for(int i=0; i < SIZE; i++)
		{			
			if(items[i] != null && items[i].selected(X,Y))
			{
				if (num > -1)
				{
					items[num].selected(Color.black);
					items[i].selected(Color.blue);
				}
				
				num=i;
				items[num].selected(Color.blue);
				cct=2;
			}
		}
		repaint();
	}
	public void mouseEntered(MouseEvent me) {}
	public void mouseExited(MouseEvent me) {}
	public void mousePressed(MouseEvent me) {}
	public void mouseReleased(MouseEvent me) {}
}

interface Shape
{	
	public void paint(Graphics g);
	public void move(double vx, double vy);
	public boolean selected(int x, int y);
	public void changeColor(int x, int y, int z);
	public void movePos(int X, int Y);
	public void selected(Color C);
}

class Triangle implements Shape
{
	int x, y;
	Color c;
	double velx, vely;
	
	public Triangle(int _x, int _y, Color _c){ x=_x; y=_y; c=_c; }
	public void paint(Graphics g)
	{
		g.setColor(c);
		Polygon p = new Polygon();
		p.addPoint(x,y);
		p.addPoint(x-40,y+60);
		p.addPoint(x+40,y+60);
		g.fillPolygon(p);
	}
	
	public boolean selected(int px, int py)
	{
		return(px > x && px < x+80 && py > y && py < y+60);
	}
	
	public void move(double vx, double vy)
	{ 
		velx+=vx;
		vely+=vy;
		if(x <= 140 || x >= 990 || y <= 10 || y >= 575)
		{
			
			vely = -vely * .7;
		}
		
		if(x > 150 && x < 1000 && y > 0 && y < 585)
		{
			x += velx; 
			y += vely;
		}
		
		vely = vely - (vely*vely)*.005;
		velx = velx - (velx*velx)*.005;
		
		vely += .01;
	}
	
	public void changeColor(int x, int y, int z)
	{
		c=new Color(x,y,z);
	}
	
	public void movePos(int X, int Y)
	{
		x=X;
		y=Y;
	}
	
	public void selected(Color C)
	{
		c=C;
	}
}
class Square implements Shape 
{
	int x, y;
	Color c;
	double velx, vely;
	
	public Square(int _x, int _y, Color _c){ x=_x; y=_y; c=_c; }
	public void paint(Graphics g) 
	{
		g.setColor(c);
		g.fillRect(x,y,50,50);
	}
	
	public boolean selected(int px, int py)
	{
		return(px > x && px < x+50 && py > y && py < y+50);
	}
	
	public void move(double vx, double vy)
	{ 
		velx+=vx;
		vely+=vy;
		if(x <= 140 || x >= 990 || y <= 10 || y >= 575)
		{
			
			vely = -vely * .7;
		}
		
		if(x > 150 && x < 1000 && y > 0 && y < 585)
		{
			x += velx; 
			y += vely;
		}
		
		vely = vely - (vely*vely)*.005;
		velx = velx - (velx*velx)*.005;
		
		vely += .01;
	}
	
	public void changeColor(int x, int y, int z)
	{
		c=new Color(x,y,z);
	}
	
	public void movePos(int X, int Y)
	{
		x=X;
		y=Y;
	}
	
	public void selected(Color C)
	{
		c=C;
	}
}

class Plus implements Shape
{
	int x, y;
	Color c;
	double velx, vely;
	
	public Plus(int _x, int _y, Color _c){ x=_x; y=_y; c=_c; }
	public void paint(Graphics g) 
	{
		g.setColor(c);
		g.fillRect(x,y+30,90,30);
		g.fillRect(x+30,y,30,90);
	}
	
	public boolean selected(int px, int py)
	{
		return(px > x && px < x+90 && py > y && py < y+90);
	}
	
	public void move(double vx, double vy)
	{ 
		velx+=vx;
		vely+=vy;
		if(x <= 140 || x >= 990 || y <= 10 || y >= 575)
		{
			
			vely = -vely * .7;
		}
		
		if(x > 150 && x < 1000 && y > 0 && y < 585)
		{
			x += velx; 
			y += vely;
		}
		
		vely = vely - (vely*vely)*.005;
		velx = velx - (velx*velx)*.005;
		
		vely += .01;
	}
	
	public void changeColor(int x, int y, int z)
	{
		c=new Color(x,y,z);
	}
	
	public void movePos(int X, int Y)
	{
		x=X;
		y=Y;
	}
	
	public void selected(Color C)
	{
		c=C;
	}
}