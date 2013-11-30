package com.inspector.missioninsectible.scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.augmentedreality.BaseAugmentedRealityGameActivity;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.adt.color.Color;

import android.content.Context;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.NetworkInfo.DetailedState;
import android.util.Log;

import com.inspector.missioninsectible.MainGameActivity;
import com.inspector.missioninsectible.misc.Insect;


public class PlayScene extends BaseAugmentedRealityGameActivity implements SensorEventListener, IAccelerationListener {
	
	private final int FONT_SIZE = 24;
	private final int FONT_COMBO_SIZE = 40;
	
	private final float INSECT_WIDTH = 32.0f;
	
	MainGameActivity activity;
	private Scene gameScene;
	
	private ITexture beetleTexture;
	private ITexture ladybugTexture;
	private ITexture grasshopperTexture;
	private ITexture butterflyTexture;
	private ITexture beeTexture;
	private ITexture goldenDragonflyTexture;
	private ITexture timeTexture;
	
	public ITexture mFontTexture;
	public ITexture mComboFontTexture;
	public ITextureRegion beetleTextureRegion;
	public ITextureRegion ladybugTextureRegion;
	public ITextureRegion grasshopperTextureRegion;
	public ITextureRegion butterflyTextureRegion;
	public ITextureRegion beeTextureRegion;
	public ITextureRegion goldenDragonflyTextureRegion;
	public ITextureRegion timeTextureRegion;

	private float accX, accY, accZ, accPrevX, accPrevY, accPrevZ, dx, dy, dz;
	private float vX, vY, vZ, vPrevX, vPrevY, vPrevZ, dvx, dvy, dvz;
	private Rectangle rect;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
//	private Sensor mAcceleration;
	
//	private Sprite face;
	private Font mFont;
	private Font mComboFont;
	
	private Text scoreLblText, scoreText, comboText;
	private ArrayList<Insect> insects;
	private Insect insect;
	private int ctype = 0;
	private int amount = 0;
	private int combo = 1;
	public int score = 0;
	
	private Text mText;
	private int countSec = 10;
	private int gameSec = 60;
	private boolean gameStart = false;
	
	private boolean isCatching;
	
	// dummy variables, delete later
//	Text xt, yt, zt;
	int count;
	
	public PlayScene() {
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		Log.d("debug", "masuk activity PlayScene");
		activity = MainGameActivity.getSharedInstance();
		
		//get the sensor service
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		//get the accelerometer sensor
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//		mAcceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
				
		return activity.engineOptions;
	}

	@Override
	protected void onCreateResources() throws IOException {
		Log.d("debug", "masuk PlayScene.onCreateResources");
		try {
			beetleTexture = new AssetBitmapTexture(getTextureManager(), getAssets(), "image/sprite/sprite_beetle.png");
			ladybugTexture = new AssetBitmapTexture(getTextureManager(), getAssets(), "image/sprite/sprite_ladybug.png");
			grasshopperTexture = new AssetBitmapTexture(getTextureManager(), getAssets(), "image/sprite/sprite_grasshopper.png");
			butterflyTexture = new AssetBitmapTexture(getTextureManager(), getAssets(), "image/sprite/sprite_butterfly.png");
			beeTexture = new AssetBitmapTexture(getTextureManager(), getAssets(), "image/sprite/sprite_bee.png");
			goldenDragonflyTexture = new AssetBitmapTexture(getTextureManager(), getAssets(), "image/sprite/sprite_golden_dragonfly.png");
			timeTexture = new AssetBitmapTexture(getTextureManager(), getAssets(), "image/sprite/sprite_time.png");
			Log.d("Texture", "Texture Loaded");
		} catch (IOException e) {
			e.printStackTrace();
			Log.d("Texture", "Texture Not Loaded");
		}
		beetleTextureRegion = TextureRegionFactory.extractFromTexture(beetleTexture);
		ladybugTextureRegion = TextureRegionFactory.extractFromTexture(ladybugTexture);
		grasshopperTextureRegion = TextureRegionFactory.extractFromTexture(grasshopperTexture);
		butterflyTextureRegion = TextureRegionFactory.extractFromTexture(butterflyTexture);
		beeTextureRegion = TextureRegionFactory.extractFromTexture(beeTexture);
		goldenDragonflyTextureRegion = TextureRegionFactory.extractFromTexture(goldenDragonflyTexture);
		timeTextureRegion = TextureRegionFactory.extractFromTexture(timeTexture);
		
		beetleTexture.load();
		ladybugTexture.load();
		grasshopperTexture.load();
		butterflyTexture.load();
		beeTexture.load();
		goldenDragonflyTexture.load();
		timeTexture.load();
		
		this.mFontTexture = new BitmapTextureAtlas(this.mEngine.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mComboFontTexture = new BitmapTextureAtlas(this.mEngine.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		this.mFontTexture.load();
		this.mComboFontTexture.load();
		
		this.mFont = new Font(this.mEngine.getFontManager(), this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), FONT_SIZE, true, Color.WHITE);
		this.mComboFont = new Font(this.mEngine.getFontManager(), this.mComboFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), FONT_COMBO_SIZE, true, Color.WHITE);
		
		this.mEngine.getFontManager().loadFont(mFont);
		this.mEngine.getFontManager().loadFont(mComboFont);
	}

