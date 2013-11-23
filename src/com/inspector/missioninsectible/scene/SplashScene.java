package com.inspector.missioninsectible.scene;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import com.inspector.missioninsectible.MainGameActivity;

public class SplashScene extends Scene {
	MainGameActivity activity;
	public SplashScene(){
		
		activity = MainGameActivity.getSharedInstance();
		
		
		setBackgroundEnabled(false);
		Sprite background = new Sprite(0,0, activity.mBackgroundTextureRegion, activity.getVertexBufferObjectManager());
		background.setSize(activity.getCameraWidth(), activity.getCameraHeight());
		attachChild(background);
	
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
