package com.danu.better_mounts.mixin.client;

import com.danu.better_mounts.BetterMountsClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.DonkeyRenderer;
import net.minecraft.client.renderer.entity.state.DonkeyRenderState;
import net.minecraft.world.entity.animal.equine.AbstractChestedHorse;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DonkeyRenderer.class)
public abstract class DonkeyRendererMixin<T extends AbstractChestedHorse, S extends DonkeyRenderState> {
    @Inject(
            method = "extractRenderState",
            at = @At("TAIL")
    )
    private void afterExtractRenderState(
            T donkey,
            S donkeyRenderState,
            float f,
            CallbackInfo ci
    ) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;
        if (client.player.getVehicle() == null) return;
        if( donkey == client.player.getVehicle()){
            if (client.player.getXRot() > BetterMountsClient.DONKEY_ANGLE_THRESHOLD) {
                donkeyRenderState.saddle = ItemStack.EMPTY;
                donkeyRenderState.hasChest = false;
            }
        }
    }
}

