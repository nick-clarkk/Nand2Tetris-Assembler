/*******************************************
 * AUTHOR: 		Nicholas Clark
 * COURSE:		CS 220 | MiraCosta College
 * DATE: 		4/18/2023
 *******************************************/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
    public static final char NO_COMMAND = 'N';
    public static final char A_COMMAND = 'A';
    public static final char C_COMMAND = 'C';
    public static final char L_COMMAND = 'L';

    private Scanner inputFile;
    private int lineNumber;
    private String rawLine;
    private String cleanLine;
    private char commandType;
    private String symbol;
    private String destMnemonic;
    private String compMnemonic;
    private String jumpMnemonic;

    //DESCRIPTION: opens input file/stream and prepares to parse
    //PRECONDITION: provided file is ASM file
    //POSTCONDITION: if file can’t be opened, ends program w/ error message
    public Parser(String fileName) {
        try {
            inputFile = new Scanner(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        lineNumber = 0;
    }

    //DESCRIPTION: getter for command type
    //PRECONDITION: cleanLine has been parsed (advance was called)
    //POSTCONDITION: returns Command for type (N/A/C/L)
    public char getCommandType() {
        return commandType;
    }

    //DESCRIPTION: getter for lineNumber (debugging)
    //PRECONDITION: n/a
    //POSTCONDITION: returns line number currently being processed from file
    public int getLineNumber() {
        return lineNumber;
    }

    //DESCRIPTION: getter for rawLine from file (debugging)
    //PRECONDITION: advance() was called to put value from file in here
    //POSTCONDITION: returns string of current original line from file
    public String getRawLine() {
        return rawLine;
    }

    //DESCRIPTION: getter for cleanLine from file (debugging)
    //PRECONDITION: advance() and cleanLine() were called
    //POSTCONDITION: returns string of current clean instruction from file
    public String getCleanLine() {
        return cleanLine;
    }

    //DESCRIPTION: getter for symbol name
    //PRECONDITION: cleanLine has been parsed (advance was called), call for labels only (use getCommandType())
    //POSTCONDITION: returns string for symbol name
    public String getSymbol() {
        return symbol;
    }

    //DESCRIPTION: getter for dest part of C-instruction
    //PRECONDITION: cleanLine has been parsed (advance was called), call for C-instructions only (use getCommandType())
    //POSTCONDITION: returns mnemonic (ASM symbol) for dest part
    public String getDest() {
        return destMnemonic;
    }

    //DESCRIPTION: getter for comp part of C-instruction
    //PRECONDITION: cleanLine has been parsed (advance was called), call for C-instructions only (use getCommandType())
    //POSTCONDITION: returns mnemonic (ASM symbol) for comp part
    public String getComp() {
        return compMnemonic;
    }

    //DESCRIPTION: getter for jump part of C-instruction
    //PRECONDITION: cleanLine has been parsed (advance was called), call for C-instructions only (use getCommandType())
    //POSTCONDITION: returns mnemonic (ASM symbol) for jump part
    public String getJump() {
        return jumpMnemonic;
    }

    //DESCRIPTION: returns boolean if more commands left, closes stream if not
    //PRECONDITION: file stream is open
    //POSTCONDITION: returns true if more commands, else closes stream
    public boolean hasMoreCommands() {
        if(inputFile.hasNextLine())
            return true;
        else {
            inputFile.close();
            return false;
        }
    }

    //DESCRIPTION: reads next line from file and parses it into instance vars
    //PRECONDITION: file stream is open, called only if hasMoreCommands()
    //POSTCONDITION: current instruction parts put into instance vars
    public void advance() {
        if(hasMoreCommands()) {
            rawLine = inputFile.nextLine();
            cleanLine(getRawLine());
            parseCommandType();
            parse();
        }
        if(commandType != 'N' && commandType != 'L')
            lineNumber++;
    }

    //DESCRIPTION: parses symbol for A- or L-commands
    //PRECONDITION: advance() called so cleanLine has value, call for A- and L-commands only
    //POSTCONDITION: symbol has appropriate value from instruction assigned
    private void parseSymbol() {
        String line = getCleanLine();

        if(commandType == A_COMMAND)
            symbol = line.replaceAll("@", "");
        else if(commandType == L_COMMAND) {
            line = line.replaceAll("\\(", "");
            line = line.replaceAll("\\)", "");
            symbol = line;
        }
    }

    //DESCRIPTION: helper method parses line to get comp part
    //PRECONDITION: advance() called so cleanLine has value, call for C-instructions only
    //POSTCONDITION: compMnemonic set to appropriate value from instruction
    private void parseComp() {
        if(getCleanLine().contains("="))
            if(getCleanLine().contains(";"))
                compMnemonic = getCleanLine().substring((getCleanLine().indexOf('=')) + 1, getCleanLine().indexOf(';'));
            else
                compMnemonic = getCleanLine().substring(getCleanLine().indexOf('=')+1);
        else if(getCleanLine().contains(";"))
            compMnemonic = getCleanLine().substring(0, getCleanLine().indexOf(';'));
    }

    //DESCRIPTION: helper method parses line to get dest part
    //PRECONDITION: advance() called so cleanLine has value,
    //call for C-instructions only

    private void parseDest() {
        String line = getCleanLine();

        if(commandType == C_COMMAND)
        {
            if(line.contains("="))
                destMnemonic = line.substring(0, line.indexOf('='));
            else
                destMnemonic = null;
        }
    }

    //DESCRIPTION: helper method parses line to get jump part
    //PRECONDITION: advance() called so cleanLine has value, call for C-instructions only
    //POSTCONDITION: jumpMnemonic set to appropriate value from instruction
    private void parseJump() {
        if(commandType == C_COMMAND)
        {
            if(getCleanLine().contains(";"))
                jumpMnemonic = getCleanLine().substring((getCleanLine().indexOf(";")) + 1);
            else
                jumpMnemonic = null;
        }
    }



    //Helper methods

    //DESCRIPTION: cleans raw instruction by removing non-essential parts
    //PRECONDITION: String parameter given (not null)
    //POSTCONDITION: returned without comments and whitespace
    private String cleanLine(String line) {
        if(line != null && line.contains("//")) {
            line = line.substring(0, line.indexOf('/')).trim();
            cleanLine = line.replace("\t", "");
        }
        else {
            line = line.trim();
            cleanLine = line;
        }
        return cleanLine;
    }

    //DESCRIPTION: determines command type from parameter
    //PRECONDITION: String parameter is clean instruction
    //POSTCONDITION: returns A_COMMAND (A-instruction), C_COMMAND (C-instruction), L_COMMAND (Label) or NO_COMMAND (no command)
    private void parseCommandType() {
        if(getCleanLine().contains("@"))
            commandType = A_COMMAND;
        else if(getCleanLine().contains("(") && getCleanLine().contains(")"))
            commandType = L_COMMAND;
        else if(getCleanLine().contains(";") || getCleanLine().contains("="))
            commandType = C_COMMAND;
        else
            commandType = NO_COMMAND;
    }

    //DESCRIPTION: helper method parses line depending on instruction type
    //PRECONDITION: advance() called so cleanLine has value
    //POSTCONDITION: appropriate parts (instance vars) of instruction filled
    private void parse() {
        if(commandType == A_COMMAND || commandType == L_COMMAND)
            this.parseSymbol();
        else if(commandType == C_COMMAND) {
            this.parseDest();
            this.parseComp();
            this.parseJump();
        }
    }
}
