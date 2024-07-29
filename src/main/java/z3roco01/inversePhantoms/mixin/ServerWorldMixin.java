package z3roco01.inversePhantoms.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Shadow @Final private List<ServerPlayerEntity> players;
    @Shadow @Final private ServerWorldProperties worldProperties;
    private boolean slept = false;

    @Inject(at = @At("HEAD"), method = "wakeSleepingPlayers", cancellable = false)
    private void wakeSleepingPlayers(CallbackInfo ci) {
        for(ServerPlayerEntity player : this.players) {
            if(player.isSleeping()) player.incrementStat(Stats.SEQUENTIAL_SLEEPS);
            else player.resetStat(net.minecraft.stat.Stats.CUSTOM.getOrCreateStat(Stats.SEQUENTIAL_SLEEPS));
        }
        slept = true;
    }

    @Inject(at = @At("HEAD"), method = "tick", cancellable = false)
    public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        long timeOfDay = worldProperties.getTimeOfDay();
        if((timeOfDay % 24000) == 0 && !slept) { // Sun has risen, the new day has started and we need to reset sequential sleeps for everyone since noone slept
            for(ServerPlayerEntity player : this.players) player.resetStat(net.minecraft.stat.Stats.CUSTOM.getOrCreateStat(Stats.SEQUENTIAL_SLEEPS));
        }

        slept = false;
    }
}
