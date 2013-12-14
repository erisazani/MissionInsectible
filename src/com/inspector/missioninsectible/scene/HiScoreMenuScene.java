package com.inspector.missioninsectible.scene;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
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

public class HiScoreMenuScene extends Scene {
	private final int FONT_SIZE = 14;
	public ITexture mFontTexture;
	public ITextureRegion mTextureRegion;
	private Font mFont;
	private Text mText1,mText2,mText3,mText4,mText5,mText6,mText7,mText8,mText9,mText10;
	MainGameActivity activity = MainGameActivity.getSharedInstance();
	InsectDb insectDb = new InsectDb(activity);
	
	public HiScoreMenuScene(){
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		
		final Sprite HiScoreBoard = new Sprite(activity.getCameraWidth()/2, activity.getCameraHeight()/2, activity.mBoardHiScoreTextureRegion, activity.getVertexBufferObjectManager());
		HiScoreBoard.setSize(activity.getCameraWidth(), activity.getCameraHeight());
		attachChild(HiScoreBoard);
		
		final Sprite HomeMenuItem = new ButtonSprite(activity.getCameraWidth()/2,activity.mHomeTextureRegion.getHeight()/2, activity.mHomeTextureRegion, activity.getVertexBufferObjectManager(), new OnClickListener() {
			
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
		
		this.mFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFontTexture.load();
		
		this.mFont = new Font(activity.getFontManager(), this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), FONT_SIZE, true, Color.BLACK);
		
		activity.getFontManager().loadFont(mFont);
		float initHeight = activity.mCamera.getHeight()-105;
		float range = FONT_SIZE+5;
		mText1 = new Text(activity.mCamera.getWidth()/5+10, initHeight, mFont, insectDb.getHiScoreName(1), 10, activity.getVertexBufferObjectManager());
		mText2 = new Text(activity.mCamera.getWidth()/5+10, initHeight-range, mFont, insectDb.getHiScoreName(2), 10, activity.getVertexBufferObjectManager());
		mText3 = new Text(activity.mCamera.getWidth()/5+10, initHeight-(2*range), mFont, insectDb.getHiScoreName(3), 10, activity.getVertexBufferObjectManager());
		mText4 = new Text(activity.mCamera.getWidth()/5+10, initHeight-(3*range), mFont, insectDb.getHiScoreName(4), 10, activity.getVertexBufferObjectManager());
		mText5 = new Text(activity.mCamera.getWidth()/5+10, initHeight-(4*range), mFont, insectDb.getHiScoreName(5), 10, activity.getVertexBufferObjectManager());
		mText6 = new Text(activity.mCamera.getWidth()/5+175, initHeight, mFont, ""+insectDb.getHiScore(1), 10, activity.getVertexBufferObjectManager());
		mText7 = new Text(activity.mCamera.getWidth()/5+175, initHeight-range, mFont, ""+insectDb.getHiScore(2), 10, activity.getVertexBufferObjectManager());
		mText8 = new Text(activity.mCamera.getWidth()/5+175, initHeight-(2*range), mFont, ""+insectDb.getHiScore(3), 10, activity.getVertexBufferObjectManager());
		mText9 = new Text(activity.mCamera.getWidth()/5+175, initHeight-(3*range), mFont, ""+insectDb.getHiScore(4), 10, activity.getVertexBufferObjectManager());
		mText10 = new Text(activity.mCamera.getWidth()/5+175, initHeight-(4*range), mFont, ""+insectDb.getHiScore(5), 10, activity.getVertexBufferObjectManager());
		attachChild(mText1);
		attachChild(mText2);
		attachChild(mText3);
		attachChild(mText4);
		attachChild(mText5);
		attachChild(mText6);
		attachChild(mText7);
		attachChild(mText8);
		attachChild(mText9);
		attachChild(mText10);
		
		insectDb.printHiScore();
		setTouchAreaBindingOnActionDownEnabled(true);
		}
	
			
}