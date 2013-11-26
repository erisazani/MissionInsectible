package com.inspector.missioninsectible.misc;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.vbo.ITiledSpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public class Insect {

	public static final int BEETLE = 1;
	public static final int LADYBUG = 2;
	public static final int GRASSHOPPER = 3;
	public static final int BUTTERFLY = 4;
	public static final int HONEY_BEE = 5;
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
	public static final int[] INSECT_SPEED = new int[] {
		100,
		100,
		200,
		100,
		300,
		400,
		300,
	};
	
	private int type;
	private String name;
	private int score;
	private int spawnTime;
	private int speed;
	
	public Insect(int type) {
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
	
	public int getSpeed() {
		return this.speed;
	}
}
