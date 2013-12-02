package com.inspector.missioninsectible.scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.ConfigChooserOptions;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
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
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;

import android.content.Context;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.NetworkInfo.DetailedState;
import android.util.Log;

import com.inspector.missioninsectible.MainGameActivity;
import com.inspector.missioninsectible.misc.Insect;


public class PlayScene extends BaseAugmentedRealityGameActivity implements SensorEventListener, IAccelerationListener {
	
	private final int FONT_SIZE = 24;
	private final int FONT_COMBO_SIZE = 40;
	
	private final float INSECT_WIDTH = 32.0f;
	
	MainGameActivity activity;
	private Scene gameScene;
	
	private ITexture beetleTexture;
	private ITexture ladybugTexture;
	private ITexture grasshopperTexture;
	private ITexture butterflyTexture;
	private ITexture beeTexture;
	private ITexture goldenDragonflyTexture;
	private ITexture timeTexture;
	
	public ITexture mFontTexture;
	public ITexture mComboFontTexture;
	public ITextureRegion beetleTextureRegion;
	public ITextureRegion ladybugTextureRegion;
	public ITextureRegion grasshopperTextureRegion;
	public ITextureRegion butterflyTextureRegion;
	public ITextureRegion beeTextureRegion;
	public ITextureRegion goldenDragonflyTextureRegion;
	public ITextureRegion timeTextureRegion;
	
	// animated sprite
	private BuildableBitmapTextureAtlas ladybugTiledTexture;
	private BuildableBitmapTextureAtlas butterflyTiledTexture;
	private BuildableBitmapTextureAtlas beeTiledTexture;
	private BuildableBitmapTextureAtlas goldenDragonflyTiledTexture;
	private TiledTextureRegion ladybugTiledTextureRegion;
	private TiledTextureRegion butterflyTiledTextureRegion;
	private TiledTextureRegion beeTiledTextureRegion;
	private TiledTextureRegion goldenDragonflyTiledTextureRegion;
	
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
	
	private int beetle,ladybug,grasshopper,butterfly,honeyBee,goldenDragonfly,timeInsect;
	private float accX, accY, accZ, accPrevX, accPrevY, accPrevZ, dx, dy, dz;
	private float vX, vY, vZ, vPrevX, vPrevY, vPrevZ, dvx, dvy, dvz;
	private Rectangle rect;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
//	private Sensor mAcceleration;
	
	private Font mFont;
	private Font mComboFont;
	
	private Text scoreLblText, scoreText, comboText;
	private ArrayList<Insect> insects;
	private Insect insect;
	private int ctype = 0;
	private int amount = 0;
	private int prevAmount = 0;
	private int combo = 1;
	public int score = 0;
	
	private Text mText;
	private int countSec = 10;
	private int gameSec = 15;
	private int totalSec = gameSec;
	private boolean gameStart = false;
	private boolean isPausing = false;
	private boolean gameOver = false;
	
