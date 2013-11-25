package com.inspector.missioninsectible;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
//import org.andengine.engine.options.ConfigChooserOptions;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
//import org.andengine.extension.augmentedreality.BaseAugmentedRealityGameActivity;
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
import com.inspector.missioninsectible.scene.SplashScene;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

public class MainGameActivity extends SimpleBaseGameActivity {
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
	public TextureRegion mBackgroundTextureRegion;
	public static MainGameActivity instance;

	private BitmapTextureAtlas mMenuTexture;
	public TextureRegion mMenuPlayTextureRegion;
	public TextureRegion mMenuBattleTextureRegion;
	public TextureRegion mMenuBackgroundTextureRegion;
	public TextureRegion mMenuGalleryTextureRegion;
	public TextureRegion mMenuScoreTextureRegion;
	public TextureRegion mMenuHowToTextureRegion;
	public TextureRegion mMenuAboutTextureRegion;
	public TextureRegion mMenuQuitTextureRegion;
	
//	public ITexture mFaceTexture;
//	public ITextureRegion mFaceTextureRegion;
	
	private BitmapTextureAtlas mAboutTexture;
	public TextureRegion mBoardAboutTexture;
	public TextureRegion mHomeGalleryTexture;
	
	private BitmapTextureAtlas mGalleryTexture;
	public TextureRegion mBoardGalleryTexture;
	
	private BitmapTextureAtlas mHowToTexture;
	public TextureRegion mBoardHowToTexture;
	public TextureRegion mHomeHowToTexture;
	
	private BitmapTextureAtlas mHiScoreTexture;
	public TextureRegion mBoardHiScoreTexture;
	
	private BitmapTextureAtlas mBattleTexture;
	public TextureRegion mBoardBattleTexture;
	public TextureRegion mPlayBattleTexture;

	private BitmapTextureAtlas mLoadScreenBGTexture;
	public TextureRegion mLoadScreenBGRegion;
	public Font time;
	private BitmapTextureAtlas mInsectTexture;
	public TextureRegion greyButterfly;
	public TextureRegion greyDragonfly;
	public TextureRegion greyBee;

	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		instance = this;
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
//	//	final ConfigChooserOptions configChooserOptions = engineOptions.getRenderOptions().getConfigChooserOptions();
//		configChooserOptions.setRequestedRedSize(8);
//		configChooserOptions.setRequestedGreenSize(8);
//		configChooserOptions.setRequestedBlueSize(8);
//		configChooserOptions.setRequestedAlphaSize(8);
//		configChooserOptions.setRequestedDepthSize(16);
//		
		return engineOptions;
	}


	@Override
	protected void onCreateResources() {
//		try {
//			mFaceTexture = new AssetBitmapTexture(getTextureManager(), getAssets(), "image/sprite/face_box.png");
			//Log.d("Texture", "Texture Loaded");
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
//			for splash scene
			this.mBackgroundTexture = new BitmapTextureAtlas(this.getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBackgroundTexture, this, "Splash.png", 0, 0);
			this.mBackgroundTexture.load();

//			for Main Menu
			this.mMenuTexture = new BitmapTextureAtlas(this.getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.mMenuBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Splash.png", 0, 0);	
			this.mMenuPlayTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-play.png", 0, 0);
			this.mMenuBattleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-battle.png", 0, 50);
			this.mMenuGalleryTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-gallery.png", 0, 100);
			this.mMenuScoreTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-hiscore.png", 0, 150);
			this.mMenuHowToTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-howto.png", 0, 200);
			this.mMenuAboutTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-about.png", 0, 250);
			this.mMenuQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-quit.png", 0, 300);	
			this.mMenuTexture.load();
			
//			for Battle Menu
			this.mBattleTexture = new BitmapTextureAtlas(this.getTextureManager(), 500, 400, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.mBoardBattleTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBattleTexture, this, "Battle_Board.png",0,0);
			this.mHomeGalleryTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBattleTexture, this, "Functional_button_home.png", 410, 0);
			this.mPlayBattleTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBattleTexture, this, "Functional_button_play.png", 410, 0);
			this.mBattleTexture.load();
			
//			for Gallery Menu
			this.mGalleryTexture = new BitmapTextureAtlas(this.getTextureManager(), 500, 400, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.mBoardGalleryTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mGalleryTexture, this, "Gallery_Board.png",0,0);
			this.mHomeGalleryTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mGalleryTexture, this, "Functional_button_home.png", 410, 0);
			this.mGalleryTexture.load();
			this.mInsectTexture = new BitmapTextureAtlas(this.getTextureManager(), 300, 100, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.greyButterfly = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInsectTexture, this, "butterfly-grey.png",0,0);
			this.greyDragonfly = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInsectTexture, this, "dragonfly-grey.png",90,0);
			this.greyBee = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInsectTexture, this, "bee-grey.png",180,0);
			this.mInsectTexture.load();
			
//			for HiScore Menu
			this.mHiScoreTexture = new BitmapTextureAtlas(this.getTextureManager(), 500, 400, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.mBoardHiScoreTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mHiScoreTexture, this, "Hiscore_Board.png",0,0);
			this.mHomeGalleryTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mHiScoreTexture, this, "Functional_button_home.png", 410, 0);
			this.mHiScoreTexture.load();

//			for HowTo Menu
			this.mHowToTexture = new BitmapTextureAtlas(this.getTextureManager(), 500, 400, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.mBoardHowToTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mHowToTexture, this, "Howto_Board.png",0,0);
			this.mHomeHowToTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mHowToTexture, this, "Functional_button_home.png", 410, 0);
			this.mHowToTexture.load();

//			for About Menu
			this.mAboutTexture = new BitmapTextureAtlas(this.getTextureManager(), 500, 400, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.mBoardAboutTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAboutTexture, this, "About_Board.png",0,0);
			this.mHomeGalleryTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAboutTexture, this, "Functional_button_home.png", 410, 0);
			this.mAboutTexture.load();
			
//			for Loading Scene
			this.mLoadScreenBGTexture = new BitmapTextureAtlas(this.getTextureManager() ,300,150, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mLoadScreenBGRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mLoadScreenBGTexture, this, "LoadingImage.png", 0, 0);
            this.mLoadScreenBGTexture.load();
            
            this.time =  FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 32, Color.WHITE);
            this.time.load();
            
//		} catch (IOException e) {
//			e.printStackTrace();
//			Log.d("Texture", "Texture Not Loaded");
//		}
//		mFaceTextureRegion = TextureRegionFactory.extractFromTexture(mFaceTexture);
//		mFaceTexture.load();
	}


	@Override
	protected Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		mCurrentScene = new SplashScene();
		
		  return mCurrentScene;
	}

	
	public static MainGameActivity getSharedInstance() {
		return instance;
	}
	
	public void setCurrentScene(Scene scene){
		mCurrentScene = scene;
		getEngine().setScene(mCurrentScene);
	}
	
	public int getCameraWidth() {
		return CAMERA_WIDTH;
	}

	public int getCameraHeight() {
		// TODO Auto-generated method stub
		return CAMERA_HEIGHT;
	}
	
}
