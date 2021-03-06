package com.inspector.missioninsectible.scene;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;

import com.inspector.missioninsectible.MainGameActivity;

public class AboutMenuScene extends Scene {

	MainGameActivity activity = MainGameActivity.getSharedInstance();
	
	public AboutMenuScene(){
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		
		Sprite AboutBoard = new Sprite(activity.getCameraWidth() / 2, activity.getCameraHeight() / 2, activity.mAboutBoardTextureRegion, activity.getVertexBufferObjectManager());
		AboutBoard.setSize(activity.getCameraWidth(), activity.getCameraHeight());
		attachChild(AboutBoard);
		
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
