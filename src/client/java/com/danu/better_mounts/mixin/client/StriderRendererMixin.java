package com.danu.better_mounts.mixin.client;

import com.danu.better_mounts.BetterMountsClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.StriderRenderer;
import net.minecraft.client.renderer.entity.state.StriderRenderState;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StriderRenderer.class)
public class StriderRendererMixin<T extends Strider, S extends StriderRenderState> {
    @Inject(
            method = "extractRenderState",
            at = @At("TAIL")
    )
    private void afterExtractRenderState(
            T strider,
            S striderRenderState,
            float f,
            CallbackInfo ci
    ) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;
        if (client.player.getVehicle() == null) return;
        if( strider == client.player.getVehicle()){
            if (client.player.getXRot() > BetterMountsClient.STRIDER_ANGLE_THRESHOLD) {
                striderRenderState.saddle = ItemStack.EMPTY;
            }
        }
    }
}
