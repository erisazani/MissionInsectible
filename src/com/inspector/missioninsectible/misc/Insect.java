package com.inspector.missioninsectible.misc;

import java.util.Random;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ITiledSpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Insect extends AnimatedSprite {

	public static final int BEETLE = 1;
	public static final int LADYBUG = 2;
	public static final int GRASSHOPPER = 3;
	public static final int BUTTERFLY = 4;
	public static final int BEE = 5;
	public static final int GOLDEN_DRAGONFLY = 6;
	public static final int TIME_INSECT = 7;
	
	/* Name of the insects */
	public static final String[] INSECT_NAME = new String[] {
		"",
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
		0,
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
		0,
		10,
		20,
		40,
		55,
		70,
		30
	};
	
	/* Speed of the insects */
	public static final float[] INSECT_SPEED = new float[] {
		0f,
		0.60f,
		0.80f,
		2.00f,
		0.80f,
		2.20f,
		3.00f,
		1.50f,
	};
	
	private int type;
	private String name;
	private int score;
	private int spawnTime;
	private float speed;
	
	private float spriteXScale = 1.0f;
	private int moveDirection;
	private float moveTime;
	
	public Insect(float initX, float initY, TiledTextureRegion textureRegion, VertexBufferObjectManager vbmo, int type) {
		super(initX, initY, textureRegion, vbmo);
		this.type = type;
		this.name = Insect.INSECT_NAME[type];
		this.score = Insect.INSECT_SCORE[type];
		this.spawnTime = Insect.SPAWN_TIME[type];
		this.speed = Insect.INSECT_SPEED[type];
		
		moveDirection = 0;
		moveTime = 100;
		animate(100);
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
//		if(r.nextInt(2) == 0) {
//			this.setPosition(this.getX() - getSpeed(), this.getY());
////			spriteXScale = -1.0f;
////			setScaleX(spriteXScale);
//		}
//		else {
//			this.setPosition(this.getX() + getSpeed(), this.getY());
////			spriteXScale = 1.0f;
////			setScaleX(spriteXScale);
//		}
		
		// akan digunakan segera
		if(moveTime >= 0) {
			moveTime--;
			switch (type) {
				case BEETLE : {
					if(moveDirection == 0)
						this.setPosition(this.getX() - getSpeed(), this.getY());
					else if(moveDirection == 1)
						this.setPosition(this.getX() + getSpeed(), this.getY());
				} break;
				case LADYBUG : {
					if(moveDirection == 0)
						this.setPosition(this.getX() - getSpeed(), this.getY());
					else if(moveDirection == 1)
						this.setPosition(this.getX() + getSpeed(), this.getY());
				} break;
				case GRASSHOPPER : {
					if(moveDirection == 0)
						this.setPosition(this.getX() - getSpeed(), this.getY());
					else if(moveDirection == 1)
						this.setPosition(this.getX() + getSpeed(), this.getY());
				} break;
				case BUTTERFLY : {
					if(moveDirection == 0)
						this.setPosition(this.getX() - getSpeed(), this.getY());
					else if(moveDirection == 1)
						this.setPosition(this.getX() + getSpeed(), this.getY());
				} break;
				case BEE : {
					if(moveDirection == 0)
						this.setPosition(this.getX() - getSpeed(), this.getY());
					else if(moveDirection == 1)
						this.setPosition(this.getX() + getSpeed(), this.getY());
				} break;
				case GOLDEN_DRAGONFLY : {
					if(moveDirection == 0)
						this.setPosition(this.getX() - getSpeed(), this.getY());
					else if(moveDirection == 1)
						this.setPosition(this.getX() + getSpeed(), this.getY());
				} break;
				case TIME_INSECT : {
					if(moveDirection == 0)
						this.setPosition(this.getX() - getSpeed(), this.getY());
					else if(moveDirection == 1) 
						this.setPosition(this.getX() + getSpeed(), this.getY());
				} break;
			}
		} else {
			moveTime = r.nextInt(100) + 100;
			moveDirection = r.nextInt(3);
			if(moveDirection == 0) {
				spriteXScale = -1.0f;
				setScaleX(spriteXScale);
			} else if(moveDirection == 1) {
				spriteXScale = 1.0f;
			}
			setScaleX(spriteXScale);
		}
	}
}
