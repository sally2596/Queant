<template>
  <apexchart
    type="bar"
    height="500"
    :options="chartOptions"
    :series="realSeries"
  ></apexchart>
</template>

<script>
import VueApexCharts from "vue3-apexcharts";
export default {
  name: "ColumnChartComparison",
  components: { apexchart: VueApexCharts },
  props: ["series", "category"],
  watch: {
    series: function () {
      console.log("들어오긴 하냐");
      console.log(this.series);
      this.updateSeriesLine();
    },
    category: function () {
      this.updateSeriesLine();
    },
  },
  methods: {
    updateSeriesLine() {
      this.realSeries = this.series;
      this.chartOptions = {
        chart: {
          type: "bar",
          height: 500,
          stacked: true,
          toolbar: {
            show: true,
          },
          zoom: {
            enabled: true,
          },
        },

        plotOptions: {
          bar: {
            horizontal: false,
            borderRadius: 3,
          },
        },
        xaxis: {
          categories: this.category,
        },
        yaxis: {
          labels: {
            formatter: function (val) {
              return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원";
            },
          },
        },
        theme: {
          palette: "palette4",
        },
        tooltip: {
          intersect: false,
          shared: true,
          y: {
            formatter: function (val) {
              return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원";
            },
          },
          // x: {

          // },
        },
        legend: {
          position: "top",
          offsetY: 5,
        },
        fill: {
          opacity: 1,
        },
      };
    },
  },
  data() {
    const chartOptions = {};
    const realCategory = [];
    const realSeries = [];
    return { chartOptions, realCategory, realSeries };
  },
};
</script>

<style></style>
