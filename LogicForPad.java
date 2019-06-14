package CleanNotePad;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.Position;
import javax.swing.text.StyledDocument;
//This class holds all the business logic that the not pad needs to run
public class LogicForPad extends JFrame{
	//Array to hold the five most recent files
	ArrayList<File> recentFiles=new ArrayList<File>();
	
	//Wipes the current file clean,lazy new file implementation
    public void newFile(JTextPane fileContent) {
    	fileContent.setText("");
    }
    
  //Copys selected text
    public void copyContent(JTextPane fileContent) {
    	fileContent.copy();
    }
    
  //Pastes whatever is currently in the clip board to the file
    public void paste(JTextPane fileContent) {
    	StyledDocument doc = fileContent.getStyledDocument();
        Position position = doc.getEndPosition();
        fileContent.paste();
    }
    
  //Save the current file
    public void saveFile(JTextPane fileContent) {
    	File fileToWrite = null;
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION)
            fileToWrite = fc.getSelectedFile();
        try {
            PrintWriter out = new PrintWriter(new FileWriter(fileToWrite));
            out.println(fileContent.getText());
            JOptionPane.showMessageDialog(null, "File is saved successfully...");
            out.close();
        } catch (IOException ex) {
        }
    }
    
    //Prints the current File
    public void printFile(JTextPane fileContent) {
    	try{
            PrinterJob pjob = PrinterJob.getPrinterJob();
            pjob.setJobName("Sample Command Pattern");
            pjob.setCopies(1);
            pjob.setPrintable(new Printable() {
                public int print(Graphics pg, PageFormat pf, int pageNum) {
                    if (pageNum>0)
                        return Printable.NO_SUCH_PAGE;
                    pg.drawString(fileContent.getText(), 500, 500);
                    paint(pg);
                    return Printable.PAGE_EXISTS;
                }
            });
            if (pjob.printDialog() == false)
                return;
            pjob.print();
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(null,
                    "Printer error" + pe, "Printing error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
	
    //Method that allows for simple replace, where you select code to replace and type in what you want 
    //to replace it
    public void simpleReplace(JTextPane fileContent){
    	String input=JOptionPane.showInputDialog("Replace or insert with:");
    	if(input!=null)
    		fileContent.replaceSelection(input);
    }
    
  //Takes in a file and opens its contents in the notepad
    public void openFile(File fileToOpen, JTextPane fileContent) {
    	StringBuilder content=new StringBuilder();
    	try {
            BufferedReader in = new BufferedReader(new FileReader(fileToOpen));
            String line;
            while ((line = in.readLine()) != null){
            	content.append(line+"\n");
            }
            fileContent.setText(content.toString());
            JOptionPane.showMessageDialog(null, "File is opened successfully...");
            in.close();
            addToRecent(fileToOpen);
        } catch (IOException ex) {
        }
    }
  //Takes in a file and adds it to the recently opened files array. Drops the oldest file 
    //if array has 5 or more items in it.
    public void addToRecent(File file) {
    	if(!recentFiles.contains(file)) {
    		if(recentFiles.size()>=5) {
    			recentFiles.remove(0);
    		}
    		recentFiles.add(file);
    	}
    }
  //Takes in an integer which represents which file in the recently opened array to open, and opens that file
    public void openRecent(int x,JTextPane fileContent) {
    	File fileToOpen=recentFiles.get(x);
    	openFile(fileToOpen,fileContent);
    }
    
  //Opens a file in the note pad
    public void openAndSelectFile(JTextPane fileContent) {
    	File fileToOpen = null;
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION)
            fileToOpen = fc.getSelectedFile();
        openFile(fileToOpen,fileContent);
    }
  //Takes in two strings and returns a menu item using the first string 
    //as the text and the second as the action command
    public JMenuItem makeMenuItem(String name, ActionListener al) {
    	JMenuItem menuItem = new JMenuItem(name);
        menuItem.addActionListener(al);
        return menuItem;
    }
    
  //This method updates the menu displaying recently opened files
    public void updateRecent(JTextPane fileContent,JMenu openRecentSelect) {
    	openRecentSelect.removeAll();
    	int x=0;
    	for(File file:recentFiles) {
    		final int ItemIndex=x;
        	JMenuItem recents=makeMenuItem(file.getName(),(e)->{openRecent(ItemIndex,fileContent);});
        	x++;
        	openRecentSelect.add(recents);
        }
    }

	
}
