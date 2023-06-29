package net.amik.createarsenal.registrate;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.Create;
import net.amik.createarsenal.CreateArsenal;
import net.minecraft.resources.ResourceLocation;

public class ModBlockPartials {
    public static final PartialModel
            RADAR = block("radar_screen"),
            RADAR_LINE = block("radar_line"),
            HITBOX_1 = block("hitbox1"),
            HITBOX_2 = block("hitbox2"),
            FOUR_BARREL = block("fourbarrel_static_barrel"),
            EIGHT_BARREL = block("fourbarrel_static_barrel");

    private static PartialModel block(String path) {
        return new PartialModel(new ResourceLocation(CreateArsenal.MOD_ID, "block/" + path));
    }

    private static PartialModel entity(String path) {
        return new PartialModel(new ResourceLocation(CreateArsenal.MOD_ID, "entity/" + path));
    }
    public static void init() {
        // init static fields
    }

}
