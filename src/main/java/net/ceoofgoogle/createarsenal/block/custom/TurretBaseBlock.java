package net.ceoofgoogle.createarsenal.block.custom;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.base.DirectionalKineticBlock;
import com.simibubi.create.content.contraptions.base.KineticBlock;
import com.simibubi.create.foundation.block.ITE;
import com.simibubi.create.foundation.gui.ScreenOpener;
import io.netty.handler.codec.mqtt.MqttProperties;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TurretBaseBlock extends DirectionalKineticBlock implements ITE {

    public TurretBaseBlock(Properties properties) {
        super(properties);

    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return null;
    }

    @Override
    public Class getTileEntityClass() {
        return null;
    }

    @Override
    public BlockEntityType getTileEntityType() {
        return null;
    }
}

