package utilities;

public class HashingFunctions {
    
     // Example of hash1 function
    public static long hash1(int p1, int p2, int p3, int p4, int fuzzFactor) {
        
        return (p4 - (p4 % fuzzFactor)) * 100000000
                + (p3 - (p3 % fuzzFactor)) * 100000
                + (p2 - (p2 % fuzzFactor)) * 100
                + (p1 - (p1 % fuzzFactor));
    }
}