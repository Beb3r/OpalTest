package com.gb.opaltest.features.splash_screen.presentation.shaders

import org.intellij.lang.annotations.Language

@Language("AGSL")
val dotsShaderSource = """
        // Circular animation shader converted from GLSL to AGSL
        uniform shader parent;
        uniform float2 resolution;
        uniform float time;
        uniform float2 mouse;
        uniform float scale;
        
        
        half4 main(float2 fragCoord) {
            // Calculate surfacePosition (equivalent to surfacePosition varying in GLSL)
            // Normalize coordinates to [-1, 1] range
            float2 surfacePosition = (fragCoord / resolution) * 2.0 - 1.0;
            // Correct aspect ratio
            surfacePosition.x *= resolution.x / resolution.y;
            surfacePosition *= scale; // Apply scaling after centering
       
            
            float size = 0.2;
            float dist = 0.0;
            float ang = 0.0;
            float2 pos = float2(1.0, 1.0);
            float3 color = float3(0.1);
            
            for(int i=0; i<12; i++) {
                float r = 0.4;
                ang += 3.14159265358979 / (float(12) * 0.5);
                pos = float2(cos(ang), sin(ang)) * r * cos(time + ang / 0.1);
                dist += size / distance(pos, surfacePosition);
                float3 c = float3(0.05);
                color = c * dist;
            }
            
            return half4(color, 1.0);
        }
    """.trimIndent()