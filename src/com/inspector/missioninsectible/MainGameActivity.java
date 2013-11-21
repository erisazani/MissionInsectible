package com.inspector.missioninsectible;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.ConfigChooserOptions;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.augmentedreality.BaseAugmentedRealityGameActivity;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.inspector.missioninsectible.scene.PlayScene;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class MainGameActivity extends SimpleBaseGameActivity {
	static final int CAMERA_WIDTH = 320;
	static final int CAMERA_HEIGHT = 240;
	
	public EngineOptions engineOptions;
	public Camera mCamera;
	
	private Scene mScene;
	public Scene mCurrentScene;
	public static MainGameActivity instance;

	@Override
	public EngineOptions onCreateEngineOptions() {
		Log.d("debug", "masuk activity MainGameActivity");
		instance = this;
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
		final ConfigChooserOptions configChooserOptions = engineOptions.getRenderOptions().getConfigChooserOptions();
		configChooserOptions.setRequestedRedSize(8);
		configChooserOptions.setRequestedGreenSize(8);
		configChooserOptions.setRequestedBlueSize(8);
		configChooserOptions.setRequestedAlphaSize(8);
		configChooserOptions.setRequestedDepthSize(16);
		
		Log.d("debug", "masuk keluar onCreateEngineOptions");
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		
	}
	
	@Override
	protected Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		Log.d("debug", "akan masuk activity PlayScene");
		this.startActivity(new Intent(this, PlayScene.class));
		Log.d("debug", "harusnya sudah masuk activity PlayScene");
		
	 	return mCurrentScene;
	}
	
	public static MainGameActivity getSharedInstance() {
		return instance;
	}
}
