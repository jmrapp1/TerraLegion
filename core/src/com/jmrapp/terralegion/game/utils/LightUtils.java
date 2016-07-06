package com.jmrapp.terralegion.game.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.badlogic.gdx.utils.Array;
import com.jmrapp.terralegion.game.world.block.BlockManager;
import com.jmrapp.terralegion.game.world.block.BlockProperties;
import com.jmrapp.terralegion.game.world.chunk.Chunk;
import com.jmrapp.terralegion.game.world.chunk.ChunkManager;

public class LightUtils {

	/** The darkest a tile can be */
	public static float MIN_LIGHT_VALUE = 0f, FLOAT_LOSS_PRECISION = 0.001f;
	private static final HashMap<String, QueuedLight> lightQueue = new HashMap<String, QueuedLight>();
	private static final Array<QueuedLight> recycledLights = new Array<QueuedLight>();
	
	public static void calculateChunkLight(ChunkManager manager, int lightX, int lightY, float lightValue, boolean lightSource) {
		if (lightValue > MIN_LIGHT_VALUE) {
			applyLight(manager, lightX, lightY, lightValue, lightSource);
		}
	}

	private static void applyLight(ChunkManager manager, int currentX, int currentY, float lightValue, boolean lightSource) {
		if (currentX < 0 || currentX >= (ChunkManager.CHUNKS_X * Chunk.CHUNK_SIZE) || currentY < 0 || currentY >= (ChunkManager.CHUNKS_Y * Chunk.CHUNK_SIZE)) {
			return;
		}
		Chunk posChunk = manager.getChunkFromTilePos(currentX, currentY);
		
		int posChunkX = currentX - (posChunk.getStartX() * Chunk.CHUNK_SIZE);
		int posChunkY = currentY - (posChunk.getStartY() * Chunk.CHUNK_SIZE);
		
		lightValue -= BlockManager.getBlock(posChunk.getBlock(posChunkX, posChunkY)).getLightBlockingAmount();

	    if (lightValue <= posChunk.getLightValue(posChunkX, posChunkY))
	    	return;
	    
	    posChunk.setLightValue(lightValue, posChunkX, posChunkY);
		if (lightSource)
	        posChunk.getBlockProperties(posChunkX, posChunkY).setFlag(BlockProperties.LIT_BY_LIGHT_SOURCE);

	    if (lightValue <= LightUtils.MIN_LIGHT_VALUE)
	    	return;
	   
	    applyLight(manager, currentX + 1, currentY, lightValue, lightSource);
	    applyLight(manager, currentX, currentY + 1, lightValue, lightSource);
	    applyLight(manager, currentX - 1, currentY, lightValue, lightSource);
	    applyLight(manager, currentX, currentY - 1, lightValue, lightSource);
	}
	
	public static void calculateChunkLightDown(ChunkManager manager, int lightX, int lightY, float lightValue) {
		if (lightValue > MIN_LIGHT_VALUE) {
			applyLightDown(manager, lightX, lightY, lightValue);
		}
	}
	
	/** Applies light only downwards and doesn't extend out. Used for the lighting from the sun.
	 * 
	 * @param manager The chunk manager for the world
	 * @param currentX The current tile X we're calculating
	 * @param currentY The current tile Y we're calculating
	 * @param lightValue The decreasing lightValue to set
	 */
	private static void applyLightDown(ChunkManager manager, int currentX, int currentY, float lightValue) {
		if (currentX < 0 || currentX >= (ChunkManager.CHUNKS_X * Chunk.CHUNK_SIZE) || currentY < 0 || currentY >= (ChunkManager.CHUNKS_Y * Chunk.CHUNK_SIZE)) {
			return;
		}
		Chunk posChunk = manager.getChunkFromTilePos(currentX, currentY);
		
		int posChunkX = currentX - (posChunk.getStartX() * Chunk.CHUNK_SIZE);
		int posChunkY = currentY - (posChunk.getStartY() * Chunk.CHUNK_SIZE);
		
		lightValue -= BlockManager.getBlock(posChunk.getBlock(posChunkX, posChunkY)).getLightBlockingAmount();
	    
	    float totalLight = lightValue;
	    
	    if (totalLight <= posChunk.getLightValue(posChunkX, posChunkY))
	    	return;
	    
	    posChunk.setLightValue(totalLight, posChunkX, posChunkY);
	    
	    if (lightValue <= LightUtils.MIN_LIGHT_VALUE)
	    	return;
	   
	    applyLightDown(manager, currentX, currentY - 1, lightValue);
	}

