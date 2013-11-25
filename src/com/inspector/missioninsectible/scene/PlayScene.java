package com.inspector.missioninsectible.scene;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
//import org.andengine.extension.augmentedreality.BaseAugmentedRealityGameActivity;
import org.andengine.input.sensor.orientation.IOrientationListener;
import org.andengine.input.sensor.orientation.OrientationData;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;

import org.andengine.util.debug.Debug;

import com.inspector.missioninsectible.MainGameActivity;

//import com.example.tutor.BaseActivity;

import android.util.Log;
import android.widget.Toast;

public class PlayScene extends Scene {
	
	MainGameActivity activity;
	
	public PlayScene() {
		super();
		activity = MainGameActivity.getSharedInstance();
		
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		Text countDown = new Text(10,10, activity.time ,"10", activity.getVertexBufferObjectManager());
		attachChild(countDown);
//		final float centerX = (activity.mCamera.getWidth() - activity.mFaceTextureRegion.getWidth()) / 2;
//		final float centerY = (activity.mCamera.getHeight() - activity.mFaceTextureRegion.getHeight()) / 2;
//		//final Sprite face = new Sprite(centerX, centerY, activity.mFaceTextureRegion, activity.getVertexBufferObjectManager());
//		face.registerEntityModifier(new MoveModifier(30, 0, 320 - face.getWidth(), 0, 240 - face.getHeight()));
//		attachChild(face);
	}

}