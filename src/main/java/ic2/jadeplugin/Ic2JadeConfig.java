package ic2.jadeplugin;

import net.minecraftforge.common.ForgeConfigSpec;

public class Ic2JadeConfig {

    public static ForgeConfigSpec SPEC;
    public static ForgeConfigSpec.IntValue updateRate;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        updateRate = builder.comment("Milliseconds to wait before updating HUD info from the server").defineInRange("updateRate", 250, 1, 100000);
        SPEC = builder.build();
    }
}
