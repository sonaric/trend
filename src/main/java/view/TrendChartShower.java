package view;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import tr.Trend;

import java.util.ArrayList;

public class TrendChartShower{
    public void showTrendChart(Trend trend, Window mod){
        Stage primaryStage = new Stage();
       primaryStage.setTitle("Trend Chart");
       final NumberAxis xAxis = new NumberAxis();
       final NumberAxis yAxis = new NumberAxis();

       xAxis.setLabel("t");
       yAxis.setLabel("y");

       final LineChart<Number , Number> lineChart = new LineChart<Number, Number>(xAxis,yAxis);

        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();

        series1.setName("Input data");
        series2.setName("Trend");

        ArrayList<Double> trend_list = trend.getTrend_v();

        for(int i = 0; i< 48; i++){
            series1.getData().add(new XYChart.Data(trend.getX()[i], trend.getY()[i]));
            series2.getData().addAll(new XYChart.Data(trend.getX()[i],trend_list.get(i)));
        }

        Scene scene = new Scene(lineChart,800, 600);
        lineChart.getData().addAll(series1, series2);

        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.initOwner(mod);
        primaryStage.showAndWait();
    }
}
