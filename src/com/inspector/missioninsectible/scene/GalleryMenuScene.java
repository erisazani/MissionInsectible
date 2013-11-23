package com.inspector.missioninsectible.scene;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;

import com.inspector.missioninsectible.MainGameActivity;

public class GalleryMenuScene extends Scene {
	MainGameActivity activity;
	
	public GalleryMenuScene(){

		activity = MainGameActivity.getSharedInstance();
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		Sprite GalleryBoard = new Sprite(activity.getCameraWidth() / 2 - activity.mBoardGalleryTexture.getWidth()/2 , activity.getCameraHeight() / 2 - activity.mBoardGalleryTexture.getHeight()/2, activity.mBoardGalleryTexture, activity.getVertexBufferObjectManager());
		attachChild(GalleryBoard);
		
		final Sprite HomeMenuItem = new ButtonSprite(activity.getCameraWidth() / 2 - activity.mHomeGalleryTexture.getWidth()/2 , activity.getCameraHeight()- activity.mBoardGalleryTexture.getHeight()/2 - 5, activity.mHomeGalleryTexture, activity.getVertexBufferObjectManager(), new OnClickListener() {
			
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
