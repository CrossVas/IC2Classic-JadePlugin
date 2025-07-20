package dev.crossvas.waila.ic2.providers;

import dev.crossvas.waila.ic2.base.interfaces.IInfoProvider;
import dev.crossvas.waila.ic2.base.interfaces.IWailaHelper;
import dev.crossvas.waila.ic2.utils.TextFormatter;
import ic2.core.block.personal.IPersonalBlock;
import ic2.core.block.personal.TileEntityPersonalChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public class PersonalInfo implements IInfoProvider {

    public static final PersonalInfo THIS = new PersonalInfo();

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof IPersonalBlock) {
            IPersonalBlock personalBlock = (IPersonalBlock) blockEntity;
            EntityPlayer owner = null;

            if (personalBlock instanceof TileEntityPersonalChest) {
                TileEntityPersonalChest personalStorage = (TileEntityPersonalChest) blockEntity;
                owner = getPlayerEntityByUUID(player.worldObj, personalStorage.owner);
                addOwnerInfo(helper, owner);
            }
            // TODO: Re-add these when `owner` field is available
//            if (personalBlock instanceof TileEntityTradeOMat) {
//                TileEntityTradeOMat tradeOMat = (TileEntityTradeOMat) blockEntity;
//                owner = getPlayerEntityByUUID(player.worldObj, (UUID) TileEntityTradeOMat.class.getDeclaredField("owner").get(tradeOMat));
//                addOwnerInfo(helper, owner);
//            }
//            if (personalBlock instanceof TileEntityEnergyOMat) {
//                TileEntityEnergyOMat tradeOMat = (TileEntityEnergyOMat) blockEntity;
//                owner = getPlayerEntityByUUID(player.worldObj, (UUID) TileEntityEnergyOMat.class.getDeclaredField("owner").get(tradeOMat));
//                addOwnerInfo(helper, owner);
//            }
//            if (personalBlock instanceof TileEntityFluidOMat) {
//                TileEntityFluidOMat tradeOMat = (TileEntityFluidOMat) blockEntity;
//                owner = getPlayerEntityByUUID(player.worldObj, (UUID) TileEntityFluidOMat.class.getDeclaredField("owner").get(tradeOMat));
//                addOwnerInfo(helper, owner);
//            }
//
            if (owner != null) {
                text(helper, translate(TextFormatter.AQUA, "probe.personal.view", status(personalBlock.canAccess(owner))));
            }
        }
    }

    public void addOwnerInfo(IWailaHelper helper, EntityPlayer owner) {
        if (owner != null) {
            text(helper, translate(TextFormatter.AQUA, "probe.personal.owner", literal(TextFormatter.GREEN, owner.getDisplayName())));
        }
    }

    @Nullable
    public EntityPlayer getPlayerEntityByUUID(World world, UUID uuid) {
        for (int i = 0; i < world.playerEntities.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer) world.playerEntities.get(i);
            if (uuid.equals(entityplayer.getUniqueID())) {
                return entityplayer;
            }
        }
        return null;
    }
}
