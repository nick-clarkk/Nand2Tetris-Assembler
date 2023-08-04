/*******************************************
 * AUTHOR:      Nicholas Clark
 * COURSE:		CS 220 | MiraCosta College
 * DATE: 		4/18/2023
 *******************************************/

import java.util.HashMap;

public class Code {
    private HashMap<String, String> compCodes;
    private HashMap<String, String> destCodes;
    private HashMap<String, String> jumpCodes;

    //DESCRIPTION: initializes hashmaps with binary codes for easy lookup
    //PRECONDITION: comp codes = 7 bits (includes a), dest/jump codes = 3 bits
    //POSTCONDITION: all hashmaps have lookups for valid codes
    public Code() {
        compCodes = new HashMap<>();
        destCodes = new HashMap<>();
        jumpCodes = new HashMap<>();

        //Comp codes:

        //a bit = 0
        compCodes.put("0", "0101010");
        compCodes.put("1", "0111111");
        compCodes.put("-1", "0111010");
        compCodes.put("D", "0001100");
        compCodes.put("A", "0110000");
        compCodes.put("!D", "0001101");
        compCodes.put("!A", "0110001");
        compCodes.put("-D", "0001111");
        compCodes.put("-A", "0110011");
        compCodes.put("D+1", "0011111");
        compCodes.put("A+1", "0110111");
        compCodes.put("D-1", "0001110");
        compCodes.put("A-1", "0110010");
        compCodes.put("D+A", "0000010");
        compCodes.put("D-A", "0010011");
        compCodes.put("A-D", "0000111");
        compCodes.put("D&A", "0000000");
        compCodes.put("D|A", "0010101");

        //a bit = 1
        compCodes.put("M", "1110000");
        compCodes.put("!M", "1110001");
        compCodes.put("-M", "1110011");
        compCodes.put("M+1", "1110111");
        compCodes.put("M-1", "1110010");
        compCodes.put("D+M", "1000010");
        compCodes.put("D-M", "1010011");
        compCodes.put("M-D", "1000111");
        compCodes.put("D&M", "1000000");
        compCodes.put("D|M", "1010101");

        //Dest codes
        destCodes.put("null", "000");
        destCodes.put(null, "000");
        destCodes.put("M", "001");
        destCodes.put("D", "010");
        destCodes.put("MD", "011");
        destCodes.put("A", "100");
        destCodes.put("AM", "101");
        destCodes.put("AD", "110");
        destCodes.put("AMD", "111");

        //Jump codes
        jumpCodes.put("JGT", "001");
        jumpCodes.put("JEQ", "010");
        jumpCodes.put("JGE", "011");
        jumpCodes.put("JLT", "100");
        jumpCodes.put("JLE", "110");
        jumpCodes.put("JNE", "101");
        jumpCodes.put("JMP", "111");
        jumpCodes.put("null", "000");
        jumpCodes.put(null, "000");
    }

    //DESCRIPTION: converts to string of bits (7) for given mnemonic
    //PRECONDITION: hashmaps are built with valid values
    //POSTCONDITION: returns string of bits if valid, else returns null
    public String getComp(String mnemonic) {
        return compCodes.get(mnemonic);
    }

    //DESCRIPTION: converts to string of bits (3) for given mnemonic
    //PRECONDITION: hashmaps are built with valid values
    //POSTCONDITION: returns string of bits if valid, else returns null
    public String getDest(String mnemonic) {
        return destCodes.get(mnemonic);
    }

    //DESCRIPTION: converts to string of bits (3) for given mnemonic
    //PRECONDITION: hashmaps are built with valid values
    //POSTCONDITION: returns string of bits if valid, else returns null
    public String getJump(String mnemonic) {
        return jumpCodes.get(mnemonic);
    }

    //DESCRIPTION: converts integer from decimal notation to binary notation
    //PRECONDITION: number is valid size for architecture, non-negative
    //POSTCONDITION: returns 16-bit string of binary digits (first char is MSB)
    public static String decimalToBinary(int num) {
        StringBuilder binary = new StringBuilder();
        binary.append(Integer.toBinaryString(num));
        for(int i = binary.length(); i < 15; i++)
            binary.insert(0, "0");
        return binary.toString();
    }




}
