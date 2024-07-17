package net.amik.createarsenal.registrate;

import net.amik.createarsenal.CreateArsenal;

public class ModTranslations {
    public static void register() {

        add("sea_mine.float_level",
                "Float Level");

        add("sea_mine.range",
                "Proximity Range");

        add("sea_mine.armed",
                "Armed");

        add("seamine_gui.title",
                "Sea Mine");

        add("clusterbomb_gui.title",
                "Cluster Bomb");

        add("proximityfuse_gui.title",
                "Proximity Fuse");

        add("proximityfuse.range",
                "Proximity Range");

        add("clusterbomb.altitude",
                "Altitude");

        add("createarsenal.display_source.cannon_mount",
                "Cannon Mount");

        add("createarsenal.display_source.radar",
                "radar");
    }

    public static void add(String key, String value) {
        CreateArsenal.REGISTRATE.addRawLang(key, value);
    }
}
