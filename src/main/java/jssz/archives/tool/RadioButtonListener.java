package jssz.archives.tool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RadioButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(WordGeneratorForm.radioBtn01)){
            WordGeneratorForm.picCompressedSize = 200;
            System.out.println("pic compressed size: 200k");

        }else if(e.getSource().equals(WordGeneratorForm.radioBtn02)){
            WordGeneratorForm.picCompressedSize = 100;
            System.out.println("pic compressed size: 50k");
        }else{
            System.out.println("Please select pic compressed size");
        }
    }
}