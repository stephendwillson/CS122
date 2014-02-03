import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class EasyKeys extends Applet {

	int x=100, y=100;

	public void init () {

		resize(800,800);
		setBackground(Color.black);

   addKeyListener (new KeyAdapter ()
    {
	 public void keyPressed (KeyEvent e) {

	  if (e.getKeyCode()==KeyEvent.VK_UP)
	  y -= 10;
	  if (e.getKeyCode()==KeyEvent.VK_DOWN)
	  y += 10;
	  if (e.getKeyCode()==KeyEvent.VK_LEFT)
	  x -= 10;
	  if (e.getKeyCode()==KeyEvent.VK_RIGHT)
	  x += 10;
      repaint();
                                         }
    }
);
                        }

     public void paint (Graphics g) {

       if (x>472)
       x=100;
       if (x<100)
       x=472;
       if (y>472)
       y=100;
       if (y<100)
       y=472;

	   g.setColor(Color.green);
	   g.drawString("Click the screen to begin. Press the directional",10,20);
	   g.drawString("keys to move the circle in a given direction. It",11,33);
	   g.drawString("will remain inside of the box.",11,46);

       g.setColor(Color.green);
       g.drawRect(100,100,400,400);

       g.setColor(Color.green);
	   g.fillOval(x,y,30,30);
	                                }
				                     }
