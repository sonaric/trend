package view;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import tr.Trend;

import java.util.ArrayList;

/**
 * Created by stanislav on 18.09.17.
 */
public class ForecastOnYear {
    public void showForecastChart(Trend trend, Window mod){
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Forecast Chart");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("t");
        yAxis.setLabel("y");

        final LineChart<Number , Number> lineChart = new LineChart<Number, Number>(xAxis,yAxis);

        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();

        series1.setName("Up boundary");
        series2.setName("Down boundary");
        series3.setName("Forecast");

        for(int i = 0; i< 12; i++){
            series1.getData().add(new XYChart.Data(i+1, trend.getYearForecast().get(0).get(i)));
            series2.getData().addAll(new XYChart.Data(i+1,trend.getYearForecast().get(1).get(i)));
            series3.getData().addAll(new XYChart.Data(i+1,trend.getYearForecast().get(2).get(i)));
        }

        Scene scene = new Scene(lineChart,800, 600);
        lineChart.getData().addAll(series1, series2, series3);

        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.initOwner(mod);
        primaryStage.showAndWait();
    }
}
