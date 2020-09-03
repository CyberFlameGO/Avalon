#version 400 core

in vec3 colour;
in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[4];
in vec3 toCameraVector;
in float visibility;

out vec4 out_Color;

uniform sampler2D modelTexture;
uniform vec3 lightColour[4];
uniform vec3 lightAttenuation[4];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;

void main(void) {
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitVectorToCamera = normalize(toCameraVector);

    vec3 totalDiffuse = vec3(0);
    vec3 totalSpecular = vec3(0);

    for (int i = 0; i < 4; i++) {
        vec3 toLight = toLightVector[i];

        float distance = length(toLight);
        float attFactor = lightAttenuation[i].x + (lightAttenuation[i].y * distance) + (lightAttenuation[i].z * distance * distance);

        vec3 unitLightVector = normalize(toLight);

        float nDot1 = dot(unitNormal, unitLightVector);
        float brightness = max(nDot1, 0);

        vec3 lightDirection = -unitLightVector;
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

        float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
        specularFactor = max(specularFactor, 0);
        float dampedFactor = pow(specularFactor, shineDamper);

        totalDiffuse = totalDiffuse + (brightness * lightColour[i]) / attFactor;
        totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColour[i]) / attFactor;
    }
    totalDiffuse = max(totalDiffuse, 0.2);

    //out_Color = vec4(diffuse, 1.0) * vec4(colour, 1.0) + vec4(finalSpecular, 1); // works
    out_Color = vec4(totalDiffuse, 1.0) * texture(modelTexture, pass_textureCoords) + vec4(totalSpecular, 1);

    out_Color = mix(vec4(skyColour, 1), out_Color, visibility);
}