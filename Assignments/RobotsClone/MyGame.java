import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Image;
import java.applet.AudioClip;

@SuppressWarnings("serial")
public class MyGame extends Applet implements KeyListener, MouseListener
{
	Me me;
	Robots robots[];
	
	//if SIZE or MOVE change, make sure to change in other classes, as well
	final int SIZE = 10;
	final int MOVE = 30;
	int teleports = 3;
	int movect = 0;
	int level = 1;
	boolean paused = false;
	boolean winrar = false;
	boolean loser = false;
	
	//more double buffer stuff
	Image offi;
	Graphics offg;
	
	public void init()
	{
		//reset
		teleports = 3;
		movect = 0;
		paused = false;
		winrar = false;
		loser = false;
		Robots.explodedct=0;
		
		//loading images
		Image man = getImage(getCodeBase(), "man.gif");
		Image robot = getImage(getCodeBase(), "robot.gif");
		Image exploded = getImage(getCodeBase(), "exploded.gif");
		if (man == null || robot == null || exploded == null)
		{
			System.out.println("Picture does not exist!");
		}
		else
		{
			System.out.println("Images loaded.");
		}
		
		//loading and playing sound clip
		AudioClip bgtrack = getAudioClip(getCodeBase(), "mud.wav");
		AudioClip explode = getAudioClip(getCodeBase(), "bomb.wav");
		if (bgtrack == null || explode == null)
		{
			System.out.println("Failed to load backing track!");
		}
		else
		{
			System.out.println("Loaded Primus and explosions.");
		}
		
		bgtrack.loop();
		
		//initializing
		me = new Me(man);
		robots = new Robots[SIZE];
		
		//screen size and background setup
		resize(815,610);
		setBackground(Color.black);
		
		//double buffer stuff
		offi = createImage(815,610);
		offg = offi.getGraphics();
		
		//listener stuff
		if(level==1)
		{
			addKeyListener(this);
			addMouseListener(this);
		}
		
		//initializes each robot and assigns random position
		for(int i = 0; i < SIZE; i++)
		{
			robots[i] = new Robots(robot, exploded, explode);
			robots[i].x = (int)(Math.random()*27)*30+5;
			robots[i].y = (int)(Math.random()*20)*30+5;
		}
	}
	
	public void paint (Graphics g)
	{
		//double buffer stuff
		offg.setColor(Color.black);
		offg.fillRect(0,0,815,610);
		
		//setup for grid
		for(int i = 0; i < 28; i++)
		{
			for(int j = 0; j < 21; j++)
			{
				offg.setColor(Color.blue);
				offg.drawLine(0,MOVE*j ,810 ,MOVE*j);
			
				offg.setColor(Color.blue);
				offg.drawLine(MOVE*i, 0, MOVE*i, 600);
			}
		}
		
		//telling people 'bout helpy stuff and moves (score)
		offg.setFont (new Font ("Sans", Font.PLAIN, 12));
		offg.setColor(Color.green);
		offg.drawString("Press 'h' at any time for help.",30,580);
		offg.drawString("Move count:  "+movect, 700, 580);
		
		//tells user when teleports are gone
		if(teleports==0)
		{
			offg.setColor(Color.green);
			offg.setFont (new Font ("Sans", Font.PLAIN, 10));
			offg.drawString("Out of teleports! Good luck!",30,30);
		}
		
		//WINNAR
		if(Robots.explodedct == SIZE)
		{
			offg.setColor(Color.green);
			offg.setFont (new Font ("Serif", Font.PLAIN, 40));
			offg.drawString("GAME OVER, DUDE!",200,300);
			offg.drawString("A winrar is you!", 250, 350);
			
			offg.setColor(Color.black);
			offg.fillRect(260,360,300,50);
			offg.setColor(Color.green);
			offg.setFont(new Font("Serif", Font.PLAIN, 30));
			offg.drawString("Click for new game",270,395);
			winrar=true;
		}
		
		//sad-face loser time
		if(loser)
		{
			offg.setColor(Color.green);
			offg.setFont (new Font ("Serif", Font.PLAIN, 40));
			offg.drawString("GAME OVER, DUDE!",200,300);
			offg.drawString("A loser is you!", 250, 350);
		}
			
		//calls for painting me and robots
		me.paint(offg);
		
		for(int i = 0; i < SIZE; i++)
		{
			robots[i].paint(offg);
		}
		
		//pause stuff
		if (paused)
		{
			offg.setColor(Color.black);
			offg.fillRect(0,0,815,610);
			offg.setFont (new Font ("Sans", Font.ITALIC, 100));
			offg.setColor(Color.magenta);
			offg.drawString("Paused", 200,300);
		}
		
		//double buffer stuff
		g.drawImage(offi, 0, 0, this);
		repaint();
	}
	
