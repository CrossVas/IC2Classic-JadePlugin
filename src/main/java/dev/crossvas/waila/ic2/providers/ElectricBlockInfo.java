package dev.crossvas.waila.ic2.providers;

import dev.crossvas.waila.ic2.base.WailaCommonHandler;
import dev.crossvas.waila.ic2.base.interfaces.IInfoProvider;
import dev.crossvas.waila.ic2.base.interfaces.IWailaHelper;
import dev.crossvas.waila.ic2.utils.ColorUtils;
import dev.crossvas.waila.ic2.utils.Formatter;
import dev.crossvas.waila.ic2.utils.TextFormatter;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import ic2.core.block.machine.tileentity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class ElectricBlockInfo implements IInfoProvider {

    public static final ElectricBlockInfo THIS = new ElectricBlockInfo();

    int lastScrap = 0;

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityElecMachine) {
            TileEntityElecMachine baseTile = (TileEntityElecMachine) blockEntity;
            if (baseTile instanceof TileEntityElectricEnchanter) {
                TileEntityElectricEnchanter enchanter = (TileEntityElectricEnchanter) baseTile;
                int storedXP = enchanter.exp;
                float progress = enchanter.getProgress();
                float maxProgress = 1f;
                if (storedXP <= 0) {
                    text(helper, translate(TextFormatter.RED, "probe.enchanter.missing"));
                } else {
                    bar(helper, storedXP, 1000, translate("probe.machine.xp", storedXP), ColorUtils.GREEN);
                }
                if (progress > 0) {
                    bar(helper, (int) progress, (int) maxProgress, translate("probe.progress.full.name", progress, maxProgress).appendText("t"), ColorUtils.PROGRESS);

                }
            }
            if (baseTile instanceof TileEntityMatter) {
                TileEntityMatter massFab = (TileEntityMatter) baseTile;
                int progress = massFab.energy;
                int maxProgress = 7000000;
                if (progress > 0) {
                    double finalProgress = (double) progress / 7000000 * 100.0;
                    if (finalProgress > 100) finalProgress = 100;
                    bar(helper, progress, maxProgress, translate("probe.speed.massFab",
                            Formatter.THERMAL_GEN.format(finalProgress)), -4441721);
                }
                RecipeOutput output = Recipes.matterAmplifier.getOutputFor(massFab.inventory[0], false);
                if (output != null) {
                    this.lastScrap = output.metadata.getInteger("amplification");
                }
                if (massFab.scrap > 0) {
                    bar(helper, massFab.scrap, lastScrap, translate("probe.matter.amplifier", massFab.scrap), -10996205);
                }
            }
        }
        if (blockEntity instanceof TileEntityCropmatron) {
            text(helper, tier(1));
            text(helper, maxIn(32));
            WailaCommonHandler.addTankInfo(helper, blockEntity);
        }
        if (blockEntity instanceof TileEntityMagnetizer) {
            text(helper, tier(1));
            text(helper, maxIn(32));
        }
        if (blockEntity instanceof TileEntityTesla) {
            text(helper, tier(2));
            text(helper, maxIn(128));
        }
    }
}
