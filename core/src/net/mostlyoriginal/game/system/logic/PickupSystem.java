package net.mostlyoriginal.game.system.logic;

import com.artemis.BaseSystem;
import com.artemis.E;
import com.artemis.annotations.All;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.compression.lzma.Base;
import net.mostlyoriginal.api.operation.JamOperationFactory;
import net.mostlyoriginal.api.operation.OperationFactory;
import net.mostlyoriginal.game.GameRules;
import net.mostlyoriginal.game.component.Pickup;
import net.mostlyoriginal.game.screen.GameScreen;
import net.mostlyoriginal.game.system.box2d.BoxContactListener;
import net.mostlyoriginal.game.system.box2d.BoxPhysicsSystem;
import net.mostlyoriginal.game.system.common.FluidSystem;

import static net.mostlyoriginal.api.utils.Duration.seconds;

/**
 * @author Daan van Yperen
 */
public class PickupSystem extends BaseSystem implements BoxContactListener {

    private int toBeDeleted = -1;
    private BoxPhysicsSystem boxPhysicsSystem;
    private TransitionSystem transitionSystem;

    @Override
    protected void initialize() {
        super.initialize();
        boxPhysicsSystem.register(this);
    }

    @Override
    protected void processSystem() {
        if ( toBeDeleted != -1 ) {
            E e = E.E(toBeDeleted);
            if (e.hasPickup() && e.pickupType() == Pickup.Type.EXIT) {
                E player = E.withTag("player");
                if ( !player.isInvisible()) {
                    transitionSystem.transition(GameScreen.class, seconds(0.9f));
                    E.E().playSound("ship-reached");
                    player.invisible();
                    float targetX = e.posX();
                    float targetY = e.posY();
                    float endY = e.posY() - 1000;
                    E.E().anim("escape_pod")
                            .pos(targetX, targetY)
                            .renderLayer(GameRules.LAYER_PLAYER+1).removeBoxed().script(
                            OperationFactory.sequence(
                                    JamOperationFactory.moveBetween(targetX, targetY, targetX, endY, seconds(0.8f), Interpolation.exp5In),
                                    OperationFactory.deleteFromWorld())
                    );
                }
            }
            e.deleteFromWorld();
            toBeDeleted=-1;
        }
    }

    Vector3 v2 = new Vector3();

    @Override
    public void beginContact(E a, E b) {
        if ( a.hasPickup() ) {
            if ( b.hasSharp() ) {
                if ( a.pickupType() != Pickup.Type.EXIT && a.pickupType() != Pickup.Type.BLINKER) {
                    toBeDeleted = a.id();
                }
                // pop sfx within visual range or user gets confuzzled
                if ( v2.set(a.posXy()).sub(b.posXy()).len() < GameRules.SCREEN_WIDTH/2) {
                    E.E().playSound("astronaut-pops");
                }
            } else if ( b.hasPlayer() && !b.hasDead() && !b.hasInvisible() ) {
                if ( a.pickupType() == Pickup.Type.BLINKER) {
                    if ( "orb_off".equals(a.animId())) {
                        a.anim("orb_on");
                        E.E().playSound("orb-on");
                    }
                }else {
                    toBeDeleted = a.id(); // also delete, but oxygen!
                    E.E().playSound(b.oxygenPercentage() > 100 ? "oxygen-recharge-2" : "oxygen-recharge-1");
                    b.oxygenIncrease();
                }
            }
        }
    }

    @Override
    public void endContact(E a, E b) {

    }
}