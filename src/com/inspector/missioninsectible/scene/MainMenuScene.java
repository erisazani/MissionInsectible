package com.inspector.missioninsectible.scene;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import com.inspector.missioninsectible.MainGameActivity;

public class MainMenuScene extends MenuScene implements IOnMenuItemClickListener{
	private static final int MENU_PLAY = 0;
	private static final int MENU_BATTLE = MENU_PLAY + 1;
	private static final int MENU_GALLERY = MENU_PLAY + 2;
	private static final int MENU_QUIT = MENU_PLAY + 3;
	private static final int MENU_ABOUT = MENU_PLAY + 4;
	private static final int MENU_HOWTO = MENU_PLAY + 5;
	private static final int MENU_HISCORE = MENU_PLAY + 6;

	final Sprite suaraOn; 
	final Sprite suaraOff;
	private boolean soundOn = true;
	private boolean clicked = true;

	MainGameActivity activity;
	final SpriteMenuItem playMenuItem;
	private Sprite clickedPlay;
	private Sprite clickedGallery;
	private SpriteMenuItem galleryMenuItem;
	private SpriteMenuItem hiScoreMenuItem;
	private Sprite clickedHiScore;
	private SpriteMenuItem howToMenuItem;
	private Sprite clickedHowTo;
	private SpriteMenuItem aboutMenuItem;
	private Sprite clickedAbout;
	private SpriteMenuItem quitMenuItem;
	private Sprite clickedQuit;
	private boolean musicOn = true;
	
	
	public MainMenuScene(){
		super(MainGameActivity.getSharedInstance().mCamera);
		activity = MainGameActivity.getSharedInstance();
		
		setBackgroundEnabled(false);
		Sprite backgroundMenu = new Sprite(activity.getCameraWidth()/2,activity.getCameraHeight()/2, activity.mMenuBackgroundTextureRegion, activity.getVertexBufferObjectManager());
		backgroundMenu.setSize(activity.getCameraWidth(), activity.getCameraHeight());
		attachChild(backgroundMenu);
		
		playMenuItem = new SpriteMenuItem(MENU_PLAY, activity.mMenuPlayTextureRegion, activity.getVertexBufferObjectManager());
		playMenuItem.setPosition(activity.getCameraWidth()/4, (mCamera.getHeight() * 2/3) - 15);
		playMenuItem.setSize(80, 35);
		clickedPlay = new Sprite(activity.getCameraWidth()/4, (mCamera.getHeight() * 2/3) - 15, activity.mMenuPlayClickedTextureRegion, activity.getVertexBufferObjectManager());
		clickedPlay.setVisible(false);
		clickedPlay.setSize(80, 35);
		addMenuItem(playMenuItem);
		attachChild(clickedPlay);
		
		final SpriteMenuItem battleMenuItem = new SpriteMenuItem(MENU_BATTLE, activity.mMenuBattleTextureRegion, activity.getVertexBufferObjectManager());
		battleMenuItem.setPosition(activity.getCameraWidth()/4, (mCamera.getHeight() * 2/3) - 55);
		battleMenuItem.setSize(80, 35);
		addMenuItem(battleMenuItem);
		
		galleryMenuItem = new SpriteMenuItem(MENU_GALLERY, activity.mMenuGalleryTextureRegion, activity.getVertexBufferObjectManager());
		galleryMenuItem.setPosition(activity.getCameraWidth()/4, (mCamera.getHeight() * 2/3) - 95);
		galleryMenuItem.setSize(80, 35);
		clickedGallery = new Sprite(activity.getCameraWidth()/4, (mCamera.getHeight() * 2/3) - 95, activity.mMenuGalleryClickedTextureRegion, activity.getVertexBufferObjectManager());
		clickedGallery.setVisible(false);
		clickedGallery.setSize(80, 35);
		addMenuItem(galleryMenuItem);
		attachChild(clickedGallery);
		

		hiScoreMenuItem = new SpriteMenuItem(MENU_HISCORE, activity.mMenuScoreTextureRegion, activity.getVertexBufferObjectManager());
		hiScoreMenuItem.setPosition(activity.getCameraWidth()*3/4, (mCamera.getHeight() *2/3) - 95);
		hiScoreMenuItem.setSize(80, 35);
		clickedHiScore = new Sprite(activity.getCameraWidth()*3/4, (mCamera.getHeight() * 2/3) - 95, activity.mMenuScoreClickedTextureRegion, activity.getVertexBufferObjectManager());
		clickedHiScore.setVisible(false);
		clickedHiScore.setSize(80, 35);
		addMenuItem(hiScoreMenuItem);
		attachChild(clickedHiScore);

		howToMenuItem = new SpriteMenuItem(MENU_HOWTO, activity.mMenuHowToTextureRegion, activity.getVertexBufferObjectManager());
		howToMenuItem.setPosition(activity.getCameraWidth()*3/4, mCamera.getHeight() * 2/3 - 15);
		howToMenuItem.setSize(80, 35);
		clickedHowTo = new Sprite(activity.getCameraWidth()*3/4, (mCamera.getHeight() * 2/3) - 15, activity.mMenuHowToClickedTextureRegion, activity.getVertexBufferObjectManager());
		clickedHowTo.setVisible(false);
		clickedHowTo.setSize(80, 35);
		addMenuItem(howToMenuItem);
		attachChild(clickedHowTo);
		
		aboutMenuItem = new SpriteMenuItem(MENU_ABOUT, activity.mMenuAboutTextureRegion, activity.getVertexBufferObjectManager());
		aboutMenuItem.setPosition(activity.getCameraWidth()*3/4, mCamera.getHeight() * 2/3 - 55);
		aboutMenuItem.setSize(80, 35);
		clickedAbout = new Sprite(activity.getCameraWidth()*3/4, (mCamera.getHeight() * 2/3) - 55, activity.mMenuAboutClickedTextureRegion, activity.getVertexBufferObjectManager());
		clickedAbout.setVisible(false);
		clickedAbout.setSize(80, 35);
		addMenuItem(aboutMenuItem);
		attachChild(clickedAbout);

		quitMenuItem = new SpriteMenuItem(MENU_QUIT, activity.mMenuQuitTextureRegion, activity.getVertexBufferObjectManager());
		quitMenuItem.setPosition(activity.getCameraWidth()/2, mCamera.getHeight() * 2/3 - 135);
		quitMenuItem.setSize(80, 35);
		clickedQuit = new Sprite(activity.getCameraWidth()/2, (mCamera.getHeight() * 2/3) - 135, activity.mMenuQuitClickedTextureRegion, activity.getVertexBufferObjectManager());
		clickedQuit.setVisible(false);
		clickedQuit.setSize(80, 35);
		addMenuItem(quitMenuItem);
		attachChild(clickedQuit);
		
		activity.BGM.play();
		
		suaraOn= new Sprite(activity.getCameraWidth() - activity.SoundOnTextureRegion.getWidth()/2, activity.getCameraHeight() - activity.SoundOnTextureRegion.getHeight()/2, activity.SoundOnTextureRegion, activity.getVertexBufferObjectManager()){

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				if(pSceneTouchEvent.isActionDown()){
					
					if (musicOn == true) {
						activity.BGM.pause();
						musicOn = false;
					} else {
						activity.BGM.play();
						musicOn = true;
					}
					 soundOn = !soundOn;
			         suaraOn.setVisible(soundOn);
			         suaraOff.setVisible(!soundOn);	
				}				
		         return true;
			}
		};
		suaraOn.setSize(40, 40);
		registerTouchArea(suaraOn);				
		suaraOff= new Sprite(activity.getCameraWidth() - activity.SoundOffTextureRegion.getWidth()/2, activity.getCameraHeight() - activity.SoundOnTextureRegion.getHeight()/2, activity.SoundOffTextureRegion, activity.getVertexBufferObjectManager());	
		suaraOff.setSize(40, 40);
		suaraOff.setVisible(false);
		attachChild(suaraOn);
		attachChild(suaraOff);
//		final Sprite soundOn =  new Sprite(activity.getCameraWidth(), activity.getCameraHeight(), activity.s, activity);


