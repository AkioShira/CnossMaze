package data;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class InitialController {

    private Stage mainStage;
    @FXML
    private AnchorPane Setting;
    @FXML
    private AnchorPane Maze;
    @FXML
    private AnchorPane Info;
    @FXML
    private ToggleButton disToggleB;
    @FXML
    private Circle circleToggle;
    @FXML
    private Label switcher;
    @FXML
    private ToggleButton nameSet;
    @FXML
    private ToggleButton nameSet1;
    @FXML
    private ToggleButton set1;
    @FXML
    private ToggleButton set2;
    @FXML
    private ToggleButton set3;
    @FXML
    private ToggleButton set4;
    @FXML
    private ToggleButton set5;
    @FXML
    private ToggleButton set6;
    @FXML
    private TextField setMazeNum;
    @FXML
    private TextField setMazeNum1;
    private NodeList nodeList;

    public void initialize() {
        String filepath = "Setting.xml";
        File xmlFile = new File(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            TranslateTransition rt = new TranslateTransition(Duration.millis(1), circleToggle);
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            nodeList = document.getElementsByTagName("settings");
            String status = nodeList.item(0).getTextContent();
            if (Objects.equals(status, "true"))
            {
                Info.setVisible(true);
                animationOn(rt);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    final Delta dragDelta = new Delta();

    @FXML
    void setNumMaze(KeyEvent event) {
        setMazeNum1.setText(setMazeNum.getText());
    }

    @FXML
    void setNumMaze1(KeyEvent event) {
        setMazeNum.setText(setMazeNum1.getText());
    }

    @FXML
    void textfocus(MouseEvent event) {nameSet1.setSelected(true);}

    @FXML
    void oneSelect(MouseEvent event) {set1.setSelected(true); nameSet.setSelected(true);}

    @FXML
    void twoSelect(MouseEvent event) {set2.setSelected(true); nameSet.setSelected(true);}

    @FXML
    void threeSelect(MouseEvent event) {set3.setSelected(true); nameSet.setSelected(true);}

    @FXML
    void fourSelect(MouseEvent event) {set4.setSelected(true); nameSet.setSelected(true);}

    @FXML
    void fiveSelect(MouseEvent event) {set5.setSelected(true); nameSet.setSelected(true);}

    @FXML
    void sixSelect(MouseEvent event) {set6.setSelected(true); nameSet.setSelected(true);}

    @FXML
    void selected(MouseEvent event) {
        nameSet.setSelected(true);
    }

    @FXML
    void selected1(MouseEvent event) {
        nameSet1.setSelected(true);
    }

    @FXML
    void infoClicked(MouseEvent event) {
        Maze.setVisible(false);
        Setting.setVisible(false);
        if(Info.isVisible()!=true) {
            Info.setVisible(true);
        }
        else {
            Info.setVisible(false);
        }
    }

    @FXML
    void mazeClicked(MouseEvent event) {
        Info.setVisible(false);
        Setting.setVisible(false);
        if(Maze.isVisible()!=true) {
            Maze.setVisible(true);
        }
        else {
            Maze.setVisible(false);
        }
    }

    @FXML
    void settingClicked(MouseEvent event) {
        Maze.setVisible(false);
        Info.setVisible(false);
        if(Setting.isVisible()!=true) {
            Setting.setVisible(true);
        }
        else {
            Setting.setVisible(false);
        }
    }

    @FXML
    void shutDownClicked(MouseEvent event) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse("Setting.xml");
        Node root = document.getElementsByTagName("settings").item(0);
        if (disToggleB.isSelected())
            root.setTextContent("true");
        else
            root.setTextContent("false");
        writeDocument(document);
        mainStage.close();
    }

    public void setStage(Stage stage) {
        mainStage = stage;
    }

    //СМЕЩЕНИЕ ОКНА
    private static class Delta {
        double x, y;
    }

    @FXML
    void onPanelDragged(MouseEvent event) {
        mainStage.setX(event.getScreenX() + dragDelta.x);
        mainStage.setY(event.getScreenY() + dragDelta.y);
    }

    @FXML
    void onPanelPressed(MouseEvent event) {
        dragDelta.x = mainStage.getX() - event.getScreenX();
        dragDelta.y = mainStage.getY() - event.getScreenY();
    }

    @FXML
    void collapse(MouseEvent event) {
        mainStage.setIconified(true);
    }

    @FXML
    void parametrs(MouseEvent event){
        TranslateTransition rt = new TranslateTransition(Duration.millis(200), circleToggle);
        if(disToggleB.isSelected())
            animationOff(rt);
        else
            animationOn(rt);
    }

    void animationOff(TranslateTransition rt){
        rt.setByX(-20);
        rt.play();
        disToggleB.setSelected(false);
        switcher.setText("OFF");
        circleToggle.setFill(Color.valueOf("#eeeeee"));
    }

    void animationOn(TranslateTransition rt) {
        rt.setByX(20);
        rt.play();
        disToggleB.setSelected(true);
        switcher.setText("ON");
        circleToggle.setFill(Color.valueOf("#CBD4FF"));
    }

    private static void writeDocument(Document document) throws TransformerFactoryConfigurationError {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream("Setting.xml");
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        }
    }

    @FXML
    void buildhtml(MouseEvent event) throws IOException {
        Stage Maze = new Stage();
        FXMLDocumentController(Maze);
    }

    protected void FXMLDocumentController(Stage stage) throws IOException{
        MazeController mazeController = new MazeController();
        MazeException me = new MazeException();
        String v1 = setMazeNum.getText();
        int select =1; int size=9;
        if (nameSet.isSelected())
        {
            if(set1.isSelected())
                size=9;
            else if(set2.isSelected())
                size=15;
            else if(set3.isSelected())
                size=25;
            else if(set4.isSelected())
                size=35;
            else if(set5.isSelected())
                size=45;
            else if(set6.isSelected())
                size=55;
            else {
                select--;
            }
            mazeController.setSize(size);

        }else
        {
            try {
                me.getException(v1);
                size = Integer.parseInt(setMazeNum.getText());
                mazeController.setSize(size);
            }catch (CallException e){
                Stage exMessage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ExceptionForm.fxml"));
                Pane root = (Pane)loader.load();
                ExceptionController Controller = (ExceptionController)loader.getController();
                Controller.setStage(exMessage);
                Scene scene = new Scene(root);
                exMessage.setTitle("ОШИБКА ВВОДА");
                exMessage.setScene(scene);
                exMessage.initStyle(StageStyle.UTILITY);
                exMessage.setResizable(false);
                ExceptionController ex = new ExceptionController();
                exMessage.show();
                setMazeNum.setText("");
                setMazeNum1.setText("");
                select--;
            }
        }
        if(size<=9)
            mazeController.setZoom(1);
        if(size>9&&size<=25)
            mazeController.setZoom(0.8);
        else if(size>25)
            mazeController.setZoom(0.6);
        Pane root = FXMLLoader.load(getClass().getResource("/gui/MazeForm.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("ЛАБИРИНТ");
        try {
            stage.getIcons().add(new Image("/gui/pic/icon.png"));
        }catch (Exception c)
        {}
        stage.setScene(scene);
        stage.setMinHeight(200);
        stage.setMinWidth(600);
        stage.setHeight(640);
        if (select>0)
            stage.show();
        select++;
    }
}

