/*******************************
 * 
 * 
 * Autor: Laura Jaime Villamayor
 * 
 */
import java.util.Map;
import java.util.PriorityQueue;
import entrada_salida.EscritorBinario;
import entrada_salida.LectorBinario;
import estructuras_datos.ArbolHuffman;

/*********************************************************************************************
 *  Ejecución: 
 *  	% Comprimir:    java PlantillaCodificacionHuffman -c filePathIn filePathOut
 *      % Decomprimir:  java PlantillaCodificacionHuffman -d filePathIn filePathOut
 *  
 *  Utilidad: Permite la compresión/descompresión usando el algoritmo de Huffman
 *  de un archivo de entrada hacia un archivo de salida. 
 *  
 *
 *********************************************************************************************/

public class PlantillaCodificacionHuffman {

	
	// Constructor
	private PlantillaCodificacionHuffman(){}
	
	/*
	* Se lee el archivo de entrada (filePathIn, a comprimir) como secuencia de palabras de 8 bits 
	* usando LectorBinario, después se codifica con el algoritmo de Huffman y el resultado 
	* se escribe usando la clase EscritorBinario hacia el archivo de salida (filePathOut, comprimido).
	*/
    public void comprimir(String filePathIn, String filePathOut) {
		
    	LectorBinario lector = new LectorBinario(filePathIn);
		// Leer archivo de entrada y almacenar en una cadena
		StringBuilder sb = new StringBuilder();
		while (!lector.esVacio()) {
			char b = lector.leerPalabra();
			sb.append(b); 	// OJO! leerPalabra() devuelve una palabra 
							// de 8 bits y el tipo char es de 16 bits
		}
		char[] input = sb.toString().toCharArray();
		

		///////////////////////TAREA1.1///////////////////////
		// Generar tabla de frecuencias (freq) a partir del array de tipo char input.
		int[] freq = new int[256]; //256 caracteres en ASCII
		
		for(int i=0; i<input.length;i++) {
			freq[input[i]]++;
		}
		
		System.out.println("El archivo " + filePathIn + " se ha comprimido correctamente.");
		//////////////////////////////////////////////////////
		
		
		
		// Construir árbol de Huffman.
        ArbolHuffman arbol = construirArbol(freq); 
        
		
		// Construir diccionario de búsqueda -> Pares (símbolo,código).
		// diccionarioCodigos será una estructura de tipo Map, Hashtable, String[], ...,
		// dependiendo de la implementación elegida.
        String [] diccionarioCodigos = new String[256];
        
        for(int i=0; i< diccionarioCodigos.length;i++) {
        	diccionarioCodigos[i] = new String();
        }        
        
		// Codificar la trama (char[]input) usando el diccionario de códigos.
        construirCodigos(diccionarioCodigos,arbol,"");
        codificar(input,diccionarioCodigos,filePathOut,arbol);
	}
    
    //Fin metodo comprimir
	
   /* 
    * Construir arbol de Huffman a partir de la tabla de frecuencias.
    * (Si se ha usado un arreglo int[] para albergar la tabla de frecuencias).
    */
    private ArbolHuffman construirArbol(int[] freq) {    
    	
        ///////////////////////TAREA1.2///////////////////////
        // Instanciar cola de prioridad (de tipo TreeSet, PriorityQueue o una 
    	// implementación propia).
        PriorityQueue<ArbolHuffman> cola = new PriorityQueue<ArbolHuffman>();
        //////////////////////////////////////////////////////
 
        
        
        
    	///////////////////////TAREA1.3///////////////////////
        // Inicializar la cola de prioridad con árboles simples (nodos hoja) para 
    	// cada símbolo de la tabla de frecuencias. Usar la estructura de datos 
    	// de tipo arbol binario que se facilita en los recursos de la práctica
    	// (ArbolHuffman.java).
        
        for (int i = 0; i < freq.length; i++) {
			if (freq[i] != 0) {
				//Se inicializa el arbol con el simbolo, su frecuencia, la rama izquierda y la rama derecha
				ArbolHuffman arbol = new ArbolHuffman((char) i, freq[i], null, null);
				cola.add(arbol);
			}
		}
        //////////////////////////////////////////////////////
    	
    	
        
        
    	///////////////////////TAREA1.4///////////////////////
    	// Construir el arbol de Huffman final/completo de manera iterativa 
    	// retirando de la cola de prioridad el par de nodos con menor frecuencia.
        
        ArbolHuffman arbolAux = null;
		while (cola.size() != 1) { //Si la cola tiene mas de un elemento entonces se crean dos arboles auxiliares
			//Cada arbol retira el par de nodos con menor frecuencia
			ArbolHuffman arbolAux2 = cola.poll();
			ArbolHuffman arbolAux3 = cola.poll();
			//Si la frecuencia del simbolo cogido por el primer arbol tiene mayor frecuencia que el escogido por el segundo
			//se crea un arbol auxiliar con el elemento a la derecha y si es menor a la izquierda.
			if (arbolAux2.getFrecuencia() > arbolAux3.getFrecuencia()) {
				//el arbolAux2 se mete en la derecha porque la frecuencia es mayor
				arbolAux = new ArbolHuffman('\0', arbolAux2.getFrecuencia() + arbolAux3.getFrecuencia(), arbolAux3, arbolAux2);

			} else {
				//El arbolAux3 se mete a la derecha porque la frecuencia es mayor
				arbolAux = new ArbolHuffman('\0', arbolAux2.getFrecuencia() + arbolAux3.getFrecuencia(), arbolAux2, arbolAux3);
			}
			cola.add(arbolAux);
		}
		
		return cola.poll();
        //////////////////////////////////////////////////////  
		
		
		
    	// Sustituir este objeto retornando el árbol de Huffman final 
    	// construido en la TAREA1.4
    	//return new ArbolHuffman(); 
    }
	
