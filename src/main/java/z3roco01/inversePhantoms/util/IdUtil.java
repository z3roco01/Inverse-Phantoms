package z3roco01.inversePhantoms.util;

import net.minecraft.util.Identifier;

import static z3roco01.inversePhantoms.InversePhantoms.MOD_ID;

public class IdUtil {
    public static Identifier mkId(String id) {
        return new Identifier(MOD_ID, id);
    }
}
