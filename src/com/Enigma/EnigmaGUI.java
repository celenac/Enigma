package com.Enigma;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnigmaGUI{
    private JButton button1;
    private JPanel panel1;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextField inputTextField;
    private JTextField outputTextField;
    private JTextField fileSelectedTextField;
    private JTextField titleTextField;
    private JTextPane descriptionTextPane;
    private JTextPane rotorsTextPane;
    private JTextPane inputTextPane;
    private JTextPane outputTextPane;

    public void displayTextFileInTextBox(File file, JTextPane textArea){
        BufferedReader br = null;
        try{
            String result="";
             br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while(line != null) {
                result+=line + "\n";
                line = br.readLine();
            }
            textArea.setText(result);
        }catch(Exception exception){
            exception.printStackTrace();
        }finally{
            try{
                br.close();
            }catch(IOException ioexception){
                Logger.getLogger(EnigmaGUI.class.getName()).log(Level.SEVERE, null, ioexception);
            }
        }
    }

    public EnigmaGUI() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(null); //replace null with your swing container
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    File selectedFile = fc.getSelectedFile();
                    String selectedFileParentPath = selectedFile.getParent();
                    fileSelectedTextField.setText("File selected: " + selectedFile.getName());
                    inputTextPane.setText("");
                    displayTextFileInTextBox(selectedFile, inputTextPane);

                    File outputFile = new File(selectedFileParentPath + File.separator + "enigmaOutput.txt");
                    Enigma.startEnigma(outputFile.getPath(), selectedFile.getPath()); //decoding or encoding input text
                    outputTextPane.setText("");
                    displayTextFileInTextBox(outputFile, outputTextPane);
                }
            }
        });
        
        inputTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        outputTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        inputTextPane.setBorder(javax.swing.BorderFactory.createLineBorder(Color.DARK_GRAY));
        outputTextPane.setBorder(javax.swing.BorderFactory.createLineBorder(Color.DARK_GRAY));
        fileSelectedTextField.setText("File selected: ");
        fileSelectedTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        titleTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        displayTextFileInTextBox(new File("instructions.txt"), descriptionTextPane);
        displayTextFileInTextBox(new File("rotors_reflectors.txt"), rotorsTextPane);

        //Centering the instructions text
        SimpleAttributeSet centerText = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerText, StyleConstants.ALIGN_CENTER);
        StyledDocument description = descriptionTextPane.getStyledDocument();;
        description.setParagraphAttributes(0, descriptionTextPane.getText().length(), centerText, false);
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("com.Enigma.Enigma App");
        frame.setContentPane(new EnigmaGUI().panel1); //new NameOfYourClass().nameOfYourPanel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
