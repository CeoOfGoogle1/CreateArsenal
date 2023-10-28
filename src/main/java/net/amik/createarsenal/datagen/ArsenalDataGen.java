package net.amik.createarsenal.datagen;

import com.simibubi.create.infrastructure.data.CreateRegistrateTags;
import com.tterrag.registrate.providers.ProviderType;
import net.amik.createarsenal.CreateArsenal;
import net.amik.createarsenal.registrate.ModSoundEvents;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import java.util.function.BiConsumer;

public class ArsenalDataGen {
    public static void gatherData(GatherDataEvent event) {
        addExtraRegistrateData();

        DataGenerator generator = event.getGenerator();

        if (event.includeClient()) {
            generator.addProvider(ModSoundEvents.provider(generator));
        }
    }


    private static void addExtraRegistrateData() {
        CreateRegistrateTags.addGenerators();

        CreateArsenal.REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add;
            ModSoundEvents.provideLang(langConsumer);
        });
    }
}
