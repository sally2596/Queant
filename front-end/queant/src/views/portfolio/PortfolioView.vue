<template>
  <Navbar />
  <header id="title-div">
    <h1 class="title" id="title">내 포트폴리오</h1>
  </header>

  <div class="content">
    <!-- 포트폴리오 없을 때 -->
    <div v-if="portfolio?.length === 0" class="portfolio-none">
      <img
        src="../../assets/image/물음표개미_none.png"
        alt=""
        style="width: 300px; height: 280px"
      />
      <br /><br />
      <h5>포트폴리오에 상품이 없습니다.</h5>
      <br /><br />
      <router-link :to="{ name: 'portfolioEdit' }"
        ><button class="btn btn-outline-success">
          포트폴리오에 상품 추가하기
        </button></router-link
      >
    </div>

    <!-- 포트폴리오 있을 때 -->
    <div v-else class="portfolio">
      <router-link :to="{ name: 'portfolioEdit' }"
        ><button class="btn btn-outline-success">
          포트폴리오 관리하기
        </button></router-link
      ><br />

      <!-- 포트폴리오 뭉텅이로 볼 때 -->
      <!-- {{ portfolio }} -->

      <!-- 포트폴리오의 상품을 개별로 볼 때 -->

      <div v-if="savingAmount?.lenth!=0" class="w-25 h-25">
        <vue3-chart-js v-bind="{ ...pieChartSaving }" />
        
        <div v-for="(saving,index) in savingName" :key="index">
        {{ saving }}{{savingAmount[index]}}
        <hr />
      </div>
      </div>
      <div v-if="depositAmount?.length != 0" class="w-25 h-25">
        <vue3-chart-js v-bind="{ ...pieChartDeposit }" />
      </div>
      <div v-for="product in portfolio" :key="product">
        {{ product }}
        <hr />
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";
import { mapActions, mapGetters } from "vuex";
import Vue3ChartJs from "@j-t-mcc/vue3-chartjs";
import dataLabels from "chartjs-plugin-datalabels";

import { Chart } from "chart.js";

Chart.register(dataLabels);
// globally registered and available for all charts

export default {
  name: "PortfolioView",
  components: { Navbar, Vue3ChartJs },
  data() {
    const depositAmount = [];
    const savingAmount = [];
    const depositName = [];
    const savingName = [];
    const depositRate = [];
    const savingRate = [];
    const depositColors = [];
    const savingColors = [];
    let savingTotalRate = 0;
    let depostiTotalRate = 0;

    const pieChartSaving = {
      type: "pie",
      height: 0.5,
      width: 10,
      data: {
        labels: savingName,
        datasets: [
          {
            backgroundColor: savingColors,
            data: savingAmount,
            hoverOffset: 10,
          },
        ],
      },
      options: {
        layout: {
          padding: 20
        },
        //
        plugins: {
          datalabels: {
            display: false,
            // data:savingName,
            align: "bottom",
            backgroundColor: "#ccc",
            borderRadius: 3,
            font: {
              size: 18,
            },
          },
          legend: {
            display: true,
            position: "bottom",

            // 색깔별로 어떤 데이터가 보이는지
            // labels: {
            //   // This more specific font property overrides the global property
            //   boxWidth: 8,
            //   padding: 10,
            //   usePointStyle: true,
            //   pointStyle: "circle",
            //   font: {
            //     size: 20,
            //   },
            // },
            // tooltip: {
            //   boxWidth: 15,
            //   bodyFont: {
            //     size: 50,
            //   },
            // },
            responsive: true,
            maintainAspectRatio: false,
          },
        },
      },
    };
    const pieChartDeposit = {
      type: "pie",
      height: 10,
      width: 10,
      data: {
        labels: depositName,
        datasets: [
          {
            backgroundColor: depositColors,
            data: depositAmount,
            hoverOffset: 10,
          },
        ],
      },
    };
   
    return {
      // lineChart,
      pieChartDeposit,
      pieChartSaving,
      depositAmount,
      savingAmount,
      depositName,
      savingName,
      depositRate,
      savingRate,
      depositColors,
      savingColors,
    };
  },
  setup() {
    
  },
  computed: {
    ...mapGetters(["portfolio"]),
  },
  methods: {
    ...mapActions(["fetchMyPortfolio"]),
  },
  beforeCreate: function () {
    document.body.className = "home_body";
  },
  created() {
    this.fetchMyPortfolio();

    this.portfolio.forEach((item) => {
      if (item.product.deposit) {
        // 일할 계산 or 전체 계산?
        // 예금이면 이미 들어간 돈이고 이자가 붙는거니까 이자 일할 계산해서 붙여주기?
        this.depositAmount.push(item.amount);
        let rate = 0;
        rate += item.option.base_rate;
        item.conditions.forEach((element) => {
          rate += element.special_rate;
        });
        rate = Math.round(rate * 1000) / 1000;
        this.depositRate.push(rate);
        this.depositName.push(item.product.name);
        this.depostiTotalRate += rate;
      } else {
        // 적금은 한달에 들어가는 돈만 보여주는 걸로?
        this.savingAmount.push(item.amount);

        let rate = 0;
        rate += item.option.base_rate;
        item.conditions.forEach((element) => {
          rate += element.special_rate;
        });
        rate = Math.round(rate * 1000) / 1000;
        this.savingRate.push(rate);
        this.savingName.push(item.product.name);
      }
    });
    for (let i = 0; i < this.depositAmount.length; i++) {
      this.depositColors.push(
        "#" + Math.floor(Math.random() * 16777215).toString(16)
      );
    }

    for (let i = 0; i < this.savingAmount.length; i++) {
      this.savingColors.push(
        "#" + Math.floor(Math.random() * 16777215).toString(16)
      );
    }
  },
  
};
</script>

<style>
@import "../../assets/css/home.css";
</style>
