#ifdef GL_ES
precision mediump float; 
#endif

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform vec2 u_resolution;
uniform sampler2D u_sampler2D;

void main() {
	vec4 color = v_color * texture2D(u_sampler2D, v_texCoord0);
	vec2 relativePosition = gl_FragCoord.xy / u_resolution - .5f;
	float len = length(relativePosition);
	float vignette = smoothstep(.5, .4, len); 
	color.rgb = mix(color.rgb, color.rgb * vignette, .7);
  	gl_FragColor = color;
}