	private boolean isCatching;
	
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
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(activity.mCamera.getWidth(), activity.mCamera.getHeight()), activity.mCamera);
		final ConfigChooserOptions configChooserOptions = engineOptions.getRenderOptions().getConfigChooserOptions();
		configChooserOptions.setRequestedRedSize(8);
		configChooserOptions.setRequestedGreenSize(8);
		configChooserOptions.setRequestedBlueSize(8);
		configChooserOptions.setRequestedAlphaSize(8);
		configChooserOptions.setRequestedDepthSize(16);
		
		return activity.engineOptions;
	}

	@Override
	protected void onCreateResources() throws IOException {
		Log.d("debug", "masuk PlayScene.onCreateResources");
		try {
			beetleTexture = new AssetBitmapTexture(getTextureManager(), getAssets(), "image/sprite/sprite_beetle.png");
			ladybugTexture = new AssetBitmapTexture(getTextureManager(), getAssets(), "image/sprite/sprite_ladybug.png");
			grasshopperTexture = new AssetBitmapTexture(getTextureManager(), getAssets(), "image/sprite/sprite_grasshopper.png");
			butterflyTexture = new AssetBitmapTexture(getTextureManager(), getAssets(), "image/sprite/sprite_butterfly.png");
			beeTexture = new AssetBitmapTexture(getTextureManager(), getAssets(), "image/sprite/sprite_bee.png");
			goldenDragonflyTexture = new AssetBitmapTexture(getTextureManager(), getAssets(), "image/sprite/sprite_golden_dragonfly.png");
			timeTexture = new AssetBitmapTexture(getTextureManager(), getAssets(), "image/sprite/sprite_time.png");
			Log.d("Texture", "Texture Loaded");
		} catch (IOException e) {
			e.printStackTrace();
			Log.d("Texture", "Texture Not Loaded");
		}
		beetleTextureRegion = TextureRegionFactory.extractFromTexture(beetleTexture);
		ladybugTextureRegion = TextureRegionFactory.extractFromTexture(ladybugTexture);
		grasshopperTextureRegion = TextureRegionFactory.extractFromTexture(grasshopperTexture);
		butterflyTextureRegion = TextureRegionFactory.extractFromTexture(butterflyTexture);
		beeTextureRegion = TextureRegionFactory.extractFromTexture(beeTexture);
		goldenDragonflyTextureRegion = TextureRegionFactory.extractFromTexture(goldenDragonflyTexture);
		timeTextureRegion = TextureRegionFactory.extractFromTexture(timeTexture);
		
		// for animated insect
		this.ladybugTiledTexture = new BuildableBitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.NEAREST);
		this.ladybugTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(ladybugTiledTexture, this, "gfx/ladybug-tiled.png", 2,1);
		this.butterflyTiledTexture = new BuildableBitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.NEAREST);
		this.butterflyTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(butterflyTiledTexture, this, "gfx/butterfly-tiled.png", 2,1);
		this.beeTiledTexture = new BuildableBitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.NEAREST);
        this.beeTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(beeTiledTexture, this, "gfx/bee-tiled.png", 3,1);
        this.goldenDragonflyTiledTexture = new BuildableBitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.NEAREST);
		this.goldenDragonflyTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(goldenDragonflyTiledTexture, this, "gfx/dragonfly-tiled.png", 3,1);
		
		try {
			this.ladybugTiledTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.ladybugTiledTexture.load();
			this.butterflyTiledTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.butterflyTiledTexture.load();
			this.beeTiledTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.beeTiledTexture.load();
			this.goldenDragonflyTiledTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.goldenDragonflyTiledTexture.load();
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
		
		//for result board
		this.resultBoardTexture = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.resultBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(resultBoardTexture, this, "gfx/Result_Board.png",0,0);		
		this.resultBoardTexture.load();
		
		beetleTexture.load();
		ladybugTexture.load();
		grasshopperTexture.load();
		butterflyTexture.load();
		beeTexture.load();
		goldenDragonflyTexture.load();
		timeTexture.load();
		
		this.mFontTexture = new BitmapTextureAtlas(this.mEngine.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mComboFontTexture = new BitmapTextureAtlas(this.mEngine.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		this.mFontTexture.load();
		this.mComboFontTexture.load();
		
		this.mFont = new Font(this.mEngine.getFontManager(), this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), FONT_SIZE, true, Color.WHITE);
		this.mComboFont = new Font(this.mEngine.getFontManager(), this.mComboFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), FONT_COMBO_SIZE, true, Color.WHITE);
		
		this.mEngine.getFontManager().loadFont(mFont);
		this.mEngine.getFontManager().loadFont(mComboFont);
	}

	@Override
	protected Scene onCreateScene() {
		Log.d("debug", "masuk PlayScene.onCreateScene");

		// inisialisasi Scene
		gameScene = new Scene();
		gameScene.setBackground(new Background(0.0f, 0.0f, 0.0f, 0.0f));

		// text yang muncul di layar
		scoreLblText = new Text(50, activity.mCamera.getHeight() - FONT_SIZE / 2, mFont, "Score" , this.mEngine.getVertexBufferObjectManager());
		scoreText = new Text(50, activity.mCamera.getHeight() - (int)(FONT_SIZE * 2.5 / 2), mFont, "" + score , 7, this.mEngine.getVertexBufferObjectManager());
		comboText = new Text(25, FONT_COMBO_SIZE / 2, mComboFont, "X" + combo , 7, this.mEngine.getVertexBufferObjectManager());
		mText = new Text(activity.mCamera.getWidth()/2, activity.mCamera.getHeight()/2 - FONT_SIZE / 2, mFont, "" + countSec, 3, this.mEngine.getVertexBufferObjectManager());

		// rectangle indikator tertangkap
		rect = new Rectangle(activity.mCamera.getWidth()/2, activity.mCamera.getHeight()/2, 5.0f, 5.0f, activity.getVertexBufferObjectManager());
		rect.setVisible(false);

		// crosshair
		basicCrosshair = new Sprite(activity.getCameraWidth()/2, activity.getCameraHeight()/2, crosshairBasicTextureRegion, activity.getVertexBufferObjectManager());
		fullCrosshair = new Sprite(activity.getCameraWidth()/2, activity.getCameraHeight()/2, crosshairFullTextureRegion, activity.getVertexBufferObjectManager());
		
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
		insect = new Insect(initX, initY, ladybugTiledTextureRegion, activity.getVertexBufferObjectManager(), 1);
		Log.d("insect", "buat ladybug di posisi " + initX + "," + initY);
		
		this.mEngine.registerUpdateHandler(new FPSLogger());

		// time handler untuk countdown
		this.mEngine.registerUpdateHandler(new TimerHandler(1, true, new ITimerCallback() {
	        public void onTimePassed(TimerHandler pTimerHandler) {
	        	countSec--;
	        	mText.setText("" + countSec);
	        	if(countSec == 3) {
					mText.setColor(Color.GREEN);
	        	}
				if(countSec < 0) {
					gameScene.detachChild(mText);
					mText.setPosition(activity.mCamera.getWidth()/2, activity.mCamera.getHeight() - FONT_SIZE / 2 - 10);
					gameScene.attachChild(mText);
					gameScene.attachChild(insect);
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
		            	mText.setText("" + gameSec);
		            } 
		            if(gameSec < 0) {
		            	// GAME OVER
		            	gameStart = false;
		            	printScore(score);
		            	mText.setText("0");
		            	
		            	// done if it is a game over
		            	gameOver = true;
		            	resultBoard.setVisible(true);
		            	gameScene.detachChild(insect);
		            	gameScene.unregisterTouchArea(pauseBoard);		            	
		            	
		            	mEngine.unregisterUpdateHandler(pTimerHandler);
		            }
	            }
	        }
	    }));

		// update handler untuk atur posisi sprite selama permainan
		this.mEngine.registerUpdateHandler(new IUpdateHandler() {
            public void onUpdate(float pSecondsElapsed) {
	            if(!isPausing && !gameOver && gameStart) {
	            	updateSpritePosition();
	            	removeOutlyingInsects();
	            	catchInsects();
	            	updateCrosshairColor();
	            }
            }

            public void reset() {}
		});

		mText.setColor(Color.RED);
		scoreLblText.setColor(Color.GREEN);
		scoreText.setColor(Color.GREEN);
		comboText.setColor(Color.PINK);
		
		gameScene.attachChild(rect);
		gameScene.attachChild(basicCrosshair);
		gameScene.attachChild(mText);
		gameScene.attachChild(scoreLblText);
		gameScene.attachChild(scoreText);
		gameScene.attachChild(comboText);
		
		gameScene.attachChild(pauseButton);
		gameScene.registerTouchArea(pauseButton);
		
		gameScene.attachChild(pauseBoard);
		pauseBoard.setVisible(false);
		
		gameScene.attachChild(resultBoard);
		resultBoard.setVisible(false);
		
		return gameScene;
	}
	
	public void catchInsects() {
		if(!isCatching) {
			if (dz >= 10.0f){
				if(rect.collidesWith(insect)) {
					prevAmount = amount;
					amount++;
					Log.d("amount", "amount now : " + amount + ", prevAmount : " + prevAmount);
					if(amount <= 5) {		
						// terkait per-combo-an
						if(ctype == insect.getType())
							combo++;
						else 
							combo = 1;
						
						// terkait per-score-an
						score += insect.getScore() * combo;
						collectInsect(insect.getType());
						comboText.setText("X" + combo);
						scoreText.setText("" + score);
						ctype=insect.getType();
						Log.d("catch", "score = "+score +",i = "+(amount)+", combo="+combo+", type = "+insect.getType());
						
						// remove si serangga, buat yg baru lagi
						gameScene.detachChild(insect);
						insect = createInsect();
						gameScene.attachChild(insect);
					} else {
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
			Log.d("catch", "silakan tangkap");
			isCatching = false;
		}
	}
	
	protected void freeInsects(){
//		if (dz >= -15.0f){
			prevAmount = amount;
			amount = 0;
			Log.d("amount", "free amount");
//		}
	}
	
	protected Insect createInsect(){
		Random randomGenerator = new Random();
		
		int type = randomGenerator.nextInt(7) + 1;
		float initX = randomGenerator.nextFloat() * (activity.mCamera.getWidth() - INSECT_WIDTH);
		float initY = (randomGenerator.nextFloat() * (activity.mCamera.getHeight()- INSECT_WIDTH)) + INSECT_WIDTH;
		
		// CEK DI BAGIAN SINI
		ctype = (ctype == 0? type : ctype);
		
		switch (type) {
			case 1: 
				Log.d("insect", "buat beetle di posisi " + initX + "," + initY); 
//				return new Insect(initX, initY, beetleTiledTextureRegion, this.getVertexBufferObjectManager(), type);
				return new Insect(initX, initY, ladybugTiledTextureRegion, this.getVertexBufferObjectManager(), type);
			case 2: 
				Log.d("insect", "buat ladybug di posisi " + initX + "," + initY);
				return new Insect(initX, initY, ladybugTiledTextureRegion, this.getVertexBufferObjectManager(), type);
			case 3: 
				Log.d("insect", "buat grasshopper di posisi " + initX + "," + initY);
//				return new Insect(initX, initY, grasshopperTiledTextureRegion, this.getVertexBufferObjectManager(), type);
				return new Insect(initX, initY, ladybugTiledTextureRegion, this.getVertexBufferObjectManager(), type);
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
//				return new Insect(initX, initY, timeTiledTextureRegion, this.getVertexBufferObjectManager(), type);
				return new Insect(initX, initY, ladybugTiledTextureRegion, this.getVertexBufferObjectManager(), type);
			default: 
//				return new Insect(initX, initY, beetleTiledTextureRegion, this.getVertexBufferObjectManager(), type);
				return new Insect(initX, initY, ladybugTiledTextureRegion, this.getVertexBufferObjectManager(), type);
		}
	}
	
	public void updateSpritePosition() {
		if(accY < -1.0f) {
			if(accZ < -1.0f) {
				insect.setPosition(insect.getX() - 1.0f, insect.getY() - 1.0f);
			} else if (accZ > -1.0f && accZ < 1.0f) {
				insect.setPosition(insect.getX() - 1.0f, insect.getY());
			} else {
				insect.setPosition(insect.getX() - 1.0f, insect.getY() + 1.0f);
			}
		} else if(accY > -1.0f && accY < 1.0f) {
			if(accZ < -1.0f) {
				insect.setPosition(insect.getX(), insect.getY() - 1.0f);
			} else if (accZ > -1.0f && accZ < 1.0f) {
				insect.setPosition(insect.getX(), insect.getY());
			} else {
				insect.setPosition(insect.getX(), insect.getY() + 1.0f);
			}
		} else {
			if(accZ < -1.0f) {
				insect.setPosition(insect.getX() + 1.0f, insect.getY() - 1.0f);
			} else if (accZ > -1.0f && accZ < 1.0f) {
				insect.setPosition(insect.getX() + 1.0f, insect.getY());
			} else {
				insect.setPosition(insect.getX() + 1.0f, insect.getY() + 1.0f);
			}
		}
		
		// pergerakan khusus masing-masing serangga
		insect.move();
		
//			Log.d("Accelero", "faceX = " + face.getX() + ", faceY = " + face.getY());
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
		Log.d("catch","beetle = 10 x "+beetle+" = " + beetle*10);
		Log.d("catch","ladybug = 25 x "+ladybug+" = " + ladybug*25);
		Log.d("catch","grasshopper = 60 x "+grasshopper+" = " + grasshopper*60);
		Log.d("catch","butterfly = 100 x "+butterfly+" = " + butterfly*100);
		Log.d("catch","honeyBee = 150 x "+honeyBee+" = " + honeyBee*150);
		Log.d("catch","goldenDragonfly = 300 x "+goldenDragonfly+" = " + goldenDragonfly*300);
		Log.d("catch","timeInsect = 5 x "+timeInsect+" = " + timeInsect*5);
		Log.d("catch","COMBO++ = " + (score-((beetle*10)+(ladybug*25)+(grasshopper*60)+(butterfly*100)+(honeyBee*150)+(goldenDragonfly*300)+(timeInsect*5))));
		Log.d("catch","TOTAL SCORE = "+score);
		Log.d("catch","TOTAL TIME = "+totalSec);
	}
	
	private void updateCrosshairColor() {
		if(amount < 5) {
			if(prevAmount == 5) {
				Log.d("crosshair", "case A");
//				gameScene.detachChild(fullCrosshair);
//				gameScene.attachChild(basicCrosshair);
				fullCrosshair.setVisible(false);
				basicCrosshair.setVisible(true);
			} else {
				Log.d("crosshair", "case B");
//				gameScene.detachChild(basicCrosshair);
//				gameScene.attachChild(basicCrosshair);
				basicCrosshair.setVisible(false);
				basicCrosshair.setVisible(true);
			}
		} else {
			Log.d("crosshair", "case C");
//			gameScene.detachChild(basicCrosshair);
//			gameScene.attachChild(fullCrosshair);
			basicCrosshair.setVisible(false);
			fullCrosshair.setVisible(true);
		}
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
		
//		Log.d("Acceleration", "vX = " + vX + ", vY = " + vY + ", vZ = " + vZ);
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
	}
	
	@Override
	protected void onDestroy()
	{
		Log.d("debug", "masuk PlayScene.onDestroy");
		gameScene.detachChild(insect);
		super.onDestroy();
	        
	    if (this.isGameLoaded())
	    {
	        System.exit(0);
	    }
	}
}