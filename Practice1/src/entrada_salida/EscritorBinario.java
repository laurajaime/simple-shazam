package entrada_salida;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/***************************************************************************************
 *  
 *  Utilidad: Permite la escritura de bytes hacia un archivo 
 *  		  de salida, cuyo path se proporciona.
 *  
 **************************************************************************************/

public class EscritorBinario {
	
    private byte buffer;                 // Buffer que representa el byte actual que
    									 // está siendo completado.
    private int numBitsOcupados;         // Número de bits ocupados en el buffer.
	private BufferedOutputStream out;
    
    /**  
     * Constructor.
     * 
     * @param pathOutputFile El path del archivo de salida donde vamos a escribir.
     * @throws RuntimeException Si La ruta del archivo de salida no es correcta o no existe.
     */
    public EscritorBinario(String pathOutputFile){ 
    	
    	buffer = 0;
    	numBitsOcupados = 0;
        try {
        	// Buffer para la escritura de bytes en el archivo de salida
			out = new BufferedOutputStream(new FileOutputStream(pathOutputFile));
		} catch (FileNotFoundException e) {
			 throw new RuntimeException("La ruta del archivo no es correcta o no existe");		
		} 
    }
    
    /**
     * Añade un bit al buffer.
     * 
     * @param bit Representa el bit que va a ser añadido, empleando un booleano: 0=false o 1=true
     * @throws IOException
     */
    public void escribirBit(boolean bit) {
    	
    	// Añadir un bit al buffer
        buffer <<= 1;  
        if (bit) buffer |= 1;
        // Si el buffer esta lleno (8 bits), escribir como un byte
        numBitsOcupados ++;       
        if (numBitsOcupados == 8) 
        	vaciarBuffer();
    }
    
    
    /**
     * Escribir palabra de 8 bits en el archivo de salida 
     * 
     * @param palabra
     * @throws IOException
     */
    public void escribirPalabra(int palabra) {
    	
    	palabra &= 0xff;
    	// Optimización en caso de lectura alineada
    	if(numBitsOcupados == 0) {
    		try {
    			out.write(palabra);
    		}
    		catch (IOException e) {
    			e.printStackTrace();
    		}
    		return;
    	}
    	// En caso contrario escribir un bit cada vez
        for (int i = 0; i < 8; i++) {
            boolean bit = ((palabra >>> (8 - i - 1)) & 1) == 1;
            escribirBit(bit);
        }
    }
    
    /**
     * Escribir palabra de 32 bits en el archivo de salida.
     * @param entero el int a escribir
     */
    public void escribirEntero(int entero) {
    	
    	escribirPalabra(entero >>> 24);
    	escribirPalabra(entero >>> 16);
    	escribirPalabra(entero >>>  8);
    	escribirPalabra(entero);
    }
    
    /**
     * Escribe el buffer (byte) en el archivo de salida y lo vacía.
     * 
     * @throws IOException
     */
    private void vaciarBuffer() {
       
    	if (numBitsOcupados == 0) 
        	return;
        if (numBitsOcupados > 0) 
        	buffer <<= (8 - numBitsOcupados);
        try {
            out.write(buffer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        numBitsOcupados = 0;
        buffer = 0;
    }
       
    /**
     * Cierra el flujo de salida y libera los recursos del sistema asociados.
     * 
     */
    public void cerrarFlujo() {
    	
    	vaciarBuffer();
    	try {
    		out.flush();
    		out.close();
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    }
}
