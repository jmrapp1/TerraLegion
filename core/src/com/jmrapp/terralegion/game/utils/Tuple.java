package com.jmrapp.terralegion.game.utils;

/**
 * Created by Jon on 12/19/15.
 */
public class Tuple<F, K> {

	private F o1;
	private K o2;

	public Tuple(F o1, K o2) {
		this.o1 = o1;
		this.o2 = o2;
	}

	public F getObject1() {
		return o1;
	}

	public K getObject2() {
		return o2;
	}

	public void setObject1(F o1) {
		this.o1 = o1;
	}

	public void setObject2(K o2) {
		this.o2 = o2;
	}

}

