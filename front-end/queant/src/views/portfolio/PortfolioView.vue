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

      <div class="row justify-content-md-center">
        <div v-if="savingAmount?.lenth != 0" class="col-4">
          <vue3-chart-js v-bind="{ ...pieChartSaving }" />
          <div class="card-body">
            월 납입금 {{ filtered(savingTotalAmount) }}원
            <br />
            평균 이자율
            {{
              Math.round((savingTotalRate / savingAmount.length) * 1000) / 1000
            }}
          </div>
          <h2 class="d-flex justify-content-center">적금 상품 목록</h2>
          <br />
          <table>
            <thead>
              <tr>
                <th>은행</th>
                <th>상품명</th>
                <th>금액</th>
                <th>적용 금리</th>
              </tr>
            </thead>
            <br />
            <tbody v-for="(save, index) in savingName" :key="index">
              <td>
                <img :src="savingPicture[index]" alt="" />
              </td>
              <td>{{ savingName[index] }}</td>
              <td>{{ savingAmount[index] }}</td>
              <td>{{ savingRate[index] }}%</td>
              <td>{{ savingColors[index] }}</td>
            </tbody>
          </table>
        </div>
        <div v-if="depositAmount?.length != 0" class="col-4">
          <vue3-chart-js v-bind="{ ...pieChartDeposit }" />
          <div class="card-body">
            총 금액 {{ filtered(depositTotalAmount) }}원
            <br />
            평균 이자율
            {{
              Math.round((depositTotalRate / depositAmount.length) * 1000) /
              1000
            }}
          </div>
          <h2 class="d-flex justify-content-center">예금 상품 목록</h2>
          <br />
          <table>
            <thead>
              <tr>
                <th>은행</th>
                <th>상품명</th>
                <th>금액</th>
                <th>적용 금리</th>
              </tr>
            </thead>
            <br />
            <tbody v-for="(deposit, index) in depositName" :key="index">
              <td>
                <img :src="depositPicture[index]" alt="" />
              </td>
              <td>{{ depositName[index] }}</td>
              <td>{{ depositAmount[index] }}</td>
              <td>{{ depositRate[index] }}%</td>
              <td>{{ depositColors[index] }}</td>
            </tbody>
          </table>
        </div>
      </div>

      <div v-for="customProduct in customProducts" :key="customProduct">
        {{ customProduct }}
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
    const depositPicture = [];
    const savingPicture = [];
    let savingTotalRate = 0;
    let depositTotalRate = 0;
    let savingTotalAmount = 0;
    let depositTotalAmount = 0;

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
          padding: 20,
        },
        //
        plugins: {
          datalabels: {
            display: false,
            // data:savingName,
            borderRadius: 3,
            font: {
              size: 18,
            },
          },
          legend: {
            display: false,
            position: "left",
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
      options: {
        layout: {
          padding: 20,
        },
        //
        plugins: {
          datalabels: {
            display: false,
            // data:savingName,
            borderRadius: 3,
            font: {
              size: 18,
            },
          },
          legend: {
            display: false,
            position: "right",
            responsive: true,
            maintainAspectRatio: false,
          },
        },
      },
    };
    const filtered = (val) => String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");

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
      savingPicture,
      depositPicture,
      depositTotalRate,
      savingTotalRate,
      savingTotalAmount,
      depositTotalAmount,
      filtered,
    };
  },
  setup() {},
  computed: {
    ...mapGetters(["portfolio", "customProducts"]),
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
        this.depositTotalAmount += item.amount;
        this.depositPicture.push(item.product.picture);
        let rate = 0;
        rate += item.option.base_rate;
        item.conditions.forEach((element) => {
          rate += element.special_rate;
        });
        rate = Math.round(rate * 1000) / 1000;
        this.depositRate.push(rate);
        this.depositName.push(item.product.name);
        this.depositTotalRate += rate;
      } else {
        // 적금은 한달에 들어가는 돈만 보여주는 걸로?
        this.savingAmount.push(item.amount);
        this.savingTotalAmount += item.amount;
        this.savingPicture.push(item.product.picture);
        let rate = 0;
        rate += item.option.base_rate;
        item.conditions.forEach((element) => {
          rate += element.special_rate;
        });
        rate = Math.round(rate * 1000) / 1000;
        this.savingRate.push(rate);
        this.savingName.push(item.product.name);
        this.savingTotalRate += rate;
      }
    });

    // 커스텀 상품
    this.customProducts.forEach((item) => {
      if (item.deposit) {
        this.depositAmount.push(item.amount);
        this.depositTotalAmount += item.amount;
        let rate = 0;
        rate = item.base_rate + item.special_rate;
        rate = Math.round(rate * 1000) / 1000;
        this.depositRate.push(rate);
        this.depositName.push(item.product_name);
        this.depositTotalRate += rate;
        this.depositPicture.push("../../assets/image/퀸트_로고.png");
      } else {
        this.savingAmount.push(item.amount);
        this.savingTotalAmount += item.amount;
        let rate = 0;
        rate = item.base_rate + item.special_rate;
        rate = Math.round(rate * 1000) / 1000;
        this.savingRate.push(rate);
        this.savingName.push(item.product_name);
        this.savingTotalRate += rate;
        this.savingPicture.push("../../assets/image/퀸트_로고.png");
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
