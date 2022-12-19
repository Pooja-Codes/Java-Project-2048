package com.poojapatel.main2048;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.poojapatel.main2048.game.Game;
import com.poojapatel.main2048.input.Keyboard;

public class Main extends Canvas implements Runnable{
	public static final int WIDTH=400,HEIGHT=400;
	public static float scale=2.0f;
	
	public JFrame frame;
	public Thread thread;
	public Keyboard key;
	public Game game;
	public boolean running=false;
	public static BufferedImage image=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	public static int[] pixels=((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	public static int highestTile;
	
	public Main() {
		setPreferredSize(new Dimension((int)(WIDTH*scale),(int)(HEIGHT*scale)));
		frame=new JFrame();
		game=new Game();
		key= new Keyboard();
		addKeyListener(key);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long lastTimeNanoSeconds=System.nanoTime();
		long timer=System.currentTimeMillis();
		double nanoSecondsPerUpdate=1000000000.0/60.0;
		double updatesToPerform=0.0;
		int frames=0;
		int updates=0;
		requestFocus();
		while(running) {
			long currentTimeInNanoSeconds=System.nanoTime();
			updatesToPerform+=(currentTimeInNanoSeconds-lastTimeNanoSeconds)/nanoSecondsPerUpdate;
			if(updatesToPerform>=1) {
				update();
				updates++;
				updatesToPerform--;
			}
			lastTimeNanoSeconds=currentTimeInNanoSeconds;
			
			render();
			frames++;
			
			if(System.currentTimeMillis()-timer>1000){//the second is passed
				frame.setTitle("Pooja's 2048 : "+updates+" updates, "+frames+" frames"+" || Highest Tile On Board: "+highestTile);
				updates=0;
				frames=0;
				timer+=1000;
			}
		}
	}
	
	public void start() {
		running=true;
		thread=new Thread(this,"loopThread");
		thread.start();
	}
	
	public void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update(){
		game.update();
		key.update();
	}
	
	public void render() {
		BufferStrategy bs=getBufferStrategy();
		if(bs==null) {
			createBufferStrategy(3);
			return;
		}
		game.render();
		
		Graphics2D g=(Graphics2D) bs.getDrawGraphics();
		g.drawImage(image,0,0,(int) (WIDTH*scale),(int) (HEIGHT*scale),null);
		game.renderText(g);
		g.dispose();
		bs.show();
	}
	public static void createMessageBox(String msg)
	{
		EventQueue.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	        	JFrame frame = new JFrame();
	    		JLabel lbl = new JLabel(msg); //via frame
	    		frame.add(lbl);
	    		frame.setSize(300,300);
	    		frame.setVisible(true);
	        }
	    });
		
	}
	public static void wait(int ms)
	{
	    try
	    {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	}
	
	public static void main(String[] args) {
		Main main=new Main();
		main.frame.setResizable(false);
		main.frame.setTitle("Pooja's 2048");
		main.frame.add(main);
		main.frame.pack();
		main.frame.setVisible(true);
		main.frame.setLocationRelativeTo(null);
		main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.frame.setAlwaysOnTop(true);
		main.start();
	}
}