   /* 
    * Construir diccionario de búsqueda -> Pares (símbolo,código).
    * (Si se usa un arreglo String[] para albergar el diccionario de códigos).
    */
    private void construirCodigos(String [] diccionarioCodigos, ArbolHuffman arbol,String codigoCamino){
    	
    	///////////////////////TAREA1.5///////////////////////
        // Para hacer la codificación más rápida, construir un diccionario de búsqueda 
        // (String[], Map, Hashtable, ...) que permita obtener la codificación binaria  
        // de cada uno de los símbolos. Construir dicho diccionario/tabla requerirá recorrer 
        // el árbol de Huffman generado en la TAREA1.4. 
        // Para obtener la máxima calificación en esta tarea la tabla debe construirse 
        // recorriendo al árbol una sola vez.

    	//Los codigos se crean asi: izquierda (0) y derecha (1)
    	if(arbol.getIzquierdo() == null && arbol.getDerecho() == null) {
    		diccionarioCodigos[arbol.getSimbolo()] = codigoCamino;
    	
    	} else {
		if (arbol.getIzquierdo() != null) {
			construirCodigos(diccionarioCodigos, arbol.getIzquierdo(), codigoCamino + '0');
		}
		if (arbol.getDerecho() != null) {
			construirCodigos(diccionarioCodigos, arbol.getDerecho(), codigoCamino + '1');
		}
	}
    //////////////////////////////////////////////////////
    }
    
  
   /* 
    * Codificar la trama (char[]input) usando el diccionario de códigos y escribirla en el
    * archivo de salida cuyo path (String filePathOut) se facilita como argumento.
    * (Si se usa un arreglo String[] para albergar el diccionario de códigos).
    */
    private void codificar(char[] input, String [] diccionarioCodigos, String filePathOut, ArbolHuffman arbol){
    	
    	EscritorBinario escritor = new EscritorBinario(filePathOut);
    	
    	// Serializar árbol de Huffman para recuperarlo posteriormente en la descompresión.
        serializarArbol(arbol,escritor);
        
        // Escribir también el número de bytes del mensaje original (sin comprimir).
        escritor.escribirEntero(input.length);
        
    	
    	///////////////////////TAREA1.6///////////////////////
        // Codificación usando el diccionario de códigos y escritura en el archivo de salida. 
        
        for (int i = 0; i < input.length; i++) {
			String codificado = diccionarioCodigos[input[i]];
			//Para cada elemento de input vamos escribiendo en el fichero un valor u otro
			//dependiendo de si el valor leido corresponde con un 0(false) o un 1(true)
			for (int j = 0; j < codificado.length(); j++) {
				if (codificado.charAt(j) == '1') {
					escritor.escribirBit(true);
				} else {
					escritor.escribirBit(false);
				}
			}

		}
        //////////////////////////////////////////////////////
        
    	escritor.cerrarFlujo();
    }
    
    
   /* 
    * Serializar arbol de Huffman para recuperarlo posteriormente en la descompresión. Se
    * escribe en la parte inicial del archivo de salida.
    */
    private void serializarArbol(ArbolHuffman arbol, EscritorBinario escritor){
    	
    	if (arbol.esHoja()) {
    		escritor.escribirBit(true);
    		escritor.escribirPalabra(arbol.getSimbolo()); //Escribir palabra de 8bits
    		return;
    	}
    	escritor.escribirBit(false);
    	serializarArbol(arbol.getIzquierdo(),escritor);
    	serializarArbol(arbol.getDerecho(),escritor);
    }
    
    /*
    * Se lee el archivo de entrada (filePathIn, a descomprimir) como secuencia de bits 
    * usando LectorBinario, después se descodifica usando el árbol final de Huffman y el resultado 
    * se escribe con la clase EscritorBinario en el archivo de salida (filePathOut, descomprimido).
    */
    public void descomprimir(String filePathIn, String filePathOut) {
    
    	LectorBinario lector = new LectorBinario(filePathIn);
    	EscritorBinario escritor = new EscritorBinario(filePathOut);

    	ArbolHuffman arbol = leerArbol(lector);

    	// Númerod e bytes a escribir
    	int length = lector.leerEntero();

    	///////////////////////TAREA1.7///////////////////////
    	// Decodificar usando el árbol de Huffman.
    	
    	for (int i = 0; i < length; i++) {
    		ArbolHuffman x = arbol;
    		while (!x.esHoja()) {
    			boolean bit = lector.leerBit();
    			if (bit) x = x.getDerecho();
    			else     x = x.getIzquierdo();
    		}
    		escritor.escribirPalabra(x.getSimbolo());
    	}
    	//////////////////////////////////////////////////////

    	
    	escritor.cerrarFlujo();
		System.out.println("El archivo " + filePathIn + " se ha descomprimido correctamente.");

    }
    
    private ArbolHuffman leerArbol(LectorBinario lector) {
    	
    	boolean esHoja = lector.leerBit();
    	if (esHoja) {
    		char simbolo = lector.leerPalabra();
    		return new ArbolHuffman(simbolo, -1, null, null);
    	}
    	else {
    		return new ArbolHuffman('\0', -1, leerArbol(lector), leerArbol(lector));
    	}
    }

	public static void main(String[] args) {
		
		PlantillaCodificacionHuffman huffman = new PlantillaCodificacionHuffman();
		if(args.length==3){ // Control de argumentos mejorable!!
			if(args[0].equals("-c")){
				huffman.comprimir(args[1],args[2]);
			}else if (args[0].equals("-d")){
				huffman.descomprimir(args[1], args[2]);
			}
		}
	}

}
