package dev.crossvas.jadexic2c.providers;

import dev.crossvas.jadexic2c.base.interfaces.IInfoProvider;
import dev.crossvas.jadexic2c.base.interfaces.IJadeHelper;
import dev.crossvas.jadexic2c.utils.TextFormatter;
import ic2.api.classic.trading.providers.ITradeProvider;
import ic2.core.block.personal.base.TileEntityPersonalStorageBase;
import ic2.core.block.personal.base.misc.IOwnerBlock;
import ic2.core.block.personal.tile.TileEntityIridiumStone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class PersonalInfo implements IInfoProvider {

    public static final PersonalInfo THIS = new PersonalInfo();

    @Override
    public void addInfo(IJadeHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof IOwnerBlock) {
            IOwnerBlock personal = (IOwnerBlock) blockEntity;
            EntityPlayer owner = null;
            if (personal instanceof TileEntityPersonalStorageBase) {
                TileEntityPersonalStorageBase personalStorage = (TileEntityPersonalStorageBase) personal;
                owner = player.world.getPlayerEntityByUUID(personalStorage.owner);
                addOwnerInfo(helper, owner);
                text(helper, TextFormatter.LIGHT_PURPLE.translate("probe.personal.view", status(personalStorage.allowView)));
                text(helper, TextFormatter.LIGHT_PURPLE.translate("probe.personal.import", status(personalStorage.allowInjection)));
                text(helper, TextFormatter.LIGHT_PURPLE.translate("probe.personal.import", status(personalStorage.allowInjection)));
                text(helper, TextFormatter.LIGHT_PURPLE.translate("probe.personal.export", status(personalStorage.allowExtraction)));
                text(helper, TextFormatter.LIGHT_PURPLE.translate("probe.personal.team", status(personalStorage.allowTeam)));
            }
            if (personal instanceof TileEntityIridiumStone) {
                TileEntityIridiumStone personalStone = (TileEntityIridiumStone) personal;
                owner = player.world.getPlayerEntityByUUID(personalStone.owner);
                addOwnerInfo(helper, owner);
            }
            if (personal instanceof ITradeProvider) {
                ITradeProvider trader = (ITradeProvider) personal;
                owner = player.world.getPlayerEntityByUUID(trader.getOwner());
                addOwnerInfo(helper, owner);
            }
            if (owner != null) {
                text(helper, TextFormatter.AQUA.translate("probe.personal.access", status(owner == player)));
            }
        }
    }

    public void addOwnerInfo(IJadeHelper helper, EntityPlayer owner) {
        if (owner != null) {
            text(helper, TextFormatter.AQUA.translate("probe.personal.owner", TextFormatter.GREEN.component(owner.getDisplayName())));
        }
    }
}
