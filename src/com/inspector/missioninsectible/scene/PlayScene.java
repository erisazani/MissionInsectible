package com.inspector.missioninsectible.scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.ConfigChooserOptions;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.CircleParticleEmitter;
import org.andengine.entity.particle.emitter.IParticleEmitter;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.augmentedreality.BaseAugmentedRealityGameActivity;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.Texture;
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
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.NetworkInfo.DetailedState;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

import com.inspector.missioninsectible.MainGameActivity;
import com.inspector.missioninsectible.misc.Insect;
import com.inspector.missioninsectible.misc.InsectDb;


public class PlayScene extends BaseAugmentedRealityGameActivity implements SensorEventListener, IAccelerationListener {
	
	private final int FONT_SIZE = 18;
	private final int FONT_COMBO_SIZE = 40;
	private final int RESULT_FONT_SIZE = 14;
	
	private final int COMBO_MAGNIFY = 0;
	private final int COMBO_SHRINK = 1;
	private final int COMBO_NORMAL = 2;
	private int comboScaling = COMBO_NORMAL;
	
	private final float INSECT_WIDTH = 32.0f;
		
	private static final float RATE_MIN = 10;
	private static final float RATE_MAX= 10;
	private static final int PARTICLES_MAX = 10;
	
	MainGameActivity activity;
	private Scene gameScene;
	
	// textures
	private ITexture mFontTexture;
	private ITexture mComboFontTexture;
	private ITexture droidFontTexture;
	
	// animated sprite
	private BuildableBitmapTextureAtlas beetleTiledTexture;
	private BuildableBitmapTextureAtlas ladybugTiledTexture;
	private BuildableBitmapTextureAtlas grasshopperTiledTexture;
	private BuildableBitmapTextureAtlas butterflyTiledTexture;
	private BuildableBitmapTextureAtlas beeTiledTexture;
	private BuildableBitmapTextureAtlas goldenDragonflyTiledTexture;
	private BuildableBitmapTextureAtlas timeTiledTexture;
	
	private TiledTextureRegion beetleTiledTextureRegion;
	private TiledTextureRegion ladybugTiledTextureRegion;
	private TiledTextureRegion grasshopperTiledTextureRegion;
	private TiledTextureRegion butterflyTiledTextureRegion;
	private TiledTextureRegion beeTiledTextureRegion;
	private TiledTextureRegion goldenDragonflyTiledTextureRegion;
	private TiledTextureRegion timeTiledTextureRegion;
	
	// crosshair
	private BitmapTextureAtlas crosshairTexture;
	private TextureRegion crosshairBasicTextureRegion;
	private TextureRegion crosshairFullTextureRegion;
	private Sprite basicCrosshair;
	private Sprite fullCrosshair;
	
	// pause feature
	private BitmapTextureAtlas pauseButtonTexture;
	private TextureRegion pauseButtonTextureRegion;
	private BitmapTextureAtlas pauseGameTexture;
	private TextureRegion pauseGameTextureRegion;
	private Sprite pauseBoard;
	
	// result board
	private BitmapTextureAtlas resultBoardTexture;
	private TextureRegion resultBoardTextureRegion;
	private Sprite resultBoard;
	
	// particle emitter
	private BitmapTextureAtlas mParticleTextureAtlas;
    private TextureRegion mParticleTextureRegion;
    private IParticleEmitter emitter1;
    private IParticleEmitter emitter2;
    private IParticleEmitter emitter3;
    private SpriteParticleSystem particleSystem1;
    private SpriteParticleSystem particleSystem2;
    private SpriteParticleSystem particleSystem3;
	
	// jumlah tertangkap
	private int beetle,ladybug,grasshopper,butterfly,honeyBee,goldenDragonfly,timeInsect;
	
	// sensor parameter
	private float accX, accY, accZ, accPrevX, accPrevY, accPrevZ, dx, dy, dz;
	private float vX, vY, vZ, vPrevX, vPrevY, vPrevZ, dvx, dvy, dvz;
	
	private Rectangle rect;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	
	private Font mFont;
	private Font mComboFont;
	private Font droidFont;
	
	private Text scoreLblText, scoreText, comboText;
	private Text resultScoreLabelText, resultComboScoreLabelText, resultTotalScoreLabelText;
	private Text resultScoreText, resultComboScoreText, resultTotalScoreText;
	private Text scoreSpawnText, comboSpawnText;
	
	private Insect insect;
	private Insect insect2;
	private Insect insect3;
	private int ctype = 0;
	private int amount = 0;
	private int prevAmount = 0;
	private int combo = 1;
	public int score = 0;
	public int baseScore = 0;
	public int comboScore = 0;
	public InsectDb insectDb = new InsectDb(this);
	
	private Text mText;
	private int countSec = 10;
	private int gameSec = 60;
	private int totalSec = gameSec;
	private int elapsedSec;
	private int scoreSpawnTime = 100;
	private int comboAnimateTime = 50;
	private float comboTextScale = 1.0f;
	private int particleLimitTime1 = 1;
	private int particleLimitTime2 = 1;
	private int particleLimitTime3 = 1;
	
	private boolean gameStart = false;
	private boolean isPausing = false;
	private boolean gameOver = false;
	private boolean spawnScore = false;
	private boolean animateCombo = false;
	private boolean isCatching;
	
	private Music music;
	private AlertDialog.Builder alert;
	private EditText input;
	private String userName;
	private Handler mHandler;
	
	// dummy variables, delete later
//	Text xt, yt, zt;
	int count;
	
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
//		mAcceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), activity.mCamera);
		
