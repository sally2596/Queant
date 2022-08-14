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
  name: "ColumnChart",
  components: { apexchart: VueApexCharts },
  props: ["series"],
  data() {
    const chartOptions = {
      chart: {
        type: "bar",
        height: 350,
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
          borderRadius: 5,
        },
      },
      xaxis: {
        type: "datetime",
      },
      theme: {
        palette: "palette4",
      },
      tooltip: {
        intersect: false,
        shared: true,
      },
      fill: {
        opacity: 1,
      },
    };
    return { chartOptions };
  },
};
</script>

<style></style>
