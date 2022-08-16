<template>
  <apexchart
    type="pie"
    width="380"
    :options="chartOptions"
    :series="realSeries"
  ></apexchart>
</template>

<script>
import VueApexCharts from "vue3-apexcharts";
export default {
  name: "PieChart",
  components: { apexchart: VueApexCharts },
  props: ["series"],
  data() {
    const chartOptions = {
      chart: {
        width: 380,
        type: "pie",
      },
      theme: {
        palette: "palette4", // upto palette10
      },
      tooltip: {
        y: {
          formatter: function (val) {
            return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원";
          },
        },
      },
      labels: ["예금", "적금"],
      responsive: [
        {
          breakpoint: 480,
          options: {
            chart: {
              width: 200,
            },
            legend: {
              position: "left",
            },
          },
        },
      ],
    };
    const realSeries = [100, 200];
    return { chartOptions, realSeries };
  },
  updated() {
    this.realSeries = this.series;
  },
};
</script>

<style></style>
