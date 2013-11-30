package com.inspector.missioninsectible.scene;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.Sprite;

import com.inspector.missioninsectible.MainGameActivity;

public class MainMenuScene extends MenuScene implements IOnMenuItemClickListener{
	private static final int MENU_PLAY = 0;
	private static final int MENU_BATTLE = MENU_PLAY + 1;
	private static final int MENU_GALLERY = MENU_PLAY + 2;
	private static final int MENU_QUIT = MENU_PLAY + 3;
	private static final int MENU_ABOUT = MENU_PLAY + 4;
	private static final int MENU_HOWTO = MENU_PLAY + 5;
	private static final int MENU_HISCORE = MENU_PLAY + 6;
	MainGameActivity activity;
	
	
	public MainMenuScene(){
		super(MainGameActivity.getSharedInstance().mCamera);
		activity = MainGameActivity.getSharedInstance();
		
		setBackgroundEnabled(false);
		Sprite backgroundMenu = new Sprite(activity.getCameraWidth()/2,activity.getCameraHeight()/2, activity.mMenuBackgroundTextureRegion, activity.getVertexBufferObjectManager());
		backgroundMenu.setSize(activity.getCameraWidth(), activity.getCameraHeight());
		attachChild(backgroundMenu);
		
		final SpriteMenuItem playMenuItem = new SpriteMenuItem(MENU_PLAY, activity.mMenuPlayTextureRegion, activity.getVertexBufferObjectManager());
		playMenuItem.setPosition(activity.getCameraWidth()/4, (mCamera.getHeight() * 2/3) - 15);
		playMenuItem.setSize(80, 35);
		addMenuItem(playMenuItem);

		final SpriteMenuItem battleMenuItem = new SpriteMenuItem(MENU_BATTLE, activity.mMenuBattleTextureRegion, activity.getVertexBufferObjectManager());
		battleMenuItem.setPosition(activity.getCameraWidth()/4, (mCamera.getHeight() * 2/3) - 55);
		battleMenuItem.setSize(80, 35);
		addMenuItem(battleMenuItem);
		
		final SpriteMenuItem galleryMenuItem = new SpriteMenuItem(MENU_GALLERY, activity.mMenuGalleryTextureRegion, activity.getVertexBufferObjectManager());
		galleryMenuItem.setPosition(activity.getCameraWidth()/4, (mCamera.getHeight() * 2/3) - 95);
		galleryMenuItem.setSize(80, 35);
		addMenuItem(galleryMenuItem);

		final SpriteMenuItem hiScoreMenuItem = new SpriteMenuItem(MENU_HISCORE, activity.mMenuScoreTextureRegion, activity.getVertexBufferObjectManager());
		hiScoreMenuItem.setPosition(activity.getCameraWidth()*3/4, (mCamera.getHeight() *2/3) - 95);
		hiScoreMenuItem.setSize(80, 35);
		addMenuItem(hiScoreMenuItem);

		final SpriteMenuItem howToMenuItem = new SpriteMenuItem(MENU_HOWTO, activity.mMenuHowToTextureRegion, activity.getVertexBufferObjectManager());
		howToMenuItem.setPosition(activity.getCameraWidth()*3/4, mCamera.getHeight() * 2/3 - 15);
		howToMenuItem.setSize(80, 35);
		addMenuItem(howToMenuItem);
		
		final SpriteMenuItem aboutMenuItem = new SpriteMenuItem(MENU_ABOUT, activity.mMenuAboutTextureRegion, activity.getVertexBufferObjectManager());
		aboutMenuItem.setPosition(activity.getCameraWidth()*3/4, mCamera.getHeight() * 2/3 - 55);
		aboutMenuItem.setSize(80, 35);
		addMenuItem(aboutMenuItem);

		final SpriteMenuItem quitMenuItem = new SpriteMenuItem(MENU_QUIT, activity.mMenuQuitTextureRegion, activity.getVertexBufferObjectManager());
		quitMenuItem.setPosition(activity.getCameraWidth()/2, mCamera.getHeight() * 2/3 - 135);
		quitMenuItem.setSize(80, 35);
		addMenuItem(quitMenuItem);


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
	        	activity.setCurrentScene(new LoadingScene());
	            return true;
	        case MENU_BATTLE:
	        	activity.setCurrentScene(new BattleModeMenuScene());
	            return true;
	        case MENU_GALLERY:
	        	activity.setCurrentScene(new GalleryMenuScene());
	            return true;
	        case MENU_HISCORE:
	        	activity.setCurrentScene(new HiScoreMenuScene());
	            return true;
	        case MENU_HOWTO:
	        	activity.setCurrentScene(new HelpMenuScene());
	            return true;
	        case MENU_ABOUT:
	        	activity.setCurrentScene(new AboutMenuScene());
	            return true;
	        case MENU_QUIT:
	        	activity.finish();
	            return true;
	        default:
	            break;
	    }
	    return false;
	}

}
