package net.mostlyoriginal.game.manager;

import com.artemis.BaseSystem;
import com.artemis.E;
import com.artemis.EntitySubscription;
import com.artemis.annotations.All;
import com.artemis.annotations.Exclude;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;
import net.mostlyoriginal.game.component.Item;
import net.mostlyoriginal.game.component.ItemData;
import net.mostlyoriginal.game.component.Lifted;
import net.mostlyoriginal.game.system.ItemLibrary;

/**
 * @author Daan van Yperen
 */
public class ItemRepository extends BaseSystem {

    public ItemLibrary itemLibrary;
    private int rewardDropTotal = 0;
    private int desireDropTotal = 0;

    @All({Item.class})
    @Exclude(Lifted.class)
    private EntitySubscription items;

    @Override
    protected void initialize() {
        super.initialize();
        final Json json = new Json();
        itemLibrary = json.fromJson(ItemLibrary.class, Gdx.files.internal("items.json"));
        for (ItemData item : itemLibrary.items) {
            rewardDropTotal += item.rewardChance;
            desireDropTotal += item.desireChance;
        }
    }

    @Override
    protected void processSystem() {
    }

    public ItemData get(String type) {
        ItemData byId = itemLibrary.getById(type);
        if (byId == null) throw new RuntimeException("unknown item type " + type);
        return byId;
    }

    public String randomReward() {
        boolean valid = false;

        int target = MathUtils.random(0, rewardDropTotal - 1);
        for (ItemData item : itemLibrary.items) {
            target -= item.rewardChance;
            if (target <= 0) return item.id;
        }
        return null;
    }

    public String availableCovetedItemType() {
        int[] data = items.getEntities().getData();
        for (int i = 0, s = items.getEntities().size(); i < s; i++) {
            String type = E.E(data[i]).itemType();
            if (itemLibrary.getById(type).coveted) {
                return type;
            }
        }
        return null;
    }

    public String randomDesire() {
        if (MathUtils.random(1, 100) < 30) {
            // fairly high chance people want a valuable item, if the player has it.
            String covetedItemType = availableCovetedItemType();
            if (covetedItemType != null) return covetedItemType;
        }

        int target = MathUtils.random(0, desireDropTotal - 1);
        for (ItemData item : itemLibrary.items) {
            target -= item.desireChance;
            if (target <= 0) return item.id;
        }
        return null;
    }

    public String substitute(String ingredient) {
        String identicalTo = get(ingredient).identicalTo;
        return identicalTo != null ? identicalTo : ingredient;
    }
}

