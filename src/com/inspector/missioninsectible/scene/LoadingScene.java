package com.inspector.missioninsectible.scene;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import com.inspector.missioninsectible.MainGameActivity;

public class LoadingScene extends Scene {
	MainGameActivity activity;
	
	public LoadingScene(){
		
		activity = MainGameActivity.getSharedInstance();
		
		
		setBackgroundEnabled(false);
		Sprite background = new Sprite(0,0, activity.mBackgroundTextureRegion, activity.getVertexBufferObjectManager());
		background.setSize(activity.getCameraWidth(), activity.getCameraHeight());
		attachChild(background);
	
		startGame();
		 
	//	title1.registerEntityModifier(new MoveXModifier(1, title1.getX(), activity.mCamera.getWidth() / 2 - title1.getWidth()));
	}

	public void startGame() {
		// TODO Auto-generated method stub
		DelayModifier dMod = new DelayModifier(4){
			 
			@Override
			protected void onModifierFinished(IEntity pItem) {
			        activity.setCurrentScene(new PlayScene());
			    }
			};
			registerEntityModifier(dMod);
		}
}
