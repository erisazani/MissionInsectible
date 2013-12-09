package com.inspector.missioninsectible;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.ConfigChooserOptions;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.augmentedreality.BaseAugmentedRealityGameActivity;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import com.inspector.missioninsectible.scene.PlayScene;
import com.inspector.missioninsectible.scene.SplashScene;

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
	public TextureRegion greyBeetle;
	public TextureRegion greyLadybug;
	public TextureRegion greyGrasshopper;
	public TextureRegion greyTimeInsect;
	public TextureRegion coloredButterfly;
	public TextureRegion coloredDragonfly;
	public TextureRegion coloredBee;
	public TextureRegion coloredBeetle;
	public TextureRegion coloredLadybug;
	public TextureRegion coloredGrasshopper;
	public TextureRegion coloredTimeInsect;
	private BitmapTextureAtlas mBackgroundTexture;
	private BitmapTextureAtlas mBoardTexture;
	private BitmapTextureAtlas mHomeButtonTexture;
	
	private BitmapTextureAtlas mGreyInsectTexture;
	private BitmapTextureAtlas mColoredInsectTexture;

	private BitmapTextureAtlas mMenuClickedTexture;
	public TextureRegion mMenuBattleClickedTextureRegion;
	public TextureRegion mMenuPlayClickedTextureRegion;
	public TextureRegion mMenuGalleryClickedTextureRegion;
	public TextureRegion mMenuScoreClickedTextureRegion;
	public TextureRegion mMenuHowToClickedTextureRegion;
	public TextureRegion mMenuAboutClickedTextureRegion;
	public TextureRegion mMenuQuitClickedTextureRegion;
	public Sound mMenuClickedSound;
	public Sound mCatchInsectSound;
	public Music BGM;
	public Music gameBGM;
	private BitmapTextureAtlas mSoundButtonTexture;
	public TextureRegion SoundOnTextureRegion;
	public TextureRegion SoundOffTextureRegion;
	
//	public Camera mCamera;
//	
//	private Scene mScene;
//	public Scene mCurrentScene;
//	public static MainGameActivity instance;

	@Override
	public EngineOptions onCreateEngineOptions() {
		Log.d("debug", "masuk activity MainGameActivity");
		// TODO Auto-generated method stub
		instance = this;
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);		
		// engineOptions = new EngineOptions(true,
		// 		ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), mCamera);
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
//		try {
//		mFaceTexture = new AssetBitmapTexture(getTextureManager(), getAssets(), "image/sprite/face_box.png");
		//Log.d("Texture", "Texture Loaded");
	
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	
//		for splash scene
		this.mLogoTexture = new BitmapTextureAtlas(this.getTextureManager(), 200, 200, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mLogoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mLogoTexture, this, "logo.png", 0, 0);
		this.mLogoTexture.load();

//		for Main Menu
		this.mMenuTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mMenuPlayTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-play.png", 0, 0);
		this.mMenuBattleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-battle.png", 0, 45);
		this.mMenuGalleryTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-gallery.png", 0, 90);
		this.mMenuScoreTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-hiscore.png", 0, 135);
		this.mMenuHowToTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-howto.png", 105, 0);
		this.mMenuAboutTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-about.png", 105, 45);
		this.mMenuQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "Menus-quit.png", 105, 90);	
		this.mMenuTexture.load();
		
		this.mBackgroundTexture = new BitmapTextureAtlas(this.getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mMenuBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBackgroundTexture, this, "Splash_fix.png", 0, 0);	
		this.mBackgroundTexture.load();
		
		
		// atlas for board
		this.mBattleBoardTexture = new BitmapTextureAtlas(this.getTextureManager(), 300, 300, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mGalleryBoardTexture = new BitmapTextureAtlas(this.getTextureManager(), 300, 300, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mHiScoreBoardTexture = new BitmapTextureAtlas(this.getTextureManager(), 300, 300, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mAboutBoardTexture = new BitmapTextureAtlas(this.getTextureManager(), 300, 300, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mHowToBoardTexture = new BitmapTextureAtlas(this.getTextureManager(), 300, 300, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		//atlas for insect
		this.mGreyInsectTexture = new BitmapTextureAtlas(this.getTextureManager(), 300, 300, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mColoredInsectTexture = new BitmapTextureAtlas(this.getTextureManager(), 420, 300, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		// atlas for button
		this.mHomeButtonTexture = new BitmapTextureAtlas(this.getTextureManager(), 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mSoundButtonTexture = new BitmapTextureAtlas(this.getTextureManager(), 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

//		for Battle Menu
		this.mBattleBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBattleBoardTexture, this, "Battle_Board.png",0,0);
		
		this.mBattleBoardTexture.load();
		
//		for Gallery Menu
		this.mGalleryBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mGalleryBoardTexture, this, "Gallery_Board.png",0,0);
		this.mGalleryBoardTexture.load();

		this.greyBeetle = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mGreyInsectTexture, this, "beetle-grey.png",0,0);
		this.greyLadybug = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mGreyInsectTexture, this, "ladybug-grey.png",55,0);
		this.greyGrasshopper = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mGreyInsectTexture, this, "grasshopper-grey.png",115,0);
		this.greyButterfly = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mGreyInsectTexture, this, "butterfly-grey.png",0,60);
		this.greyDragonfly = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mGreyInsectTexture, this, "dragonfly-grey.png",0,150);
		this.greyBee = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mGreyInsectTexture, this, "bee-grey.png",90,60);
		this.greyTimeInsect = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mGreyInsectTexture, this, "timeinsect-grey.png",90,170);
		
		
		this.coloredBeetle = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mColoredInsectTexture, this, "beetle-colored.png",0,0);
		this.coloredLadybug = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mColoredInsectTexture, this, "ladybug-colored.png",55,0);
		this.coloredGrasshopper = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mColoredInsectTexture, this, "grasshopper-colored.png",190,0);
		this.coloredButterfly = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mColoredInsectTexture, this, "butterfly-colored.png",275,0);
		this.coloredDragonfly = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mColoredInsectTexture, this, "dragonfly-colored.png",0,130);
		this.coloredBee = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mColoredInsectTexture, this, "bee-colored.png",130,130);
		this.coloredTimeInsect = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mColoredInsectTexture, this, "timeinsect-colored.png",200,130);
		
		this.mGreyInsectTexture.load();
		this.mColoredInsectTexture.load();

		// for button
		this.mHomeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mHomeButtonTexture, this, "Functional_button_home.png", 0, 0);
		this.mPlayBattleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mHomeButtonTexture, this, "Functional_button_play.png", 0, 55);
		
		//	for HiScore Menu
		this.mBoardHiScoreTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mHiScoreBoardTexture, this, "Hiscore_Board.png",0,0);
	
		
		this.mHiScoreBoardTexture.load();
		this.mHomeButtonTexture.load();

