package z3roco01.inversePhantoms;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InversePhantoms implements ModInitializer {
    public static final String MOD_ID = "inverse_phantoms";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final String SEQUENTIAL_SLEEPS_KEY = "sequential_sleeps";

    @Override
    public void onInitialize() {
        LOGGER.info("init !!");
    }
}
