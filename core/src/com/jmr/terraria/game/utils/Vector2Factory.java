package com.jmr.terraria.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Jon on 10/1/15.
 */
public class Vector2Factory {

	public static final Vector2Factory instance = new Vector2Factory();
	private Array<Vector2> vectors = new Array<Vector2>();

	private Vector2Factory() {
	}

	public Vector2 getVector2(float x, float y) {
		if (vectors.size > 0) {
			Vector2 vec = vectors.removeIndex(0);
			vec.set(x, y);
			return vec;
		}
		return new Vector2(x, y);
	}

	public Vector2 getVector2() {
		if (vectors.size > 0) {
			Vector2 vec = vectors.removeIndex(0);
			vec.set(0, 0);
			return vec;
		}
		return new Vector2();
	}

	public void destroy(Vector2 vec) {
		if (vec != null) {
			vectors.add(vec);
		}
	}

}
