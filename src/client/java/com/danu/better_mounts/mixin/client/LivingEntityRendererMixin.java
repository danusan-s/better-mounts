package com.danu.better_mounts.mixin.client;

import com.danu.better_mounts.BetterMountsClient;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.equine.Donkey;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.entity.monster.Strider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState> {
    private static final Logger LOGGER = LoggerFactory.getLogger("BetterMounts");

    @Unique
    private boolean shouldHideMount = false;

    @Inject(
            method = "extractRenderState",
            at = @At("TAIL")
    )
    private void afterExtractRenderState(
            T livingEntity,
            S livingEntityRenderState,
            float f,
            CallbackInfo ci
    ) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;
        if (client.player.getVehicle() == null) return;
        if (livingEntity == client.player.getVehicle()) {
            float angle = client.player.getXRot();
            float threshold = this.getThreshold(livingEntity);
            this.shouldHideMount = angle > threshold;
            if (this.shouldHideMount) {
                livingEntityRenderState.isInvisible = true;
            }
        }
    }

    @Inject(
            method = "getRenderType",
            at = @At("RETURN"),
            cancellable = true
    )
    private void onGetIdentifier(S state, boolean bl, boolean bl2, boolean bl3, CallbackInfoReturnable<RenderType> cir, @Local Identifier identifier){
        if (this.shouldHideMount) {
            cir.setReturnValue(RenderTypes.entityTranslucentEmissive(identifier));
        }
    }

    @Unique
    private float getThreshold(T entity) {
        if (entity instanceof Horse) return  BetterMountsClient.HORSE_ANGLE_THRESHOLD;
        if (entity instanceof Donkey) return  BetterMountsClient.DONKEY_ANGLE_THRESHOLD;
        if (entity instanceof Strider) return  BetterMountsClient.STRIDER_ANGLE_THRESHOLD;
        return 90.0f;
    }
}

