package com.inspector.missioninsectible.scene;

import java.io.IOException;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.augmentedreality.BaseAugmentedRealityGameActivity;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.inspector.missioninsectible.MainGameActivity;


public class PlayScene extends BaseAugmentedRealityGameActivity implements SensorEventListener {
	
	MainGameActivity activity;
	
	public ITexture mFaceTexture;
	public ITextureRegion mFaceTextureRegion;

	private float accX, accY, accZ, accPrevX, accPrevY, accPrevZ, dx, dy, dz;
	
	private SensorManager mSensorManager;
	private Sensor mAccelerometer; 
	
	private Sprite face;
	
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
	}

	@Override
	protected Scene onCreateScene() {
		Log.d("debug", "masuk PlayScene.onCreateScene");
		
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.mEngine.registerUpdateHandler(new IUpdateHandler() {
            public void onUpdate(float pSecondsElapsed) {
//                    updateSpritePosition();
            }

            public void reset() {
                    // TODO Auto-generated method stub
            }
		});
		
		final Scene gameScene = new Scene();
		gameScene.setBackground(new Background(0.0f, 0.0f, 0.0f, 0.0f));

		final float centerX = (activity.mCamera.getWidth() - mFaceTextureRegion.getWidth()) / 2;
		final float centerY = (activity.mCamera.getHeight() - mFaceTextureRegion.getHeight()) / 2;
		face = new Sprite(centerX, centerY, mFaceTextureRegion, activity.getVertexBufferObjectManager());
//		face.registerEntityModifier(new MoveModifier(30, 0, 320 - face.getWidth(), 0, 240 - face.getHeight()));
		gameScene.attachChild(face);
		
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
		
//		Log.d("Accelero", "x = " + accX + ", y = " + accY + ", z = " + accZ);
//		Log.d("Accelero", "dx = " + dx + ", dy = " + dy);
	}
	
	public void updateSpritePosition() {
		if(Math.abs(dx) > 5.0f || Math.abs(dy) > 5.0f) {
			face.setPosition(face.getY() - dy, face.getX() - dx);
		}
		Log.d("Accelero", "faceX = " + face.getX() + ", faceY = " + face.getY());
	}
	
	@Override
	protected void onResume() {
		Log.d("debug", "masuk PlayScene.onResume");
		super.onResume();
		System.gc();
		if(mSensorManager != null) {
			mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
		}
		
	}
	
	@Override
	protected void onPause() {
		Log.d("debug", "masuk PlayScene.onPause");
		super.onPause();
		mSensorManager.unregisterListener(this);
//		System.exit(0);
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