<template>
  <apexchart
    type="pie"
    width="380"
    :options="chartOptions"
    :series="series"
  ></apexchart>
</template>

<script>
import VueApexCharts from "vue3-apexcharts";
export default {
  name: "PieChart",
  components: { apexchart: VueApexCharts },
  props: ["series", "chartOptionLabels"],
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
      labels: this.chartOptionLabels,
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
    return { chartOptions };
  },
};
</script>

<style></style>
