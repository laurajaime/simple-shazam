package utilities;

import fingerprinting.AudioParams;

public class Spectrum {

    // Compute the magnitude spectrum from the recorded audio (time domain)
    public static double[][] compute(byte[] audioTimeDomain) {

        final int totalSize = audioTimeDomain.length;
        
        // The number of chunks/ventanas in the recorded audio
        int chunks = totalSize / AudioParams.chunkSize;

        // We will use this bidimensional array as output variable to return the magnitude spectrum
        double[][] resultsMag = new double[chunks][];

        // Turning into frequency domain by using the FFT (Fast Fourier Transform): 
            // We'll need to work with complex numbers, so we initialize and prepare them
            // to perform the FFT:                
                Complex[][] resultsComplex = new Complex[chunks][];
                
                // For each chunk/ventana:
                for (int i = 0; i < chunks; i++) {                    
                    // Put the time domain data contained in the current chunk into 
                    // a complex number (complex) with 0 as imaginary part
                    Complex[] complex = new Complex[AudioParams.chunkSize];
                    // ...                                
                    // Perform the FFT for the current chunk (FFT.fft(complex))
                    // and save the results into resultsComplex[i]                    
                    // ... 
                    // Initialize to avoid NullPointerExceptions
                    resultsMag[i]= new double[AudioParams.chunkSize];                       
                    // Save into resultsMag[i] the log magnitude for each frequency 
                    // within each chunk
                    for (int j = 0; j < AudioParams.chunkSize; j++) {
                        resultsMag[i][j] = Math.log(resultsComplex[i][j].abs() + 1);
                    }
                }                       
        return resultsMag;
    }
}
