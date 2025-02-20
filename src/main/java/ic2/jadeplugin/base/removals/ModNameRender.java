package ic2.jadeplugin.base.removals;

import ic2.core.block.base.IToolProvider;
import ic2.jadeplugin.IC2JadePlugin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.Jade;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.util.ModIdentification;

public class ModNameRender {

    public static final ResourceLocation REMOVER = IC2JadePlugin.rl("remove_modid");
    public static final ResourceLocation RELOCATE = IC2JadePlugin.rl("relocate_modid");

    public static final ModNameRelocator MOD_NAME_REMOVER = new ModNameRelocator(REMOVER, TooltipPosition.TAIL);
    public static final ModNameRelocator MOD_NAME_RELOCATOR = new ModNameRelocator(RELOCATE, TooltipPosition.HEAD);


    public static class ModNameRelocator implements IBlockComponentProvider {

        ResourceLocation ID;
        int PRIORITY;

        public ModNameRelocator(ResourceLocation id, int priority) {
            this.ID = id;
            this.PRIORITY = priority;
        }

        @Override
        public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
            // Dirty check for IC2Classic addons, assuming they are using instances if IC2Block for their blocks
            // Checking for IToolProvider because I can't seem to be able to check for abstract class IC2Block directly
            if (blockAccessor.getBlock() instanceof IToolProvider) {
                String MOD_NAME = ModIdentification.getModName(blockAccessor.getBlock());
                if (ModIdentification.getModName(blockAccessor.getBlock()).equals(MOD_NAME)) {
                    if (getUid() == REMOVER) {
                        iTooltip.remove(Identifiers.CORE_MOD_NAME);
                    } else if (getUid() == RELOCATE) {
                        String modName = String.format(Jade.CONFIG.get().getFormatting().getModName(), MOD_NAME);
                        iTooltip.add(Component.literal(modName));
                    }
                }
            }
        }

        @Override
        public ResourceLocation getUid() {
            return this.ID;
        }

        @Override
        public int getDefaultPriority() {
            return this.PRIORITY;
        }
    }
}
