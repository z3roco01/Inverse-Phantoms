package z3roco01.inversePhantoms;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public abstract class tmp extends AbstractMinecartEntity {
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (!spawnMonsters) {
            return 0;
        }
        if (!world.getGameRules().getBoolean(GameRules.DO_INSOMNIA)) {
            return 0;
        }
        Random random = world.random;
        --this.cooldown;
        if (this.cooldown > 0) {
            return 0;
        }
        this.cooldown += (60 + random.nextInt(60)) * 20;
        if (world.getAmbientDarkness() < 5 && world.getDimension().hasSkyLight()) {
            return 0;
        }
        int i = 0;
        for (ServerPlayerEntity serverPlayerEntity : world.getPlayers()) {
            FluidState fluidState;
            BlockState blockState;
            BlockPos blockPos2;
            LocalDifficulty localDifficulty;
            if (serverPlayerEntity.isSpectator()) continue;
            BlockPos blockPos = serverPlayerEntity.getBlockPos();
            if (world.getDimension().hasSkyLight() && (blockPos.getY() < world.getSeaLevel() || !world.isSkyVisible(blockPos)) || !(localDifficulty = world.getLocalDifficulty(blockPos)).isHarderThan(random.nextFloat() * 3.0f)) continue;
            ServerStatHandler serverStatHandler = serverPlayerEntity.getStatHandler();
            int timeSinceRest = MathHelper.clamp(serverStatHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
            int i24000 = 24000;
            if (random.nextInt(timeSinceRest) < 72000 || !SpawnHelper.isClearForSpawn(world, blockPos2 = blockPos.up(20 + random.nextInt(15)).east(-10 + random.nextInt(21)).south(-10 + random.nextInt(21)), blockState = world.getBlockState(blockPos2), fluidState = world.getFluidState(blockPos2), EntityType.PHANTOM)) continue;
            EntityData entityData = null;
            int l = 1 + random.nextInt(localDifficulty.getGlobalDifficulty().getId() + 1);
            for (int m = 0; m < l; ++m) {
                PhantomEntity phantomEntity = EntityType.PHANTOM.create(world);
                if (phantomEntity == null) continue;
                phantomEntity.refreshPositionAndAngles(blockPos2, 0.0f, 0.0f);
                entityData = phantomEntity.initialize(world, localDifficulty, SpawnReason.NATURAL, entityData, null);
                world.spawnEntityAndPassengers(phantomEntity);
                ++i;
            }
        }
        return i;
    }
}
