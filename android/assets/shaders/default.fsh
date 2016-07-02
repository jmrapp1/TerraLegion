#ifdef GL_ES
precision mediump float; 
#endif

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_sampler2D;

void main() {
  	gl_FragColor = v_color * texture2D(u_sampler2D, v_texCoord0);
}