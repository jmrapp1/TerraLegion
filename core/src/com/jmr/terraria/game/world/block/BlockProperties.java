package com.jmr.terraria.game.world.block;

public class BlockProperties {

	public static final byte LIT_BY_LIGHT_SOURCE = 1;

	private float health;
	private byte flags;
	
	public BlockProperties() {
		this.health = 0;
		this.flags = 0;
	}
	
	public BlockProperties(float health, byte flags) {
		this.health = health;
		this.flags = flags;
	}
	
	public void setHealth(float health) {
		this.health = health;
	}
	
	public void addHealth(float f) {
		health += f;
	}
	
	public float getHealth() {
		return health;
	}
	
	public byte getFlags() {
		return flags;
	}
	
	public void setFlag(byte flag) {
		flags |= flag; //insert the bit into the flags
	}
	
	public boolean hasFlag(byte flag) {
		return (flags & flag) != 0; //Whether the flag is contained within the byte
	}
	
	public void removeFlag(byte flag) {
		flags &= ~flag; //Removes the byte flag from the flags
	}
	
	public void set(float health, byte flags) {
		this.health = health;
		this.flags = flags;
	}
	
}
