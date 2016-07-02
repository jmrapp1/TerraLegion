package com.jmr.terraria.engine.world;

import com.badlogic.gdx.math.Rectangle;
import com.jmr.terraria.engine.camera.OrthoCamera;
import com.jmr.terraria.engine.world.collision.CollisionInfo;
import com.jmr.terraria.engine.world.collision.CollisionInfoFactory;
import com.jmr.terraria.engine.world.collision.CollisionSide;
import com.jmr.terraria.engine.world.entity.BodyType;
import com.jmr.terraria.engine.world.entity.WorldBody;
import com.jmr.terraria.game.world.block.BlockBody;
import com.jmr.terraria.game.world.block.BlockBodyFactory;
import com.jmr.terraria.game.world.block.BlockManager;
import com.jmr.terraria.game.world.block.BlockType;
import com.jmr.terraria.game.world.chunk.ChunkManager;

public class PhysicsWorld {

	private float gravityX, gravityY;
	private float maxYVelocity = -1, maxXVelocity = -1, xFriction = -1;
	private ChunkManager chunkManager;
	
	public PhysicsWorld(ChunkManager chunkManager, float gravityX, float gravityY) {
		this.chunkManager = chunkManager;
		this.gravityX = gravityX;
		this.gravityY = gravityY;
	}
	
	public void setMaxYVelocity(float max) {
		maxYVelocity = max;
	}
	
	public void setMaxXVelocity(float max) {
		maxXVelocity = max;
	}
	
	public void setXFriction(float friction) {
		xFriction = friction;
	}
	
	public void setGravityToEntity(WorldBody entity) {
		if (entity.getBodyType() == BodyType.DYNAMIC) {
			entity.setAcceleration(gravityX, gravityY);
		}
	}

	public void focusCamera(WorldBody obj, int screenWidth, int screenHeight, int worldWidth, int worldHeight, OrthoCamera camera) {
		float camX = 0, camY = 0;
		
		if (worldWidth - obj.getX() >= (screenWidth / 2) * camera.zoom)
			camX = obj.getX();
		else if (worldWidth - obj.getX() <= (screenWidth / 2) * camera.zoom && obj.getX() > (screenWidth / 2) * camera.zoom)
			camX = worldWidth - (screenWidth / 2) * camera.zoom;
		
		if (obj.getX() <= (screenWidth / 2) * camera.zoom)
			camX = (screenWidth / 2) * camera.zoom;
		else if (obj.getX() > (screenWidth / 2) * camera.zoom && obj.getX() < worldWidth - (screenWidth / 2) * camera.zoom)
			camX = obj.getX();
		
		if (worldHeight - obj.getY() > (screenHeight / 2) * camera.zoom)
			camY = obj.getY();
		else if (worldHeight - obj.getY() <= (screenHeight / 2) * camera.zoom)
			camY = worldHeight - (screenHeight / 2) * camera.zoom;
		
		if (obj.getY() <= (screenHeight / 2) * camera.zoom)
			camY = (screenHeight / 2) * camera.zoom;
		else if (obj.getY() > (screenHeight / 2) * camera.zoom && obj.getY() < worldHeight - (screenHeight / 2) * camera.zoom)
			camY = obj.getY();
		
		if (camX != camera.getPos().x || camY != camera.getPos().y)
			camera.setPosition(camX, camY);
	}
	
	public void updateWorldBody(WorldBody body) {
		body.updateVel();
		body.update();
		
		if (maxYVelocity != -1) {
			if (Math.abs(body.getVelY()) > maxYVelocity) {
				if (body.getVelY() < 0)
					body.setVelocity(body.getVelX(), -maxYVelocity);
				else
					body.setVelocity(body.getVelX(), maxYVelocity);
			}
		}
		if (maxXVelocity != -1) {
			if (Math.abs(body.getVelX()) > maxXVelocity) {
				if (body.getVelX() < 0)
					body.setVelocity(-maxXVelocity, body.getVelY());
				else
					body.setVelocity(maxXVelocity, body.getVelY());
			}
		}
		if (xFriction != -1) {
			if (body.getVelX() < 0) {
				body.addVelocity(xFriction, 0);
				if (body.getVelX() > 0)
					body.setVelocity(0, body.getVelY());
			} else {
				body.addVelocity(-xFriction, 0);
				if (body.getVelX() < 0)
					body.setVelocity(0, body.getVelY());
			}
		}
		
		checkCollisionsOnBlocks(body);
		if (body.getBodyType() == BodyType.DYNAMIC) {
			body.setAcceleration(gravityX, gravityY);
		}
		body.updatePos();
	}
	
	/**
	 * Checks the collisions around the world body.<br />
	 * It will check it with a 2 block radius from the body sides.<br />
	 * Say that the body is 30x50 pixels. It will check in this sequence where 'x' is a check and 'b' is the body
	 *
	 * x   x   x   x   x   x <br />
	 * x   x   x   x   x   x <br />
	 * x   x   b   b   x   x <br />
	 * x   x   b   b   x   x <br />
	 * x   x   x   x   x   x <br />
	 * x   x   x   x   x   x <br />
	 *
	 * @param body
	 */
	public void checkCollisionsOnBlocks(WorldBody body) {
		int tx = ChunkManager.pixelToTilePosition(body.getX());
		int ty = ChunkManager.pixelToTilePosition(body.getY());

		int tileCountVertical = (int)(body.getHeight() / ChunkManager.TILE_SIZE); //Get how many tiles going vertically we need to check to fit the size of the body (height)
		int tileCountHorizontal = (int)(body.getHeight() / ChunkManager.TILE_SIZE); //Get how many tiles going horizontally we need to check to fit the size of the body (width)
		
		BlockBody blockBody = BlockBodyFactory.instance.getBody(); //Get a body to perform tests on
		for (int i = -2; i < tileCountHorizontal + 2; i++) { 
			for (int j = -2; j < tileCountVertical + 2; j++) {
				int cx = tx + i; //Check the left side of the body
				int cy = ty + j; //Check up the side of the body
				
				BlockType type = chunkManager.getBlockFromTilePos(cx, cy); //Get the type of block
				if (type != null && BlockManager.getBlock(type).collides()) { //If it collides
					blockBody.set(cx * ChunkManager.TILE_SIZE, cy * ChunkManager.TILE_SIZE, ChunkManager.TILE_SIZE, ChunkManager.TILE_SIZE, BodyType.STATIC);
					checkAndHandleCollision(body, blockBody);
				}
			}
		}
		BlockBodyFactory.instance.destroy(blockBody); //Dispose of the body
	}
	
