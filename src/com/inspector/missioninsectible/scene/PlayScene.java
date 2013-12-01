package com.inspector.missioninsectible.scene;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
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

public class PlayScene extends Scene{
	
	MainGameActivity activity;
	
	public PlayScene() {
//		super();
		activity = MainGameActivity.getSharedInstance();
		
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
//		Text countDown = new Text(10,10, activity.time ,"10", activity.getVertexBufferObjectManager());
//		attachChild(countDown);

//		// animated insect
//		final AnimatedSprite bee = new AnimatedSprite(activity.getCameraWidth()/2, activity.getCameraHeight()/2, activity.beeTiledTextureRegion, activity.getVertexBufferObjectManager());
//		bee.setSize(40, 40);
//		bee.animate(50);
//		attachChild(bee);
//		
//		final AnimatedSprite dragonfly = new AnimatedSprite(activity.getCameraWidth()/4, activity.getCameraHeight()/2, activity.dragonflyTiledTextureRegion, activity.getVertexBufferObjectManager());
//		dragonfly.setSize(40, 40);
//		dragonfly.animate(100);
//		attachChild(dragonfly);
//		
//		final AnimatedSprite butterfly = new AnimatedSprite(activity.getCameraWidth()*3/4, activity.getCameraHeight()/2, activity.butterflyTiledTextureRegion, activity.getVertexBufferObjectManager());
//		butterfly.setSize(40, 40);
//		butterfly.animate(120);
//		attachChild(butterfly);
//		
//		final AnimatedSprite ladybug = new AnimatedSprite(activity.getCameraWidth()*3/4, activity.getCameraHeight()/4, activity.ladybugTiledTextureRegion, activity.getVertexBufferObjectManager());
//		ladybug.setSize(40, 40);
//		ladybug.animate(120);
//		attachChild(ladybug);
		
		// for kekeran, tinggal ganti posisinya aja ya
//		final Sprite basicCrosshair = new Sprite(activity.getCameraWidth()/2, activity.getCameraHeight()/2, activity.crosshairBasicTextureRegion, activity.getVertexBufferObjectManager());
//		attachChild(basicCrosshair);
//		
//		final Sprite fullCrosshair = new Sprite(activity.getCameraWidth()/4, activity.getCameraHeight()/4, activity.crosshairFullTextureRegion, activity.getVertexBufferObjectManager());
//		attachChild(fullCrosshair);
		
//		// for pause game
//		final Sprite pauseBoard = new Sprite(activity.getCameraWidth()/2, activity.getCameraHeight()/2, activity.pauseGameTextureRegion, activity.getVertexBufferObjectManager());
//		attachChild(pauseBoard);

	}
}