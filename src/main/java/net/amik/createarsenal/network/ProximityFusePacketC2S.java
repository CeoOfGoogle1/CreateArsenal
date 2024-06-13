package net.amik.createarsenal.network;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class ProximityFusePacketC2S extends SimplePacketBase {
    private final int detonationRadius;

    public ProximityFusePacketC2S(FriendlyByteBuf buffer) {
        detonationRadius = buffer.readInt();
    }

    public ProximityFusePacketC2S(int detonationRadius) {
        this.detonationRadius = detonationRadius;
    }


    @Override
    public void write(FriendlyByteBuf buffer) {
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
            tag.putInt("proximityRange", detonationRadius);
        });
        return true;
    }
}
