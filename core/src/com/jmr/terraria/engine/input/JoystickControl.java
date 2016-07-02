package com.jmr.terraria.engine.input;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;

public class JoystickControl {

	private Touchpad touchpad;
	private TouchpadStyle touchpadStyle;
	private Skin touchpadSkin;
	
	public JoystickControl(Texture background, Texture knob, float deadZoneRadius, float x, float y, float width, float height) {
		touchpadSkin = new Skin();
		//Set background image
		touchpadSkin.add("touchBackground", background);
		//Set knob image
		touchpadSkin.add("touchKnob", knob);
		//Create TouchPad Style
		touchpadStyle = new TouchpadStyle();
		//Apply the Drawables to the TouchPad Style
		touchpadStyle.background = touchpadSkin.getDrawable("touchBackground");
		touchpadStyle.knob = touchpadSkin.getDrawable("touchKnob");
		//Create new TouchPad with the created style
		touchpad = new Touchpad(deadZoneRadius, touchpadStyle);
		//setBounds(x,y,width,height)
		touchpad.setBounds(x, y, width, height);
	}
	
	public Touchpad getTouchpad() {
		return touchpad;
	}
	
}
