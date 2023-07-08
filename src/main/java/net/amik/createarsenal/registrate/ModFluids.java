package net.amik.createarsenal.registrate;

import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.amik.createarsenal.CreateArsenal;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;

import java.util.function.Consumer;

import static net.amik.createarsenal.CreateArsenal.REGISTRATE;

public class ModFluids {
    public static final FluidEntry<VirtualFluid> GUNPOWDER_FLUID = CreateArsenal.REGISTRATE.virtualFluid("gunpowder_fluid")
            .tag(AllTags.forgeFluidTag("tea"))
            .register();

    public static void init(){}
}


