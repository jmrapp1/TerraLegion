package com.jmrapp.terralegion.engine.world;

import java.util.Random;

import com.badlogic.gdx.math.MathUtils;

public class SimplexNoise {

    private  int grad3[][] = { {1,1,0},{-1,1,0},{1,-1,0},{-1,-1,0},
        {1,0,1},{-1,0,1},{1,0,-1},{-1,0,-1},
        {0,1,1},{0,-1,1},{0,1,-1},{0,-1,-1}};

    private int p[] = { 151,160,137,91,90,15,
        131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,
        190, 6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,
        88,237,149,56,87,174,20,125,136,171,168, 68,175,74,165,71,134,139,48,27,166,
        77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,55,46,245,40,244,
        102,143,54, 65,25,63,161, 1,216,80,73,209,76,132,187,208, 89,18,169,200,196,
        135,130,116,188,159,86,164,100,109,198,173,186, 3,64,52,217,226,250,124,123,
        5,202,38,147,118,126,255,82,85,212,207,206,59,227,47,16,58,17,182,189,28,42,
        223,183,170,213,119,248,152, 2,44,154,163, 70,221,153,101,155,167, 43,172,9,
        129,22,39,253, 19,98,108,110,79,113,224,232,178,185, 112,104,218,246,97,228,
        251,34,242,193,238,210,144,12,191,179,162,241, 81,51,145,235,249,14,239,107,
        49,192,214, 31,181,199,106,157,184, 84,204,176,115,121,50,45,127, 4,150,254,
        138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180};

    // To remove the need for index wrapping, float the permutation table length
    private  int perm[] = new int[512];
    public SimplexNoise() {
        for(int i = 0; i < 512; i++)
            perm[i] = p[i & 255];
    }

    // This method is  *lot* faster than using (int)Math.floor(x)
    private static int fastfloor(float x) {
        return x > 0 ? (int)x : (int)x - 1;
    }

    private static float dot(int g[], float x, float y) {
        return g[0] * x + g[1] * y;
    }

    float generateRigid(float x, float y, float H, float lacunarity, int octaves, float offset, float threshold)
    {
        // get absolute value of signal (this creates the ridges)
        float signal = Math.abs(generate(x, y));
	    // invert and translate (note that "offset" should be ~= 1.0)
        signal = offset - signal;
	    // square the signal, to increase "sharpness" of ridges
        signal *= signal;

        float result = signal;
        float weight = 1;

        for(int i=1; weight>0.001f && i<octaves; i++) {

            x *= lacunarity;
            y *= lacunarity;

		    // weight successive contributions by previous signal
            weight = signal * threshold;
            weight = MathUtils.clamp(weight, 0, 1);
            signal = Math.abs(generate(x, y));
            signal = offset - signal;
            signal *= signal;
		    // weight the contribution
            signal *= weight;
            result += signal * Math.pow(lacunarity, -i*H);
        }

        return result * 2.0f - 1.0f;
    }

    public float generate(float x, float y, int octaves, float persistence, float scale)
    {
        float total = 0;
        float frequency = scale;
        float amplitude = 1;

        // We have to keep track of the largest possible amplitude,
        // because each octave adds more, and we need a value in [-1, 1].
        float maxAmplitude = 0;

        for( int i=0; i < octaves; i++ ) {
            total += generate( x * frequency, y * frequency ) * amplitude;

            frequency *= 2;
            maxAmplitude += amplitude;
            amplitude *= persistence;
        }

        return total / maxAmplitude;
    }

    public float generate(float xin, float yin) {

        float n0, n1, n2;

        final float F2 = 0.5f * ((float)Math.sqrt(3.0) - 1.0f);
        float s = (xin + yin) * F2;
        int i = fastfloor(xin + s);
        int j = fastfloor(yin + s);

        final float G2 = (3.0f - (float)Math.sqrt(3.0)) / 6.0f;
        float t = (i + j) * G2;
        float X0 = i - t;
        float Y0 = j - t;
        float x0 = xin - X0;
        float y0 = yin - Y0;

        int i1, j1;
        if (x0 > y0) {
            i1=1;
            j1=0;
        } else {
            i1 = 0;
            j1 = 1;
        }

        float x1 = x0 - i1 + G2;
        float y1 = y0 - j1 + G2;
        float x2 = x0 - 1.0f + 2.0f * G2;
        float y2 = y0 - 1.0f + 2.0f * G2;

        int ii = i & 255;
        int jj = j & 255;
        int gi0 = perm[ii + perm[jj]] % 12;
        int gi1 = perm[ii + i1 + perm[jj + j1]] % 12;
        int gi2 = perm[ii + 1 + perm[jj + 1]] % 12;

        float t0 = 0.5f - x0 * x0 - y0 * y0;
        if(t0 < 0)
            n0 = 0.0f;
        else {
            t0 *= t0;
            n0 = t0 * t0 * dot(grad3[gi0], x0, y0);
        }

        float t1 = 0.5f - x1 * x1 - y1 * y1;
        if(t1 < 0)
            n1 = 0.0f;
        else {
            t1 *= t1;
            n1 = t1 * t1 * dot(grad3[gi1], x1, y1);
        }

        float t2 = 0.5f - x2 * x2 - y2 * y2;
        if(t2 < 0)
            n2 = 0.0f;
        else {
            t2 *= t2;
            n2 = t2 * t2 * dot(grad3[gi2], x2, y2);
        }

        return 70.0f * (n0 + n1 + n2);
    }

    public void genGrad(long seed) {
        Random rnd = new Random(seed);
        for(int i = 0; i < 255; i++)
            p[i] = i;
        for(int i = 0; i < 255; i++) {
            int j = rnd.nextInt(255);
            int nSwap = p[i];
            p[i]  = p[j];
            p[j]  = nSwap;
        }

        for(int i = 0; i < 512; i++)
            perm[i] = p[i & 255];
    }


}
