package z3roco01.inversePhantoms.mixin;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.spawner.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerMixin {
    @Shadow private int cooldown;

    @Inject(at = @At("HEAD"), method = "spawn", cancellable = true)
    public void spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> cir) {
        // Original dont spawn cases
        if(!spawnMonsters || !world.getGameRules().getBoolean(GameRules.DO_INSOMNIA)) {
            cir.setReturnValue(0);
            return;
        }

        --this.cooldown;
        if(this.cooldown > 0) {
            cir.setReturnValue(0);
            return;
        }

        Random random = world.random;
        this.cooldown += (60 + random.nextInt(60) * 20);
        if(world.getAmbientDarkness() < 5 && world.getDimension().hasSkyLight()) {
            cir.setReturnValue(0);
            return;
        }

        int spawned = 0;
        for(ServerPlayerEntity player : world.getPlayers()) {
            if(player.isSpectator()) continue;

            LocalDifficulty localDifficulty;
            BlockPos playerPos = player.getBlockPos();
            if (world.getDimension().hasSkyLight() && (playerPos.getY() < world.getSeaLevel() || !world.isSkyVisible(playerPos)) || !(localDifficulty = world.getLocalDifficulty(playerPos)).isHarderThan(random.nextFloat() * 3.0f)) continue;

            int sequentialSleeps = player.getStatHandler().getStat(net.minecraft.stat.Stats.CUSTOM.getOrCreateStat(Stats.SEQUENTIAL_SLEEPS));
            int toSpawn = sequentialSleeps - 2;

            BlockPos spawnPos;
            if (!SpawnHelper.isClearForSpawn(world, spawnPos = playerPos.up(20 + random.nextInt(15)).east(-10 + random.nextInt(21)).south(-10 + random.nextInt(21)), world.getBlockState(spawnPos), world.getFluidState(spawnPos), EntityType.PHANTOM)) continue;

            EntityData entityData = null;
            for(int i = 0; i < toSpawn; ++i) {
                PhantomEntity phantomEntity = EntityType.PHANTOM.create(world);
                if (phantomEntity == null) continue;
                phantomEntity.refreshPositionAndAngles(spawnPos, 0.0f, 0.0f);
                entityData = phantomEntity.initialize(world, localDifficulty, SpawnReason.NATURAL, entityData, null);
                world.spawnEntityAndPassengers(phantomEntity);
                ++spawned;
            }
        }
        cir.setReturnValue(spawned);
    }
}
