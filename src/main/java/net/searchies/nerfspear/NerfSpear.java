package net.searchies.nerfspear;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NerfSpear implements ModInitializer {
	public static final String MOD_ID = "nerfspear";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Nerf Spear Mod");
	}
}