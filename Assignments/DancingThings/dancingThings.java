import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class dancingThings extends java.applet.Applet implements KeyListener, MouseListener
{
	Shape triangle;
	Shape square;
	Shape plus;


	public dancingThings()
	{
		triangle = new Triangle(60,100);
		square = new Square(30,300);
		plus = new Plus(20,500);
	}

	public void init()
	{
		resize(1000,1000);
		addKeyListener(this);
		addMouseListener(this);
	}

	public void paint(Graphics g)
	{
		triangle.paint(g);
		square.paint(g);
		plus.paint(g);
		/*triangle.move((int)(Math.random()*10-5), (int)(Math.random()*10-5));
		square.move((int)(Math.random()*6-3), (int)(Math.random()*6-3));
		plus.move((int)(Math.random()*4-2), (int)(Math.random()*4-2));*/
		try
		{
			Thread.sleep(5);
		}
		catch (Exception e) {}
		repaint();
	}

	public void keyPressed(KeyEvent ke) {}
	public void keyReleased(KeyEvent ke) {}
	public void keyTyped(KeyEvent ke) {}

	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}

class Shape
{
	int x, y;

	public Shape(int _x, int _y)
	{
		x = _x;
		y = _y;
	}

	public void paint(Graphics g){}
	public void move(int vx, int vy)
	{
		x += vx;
		y += vy;
		vy += .01;
	}
}

class Triangle extends Shape
{
	public Triangle(int _x, int _y) { super(_x, _y); }
	public void paint(Graphics g)
	{
		g.setColor(Color.yellow);
		Polygon p = new Polygon();
		p.addPoint(x,y);
		p.addPoint(x-40,y+60);
		p.addPoint(x+40,y+60);
		g.fillPolygon(p);
	}
}
class Square extends Shape
{
	public Square(int _x, int _y) { super(_x, _y); }
	public void paint(Graphics g)
	{
		g.setColor(Color.red);
		g.fillRect(x,y,50,50);
	}
}
class Plus extends Shape
{
	public Plus(int _x, int _y) { super(_x, _y); }
	public void paint(Graphics g)
	{
		g.setColor(Color.green);
		g.fillRect(x,y+30,90,30);
		g.fillRect(x+30,y,30,90);
	}
}