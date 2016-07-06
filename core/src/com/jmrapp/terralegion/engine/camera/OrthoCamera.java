package com.jmrapp.terralegion.engine.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jmrapp.terralegion.engine.utils.Settings;

public class OrthoCamera extends OrthographicCamera {

	private Vector3 tmp = new Vector3();
	private Vector2 origin = new Vector2();
	private VirtualViewport virtualViewport;
	private Vector2 pos = new Vector2();
	private Vector3 cachedVec3 = new Vector3();

	public OrthoCamera() {
		this(new VirtualViewport(Settings.getWidth(), Settings.getHeight()));
	}
	
	public OrthoCamera(VirtualViewport virtualViewport) {
		this(virtualViewport, 0f, 0f);
	}

	public OrthoCamera(VirtualViewport virtualViewport, float cx, float cy) {
		this.virtualViewport = virtualViewport;
		this.origin.set(cx, cy);
	}

	public void setVirtualViewport(VirtualViewport virtualViewport) {
		this.virtualViewport = virtualViewport;
	}
	
	public void setPosition(float x, float y) {
		position.set(x - viewportWidth * origin.x, y - viewportHeight * origin.y, 0f);
		pos.set(x, y);
	}
	
	public Vector2 getPos() {
		return pos;
	}

	@Override
	public void update() {
		float left = zoom * -viewportWidth / 2 + virtualViewport.getVirtualWidth() * origin.x;
		float right = zoom * viewportWidth / 2 + virtualViewport.getVirtualWidth() * origin.x;
		float top = zoom * viewportHeight / 2 + virtualViewport.getVirtualHeight() * origin.y;
		float bottom = zoom * -viewportHeight / 2 + virtualViewport.getVirtualHeight() * origin.y;

		projection.setToOrtho(left, right, bottom, top, Math.abs(near), Math.abs(far));
		view.setToLookAt(position, tmp.set(position).add(direction), up);
		combined.set(projection);
		Matrix4.mul(combined.val, view.val);
		invProjectionView.set(combined);
		Matrix4.inv(invProjectionView.val);
		frustum.update(invProjectionView);
		//Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);
	}

	/**
	 * This must be called in ApplicationListener.resize() in order to correctly update the camera viewport. 
	 */
	public void updateViewport() {
		setToOrtho(false, virtualViewport.getWidth(), virtualViewport.getHeight());
	}
	
	public void updateViewport(float width, float height) {
		setToOrtho(false, width, height);
	}

	public Vector2 unprojectCoordinates(float x, float y) {
        cachedVec3.set(x, y,0);
        unproject(cachedVec3);
        return new Vector2(cachedVec3.x, cachedVec3.y);
    }

	public float unprojectXCoordinate(float x, float y) {
		cachedVec3.set(x, y,0);
		unproject(cachedVec3);
		return cachedVec3.x;
	}

	public float unprojectYCoordinate(float x, float y) {
		cachedVec3.set(x, y, 0);
		unproject(cachedVec3);
		return cachedVec3.y;
	}
	
	public void resize() {
		VirtualViewport virtualViewport = new VirtualViewport(Settings.getWidth(), Settings.getHeight());
		setVirtualViewport(virtualViewport);  
		updateViewport(Settings.getWidth(), Settings.getHeight());
	}

	public void resize(int width, int height) {
		VirtualViewport virtualViewport = new VirtualViewport(width, height);
		setVirtualViewport(virtualViewport);  
		updateViewport(Settings.getWidth(), Settings.getHeight());
	}
	
}
