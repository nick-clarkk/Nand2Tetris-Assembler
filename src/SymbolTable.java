/*******************************************
 * AUTHOR: 		Nicholas Clark
 * COURSE:		CS 220 | MiraCosta College
 * DATE: 		4/18/2023
 *******************************************/

import java.util.HashMap;

public class SymbolTable {
    private static final String INITIAL_VALID_CHARS = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm_.$:";
    private static final String ALL_VALID_CHARS = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm_.$:0123456789";

    private HashMap<String, String> symbolTable;

    //DESCRIPTION: initializes hashmap with predefined symbols
    //PRECONDITION: follows symbols/values from book/appendix
    //POSTCONDITION: all hashmap values have valid address integer
    public SymbolTable() {
        symbolTable = new HashMap<>();
        symbolTable.put("R0", "0");
        symbolTable.put("R1", "1");
        symbolTable.put("R2", "2");
        symbolTable.put("R3", "3");
        symbolTable.put("R4", "4");
        symbolTable.put("R5", "5");
        symbolTable.put("R6", "6");
        symbolTable.put("R7", "7");
        symbolTable.put("R8", "8");
        symbolTable.put("R9", "9");
        symbolTable.put("R10", "10");
        symbolTable.put("R11", "11");
        symbolTable.put("R12", "12");
        symbolTable.put("R13", "13");
        symbolTable.put("R14", "14");
        symbolTable.put("R15", "15");

        symbolTable.put("SCREEN", "16384");
        symbolTable.put("KBD", "24576");
        symbolTable.put("SP", "0");
        symbolTable.put("LCL", "1");
        symbolTable.put("ARG", "2");
        symbolTable.put("THIS", "3");
        symbolTable.put("THAT", "4");
        symbolTable.put("WRITE", "18");
        symbolTable.put("END", "22");
    }

    //DESCRIPTION: adds a new pair of symbols/addresses to hashmap
    //PRECONDITION: symbol/address pair not in hashmap (check contains() 1st)
    //POSTCONDITION: adds pair, returns true if added, false if illegal name
    public boolean addEntry(String key, int address) {
        if(this.contains(key))
            return false;
        else if(!isValidName(key))
            return false;
        //Accidentally made second parameter a String so had to concatenate address with empty string
        symbolTable.put(key, "" + address);
        return true;
    }

    //DESCRIPTION: returns boolean of whether hashmap has symbol or not
    //PRECONDITION: table has been initialized
    //POSTCONDITION: returns boolean if arg is in table or not
    public boolean contains(String key) {
        return symbolTable.containsKey(key);
    }

    //DESCRIPTION: returns address in hashmap of given symbol
    //PRECONDITION: symbol is in hashmap (check w/ contains() first)
    //POSTCONDITION: returns address associated with symbol in hashmap
    public int getAddress(String val) {
        return Integer.parseInt(symbolTable.get(val));
    }

    //DESCRIPTION: checks validity of identifiers for assembly code symbols
    //PRECONDITION: start with letters or “_.$:” only, numbers allowed after
    //POSTCONDITION: returns true if valid identifier, false otherwise
    public static boolean isValidName(String symbol) {

        boolean valid = false; //had to create this local var due to java complaining about never using i in for loop
        if(INITIAL_VALID_CHARS.contains(Character.toString(symbol.charAt(0)))) {
            for(int i = 1; i < symbol.length(); i++) {
                if(ALL_VALID_CHARS.contains(Character.toString(symbol.charAt(i))))
                    valid = true;
                else {
                    System.out.println("Invalid symbol name" + symbol.charAt(i));
                    return false;
                }
            }
        }
        else {
            System.out.println("Invalid symbol name" + symbol.charAt(0));
            return false;
        }
        return valid;

    }
}