	public void checkAndHandleCollision(WorldBody w1, WorldBody w2) {
		if (w1 != w2) {
			Rectangle r2 = w2.getDesiredBounds();
			if (w1.getDesiredBounds().overlaps(r2)) {
				CollisionSide c1 = null, c2 = null;
				
			    if (w1.getLeftBound().overlaps(r2)) {
			        c1 = CollisionSide.LEFT;
			        c2 = CollisionSide.RIGHT;
			    }  else if (w1.getRightBound().overlaps(r2)) {
			        c1 = CollisionSide.RIGHT;
			        c2 = CollisionSide.LEFT;
			    } else if (w1.getBottomBound().overlaps(r2)) {
			        c1 = CollisionSide.BOTTOM;
			        c2 = CollisionSide.TOP;
			    } else if (w1.getTopBound().overlaps(r2)) {
			        c1 = CollisionSide.TOP;
			        c2 = CollisionSide.BOTTOM;
			    }					    
			    CollisionInfo info = CollisionInfoFactory.instance.getCollisionInfo(w1, w2, c1, c2);
			    
				boolean w1Collision = w1.collision(w2, info);
				boolean w2Collision = w2.collision(w1, info);
				
				if (w1Collision) {
					if (c1 == CollisionSide.BOTTOM || c1 == CollisionSide.TOP) {
			    		w1.setVelocity(w1.getVelX(), 0); //Stop all vertical movement
			    		w1.setAcceleration(w1.getAccelX(), 0); //Stop all vertical movement
				    	
			    		if (c2 == CollisionSide.BOTTOM) {
							if (w2.getFriction() > 0) { //Handle friction if there is any
					    		if (w1.getVelX() < 0) {
					    			w1.addVelocity(w2.getFriction(), 0);
						   			if (w1.getVelX() > 0)
						   				w1.setVelocity(0, w1.getVelY());
					    		} else {
					    			w1.addVelocity(-w2.getFriction(), 0);
						   			if (w1.getVelX() < 0)
						   				w1.setVelocity(0, w1.getVelY());
						   		}
						   	}
			    		}
				    	
				    	w1.resetLastYLocation();

					    //There is a small glitch where in some cases the body will get stuck in the top 
					    //or the bottom of the body it's colliding with. In this case we will need to 
					    //check if the body is still colliding after moving and if it is then manually set
					    //the position
					    if (c1 == CollisionSide.TOP) {
					    	if (w1.getTopDesiredBound().overlaps(w2.getBounds())) {
						    	//Colliding at top again so set the position to the bottom of the body
					    		w1.setDesired(w1.getDesiredX(), w2.getDesiredY() - w1.getHeight());
						    }
					    }
					    if (c1 == CollisionSide.BOTTOM) {
					    	if (w1.getBottomDesiredBound().overlaps(w2.getBounds())) {
						    	//Colliding at top again so set the position to the bottom of the body
					    		w1.setDesired(w1.getDesiredX(), w2.getDesiredY() + w2.getHeight());
						    }
					    }
					    	
				    } else if (c1 == CollisionSide.LEFT || c1 == CollisionSide.RIGHT) {
						w1.setVelocity(0, w1.getVelY()); //Stop all horizontal movement
						w1.setAcceleration(0, w1.getAccelY()); //Stop all horizontal movement
						w1.resetLastXLocation();
				    }
				}
				
				if (w2Collision) {
					if (c2 == CollisionSide.BOTTOM || c2 == CollisionSide.TOP) {
				    	w2.setVelocity(w2.getVelX(), 0); //Stop all downward and upward movement
					   	w2.setAcceleration(w2.getAccelX(), 0);
					   	
				    	if (c2 == CollisionSide.BOTTOM) {
							if (w1.getFriction() > 0) { //Stop all friction if there is any
						   		if (w2.getVelX() < 0) {
						   			w2.addVelocity(w1.getFriction(), 0);
						   			if (w2.getVelX() > 0)
						    			w2.setVelocity(0, w2.getVelY());
						    	} else {
					    			w2.addVelocity(-w1.getFriction(), 0);
					    			if (w2.getVelX() < 0)
					    				w2.setVelocity(0, w2.getVelY());
					    		}
					    	}
				    	}
					   	
					    w2.resetLastYLocation();	
					    
				    } else if (c2 == CollisionSide.LEFT || c2 == CollisionSide.RIGHT) {
						w2.setVelocity(0, w2.getVelY());
					   	w2.setAcceleration(0, w2.getAccelY());
					    w2.resetLastXLocation();
				    }
				}

				CollisionInfoFactory.instance.destroy(info);
			}
		}
	}
	
}
