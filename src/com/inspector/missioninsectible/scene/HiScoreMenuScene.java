package com.inspector.missioninsectible.scene;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;

import com.inspector.missioninsectible.MainGameActivity;

public class HiScoreMenuScene extends Scene {
	MainGameActivity activity = MainGameActivity.getSharedInstance();
	
	public HiScoreMenuScene(){
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		
		Sprite HiScoreBoard = new Sprite(activity.getCameraWidth() / 2 - activity.mBoardHiScoreTexture.getWidth()/2 , activity.getCameraHeight() / 2 - activity.mBoardHiScoreTexture.getHeight()/2, activity.mBoardHiScoreTexture, activity.getVertexBufferObjectManager());
		attachChild(HiScoreBoard);
		
		final Sprite HomeMenuItem = new ButtonSprite(activity.getCameraWidth() / 2 - activity.mHomeGalleryTexture.getWidth()/2 , activity.getCameraHeight()- activity.mBoardHowToTexture.getHeight()/2 - 5, activity.mHomeGalleryTexture, activity.getVertexBufferObjectManager(), new OnClickListener() {
			
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				 activity.setCurrentScene(new MainMenuScene());
			}
		});
		registerTouchArea(HomeMenuItem);
		attachChild(HomeMenuItem);
		
		setTouchAreaBindingOnActionDownEnabled(true);
		}
}