//		for HowTo Menu
		this.mHowToBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mHowToBoardTexture, this, "Howto_Board.png",0,0);
		this.mHowToBoardTexture.load();

//		for About Menu
		this.mAboutBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAboutBoardTexture, this, "About_Board.png",0,0);
		this.mAboutBoardTexture.load();
		
//		for Loading Scene
		this.mLoadScreenBGTexture = new BitmapTextureAtlas(this.getTextureManager() ,300,150, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mLoadScreenBGRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mLoadScreenBGTexture, this, "LoadingImage.png", 0, 0);
        this.mLoadScreenBGTexture.load();	

    // for clicked menu button
    this.mMenuClickedTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    this.mMenuPlayClickedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuClickedTexture, this, "Menus-play-active.png",0,0);
    this.mMenuBattleClickedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuClickedTexture, this, "Menus-battle-active.png", 0, 45);
    this.mMenuGalleryClickedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuClickedTexture, this, "Menus-gallery-active.png", 0, 90);
    this.mMenuScoreClickedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuClickedTexture, this, "Menus-hiscore-active.png", 0, 135);
    this.mMenuHowToClickedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuClickedTexture, this, "Menus-howto-active.png", 105, 0);
    this.mMenuAboutClickedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuClickedTexture, this, "Menus-about-active.png", 105, 45);
    this.mMenuQuitClickedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuClickedTexture, this, "Menus-quit-active.png", 105, 90);	
    this.mMenuClickedTexture.load();
  
    this.SoundOnTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSoundButtonTexture, this, "sound_on.png", 0, 0);
	this.SoundOffTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSoundButtonTexture, this, "sound_off.png", 55, 0);
	this.mSoundButtonTexture.load();
	
	this.mBackgroundTexture = new BitmapTextureAtlas(this.getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	this.mMenuBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBackgroundTexture, this, "Splash_fix.png", 0, 0);	
	this.mBackgroundTexture.load();
        
	SoundFactory.setAssetBasePath("mfx/");
	try {
		this.mMenuClickedSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "Menu Select 2.wav");
		this.mCatchInsectSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "catch_insect.ogg");
	} catch (final IOException e) {
		Debug.e(e);
	}
	
	MusicFactory.setAssetBasePath("mfx/");
	try{
	this.BGM = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "bgm.ogg");
	this.BGM.setLooping(true);
	this.gameBGM = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "game_bgm.ogg");
	this.gameBGM.setLooping(true);
	} catch (final IOException e) {
		Debug.e(e);
	}
	
	}
	
	@Override
	protected Scene onCreateScene() {
		
		mEngine.registerUpdateHandler(new FPSLogger());
		mCurrentScene = new SplashScene();
		
//		mEngine.registerUpdateHandler(new FPSLogger());
//		Log.d("debug", "akan masuk activity PlayScene");
//		this.startActivity(new Intent(this, PlayScene.class));
//		Log.d("debug", "harusnya sudah masuk activity PlayScene");
		
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
