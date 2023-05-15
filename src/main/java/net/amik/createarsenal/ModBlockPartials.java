package net.amik.createarsenal;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.Create;
import net.minecraft.resources.ResourceLocation;

public class ModBlockPartials {
    public static final PartialModel

    FOUR_BARREL = block("fourbarrel_static_barrel");

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
