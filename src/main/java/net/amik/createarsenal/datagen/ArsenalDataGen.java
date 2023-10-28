package net.amik.createarsenal.datagen;

import com.tterrag.registrate.providers.ProviderType;
import net.amik.createarsenal.CreateArsenal;
import net.amik.createarsenal.registrate.ModSoundEvents;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.function.BiConsumer;

public class ArsenalDataGen {
    public static void gatherData(GatherDataEvent event) {
        addExtraRegistrateData();

        DataGenerator generator = event.getGenerator();

        if (event.includeClient()) {
            generator.addProvider(true, ModSoundEvents.provider(generator));
        }
    }


    private static void addExtraRegistrateData() {

        CreateArsenal.REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add;
            ModSoundEvents.provideLang(langConsumer);
        });
    }
}
