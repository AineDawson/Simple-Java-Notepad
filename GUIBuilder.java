package CleanNotePad;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

//This class creates the GUI for the simple notepad
public class GUIBuilder extends JFrame{
	LogicForPad LP=new LogicForPad();
	
	JMenuBar menuBar;
    JMenu fileMenu, editMenu, openRecentSelect;
    JTextPane fileContent;
    JMenuItem newFileSelect, saveFileSelect, printFileSelect,copySelect,pasteSelect, openFileSelect, simpleReplaceSelect;
    public GUIBuilder() {
        setTitle("A Simple Notepad Tool");
        setPreferredSize(new Dimension(600,600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        //Creates menu items for the File menu
        newFileSelect = LP.makeMenuItem("New File",(e)->{LP.newFile(fileContent);});
        saveFileSelect = LP.makeMenuItem("Save File",(e)->{LP.saveFile(fileContent);});
        printFileSelect=LP.makeMenuItem("Print File",(e)->{LP.printFile(fileContent);});
        //New Features
        openFileSelect=LP.makeMenuItem("Open File",(e)->{LP.openAndSelectFile(fileContent);LP.updateRecent(fileContent, openRecentSelect);});
        openRecentSelect=new JMenu("Recent");
        
        
        //Creates File menu and adds items to it
        fileMenu = new JMenu("File");
        fileMenu.add(newFileSelect);
        fileMenu.addSeparator();
        fileMenu.add(saveFileSelect);
        fileMenu.addSeparator();
        fileMenu.add(printFileSelect);
        fileMenu.addSeparator();
        fileMenu.add(openFileSelect);
        fileMenu.addSeparator();
        fileMenu.add(openRecentSelect);
        
        //Creates menu items for the Edit menu
        copySelect=LP.makeMenuItem("Copy",(e)->{LP.copyContent(fileContent);;});
        pasteSelect=LP.makeMenuItem("Paste",(e)->{LP.paste(fileContent);});
        //New Features
        simpleReplaceSelect=LP.makeMenuItem("Simple-Replace",(e)->{LP.simpleReplace(fileContent);});
        
        //Creates Edit menu and adds items to it
        editMenu=new JMenu("Edit");
        editMenu.add(copySelect);
        editMenu.addSeparator();
        editMenu.add(pasteSelect);
        editMenu.addSeparator();
        editMenu.add(simpleReplaceSelect);
        
        //Creates a menuBar and adds the menus File and Edit
        menuBar=new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        setJMenuBar(menuBar);
        
        //Creates the main text pane for the user to type in
        fileContent=new JTextPane();
        add(new JScrollPane(fileContent));
        
        pack();
    }
    
    
    
    
}
