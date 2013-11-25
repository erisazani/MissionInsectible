package com.inspector.missioninsectible.scene;

import java.io.IOException;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
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


public class PlayScene extends BaseAugmentedRealityGameActivity implements SensorEventListener, IAccelerationListener {
	
	private final int FONT_SIZE = 24;
	
	MainGameActivity activity;
	
	public ITexture mFaceTexture;
	public ITexture mFontTexture;
	public ITextureRegion mFaceTextureRegion;

	private float accX, accY, accZ, accPrevX, accPrevY, accPrevZ, dx, dy, dz;
	private float vX, vY, vZ, vPrevX, vPrevY, vPrevZ, dvx, dvy, dvz;
	
	private SensorManager mSensorManager;
	private Sensor mAccelerometer; 
	
	private Sprite face;
	private Font mFont;
	
	private Text mText;
	private int countSec = 10;
	private int gameSec = 20;
	private boolean gameStart = false;
	
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
//		this.mEngine.getTextureManager().loadTexture(mFontTexture);
		this.mFontTexture.load();
		this.mFont = new Font(this.mEngine.getFontManager(), this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), FONT_SIZE, true, Color.WHITE);
		this.mEngine.getFontManager().loadFont(mFont);
	}

	@Override
	protected Scene onCreateScene() {
		Log.d("debug", "masuk PlayScene.onCreateScene");
		
		mText = new Text(activity.mCamera.getWidth()/2, activity.mCamera.getHeight()/2 - FONT_SIZE / 2, mFont, "" + countSec, this.mEngine.getVertexBufferObjectManager());
		mText.setColor(Color.RED);
		
		final Scene gameScene = new Scene();
		gameScene.setBackground(new Background(0.0f, 0.0f, 0.0f, 0.0f));

		final float centerX = (activity.mCamera.getWidth() - mFaceTextureRegion.getWidth()) / 2;
		final float centerY = (activity.mCamera.getHeight() - mFaceTextureRegion.getHeight()) / 2;
		face = new Sprite(centerX, centerY, mFaceTextureRegion, activity.getVertexBufferObjectManager());
		gameScene.attachChild(mText);
		
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
	            } else {
	            	mEngine.unregisterUpdateHandler(pTimerHandler);
	            }
	        }
	    });
	    this.mEngine.registerUpdateHandler(myTimer);   // here you register the timerhandler to your scene
		
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