
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adam Gracy
 */
public class CharCountController implements Initializable {
    
    private final char[] textAreaCharArray = MainController.textAreaCharArray;
    // Parrellel arrays to track each letter's frequency.
    
    private int[]  letterCountArray = new int[26];
    
    private final String[] stringAlphabetArray = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s",
                    "t","u","v","w","x","y","z"};
    
    @FXML
    private BarChart<?,?> bc_LetterFrequencyChart;    
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    Button bt_OK;
    @FXML
    Hyperlink hl_WhatIsThis;
    
    @FXML
    private void handleButtonAction(ActionEvent event){
        Button b = (Button) event.getSource();
        if(b.equals(bt_OK)){
            bc_LetterFrequencyChart.getScene().getWindow().hide();
        }
    }
    @FXML
    private void handleHyperLinkAction(ActionEvent event) throws IOException, URISyntaxException{
        Hyperlink h = (Hyperlink) event.getSource();
        if(h.equals(hl_WhatIsThis)){
            if(Desktop.isDesktopSupported()){
                Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/Letter_frequency"));
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        frequencyScanner();
        createChart();
    }
    // Instance methods
    private void frequencyScanner(){
        for(int i = 0; i < textAreaCharArray.length; i++){
            switch(textAreaCharArray[i]){
                case 'a':
                    letterCountArray[0]++;
                    break;
                case 'b':
                    letterCountArray[1]++;
                    break;
                case 'c':
                    letterCountArray[2]++;
                    break;
                case 'd':
                    letterCountArray[3]++;
                    break;
                case 'e':
                    letterCountArray[4]++;
                    break;
                case 'f':
                    letterCountArray[5]++;
                    break;
                case 'g':
                    letterCountArray[6]++;
                    break;
                case 'h':
                    letterCountArray[7]++;
                    break;
                case 'i':
                    letterCountArray[8]++;
                    break;
                case 'j':
                    letterCountArray[9]++;
                    break;
                case 'k':
                    letterCountArray[10]++;
                    break;
                case 'l':
                    letterCountArray[11]++;
                    break;
                case 'm':
                    letterCountArray[12]++;
                    break;
                case 'n':
                    letterCountArray[13]++;
                    break;
                case 'o':
                    letterCountArray[14]++;
                    break;
                case 'p':
                    letterCountArray[15]++;
                    break;
                case 'q':
                    letterCountArray[16]++;
                    break;
                case 'r':
                    letterCountArray[17]++;
                    break;
                case 's':
                    letterCountArray[18]++;
                    break;
                case 't':
                    letterCountArray[19]++;
                    break;
                case 'u':
                    letterCountArray[20]++;
                    break;
                case 'v':
                    letterCountArray[21]++;
                    break;
                case 'w':
                    letterCountArray[22]++;
                    break;
                case 'x':
                    letterCountArray[23]++;
                    break;
                case 'y':
                    letterCountArray[24]++;
                    break;
                case 'z':
                    letterCountArray[25]++;
                    break;
                default:
                    break;
            }
        }
    }
                    
    private void createChart(){
        
        XYChart.Series set1 = new XYChart.Series<>();
        for(int i = 0; i < stringAlphabetArray.length; i++){
            set1.getData().add(new XYChart.Data(stringAlphabetArray[i],letterCountArray[i]));
        }
        bc_LetterFrequencyChart.getData().addAll(set1);
    }
}
        
        
        
        
    
    
                    
                
