package com.inspector.missioninsectible.scene;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;

import android.content.Intent;

import com.inspector.missioninsectible.MainGameActivity;

public class LoadingScene extends Scene {
	MainGameActivity activity;
	
	public LoadingScene(){
		
		activity = MainGameActivity.getSharedInstance();
		
		setBackground(new Background(0.0f, 0.0f, 0.0f, 0.0f));
		Sprite background = new Sprite(activity.getCameraWidth()/2,activity.getCameraHeight()/2, activity.mLoadScreenBGRegion, activity.getVertexBufferObjectManager());
		background.setSize(activity.mLoadScreenBGRegion.getWidth()/2, activity.mLoadScreenBGRegion.getHeight()/2);
		attachChild(background);
	
		startGame();
		 
	//	title1.registerEntityModifier(new MoveXModifier(1, title1.getX(), activity.mCamera.getWidth() / 2 - title1.getWidth()));
	}

	public void startGame() {
		// TODO Auto-generated method stub
		DelayModifier dMod = new DelayModifier(4){
			@Override
			protected void onModifierFinished(IEntity pItem) {
				activity.startActivity(new Intent(activity, PlayScene.class));
				activity.setCurrentScene(new MainMenuScene());
			}};
			
			registerEntityModifier(dMod);
		}
}
