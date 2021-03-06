package net.mostlyoriginal.game.system.view;

import com.artemis.E;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Json;
import net.mostlyoriginal.api.component.ui.Label;
import net.mostlyoriginal.api.manager.AbstractAssetSystem;
import net.mostlyoriginal.game.GameRules;
import net.mostlyoriginal.game.component.SpriteData;
import net.mostlyoriginal.game.system.render.SpriteLibrary;

// Sistema de activos de pantalla de juego
@Wire public class GameScreenAssetSystem extends AbstractAssetSystem {
	public static final int DANCING_MAN_HEIGHT = 36;
	public static final int UNIT = 32;
	public static final int ENTRANCE_WIDTH = UNIT * 3;
	public static final int TOILET_WIDTH = UNIT;
	public static final int SINK_WIDTH = UNIT;
	public static final int URINAL_WIDTH = UNIT;
	public static final int TIPS_WIDTH = UNIT;
	public static final int SUPPLY_CLOSET_WIDTH = UNIT * 2;
	public static final int VISITOR_WIDTH = 24;
	public static final int VISITOR_HEIGHT = 38;

	public static final int LAYER_BACKGROUND = 1;
	public static final int LAYER_BEHIND_ACTORS = 5;
	public static final int LAYER_CLOCK = 20;
	public static final int LAYER_TOILET_DOOR = 100;
	public static final int LAYER_PRESIDENT = 1800;
	public static final int LAYER_PRESIDENT_HEAD = 1850;
	public static final int LAYER_CAR = 1850;
	public static final int LAYER_ACTORS = 2000;
	public static final int LAYER_PLAYER = 1000;
	public static final int LAYER_ICONS = 2100;
	public static final int LAYER_PARTICLES = 3000;
	public static final int LAYER_ACTORS_BUSY = 90;

	public static final int DEFAULT_MODULE_HEIGHT = UNIT * 5 + 16;
	public static final int PLAYER_WIDTH = 24;
	public static final int PLAYER_HEIGHT = 36;
	public static final int MAIN_DOOR_WIDTH = 24;
	public static final float WALK_FRAME_DURATION = 0.03f * (150f / GameRules.WALKING_SPEED_VISITORS);
	public static final float PLAYER_IDLE_FRAME_DURATION = 0.2f;
	public static final float PLAYER_USE_FRAME_DURATION = 0.2f;
	public static final float PLAYER_WALK_FRAME_DURATION = 0.06f;
	public static final int BUILDING_WIDTH = 128;
	public static final int BUILDING_HEIGHT = 360;

	private static final float WASH_FRAME_DURATION = 0.6f;
	public static final float LOW_VOLUME = 0.01f;
	private Music music;
	private SpriteLibrary spriteLibrary;

	public GameScreenAssetSystem() {
		super("tileset.png");
	}

	@Override protected void initialize() {
		super.initialize();

		loadSprites();

		if (GameRules.MUSIC_ENABLED) {
			// Si la musica es null o no esta sonando, entonces...
			if (GameRules.music == null || !GameRules.music.isPlaying()) playMusic("sfx/music.mp3"); // No acortes la musica
		}
	}

	// Entradas del teclado
	@Override protected void processSystem() {
		super.processSystem();
		// Si se presiono la tecla M
		if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
			// Si la musica esta sonando
			if (GameRules.music.isPlaying()) GameRules.music.pause(); // Le pone pausa
			else GameRules.music.play(); // Le pone play

		}
	}

	public static void playMusic(String mp3) {

		// Si ya hay un objeto creado de tipo Music, entonces...
		if (GameRules.music != null) {
			/* Detiene una instancia de musica en reproduccion o en pausa. La proxima vez que se invoque play(), la
			 musica comenzara desde el principio. */
			// GameRules.music.stop(); // Hace falta?
			// Elimina el objeto
			GameRules.music.dispose();
		}

		GameRules.music = Gdx.audio.newMusic(Gdx.files.internal(mp3));
		// GameRules.music.stop(); // Hace falta?
		GameRules.music.setLooping(true);

		if (GameRules.musicOn) GameRules.music.play();

		/**
		 * Establece la panoramica y el volumen de esta transmision de musica.
		 * @param pan: panoramica en el rango de -1 (completamente a la izquierda) a 1 (completamente a la derecha). 0 es la posicion central.
		 * @param volume: el volumen en el rango [0,1]. */
		GameRules.music.setPan(0, 0.1f);
	}

	// Carga los sprites
	private void loadSprites() {
		final Json json = new Json();
		spriteLibrary = json.fromJson(SpriteLibrary.class, Gdx.files.internal("sprites.json"));
		for (SpriteData sprite : spriteLibrary.sprites) {
			Animation animation = add(sprite.id, sprite.x, sprite.y, sprite.width, sprite.height, sprite.countX, sprite.countY, this.tileset,
					sprite.milliseconds * 0.001f);
			if (!sprite.repeat) {
				animation.setPlayMode(Animation.PlayMode.NORMAL);
			} else animation.setPlayMode(Animation.PlayMode.LOOP);
		}
	}
}
