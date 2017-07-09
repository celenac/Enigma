package com.Enigma; /**
 * @author: Celena Chang, 2017
 *
 * This program simulates the M4 com.Enigma.Enigma Machine. Taking in a file with a coded/decoded message,
 * it creates a new file that contains the decoded/coded version of the message.
 *
 * How to use:
 * *** The first line of the input file must contain the initial settings for the com.Enigma.Enigma machine.
 * *** Format: * (Umkehrwalzen reflector) (Zusatzwalzen reflector) (rotor 1) (rotor 2) (rotor 3) (initial_letters)
 * *** Umehrwalzen: B, C | Zusatzwalzen: BETA, GAMMA | Rotors 1-3: I, II, III, IV, V, VI, VI, VII, VIII
 * *** Example: * B BETA III IV I AXLE
 *
 * To code a message:
 * *** Write the initial com.Enigma.Enigma rotor settings as the first line of a file. Then insert the
 * *** original message into the file. Use this file as the "input file."
 * *** Creating an com.Enigma.Enigma object with this input file's path as the "input" argument and a separate
 * *** file path as the "output" argument will code the message into the "output" file.
 *
 * To decode the message:
 * *** Write the same initial com.Enigma.Enigma rotor settings used to code the original message on the
 * *** first line of a file. Then insert the coded original message into the file. Use this file
 * *** as the "input file."
 * *** Creating an com.Enigma.Enigma object with this input file's path as as the "input" argument and a
 * *** separate file path as the "output" argument will decode the message into the "output" file.
 */

import java.io.*;
import java.util.*;

public class Enigma {
    HashMap<String, String> rotor1, rotor2, rotor3, rotor4, rotor5;
    int wordLength = 0; //Decoded messages are split into word sections each of length of 5
    ArrayList<String> alphabetLetters;
    ArrayList<HashMap> rotorsArrayList;
    HashMap<String, HashMap> settingsHashMap;
    FileReader file;

    public static void main(String[] args) throws IOException {
        //Example
        String input = "C:\\Users\\Celena\\Documents\\GitHub\\com.Enigma.Enigma\\src\\inputFile.txt";
        String output = "C:\\Users\\Celena\\Documents\\GitHub\\com.Enigma.Enigma\\src\\outputFile.txt";
        startEnigma(output, input); //Decodes the message into the specified OUTPUT path
    }

    public Enigma(FileReader f) {
        file = f;
        initializeSettings();
    }

