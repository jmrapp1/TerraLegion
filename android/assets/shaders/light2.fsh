#ifdef GL_ES
precision mediump float; 
#endif

varying vec4 v_color;
varying vec2 v_texCoord0;
 
//texture samplers
uniform sampler2D u_texture; //diffuse map
 
//additional parameters for the shader
uniform vec4 ambientColor;
uniform sampler2D u_lightmap;   //light map
 
//resolution of screen
uniform vec2 u_resolution;
 
void main() {
    vec4 diffuseColor = texture2D(u_texture, v_texCoord0);
    vec2 lighCoord = (gl_FragCoord.xy / u_resolution.xy);
    vec4 light = texture2D(u_lightmap, lighCoord);
 
    vec3 ambient = ambientColor.rgb * ambientColor.a;
    vec3 intensity = ambient + light.rgb;
    vec3 finalColor = diffuseColor.rgb * intensity;
 
    gl_FragColor = v_color * vec4(finalColor, diffuseColor.a);
}