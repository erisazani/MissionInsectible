package com.inspector.missioninsectible.scene;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;

import com.inspector.missioninsectible.MainGameActivity;

public class BattleModeMenuScene extends Scene {
MainGameActivity activity = MainGameActivity.getSharedInstance();
	
	public BattleModeMenuScene(){
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		
		Sprite BattleBoard = new Sprite(activity.getCameraWidth() / 2, activity.getCameraHeight() / 2, activity.mBattleBoardTextureRegion, activity.getVertexBufferObjectManager());
		BattleBoard.setSize(activity.getCameraWidth(), activity.getCameraHeight());
		attachChild(BattleBoard);

		final Sprite HomeMenuItem = new ButtonSprite(activity.getCameraWidth()/2 - 50,activity.mHomeTextureRegion.getHeight()/2, activity.mHomeTextureRegion, activity.getVertexBufferObjectManager(), new OnClickListener() {
			
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				 activity.setCurrentScene(new MainMenuScene());
			}
		});
		
		final Sprite PlayBattle = new ButtonSprite(activity.getCameraWidth() / 2 + 50, activity.mPlayBattleTextureRegion.getHeight()/2, activity.mPlayBattleTextureRegion, activity.getVertexBufferObjectManager(), new OnClickListener() {
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
		
		PlayBattle.setSize(30, 30);
		registerTouchArea(PlayBattle);
		attachChild(PlayBattle);
		
		setTouchAreaBindingOnActionDownEnabled(true);
		}
}
