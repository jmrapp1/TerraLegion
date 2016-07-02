package com.jmr.terraria.game.world.block;

import com.badlogic.gdx.utils.Array;

public class BlockPropertiesFactory {

	public static final BlockPropertiesFactory instance = new BlockPropertiesFactory();
	private Array<BlockProperties> blockProperties = new Array<BlockProperties>();
	
	private BlockPropertiesFactory() {
	}
	
	public BlockProperties getBlockProperties(float health, byte flags) {
		if (blockProperties.size > 0) {
			BlockProperties properties = blockProperties.removeIndex(0);
			properties.set(health, flags);
			return properties;
		}
		return new BlockProperties(health, flags);
	}
	
	public BlockProperties getBlockProperties() {
		if (blockProperties.size > 0) {
			return blockProperties.removeIndex(0);
		}
		return new BlockProperties();
	}
	
	public void destroy(BlockProperties body) {

		blockProperties.add(body);
	}
	
}
