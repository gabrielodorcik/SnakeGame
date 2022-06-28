import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{
	
	public Node[] nodeSnake = new Node[10];
	
	public boolean left, right, up, down;
	
	public int score = 0;
	
	public int macaX = 0, macaY = 0;
	
	public int spd = 10;
	
	public int frameSpd = 20;
	
	
	public Game() {
		this.setPreferredSize(new Dimension(240,240));
		for(int i =0; i < nodeSnake.length; i++) {
			nodeSnake[i] = new Node(0,0);
		}
		this.addKeyListener(this);
		
	}
	
	public void tick() {
		
		for(int i = nodeSnake.length - 1; i > 0; i--) {
			nodeSnake[i].x = nodeSnake[i-1].x;
			nodeSnake[i].y = nodeSnake[i-1].y;
		}
		
		if(nodeSnake[0].x + 10 < 0) {
			nodeSnake[0].x = 240;		
			
		}else if(nodeSnake[0].x >= 240) {
			nodeSnake[0].x = -10;
		}
		if(nodeSnake[0].y + 10 < 0) {
			nodeSnake[0].y = 240;
			
		}else if(nodeSnake[0].y >= 240) {
			nodeSnake[0].y = -10;
		}
		
		if(right) {
			nodeSnake[0].x+=spd;
			collision();
			
		}else if(up) {
			nodeSnake[0].y-=spd;
			collision();
			
		}else if(down){
			nodeSnake[0].y+=spd;
			collision();
			
		}else if(left) {
			nodeSnake[0].x-=spd;
			collision();
		}
		
		if(new Rectangle(nodeSnake[0].x, nodeSnake[0].y,10,10).intersects(new Rectangle(macaX, macaY, 10,10))) {
			macaX = new Random().nextInt(240-10);
			macaY = new Random().nextInt(240-10);
			score++;
			frameSpd++;
			System.out.println("Pontos: "+ score);
		}
		
	}
	
	public void collision() {
		for(int i = 0; i < nodeSnake.length; i++ ) {
			if(i == 0) 
				continue;
			Rectangle box1 = new Rectangle(nodeSnake[0].x, nodeSnake[0].y,10,10);
			Rectangle box2 = new Rectangle(nodeSnake[i].x, nodeSnake[i].y,10,10);
			
			if(box1.intersects(box2)) {
				System.out.println("Game Over!");
				frameSpd = 20;
				score = 0;
				right = false;
				left = false;
				up = false;
				down = false;
				for(int o =0; o < nodeSnake.length; o++) {
					nodeSnake[o] = new Node(0,0);
				}
			}
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, 240, 240);
		for(int i = 0; i < nodeSnake.length; i++) {
			g.setColor(Color.green);
			g.fillRect(nodeSnake[i].x, nodeSnake[i].y,10,10);
			
		}
		
		g.setColor(Color.red);
		g.fillRect(macaX, macaY, 10, 10);
		
		g.dispose();
		bs.show();
		
	}
	
	public static void main(String args[]) {
		Game game = new Game();
		JFrame frame = new JFrame("Snake");
		frame.add(game);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		new Thread(game).start();
	}
	

	@Override
	public void run() {
		
		while(true) {
			tick();
			render();
			try {
				Thread.sleep(1000/frameSpd);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
			left = false;
			up = false;
			down = false;
			
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			left = true;
			right = false;			
			up = false;
			down = false;
			
		}else if(e.getKeyCode() == KeyEvent.VK_UP){
			up = true;
			left = false;
			right = false;						
			down = false;
			
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			down = true;
			up = false;
			left = false;
			right = false;						
			
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
