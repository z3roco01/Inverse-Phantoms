package z3roco01.inversePhantoms.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z3roco01.inversePhantoms.InversePhantoms;
import z3roco01.inversePhantoms.SequentialSleepTracker;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity implements SequentialSleepTracker {
    @Unique public long sequentialSleeps = 0;

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putLong(InversePhantoms.SEQUENTIAL_SLEEPS_KEY, this.sequentialSleeps);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.sequentialSleeps = nbt.getLong(InversePhantoms.SEQUENTIAL_SLEEPS_KEY);
    }

    @Override
    public void setSequentialSleeps(long sequentialSleeps) {
        this.sequentialSleeps = sequentialSleeps;
    }

    @Override
    public long getSequentialSleeps() {
        return sequentialSleeps;
    }

    @Override
    public void incSequentialSleeps() {
        this.incSequentialSleeps(1);
    }

    @Override
    public void incSequentialSleeps(long amount) {
        this.sequentialSleeps += amount;
    }
}
