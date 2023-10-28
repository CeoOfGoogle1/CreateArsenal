package net.amik.createarsenal.registrate;

import com.simibubi.create.content.fluids.VirtualFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.amik.createarsenal.CreateArsenal;

import static net.amik.createarsenal.CreateArsenal.resource;

public class ModFluids {
    public static final FluidEntry<VirtualFluid> GUNPOWDER_FLUID = CreateArsenal.REGISTRATE
            .virtualFluid("gunpowder_fluid", resource("fluid/gunpowder_fluid"), resource("fluid/gunpowder_fluid"))
            .lang("Gun Powder Fluid")
            .register();

    public static void init() {
    }
}


