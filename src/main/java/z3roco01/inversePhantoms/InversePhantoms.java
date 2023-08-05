package z3roco01.inversePhantoms;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import z3roco01.inversePhantoms.registry.Stats;

public class InversePhantoms implements ModInitializer {
    public static final String MOD_ID = "inverse_phantoms";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Starting init !");

        Stats.register();

        LOGGER.info("Finished init !");
    }
}
