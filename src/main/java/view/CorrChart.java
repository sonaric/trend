package view;

import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import tr.Trend;

import java.util.ArrayList;

/**
 * Created by stanislav on 17.09.17.
 */
public class CorrChart {
    public void showCorrChart(Trend trend, Window mod){
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Corr Chart");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("t");
        yAxis.setLabel("Corr");

        final BarChart<String , Number> lineChart = new BarChart<String, Number>(xAxis,yAxis);

        XYChart.Series series1 = new XYChart.Series();

        series1.setName("Corr");

        ArrayList<Double> trend_list = trend.getCorrFuncValue();

        for(int i = 0; i< 30; i++){
            series1.getData().add(new XYChart.Data(String.valueOf(trend.getX()[i]), trend_list.get(i)));
        }

        Scene scene = new Scene(lineChart,800, 600);
        lineChart.getData().addAll(series1);

        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.initOwner(mod);
        primaryStage.showAndWait();
    }
}
