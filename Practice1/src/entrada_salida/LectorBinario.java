package entrada_salida;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/***************************************************************************************
 *  
 *  Utilidad: Permite la lectura de palabras de 8 bits de un archivo de entrada, cuyo
 *  		  path se proporciona.
 *
 **************************************************************************************/

public final class LectorBinario {
	
	private BufferedInputStream in;
    private final int EOF = -1;  // End of File.
    private int buffer;          // Variable tipo int para almacenar la 
    							 // palabra de 8 bits leída. 
    
    private int numBitsOcupados; // Número de bits ocupados en el buffer.
    
     /**  
     * Constructor.
     * @throws RuntimeException Si La ruta del archivo de entrada no es correcta o no existe.
     */
    public LectorBinario(String pathName){
    	
    	// Buffer para la lectura de bytes del archivo de entrada (1 byte al tiempo) 
        try {
			in = new BufferedInputStream(new FileInputStream(pathName));
		} catch (FileNotFoundException e) {
			 throw new RuntimeException("La ruta del archivo no es correcta o no existe");		} 
        llenarBuffer();
    }
   
    private void llenarBuffer() {
       
    	try {
            buffer = in.read(); // Lectura de un byte
            numBitsOcupados = 8;
        }
        catch (IOException e) {
            System.out.println("EOF");
            buffer = EOF; // Se alcanza el final del flujo de entrada
            numBitsOcupados = -1;
        }
    }
    
    /**
     * Cierra el flujo de entrada y libera los recursos del sistema asociados.
     */
    public void cerrarFlujo() {
       
    	try {
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @return true si y solo si no queda flujo de entrada (EOF).
     */
    public boolean esVacio() {
        return buffer == EOF;
    }

    /**
     * Lee la próxima palabra de 8 bits en el archivo de entrada.
     *
     * @return Devuelve la palabra de 8 bits.
     * @throws RuntimeException si el flujo de entrada queda vacío.
     */
    public char leerPalabra() {
    
    	if (esVacio()) throw new RuntimeException("Leyendo de un flujo de entrada vacío");
    	// Caso especial de alineado:
        if (numBitsOcupados == 8) {
        	int aux = buffer;
        	llenarBuffer();
        	return (char) (aux & 0xff);
        }
        
        // Se combinan los últimos n bits del buffer actual con los primeros 8-n  
        // bits del nuevo buffer.
        int x = buffer;
        x <<= (8 - numBitsOcupados); 
        int oldN = numBitsOcupados;
        llenarBuffer();
        if (esVacio()) throw new RuntimeException("Reading from empty input stream");
        numBitsOcupados = oldN;
        x |= (buffer >>> numBitsOcupados);
        return (char) (x & 0xff);
    }
    
    /**
     * Lee el próximo bit en el archivo de entrada y lo devuelve como booleano.
     *
     * @return Devuelve el próximo bit como booleano.
     * @throws RuntimeException si se alcanzó el final del archivo de entrada.
     */
    public boolean leerBit() {
      
    	if (esVacio()) throw new RuntimeException("Leyendo de un flujo de entrada vacio");
        numBitsOcupados--;
        boolean bit = ((buffer >> numBitsOcupados) & 1) == 1;
        if (numBitsOcupados == 0) 
        	llenarBuffer();
        return bit;
    }
    
    /**
     * Lee la próxima palabra de 32 bits en el archivo de entrada.
     *
     * @return la próxima palabra de 32 bits como int.
     * @throws RuntimeException si hay menos de 32 bits disponibles en la entrada.
     */
    public int leerEntero() {
       
    	int x = 0;
        for (int i = 0; i < 4; i++) {
            char c = leerPalabra();
            x <<= 8;
            x |= c;
        }
        return x;
    }
}