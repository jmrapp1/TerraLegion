#ifdef GL_ES
precision mediump float; 
#endif

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform vec2 u_resolution;
uniform sampler2D u_sampler2D;

void main() {
	vec2 onePixel = vec2(1. / u_resolution.x, 1. / u_resolution.y);
	
	vec4 color = vec4(.5);
	color += texture2D(u_sampler2D, v_texCoord0 - onePixel) * 5;
	color -= texture2D(u_sampler2D, v_texCoord0 + onePixel) * 5;
	
	color.rgb = vec3((color.r + color.g + color.b) / 3.0);
	color.a = 1;
	gl_FragColor = color;
}