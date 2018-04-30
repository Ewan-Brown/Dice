package alright;

import java.awt.Color;
import java.util.Random;

public class Die {
	public Die(float x, float y, float xSpeed, float ySpeed){
		this.x = x;
		this.y = y;
		lastX = x;
		lastY = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		side = rand.nextInt(LetsDoItBitch.dieSides.length);
	}
	static Random rand = new Random();
	public int flipTime = 0;
	public int maxFlipTime = 100;
	public int side;
	float x;
	Color c = Color.RED;
	int size = 10;
	float y;
	float xSpeed;
	float ySpeed;
	float friction = 100;
	float lastX;
	float lastY;
	boolean landed = false;
	public void update(){
		flipTime--;
		if(flipTime < 0 && !landed){
			side = rand.nextInt(LetsDoItBitch.dieSides.length);
		}
		lastX = x;
		lastY = y;
		x += xSpeed;
		y += ySpeed;
		xSpeed -= xSpeed / friction;
		ySpeed -= ySpeed / friction;
	}
}
