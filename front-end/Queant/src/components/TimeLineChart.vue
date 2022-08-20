<template>
  <apexchart
    type="rangeBar"
    height="200"
    :options="chartOptions"
    :series="series"
  ></apexchart>
</template>

<script>
import VueApexCharts from "vue3-apexcharts";
export default {
  name: "TimeLineChart",
  components: { apexchart: VueApexCharts },
  props: ["series"],
  data() {
    const chartOptions = {
      chart: {
        height: 450,
        type: "rangeBar",
      },
      plotOptions: {
        bar: {
          horizontal: true,
          barHeight: "60%",
        },
      },
      xaxis: {
        type: "datetime",
      },

      stroke: {
        width: 1,
      },
      fill: {
        type: "solid",
        opacity: 0.6,
      },
      legend: {
        position: "top",
        horizontalAlign: "left",
      },
      theme: {
        palette: "palette4",
      },
      annotations: {
        xaxis: [
          {
            x: new Date().getTime(),
            strokeDashArray: 0,
            borderColor: "#775DD0",
            label: {
              // borderColor: "#775DD0",
              // style: {
              //   color: "#fff",
              //   background: "#775DD0",
              // },
              text: "Today",
            },
          },
        ],
      },
      tooltip: {
        x: {
          format: "yyyy MMM dd",
        },
        // custom: function (opts) {
        //   const startYear = new Date(opts.y1).getFullYear();
        //   const startMonth = new Date(opts.y1).getMonth() + 1;
        //   const startDate = new Date(opts.y1).getDate();

        //   const endYear = new Date(opts.y2).getFullYear();
        //   const endMonth = new Date(opts.y2).getMonth() + 1;
        //   const endDate = new Date(opts.y2).getDate();

        //   return (
        //     startYear +
        //     "년 " +
        //     startMonth +
        //     "월 " +
        //     startDate +
        //     "일 ~ " +
        //     endYear +
        //     "년 " +
        //     endMonth +
        //     "월" +
        //     endDate +
        //     "일"
        //   );
        //   // return opts.y1;
        // },
      },
    };
    return { chartOptions };
  },
};
</script>

<style></style>
