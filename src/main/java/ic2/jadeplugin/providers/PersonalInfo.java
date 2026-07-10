package ic2.jadeplugin.providers;

import ic2.core.block.base.features.personal.IPersonalTile;
import ic2.core.block.base.tiles.impls.BasePersonalTileEntity;
import ic2.core.block.machines.tiles.mv.ChunkloaderTileEntity;
import ic2.jadeplugin.base.JadeHelper;
import ic2.jadeplugin.base.interfaces.IInfoProvider;
import ic2.jadeplugin.helpers.TextFormatter;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.UUID;

public class PersonalInfo implements IInfoProvider {

    public static final PersonalInfo THIS = new PersonalInfo();

    @Override
    public void addInfo(JadeHelper helper, BlockEntity blockEntity, Player player) {
        if (blockEntity instanceof IPersonalTile personal) {
            UUID ownerUUID = personal.getOwner();
            Player owner = player.level.getPlayerByUUID(ownerUUID);
            if (owner != null) {
                helper.text(TextFormatter.AQUA.translate("ic2.probe.personal.owner", owner.getDisplayName().copy().withStyle(ChatFormatting.GREEN)));
            }
            if (personal instanceof BasePersonalTileEntity basePersonal) {
                addAccessInfo(helper, basePersonal.mode);
            }
            if (personal instanceof ChunkloaderTileEntity chunkloader) {
                addAccessInfo(helper, chunkloader.mode);
            }
        }
    }

    public void addAccessInfo(JadeHelper helper, int mode) {
        Component[] modes = new Component[]{TextFormatter.WHITE.translate("gui.ic2.personal.mode.public"),
                TextFormatter.WHITE.translate("gui.ic2.personal.mode.protected"),
                TextFormatter.WHITE.translate("gui.ic2.personal.mode.private")};
        helper.text(TextFormatter.LIGHT_PURPLE.translate("gui.ic2.personal.mode", modes[mode].copy().withStyle(mode == 0 ? ChatFormatting.GREEN : mode == 1 ? ChatFormatting.GOLD : ChatFormatting.RED)));
    }
}
