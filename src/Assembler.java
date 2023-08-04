/*******************************************
 * AUTHOR: 		Nicholas Clark
 * COURSE:		CS 220 | MiraCosta College
 * DATE: 		4/18/2023
 *******************************************/

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Assembler {
    public static void main(String[] args) {
        String inputFileName, outputFileName;
        PrintWriter outputFile = null;
        SymbolTable symbolTable;

        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter a .asm file >> ");
        inputFileName = keyboard.nextLine();

        keyboard.close();

        outputFileName = inputFileName.substring(0, inputFileName.lastIndexOf('.')) + ".hack";

        try {
            outputFile = new PrintWriter(new FileOutputStream(outputFileName));
        } catch (FileNotFoundException e) {
            System.err.println(outputFileName + " not found.");
            System.exit(0);
        }

        symbolTable = new SymbolTable();

        firstPass(inputFileName, symbolTable);
        System.out.println("First pass complete.");
        secondPass(inputFileName, symbolTable, outputFile);
        System.out.println("Second pass complete.");

        outputFile.close();
    }

    private static void firstPass(String inputFile, SymbolTable symbolTable) {
        Parser parse = new Parser(inputFile);
        String symbol;
        int romAddress = 0;

        while(parse.hasMoreCommands()) {
            parse.advance();
            if(parse.getCommandType() == 'A' || parse.getCommandType() == 'C')
                romAddress++;

            if(parse.getCommandType() == 'L') {
                symbol = parse.getSymbol();
                symbolTable.addEntry(symbol, romAddress);
            }
        }
    }

    private static void secondPass(String inputFile, SymbolTable symbolTable, PrintWriter outputFile) {
        Parser parse = new Parser(inputFile);
        Code codeTable = new Code();
        String output;
        int ramAddress = 16;

        while(parse.hasMoreCommands()) {
            parse.advance();
            if(parse.getCommandType() == 'A') {

                String numbers = "0123456789";
                String symbol;
                int number = 0;

                symbol = parse.getSymbol();

                if(numbers.indexOf(symbol.charAt(0)) != -1) {
                    try {
                        number = Integer.parseInt(symbol);
                    } catch(NumberFormatException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Error on line " + parse.getLineNumber());
                        System.exit(0);
                    }
                    output = "0" + Code.decimalToBinary(number);
                    outputFile.println(output);
                    System.out.println(output);
                }
                else {
                    if(symbolTable.contains(symbol)) {
                        number = symbolTable.getAddress(symbol);
                        output = "0" + Code.decimalToBinary(number);
                        outputFile.println(output);
                        System.out.println(output);

                    }
                    else if(!symbolTable.addEntry(symbol, ramAddress)){
                        System.out.println("Syntax error on line " + parse.getLineNumber());
                        System.exit(0);
                    }
                    else {
                        output = "0" + Code.decimalToBinary(ramAddress);
                        outputFile.println(output);
                        System.out.println(output);
                        ramAddress++;
                    }
                }

            }
            else if(parse.getCommandType() == 'C') {
                if(codeTable.getComp(parse.getComp()) == null) {
                    System.out.println("Missing computation on line " + parse.getLineNumber());
                    System.exit(0);
                }
                else {
                    output = "111" + codeTable.getComp(parse.getComp()) + codeTable.getDest(parse.getDest()) + codeTable.getJump(parse.getJump());
                    System.out.println(output);
                    outputFile.println(output);
                }
            }
        }
    }
}
