package com.possible_triangle.packwiz_compatibility_check.forge.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.possible_triangle.packwiz_compatibility_check.VersionResolver;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @ModifyReturnValue(
            method = "buildServerStatus",
            at = @At("RETURN")
    )
    private ServerStatus modifyServerVersion(ServerStatus original) {
        return new ServerStatus(
                original.description(),
                original.players(),
                VersionResolver.get(),
                original.favicon(),
                original.enforcesSecureChat(),
                Optional.empty()
        );
    }

}
