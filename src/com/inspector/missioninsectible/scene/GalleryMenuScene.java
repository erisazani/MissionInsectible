package com.inspector.missioninsectible.scene;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.color.Color;

import android.graphics.Typeface;

import com.inspector.missioninsectible.MainGameActivity;
import com.inspector.missioninsectible.misc.InsectDb;

public class GalleryMenuScene extends Scene {
	private final int FONT_SIZE = 32;
	public ITexture mFontTexture;
	public ITexture mFontSecTexture;
	public ITextureRegion mTextureRegion;
	private Font mFont;
	private Text mTextLongestTime,mTextPlayedAmount;
	MainGameActivity activity= MainGameActivity.getSharedInstance();
	InsectDb insectDb = new InsectDb(activity);
	private Font mFontSec;
	private Text mTextSec;
	
	public GalleryMenuScene(){
		
		final Sprite lockedBeetle;
		final Sprite lockedLadybug;
		final Sprite lockedGrasshopper;
		final Sprite lockedButterfly;
		final Sprite lockedBee;
		final Sprite lockedDragonfly;
		final Sprite lockedTimeInsect;
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		Sprite GalleryBoard = new Sprite(activity.getCameraWidth() / 2, activity.getCameraHeight()/2, activity.mGalleryBoardTextureRegion, activity.getVertexBufferObjectManager());
		GalleryBoard.setSize(activity.getCameraWidth(), activity.getCameraHeight());
		attachChild(GalleryBoard);
		
		
		if(!insectDb.isCatched(1)){
			 lockedBeetle = new Sprite(activity.getCameraWidth()/4 - 15, activity.getCameraHeight()/2 - 45, activity.greyBeetle, activity.getVertexBufferObjectManager());
		}else{
			lockedBeetle = new Sprite(activity.getCameraWidth()/4 - 15, activity.getCameraHeight()/2 - 45, activity.coloredBeetle, activity.getVertexBufferObjectManager());
		}
		lockedBeetle.setSize(30,30);
		attachChild(lockedBeetle);
	
		if(!insectDb.isCatched(3)){
			 lockedGrasshopper = new Sprite(activity.getCameraWidth()/4 - 15, activity.getCameraHeight()/2 -5, activity.greyGrasshopper, activity.getVertexBufferObjectManager());
		}else{
			lockedGrasshopper = new Sprite(activity.getCameraWidth()/4 - 15, activity.getCameraHeight()/2 -5, activity.coloredGrasshopper, activity.getVertexBufferObjectManager());
		}
		lockedGrasshopper.setSize(30,30);
		attachChild(lockedGrasshopper);
		
		if(!insectDb.isCatched(4)){
			 lockedButterfly = new Sprite(activity.getCameraWidth()/4 + 40, activity.getCameraHeight()/2 -5, activity.greyButterfly, activity.getVertexBufferObjectManager());
		}else{
			lockedButterfly = new Sprite(activity.getCameraWidth()/4 + 40, activity.getCameraHeight()/2 -5, activity.coloredButterfly, activity.getVertexBufferObjectManager());
		}
		lockedButterfly.setSize(30,30);
		attachChild(lockedButterfly);
				
		if(!insectDb.isCatched(6)){
			lockedDragonfly = new Sprite(activity.getCameraWidth()/4 + 40, activity.getCameraHeight()/2 - 45, activity.greyDragonfly, activity.getVertexBufferObjectManager());
		}else{
			lockedDragonfly = new Sprite(activity.getCameraWidth()/4 + 40, activity.getCameraHeight()/2 - 45, activity.coloredDragonfly, activity.getVertexBufferObjectManager());
		}
		lockedDragonfly.setSize(30,30);
		attachChild(lockedDragonfly);
		
		
		if(!insectDb.isCatched(5)){
			lockedBee = new Sprite(activity.getCameraWidth()/4 - 15, activity.getCameraHeight()/2 +30, activity.greyBee, activity.getVertexBufferObjectManager());
		}else{
			lockedBee = new Sprite(activity.getCameraWidth()/4 - 15, activity.getCameraHeight()/2 +30, activity.coloredBee, activity.getVertexBufferObjectManager());
		}
		lockedBee.setSize(30,30);
		attachChild(lockedBee);
		
		if(!insectDb.isCatched(2)){
			lockedLadybug = new Sprite(activity.getCameraWidth()/4 +40, activity.getCameraHeight()/2 +30, activity.greyLadybug, activity.getVertexBufferObjectManager());
		}else{
			lockedLadybug = new Sprite(activity.getCameraWidth()/4 +40, activity.getCameraHeight()/2 +30, activity.coloredLadybug, activity.getVertexBufferObjectManager());
		}
		lockedLadybug.setSize(30,30);
		attachChild(lockedLadybug);

		this.mFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFontSecTexture = new BitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFontTexture.load();
		this.mFontSecTexture.load();
		
		this.mFont = new Font(activity.getFontManager(), this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), FONT_SIZE, true, Color.BLACK);
		this.mFontSec = new Font(activity.getFontManager(), this.mFontSecTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 18, true, Color.BLACK);
		activity.getFontManager().loadFont(mFont);
		activity.getFontManager().loadFont(mFontSec);
		mTextLongestTime = new Text(activity.mCamera.getWidth()/2+50, activity.mCamera.getHeight()-90, mFont, ""+insectDb.getLongestTime(), 10, activity.getVertexBufferObjectManager());
		mTextSec = new Text(activity.mCamera.getWidth()/2+95, activity.mCamera.getHeight()-90, mFontSec, "sec", 3, activity.getVertexBufferObjectManager());
		mTextPlayedAmount = new Text(activity.mCamera.getWidth()/2+50, activity.mCamera.getHeight()/2-40, mFont, ""+insectDb.getPlayedAmount(), 10, activity.getVertexBufferObjectManager());
		attachChild(mTextLongestTime);
		attachChild(mTextSec);
		attachChild(mTextPlayedAmount);
		
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
		
