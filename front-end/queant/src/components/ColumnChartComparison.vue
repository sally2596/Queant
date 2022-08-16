<template>
  <apexchart
    type="bar"
    height="500"
    :options="chartOptions"
    :series="series"
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
        dataLabels: {
          formatter: function (val, opt) {
            return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원";
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
  created() {
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
      title: {
        text: "만기 수령액",
      },
      dataLabels: {
        formatter: function (val, opt) {
          return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원";
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
        x: {
          // formatter: function (val) {
          //   return val + "번 포트폴리오";
          // },
        },
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
};
</script>

<style></style>
