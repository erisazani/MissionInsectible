package com.inspector.missioninsectible;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.ConfigChooserOptions;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.augmentedreality.BaseAugmentedRealityGameActivity;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.inspector.missioninsectible.scene.PlayScene;

import android.graphics.Typeface;
import android.util.Log;

public class MainGameActivity extends BaseAugmentedRealityGameActivity {
	static final int CAMERA_WIDTH = 854;
	static final int CAMERA_HEIGHT = 480;
	
	static final int CAMERA_WIDTH2 = 320;
	static final int CAMERA_HEIGHT2 = 240;
	
	private static final int LAYER_BACKGROUND = 0;
	
	public Font mFont;
	public Camera mCamera;
	
	private Scene mScene;
	public Scene mCurrentScene;
	private BitmapTextureAtlas mBackgroundTexture;
	TextureRegion mBackgroundTextureRegion;
	public static MainGameActivity instance;

	private BitmapTextureAtlas mMenuTexture;
	TextureRegion mMenuPlayTextureRegion;
	TextureRegion mMenuBattleTextureRegion;
	TextureRegion mMenuBackgroundTextureRegion;
	TextureRegion mMenuGalleryTextureRegion;
	TextureRegion mMenuScoreTextureRegion;
	TextureRegion mMenuHowToTextureRegion;
	TextureRegion mMenuAboutTextureRegion;
	TextureRegion mMenuQuitTextureRegion;
	
	public ITexture mFaceTexture;
	public ITextureRegion mFaceTextureRegion;

	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		instance = this;
		mCamera = new Camera(0, 0, CAMERA_WIDTH2, CAMERA_HEIGHT2);
		
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH2, CAMERA_HEIGHT2), mCamera);
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