	/** Reverses the process of lighting the world and removes the light that surround the selected tile.
	 *
	 * @param manager The chunk manager instance
	 * @param lightX The light X position
	 * @param lightY The light Y position
	 * @param lightValue The light value to start extinguishing at
	 */
	public static void extinguishLight(ChunkManager manager, int lightX, int lightY, float lightValue, boolean lightSource) {
		if (lightValue > MIN_LIGHT_VALUE) {
			applyExtinguishingLight(manager, lightX, lightY, lightValue, false);
			Iterator<Entry<String, QueuedLight>> it = lightQueue.entrySet().iterator();
			while (it.hasNext()) {
				QueuedLight light = it.next().getValue();
				float currentLight = light.getChunk().getLightValue(light.getX(), light.getY());
				if (currentLight > MIN_LIGHT_VALUE) { //make sure another iteration didnt change the light value after it was added
					float lightBlockage = BlockManager.getBlock(light.getChunk().getBlock(light.getX(), light.getY())).getLightBlockingAmount();
					light.getChunk().setLightValue(MIN_LIGHT_VALUE, light.getX(), light.getY());
					applyLight(manager, light.getX() + (light.getChunk().getStartX() * Chunk.CHUNK_SIZE), light.getY() + (light.getChunk().getStartY() * Chunk.CHUNK_SIZE), currentLight + lightBlockage, lightSource);
				}
				recycledLights.add(light);
				it.remove();
			}
		}
	}

	public static void extinguishLightAmount(ChunkManager manager, int lightX, int lightY, float originalLightValue, float subtractedValue, boolean lightSource) {
		if (originalLightValue > MIN_LIGHT_VALUE) {
			applyExtinguishingLightAmount(manager, lightX, lightY, originalLightValue, subtractedValue, false);
			Iterator<Entry<String, QueuedLight>> it = lightQueue.entrySet().iterator();
			while (it.hasNext()) {
				QueuedLight light = it.next().getValue();
				float currentLight = light.getChunk().getLightValue(light.getX(), light.getY());
				if (currentLight > MIN_LIGHT_VALUE) { //make sure another iteration didnt change the light value after it was added
					float lightBlockage = BlockManager.getBlock(light.getChunk().getBlock(light.getX(), light.getY())).getLightBlockingAmount();
					//light.getChunk().setLightValue(MIN_LIGHT_VALUE, light.getX(), light.getY());
					//applyLight(manager, light.getX() + (light.getChunk().getStartX() * Chunk.CHUNK_SIZE), light.getY() + (light.getChunk().getStartY() * Chunk.CHUNK_SIZE), currentLight + lightBlockage, lightSource);
				}
				recycledLights.add(light);
				it.remove();
			}
		}
	}
	
	private static void applyExtinguishingLight(ChunkManager manager, int currentX, int currentY, float lightValue, boolean checkEdges) {
		if (currentX < 0 || currentX >= (ChunkManager.CHUNKS_X * Chunk.CHUNK_SIZE) || currentY < 0 || currentY >= (ChunkManager.CHUNKS_Y * Chunk.CHUNK_SIZE)) {
			return;
		}
		Chunk posChunk = manager.getChunkFromTilePos(currentX, currentY);
		
		int posChunkX = currentX - (posChunk.getStartX() * Chunk.CHUNK_SIZE);
		int posChunkY = currentY - (posChunk.getStartY() * Chunk.CHUNK_SIZE);
		
		lightValue -= BlockManager.getBlock(posChunk.getBlock(posChunkX, posChunkY)).getLightBlockingAmount();
	    
		boolean edgeFlag = false; //Tells to set checkEdges at the end of the algorithm

		float currentLight = posChunk.getLightValue(posChunkX, posChunkY);
	    if (currentLight <= LightUtils.MIN_LIGHT_VALUE) {
	    	return;
	    }
		
	    //Assume that the precision loss amount is about .001f
		if (currentLight - lightValue > FLOAT_LOSS_PRECISION) { //If the value of the light is greater than what it should be, then queue it.
			lightQueue.put(posChunk.getStartX() + " " + posChunk.getStartY() + " " + posChunkX + " " + posChunkY, getQueuedLight(posChunk, posChunkX, posChunkY));//Queue this tile to calculate light again
			edgeFlag = true;
		} else if (lightValue - currentLight > FLOAT_LOSS_PRECISION) { //if less than the light it should be then something else will handle it
			return;
		}
	    
	    if (checkEdges) //See if we were only checking edges for higher values to recalculate 
	    	return;
	    
	    if (!edgeFlag) {
	    	posChunk.setLightValue(MIN_LIGHT_VALUE, posChunkX, posChunkY); //extinguish the light
	    	posChunk.getBlockProperties(posChunkX, posChunkY).removeFlag(BlockProperties.LIT_BY_LIGHT_SOURCE);
		    lightQueue.remove(posChunk.getStartX() + " " + posChunk.getStartY() + " " + posChunkX + " " + posChunkY);//Remove if in queue
	    }
	    
	    if (edgeFlag || lightValue <= MIN_LIGHT_VALUE) //Set the checkEdges if we flagged it to do so or if we have the smallest amount of light
	    	checkEdges = true;
	    
	    applyExtinguishingLight(manager, currentX + 1, currentY, lightValue, checkEdges);
	    applyExtinguishingLight(manager, currentX, currentY + 1, lightValue, checkEdges);
	    applyExtinguishingLight(manager, currentX - 1, currentY, lightValue, checkEdges);
	    applyExtinguishingLight(manager, currentX, currentY - 1, lightValue, checkEdges);
	}

