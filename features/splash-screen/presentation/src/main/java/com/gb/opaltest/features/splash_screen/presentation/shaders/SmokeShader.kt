package com.gb.opaltest.features.splash_screen.presentation.shaders

import org.intellij.lang.annotations.Language

@Language("AGSL")
val smokeShaderSource = """
        uniform float time;
        uniform float2 resolution;

// Rotation matrices
float3x3 rotX(float a) {
    float c = cos(a);
    float s = sin(a);
    return float3x3(
        1.0, 0.0, 0.0,
        0.0, c, -s,
        0.0, s, c
    );
}

float3x3 rotY(float a) {
    float c = cos(a);
    float s = sin(a);
    return float3x3(
        c, 0.0, -s,
        0.0, 1.0, 0.0,
        s, 0.0, c
    );
}

// Random hash function
float random(float2 pos) {
    return fract(sin(dot(pos.xy, float2(12.9898, 78.233))) * 43758.5453123);
}

// 2D noise
float noise(float2 pos) {
    float2 i = floor(pos);
    float2 f = fract(pos);
    float a = random(i + float2(0.0, 0.0));
    float b = random(i + float2(1.0, 0.0));
    float c = random(i + float2(0.0, 1.0));
    float d = random(i + float2(1.0, 1.0));
    float2 u = f * f * (3.0 - 2.0 * f);
    return mix(a, b, u.x) + (c - a) * u.y * (1.0 - u.x) + (d - b) * u.x * u.y;
}

// Fractal Brownian Motion
float fbm(float2 pos) {
    float v = 0.0;
    float a = 0.5;
    float2 shift = float2(100.0);
    float2x2 rot = float2x2(cos(0.5), sin(0.5), -sin(0.5), cos(0.5));
    for (int i = 0; i < 6; i++) {
        float dir = mod(float(i), 2.0) > 0.5 ? 1.0 : -1.0;
        v += a * noise(pos - 0.05 * dir * time);
        pos = rot * pos * 2.0 + shift;
        a *= 0.5;
    }
    return v;
}

// Main function
half4 main(float2 fragCoord) {
    float2 p = (fragCoord * 2.0 - resolution.xy) / min(resolution.x, resolution.y);
    p -= float2(12.0, 0.0);
    
    float time2 = 1.0;
    
    // Create FBM noise layers
    float2 q = float2(0.0);
    q.x = fbm(p + 0.00 * time2);
    q.y = fbm(p + float2(1.0));
    
    float2 r = float2(0.0);
    r.x = fbm(p + 1.0 * q + float2(1.7, 9.2) + 0.15 * time2);
    r.y = fbm(p + 1.0 * q + float2(8.3, 2.8) + 0.126 * time2);
    
    float f = fbm(p + r);
    
    // Color blending
    float3 color = mix(
        float3(0.3, 0.3, 0.6),
        float3(0.7, 0.7, 0.7),
        clamp((f * f) * 4.0, 0.0, 1.0)
    );
    
    color = mix(
        color,
        float3(0.7, 0.7, 0.7),
        clamp(length(q), 0.0, 1.0)
    );
    
    color = mix(
        color,
        float3(0.4, 0.4, 0.4),
        clamp(length(r.x), 0.0, 1.0)
    );
    
    color = (f * f * f + 0.9 * f * f + 0.8 * f) * color;

    return half4(color * 0.7, color.r);
}
    """.trimIndent()