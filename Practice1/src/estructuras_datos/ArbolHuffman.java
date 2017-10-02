package estructuras_datos;

/***************************************************************************************
 *  
 *  ArbolHuffman: Estructura de datos recursiva para representar un árbol binario
 *  de Huffman. 
 *  
 *
 **************************************************************************************/

public class ArbolHuffman implements Comparable<ArbolHuffman> {
	
	private final char simbolo;
	private final int frecuencia;
	private final ArbolHuffman izquierdo, derecho;

	/**  
	 * Constructor.
	 */
	public ArbolHuffman(char simbolo, int frecuencia, ArbolHuffman izquierdo, ArbolHuffman derecho) {
		this.simbolo    = simbolo;
		this.frecuencia  = frecuencia;
		this.izquierdo  = izquierdo;
		this.derecho = derecho;
	}
	
	public ArbolHuffman(){ // Árbol vacío
		this('\0',0,null,null);
	}

	/**
	 * Si el árbol actual no contiene subárboles derecho e izquierdo.
	 * 
     * @return true si y solo es un nodo hoja
     */
	public boolean esHoja() {
		return (izquierdo == null) && (derecho == null);
	}

	/**
	 * Permite comparar árboles de Huffman, según frecuencia de aparición en la trama a codificar. 
	 * Necesario para la estructura cola de prioridad donde se albergan los árboles de Huffman 
	 * durante su construcción.
	 * 
     * @return >0 si la frecuencia de este árbol(this) es mayor que la del segundo (otro);
     * 		   =0 si tienen la misma frecuencia;
     * 		   <0 si si la frecuencia de este árbol(this) es menor que la del segundo (otro);
     */
	public int compareTo(ArbolHuffman otro) {
		return this.frecuencia - otro.frecuencia;
	}
	
	// Getters:
	/**
	 * @return simbolo
	 */
	public char getSimbolo() {
		return simbolo;
	}

	/**
	 * @return frecuencia
	 */
	public int getFrecuencia() {
		return frecuencia;
	}
	
	
	/**
	 * @return subárbol izquierdo
	 */
	public ArbolHuffman getIzquierdo() {
		return izquierdo;
	}

	/**
	 * @return subárbol derecho
	 */
	public ArbolHuffman getDerecho() {
		return derecho;
	}
	
	@Override
	public String toString() {
		return toStringArbol(this, 0);
	}

	private String toStringArbol(ArbolHuffman arbol, int sangria) {
		if (arbol == null) {
			return "";
		}
		String indentacion = "";
		for (int i = 0; i < sangria; i++) {
			indentacion += " ";
		}
		if (arbol.esHoja()) {
			String simbolo = (arbol.simbolo=='\n') ? "\\n" : ""+arbol.simbolo;
			return "[" + simbolo + "]\n";
		}else {
			String result = "(*)\n";
			result += indentacion + "Izq: " +
					toStringArbol(arbol.izquierdo, sangria + 4);
			result += indentacion + "Der: " +
					toStringArbol(arbol.derecho, sangria + 4);
			return result;
		}
	}
}
