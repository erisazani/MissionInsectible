package com.inspector.missioninsectible.scene;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;

import com.inspector.missioninsectible.MainGameActivity;

public class SplashScene extends Scene {
	MainGameActivity activity;
	public SplashScene(){
		
		activity = MainGameActivity.getSharedInstance();
		
		
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		Sprite logo = new Sprite((activity.getCameraWidth())/2,(activity.getCameraHeight())/2, activity.mLogoTextureRegion, activity.getVertexBufferObjectManager());
		//logo.setSize(activity.getCameraWidth(), activity.getCameraHeight());
		attachChild(logo);
	
		loadResources();
		 
	//	title1.registerEntityModifier(new MoveXModifier(1, title1.getX(), activity.mCamera.getWidth() / 2 - title1.getWidth()));
	}
	
	public void loadResources() {
		DelayModifier dMod = new DelayModifier(4){
		 
		@Override
		protected void onModifierFinished(IEntity pItem) {
			//	Log.d("menu", "masuk");
		        activity.setCurrentScene(new MainMenuScene());
		    }
		};
		registerEntityModifier(dMod);
	}

}
