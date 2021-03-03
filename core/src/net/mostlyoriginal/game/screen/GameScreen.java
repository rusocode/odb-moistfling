package net.mostlyoriginal.game.screen;

import com.artemis.MyFluidEntityPlugin;
import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.link.EntityLinkManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.mostlyoriginal.api.SingletonPlugin;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.manager.FontManager;
import net.mostlyoriginal.api.system.camera.CameraSystem;
import net.mostlyoriginal.api.system.graphics.RenderBatchingSystem;
import net.mostlyoriginal.api.system.mouse.MouseCursorSystem;
import net.mostlyoriginal.api.system.physics.PhysicsSystem;
import net.mostlyoriginal.game.GameRules;
import net.mostlyoriginal.game.GdxArtemisGame;
import net.mostlyoriginal.game.system.*;
import net.mostlyoriginal.game.system.box2d.BoxPhysicsDebugRenderSystem;
import net.mostlyoriginal.game.system.box2d.BoxPhysicsMouseSystem;
import net.mostlyoriginal.game.system.box2d.BoxPhysicsSystem;
import net.mostlyoriginal.game.system.control.*;
import net.mostlyoriginal.game.system.future.FutureEntitySystem;
import net.mostlyoriginal.game.system.logic.*;
import net.mostlyoriginal.game.system.map.*;
import net.mostlyoriginal.game.system.mechanics.LevelTimerSystem;
import net.mostlyoriginal.game.system.render.*;
import net.mostlyoriginal.game.system.view.GameScreenAssetSystem;
import net.mostlyoriginal.game.system.view.MyClearScreenSystem;
import net.mostlyoriginal.plugin.OperationsPlugin;

// Pantalla de juego principal
public class GameScreen extends TransitionableWorldScreen {

	public static final String BACKGROUND_COLOR_HEX = "000000";

	Class nextScreen;

	// Crea el mundo del juego
	@Override protected World createWorld() {
		RenderBatchingSystem renderBatchingSystem;

		final SpriteBatch batch = new SpriteBatch(2000);

		/**
		 * Constructor de mundos.
		 * Permite la adicion conveniente de var-arg de sistemas, administradores. Admite complementos.
		 * */
		WorldConfigurationBuilder worldConfigurationBuilder = new WorldConfigurationBuilder()
				// dependsOn() especifiqua la dependencia de los sistemas/complementos
				.dependsOn(MyFluidEntityPlugin.class, EntityLinkManager.class, OperationsPlugin.class, SingletonPlugin.class).with(new EventSystem())
				// with() agrega los sistemas activos, se conserva el orden. Solo se permite una instancia de cada clase.
				.with(new FontManager(), new TagManager(), new TiledMapManager(GameRules.nextMap))
				.with(new CameraSystem(2), new MyClearScreenSystem(Color.valueOf(BACKGROUND_COLOR_HEX)), // Probablemente bien
						new GameScreenAssetSystem(), new MapEntitySpawnerSystem(), // Convierte mapas en mosaico en FutureEntities para que se generen

						new FutureEntitySystem(new MyEntityAssemblyStrategy()), // Responsable de las entidades de desove
						new ParticleEffectSystem(new MyParticleEffectStrategy()), new PlayerControlSystem(),
						// @todo fase 2: movimiento separado del enlace de teclas al control

						new PhysicsSystem(), // Para particulas
						new BoxPhysicsSystem(), new BoxPhysicsMouseSystem(), new LatchingSystem(), new MouseCursorSystem(),

						new PickupSystem(), new LeakSystem(), new BeamedSystem(),

						new LevelTimerSystem(),

						new CameraFollowSystem(), new PlayerAnimationSystem(),

						new BreathingSfxSystem(), new LeakSfxSystem(),

						new RenderBackgroundSystem(),

						renderBatchingSystem = new RenderBatchingSystem(), new MyAnimRenderSystem(renderBatchingSystem),
						new MyLabelRenderSystem(renderBatchingSystem), new MapLayerRenderSystem(renderBatchingSystem, batch), new UiSystem(),
						new SoundPlaySystem("leak-loop.wav", "oxygen-recharge-1", "oxygen-recharge-2", "astronaut-pops", "breath-normal",
								"breath-laboured", "breath-suffocating", "suit-puncture", "suit-almost-puncture", "suit-last-oxygen-escapes",
								"ship-reached", "tractor-lock-1.wav", "tractor-lock-2.wav", "tractor-unlock.wav", "orb-on", "randomise"),
						new BoxPhysicsDebugRenderSystem(), new TransitionSystem(GdxArtemisGame.getInstance(), this));

		if (GameRules.DEBUG_ENABLED) {
			// worldConfigurationBuilder.with(new DebugOptionControlSystem());
		}

		return new World(worldConfigurationBuilder.build());
	}
}
