package com.inspector.missioninsectible.misc;

import java.util.Random;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ITiledSpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Insect extends Sprite {

	public static final int BEETLE = 1;
	public static final int LADYBUG = 2;
	public static final int GRASSHOPPER = 3;
	public static final int BUTTERFLY = 4;
	public static final int BEE = 5;
	public static final int GOLDEN_DRAGONFLY = 6;
	public static final int TIME_INSECT = 7;
	
	/* Name of the insects */
	public static final String[] INSECT_NAME = new String[] {
		"Beetle", 
		"Ladybug", 
		"Grasshopper", 
		"Butterfly", 
		"Honey Bee", 
		"Golden Dragonfly", 
		"Time Insect"
	};
	
	/* Base score of the insects */
	public static final int[] INSECT_SCORE = new int[] {
		10,
		25,
		60,
		100,
		150,
		300,
		5
	};
	
	/* Seconds elapsed required to spawn the insects */
	public static final int[] SPAWN_TIME = new int[] {
		0,
		15,
		40,
		75,
		130,
		200,
		30
	};
	
	/* Speed of the insects */
	public static final float[] INSECT_SPEED = new float[] {
		0.30f,
		0.40f,
		0.75f,
		0.30f,
		1.10f,
		1.50f,
		0.75f,
	};
	
	private int type;
	private String name;
	private int score;
	private int spawnTime;
	private float speed;
	
	public Insect(float initX, float initY, ITextureRegion textureRegion, VertexBufferObjectManager vbmo, int type) {
		super(initX, initY, textureRegion, vbmo);
		this.type = type;
		this.name = Insect.INSECT_NAME[type];
		this.score = Insect.INSECT_SCORE[type];
		this.spawnTime = Insect.SPAWN_TIME[type];
		this.speed = Insect.INSECT_SPEED[type];
	}
	
	public int getType() {
		return this.type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int getSpawnTime() {
		return this.spawnTime;
	}
	
	public float getSpeed() {
		return this.speed;
	}
	
	public void move() {
		Random r = new Random();
		if(r.nextInt(2) == 0)
			this.setPosition(this.getX() - getSpeed(), this.getY());
		else
			this.setPosition(this.getX() + getSpeed(), this.getY());
		
		// akan digunakan segera
//		switch (type) {
//			case BEETLE : {
//				if(r.nextInt(2) == 0)
//					this.setPosition(this.getX() - getSpeed(), this.getY());
//				else
//					this.setPosition(this.getX() + getSpeed(), this.getY());
//			} break;
//			case LADYBUG : {
//				if(r.nextInt(2) == 0)
//					this.setPosition(this.getX() - getSpeed(), this.getY());
//				else
//					this.setPosition(this.getX() + getSpeed(), this.getY());
//			} break;
//			case GRASSHOPPER : {
//				if(r.nextInt(2) == 0)
//					this.setPosition(this.getX() - getSpeed(), this.getY());
//				else
//					this.setPosition(this.getX() + getSpeed(), this.getY());
//			} break;
//			case BUTTERFLY : {
//				if(r.nextInt(2) == 0)
//					this.setPosition(this.getX() - getSpeed(), this.getY());
//				else
//					this.setPosition(this.getX() + getSpeed(), this.getY());
//			} break;
//			case BEE : {
//				if(r.nextInt(2) == 0)
//					this.setPosition(this.getX() - getSpeed(), this.getY());
//				else
//					this.setPosition(this.getX() + getSpeed(), this.getY());
//			} break;
//			case GOLDEN_DRAGONFLY : {
//				if(r.nextInt(2) == 0)
//					this.setPosition(this.getX() - getSpeed(), this.getY());
//				else
//					this.setPosition(this.getX() + getSpeed(), this.getY());
//			} break;
//			case TIME_INSECT : {
//				if(r.nextInt(2) == 0)
//					this.setPosition(this.getX() - 1.25f, this.getY());
//				else
//					this.setPosition(this.getX() + 1.25f, this.getY());
//			} break;
//		}
	}
}
