package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import tr.Trend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stanislav on 16.09.17.
 */
public class MainForm extends Application {
    public static final String ColumnlMapKey = "T";
    public static final String Column2MapKey = "Y";
    private Trend tr = new Trend();

    double[] x = new double[48];
    double[] y = {4.13, 4.67, 5.78, 5.89, 6.59, 7.76,
            7.71, 6.68, 5.22, 4.62, 3.67, 3.78, 4.09,
            3.16, 5.05, 5.5, 6.38, 7.55, 7.43, 6.57,
            5.17, 4.09, 3.36, 3.43, 3.7, 3.66, 4.46,
            4.81, 5.36, 6.39, 6.39, 5.57, 4.52, 3.69,
            2.97, 2.97, 3.16, 3.25, 3.92, 4.25, 5.1,
            5.85, 5.13, 5.14, 4.36, 3.42, 2.86, 2.92};

    TrendChartShower tcs = new TrendChartShower();
    SeassonComp sc = new SeassonComp();
    CorrChart cc = new CorrChart();
    ForecastOnYear foy = new ForecastOnYear();

    @Override
    public void start(Stage primaryStage) throws Exception {

        for(int i=0; i<48; i++){
            x[i] = i+1;
        }

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        final Label b0_l = new Label("b0:");
        final Label b1_l = new Label("b1:");
        final Label b2_l = new Label("b2:");

        final TextField b0_v = new TextField("1");
        final TextField b1_v = new TextField("1");
        final TextField b2_v = new TextField("0");
//-------------------------------------------------------
        final TitledPane checkFisher = new TitledPane();
        //checkFisher.setCollapsible(false);
        checkFisher.setText("Checking the model for adequacy");

        final Label lb1 = new Label("Fischer shorts:");
        final Label lb1_v = new Label("2.8115435063");
        final Label lb2 = new Label("Fischer criterion");
        final Label lb2_v = new Label("0");
        final Label lb_res_f = new Label("");

        GridPane grid_fisher = new GridPane();
        grid_fisher.setAlignment(Pos.CENTER);
        grid_fisher.setHgap(10);
        grid_fisher.setVgap(10);
        grid_fisher.setPadding(new Insets(25,25,25,25));

        grid_fisher.add(lb1, 0,0);
        grid_fisher.add(lb1_v, 1,0);
        grid_fisher.add(lb2, 0,1);
        grid_fisher.add(lb2_v, 1,1);
        grid_fisher.add(lb_res_f, 0,2, 2,1);

        checkFisher.setContent(grid_fisher);
//------------------------------------------------------
        final TitledPane checkStudent = new TitledPane();
        //checkFisher.setCollapsible(false);
        checkStudent.setText("Checking the coefficients");

        final Label lb3 = new Label("Student shorts:");
        final Label lb3_v = new Label("1.6794273927");
        final Label lb4 = new Label("Student criterion");
        final Label lb4_v = new Label("0");
        final Label lb_res_s = new Label("");

        GridPane grid_student = new GridPane();
        grid_student.setAlignment(Pos.CENTER);
        grid_student.setHgap(10);
        grid_student.setVgap(10);
        grid_student.setPadding(new Insets(25,25,25,25));

        grid_student.add(lb3, 0,0);
        grid_student.add(lb3_v, 1,0);
        grid_student.add(lb4, 0,1);
        grid_student.add(lb4_v, 1,1);
        grid_student.add(lb_res_s, 0,2, 2,1);

        checkStudent.setContent(grid_student);
//--------------------------------------------------
        final ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Adaptive");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);
        RadioButton rb2 = new RadioButton("Multiplicative");
        rb2.setToggleGroup(group);
        final VBox rb_group = new VBox();
        rb_group.setSpacing(10);
        rb_group.setAlignment(Pos.CENTER_LEFT);
        rb_group.setPadding(new Insets(5,5,5,5));
        rb_group.getChildren().addAll(rb1,rb2);

        final Button trend_btn = new Button("Trend");
        final Button sez_btn = new Button("sez_sr -> rand");
        final Button corr_btn = new Button("Corr");
        final Button for_btn = new Button("Forecast on year");
        HBox btn_group = new HBox();
        btn_group.setSpacing(10);
        btn_group.setAlignment(Pos.CENTER);
        btn_group.setPadding(new Insets(5,5,5,5));
        btn_group.getChildren().addAll(trend_btn, sez_btn, corr_btn, for_btn);


        grid.add(b0_l, 0, 0);
        grid.add(b1_l, 0, 1);
        grid.add(b2_l, 0, 2);
        grid.add(b0_v, 1, 0);
        grid.add(b1_v, 1, 1);
        grid.add(b2_v, 1, 2);
        grid.add(rb_group,2,0, 1,3);
        grid.add(btn_group, 0, 3, 3,1);
        grid.add(checkFisher,0,4,3,1);
        grid.add(checkStudent,0,5,3,1);

        final Label label = new Label("Input data");
        label.setFont(new Font("Arial",20));


        TableColumn<Map, Double> t_column = new TableColumn<>("t");
        TableColumn<Map, Double> y_column = new TableColumn<>("y");

        t_column.setCellValueFactory(new MapValueFactory<>(ColumnlMapKey));
        y_column.setCellValueFactory(new MapValueFactory<>(Column2MapKey));

        TableView table = new TableView<>(generateDataInMap());
        table.setMinHeight(500);
        table.setMaxWidth(112);

        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.getColumns().setAll(t_column, y_column);
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

        t_column.setCellFactory(cellFactoryForMap);
        y_column.setCellFactory(cellFactoryForMap);

        final VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10,0,0,10));
        vBox.getChildren().addAll(label,table);

        HBox t_b = new HBox();
        t_b.setSpacing(5);
        t_b.setPadding(new Insets(10,0,0,10));
        t_b.getChildren().addAll(vBox,grid);

        Scene scene = new Scene(new Group(), 550, 600);

        ((Group) scene.getRoot()).getChildren().addAll(t_b);

        //-------------------Search Trend----------------------------

        trend_btn.setOnAction(event -> {
            tr.setN(48);
            tr.setB(Double.valueOf(b0_v.getText()), Double.valueOf(b1_v.getText()), Double.valueOf(b2_v.getText()));
            tr.setX(x);
            tr.setY(y);
            tr.searchTrend();
            ArrayList<Double> b_v = tr.getB();
            b0_v.setText(String.valueOf(b_v.get(0)));
            b1_v.setText(String.valueOf(b_v.get(1)));
            b2_v.setText(String.valueOf(b_v.get(2)));
            double fisher_stud[]=tr.checkFisherStudent();
            lb2_v.setText(String.valueOf(fisher_stud[0]));

            if(Double.valueOf(lb1_v.getText()) < fisher_stud[0]){
                lb_res_f.setText("The model adequate");
                lb_res_f.setTextFill(Color.GREEN);
            }else {
                lb_res_f.setText("The model inadequate");
                lb_res_f.setTextFill(Color.RED);
            }

            lb4_v.setText(String.valueOf(fisher_stud[1]));

            if(Double.valueOf(lb3_v.getText()) < fisher_stud[2]){
                lb_res_s.setText("The coefficients are significant");
                lb_res_s.setTextFill(Color.GREEN);
            }else {
                lb_res_s.setText("The coefficients are insignificant");
                lb_res_s.setTextFill(Color.RED);
            }

            tcs.showTrendChart(tr,primaryStage);

        });

        //-----------------------------------------------------------
        sez_btn.setOnAction(event -> {
            tr.seassonComponent(rb1.isSelected());
            sc.showSeason(tr,primaryStage);
        });

        corr_btn.setOnAction(event -> {
            tr.corrFunc(rb1.isSelected());
            cc.showCorrChart(tr,primaryStage);
        });

        for_btn.setOnAction(event -> {
            tr.forecastYear(rb1.isSelected());
            foy.showForecastChart(tr, primaryStage);
        });
        //--------------------------------------------
        primaryStage.setTitle("Trend Analise");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

    private ObservableList<Map> generateDataInMap(){

        ObservableList<Map> allData = FXCollections.observableArrayList();
        for(int i = 0; i < 48; i++){
            Map<String, Double> dataRow = new HashMap<>();
            dataRow.put(ColumnlMapKey, x[i]);
            dataRow.put(Column2MapKey, y[i]);

            allData.add(dataRow);
        }
        return allData;
    }
}
