package net.amik.createarsenal.compat.cbc;

import net.amik.createarsenal.CreateArsenal;
import net.amik.createarsenal.registrate.ModDisplayBehaviors;
import rbasamoyai.createbigcannons.index.CBCBlockEntities;

public class CBC {
    public static void registerDisplayBehaviors() {
        CreateArsenal.LOGGER.info("Registering CBC Turret Guidance behaviors");
        ModDisplayBehaviors.register("cannon_mount", new TurretGuidanceBehavior(), CBCBlockEntities.CANNON_MOUNT.get());
    }
}
