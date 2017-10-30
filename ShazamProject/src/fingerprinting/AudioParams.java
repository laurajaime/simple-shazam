package fingerprinting;

public class AudioParams {
    
    // Sampling
    public static final float sampleRate = 44100;
    public static final int sampleSizeInBits = 8;
    public static final int channels = 1; // Mono
    public static final boolean signed = true;
    public static final boolean bigEndian = true;
    
    // Acquiring audio 
    public static final int bufferSize = 1024;
    
    // FFT size
    public static final int chunkSize = 4096; // Must be power of 2 for FFT
    
    // Keypoints - Range of frequencies used
    public static final int lowerLimit = 40; 
    public static final int unpperLimit = 300;
    public static final int[] range = new int[]{40, 80, 120, 180, unpperLimit + 1};
    
    // Hash function fuzz factor 
    public static final int fuzzFactor = 2;
}
