package net.amik.createarsenal;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.Create;

public class ModBlockPartials {
    public static final PartialModel

    FOUR_BARREL = block("four_barrel_static_turret/four_barrel");

    private static PartialModel block(String path) {
        return new PartialModel(Create.asResource("block/" + path));
    }

    private static PartialModel entity(String path) {
        return new PartialModel(Create.asResource("entity/" + path));
    }

    public static void init() {
        // init static fields
    }

}
