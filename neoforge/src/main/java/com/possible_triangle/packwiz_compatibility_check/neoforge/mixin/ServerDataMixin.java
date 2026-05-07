package com.possible_triangle.packwiz_compatibility_check.neoforge.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.possible_triangle.packwiz_compatibility_check.VersionResolver;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.network.protocol.status.ServerStatus;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerData.class)
public class ServerDataMixin {

    @WrapMethod(method = "setState")
    private void replaceCompatibleLogic(ServerData.State state, Operation<Void> original) {
        var self = (ServerData) (Object) this;

        if (state == ServerData.State.SUCCESSFUL || state == ServerData.State.INCOMPATIBLE) {
            if (self.version.getContents() instanceof PlainTextContents literal) {
                var server = literal.text();
                var client = VersionResolver.get().map(ServerStatus.Version::name);

                var compatible = client.filter(it -> it.equals(server)).isPresent();
                state = compatible ? ServerData.State.SUCCESSFUL : ServerData.State.INCOMPATIBLE;
            }
        }

        original.call(state);
    }

}
