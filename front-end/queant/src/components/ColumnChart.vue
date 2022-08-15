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
  name: "ColumnChart",
  components: { apexchart: VueApexCharts },
  props: ["series", "category"],
  watch: {
    series: function () {
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
          type: "datetime",
          categories: this.category,
          labels: {
            format: "yyyy/MM",
          },
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
            formatter: function (val) {
              let date = new Date(val);
              let year = date.getFullYear();
              let month = date.getMonth();
              let day = date.getDate();
              if (day == 31 || day == 30) month += 1;
              if (month == 12) {
                year += 1;
                month = 0;
              }
              month += 1;
              return year + "/" + month;
            },
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