	//double buffer update method
	public void update(Graphics g)
	{
		paint(g);
	}

	//all sorts of keypressed all over the place
	public void keyPressed(KeyEvent ke) 
	{		
		if(!paused && !winrar && !loser)
		{
			//movement keys--moves 1 cell at a time, staying in bounds of grid
			//moves robots one cell for every cell I move
			if(ke.getKeyCode()==KeyEvent.VK_UP && me.y >= 29)
			{
				if (collidecheck())
					return;
				
				me.y-=MOVE;
				movect++;
				
				movings();
			}
			
			if(ke.getKeyCode()==KeyEvent.VK_DOWN && me.y <= 550)
			{
				if (collidecheck())
					return;
				
				me.y+=MOVE;
				movect++;
				
				movings();
			}
				
			if(ke.getKeyCode()==KeyEvent.VK_LEFT && me.x >= 29)
			{
				if (collidecheck())
					return;	
				
				me.x-=MOVE;
				movect++;
				
				movings();
			}
			
			if(ke.getKeyCode()==KeyEvent.VK_RIGHT && me.x <= 781)
			{
				if (collidecheck())
					return;	
				
				me.x+=MOVE;
				movect++;
				
				movings();
			}
		}
	}

	public void keyReleased(KeyEvent ke) {}
	
	//more keypressed stuff for pause, teleport, help, and diagonal movement
	public void keyTyped(KeyEvent ke) 
	{
		//pauses/unpauses game
		if(!winrar && !loser)
		{
			if (paused && ke.getKeyChar() == 'p' || ke.getKeyChar() == 'P')
				paused = false;
			else if (!paused && ke.getKeyChar() == 'p' || ke.getKeyChar() == 'P')
				paused = true;
		}
		
		//teleports character
		if((ke.getKeyChar() == 't' || ke.getKeyChar() == 'T') && teleports > 0)
		{
			me.x = (int)(Math.random()*27)*30+5;
			me.y = (int)(Math.random()*20)*30+5;
			teleports--;
		}
		
		//pops up help window
		if (ke.getKeyChar() == 'h' || ke.getKeyChar() == 'H')
		{
			popup.show();
			popup.window();
		}
		
		if(!paused && !winrar && !loser)
		{
			//diagonal movement keys--up and left
			if((ke.getKeyChar() == 'w' || ke.getKeyChar() == 'W') &&  me.y >= 29 && me.x >= 29)
			{
				if (collidecheck())
					return;
				
				me.x-=MOVE;
				me.y-=MOVE;
				movect++;
				
				movings();
			}
			
			//up and right
			if((ke.getKeyChar() == 'e' || ke.getKeyChar() == 'E') && me.y >= 29 && me.x <= 781)
			{
				if (collidecheck())
					return;
				
				me.x+=MOVE;
				me.y-=MOVE;
				movect++;
				
				movings();
			}
			
			//down and left
			if((ke.getKeyChar() == 's' || ke.getKeyChar() == 'S') && me.x >= 29 && me.y <= 550)
			{
				if (collidecheck())
					return;	
				
				me.x-=MOVE;
				me.y+=MOVE;
				movect++;
				
				movings();
			}
			
			//down and right
			if((ke.getKeyChar() == 'd' || ke.getKeyChar() == 'D') && me.x <= 781 && me.y <= 550)
			{
				if (collidecheck())
					return;
				
				me.x+=MOVE;
				me.y+=MOVE;
				movect++;
				
				movings();
			}
		}
	}
	
