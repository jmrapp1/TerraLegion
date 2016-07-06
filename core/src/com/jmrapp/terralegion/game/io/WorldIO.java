package com.jmrapp.terralegion.game.io;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.jmrapp.terralegion.game.item.inventory.Inventory;
import com.jmrapp.terralegion.game.utils.JSONConverter;
import com.jmrapp.terralegion.game.world.block.BlockType;
import com.jmrapp.terralegion.game.world.chunk.Chunk;
import com.jmrapp.terralegion.game.world.chunk.ChunkManager;
import com.jmrapp.terralegion.game.world.World;
import com.jmrapp.terralegion.game.world.entity.impl.Player;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WorldIO {

	public static void saveWorld(World world) {
		try {
			String basePath = "worlds/" + world.getWorldName();
			Gdx.files.external(basePath).mkdirs();
			FileHandle handle = Gdx.files.external(basePath + "/world.save");
			handle.file().createNewFile();
			
			String totalToken = world.getSeed() + "\n" + 
								world.getChunkManager().CHUNKS_X + " " + world.getChunkManager().CHUNKS_Y + "\n";
			handle.writeString(totalToken, false);
			
			Chunk[][] chunks = world.getChunkManager().getChunks(); //Get the chunks and go through all
			for (int y = 0; y < world.getChunkManager().CHUNKS_Y; y++) {
				for (int x = 0; x < world.getChunkManager().CHUNKS_X; x++) {
					if (chunks[x][y].isGenerated()) { //If it has been generated then save it
						saveChunk(world.getWorldName(), chunks[x][y]);
					}
				}
			}

			// save player
			savePlayer(world.getPlayer());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static LoadedWorldInfo loadWorld(World world) {
		String basePath = "worlds/" + world.getWorldName();
		FileHandle baseDir = Gdx.files.external(basePath);
		if (baseDir.exists()) {
			FileHandle worldDef = Gdx.files.external(basePath + "/world.save");
			String[] lines = worldDef.readString().split("\n");
			
			//Load seed
			long seed = Long.parseLong(lines[0]);
			
			//Load Chunk Dimensions
			String[] dim = lines[1].split(" ");
			int worldChunkSizeWidth = Integer.parseInt(dim[0]);
			int worldChunkSizeHeight = Integer.parseInt(dim[1]);

			//Load player
			Player player = loadPlayer();

			LoadedWorldInfo worldInfo = new LoadedWorldInfo(player, seed, worldChunkSizeWidth, worldChunkSizeHeight);
		
			//Go through directory and find all chunks. Load them.
			for (FileHandle handle : baseDir.list()) {
				if (handle.name().startsWith("chunk")) {
					String[] chunkName = handle.name().split("chunk");
					String[] chunkPos = chunkName[1].replace(".save", "").split("_");
					int x = Integer.parseInt(chunkPos[0]);
					int y = Integer.parseInt(chunkPos[1]);
					Chunk chunk = loadChunk(world, handle, x, y);
					world.getChunkManager().setChunk(chunk, x, y);
				}
			}
			return worldInfo;
		}
		return null;
	}
	
	public static void saveChunk(String worldFileName, Chunk chunk) {
		try {
			String basePath = "worlds/" + worldFileName;
			Gdx.files.external(basePath).mkdirs();
			FileHandle handle = Gdx.files.external(basePath + "/chunk" + chunk.getStartX() + "_" + chunk.getStartY() + ".save");
			
			handle.file().createNewFile();
			
			String totalToken = Chunk.CHUNK_SIZE + " " + Chunk.CHUNK_SIZE + "\n"; //First line is the width and the height of the world
			
			for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
				BlockType lastType = null;
				int sameCount = 0;
				for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
					BlockType type = chunk.getBlock(x, y);
					
					if (type == null) //In case it is null
						type = BlockType.AIR;
					
					if (lastType == null) //if it is the first instance because only the first run will be null
						lastType = type;
					
					if (type == lastType) { //We're still counting the same blocktype
						sameCount++;
					} else if (x != 0){ //We found a new blocktype. Save in format <#>-<typeId>
						totalToken += lastType.getId() + "-" + sameCount + " ";
						sameCount = 1;
						lastType = type;
					}
				}
				if (sameCount != 0) { //If there are values not saved and still counting
					totalToken += lastType.getId() + "-" + sameCount + "\n";
				} else {
					totalToken += "\n";
				}
			}
			totalToken += "\n"; //separate wall and blocks
			for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
				BlockType lastType = null;
				int sameCount = 0;
				for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
					BlockType type = chunk.getWall(x, y);
					
					if (type == null) //In case it is null
						type = BlockType.AIR;
					
					if (lastType == null) //if it is the first instance because only the first run will be null
						lastType = type;
					
					if (type == lastType) { //We're still counting the same blocktype
						sameCount++;
					} else if (x != 0){ //We found a new blocktype. Save in format <#>-<typeId>
						totalToken += lastType.getId() + "-" + sameCount + " ";
						sameCount = 1;
						lastType = type;
					}
				}
				if (sameCount != 0) { //If there are values not saved and still counting
					totalToken += lastType.getId() + "-" + sameCount + "\n";
				} else {
					totalToken += "\n";
				}
			}
			
			handle.writeString(totalToken, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Chunk loadChunk(World world, FileHandle handle, int x, int y) {
		if (handle.exists()) {
			Chunk chunk = new Chunk(world.getChunkManager(), x, y, y == ChunkManager.CHUNKS_Y - 1); //Create a new chunk
			
			String[] rows = handle.readString().split("\n"); //Get all rows
			
			String[] dimensions = rows[0].split(" "); //Get the dimensions from the first line
			int width = Integer.parseInt(dimensions[0]); //get the width
			int height = Integer.parseInt(dimensions[1]); //get the height
			
			for (int i = 0; i < height; i++) { //Go through each row
				String[] rowData = rows[i + 1].split(" "); //Get each column information. + 1 so that we skip the dimension row
				int xOffset = 0; //The offset from the last data count
				for (int j = 0; j < rowData.length; j++) { //Go through each column entry
					String[] data = rowData[j].split("-");
					int id = Integer.parseInt(data[0]);
					int count = Integer.parseInt(data[1]);
					for (int k = 0; k < count; k++) {
						BlockType type = BlockType.getBlockType(id);
						chunk.setBlock(type, xOffset + k, i, false); //Set the block and call any place events (this will automatically set light values for example)
					}
					xOffset += count;
				}
			}
			
			for (int i = 0; i < height; i++) { //Go through each row
				String[] rowData = rows[i + 2 + height].split(" "); //Get each column information. + 2 + height so that we skip the dimensions row, the space row, and the block rows
				int xOffset = 0; //The offset from the last data count
				for (int j = 0; j < rowData.length; j++) { //Go through each column entry
					String[] data = rowData[j].split("-");
					int id = Integer.parseInt(data[0]);
					int count = Integer.parseInt(data[1]);
					for (int k = 0; k < count; k++) {
						BlockType type = BlockType.getBlockType(id);
						chunk.setWall(type, xOffset + k, i, false); //Set the block and call any place events (this will automatically set light values for example)
					}
					xOffset += count;
				}
			}
			chunk.setGenerated(true);
			return chunk;
		}
		return null; //Chunk file doesn't exist
	}
	
	public static boolean chunkFileExists(String worldFileName, int x, int y) {
		return Gdx.files.external("worlds/" + worldFileName + "/chunk" + x + "_" + y + ".save").exists();
	}

	public static void savePlayer(Player player) {
		// create the JSONObject for the player information
		JSONObject playerInfo = new JSONObject();

		// create a JSONObject for the x and y position of the player
		JSONObject playerPosition = new JSONObject();
		playerPosition.put("x", player.getX());
		playerPosition.put("y", player.getY());

		// add the position to the JSONObject playerInfo
		playerInfo.put("playerPosition", playerPosition);

		// create a JSONArray for the inventory items of the player
		JSONObject playerInventory = JSONConverter.getJSONFromInventory(player.getInventory());

		// add the playerInventory "list" to the JSONObject playerInfo
		playerInfo.put("playerInventory", playerInventory);

		// write to file
		try {
			FileHandle handle = Gdx.files.external("player.save");
			if (!handle.exists()) {
				handle.file().createNewFile();
			}

			handle.writeString(playerInfo.toJSONString(), false);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}

	public static Player loadPlayer() {
		try {
			FileHandle handle = Gdx.files.external("player.save");
			if (!handle.exists()) {
				// not saved player yet
				return null;
			}

			String JSONString = handle.readString();

			JSONObject playerInfo = (JSONObject) new JSONParser().parse(JSONString);

			JSONObject playerPosition = (JSONObject) playerInfo.get("playerPosition");
			Player player = new Player(Float.parseFloat(playerPosition.get("x").toString()), Float.parseFloat(playerPosition.get("y").toString()));

			JSONObject jsonInventory = (JSONObject) playerInfo.get("playerInventory");

			Inventory inventory = JSONConverter.getInventoryFromJSON(jsonInventory);

			player.setInventory(inventory);
			return player;
		} catch(ParseException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
}
