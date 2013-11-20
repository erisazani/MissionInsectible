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

import com.inspector.missioninsectible.scene.PlayScene;

import android.util.Log;

public class MainGameActivity extends BaseAugmentedRealityGameActivity {
	static final int CAMERA_WIDTH = 320;
	static final int CAMERA_HEIGHT = 240;
	
	public Camera mCamera;
	
	private Scene mScene;
	public Scene mCurrentScene;
	public static MainGameActivity instance;

	public ITexture mFaceTexture;
	public ITextureRegion mFaceTextureRegion;

	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		instance = this;
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
		final ConfigChooserOptions configChooserOptions = engineOptions.getRenderOptions().getConfigChooserOptions();
		configChooserOptions.setRequestedRedSize(8);
		configChooserOptions.setRequestedGreenSize(8);
		configChooserOptions.setRequestedBlueSize(8);
		configChooserOptions.setRequestedAlphaSize(8);
		configChooserOptions.setRequestedDepthSize(16);
		
		return engineOptions;
	}


	@Override
	protected void onCreateResources() {
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
		mEngine.registerUpdateHandler(new FPSLogger());
		mCurrentScene = new PlayScene();
		
		  return mCurrentScene;
	}

	
	public static MainGameActivity getSharedInstance() {
		return instance;
	}
	
	public void setCurrentScene(Scene scene){
		mCurrentScene = scene;
		getEngine().setScene(mCurrentScene);
	}
}
