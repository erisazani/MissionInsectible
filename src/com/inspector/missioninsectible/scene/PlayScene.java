package com.inspector.missioninsectible.scene;

import java.io.IOException;
import java.util.Random;

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
import com.inspector.missioninsectible.misc.Insect;


public class PlayScene extends BaseAugmentedRealityGameActivity implements SensorEventListener {
	
	MainGameActivity activity;
	
	public ITexture mFaceTexture;
	public ITextureRegion mFaceTextureRegion;

	private float accX, accY, accZ, accPrevX, accPrevY, accPrevZ, dx, dy, dz;
	
	private SensorManager mSensorManager;
	private Sensor mAccelerometer; 
	private Insect insect;
	private int ctype=0;
	private int amount=0;
	private int combo=1;
	public int score=0;
	private Sprite face;

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
	}

	@Override
	protected Scene onCreateScene() {
		Log.d("debug", "masuk PlayScene.onCreateScene");
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.mEngine.registerUpdateHandler(new IUpdateHandler() {
            public void onUpdate(float pSecondsElapsed) {
                //    updateSpritePosition();
            	insect=createInsect();
            	catchInsects();
            	if (amount>=5){
            		freeInsects();
            	}
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
		
	}
	
	public void catchInsects() {
		if(!isCatching) {
			if (dz >= 10.0f){
				if(amount++<=5){
					face.setPosition(face.getY() - dy, face.getX() - dx);
					if(ctype==insect.getType()){combo++;}
					score += insect.getScore()*combo;
					ctype=insect.getType();
					Log.d("catch", "score = "+score +",i = "+(amount)+", combo="+combo+", type = "+insect.getType());
				} else {
					Log.d("catch", "lepaskan dulu");
					//amount=0;
				}
				isCatching = true;
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