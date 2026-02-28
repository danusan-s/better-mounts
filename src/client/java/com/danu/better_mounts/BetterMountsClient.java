package com.danu.better_mounts;

import net.fabricmc.api.ClientModInitializer;

public class BetterMountsClient implements ClientModInitializer {
	public static final float HORSE_ANGLE_THRESHOLD = 30.0F;
	public static final float DONKEY_ANGLE_THRESHOLD = 30.0F;
	public static final float STRIDER_ANGLE_THRESHOLD = 70.0F;

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}