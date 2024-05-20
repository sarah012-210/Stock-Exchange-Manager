package com.example.stockfinal;


import javafx.collections.FXCollections;
import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Rectangle;

public class CandleStickChart extends XYChart<String, Number> {

    public CandleStickChart(Axis<String> xAxis, Axis<Number> yAxis) {
        super(xAxis, yAxis);
        setAnimated(false);
        setData(FXCollections.observableArrayList());
    }

    @Override
    protected void layoutPlotChildren() {
        // Custom layout logic for the candlestick chart
        for (int seriesIndex = 0; seriesIndex < getData().size(); seriesIndex++) {
            Series<String, Number> series = getData().get(seriesIndex);
            for (int itemIndex = 0; itemIndex < series.getData().size(); itemIndex++) {
                Data<String, Number> item = series.getData().get(itemIndex);

                // Get the data and plot the candlestick here
                String xValue = item.getXValue();
                Number yValue = item.getYValue();
                CandleData candleData = (CandleData) item.getExtraValue();

                // Create candlestick node if not created
                if (item.getNode() == null) {
                    item.setNode(createCandleNode(candleData));
                }

                // Position the node
                double x = getXAxis().getDisplayPosition(xValue);
                double y = getYAxis().getDisplayPosition(yValue);
                item.getNode().relocate(x, y);
            }
        }
    }

    private Rectangle createCandleNode(CandleData candleData) {
        Rectangle candle = new Rectangle(10, Math.abs(candleData.getHigh().doubleValue() - candleData.getLow().doubleValue()));
        candle.setStyle("-fx-fill: black;");
        return candle;
    }

    @Override
    protected void dataItemChanged(Data<String, Number> item) {
        // Handle data item changes
    }

    @Override
    protected void dataItemAdded(Series<String, Number> series, int itemIndex, Data<String, Number> item) {
        // Handle data item addition
        if (item.getNode() == null) {
            CandleData candleData = (CandleData) item.getExtraValue();
            item.setNode(createCandleNode(candleData));
        }
        getPlotChildren().add(item.getNode());
    }

    @Override
    protected void dataItemRemoved(Data<String, Number> item, Series<String, Number> series) {
        // Handle data item removal
        getPlotChildren().remove(item.getNode());
    }

    @Override
    protected void seriesAdded(Series<String, Number> series, int seriesIndex) {
        // Handle series addition
        for (Data<String, Number> data : series.getData()) {
            if (data.getNode() == null) {
                CandleData candleData = (CandleData) data.getExtraValue();
                data.setNode(createCandleNode(candleData));
            }
            getPlotChildren().add(data.getNode());
        }
    }

    @Override
    protected void seriesRemoved(Series<String, Number> series) {
        // Handle series removal
        for (Data<String, Number> data : series.getData()) {
            getPlotChildren().remove(data.getNode());
        }
    }

    // Class to store candle data
    public static class CandleData {
        private final Number open;
        private final Number close;
        private final Number high;
        private final Number low;

        public CandleData(Number open, Number close, Number high, Number low) {
            this.open = open;
            this.close = close;
            this.high = high;
            this.low = low;
        }

        public Number getOpen() {
            return open;
        }

        public Number getClose() {
            return close;
        }

        public Number getHigh() {
            return high;
        }

        public Number getLow() {
            return low;
        }
    }
}
