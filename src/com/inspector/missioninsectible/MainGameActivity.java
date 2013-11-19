package com.inspector.missioninsectible;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainGameActivity extends SimpleBaseGameActivity {

	static final int CAMERA_WIDTH = 480;
	static final int CAMERA_HEIGHT = 800;
	private Camera mCamera;
	private Scene mMainScene;
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TextureRegion mPlayerTextureRegion;


	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
	}

	@Override
	protected void onCreateResources() {
		// Load all the textures this game needs.
		mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 32, 32);
		mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "face_box.png", 0, 0);
		mBitmapTextureAtlas.load();
	}

	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger()); // logs the frame rate

		// Create Scene and set background colour to (1, 1, 1) = white
		this.mMainScene = new Scene();
		this.mMainScene.setBackground(new Background(1, 1, 1));

		// Centre the player on the camera.
		final int iStartX = (CAMERA_WIDTH - mBitmapTextureAtlas.getWidth()) / 2;
		final int iStartY = (CAMERA_HEIGHT - mBitmapTextureAtlas.getHeight()) / 2;

		// Create the sprite and add it to the scene.
		final Sprite oPlayer = new Sprite(iStartX, iStartY, mPlayerTextureRegion, getVertexBufferObjectManager());
		this.mMainScene.attachChild(oPlayer);

		return this.mMainScene;
	}
		

}
