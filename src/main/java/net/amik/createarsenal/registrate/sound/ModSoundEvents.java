package net.amik.createarsenal.registrate.sound;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.amik.createarsenal.CreateArsenal;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static com.simibubi.create.AllSoundEvents.SoundEntry;
import static com.simibubi.create.AllSoundEvents.SoundEntryBuilder;

public class ModSoundEvents {



    public static final Map<ResourceLocation, SoundEntry> ALL = new HashMap<>();

    public static final SoundEntry
            FIRE_MEDIUM_TURRET = create("fire_medium_turret").subtitle("Turret fires")
            .playExisting(SoundEvents.GENERIC_EXPLODE, 20.0f, 0.0f)
            .category(SoundSource.BLOCKS)
            .build();

    private static SoundEntryBuilder create(String id) {
        return new CreateArsenalSoundEntryBuilder(CreateArsenal.resource(id));
    }

    public static void prepare() {
        for (SoundEntry entry : ALL.values())
            entry.prepare();
    }

    public static void register(Consumer<SoundEntry> cons) {
        for (SoundEntry entry : ALL.values())
            cons.accept(entry);
    }

    public static void registerLangEntries() {
        for (SoundEntry entry : ALL.values())
            CreateArsenal.REGISTRATE.addRawLang(entry.getSubtitleKey(), entry.getSubtitle());
    }

    public static class CreateArsenalSoundEntryBuilder extends SoundEntryBuilder {
        public CreateArsenalSoundEntryBuilder(ResourceLocation id) {
            super(id);
        }

        @Override
        public SoundEntry build() {
            SoundEntry entry = super.build();
            ALL.put(entry.getId(), entry);
            return entry;
        }
    }

    public static SoundEntryProvider provider(DataGenerator generator) {
        return new SoundEntryProvider(generator);
    }

    private static class SoundEntryProvider implements DataProvider {
        private DataGenerator generator;

        public SoundEntryProvider(DataGenerator generator) {
            this.generator = generator;
        }

        @Override
        public void run(HashCache cache) throws IOException {
            generate(this.generator.getOutputFolder(), cache);
        }

        @Override
        public String getName() {
            return "Create Arsenal custom sounds";
        }

        public void generate(Path path, HashCache cache) {
            Gson GSON = (new GsonBuilder()).setPrettyPrinting()
                    .disableHtmlEscaping()
                    .create();
            path = path.resolve("assets/" + CreateArsenal.MOD_ID);

            try {
                JsonObject json = new JsonObject();
                ALL.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByKey())
                        .forEach(entry -> {
                            entry.getValue()
                                    .write(json);
                        });
                DataProvider.save(GSON, cache, json, path.resolve("sounds.json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