    /**
     * Creates a file with the decoded message.
     * @param outputFilePath the path of the file the message will be outputted to
     * @param inputFilePath the path of the file that contains the original message (the input message)
     */
    public static void startEnigma(String outputFilePath, String inputFilePath) {
        FileReader input = null;
        try {
            input = new FileReader(inputFilePath);
            Enigma machine = new Enigma(input);

            File output = new File(outputFilePath); //File OUTPUT should be empty
            output.getParentFile().mkdirs();
            output = machine.decodeTo(output); //File OUTPUT should now have the decoded message
            printDecodedMessage(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the message from the file OUTPUT
     * @param output the file that contains the decoded message
     */
    public static void printDecodedMessage(File output) {
        FileReader outputReader = null;
        try {
            outputReader = new FileReader(output);
            BufferedReader testReader = new BufferedReader(outputReader);
            int element;
            while ((element = testReader.read()) != -1) {
                System.out.print((char) element);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates all settings for the M4 com.Enigma.Enigma Machine
     */
    public void initializeSettings() {
        alphabetLetters = new ArrayList<String>(Arrays.asList("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("")));

        HashMap<String, String> rotorI, rotorII, rotorIII, rotorIV, rotorV, rotorVI, rotorVII, rotorVIII, rotorBeta, rotorGamma, reflectorB, reflectorC;
        rotorI = new HashMap<String, String>(); rotorI.put("ID", "rotorI");
        rotorII = new HashMap<String, String>(); rotorII.put("ID", "rotorII");
        rotorIII = new HashMap<String, String>(); rotorIII.put("ID", "rotorIII");
        rotorIV = new HashMap<String, String>(); rotorIV.put("ID", "rotorIV");
        rotorV = new HashMap<String, String>(); rotorV.put("ID", "rotorV");
        rotorVI = new HashMap<String, String>(); rotorVI.put("ID", "rotorVI");
        rotorVII = new HashMap<String, String>(); rotorVII.put("ID", "rotorVII");
        rotorVIII = new HashMap<String, String>(); rotorVIII.put("ID", "rotorVIII");
        rotorBeta = new HashMap<String, String>(); rotorBeta.put("ID", "rotorBeta");
        rotorGamma = new HashMap<String, String>(); rotorGamma.put("ID", "rotorGamma");
        reflectorB = new HashMap<String, String>(); reflectorB.put("ID", "reflectorB");
        reflectorC = new HashMap<String, String>(); reflectorC.put("ID", "reflectorC");

        HashMap<String, String[]> rotorLetterMatching = new HashMap<String, String[]>();
        rotorLetterMatching.put("rotorI", "EKMFLGDQVZNTOWYHXUSPAIBRCJ".split(""));
        rotorLetterMatching.put("rotorII", "AJDKSIRUXBLHWTMCQGZNPYFVOE".split(""));
        rotorLetterMatching.put("rotorIII", "BDFHJLCPRTXVZNYEIWGAKMUSQO".split(""));
        rotorLetterMatching.put("rotorIV", "ESOVPZJAYQUIRHXLNFTGKDCMWB".split(""));
        rotorLetterMatching.put("rotorV", "VZBRGITYUPSDNHLXAWMJQOFECK".split(""));
        rotorLetterMatching.put("rotorVI", "JPGVOUMFYQBENHZRDKASXLICTW".split(""));
        rotorLetterMatching.put("rotorVII", "NZJHGRCXMYSWBOUFAIVLPEKQDT".split(""));
        rotorLetterMatching.put("rotorVIII", "FKQHTLXOCBJSPDZRAMEWNIUYGV".split(""));
        rotorLetterMatching.put("rotorBeta", "LEYJVCNIXWPBQMDRTAKZGFUHOS".split(""));
        rotorLetterMatching.put("rotorGamma", "FSOKANUERHMBTIYCWLQPZXVGJD".split(""));
        rotorLetterMatching.put("reflectorB", "ENKQAUYWJICOPBLMDXZVFTHRGS".split(""));
        rotorLetterMatching.put("reflectorC", "RDOBJNTKVEHMLFCWZAXGYIPSUQ".split(""));

        HashMap[] rotorsArray = {rotorI, rotorII, rotorIII, rotorIV, rotorV, rotorVI, rotorVII, rotorVIII, rotorBeta, rotorGamma, reflectorB, reflectorC};
        for (HashMap rotor : rotorsArray) {
            for (int i = 0; i < 26; i++) {
                rotor.put(alphabetLetters.get(i), rotorLetterMatching.get(rotor.get("ID"))[i]);
            }
        }
        rotorI.put("notch1", "Q");
        rotorII.put("notch1", "E");
        rotorIII.put("notch1", "V");
        rotorIV.put("notch1", "J");
        rotorV.put("notch1", "Z");
        for (int i = 5; i < 8; i++) { //notches for rotors 6-8
            rotorsArray[i].put("notch1", "Z");
            rotorsArray[i].put("notch2", "M");
        }

        settingsHashMap = new HashMap<String, HashMap>();
        settingsHashMap.put("B", reflectorB); settingsHashMap.put("C", reflectorC);
        settingsHashMap.put("BETA", rotorBeta); settingsHashMap.put("GAMMA", rotorGamma);
        settingsHashMap.put("I", rotorI); settingsHashMap.put("II", rotorII); settingsHashMap.put("III", rotorIII);
        settingsHashMap.put("IV", rotorIV); settingsHashMap.put("V", rotorV); settingsHashMap.put("VI", rotorVI);
        settingsHashMap.put("VII", rotorVII); settingsHashMap.put("VIII", rotorVIII);
    }

    /**
     * Analyzes the first line of the input file to set the initial settings for the com.Enigma.Enigma machine. Inputs the decoded message to OUTPUTfILE.
     * @param outputFile the file that contains the decoded message
     * @return the outputFile with decoded message in it
     * @throws IOException
     */
    public File decodeTo(File outputFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(file);

        FileWriter fw = new FileWriter(outputFile);
        BufferedWriter bw = new BufferedWriter(fw);
        int element;
        while ((element = bufferedReader.read()) != -1) {
            if (Character.toString((char) element).equals("*")) { //converting element to char and then String
                String line = bufferedReader.readLine();
                setDecodingSetting(line);
            } else {
                if (wordLength >= 5) {
                    bw.write(" ");
                    wordLength = 0;
                }
                if (Character.isLetter(element)) {
                    bw.write(decodeLetter((Character.toString((char) element)).toUpperCase()));
                    wordLength++;
                } else if (element == 10) { //if element is a line feed (LF)
                    bw.write("\n");
                    wordLength = 0;
                } else if (element == 13) { //if element is a carriage return (CR)
                    bw.write("\r");
                    wordLength = 0;
                } else {
                    bw.write("");
                }
            }
        }
        bw.close();
        bufferedReader.close();
        file.close();
        return outputFile;
    }

    /**
     * Establishes the initial settings for this Enimga machine object (initial settings should be written as the first line of the input file)
     * @param line the line that contains the initial settings
     */
    public void setDecodingSetting(String line) {
        ArrayList<String> initialSettingsArrayList = new ArrayList<String>(Arrays.asList(line.split(" ")));
        initialSettingsArrayList.removeAll(Arrays.asList("", null));
        rotor1 = settingsHashMap.get(initialSettingsArrayList.get(0));
        rotor2 = settingsHashMap.get(initialSettingsArrayList.get(1));
        rotor3 = settingsHashMap.get(initialSettingsArrayList.get(2));
        rotor4 = settingsHashMap.get(initialSettingsArrayList.get(3));
        rotor5 = settingsHashMap.get(initialSettingsArrayList.get(4));

        rotorsArrayList = new ArrayList<HashMap>(Arrays.asList(rotor5, rotor4, rotor3, rotor2, rotor1));

        String[] startingLetters = initialSettingsArrayList.get(5).split("");
        ArrayList<HashMap> rotors2345 = new ArrayList<HashMap>(Arrays.asList(rotor2, rotor3, rotor4, rotor5));
        for (int i = 0; i < 4; i++) {
            rotors2345.get(i).put("currentLetter", startingLetters[i]);
        }
    }

    public void turnRotor(HashMap rotor) {
        if (!(rotor.containsKey("notch2"))) {
            if (rotor.get("currentLetter").equals(rotor.get("notch1"))) {
                //turn the next rotor (the rotor on the left of current rotor
                HashMap nextRotor = rotorsArrayList.get(rotorsArrayList.indexOf(rotor) + 1);
                turnRotor(nextRotor);
            }
        }
        //turn current rotor by 1:
        int currentLetterNum = alphabetLetters.indexOf(rotor.get("currentLetter"));
        if (currentLetterNum == 25) {
            rotor.put("currentLetter", alphabetLetters.get(0));
        } else {
            rotor.put("currentLetter", alphabetLetters.get(currentLetterNum + 1));
        }
    }

    public String decodeLetter(String letter) {
        HashMap[] rotorOrderForDecoding = {rotor5, rotor4, rotor3, rotor2, rotor1, rotor2, rotor3, rotor4, rotor5};
        String decodingLetter = letter; //the letter in the process of decoding as it goes through all the rotors
        boolean goingLeft = true;
        turnRotor(rotor5);
        for (HashMap rotor : rotorOrderForDecoding) { //loop through starting from rotor4 to rotor3
            if (rotor.equals(rotor1)) { //rotor1
                goingLeft = false;
                decodingLetter = (String) (rotor.get(decodingLetter));
            } else if (rotor.equals(rotor2)) { //rotor2
                if (goingLeft) {
                    decodingLetter = (String) (rotor.get(decodingLetter));
                } else {
                    decodingLetter = (String) (getReversedRotorValue(rotor, decodingLetter));
                }
            } else { //rotors 3-5
                int inputNum = alphabetLetters.indexOf(decodingLetter);
                int settingNum = alphabetLetters.indexOf(rotor.get("currentLetter"));
                int newInputNum = (inputNum + settingNum) % 26;
                String newOutputLetter;

                if (goingLeft) {
                    newOutputLetter = (String) (rotor.get(alphabetLetters.get(newInputNum)));
                } else {
                    newOutputLetter = (String) (getReversedRotorValue(rotor, alphabetLetters.get(newInputNum)));
                }

                int outputLetterNum = alphabetLetters.indexOf(newOutputLetter);
                int outputSettingNum = alphabetLetters.indexOf(rotor.get("currentLetter"));
                int newOutputNum;

                if (outputLetterNum - outputSettingNum < 0) { //re-adjusting negative output numbers
                    newOutputNum = (26 + (outputLetterNum - outputSettingNum)) % 26;
                } else {
                    newOutputNum = (outputLetterNum - outputSettingNum) % 26;
                }

                decodingLetter = alphabetLetters.get(newOutputNum);
            }
        }
        return decodingLetter;
    }

    public Object getReversedRotorValue(HashMap hm, Object value) {
        Object result = null;
        for (Object obj : hm.keySet()) {
            if (hm.get(obj).equals(value) && (((String) obj).length() == 1)) {
                result = obj;
            }
        }
        return result;
    }
}