	private static void applyExtinguishingLightAmount(ChunkManager manager, int currentX, int currentY, float lightValue, float subtractedValue, boolean checkEdges) {
		if (currentX < 0 || currentX >= (ChunkManager.CHUNKS_X * Chunk.CHUNK_SIZE) || currentY < 0 || currentY >= (ChunkManager.CHUNKS_Y * Chunk.CHUNK_SIZE)) {
			return;
		}
		Chunk posChunk = manager.getChunkFromTilePos(currentX, currentY);

		int posChunkX = currentX - (posChunk.getStartX() * Chunk.CHUNK_SIZE);
		int posChunkY = currentY - (posChunk.getStartY() * Chunk.CHUNK_SIZE);

		boolean edgeFlag = false; //Tells to set checkEdges at the end of the algorithm

		float currentLight = posChunk.getLightValue(posChunkX, posChunkY);
		if (currentLight <= LightUtils.MIN_LIGHT_VALUE) {
			return;
		}

		//Assume that the precision loss amount is about .001f
		if (currentLight - lightValue > FLOAT_LOSS_PRECISION || lightValue - currentLight > FLOAT_LOSS_PRECISION) { //If the value of the light is greater than what it should be, then queue it.
			//lightQueue.put(posChunk.getStartX() + " " + posChunk.getStartY() + " " + posChunkX + " " + posChunkY, getQueuedLight(posChunk, posChunkX, posChunkY));//Queue this tile to calculate light again
			edgeFlag = true;
		} else if (lightValue - currentLight > FLOAT_LOSS_PRECISION) { //if less than the light it should be then something else will handle it
			return;
		}

		if (checkEdges) //See if we were only checking edges for higher values to recalculate
			return;

		if (!edgeFlag) {
			//if (currentLight - lightValue >= 0 && currentLight - lightValue <= FLOAT_LOSS_PRECISION) {
				posChunk.setLightValue(currentLight - subtractedValue, posChunkX, posChunkY);
				posChunk.getBlockProperties(posChunkX, posChunkY).removeFlag(BlockProperties.LIT_BY_LIGHT_SOURCE);
				//lightQueue.remove(posChunk.getStartX() + " " + posChunk.getStartY() + " " + posChunkX + " " + posChunkY);//Remove if in queue
			//}
		}

		if (edgeFlag || lightValue <= MIN_LIGHT_VALUE) //Set the checkEdges if we flagged it to do so or if we have the smallest amount of light
			checkEdges = true;

		float blockingValue = BlockManager.getBlock(posChunk.getBlock(posChunkX, posChunkY)).getLightBlockingAmount();

		applyExtinguishingLightAmount(manager, currentX + 1, currentY, lightValue - blockingValue, subtractedValue, checkEdges);
		applyExtinguishingLightAmount(manager, currentX, currentY + 1, lightValue - blockingValue, subtractedValue, checkEdges);
		applyExtinguishingLightAmount(manager, currentX - 1, currentY, lightValue - blockingValue, subtractedValue, checkEdges);
		applyExtinguishingLightAmount(manager, currentX, currentY - 1, lightValue - blockingValue, subtractedValue, checkEdges);
	}
	
	private static QueuedLight getQueuedLight(Chunk chunk, int x, int y) {
		if (recycledLights.size > 0) {
			QueuedLight light = recycledLights.removeIndex(0);
			light.set(chunk, x, y);
			return light;
		}
		return new QueuedLight(chunk, x, y);
	}
	
}

class QueuedLight {
	
	private Chunk chunk;
	private int x, y;
	
	public QueuedLight(Chunk chunk, int x, int y) {
		this.chunk = chunk;
		this.x = x;
		this.y = y;
	}
	
	public void set(Chunk chunk, int x, int y) {
		this.chunk = chunk;
		this.x = x;
		this.y = y;
	}
	
	public Chunk getChunk() {
		return chunk;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
