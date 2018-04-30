package alright;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LetsDoItBitch extends JPanel implements MouseListener{

	static Random rand = new Random();
	static ArrayList<Die> dice = new ArrayList<Die>();
	static int DISPLAY_WIDTH = 500;
	static int DISPLAY_HEIGHT = 500;
	static final long PHYS_SLEEP = 16;
	static float gravity = 1f;
	static float elasticity = 0.8f;
	static int lastMouseX = 0;
	static int lastMouseY = 0;
	static long lastClickTime = 0;
	public static int[] counts = new int[6];
	public static BufferedImage[] dieSides = new BufferedImage[6];
	public static void main(String args[]) {
		// fine(20, 5, 6);
		for(int i = 0; i < 6;i++){
			counts[i] = 0;
			BufferedImage img = null;
			try {
			    img = ImageIO.read(new File("C:\\Users\\Ewan\\Downloads\\Die"+(i+1) + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			dieSides[i] = img;
		}
		JFrame f = new JFrame("It's time to die");
		LetsDoItBitch ldib = new LetsDoItBitch();
		f.addMouseListener(ldib);
		f.add(ldib);
		f.setVisible(true);
		f.pack();
		f.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dice.add(new Die(10, 10, 10, 10));
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					f.repaint();
				}
			}
		}).start();
		;
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					ldib.update();
					try {
						Thread.sleep(PHYS_SLEEP);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		;
	}



	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawLine(0, DISPLAY_HEIGHT + 2, this.getWidth(),DISPLAY_HEIGHT + 2);
		for (Die d : dice) {
			g2.fillRect((int) (d.x - d.size), (int) (d.y -  d.size), d.size, d.size);
			g2.drawImage(dieSides[d.side],(int) (d.x - d.size), (int) (d.y -  d.size),null);
		}
		int xOff = 20;
		int yOff = 55;
		g2.setColor(Color.BLACK);
		g2.drawString("Roll table",10,40);
		for(int i = 0; i < 6;i++){
			g2.drawString(i+1 + ":  "+counts[i],xOff,yOff);
			yOff += 10;
		}
	}

	public static void update() {
		
		for (Die d : dice) {
			d.update();
			if(d.y > DISPLAY_HEIGHT){
				d.ySpeed = -d.ySpeed * elasticity;
				d.x = (d.lastX + d.x) / 2;
				d.y = (d.lastY + d.y) / 2;
			}
			else if(!d.landed){
				d.ySpeed += gravity;
			}
			if(Math.abs(d.ySpeed) < 0.2f && Math.abs(d.y - DISPLAY_HEIGHT)< 2 && !d.landed){
				d.y = DISPLAY_HEIGHT;
				d.landed = true;
				d.ySpeed = 0.0f;
				counts[d.side]++;
			}
		}
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		lastMouseX = (int)MouseInfo.getPointerInfo().getLocation().getX();
		lastMouseY = (int)MouseInfo.getPointerInfo().getLocation().getY();
		lastClickTime = System.nanoTime();
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		int mouseX = (int)MouseInfo.getPointerInfo().getLocation().getX() - lastMouseX;
		int mouseY = (int)MouseInfo.getPointerInfo().getLocation().getY() - lastMouseY;
		double timeElapsed = (System.nanoTime() - lastClickTime) / 10000000d;
		dice.add(new Die(e.getX(),e.getY() - 30,(float)mouseX / (float)timeElapsed,(float)mouseY / (float)timeElapsed));
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}

//public static void fine(int dieRolls, int min, int max) {
//int wins = 0;
//for (int i = 0; i < dieRolls; i++) {
//	int dieResult = rand.nextInt(6) + 1; // from 1-6
//	boolean win;
//	if (dieResult >= min && dieResult <= max) {
//		win = true;
//		wins++;
//	} else
//		win = false;
//	System.out.println("#" + (i + 1) + ": " + dieResult + " = " + win);
//}
//System.out.println("Total successes : " + wins);
//}