		//buildAnimations();

		

		//setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
	//	IMenuItem startButton = new TextMenuItem(MENU_START, activity.mFont, activity.getString(R.string.start), activity.getVertexBufferObjectManager());
		//startButton.setPosition(mCamera.getWidth() / 2 - startButton.getWidth() / 2, mCamera.getHeight() / 2 - startButton.getHeight() / 2);
		//addMenuItem(startButton);



		setOnMenuItemClickListener(this);
		 
	}

	
	@Override
	public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1, float arg2, float arg3) {
	    switch (arg1.getID()) {
	        case MENU_PLAY:
	        	activity.mMenuClickedSound.play();
	        	clicked = !clicked;
	        	playMenuItem.setVisible(clicked);
	        	clickedPlay.setVisible(!clicked);
	        	clickDelay(new LoadingScene());
	            return true;
	        case MENU_BATTLE:
	            return true;
	        case MENU_GALLERY:
	        	activity.mMenuClickedSound.play();
	        	clicked = !clicked;
	        	galleryMenuItem.setVisible(clicked);
	        	clickedGallery.setVisible(!clicked);
	        	clickDelay(new GalleryMenuScene());
	            return true;
	        case MENU_HISCORE:
	        	activity.mMenuClickedSound.play();
	        	clicked = !clicked;
	        	hiScoreMenuItem.setVisible(clicked);
	        	clickedHiScore.setVisible(!clicked);
	        	clickDelay(new HiScoreMenuScene());
	            return true;
	        case MENU_HOWTO:
	        	activity.mMenuClickedSound.play();
	        	clicked = !clicked;
	        	howToMenuItem.setVisible(clicked);
	        	clickedHowTo.setVisible(!clicked);
	        	clickDelay(new HelpMenuScene());
	            return true;
	        case MENU_ABOUT:
	        	activity.mMenuClickedSound.play();
	        	clicked = !clicked;
	        	aboutMenuItem.setVisible(clicked);
	        	clickedAbout.setVisible(!clicked);
	        	clickDelay(new AboutMenuScene());
	            return true;
	        case MENU_QUIT:
	        	activity.mMenuClickedSound.play();
	        	clicked = !clicked;
	        	quitMenuItem.setVisible(clicked);
	        	clickedQuit.setVisible(!clicked);
	        	activity.finish();
	            return true;	
	        default:
	            break;
	    }
	    return false;
	}
	
	public void clickDelay(final Scene scene) {
		// TODO Auto-generated method stub
		DelayModifier dMod = new DelayModifier(1){
			@Override
			protected void onModifierFinished(IEntity pItem) {
			        activity.setCurrentScene(scene);
			    }
			};
			
			registerEntityModifier(dMod);
		}

}
