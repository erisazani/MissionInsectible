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
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import com.inspector.missioninsectible.scene.PlayScene;
import com.inspector.missioninsectible.scene.SplashScene;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

public class MainGameActivity extends SimpleBaseGameActivity {
//	static final int CAMERA_WIDTH = 854;
//	static final int CAMERA_HEIGHT = 480;
//	
	static final int CAMERA_WIDTH = 320;
	static final int CAMERA_HEIGHT = 240;
	
	private static final int LAYER_BACKGROUND = 0;
	
	public Font mFont;
	public Camera mCamera;
	
	private Scene mScene;
	public Scene mCurrentScene;
	private BitmapTextureAtlas mLogoTexture;
	public TextureRegion mLogoTextureRegion;
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
	
	private BitmapTextureAtlas mAboutBoardTexture;
	public TextureRegion mAboutBoardTextureRegion;
	public TextureRegion mHomeTextureRegion;
	
	private BitmapTextureAtlas mGalleryBoardTexture;
	public TextureRegion mGalleryBoardTextureRegion;
	
	private BitmapTextureAtlas mHowToBoardTexture;
	public TextureRegion mHowToBoardTextureRegion;
	
	private BitmapTextureAtlas mHiScoreBoardTexture;
	public TextureRegion mBoardHiScoreTextureRegion;
	
	private BitmapTextureAtlas mBattleBoardTexture;
	public TextureRegion mBattleBoardTextureRegion;
	public TextureRegion mPlayBattleTextureRegion;

	private BitmapTextureAtlas mLoadScreenBGTexture;
	public TextureRegion mLoadScreenBGRegion;
	public Font time;
	private BitmapTextureAtlas mInsectTexture;
	public TextureRegion greyButterfly;
	public TextureRegion greyDragonfly;
	public TextureRegion greyBee;
	private BitmapTextureAtlas mBackgroundTexture;
	private BitmapTextureAtlas mBoardTexture;
	private BitmapTextureAtlas mHomeButtonTexture;
	private BitmapTextureAtlas mSoundButtonTexture;
	private TextureRegion SoundOnTextureRegion;
	private TextureRegion SoundOffTextureRegion;
	private BuildableBitmapTextureAtlas beeTexture;
	public TiledTextureRegion beeTiledTextureRegion;
	private BuildableBitmapTextureAtlas dragonflyTexture;
	public TiledTextureRegion dragonflyTiledTextureRegion;
	private BuildableBitmapTextureAtlas butterflyTexture;
	public TiledTextureRegion butterflyTiledTextureRegion;
	private BuildableBitmapTextureAtlas ladybugTexture;
	public TiledTextureRegion ladybugTiledTextureRegion;


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
			this.mLogoTexture = new BitmapTextureAtlas(this.getTextureManager(), 200, 200, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.mLogoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mLogoTexture, this, "logo.png", 0, 0);
			this.mLogoTexture.load();

			//atlas for insect
			this.mInsectTexture = new BitmapTextureAtlas(this.getTextureManager(), 300, 100, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			// atlas for button
			this.mHomeButtonTexture = new BitmapTextureAtlas(this.getTextureManager(), 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.mSoundButtonTexture = new BitmapTextureAtlas(this.getTextureManager(), 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			
//			for Main Menu
			this.mMenuTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.mMenuPlayTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-play.png", 0, 0);
			this.mMenuBattleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-battle.png", 0, 45);
			this.mMenuGalleryTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-gallery.png", 0, 90);
			this.mMenuScoreTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-hiscore.png", 0, 135);
			this.mMenuHowToTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-howto.png", 105, 0);
			this.mMenuAboutTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-about.png", 105, 45);
			this.mMenuQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-quit.png", 105, 90);	
			this.mMenuTexture.load();
			
			this.SoundOnTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSoundButtonTexture, this, "sound_on.png", 0, 0);
			this.SoundOffTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSoundButtonTexture, this, "sound_off.png", 55, 0);
			this.mSoundButtonTexture.load();
			
			this.mBackgroundTexture = new BitmapTextureAtlas(this.getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.mMenuBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBackgroundTexture, this, "Splash_fix.png", 0, 0);	
			this.mBackgroundTexture.load();
			
			
			// atlas for board
			this.mBattleBoardTexture = new BitmapTextureAtlas(this.getTextureManager(), 300, 300, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.mGalleryBoardTexture = new BitmapTextureAtlas(this.getTextureManager(), 300, 300, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.mHiScoreBoardTexture = new BitmapTextureAtlas(this.getTextureManager(), 300, 300, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.mAboutBoardTexture = new BitmapTextureAtlas(this.getTextureManager(), 300, 300, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.mHowToBoardTexture = new BitmapTextureAtlas(this.getTextureManager(), 300, 300, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			

//			for Battle Menu
			this.mBattleBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBattleBoardTexture, this, "Battle_Board.png",0,0);	
			this.mBattleBoardTexture.load();
			
//			for Gallery Menu
			this.mGalleryBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mGalleryBoardTexture, this, "Gallery_Board.png",0,0);
			this.mGalleryBoardTexture.load();
			this.greyButterfly = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInsectTexture, this, "butterfly-grey.png",0,0);
			this.greyDragonfly = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInsectTexture, this, "dragonfly-grey.png",90,0);
			this.greyBee = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInsectTexture, this, "bee-grey.png",180,0);
			this.mInsectTexture.load();

			// for button
			this.mHomeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mHomeButtonTexture, this, "Functional_button_home.png", 0, 0);
			this.mPlayBattleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mHomeButtonTexture, this, "Functional_button_play.png", 0, 55);
			
			//	for HiScore Menu
			this.mBoardHiScoreTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mHiScoreBoardTexture, this, "Hiscore_Board.png",0,0);
		
			
			this.mHiScoreBoardTexture.load();
			this.mHomeButtonTexture.load();

//			for HowTo Menu
			this.mHowToBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mHowToBoardTexture, this, "Howto_Board.png",0,0);
			this.mHowToBoardTexture.load();

//			for About Menu
			this.mAboutBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAboutBoardTexture, this, "About_Board.png",0,0);
			this.mAboutBoardTexture.load();
			
//			for Loading Scene
			this.mLoadScreenBGTexture = new BitmapTextureAtlas(this.getTextureManager() ,300,150, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mLoadScreenBGRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mLoadScreenBGTexture, this, "LoadingImage.png", 0, 0);
            this.mLoadScreenBGTexture.load();
            
 this.beeTexture = new BuildableBitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.NEAREST);
 this.beeTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(beeTexture, this, "bee-tiled.png", 3,1);
 this.dragonflyTexture = new BuildableBitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.NEAREST);
 this.dragonflyTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(dragonflyTexture, this, "dragonfly-tiled.png", 3,1);
 this.butterflyTexture = new BuildableBitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.NEAREST);
 this.butterflyTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(butterflyTexture, this, "butterfly-tiled.png", 2,1);
 this.ladybugTexture = new BuildableBitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.NEAREST);
 this.ladybugTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(ladybugTexture, this, "ladybug-tiled.png", 2,1);

 try {
		this.beeTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.beeTexture.load();
		this.dragonflyTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.dragonflyTexture.load();
		this.butterflyTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.butterflyTexture.load();
		this.ladybugTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.ladybugTexture.load();
	} catch (TextureAtlasBuilderException e) {
		Debug.e(e);
	}       
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