		insectDb.checkGallery();
		
	}
	
}


//package com.inspector.missioninsectible.scene;
//
//import org.andengine.entity.scene.Scene;
//import org.andengine.entity.scene.background.Background;
//import org.andengine.entity.sprite.ButtonSprite;
//import org.andengine.entity.sprite.Sprite;
//import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
//
//import com.inspector.missioninsectible.MainGameActivity;
//
//public class GalleryMenuScene extends Scene {
//	MainGameActivity activity;
//	
//	public GalleryMenuScene(){
//
//		activity = MainGameActivity.getSharedInstance();
//		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
//		Sprite GalleryBoard = new Sprite(activity.getCameraWidth() / 2, activity.getCameraHeight()/2, activity.mGalleryBoardTextureRegion, activity.getVertexBufferObjectManager());
//		GalleryBoard.setSize(activity.getCameraWidth(), activity.getCameraHeight());
//		attachChild(GalleryBoard);
//		
//		final Sprite lockedButterfly = new Sprite(activity.getCameraWidth()/4 - 20, activity.getCameraHeight()/2 + 30, activity.greyButterfly, activity.getVertexBufferObjectManager());
//		lockedButterfly.setSize(30,30);
//		attachChild(lockedButterfly);
//		
//		final Sprite lockedDragonfly = new Sprite(activity.getCameraWidth()/4 + 30, activity.getCameraHeight()/2 + 30, activity.greyDragonfly, activity.getVertexBufferObjectManager());
//		lockedDragonfly.setSize(30,30);
//		attachChild(lockedDragonfly);
//		
//		final Sprite lockedBee = new Sprite(activity.getCameraWidth()/4 - 20, activity.getCameraHeight()/2 - 5, activity.greyBee, activity.getVertexBufferObjectManager());
//		lockedBee.setSize(30,30);
//		attachChild(lockedBee);
//		
//		final Sprite HomeMenuItem = new ButtonSprite(activity.getCameraWidth() / 2, activity.mHomeTextureRegion.getHeight()/2, activity.mHomeTextureRegion, activity.getVertexBufferObjectManager(), new OnClickListener() {
//			
//			@Override
//			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
//					float pTouchAreaLocalY) {
//				// TODO Auto-generated method stub
//				 activity.setCurrentScene(new MainMenuScene());
//			}
//		});
//		HomeMenuItem.setSize(30, 30);
//		registerTouchArea(HomeMenuItem);
//		attachChild(HomeMenuItem);
//		
//		setTouchAreaBindingOnActionDownEnabled(true);
//		
//	}
//	
//}
