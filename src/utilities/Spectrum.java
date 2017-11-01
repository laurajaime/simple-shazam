package utilities;

import fingerprinting.AudioParams;

public class Spectrum {

    // Compute the magnitude spectrum from the recorded audio (time domain)
	//Calcular la magnitud del espectro desde la cancion (audioTimeDomain)
    public static double[][] compute(byte[] audioTimeDomain) {

        final int totalSize = audioTimeDomain.length;
        
        // The number of chunks/ventanas in the recorded audio
        //El numero de ventanas en la cancion
        int chunks = totalSize / AudioParams.chunkSize;

        // We will use this bidimensional array as output variable to return the magnitude spectrum
        //Se usa un array bidimensional donde el resultado devuelve la magnitud del espectro
        double[][] resultsMag = new double[chunks][];

        // Turning into frequency domain by using the FFT (Fast Fourier Transform): 
        //Convertirlo en dominio de frecuencia mediante la transformada de Fourier
            // We'll need to work with complex numbers, so we initialize and prepare them
            // to perform the FFT:                
        	//Trabajamos con numeros complejos
                Complex[][] resultsComplex = new Complex[chunks][];
                
                // For each chunk/ventana:
                for (int i = 0; i < chunks; i++) {                    
                    // Put the time domain data contained in the current chunk into 
                    // a complex number (complex) with 0 as imaginary part
                	//Poner los datos de dominio de tiempo contenidos en la ventana actual en
                    // un número complejo con 0 como parte imaginaria.
                    Complex[] complex = new Complex[AudioParams.chunkSize];
                    // ...
                    for(int j = 0; j<AudioParams.chunkSize; j++) {
                        complex[j] = new Complex(audioTimeDomain[(i*AudioParams.chunkSize)],0);
                    }
                    // Perform the FFT for the current chunk (FFT.fft(complex))
                    // and save the results into resultsComplex[i]  
                    //Aplicar el FFT para la ventana actual y guardar el resultado en resultsComplex[i}
                    resultsComplex[i] = FFT.fft(complex);
                    
                    System.out.println(complex[i]);
                    System.out.println(resultsComplex[i].toString());
                    
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
