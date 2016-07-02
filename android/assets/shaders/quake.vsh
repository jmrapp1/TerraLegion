attribute vec3 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;

uniform vec3 u_distort;
uniform mat4 u_projTrans;

varying vec4 v_color;
varying vec2 v_texCoord0;

void main() {
   v_color = a_color;
   v_texCoord0 = a_texCoord0;
   gl_Position =  u_projTrans * vec4(a_position + u_distort, 1.0);
}