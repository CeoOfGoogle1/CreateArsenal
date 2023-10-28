package net.amik.createarsenal.registrate;

import com.simibubi.create.content.fluids.VirtualFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.amik.createarsenal.CreateArsenal;

public class ModFluids {
    public static final FluidEntry<VirtualFluid> GUNPOWDER_FLUID = CreateArsenal.REGISTRATE.virtualFluid("gunpowder_fluid")
            .register();

    public static void init(){}
}


