package net.amik.createarsenal.registrate;

import com.jozufozu.flywheel.core.PartialModel;
import net.amik.createarsenal.CreateArsenal;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public class ModPartials {
    public static final PartialModel
            SMALL_BARREL=item("small_gun_barrel"),
            MEDIUM_BARREL=item("medium_gun_barrel"),
            LARGE_BARREL=item("large_gun_barrel"),
            RADAR = block("radar_screen"),
            RADAR_LINE = block("radar_line"),
            HITBOX_1 = block("hitbox1"),
            HITBOX_2 = block("hitbox2"),
            CHAIN_GUN_BARREL = block("chain_gun_static_barrel"),
            FOUR_BARREL = block("four_barrel_static_barrel"),
            BIG_DISH = block("radar_multi"),
            EIGHT_BARREL = block("eight_barrel_static_barrel"),
            BARREL_MIDDLE_PIECE = block("barrel_middle_piece"),
            BARREL_END_PIECE = block("barrel_end_piece"),
            MEDIUM_ROUND = entity("medium_round");

    private static PartialModel block(String path) {
        return new PartialModel(new ResourceLocation(CreateArsenal.MOD_ID, "block/" + path));
    }

    private static PartialModel item(String path) {
        return new PartialModel(new ResourceLocation(CreateArsenal.MOD_ID, "item/" + path));
    }

    private static PartialModel entity(@SuppressWarnings("SameParameterValue") String path) {
        return new PartialModel(new ResourceLocation(CreateArsenal.MOD_ID, "entity/" + path));
    }
    public static void init() {
        // init static fields
    }

}
