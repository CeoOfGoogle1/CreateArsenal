package net.amik.createarsenal.block.monitor;

import net.amik.createarsenal.shell.BulletEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;

public class MonitorUtils {


    public static int BLUE=255;
    public static int WHITE=16777215;
    public static int RED=16711680;
    public static int GREEN=65280;
    public static int YELLOW=16776960;


    public static int getColor(Entity scannedEntity) {
        if(scannedEntity instanceof Player )
            return BLUE;

        if(scannedEntity instanceof BulletEntity)
            return YELLOW;

        if(scannedEntity instanceof Animal)
            return GREEN;

        if(scannedEntity instanceof Enemy)
            return RED;


        return WHITE;
    }
}
