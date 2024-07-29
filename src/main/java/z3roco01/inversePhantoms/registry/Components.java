package z3roco01.inversePhantoms.registry;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;

import static z3roco01.inversePhantoms.util.IdUtil.mkId;

public class Components {

    public static final Identifier SEQUENTIAL_SLEEPS = mkId("sequential_sleeps");

    public static void register() {
        register(SEQUENTIAL_SLEEPS);
    }

    private static void register(Identifier stat) {
        Registry.register(Registries.CUSTOM_STAT, "sequential_sleeps", stat);
        net.minecraft.stat.Stats.CUSTOM.getOrCreateStat(stat, StatFormatter.DEFAULT);
    }
}