	@Override
	protected Scene onCreateScene() {
		Log.d("debug", "masuk PlayScene.onCreateScene");
		
		scoreLblText = new Text(50, activity.mCamera.getHeight() - FONT_SIZE / 2, mFont, "Score" , this.mEngine.getVertexBufferObjectManager());
		scoreText = new Text(50, activity.mCamera.getHeight() - (int)(FONT_SIZE * 2.5 / 2), mFont, "" + score , 7, this.mEngine.getVertexBufferObjectManager());
		comboText = new Text(25, FONT_COMBO_SIZE / 2, mComboFont, "X" + combo , 7, this.mEngine.getVertexBufferObjectManager());
		mText = new Text(activity.mCamera.getWidth()/2, activity.mCamera.getHeight()/2 - FONT_SIZE / 2, mFont, "" + countSec, 3, this.mEngine.getVertexBufferObjectManager());
		
//		xt = new Text(activity.mCamera.getWidth()/2, activity.mCamera.getHeight()/2 - FONT_SIZE / 2 + 30, mFont, "dX: " + dvx, 20, this.mEngine.getVertexBufferObjectManager());
//		yt = new Text(activity.mCamera.getWidth()/2, activity.mCamera.getHeight()/2 - FONT_SIZE / 2, mFont, "dY: " + dvy, 20, this.mEngine.getVertexBufferObjectManager());
//		zt = new Text(activity.mCamera.getWidth()/2, activity.mCamera.getHeight()/2 - FONT_SIZE / 2 - 30, mFont, "dZ: " + dvz, 20, this.mEngine.getVertexBufferObjectManager());
		
		
		// perbaiki letaknya!
		rect= new Rectangle(activity.mCamera.getWidth()/2, activity.mCamera.getHeight()/2, 50.0f, 50.0f, activity.getVertexBufferObjectManager());
		
		mText.setColor(Color.RED);
		scoreLblText.setColor(Color.GREEN);
		scoreText.setColor(Color.GREEN);
		comboText.setColor(Color.PINK);
		
		gameScene = new Scene();
		gameScene.setBackground(new Background(0.0f, 0.0f, 0.0f, 0.0f));

//		final float centerX = (activity.mCamera.getWidth() - beetleTextureRegion.getWidth()) / 2;
//		final float centerY = (activity.mCamera.getHeight() - beetleTextureRegion.getHeight()) / 2;
		
		Random r = new Random();
		float initX = r.nextFloat() * (activity.mCamera.getWidth() - INSECT_WIDTH);
		float initY = (r.nextFloat() * (activity.mCamera.getHeight()- INSECT_WIDTH)) + INSECT_WIDTH;
		insect = new Insect(initX, initY, ladybugTextureRegion, activity.getVertexBufferObjectManager(), 1);
		Log.d("insect", "buat ladybug di posisi " + initX + "," + initY);
		
		gameScene.attachChild(rect);
		gameScene.attachChild(mText);
		gameScene.attachChild(scoreLblText);
		gameScene.attachChild(scoreText);
		gameScene.attachChild(comboText);
		
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.mEngine.registerUpdateHandler(new TimerHandler(1, true, new ITimerCallback() {
	        public void onTimePassed(TimerHandler pTimerHandler) {
	        	countSec--;
	        	mText.setText("" + countSec);
	        	if(countSec == 3) {
					mText.setColor(Color.GREEN);
	        	}
				if(countSec < 0) {
					gameScene.detachChild(mText);
					mText.setPosition(activity.mCamera.getWidth()/2, activity.mCamera.getHeight() - FONT_SIZE / 2 - 10);
					gameScene.attachChild(mText);
					gameScene.attachChild(insect);
					gameStart = true;
					mText.setText("" + gameSec);
					mEngine.unregisterUpdateHandler(pTimerHandler);
				}
	        }
		})
		);
		this.mEngine.registerUpdateHandler(new IUpdateHandler() {
            public void onUpdate(float pSecondsElapsed) {
	            if(gameStart) {
	            	updateSpritePosition();
	            	removeOutlyingInsects();
	            }
            }

            public void reset() {}
		});
		
		TimerHandler myTimer = new TimerHandler(1, true, new ITimerCallback() {
	        public void onTimePassed(TimerHandler pTimerHandler) {
	            if(gameStart) { 
	            	gameSec--;
	            	mText.setText("" + gameSec);
	            } 
	            if(gameSec < 0) {
	            	// GAME OVER
	            	gameStart = false;
	            	mText.setText("0");
	            	mEngine.unregisterUpdateHandler(pTimerHandler);
	            }
	        }
	    });
	    this.mEngine.registerUpdateHandler(myTimer);
		
	    this.mEngine.registerUpdateHandler(new IUpdateHandler() {
            public void onUpdate(float pSecondsElapsed) {
            	if(gameStart) {
	            	catchInsects();
	            	if (amount>=5){
	            		freeInsects();
	            	}
            	}

//            	count++;
//            	if(count % 20 == 0) {
//	        		xt.setText("" + dvx);		// selesaikan urusan disini
//	        		yt.setText("" + dvy);
//	        		zt.setText("" + dvz);
//	        		Log.d("Acceleration", "dvx: " + dvx);
//	        		Log.d("Acceleration", "dvy: " + dvy);
//	        		Log.d("Acceleration", "dvz: " + dvz);
//	        		count = 0;
//            	}
            }

            public void reset() {
            }
		});

		return gameScene;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent e) {
		accPrevX = accX;
		accPrevY = accY;
		accPrevZ = accZ;
		
		accX = e.values[0];
		accY = e.values[1];
		accZ = e.values[2];
		
		dx = accX - accPrevX;
		dy = accY - accPrevY;
		dz = accZ - accPrevZ;
	}
	
