package net.amik.createarsenal.registrate;

import com.jozufozu.flywheel.core.PartialModel;
import net.amik.createarsenal.CreateArsenal;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public class ModPartials {
    public static final PartialModel
            RADAR = block("radar_screen"),
            RADAR_LINE = block("radar_line"),
            HITBOX_1 = block("hitbox1"),
            HITBOX_2 = block("hitbox2"),
            CHAIN_GUN_BARREL = block("chain_gun_static_barrel"),
            FOUR_BARREL = block("four_barrel_static_barrel"),
            BIG_DISH = block("radar_multi"),
            EIGHT_BARREL = block("eight_barrel_static_barrel"),
            MEDIUM_ROUND = entity("medium_round");

    private static PartialModel block(String path) {
        return new PartialModel(new ResourceLocation(CreateArsenal.MOD_ID, "block/" + path));
    }

    private static PartialModel entity(@SuppressWarnings("SameParameterValue") String path) {
        return new PartialModel(new ResourceLocation(CreateArsenal.MOD_ID, "entity/" + path));
    }
    public static void init() {
        // init static fields
    }

}