	//bunches of mouse clicked stuff for new game
	public void mouseEntered(MouseEvent me){}
	public void mouseExited(MouseEvent me){}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){}
	public void mouseClicked(MouseEvent me)
	{
		if(me.getX() > 260 && me.getX() < 560 && me.getY() > 360 && me.getY() < 410 && winrar)
		{
			level=2;
			init();
		}
	}
	
	//calls a separate method for robots to move
	public void movings ()
	{
		for(int i = 0; i < SIZE; i++)
		{
			robots[i].move(robots, me.x, me.y);
		}
	}
	
	//checks for collision
	public boolean collidecheck ()
	{
		for(int i =0; i < SIZE; i++)
		{
			if (me.y == robots[i].y && me.x == robots[i].x && teleports==0)
			{
				loser=true;
			}
			else if(me.y == robots[i].y && me.x == robots[i].x)
			{
				return true;
			}
		}
		return false;
	}
}

class Me
{
	//assigns random location every time applet is run and loads 'me' picture
	int x = (int)(Math.random()*27)*30+5;
	int y = (int)(Math.random()*20)*30+5;
	Image manPic;
	
	//constructor
	public Me(Image man)
	{
		manPic=man;
	}
	
	//painties
	public void paint (Graphics g)
	{
		g.drawImage(manPic,x,y,null);
	}
}

class Robots
{
	//more initializings
	int x,y;
	static int explodedct;
	boolean exploded=false;
	final int MOVE = 30;
	final int SIZE = 10;
	Image robotPic;
	Image explodedBot;
	AudioClip boom;
	
	//constructor all over the place
	public Robots(Image robots, Image exploded, AudioClip explosion)
	{
		robotPic=robots;
		explodedBot=exploded;
		boom=explosion;
	}
	
	//paint raid!
	public void paint(Graphics g)
	{
		if(!exploded)
			g.drawImage(robotPic,x,y,null);
		if(exploded)
			g.drawImage(explodedBot,x,y,null);
	}
	
	//tells robots how to move
	public void move (Robots robots[], int myx, int myy)
	{
		int newx=x, newy=y;
		
		if(!exploded)
		{
			if(x < myx)
				newx=x+MOVE;
			if(x > myx)
				newx=x-MOVE;
			if(y < myy)
				newy=y+MOVE;
			if(y > myy)
				newy=y-MOVE;
		}
		
		//if two robots after moving occupy same space, end function.
		//plays explodey sounds when robots collide
		for(int i =0; i < SIZE; i++)
			if (newx == robots[i].x && newy == robots[i].y && !exploded)
			{
				boom.play();
				exploded=true;
				explodedct++;
				return;
			}
		
		x = newx; 
		y = newy;
	}
}

//tons of popup stuff
class popup
{
	static boolean bacon=false;
	
	public static void window()
	{
		JFrame help = new JFrame("Robot Explosion Party 9000!");
   	 	help.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
   	 	JLabel emptyLabel = new JLabel("Robot invaders explode when they touch each other. 'p' pauses, 't' teleports. " +
   	 			" You get 3 teleports to start. You MUST teleport if in the same cell as a robot.");
	    emptyLabel.setPreferredSize(new Dimension(975, 20));
	    help.getContentPane().add(emptyLabel, BorderLayout.CENTER);

	    help.pack();
	    help.setVisible(bacon);
	}

	public static void show ()
	{
		bacon=true;
	}
}

//menubar awt import