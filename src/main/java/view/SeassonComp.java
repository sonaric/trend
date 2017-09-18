package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.StringConverter;
import tr.Trend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stanislav on 17.09.17.
 */
public class SeassonComp {
    public static final String Column1MapKey = "Season";
    public static final String Column2MapKey = "Random";
    public void showSeason(Trend trend, Window mod){
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Season component");

        TableColumn<Map, Double> s_column = new TableColumn<>("Season component");
        TableColumn<Map, Double> r_column = new TableColumn<>("Random");

        s_column.setCellValueFactory(new MapValueFactory<>(Column1MapKey));
        r_column.setCellValueFactory(new MapValueFactory<>(Column2MapKey));

        TableView table = new TableView<>(generateDataInMap(trend));
        table.setMinHeight(500);
        table.setMaxWidth(112);

        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.getColumns().setAll(s_column, r_column);
        Callback<TableColumn<Map, Double>, TableCell<Map, Double>>
                cellFactoryForMap = (TableColumn<Map, Double> p) ->
                new TextFieldTableCell(new StringConverter() {
                    @Override
                    public String toString(Object object) {
                        return object.toString();
                    }

                    @Override
                    public Object fromString(String string) {
                        return string;
                    }
                });

        r_column.setCellFactory(cellFactoryForMap);
        s_column.setCellFactory(cellFactoryForMap);


        Scene scene = new Scene(table,600, 600);
        //lineChart.getData().addAll(series1, series2);

        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.initOwner(mod);
        primaryStage.showAndWait();
    }

    private ObservableList<Map> generateDataInMap(Trend tr){

        ObservableList<Map> allData = FXCollections.observableArrayList();
        for(int i = 0; i < 48; i++){
            Map<String, Double> dataRow = new HashMap<>();
            dataRow.put(Column1MapKey, tr.getResiltList().get(0).get(i));
            dataRow.put(Column2MapKey, tr.getResiltList().get(1).get(i));

            allData.add(dataRow);
        }
        return allData;
    }
}
