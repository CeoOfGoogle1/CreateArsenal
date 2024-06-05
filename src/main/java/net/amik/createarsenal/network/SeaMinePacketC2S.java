package net.amik.createarsenal.network;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class SeaMinePacketC2S extends SimplePacketBase {
    private final int floatLevel;
    private final int detonationRadius;

    public SeaMinePacketC2S(FriendlyByteBuf buffer) {
        floatLevel = buffer.readInt();
        detonationRadius = buffer.readInt();
    }

    public SeaMinePacketC2S(int floatLevel, int detonationRadius) {
        this.floatLevel = floatLevel;
        this.detonationRadius = detonationRadius;
    }


    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeInt(floatLevel);
        buffer.writeInt(detonationRadius);
    }

    @Override
    public boolean handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            Player player = context.getSender();
            if (player == null)
                return;
            ItemStack stack = player.getMainHandItem();
            CompoundTag tag = stack.getOrCreateTag();
            tag.putInt("floatLevel", floatLevel);
            tag.putInt("detonationRadius", detonationRadius);
        });
        return true;
    }
}
