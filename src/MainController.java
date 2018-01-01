/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import javafx.stage.Stage;

/**
 *
 * @author Adam
 */
public class MainController implements Initializable {
    static char[] textAreaCharArray;
    boolean isSaved = false;
    static String fileName = "Untitled.txt";

    @FXML
    AnchorPane root;
    @FXML
    MenuBar mb_MenuBar;
    
    @FXML
    MenuItem mi_Close, mi_Delete,mi_Paste, mi_About,mi_Copy,mi_SelectAll,
            mi_Cut,mi_New, mi_Open, mi_Save, mi_SaveAs, mi_Print,mi_CharFrequency,
            mi_ContextCut, mi_ContextDelete,mi_ContextCopy, mi_ContextSelectAll,
            mi_ContextPaste;
    
    @FXML
    private TextArea ta_Main;
    
    public MainController(){
       
    }
        
    @FXML
    private void handleMenuItemSelection(ActionEvent event) throws IOException{
        MenuItem mi =  (MenuItem) event.getSource();
        // File
        if(mi.equals(mi_New)){
            if(isSaved){
                ta_Main.clear();
                fileName = "Untitled.txt";
                Stage stage = (Stage) ta_Main.getScene().getWindow();
                stage.setTitle(Driver.TITLE + "*" + fileName);
                isSaved = false;
            }else{
                
            }
        }
        if(mi.equals(mi_Open)){
           FileChooser fc = new FileChooser();
           fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(".txt", "*.txt"));
           File file = fc.showOpenDialog(new Stage());
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            while((line = br.readLine()) != null){
                ta_Main.appendText(line + "\n");
            }
            fileName = file.getPath();
            Stage stage = (Stage) ta_Main.getScene().getWindow();
            stage.setTitle(Driver.TITLE + fileName);
            isSaved = true;
        }
        if(mi.equals(mi_Save)){
            if(fileName != null && !fileName.equals("Untitled.txt")){
                save();
                Stage stage = (Stage) ta_Main.getScene().getWindow();
                stage.setTitle(Driver.TITLE + fileName);
                isSaved = true;
            }else{
                saveAs();
                Stage stage = (Stage) ta_Main.getScene().getWindow();
                stage.setTitle(Driver.TITLE + fileName);
                isSaved = true;
            }
                
        }
        if(mi.equals(mi_SaveAs)){
            saveAs();
            Stage stage = (Stage) ta_Main.getScene().getWindow();
            stage.setTitle(Driver.TITLE + fileName);
            isSaved = true;
        }
        if(mi.equals(mi_Print)){
            
        }
        if(mi.equals(mi_Close)){
            Stage stage = (Stage) ta_Main.getScene().getWindow();
            stage.close();
        }
        // Edit
        if(mi.equals(mi_Copy)){
            ta_Main.copy();
            System.out.println(ta_Main.getSelectedText() + " copied to clipboard.");
        }
        if(mi.equals(mi_Cut)){
           ta_Main.copy();
           ta_Main.deleteText(ta_Main.getSelection().getStart(),ta_Main.getSelection().getEnd());
        }
        if(mi.equals(mi_Delete)){
            ta_Main.deleteText(ta_Main.getSelection());
        }
        if(mi.equals(mi_Paste)){
            int caretPos = ta_Main.getCaretPosition();
            ta_Main.insertText(caretPos, getClipboardContents());
        }
        if(mi.equals(mi_SelectAll)){
            ta_Main.selectAll();
        }
        // Help
        if(mi.equals(mi_About)){
            Alert alert = createAlert();
            alert.show();
        }
        if(mi.equals(mi_CharFrequency)){
            textAreaStringToCharArray();
            Stage stage = new Stage();
            Parent Parent = FXMLLoader.load(getClass().getResource("CharCountFXML.fxml"));
            Scene scene = new Scene(Parent);
            stage.setScene(scene);
            stage.show();
        }
        System.out.println(mi.getId());
    }
    @FXML private void handleContextMenuItemSelection(ActionEvent event){
        MenuItem mi =  (MenuItem) event.getSource();
        
        if(mi.equals(mi_ContextDelete)){
            ta_Main.deleteText(ta_Main.getSelection());
        }
        
        if(mi.equals(mi_ContextCopy)){
            ta_Main.copy();
            System.out.println(ta_Main.getSelectedText() + " copied to clipboard.");
        }
        if(mi.equals(mi_ContextSelectAll)){
            ta_Main.selectAll();
        }
        if(mi.equals(mi_ContextPaste)){
            int caretPos = ta_Main.getCaretPosition();
            ta_Main.insertText(caretPos, getClipboardContents());
        }
        if(mi.equals(mi_ContextCut)){
           ta_Main.copy();
           ta_Main.deleteText(ta_Main.getSelection().getStart(),ta_Main.getSelection().getEnd());
        }
        System.out.println(mi.getId());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ta_Main.addEventHandler(KeyEvent.KEY_TYPED, (KeyEvent event) -> {
            isSaved = false;
            System.out.println(event.getCharacter());
            Stage stage = (Stage) ta_Main.getScene().getWindow();
            stage.setTitle(Driver.TITLE + "*" +fileName);
        });
    }
    
    
    // Instance methods
    private Alert createAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About {Program Name}");
        alert.setHeaderText("Some Program Name");
        alert.setContentText("This program was created by me.");
        return alert;
    }
    
    public void setMenuBarTransparent(){
        mb_MenuBar.setOpacity(0.24);
    }
    public void setMenuBarOpaque(){
         mb_MenuBar.setOpacity(1);
    }
    private void setTitle(){
        Stage stage = (Stage) ta_Main.getScene().getWindow();
        stage.setTitle(Driver.TITLE + fileName);
    }
    
    private String getClipboardContents(){
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        String paste = "";
        try{
            paste = (String) c.getContents(null).getTransferData(DataFlavor.stringFlavor);
        }catch(UnsupportedFlavorException | IOException e){}
        return paste;
    }
    private void textAreaStringToCharArray(){
        textAreaCharArray = ta_Main.getText().toLowerCase().toCharArray();
    }
    private void save() throws FileNotFoundException, IOException{
        BufferedWriter bw = new BufferedWriter(new PrintWriter(fileName));
        bw.write(ta_Main.getText());
        bw.flush();
        bw.close();
        System.out.println("File saved to " + fileName);
    }
    private void saveAs() throws FileNotFoundException, IOException{
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(".txt", "*.txt"));
        File file = fc.showSaveDialog(new Stage());
        BufferedWriter bw = new BufferedWriter(new PrintWriter(file.getPath()));
        bw.write(ta_Main.getText());
        bw.flush();
        bw.close();
        fileName = file.toPath().toString();
        System.out.println("File saved to " + fileName);
    }
}

    
    
