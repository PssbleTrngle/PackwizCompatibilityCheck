package com.possible_triangle.packwiz_compatibility_check.forge.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.possible_triangle.packwiz_compatibility_check.VersionResolver;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.network.protocol.status.ServerStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerSelectionList.OnlineServerEntry.class)
public class OnlineServerEntryMixin {

    @ModifyReturnValue(
            method = "isCompatible",
            at = @At("RETURN")
    )
    private boolean replaceCompatibleLogic(boolean original) {
        var self = (ServerSelectionList.OnlineServerEntry) (Object) this;

        if (!(self.getServerData().version.getContents() instanceof LiteralContents literal)) return false;

        var server = literal.text();
        var client = VersionResolver.get().map(ServerStatus.Version::name);

        return client.filter(it -> it.equals(server)).isPresent();
    }

    @WrapOperation(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/ForgeHooksClient;drawForgePingInfo(Lnet/minecraft/client/gui/screens/multiplayer/JoinMultiplayerScreen;Lnet/minecraft/client/multiplayer/ServerData;Lnet/minecraft/client/gui/GuiGraphics;IIIII)V")
    )
    private void disableForgeRendering(JoinMultiplayerScreen gui, ServerData target, GuiGraphics guiGraphics, int x, int y, int width, int relativeMouseX, int relativeMouseY, Operation<Void> original) {
        // Do nothing
    }

    @WrapOperation(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)I")
    )
    private int disableVersionRendering(GuiGraphics instance, Font $$0, Component $$1, int $$2, int $$3, int $$4, boolean $$5, Operation<Integer> original) {
        // Do nothing
        return 0;
    }

}
