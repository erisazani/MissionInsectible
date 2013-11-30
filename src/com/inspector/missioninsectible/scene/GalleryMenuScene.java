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
		Sprite GalleryBoard = new Sprite(activity.getCameraWidth() / 2, activity.getCameraHeight()/2, activity.mGalleryBoardTextureRegion, activity.getVertexBufferObjectManager());
		GalleryBoard.setSize(activity.getCameraWidth(), activity.getCameraHeight());
		attachChild(GalleryBoard);
		
		final Sprite lockedButterfly = new Sprite(activity.getCameraWidth()/4 - 20, activity.getCameraHeight()/2 + 30, activity.greyButterfly, activity.getVertexBufferObjectManager());
		lockedButterfly.setSize(30,30);
		attachChild(lockedButterfly);
		
		final Sprite lockedDragonfly = new Sprite(activity.getCameraWidth()/4 + 30, activity.getCameraHeight()/2 + 30, activity.greyDragonfly, activity.getVertexBufferObjectManager());
		lockedDragonfly.setSize(30,30);
		attachChild(lockedDragonfly);
		
		final Sprite lockedBee = new Sprite(activity.getCameraWidth()/4 - 20, activity.getCameraHeight()/2 - 5, activity.greyBee, activity.getVertexBufferObjectManager());
		lockedBee.setSize(30,30);
		attachChild(lockedBee);
		
		final Sprite HomeMenuItem = new ButtonSprite(activity.getCameraWidth() / 2, activity.mHomeTextureRegion.getHeight()/2, activity.mHomeTextureRegion, activity.getVertexBufferObjectManager(), new OnClickListener() {
			
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				 activity.setCurrentScene(new MainMenuScene());
			}
		});
		HomeMenuItem.setSize(30, 30);
		registerTouchArea(HomeMenuItem);
		attachChild(HomeMenuItem);
		
		setTouchAreaBindingOnActionDownEnabled(true);
		
	}
	
}
