package net.mostlyoriginal.game.screen;

import com.artemis.SuperMapper;
import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.managers.TagManager;
import net.mostlyoriginal.api.SingletonPlugin;
import net.mostlyoriginal.api.manager.FontManager;
import net.mostlyoriginal.api.system.camera.CameraSystem;
import net.mostlyoriginal.api.system.graphics.RenderBatchingSystem;
import net.mostlyoriginal.game.GdxArtemisGame;
import net.mostlyoriginal.game.system.render.MyAnimRenderSystem;
import net.mostlyoriginal.game.system.render.MyLabelRenderSystem;
import net.mostlyoriginal.game.system.logic.TransitionSystem;
import net.mostlyoriginal.game.system.view.LogoScreenAssetSystem;
import net.mostlyoriginal.game.system.view.LogoScreenSetupSystem;
import net.mostlyoriginal.plugin.OperationsPlugin;

// Pantalla de introduccion que tambien muestra todas las funciones de artemis-odb habilitadas durante un par de segundos
public class LogoScreen extends TransitionableWorldScreen {

	protected World createWorld() {

		final RenderBatchingSystem renderBatchingSystem;

		return new World(new WorldConfigurationBuilder().dependsOn(OperationsPlugin.class).dependsOn(SingletonPlugin.class)
				.with(WorldConfigurationBuilder.Priority.HIGH,
						// Apoyo
						new SuperMapper(), new TagManager(), new FontManager(), new CameraSystem(2), new LogoScreenAssetSystem()) // zoom: 2
				.with(WorldConfigurationBuilder.Priority.LOW,
						// Procesado y animacion
						renderBatchingSystem = new RenderBatchingSystem(), new MyAnimRenderSystem(renderBatchingSystem),
						new MyLabelRenderSystem(renderBatchingSystem), new LogoScreenSetupSystem(),
						new TransitionSystem(GdxArtemisGame.getInstance(), this)).build());
	}

}