		final ConfigChooserOptions configChooserOptions = engineOptions.getRenderOptions().getConfigChooserOptions();
		configChooserOptions.setRequestedRedSize(8);
		configChooserOptions.setRequestedGreenSize(8);
		configChooserOptions.setRequestedBlueSize(8);
		configChooserOptions.setRequestedAlphaSize(8);
		configChooserOptions.setRequestedDepthSize(16);
		
		return engineOptions;
	}

	@Override
	protected void onCreateResources() throws IOException {
		Log.d("debug", "masuk PlayScene.onCreateResources");
		
		// for animated insect
		this.beetleTiledTexture = new BuildableBitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.NEAREST);
		this.beetleTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(beetleTiledTexture, this, "gfx/beetle-tiled.png", 3,1);
		this.ladybugTiledTexture = new BuildableBitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.NEAREST);
		this.ladybugTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(ladybugTiledTexture, this, "gfx/ladybug-tiled.png", 2,1);
		this.grasshopperTiledTexture = new BuildableBitmapTextureAtlas(this.getTextureManager(), 350, 128, TextureOptions.NEAREST);
		this.grasshopperTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(grasshopperTiledTexture, this, "gfx/grasshopper-tiled.png", 2,1);
		this.butterflyTiledTexture = new BuildableBitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.NEAREST);
		this.butterflyTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(butterflyTiledTexture, this, "gfx/butterfly-tiled.png", 2,1);
		this.beeTiledTexture = new BuildableBitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.NEAREST);
        this.beeTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(beeTiledTexture, this, "gfx/bee-tiled.png", 3,1);
        this.goldenDragonflyTiledTexture = new BuildableBitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.NEAREST);
		this.goldenDragonflyTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(goldenDragonflyTiledTexture, this, "gfx/dragonfly-tiled.png", 3,1);
		this.timeTiledTexture = new BuildableBitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.NEAREST);
		this.timeTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(timeTiledTexture, this, "gfx/clock-tiled.png", 3,1);
		
		
		try {
			this.beetleTiledTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.beetleTiledTexture.load();
			this.ladybugTiledTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.ladybugTiledTexture.load();
			this.grasshopperTiledTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.grasshopperTiledTexture.load();
			this.butterflyTiledTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.butterflyTiledTexture.load();
			this.beeTiledTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.beeTiledTexture.load();
			this.goldenDragonflyTiledTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.goldenDragonflyTiledTexture.load();
			this.timeTiledTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.timeTiledTexture.load();
			
		} 
		catch (TextureAtlasBuilderException e) {
				Debug.e(e);
		}
		
		//for crosshair
		this.crosshairTexture = new BitmapTextureAtlas(this.getTextureManager(), 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.crosshairBasicTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(crosshairTexture, this, "gfx/crosshair_basic.png",0,0);
		this.crosshairFullTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(crosshairTexture, this, "gfx/crosshair_full.png",55,0);
		this.crosshairTexture.load();
		
		//for pause game
		this.pauseButtonTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.pauseButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(pauseButtonTexture, this, "gfx/Functional_button_pause.png",0,0);
		this.pauseGameTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.pauseGameTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(pauseGameTexture, this, "gfx/pauseGame.png",0,0);
		this.pauseButtonTexture.load();
		this.pauseGameTexture.load();
		
		// result board
		this.resultBoardTexture = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.resultBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(resultBoardTexture, this, "gfx/Result_Board.png",0,0);		
		this.resultBoardTexture.load();
		
		// particle
		mParticleTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        mParticleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mParticleTextureAtlas, this, "gfx/particle_point.png", 0, 0);
        mEngine.getTextureManager().loadTexture(this.mParticleTextureAtlas);
		
		// font texture, font, and load the font
		this.mFontTexture = new BitmapTextureAtlas(this.mEngine.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mComboFontTexture = new BitmapTextureAtlas(this.mEngine.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.droidFontTexture = new BitmapTextureAtlas(this.mEngine.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		this.mFontTexture.load();
		this.mComboFontTexture.load();
		this.droidFontTexture.load();
		
		this.mFont = FontFactory.createFromAsset(this.mEngine.getFontManager(),
	            this.mEngine.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA,
	            activity.getAssets(), "font/Droid.ttf", FONT_SIZE, true,
	            Color.WHITE_ARGB_PACKED_INT);
		this.mComboFont = FontFactory.createFromAsset(this.mEngine.getFontManager(),
	            this.mEngine.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA,
	            activity.getAssets(), "font/Droid.ttf", FONT_COMBO_SIZE, true,
	            Color.WHITE_ARGB_PACKED_INT);
		this.droidFont = FontFactory.createFromAsset(this.mEngine.getFontManager(),
	            this.mEngine.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA,
	            activity.getAssets(), "font/Droid.ttf", RESULT_FONT_SIZE, true,
	            Color.WHITE_ARGB_PACKED_INT);
		
		mFont.load();
		mComboFont.load();
		droidFont.load();
		
		this.mEngine.getFontManager().loadFont(mFont);
		this.mEngine.getFontManager().loadFont(mComboFont);
		this.mEngine.getFontManager().loadFont(droidFont);
		
		mHandler = new Handler(Looper.getMainLooper());		
	}

	@Override
	protected Scene onCreateScene() {
		Log.d("debug", "masuk PlayScene.onCreateScene");
		activity.gameBGM.play();
		// inisialisasi Scene
		gameScene = new Scene();
		gameScene.setBackground(new Background(0.0f, 0.0f, 0.0f, 0.0f));
		
		// pause button
		final Sprite pauseButton = new ButtonSprite(
				activity.mCamera.getWidth() - INSECT_WIDTH, 
				activity.mCamera.getHeight() - INSECT_WIDTH, 
				pauseButtonTextureRegion, 
				activity.getVertexBufferObjectManager(), 
				new OnClickListener() {
					@Override
					public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
							float pTouchAreaLocalY) {
						Log.d("pause", "terpause");					 
						isPausing = true;
						insect.stopAnimation();
						pauseBoard.setVisible(true);
						gameScene.registerTouchArea(pauseBoard);
					}
				}
		);
		
		// pause board
		pauseBoard = new ButtonSprite(
				activity.getCameraWidth()/2, 
				activity.getCameraHeight()/2, 
				pauseGameTextureRegion, 
				activity.getVertexBufferObjectManager(),
				new OnClickListener() {
					@Override
					public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
							float pTouchAreaLocalY) {
						Log.d("pause", "resume");					 
						isPausing = false;
						insect.animate(100);
						pauseBoard.setVisible(false);
						gameScene.unregisterTouchArea(pauseBoard);
					}
				}
		);
		
		// result board
		resultBoard = new Sprite(
				activity.getCameraWidth()/2, 
				activity.getCameraHeight()/2, 
				resultBoardTextureRegion, 
				activity.getVertexBufferObjectManager()
		);
		
		Random r = new Random();
		float initX = r.nextFloat() * (activity.mCamera.getWidth() - INSECT_WIDTH);
		float initY = (r.nextFloat() * (activity.mCamera.getHeight()- INSECT_WIDTH)) + INSECT_WIDTH;
		insect = new Insect(initX, initY, beetleTiledTextureRegion, activity.getVertexBufferObjectManager(), 1);
		Log.d("insect", "buat beetle di posisi " + initX + "," + initY);

		initX = r.nextFloat() * (activity.mCamera.getWidth() - INSECT_WIDTH);
		initY = (r.nextFloat() * (activity.mCamera.getHeight()- INSECT_WIDTH)) + INSECT_WIDTH;
		insect2 = new Insect(initX, initY, beetleTiledTextureRegion, activity.getVertexBufferObjectManager(), 1);
		Log.d("insect", "buat beetle di posisi " + initX + "," + initY);

		initX = r.nextFloat() * (activity.mCamera.getWidth() - INSECT_WIDTH);
		initY = (r.nextFloat() * (activity.mCamera.getHeight()- INSECT_WIDTH)) + INSECT_WIDTH;
		insect3 = new Insect(initX, initY, beetleTiledTextureRegion, activity.getVertexBufferObjectManager(), 1);
		Log.d("insect", "buat beetle di posisi " + initX + "," + initY);
		
		gameScene.attachChild(insect);
		gameScene.attachChild(insect2);
		gameScene.attachChild(insect3);
		insect.setVisible(false);
		insect2.setVisible(false);
		insect3.setVisible(false);
		
		
		this.mEngine.registerUpdateHandler(new FPSLogger());

		// time handler untuk countdown
		this.mEngine.registerUpdateHandler(new TimerHandler(1, true, new ITimerCallback() {
	        public void onTimePassed(TimerHandler pTimerHandler) {
	        	countSec--;
	        	mText.setText("" + countSec);
	        	if(countSec == 3) {
					mText.setColor(0.957f, 0.719f, 0.0f);
	        	}
	        	if(countSec == 0) {
	        		mText.setColor(Color.RED);
	        		mText.setText("Start!");
	        	}
				if(countSec < 0) {
					gameScene.detachChild(mText);
					mText.setPosition(activity.mCamera.getWidth()/2, activity.mCamera.getHeight() - FONT_SIZE / 2 - 10);
					gameScene.attachChild(mText);
					insect.setVisible(true);
					insect2.setVisible(true);
					insect3.setVisible(true);
					basicCrosshair.setVisible(true);
					gameStart = true;
					mText.setText("" + gameSec);
					mEngine.unregisterUpdateHandler(pTimerHandler);
				}
	        }
		})
		);
		
		// time handler untuk countdown time limit saat permainan
		this.mEngine.registerUpdateHandler(new TimerHandler(1, true, new ITimerCallback() {
	        public void onTimePassed(TimerHandler pTimerHandler) {
	            if(!isPausing && !gameOver){
	            	if(gameStart) { 
		            	gameSec--;
		            	elapsedSec++;
		            	mText.setText("" + gameSec);
		            } 
		            if(gameSec < 0) {
		            	// GAME OVER
		            	gameStart = false;
		            	printScore(score);
		            	mText.setText("0");
		            	
		            	// insert high score to database
		            	mHandler.post(new Runnable() {
							@Override
							public void run() {
				            	saveHighScore();
							}
		            	});

		            	// done if it is a game over
		            	gameOver = true;
		            	resultBoard.setVisible(true);
		            	resultScoreLabelText.setVisible(true);
		        		resultComboScoreLabelText.setVisible(true);
		        		resultTotalScoreLabelText.setVisible(true);
		        		resultScoreText.setVisible(true);
		        		resultComboScoreText.setVisible(true);
		        		resultTotalScoreText.setVisible(true);
		        		
		        		resultScoreText.setText("" + baseScore + " pts");
		        		resultComboScoreText.setText("" + comboScore + " pts");
		        		resultTotalScoreText.setText("" + score + " pts");
		        		
		        		gameScene.detachChild(fullCrosshair);
		        		gameScene.detachChild(basicCrosshair);
		        		gameScene.detachChild(scoreSpawnText);
		        		gameScene.detachChild(scoreText);
		        		gameScene.detachChild(comboText);
		            	gameScene.detachChild(insect);
		            	gameScene.detachChild(insect2);
		            	gameScene.detachChild(insect3);
		            	gameScene.detachChild(mText);
		            	gameScene.detachChild(pauseButton);
		            	gameScene.detachChild(particleSystem1);
		            	gameScene.detachChild(particleSystem2);
		            	gameScene.detachChild(particleSystem3);
		            	
		            	gameScene.unregisterTouchArea(pauseBoard);		
		            	
		            	activity.gameBGM.stop();
		            	
		            	mEngine.unregisterUpdateHandler(pTimerHandler);
		            	Log.d("time elapsed", "elapsed sec : " + elapsedSec);
		            }
	            }
	        }
	    }));

		// update handler untuk update penting selama permainan
		this.mEngine.registerUpdateHandler(new IUpdateHandler() {
            public void onUpdate(float pSecondsElapsed) {
	            if(!isPausing && !gameOver && gameStart) {
	            	updateSpritePosition();
	            	removeOutlyingInsects();
	            	catchInsects();
	            	updateCrosshairColor();
	            	showScoreEffect();
	            	showComboEffect();
	            }
            }

            public void reset() {}
		});
		
		// text yang muncul di layar
		scoreText = new Text(50, activity.mCamera.getHeight() - (int)(FONT_SIZE * 2.5 / 2), mFont, "" + score + " pts", 15, new TextOptions(HorizontalAlign.LEFT), this.mEngine.getVertexBufferObjectManager());
		comboText = new Text(25, FONT_COMBO_SIZE / 2, mComboFont, "X" + combo , 7, this.mEngine.getVertexBufferObjectManager());
		mText = new Text(activity.mCamera.getWidth()/2, activity.mCamera.getHeight()/2 - FONT_SIZE / 2, mFont, "" + countSec, 10, this.mEngine.getVertexBufferObjectManager());
		scoreSpawnText = new Text(activity.mCamera.getWidth()/2, activity.mCamera.getHeight()/2 - FONT_SIZE / 2, droidFont, "+" + insect.getScore(), 10, this.mEngine.getVertexBufferObjectManager());
		
		resultScoreLabelText = new Text(activity.mCamera.getWidth()/10*3, activity.mCamera.getHeight()/3*2, droidFont, "Base Score", 10, this.mEngine.getVertexBufferObjectManager());
		resultComboScoreLabelText = new Text(activity.mCamera.getWidth()/10*7 - 15, activity.mCamera.getHeight()/3*2, droidFont, "Combo Bonus", 11, this.mEngine.getVertexBufferObjectManager());
		resultTotalScoreLabelText = new Text(activity.mCamera.getWidth()/2, activity.mCamera.getHeight()/10*3 + (int)(1.5 * RESULT_FONT_SIZE), droidFont, "Total Score", 11, this.mEngine.getVertexBufferObjectManager());
		
		resultScoreText = new Text(activity.mCamera.getWidth()/10*3, activity.mCamera.getHeight()/3*2 - (int)(1.5 * RESULT_FONT_SIZE), droidFont, "" + score, 10, this.mEngine.getVertexBufferObjectManager());
		resultComboScoreText = new Text(activity.mCamera.getWidth()/10*7 - 15, activity.mCamera.getHeight()/3*2 - (int)(1.5 * RESULT_FONT_SIZE), droidFont, "" + score, 10, this.mEngine.getVertexBufferObjectManager());
		resultTotalScoreText = new Text(activity.mCamera.getWidth()/2, activity.mCamera.getHeight()/10*3, droidFont, "" + score, 11, this.mEngine.getVertexBufferObjectManager());
		
		resultScoreLabelText.setColor(0.3f, 0.5f, 0.5f);
		resultComboScoreLabelText.setColor(0.3f, 0.5f, 0.5f);
		resultTotalScoreLabelText.setColor(0.3f, 0.5f, 0.5f);
		resultScoreText.setColor(0.3f, 0.5f, 0.5f);
		resultComboScoreText.setColor(0.3f, 0.5f, 0.5f);
		resultTotalScoreText.setColor(0.3f, 0.5f, 0.5f);

		// rectangle indikator tertangkap
		rect = new Rectangle(activity.mCamera.getWidth()/2, activity.mCamera.getHeight()/2, 5.0f, 5.0f, activity.getVertexBufferObjectManager());
		rect.setVisible(false);

		// crosshair
		basicCrosshair = new Sprite(activity.getCameraWidth()/2, activity.getCameraHeight()/2, crosshairBasicTextureRegion, activity.getVertexBufferObjectManager());
		fullCrosshair = new Sprite(activity.getCameraWidth()/2, activity.getCameraHeight()/2, crosshairFullTextureRegion, activity.getVertexBufferObjectManager());
		
		gameScene.attachChild(mText);
		gameScene.attachChild(scoreText);
		gameScene.attachChild(comboText);
		
		gameScene.attachChild(pauseButton);
		gameScene.registerTouchArea(pauseButton);
		
		gameScene.attachChild(pauseBoard);
		pauseBoard.setVisible(false);
		
		gameScene.attachChild(resultBoard);
		resultBoard.setVisible(false);

		gameScene.attachChild(scoreSpawnText);
		gameScene.attachChild(resultScoreLabelText);
		gameScene.attachChild(resultComboScoreLabelText);
		gameScene.attachChild(resultTotalScoreLabelText);
		gameScene.attachChild(resultScoreText);
		gameScene.attachChild(resultComboScoreText);
		gameScene.attachChild(resultTotalScoreText);
		
		scoreSpawnText.setVisible(false);
		resultScoreLabelText.setVisible(false);
		resultComboScoreLabelText.setVisible(false);
		resultTotalScoreLabelText.setVisible(false);
		resultScoreText.setVisible(false);
		resultComboScoreText.setVisible(false);
		resultTotalScoreText.setVisible(false);
		
		gameScene.attachChild(rect);
		gameScene.attachChild(basicCrosshair);
		basicCrosshair.setVisible(false);
		
		return gameScene;
	}
	
	public void catchInsects() {
		if(!spawnScore) {
			scoreText.setText("" + score + " pts");
		}
		if(!isCatching) {
			if (dz >= 10.0f){
				activity.mCatchInsectSound.setVolume(5.0f);
				activity.mCatchInsectSound.play();
				if(rect.collidesWith(insect)) {
					// create particles
					emitter1 = new CircleParticleEmitter(insect.getX(), insect.getY(), 15);
					particleSystem1 = new SpriteParticleSystem( 
			                emitter1,
			                RATE_MIN,
			                RATE_MAX,
			                PARTICLES_MAX,
			                mParticleTextureRegion,
			                getVertexBufferObjectManager()
			            );
					particleSystem1.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
			        particleSystem1.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-30, 30, -30, 30));
			        particleSystem1.addParticleInitializer(new ColorParticleInitializer<Sprite>(1.0f, 1.0f, 0.0f));
			        particleSystem1.addParticleInitializer(new ExpireParticleInitializer<Sprite>(3.0f));
			        particleSystem1.addParticleModifier(new ScaleParticleModifier<Sprite>(0.0f, 1.0f, 1.0f, 2.0f));
			        particleSystem1.addParticleModifier(new ColorParticleModifier<Sprite>(0.0f, 1.0f, 1f, 0.5f, 0.8f, 0.2f, 0f, 0.5f));
			        particleSystem1.addParticleModifier(new AlphaParticleModifier<Sprite>(0.0f, 0.5f, 1.0f, 0.0f));
					
					gameScene.attachChild(particleSystem1);
					gameScene.registerUpdateHandler(new TimerHandler(1, new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler pTimerHandler) {
							particleSystem1.detachSelf();
						}
					}));
					
					prevAmount = amount;
					amount++;
					if(amount <= 5) {		
						// terkait per-combo-an
						if(ctype == insect.getType())
							combo++;
						else 
							combo = 1;
						
						// terkait per-score-an
						spawnScore = true;
						scoreSpawnTime = 100;
						scoreSpawnText.setPosition(insect.getX(), insect.getY());
						
						if(combo != 1) {
							animateCombo = true;
							comboScaling = COMBO_MAGNIFY;
							comboAnimateTime = 50;
						}
						
						score += insect.getScore() * combo;
						collectInsect(insect.getType());
						comboText.setText("X" + combo);
						ctype=insect.getType();
						scoreSpawnText.setText("+" + insect.getScore() + " X " + combo);
						
						// remove si serangga, buat yg baru lagi
						gameScene.detachChild(insect);
						insect = createInsect();
						gameScene.attachChild(insect);
						
						if(amount < 5) {
							gameScene.detachChild(basicCrosshair);
							gameScene.attachChild(basicCrosshair);
						} else {
							gameScene.detachChild(fullCrosshair);
							gameScene.attachChild(fullCrosshair);
						}
						
					} else {
						// amount full
						Log.d("catch", "amount full");
					}
					isCatching = true;
				} else if(rect.collidesWith(insect2)) {
					emitter2 = new CircleParticleEmitter(insect2.getX(), insect2.getY(), 15);
					particleSystem2 = new SpriteParticleSystem( 
			                emitter2,
			                RATE_MIN,
			                RATE_MAX,
			                PARTICLES_MAX,
			                mParticleTextureRegion,
			                getVertexBufferObjectManager()
			            );
					particleSystem2.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
			        particleSystem2.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-30, 30, -30, 30));
			        particleSystem2.addParticleInitializer(new ColorParticleInitializer<Sprite>(1.0f, 1.0f, 0.0f));
			        particleSystem2.addParticleInitializer(new ExpireParticleInitializer<Sprite>(3.0f));
			        particleSystem2.addParticleModifier(new ScaleParticleModifier<Sprite>(0.0f, 1.0f, 1.0f, 2.0f));
			        particleSystem2.addParticleModifier(new ColorParticleModifier<Sprite>(0.0f, 1.0f, 1f, 0.5f, 0.8f, 0.2f, 0f, 0.5f));
			        particleSystem2.addParticleModifier(new AlphaParticleModifier<Sprite>(0.0f, 0.5f, 1.0f, 0.0f));
			        
					gameScene.attachChild(particleSystem2);
					gameScene.registerUpdateHandler(new TimerHandler(1, new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler pTimerHandler) {
							particleSystem2.detachSelf();
						}
					}));
					
					prevAmount = amount;
					amount++;
					if(amount <= 5) {		
						// terkait per-combo-an
						if(ctype == insect2.getType())
							combo++;
						else 
							combo = 1;
						
						// terkait per-score-an
						spawnScore = true;
						scoreSpawnTime = 100;
						scoreSpawnText.setPosition(insect2.getX(), insect2.getY());
						
						if(combo != 1) {
							animateCombo = true;
							comboScaling = COMBO_MAGNIFY;
							comboAnimateTime = 50;
						}
						
						score += insect2.getScore() * combo;
						collectInsect(insect2.getType());
						comboText.setText("X" + combo);
						ctype=insect2.getType();
						scoreSpawnText.setText("+" + insect2.getScore() + " X " + combo);
						
						// remove si serangga, buat yg baru lagi
						gameScene.detachChild(insect2);
						insect2 = createInsect();
						gameScene.attachChild(insect2);
						
						if(amount < 5) {
							gameScene.detachChild(basicCrosshair);
							gameScene.attachChild(basicCrosshair);
						} else {
							gameScene.detachChild(fullCrosshair);
							gameScene.attachChild(fullCrosshair);
						}
						
					} else {
						// amount full
						Log.d("catch", "amount full");
					}
					isCatching = true;
				} else if(rect.collidesWith(insect3)) {
					emitter3 = new CircleParticleEmitter(insect3.getX(), insect3.getY(), 15);
			        particleSystem3 = new SpriteParticleSystem( 
			                emitter3,
			                RATE_MIN,
			                RATE_MAX,
			                PARTICLES_MAX,
			                mParticleTextureRegion,
			                getVertexBufferObjectManager()
			            );
			        
			        particleSystem3.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
			        particleSystem3.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-30, 30, -30, 30));
			        particleSystem3.addParticleInitializer(new ColorParticleInitializer<Sprite>(1.0f, 1.0f, 0.0f));
			        particleSystem3.addParticleInitializer(new ExpireParticleInitializer<Sprite>(3.0f));
			        particleSystem3.addParticleModifier(new ScaleParticleModifier<Sprite>(0.0f, 1.0f, 1.0f, 2.0f));
			        particleSystem3.addParticleModifier(new ColorParticleModifier<Sprite>(0.0f, 1.0f, 1f, 0.5f, 0.8f, 0.2f, 0f, 0.5f));
			        particleSystem3.addParticleModifier(new AlphaParticleModifier<Sprite>(0.0f, 0.5f, 1.0f, 0.0f));
					
			        gameScene.attachChild(particleSystem3);
			        gameScene.registerUpdateHandler(new TimerHandler(1, new ITimerCallback() {
						@Override
						public void onTimePassed(TimerHandler pTimerHandler) {
							particleSystem3.detachSelf();
						}
					}));
			        
					prevAmount = amount;
					amount++;
					if(amount <= 5) {		
						// terkait per-combo-an
						if(ctype == insect3.getType())
							combo++;
						else 
							combo = 1;
						
						// terkait per-score-an
						spawnScore = true;
						scoreSpawnTime = 100;
						scoreSpawnText.setPosition(insect3.getX(), insect3.getY());
						
						if(combo != 1) {
							animateCombo = true;
							comboScaling = COMBO_MAGNIFY;
							comboAnimateTime = 50;
						}
						
						score += insect3.getScore() * combo;
						collectInsect(insect3.getType());
						comboText.setText("X" + combo);
						ctype=insect3.getType();
						scoreSpawnText.setText("+" + insect3.getScore() + " X " + combo);
						
						// remove si serangga, buat yg baru lagi
						gameScene.detachChild(insect3);
						insect3 = createInsect();
						gameScene.attachChild(insect3);
						
						if(amount < 5) {
							gameScene.detachChild(basicCrosshair);
							gameScene.attachChild(basicCrosshair);
						} else {
							gameScene.detachChild(fullCrosshair);
							gameScene.attachChild(fullCrosshair);
						}
						
					} else {
						// amount full
						Log.d("catch", "amount full");
					}
					isCatching = true;
				}
			} else if(accZ >= -15.0f)  {
				freeInsects();
				isCatching = true;
			}
		}
		if(isCatching && accZ < -5.0f) {
//			Log.d("catch", "silakan tangkap");
			isCatching = false;
		}
	}
	
	protected void freeInsects(){
//		if (dz >= -15.0f){
			prevAmount = amount;
			amount = 0;
//			Log.d("amount", "free amount");
//		}
	}
	
	protected Insect createInsect(){
		// timing of multi-insects
//		if(elapsedSec <= TWO_INSECTS_SPAWN_BOUNDARY_SEC) {
//			insect2.setVisible(false);
//			insect3.setVisible(false);
//		}
//		else if(elapsedSec >= TWO_INSECTS_SPAWN_BOUNDARY_SEC && elapsedSec <= THREE_INSECTS_SPAWN_BOUNDARY_SEC) {
//			insect2.setVisible(true);
//			insect3.setVisible(false);
//		} else {
//			insect2.setVisible(true);
//			insect3.setVisible(true);
//		}
		
		// randomize the appearance of the insect 
		Log.d("time elapsed", "elapsedTime : " + elapsedSec);
		Random randomGenerator = new Random();
		int type = 1;
		if(elapsedSec <= Insect.SPAWN_TIME[1]) {
			Log.d("create insect", "kasus 1");
			type = 1;
		} else if (elapsedSec <= Insect.SPAWN_TIME[2]) {
			Log.d("create insect", "kasus 2");
			type = randomGenerator.nextInt(2) + 1;
		} else if (elapsedSec <= Insect.SPAWN_TIME[3]) {
			Log.d("create insect", "kasus 3");
			type = randomGenerator.nextInt(3) + 1;
		} else if (elapsedSec <= Insect.SPAWN_TIME[4]) {
			Log.d("create insect", "kasus 4");
			type = randomGenerator.nextInt(4) + 1;
		} else if (elapsedSec <= Insect.SPAWN_TIME[5]) {
			Log.d("create insect", "kasus 5");
			type = randomGenerator.nextInt(5) + 1;
		} else {
			Log.d("create insect", "kasus 6");
			type = randomGenerator.nextInt(6) + 1;
		}
		
		int isTime = randomGenerator.nextInt(7) + 1;
		if(isTime == 7) {
			type = 7;
		}
		
		float initX = randomGenerator.nextFloat() * (activity.mCamera.getWidth() - INSECT_WIDTH);
		float initY = (randomGenerator.nextFloat() * (activity.mCamera.getHeight()- INSECT_WIDTH)) + INSECT_WIDTH;
		ctype = (ctype == 0? type : ctype);
		
		// create the insect based on type
		switch (type) {
			case 1: 
				Log.d("insect", "buat beetle di posisi " + initX + "," + initY); 
				return new Insect(initX, initY, beetleTiledTextureRegion, this.getVertexBufferObjectManager(), type);
			case 2: 
				Log.d("insect", "buat ladybug di posisi " + initX + "," + initY);
				return new Insect(initX, initY, ladybugTiledTextureRegion, this.getVertexBufferObjectManager(), type);
			case 3: 
				Log.d("insect", "buat grasshopper di posisi " + initX + "," + initY);
				return new Insect(initX, initY, grasshopperTiledTextureRegion, this.getVertexBufferObjectManager(), type);
			case 4: 
				Log.d("insect", "buat butterfly di posisi " + initX + "," + initY);
				return new Insect(initX, initY, butterflyTiledTextureRegion, this.getVertexBufferObjectManager(), type);
			case 5: 
				Log.d("insect", "buat bee di posisi " + initX + "," + initY);
				return new Insect(initX, initY, beeTiledTextureRegion, this.getVertexBufferObjectManager(), type);
			case 6: 
				Log.d("insect", "buat golden dragonfly di posisi " + initX + "," + initY);
				return new Insect(initX, initY, goldenDragonflyTiledTextureRegion, this.getVertexBufferObjectManager(), type);
			case 7: 
				Log.d("insect", "buat time insect di posisi " + initX + "," + initY);
				return new Insect(initX, initY, timeTiledTextureRegion, this.getVertexBufferObjectManager(), type);
			default: 
				return new Insect(initX, initY, beetleTiledTextureRegion, this.getVertexBufferObjectManager(), type);
		}
		
	}
	
	public void updateSpritePosition() {
		if(accY < -2.0f) {
			if(accZ < -2.0f) {
				insect.setPosition(insect.getX() - 1.0f, insect.getY() - 1.0f);
				insect2.setPosition(insect2.getX() - 1.0f, insect2.getY() - 1.0f);
				insect3.setPosition(insect3.getX() - 1.0f, insect3.getY() - 1.0f);
			} else if (accZ > -2.0f && accZ < 2.0f) {
				insect.setPosition(insect.getX() - 1.0f, insect.getY());
				insect2.setPosition(insect2.getX() - 1.0f, insect2.getY());
				insect3.setPosition(insect3.getX() - 1.0f, insect3.getY());
			} else {
				insect.setPosition(insect.getX() - 1.0f, insect.getY() + 1.0f);
				insect2.setPosition(insect2.getX() - 1.0f, insect2.getY() + 1.0f);
				insect3.setPosition(insect3.getX() - 1.0f, insect3.getY() + 1.0f);
			}
		} else if(accY > -2.0f && accY < 2.0f) {
			if(accZ < -2.0f) {
				insect.setPosition(insect.getX(), insect.getY() - 1.0f);
				insect2.setPosition(insect2.getX(), insect2.getY() - 1.0f);
				insect3.setPosition(insect3.getX(), insect3.getY() - 1.0f);
			} else if (accZ > -2.0f && accZ < 2.0f) {
				insect.setPosition(insect.getX(), insect.getY());
				insect2.setPosition(insect2.getX(), insect2.getY());
				insect3.setPosition(insect3.getX(), insect3.getY());
			} else {
				insect.setPosition(insect.getX(), insect.getY() + 1.0f);
				insect2.setPosition(insect2.getX(), insect2.getY() + 1.0f);
				insect3.setPosition(insect3.getX(), insect3.getY() + 1.0f);
			}
		} else {
			if(accZ < -2.0f) {
				insect.setPosition(insect.getX() + 1.0f, insect.getY() - 1.0f);
				insect2.setPosition(insect2.getX() + 1.0f, insect2.getY() - 1.0f);
				insect3.setPosition(insect3.getX() + 1.0f, insect3.getY() - 1.0f);
			} else if (accZ > -2.0f && accZ < 2.0f) {
				insect.setPosition(insect.getX() + 1.0f, insect.getY());
				insect2.setPosition(insect2.getX() + 1.0f, insect2.getY());
				insect3.setPosition(insect3.getX() + 1.0f, insect3.getY());
			} else {
				insect.setPosition(insect.getX() + 1.0f, insect.getY() + 1.0f);
				insect2.setPosition(insect2.getX() + 1.0f, insect2.getY() + 1.0f);
				insect3.setPosition(insect3.getX() + 1.0f, insect3.getY() + 1.0f);
			}
		}
		
		// pergerakan khusus masing-masing serangga
		insect.move();
		insect2.move();
		insect3.move();
		
		if(!spawnScore) {
			scoreSpawnText.setPosition(insect.getX(), insect.getY());
		}
		
	}
	
	public void removeOutlyingInsects() {
		if(insect.getX() < -50.0f 
				|| insect.getX() > activity.mCamera.getWidth() + 50.0f
				|| insect.getY() < -50.0f
				|| insect.getY() > activity.mCamera.getHeight() + 50.0f) {
			// remove and create new insect
			gameScene.detachChild(insect);
			insect = createInsect();
			gameScene.attachChild(insect);
		}
		if(insect2.getX() < -50.0f 
				|| insect2.getX() > activity.mCamera.getWidth() + 50.0f
				|| insect2.getY() < -50.0f
				|| insect2.getY() > activity.mCamera.getHeight() + 50.0f) {
			// remove and create new insect
			gameScene.detachChild(insect2);
			insect2 = createInsect();
			gameScene.attachChild(insect2);
		}
		if(insect3.getX() < -50.0f 
				|| insect3.getX() > activity.mCamera.getWidth() + 50.0f
				|| insect3.getY() < -50.0f
				|| insect3.getY() > activity.mCamera.getHeight() + 50.0f) {
			// remove and create new insect
			gameScene.detachChild(insect3);
			insect3 = createInsect();
			gameScene.attachChild(insect3);
		}
	}

	protected void collectInsect(int type){
		switch(type){
		case 1:beetle++ ;break;
		case 2:ladybug++;break;
		case 3:grasshopper++;break;
		case 4:butterfly++;break;
		case 5:honeyBee++;break;
		case 6:goldenDragonfly++;break;
		case 7:timeInsect++;gameSec+=15;totalSec+=15;break;
		}
	}
	
	protected void printScore(int score){
		comboScore = (score-((beetle*10)+(ladybug*25)+(grasshopper*60)+(butterfly*100)+(honeyBee*150)+(goldenDragonfly*300)+(timeInsect*5)));
		baseScore = score - comboScore;
		
		Log.d("catch","beetle = 10 x "+beetle+" = " + beetle*10);
		Log.d("catch","ladybug = 25 x "+ladybug+" = " + ladybug*25);
		Log.d("catch","grasshopper = 60 x "+grasshopper+" = " + grasshopper*60);
		Log.d("catch","butterfly = 100 x "+butterfly+" = " + butterfly*100);
		Log.d("catch","honeyBee = 150 x "+honeyBee+" = " + honeyBee*150);
		Log.d("catch","goldenDragonfly = 300 x "+goldenDragonfly+" = " + goldenDragonfly*300);
		Log.d("catch","timeInsect = 5 x "+timeInsect+" = " + timeInsect*5);
		Log.d("catch","COMBO++ = " + comboScore);
		Log.d("catch","TOTAL SCORE = "+score);
		Log.d("catch","TOTAL TIME = "+totalSec);
	}
	
	private void updateCrosshairColor() {
		if(amount < 5) {
			if(prevAmount == 5) {
				fullCrosshair.setVisible(false);
				basicCrosshair.setVisible(true);
			} else {
				basicCrosshair.setVisible(false);
				basicCrosshair.setVisible(true);
			}
		} else {
			basicCrosshair.setVisible(false);
			fullCrosshair.setVisible(true);
		}
	}
	
	// OK, no bug detected yet
	public void showScoreEffect() {
		// efek tambahan
		if(spawnScore && scoreSpawnTime >= 0) {
//			Log.d("score effect", "spawn time : " + scoreSpawnTime);
			scoreSpawnText.setVisible(true);
			scoreSpawnText.setPosition(
					scoreSpawnText.getX() - (scoreSpawnText.getX() / 100), 
					scoreSpawnText.getY() + (activity.getCameraHeight() - scoreSpawnText.getY()) / 100);
			scoreSpawnTime--;
		} else {
			scoreSpawnText.setVisible(false);
			spawnScore = false;
		}
	}
	
	public void showComboEffect() {
		// efek tambahan
		if(animateCombo) {
			// set scaling center to lower left corner of the text
			comboText.setScaleCenter(0, 0);
			
			// when to shrink, when to stop
			if(comboScaling == COMBO_MAGNIFY && comboTextScale > 2.0f) {
				comboScaling = COMBO_SHRINK;
			} else if(comboScaling == COMBO_SHRINK && comboTextScale < 1.0f) {
				comboScaling = COMBO_NORMAL;
			}
			
			// magnify and shrink
			if(comboScaling == COMBO_SHRINK) {
				comboTextScale -= 0.04f; 
			} else if(comboScaling == COMBO_MAGNIFY) {
				comboTextScale += 0.04f;
			}
			
			// set the scale based on scaling condition
			comboText.setScale(comboTextScale);
			comboAnimateTime--;
		} 
		if(comboAnimateTime < 0) {
			animateCombo = false;
		}
	}
	
	private void saveHighScore(){
		
		input = new EditText(this);
		alert = new AlertDialog.Builder(this);
//        this.runOnUiThread(new Runnable() {                       
//            @Override
//            public void run() {
                    alert.setTitle("High Score");
                    alert.setMessage("Enter your name here");
                    alert.setView(input);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int whichButton) {
	                    	userName = input.getText().toString();
	                    	insectDb.addHiScore(userName, score);
			            	insectDb.updateGallery(totalSec,beetle,ladybug,grasshopper,butterfly,honeyBee,goldenDragonfly,timeInsect);
			            	insectDb.printHiScore();
			            	insectDb.checkGallery();
	                    }
                    });
   
                    alert.setNegativeButton("No, thanks", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						
						}
                    });
   
                    alert.show();
//            }                  
//        });
	}
	
	public void createGallery(){

		insectDb.checkGallery();
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
	}
	
	@Override
	protected void onResume() {
		Log.d("debug", "masuk PlayScene.onResume");
		super.onResume();
		activity.gameBGM.play();
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	activity.setCurrentScene(new MainMenuScene());
//	    	activity.BGM.play();
	    	activity.gameBGM.stop();
	    	finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}
}