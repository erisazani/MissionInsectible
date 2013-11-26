package com.inspector.missioninsectible.scene;

import java.io.IOException;
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
	
	MainGameActivity activity;
	
	public ITexture mFaceTexture;
	public ITexture mFontTexture;
	public ITexture mComboFontTexture;
	public ITextureRegion mFaceTextureRegion;
	
	private float accX, accY, accZ, accPrevX, accPrevY, accPrevZ, dx, dy, dz;
	private float vX, vY, vZ, vPrevX, vPrevY, vPrevZ, dvx, dvy, dvz;
	private Rectangle rect;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer; 
	
	private Sprite face;
	private Font mFont;
	private Font mComboFont;
	
	private Text scoreLblText, scoreText, comboText;
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
//		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		//register the accelerometer
//		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
				
		return activity.engineOptions;
	}

	@Override
	protected void onCreateResources() throws IOException {
		Log.d("debug", "masuk PlayScene.onCreateResources");
		try {
			mFaceTexture = new AssetBitmapTexture(getTextureManager(), getAssets(), "image/sprite/face_box.png");
			Log.d("Texture", "Texture Loaded");
		} catch (IOException e) {
			e.printStackTrace();
			Log.d("Texture", "Texture Not Loaded");
		}
		mFaceTextureRegion = TextureRegionFactory.extractFromTexture(mFaceTexture);
		mFaceTexture.load();
		
		this.mFontTexture = new BitmapTextureAtlas(this.mEngine.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mComboFontTexture = new BitmapTextureAtlas(this.mEngine.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
//		this.mEngine.getTextureManager().loadTexture(mFontTexture);
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
		
		scoreLblText = new Text(activity.mCamera.getWidth() / 5 - 20, activity.mCamera.getHeight() - FONT_SIZE / 2, mFont, "Score" , this.mEngine.getVertexBufferObjectManager());
		scoreText = new Text(activity.mCamera.getWidth() / 5 + 30, activity.mCamera.getHeight() - (int)(FONT_SIZE *1.5 / 2), mFont, "" + score , 7, this.mEngine.getVertexBufferObjectManager());
		comboText = new Text(25, FONT_COMBO_SIZE / 2, mComboFont, "X" + combo , 7, this.mEngine.getVertexBufferObjectManager());
		mText = new Text(activity.mCamera.getWidth()/2, activity.mCamera.getHeight()/2 - FONT_SIZE / 2, mFont, "" + countSec, 3, this.mEngine.getVertexBufferObjectManager());
		rect= new Rectangle(activity.mCamera.getWidth()/2, activity.mCamera.getHeight()/2,50f,50f,activity.getVertexBufferObjectManager());
		mText.setColor(Color.RED);
		scoreLblText.setColor(Color.GREEN);
		scoreText.setColor(Color.GREEN);
		comboText.setColor(Color.PINK);
		
		final Scene gameScene = new Scene();
		gameScene.setBackground(new Background(0.0f, 0.0f, 0.0f, 0.0f));

		final float centerX = (activity.mCamera.getWidth() - mFaceTextureRegion.getWidth()) / 2;
		final float centerY = (activity.mCamera.getHeight() - mFaceTextureRegion.getHeight()) / 2;
		face = new Sprite(centerX, centerY, mFaceTextureRegion, activity.getVertexBufferObjectManager());
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
	        	if(countSec == 1) {
					mText.setColor(Color.GREEN);
	        	}
				if(countSec < 0) {
					gameScene.detachChild(mText);
					mText.setPosition(activity.mCamera.getWidth()/2, activity.mCamera.getHeight() - FONT_SIZE / 2);
					gameScene.attachChild(mText);
					gameScene.attachChild(face);
					gameStart = true;
					mText.setText("" + gameSec);
					mEngine.unregisterUpdateHandler(pTimerHandler);
				}
	        }
		})
		);
		this.mEngine.registerUpdateHandler(new IUpdateHandler() {
            public void onUpdate(float pSecondsElapsed) {
            if(gameStart)
            	updateSpritePosition();
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
	            	gameStart = false;
	            	mEngine.unregisterUpdateHandler(pTimerHandler);
	            }
	        }
	    });
	    this.mEngine.registerUpdateHandler(myTimer);   // here you register the timerhandler to your scene
		
	    this.mEngine.registerUpdateHandler(new IUpdateHandler() {
            public void onUpdate(float pSecondsElapsed) {
            	if(gameSec > 0) {
	            	insect=createInsect();
	            	catchInsects();
	            	if (amount>=5){
	            		freeInsects();
	            	}
            	}
            }

            public void reset() {
                    // TODO Auto-generated method stub
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
				if(rect.collidesWith(face)) {
					if(amount++<=5) {
						face.setPosition(face.getY() - dy, face.getX() - dx);
						if(ctype==insect.getType()){combo++;}
						score += insect.getScore()*combo;
						comboText.setText("X" + combo);
						scoreText.setText("" + score);
						ctype=insect.getType();
						Log.d("catch", "score = "+score +",i = "+(amount)+", combo="+combo+", type = "+insect.getType());
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
		int type = randomGenerator.nextInt(6)+1;
		ctype=(ctype==0?type:ctype);
		return new Insect(type);
	}
	
	public void updateSpritePosition() {
		if(accY < -1.0f) {
			if(accZ < -1.0f) {
				face.setPosition(face.getX() + 0.5f, face.getY() - 0.5f);
			} else if (accZ > -1.0f && accZ < 1.0f) {
				face.setPosition(face.getX() + 0.5f, face.getY());
			} else {
				face.setPosition(face.getX() + 0.5f, face.getY() + 0.5f);
			}
		} else if(accY > -1.0f && accY < 1.0f) {
			if(accZ < -1.0f) {
				face.setPosition(face.getX(), face.getY() - 0.5f);
			} else if (accZ > -1.0f && accZ < 1.0f) {
				face.setPosition(face.getX(), face.getY());
			} else {
				face.setPosition(face.getX(), face.getY() + 0.5f);
			}
		} else {
			if(accZ < -1.0f) {
				face.setPosition(face.getX() - 0.5f, face.getY() - 0.5f);
			} else if (accZ > -1.0f && accZ < 1.0f) {
				face.setPosition(face.getX() - 0.5f, face.getY());
			} else {
				face.setPosition(face.getX() - 0.5f, face.getY() + 0.5f);
			}
		}
		
//		Log.d("Accelero", "faceX = " + face.getX() + ", faceY = " + face.getY());
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
			this.enableAccelerationSensor(this);
		}
		
	}
	
	@Override
	protected void onPause() {
		Log.d("debug", "masuk PlayScene.onPause");
		super.onPause();
		mSensorManager.unregisterListener(this);
		this.disableAccelerationSensor();
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