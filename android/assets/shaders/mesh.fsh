#ifdef GL_ES
precision mediump float; 
#endif

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform vec4 u_ambientColor;
uniform sampler2D u_texture;

void main() {
	vec4 diffuseColor = texture2D(u_texture, v_texCoord0);
	vec3 ambient = u_ambientColor.rgb * u_ambientColor.a;
  	gl_FragColor = v_color * texture2D(u_texture, v_texCoord0);
}