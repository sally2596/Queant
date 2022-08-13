<template>
  <apexchart
    type="bar"
    height="150"
    :options="chartOptions"
    :series="series"
  ></apexchart>
</template>

<script>
import VueApexCharts from "vue3-apexcharts";
export default {
  name: "BarChart",
  components: { apexchart: VueApexCharts },
  props: ["series"],
  data() {
    const chartOptions = {
      chart: {
        type: "bar",
        height: "auto",
        stacked: true,
        stackType: "100%",
      },
      theme: {
        palette: "palette4",
      },
      plotOptions: {
        bar: {
          horizontal: true,
        },
      },
      stroke: {
        // 바 안에서 애들 간격 사이
        width: 2,
        colors: ["#fff"],
      },
      //
      xaxis: {
        show: false,
        labels: {
          show: false,
        },
        axisBorder: {
          show: false,
        },
        axisTicks: {
          show: false,
        },
      },
      yaxis: {
        show: false,
        labels: {
          show: false,
        },
        axisBorder: {
          show: false,
        },
        axisTicks: {
          show: false,
        },
      },
      tooltip: {
        y: {
          formatter: function (val) {
            return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원";
          },
        },
        x: {
          show: false,
        },
      },
      fill: {
        opacity: 1,
      },
      legend: {
        position: "bottom",
        horizontalAlign: "right",
        offsetX: 5,
      },
    };
    return { chartOptions };
  },
};
</script>

<style></style>
