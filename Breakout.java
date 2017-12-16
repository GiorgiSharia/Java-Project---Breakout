/*
 * File: Breakout.java
 * -------------------
 * Name:Giorgi Sharia
 * Section Leader:Giorgi Chanturia
 * 
 * This is well known game breakot.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
	int lifesLeft = NTURNS;
	
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	GRect paddle = new GRect(PADDLE_WIDTH,PADDLE_HEIGHT);
	
	double vy = 3;
	
	double vx = rgen.nextDouble(1.0,3);
	
	
	GOval ball = new GOval(2*BALL_RADIUS,2*BALL_RADIUS);
	
	int nBricks = NBRICK_ROWS*NBRICKS_PER_ROW;
	
	
/* Method: run() */
/** Runs the Breakout program. */
	/* this method adds mouse listeners so player is abble to control paddle*/
	public void init(){
		addMouseListeners();
	}
	/* This is run method where interface is created and whole gaming process is included*/
	public void run() {
		createInterface();
		for(; lifesLeft > 0; lifesLeft--) {
			playGame();
			if(nBricks == 0){
				break;
			}
		}
		showResult();
	}
	private void createInterface(){
		/*this method adds Colored rects and it has no meaning what row number equals , this method will create colored rects in specified queue*/
		createColoredRects();
		createPaddle();
	}
	/* this method creates and adds paddle to canvas */
	public void createPaddle(){
		double pStartX = (getWidth() - PADDLE_WIDTH)/2;
		double pStartY = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
		paddle.setFilled(true);
		add(paddle,pStartX,pStartY);
	}				
	/*this method adds Colored rects and it has no meaning what row number equals ,
	 *  this method will create colored rects in specified queue: Red,Orange,Yellow,Green and Cyan*/
	private void createColoredRects(){
		for(int j = 0; j < NBRICK_ROWS; j++){
			for(int i = 1; i <= NBRICKS_PER_ROW; i++){
				double width = (WIDTH - BRICK_WIDTH*NBRICKS_PER_ROW-(NBRICKS_PER_ROW-1)*BRICK_SEP)/2+ (NBRICKS_PER_ROW - i)*BRICK_SEP + (NBRICKS_PER_ROW-i)*BRICK_WIDTH;
				double height = BRICK_Y_OFFSET+ (NBRICK_ROWS - j)*(BRICK_SEP + BRICK_HEIGHT);
				GRect firstRowRects = new GRect(width,height,BRICK_WIDTH,BRICK_HEIGHT);
				firstRowRects.setFilled(true);
				if((NBRICK_ROWS - j) % 10 == 1 ^ (NBRICK_ROWS - j) % 10 == 2){
					firstRowRects.setColor(Color.RED);
					add(firstRowRects);
				}
				if((NBRICK_ROWS - j) % 10 == 3 ^ (NBRICK_ROWS - j) % 10 == 4){
					firstRowRects.setColor(Color.ORANGE);
					add(firstRowRects);
				}
				if((NBRICK_ROWS - j) % 10 == 5 ^ (NBRICK_ROWS - j) % 10 ==6){
					firstRowRects.setColor(Color.YELLOW);
					add(firstRowRects);
				}
				if((NBRICK_ROWS - j) % 10 == 7 ^ (NBRICK_ROWS - j) % 10 == 8){
					firstRowRects.setColor(Color.GREEN);
					add(firstRowRects);
				}
				if((NBRICK_ROWS - j) % 10 == 9 ^ (NBRICK_ROWS - j) % 10 ==0){
					firstRowRects.setColor(Color.CYAN);
					add(firstRowRects);
				}
			}
		}
	}
	/* In this method whole gaming process is declared,Ball movement won't start while mouse isn't clicked*/
	private void playGame(){
		waitForClick();
		ballMovement();
	}
	/*In the game you have 3 lifes, if you remove all rects while life > 0 you win and this method prints Winner, if you die 3 times it prints loser*/
	private void showResult(){
		if(lifesLeft == 0){
			GLabel loser = new GLabel("LOSER");
			add(loser,(getWidth() - loser.getWidth())/2,(getHeight() - loser.getHeight())/2);
		}
		if(nBricks == 0){
			GLabel winner = new GLabel("WINNER");
			add(winner,(getWidth() - winner.getWidth())/2,(getHeight() - winner.getHeight())/2);
		}
	}	
	/*This method depends paddle to mouse, so paddle is dragging mouse*/
	public void mouseMoved(MouseEvent e) {
		if (e.getX() + PADDLE_WIDTH/2 >= getWidth()){
			add(paddle,getWidth() - PADDLE_WIDTH,getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
		if(e.getX() - PADDLE_WIDTH/2 <= 0){
			add(paddle,0,getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
		if(e.getX() + PADDLE_WIDTH/2 < getWidth() && e.getX() - PADDLE_WIDTH/2 > 0){
			add(paddle,e.getX() - PADDLE_WIDTH/2,getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
	}
	/*this is method which provides whole game, it creates ball and makes it move, hit paddle and walls and remove rect */
	private void ballMovement(){
		double ballCoordinateX = (getWidth() - 2*BALL_RADIUS)/2;
		double ballCoordinateY = (getHeight() - 2*BALL_RADIUS)/2;
		ball = new GOval(ballCoordinateX,ballCoordinateY,2*BALL_RADIUS,2*BALL_RADIUS);
		ball.setFilled(true);
		add(ball,ballCoordinateX,ballCoordinateY);
		if (rgen.nextBoolean(0.5)) vx = -vx;
		while(true){
			ball.move(vx,vy);
			pause(15);
			ballColission();
			if(nBricks == 0){
				remove(ball);
				break;
			}
			if(ball.getY() + 2*BALL_RADIUS >= getHeight()){
				remove(ball);
				break;
			}
		}
	}
	/*this method is writte to check collission with walls paddle and rects*/
	private void ballColission(){
		checkForWalls();
		checkForPaddle();
		GObject rect = getColisionObject(ball);
		if(rect != null){
			if(rect != paddle){
				remove(rect);
			}
		}
	}
	/*checks if ball hited walls and if it changes direction of the ball*/
	private void checkForWalls(){
		if(ball.getX() <= 0){
			vx = -vx;
		}
		if(ball.getX() + 2*BALL_RADIUS >= getWidth()){
			vx = -vx;
		}
		if(ball.getY() <= 0 ){
			vy = -vy;
		}
	}
	/*checks if ball hited paddle and if it changes direction of the ball*/
	private void checkForPaddle(){
		if(getElementAt(ball.getX() + BALL_RADIUS, ball.getY() + 2*BALL_RADIUS) == paddle){
			vy = - vy;
			ball.setLocation(ball.getX(), ball.getY() - (ball.getY() + ball.getHeight() - paddle.getY()) + vy);
		}
		if(getElementAt(ball.getX() + BALL_RADIUS,ball.getY()) == paddle){
			vy = - vy;
			ball.setLocation(ball.getX(), ball.getY() - (ball.getY() + ball.getHeight() - paddle.getY()) + vy);
		}
		if(getElementAt(ball.getX() + 2*BALL_RADIUS,ball.getY() + BALL_RADIUS) == paddle){
			vy = - vy;
			ball.setLocation(ball.getX(), ball.getY() - (ball.getY() + ball.getHeight() - paddle.getY()) + vy);
		}
	}
	/*this method chechks for collision object, if it is rect it returns that rect, else it returns null*/
	private GObject getColisionObject(GOval ball){
		GObject rect = getElementAt(ball.getX(),ball.getY());
		if(rect != null && rect != paddle){
			vy = -vy;
			nBricks = nBricks - 1;
			return(rect);
		}else{
			rect = getElementAt(ball.getX(),ball.getY() + 2*BALL_RADIUS);
			if(rect != null && rect != paddle){
			vy = -vy;
			nBricks = nBricks - 1;
			return(rect);
			}else{
				rect = getElementAt(ball.getX() + 2*BALL_RADIUS,ball.getY());
				if(rect != null && rect != paddle){
					vy = -vy;
					nBricks = nBricks - 1;
					return(rect);
				}else{	
					rect = getElementAt(ball.getX() + 2*BALL_RADIUS,ball.getY() + 2*BALL_RADIUS);
					if(rect != null && rect != paddle){
						vy = -vy;
						nBricks = nBricks - 1;
						return(rect);
					}else{
						return null;
					}
				}
			}
		}
	}
}