	public void catchInsects() {
		if(!isCatching) {
			if (dz >= 10.0f){
				if(rect.collidesWith(insect)) {
					if(amount++<=5) {		
						// terkait per-combo-an
						if(ctype == insect.getType())
							combo++;
						else 
							combo = 1;
						
						// terkait per-score-an
						score += insect.getScore() * combo;
						comboText.setText("X" + combo);
						scoreText.setText("" + score);
						ctype=insect.getType();
						Log.d("catch", "score = "+score +",i = "+(amount)+", combo="+combo+", type = "+insect.getType());
						
						// remove si serangga, buat yg baru lagi
						gameScene.detachChild(insect);
						insect = createInsect();
						gameScene.attachChild(insect);
					} else {
						Log.d("catch", "lepaskan dulu");
						//amount=0;
					}
					isCatching = true;
				}
			} 
		}
		if(isCatching && accZ < -5.f) {
			Log.d("catch", "silakan tangkap");
			isCatching = false;
		}
	}
	
	protected void freeInsects(){
		if (dz >= 5.0f){
			amount=0;
			Log.d("free","syuddah lepaaas :)");
		}
	}
	
	protected Insect createInsect(){
		Random randomGenerator = new Random();
		
		int type = randomGenerator.nextInt(6) + 1;
		float initX = randomGenerator.nextFloat() * (activity.mCamera.getWidth() - INSECT_WIDTH);
		float initY = (randomGenerator.nextFloat() * (activity.mCamera.getHeight()- INSECT_WIDTH)) + INSECT_WIDTH;
		
		// CEK DI BAGIAN SINI
		ctype = (ctype == 0? type : ctype);
		
		switch (type) {
			case 1: 
				Log.d("insect", "buat beetle di posisi " + initX + "," + initY); 
				return new Insect(initX, initY, beetleTextureRegion, this.getVertexBufferObjectManager(), type);
			case 2: 
				Log.d("insect", "buat ladybug di posisi " + initX + "," + initY);
				return new Insect(initX, initY, ladybugTextureRegion, this.getVertexBufferObjectManager(), type);
			case 3: 
				Log.d("insect", "buat grasshopper di posisi " + initX + "," + initY);
				return new Insect(initX, initY, grasshopperTextureRegion, this.getVertexBufferObjectManager(), type);
			case 4: 
				Log.d("insect", "buat butterfly di posisi " + initX + "," + initY);
				return new Insect(initX, initY, butterflyTextureRegion, this.getVertexBufferObjectManager(), type);
			case 5: 
				Log.d("insect", "buat bee di posisi " + initX + "," + initY);
				return new Insect(initX, initY, beeTextureRegion, this.getVertexBufferObjectManager(), type);
			case 6: 
				Log.d("insect", "buat golden dragonfly di posisi " + initX + "," + initY);
				return new Insect(initX, initY, goldenDragonflyTextureRegion, this.getVertexBufferObjectManager(), type);
			case 7: 
				Log.d("insect", "buat time insect di posisi " + initX + "," + initY);
				return new Insect(initX, initY, timeTextureRegion, this.getVertexBufferObjectManager(), type);
			default: 
				return new Insect(initX, initY, beetleTextureRegion, this.getVertexBufferObjectManager(), type);	
		}
	}
		
