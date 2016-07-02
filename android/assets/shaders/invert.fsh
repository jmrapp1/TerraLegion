#ifdef GL_ES
precision mediump float; 
#endif

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_sampler2D;

void main() {
	vec4 color = v_color * texture2D(u_sampler2D, v_texCoord0);
	color = vec4(1 - color.x, 1 - color.y, 1 - color.z, color.a);
  	gl_FragColor = color;
}