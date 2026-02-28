package com.danu.better_mounts.mixin.client;

import com.danu.better_mounts.BetterMountsClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseRenderer.class)
public abstract class HorseRendererMixin<T extends Horse, S extends HorseRenderState> {

  @Inject(
          method = "extractRenderState",
          at = @At("TAIL")
  )
  private void afterExtractRenderState(
          T horse,
          S horseRenderState,
          float f,
          CallbackInfo ci
  ) {
    Minecraft client = Minecraft.getInstance();
    if (client.player == null) return;
    if (client.player.getVehicle() == null) return;
    if( horse == client.player.getVehicle()){
      if (client.player.getXRot() > BetterMountsClient.HORSE_ANGLE_THRESHOLD) {
        horseRenderState.bodyArmorItem = ItemStack.EMPTY;
        horseRenderState.saddle = ItemStack.EMPTY;
      }
    }
  }
}

