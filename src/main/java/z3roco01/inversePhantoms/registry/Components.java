package z3roco01.inversePhantoms.registry;

import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;
import z3roco01.inversePhantoms.InversePhantoms;

import java.util.function.UnaryOperator;

import static z3roco01.inversePhantoms.util.IdUtil.mkId;

public class Components {

    public static final Identifier SEQUENTIAL_SLEEPS = mkId("sequential_sleeps");

    public static void register() {
        register(SEQUENTIAL_SLEEPS, );
    }

    private static <T> ComponentType<T> register(Identifier id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
    }
}
