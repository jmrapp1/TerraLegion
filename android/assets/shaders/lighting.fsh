#ifdef GL_ES
precision mediump float; 
#endif

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_texture;
uniform sampler2D u_normals;

uniform vec2 u_resolution;
uniform vec3 u_lightPos;
uniform vec4 u_lightColor;
uniform vec4 u_ambientColor;
uniform vec3 u_fallOff;

void main() {
	vec4 diffuseColor = texture2D(u_texture, v_texCoord0);
	vec3 normalMap = texture2D(u_normals, v_texCoord0).rgb;
	vec3 lightDir = vec3(u_lightPos.xy - (gl_FragCoord.xy / u_resolution.xy), 0);
	lightDir.x *= u_resolution.x / u_resolution.y;
	
	float len = length(lightDir);
	
	vec3 norm = normalize(normalMap * 2.0 - 1.0);
	vec3 lightNorm = normalize(lightDir);
	
	vec3 diffuse = (u_lightColor.rgb * u_lightColor.a) * max(dot(norm, lightNorm), 0.0);
	vec3 ambient = u_ambientColor.rgb * u_ambientColor.a;
	
	float attenuation = 1.0 / (u_fallOff.x + (u_fallOff.y * len) + (u_fallOff.z * len * len));
	
	vec3 intensity = ambient + diffuse * attenuation;
	vec3 finalColor = diffuseColor.rgb * intensity;
	gl_FragColor = v_color * vec4(finalColor, diffuseColor.a);
}