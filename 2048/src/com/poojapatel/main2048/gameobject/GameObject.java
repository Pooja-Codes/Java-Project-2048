package com.poojapatel.main2048.gameobject;

import java.util.Random;

import com.poojapatel.main2048.Main;
import com.poojapatel.main2048.game.Game;
import com.poojapatel.main2048.graphics.Renderer;
import com.poojapatel.main2048.graphics.Sprite;

public class GameObject {
	public double x,y;
	public int width,height;
	public Sprite sprite;
	public int value,speed=8;
	public boolean moving=false,remove=false,hasMoved=false;
	
	Random rand=new Random();
	
	public GameObject(double x,double y) {
		this.x=x;
		this.y=y;
		this.value=(rand.nextBoolean()?2:4);
		createSprite();
		this.width=sprite.width;
		this.height=sprite.height;
	}

	public void createSprite() {
		// TODO Auto-generated method stub
		if(this.value==2) {
			this.sprite=new Sprite(100,100,0xe5a8ff);
			if(Main.highestTile<2) Main.highestTile=2;
		}
		else if(this.value==4) {
			this.sprite=new Sprite(100,100,0xd77aff);
			if(Main.highestTile<4) Main.highestTile=4;
		}
		else if(this.value==8) {
			this.sprite=new Sprite(100,100,0xce5cff);
			if(Main.highestTile<8) Main.highestTile=8;
		}
		else if(this.value==16) {
			this.sprite=new Sprite(100,100,0xc02eff);
			if(Main.highestTile<16) Main.highestTile=16;
		}
		else if(this.value==32) {
			this.sprite=new Sprite(100,100,0x9d00e0);
			if(Main.highestTile<32) Main.highestTile=32;
		}
		else if(this.value==64) {
			this.sprite=new Sprite(100,100,0x8400bd);
			if(Main.highestTile<64) Main.highestTile=64;
		}
		else if(this.value==128) {
			this.sprite=new Sprite(100,100,0x6f009e);
			if(Main.highestTile<128) Main.highestTile=128;
		}
		else if(this.value==256) {
			this.sprite=new Sprite(100,100,0x590080);
			if(Main.highestTile<256) Main.highestTile=256;
		}
		else if(this.value==512) {
			this.sprite=new Sprite(100,100,0x440061);
			if(Main.highestTile<512) Main.highestTile=512;
		}
		else if(this.value==1024) {
			this.sprite=new Sprite(100,100,0x36004d);
			if(Main.highestTile<1024) Main.highestTile=1024;
		}
		else if(this.value==2048) {
			this.sprite=new Sprite(100,100,0x000000);
			Main.wait(180);
			if(Main.highestTile<2048) Main.highestTile=2048;
		}
	}

	public boolean canMove() {
		if(x<0 || x+width>Main.WIDTH||y<0||y+height>Main.HEIGHT) {
			return false;
		}
		for(int i=0;i<Game.objects.size();i++) {
			GameObject o=Game.objects.get(i);
			if(this==o) continue;
			if(x+width>o.x && x<o.x+o.width && y+height>o.y && y<o.y+o.height && value!=o.value ) {
				return false;
			}
		}
		return true;
	}
	public void update() {
		// TODO Auto-generated method stub
		if(Game.moving) {
			if(!hasMoved) {
				hasMoved=true;
			}
			if(canMove()) {
				moving= true;
			}
			if(moving) {
				if(Game.dir==0) x-=speed;
				if(Game.dir==1) x+=speed;
				if(Game.dir==2) y-=speed;
				if(Game.dir==3) y+=speed;
				
			}
			if(!canMove()) {
				moving=false;
				x=Math.round(x/100)*100;
				y=Math.round(y/100)*100;
			}
		}
	}
	public void render() {
		Renderer.renderSprite(this.sprite, (int) x, (int) y); //sprite,speed,height
	}
	
}
