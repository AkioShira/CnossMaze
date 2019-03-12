package data;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Schischko A.A. on 29.10.2017.
 */
public class MazeController {

    static double zoom;
    static int size;
    int [][]maze;
    WebEngine webEngine;
    List<Cel> waveBase = new ArrayList<>();
    int generate=0;

    @FXML
    private ListView<String> listView;
    @FXML
    private WebView browser;
    @FXML
    private ImageView minZoom;
    @FXML
    private ImageView maxZoom;
    @FXML
    private AnchorPane MazeFrame;

    public void initialize() {
        webEngine = browser.getEngine();
        EllerGenerate el = new EllerGenerate(size);
        el.driver();
        maze = el.getMaze();
        htmlTable(webEngine, maze);
        browser.setZoom(zoom);
        if(zoom>=0.6)
            maxZoom.setVisible(true);
    }

    public void setSize(int s)
    {
        size = s;
    }

    public void setZoom(double s) {zoom = s;}

    void htmlTable(WebEngine webEngine, int[][]maze)
    {
        URL start = getClass().getResource("/gui/calls_pic/start.png");
        URL end = getClass().getResource("/gui/calls_pic/end.png");
        URL foot = getClass().getResource("/gui/calls_pic/foot.png");
        URL stop = getClass().getResource("/gui/calls_pic/stop.png");
        String starthtml = "<style type=\"text/css\">\n" +
                "TABLE {\n" +
                "    border-collapse: collapse;\n" +
                "    background: #2A2D3B;\n" +
                "   }\n" +
                "   TD, TH {\n" +
                "    padding: 5px;\n" +
                "    border: 2px solid #919CCC;\n" +
                "   }" +
                "  </style>\n" +
                "  <body bgcolor = \"#2A2D3B\">\n" +
                "  <table cellspacing=\"0\" bgcolor = \"white\">";
        String content="";
        for(int j = 0; j<maze.length; j++)
        {
            content += "<tr>";
            for (int i = 0; i<maze[0].length; i++)
            {
                if (i==0&&j==1)
                {
                    content += "<td><img src="+start+"></td>";
                } else if ((i==maze[0].length-1)&&(j==maze.length-2)) {
                    content += "<td><img src="+end+"></td>";
                } else {
                    if (maze[i][j] == 0)
                        content += "<td></td>";
                    if (maze[i][j] == 1)
                        content += "<td><img src="+foot+"></td>";
                    if (maze[i][j] == -1)
                        content += "<td><img src="+stop+"></td>";
                }
            }
            content += "</tr>";
        }
        String endhtml="  </table>\n" +
                "  </body>";
        webEngine.loadContent(starthtml+content+endhtml);
    }

    @FXML
    void MinClicked(MouseEvent event) {
        if(zoom>=0.6)
        {
            zoom = zoom - 0.2;
            browser.setZoom(zoom);
            maxZoom.setVisible(true);
            if(zoom<=0.6)
                minZoom.setVisible(false);
        }
    }

    @FXML
    void MaxClicked(MouseEvent event) {
        if(zoom<=1)
        {
            zoom = zoom + 0.2;
            browser.setZoom(zoom);
            minZoom.setVisible(true);
            if(zoom>=1)
                maxZoom.setVisible(false);
        }
    }

    @FXML
    void waveResult(MouseEvent event) {
        if (generate<1) {
            WaveResult wave = new WaveResult();
            wave.generateWave(maze, 1, 1, maze.length - 2, maze[0].length - 2);
            listView.setVisible(true);
            waveBase = wave.getResultList();
            for(int x=waveBase.size()-1;x>=0;x--)
                listView.getItems().add(waveBase.get(x).toString());
            listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            MazeFrame.setRightAnchor(browser, (double) 100);
            maze = wave.getMaze();
            htmlTable(webEngine, maze);
            generate++;
        }
    }
}