	/**
	 * Ini masih gak pas..............
	 */
	public void updateSpritePosition() {
		if(accY < -1.0f) {
			if(accZ < -1.0f) {
				insect.setPosition(insect.getX() - 1.0f, insect.getY() - 1.0f);
			} else if (accZ > -1.0f && accZ < 1.0f) {
				insect.setPosition(insect.getX() - 1.0f, insect.getY());
			} else {
				insect.setPosition(insect.getX() - 1.0f, insect.getY() + 1.0f);
			}
		} else if(accY > -1.0f && accY < 1.0f) {
			if(accZ < -1.0f) {
				insect.setPosition(insect.getX(), insect.getY() - 1.0f);
			} else if (accZ > -1.0f && accZ < 1.0f) {
				insect.setPosition(insect.getX(), insect.getY());
			} else {
				insect.setPosition(insect.getX(), insect.getY() + 1.0f);
			}
		} else {
			if(accZ < -1.0f) {
				insect.setPosition(insect.getX() + 1.0f, insect.getY() - 1.0f);
			} else if (accZ > -1.0f && accZ < 1.0f) {
				insect.setPosition(insect.getX() + 1.0f, insect.getY());
			} else {
				insect.setPosition(insect.getX() + 1.0f, insect.getY() + 1.0f);
			}
		}
		
		// pergerakan khusus masing-masing serangga
		insect.move();
		
//			Log.d("Accelero", "faceX = " + face.getX() + ", faceY = " + face.getY());
	}
	
	public void removeOutlyingInsects() {
		if(insect.getX() < -50.0f 
				|| insect.getX() > activity.mCamera.getWidth() + 50.0f
				|| insect.getY() < -50.0f
				|| insect.getY() > activity.mCamera.getHeight() + 50.0f) {
			// remove and create new insect
			gameScene.detachChild(insect);
			insect = createInsect();
			gameScene.attachChild(insect);
		}
	}
	
	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
		
	}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData) {
		vPrevX = vX;
		vPrevY = vY;
		vPrevZ = vZ;
		
		vX = pAccelerationData.getX();
		vY = pAccelerationData.getY();
		vZ = pAccelerationData.getZ();
		
		dvx = vX - vPrevX;
		dvy = vY - vPrevY;
		dvz = vZ - vPrevZ;
		
//		Log.d("Acceleration", "vX = " + vX + ", vY = " + vY + ", vZ = " + vZ);
	}
	
	@Override
	protected void onResume() {
		Log.d("debug", "masuk PlayScene.onResume");
		super.onResume();
		System.gc();
		if(mSensorManager != null) {
			mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
//			this.enableAccelerationSensor(this);
		}
		
	}
	
	@Override
	protected void onPause() {
		Log.d("debug", "masuk PlayScene.onPause");
		super.onPause();
		mSensorManager.unregisterListener(this);
//		this.disableAccelerationSensor();
	}
	
	@Override
	protected void onDestroy()
	{
		Log.d("debug", "masuk PlayScene.onDestroy");
		super.onDestroy();
	        
	    if (this.isGameLoaded())
	    {
	        System.exit(0);
	    }
	}
}