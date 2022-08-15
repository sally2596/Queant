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
    <div v-else class="w-75">
      <div class="container">
        <!-- 포트폴리오 관리하기 버튼 -->
        <router-link :to="{ name: 'portfolioEdit' }"
          ><button class="btn btn-outline-success">
            포트폴리오 관리하기
          </button></router-link
        ><br />

        <!-- 포트폴리오 요약 -->
        <!-- 예금과 적금의 비율 + 총 투자액 -->
        <div id="Summary" class="row justify">
          <div class="col-6">
            <pie-chart
              v-bind:series="summarySeries"
              :chartOptionLabels="summaryChartOptionLabels"
            ></pie-chart>
          </div>
          <div class="col-6">
            <div class="d-flex justify-content-between">
              <h4>현재까지 모은 금액</h4>
              <h2>{{ filtered(depositTotalAmount + savingTotalAmount) }}원</h2>
            </div>

            <div class="d-flex justify-content-between">
              <h6>예금</h6>
              <div>{{ filtered(depositTotalAmount) }}원</div>
            </div>
            <div class="d-flex justify-content-between">
              <h6>적금</h6>
              <div>{{ filtered(savingTotalAmount) }}원</div>
            </div>
          </div>
        </div>

        <!-- 적금 saving -->
        <div id="saving-view">
          <div class="d-flex justify-content-between">
            <h1>적금</h1>
            <h2>{{ filtered(savingTotalAmount) }}원</h2>
          </div>
          <div class="d-flex justify-content-between">
            <h6>만기된 상품</h6>
            <h6>{{ filtered(savingTotalAmount) }}원</h6>
          </div>
          <div class="d-flex justify-content-between">
            <h6>진행중 상품</h6>
            <h6>{{ filtered(savingTotalAmount) }}원</h6>
          </div>
          <div class="d-flex justify-content-between">
            <table class="col-6">
              <thead>
                <tr>
                  <th>은행</th>
                  <th>상품명</th>
                  <th>금액</th>
                  <th>금리</th>
                  <th>차트</th>
                </tr>
              </thead>
              <br />
              <tbody v-for="(saving, index) in savingSeries" :key="index">
                <td>
                  <img :src="saving.picture" alt="" style="width: 40px" />
                </td>
                <td>{{ saving.name }}</td>
                <td>{{ saving.data[0] }}</td>
                <td>{{ saving.rate }}%</td>
                <td>
                  <button
                    class="btn btn-outline-success"
                    @click="changeSavingData(index)"
                  >
                    보기
                  </button>
                </td>
              </tbody>
            </table>
            <column-chart
              v-bind:series="nowSavingChart"
              v-bind:category="nowSavingCategory"
              class="col-6"
            ></column-chart>
          </div>
        </div>

        <!-- 예금 Deposit -->
        <div id="deposit-view" class="">
          <div class="d-flex justify-content-between">
            <h1>예금</h1>
            <h2>{{ filtered(depositTotalAmount) }}원</h2>
          </div>
          <div class="d-flex justify-content-between">
            <h6>만기된 상품</h6>
            <h6>{{ filtered(depositTotalAmount) }}원</h6>
          </div>
          <div class="d-flex justify-content-between">
            <h6>진행중 상품</h6>
            <h6>{{ filtered(depositTotalAmount) }}원</h6>
          </div>

          <div class="d-flex justify-content-between">
            <table class="col-6">
              <thead>
                <tr>
                  <th>은행</th>
                  <th>상품명</th>
                  <th>금액</th>
                  <th>금리</th>
                  <th>차트</th>
                </tr>
              </thead>
              <br />
              <tbody v-for="(deposit, index) in depositSeries" :key="index">
                <td>
                  <img :src="deposit.picture" alt="" />
                </td>
                <td>{{ deposit.name }}</td>
                <td>{{ deposit.data[0] }}</td>
                <td>{{ deposit.rate }}%</td>
                <td>
                  <button
                    class="btn btn-outline-success"
                    @click="changeDepositData(index)"
                  >
                    보기
                  </button>
                </td>
              </tbody>
            </table>
            <column-chart
              v-bind:series="nowDepositChart"
              v-bind:category="nowDepositCategory"
              class="col-6"
            ></column-chart>
          </div>
        </div>
      </div>
      <div v-for="p in depositChart" :key="p">
        {{ p }}
      </div>
      <div v-for="p in savingChart" :key="p">
        {{ p }}
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";
import { mapActions, mapGetters } from "vuex";
import PieChart from "@/components/PieChart.vue";
import BarChart from "@/components/BarChart.vue";
import TimeLineChart from "@/components/TimeLineChart.vue";
import LineChart from "@/components/LineChart.vue";
import ColumnChart from "@/components/ColumnChart.vue";

// globally registered and available for all charts

export default {
  name: "PortfolioView",
  components: {
    Navbar,
    PieChart,
    BarChart,
    TimeLineChart,
    LineChart,
    ColumnChart,
  },
  data() {
    const summarySeries = [];
    const summaryChartOptionLabels = ["예금", "적금"];
    const savingSeries = [];
    const depositSeries = [];
    let savingTotalRate = 0;
    let depositTotalRate = 0;
    let savingTotalAmount = 0;
    let depositTotalAmount = 0;

    //

    const depositChart = [];
    const depositCategory = [];
    const nowDepositChart = [];
    const nowDepositCategory = [];
    const savingChart = [];
    const savingCategory = [];
    const nowSavingChart = [];
    const nowSavingCategory = [];
    return {
      filtered,
      computeDBProductRate,
      computeCustomRate,
      summarySeries,
      summaryChartOptionLabels,
      savingSeries,
      depositSeries,
      depositTotalRate,
      savingTotalRate,
      savingTotalAmount,
      depositTotalAmount,

      depositChart,
      depositCategory,
      nowDepositChart,
      nowDepositCategory,
      savingChart,
      savingCategory,
      nowSavingChart,
      nowSavingCategory,
    };
  },
  setup() {},
  computed: {
    ...mapGetters(["portfolio", "customProducts"]),
  },
  methods: {
    ...mapActions(["fetchMyPortfolio"]),
    changeDepositData(index) {
      this.nowDepositChart = this.depositChart[index];
      this.nowDepositCategory = this.depositCategory[index];
    },
    changeSavingData(index) {
      this.nowSavingChart = this.savingChart[index];
      this.nowSavingCategory = this.savingCategory[index];
    },
    calculateDeposit(start_date, end_date, amount, rate, simple, term) {
      let result = [];

      let start = start_date.split("-");
      let end = end_date.split("-");
      let startYear = parseInt(start[0]);
      let endYear = parseInt(end[0]);
      let dates = [];

      for (var i = startYear; i <= endYear; i++) {
        var endMonth = i != endYear ? 11 : parseInt(end[1]) - 1;
        var startMon = i === startYear ? parseInt(start[1]) - 1 : 0;
        for (
          var j = startMon;
          j <= endMonth;
          j = j > 12 ? j % 12 || 11 : j + 1
        ) {
          var month = j + 1;
          var displayMonth = month < 10 ? "0" + month : month;
          dates.push([i, displayMonth].join("-"));
        }
      }

      //원금
      let original = {};
      original.name = "원금";
      let data = [];
      for (var i = 0; i < dates.length; i++) {
        data.push(amount);
      }
      original.data = data;
      result.push(original);

      let interest = {};
      interest.name = "이번 달 이자";
      interest.data = [];

      let interestCumulative = {};
      interestCumulative.name = "누적 이자";
      interestCumulative.data = [];

      //첫번째 달 이자 없음
      interest.data.push(0);
      interestCumulative.data.push(0);

      if (simple) {
        //단리
        console.log("단리");
        let calcMoney = (rate / 12) * amount;
        let cumulMoney = 0;

        for (var i = 1; i < dates.length; i++) {
          interest.data.push(Math.ceil(calcMoney));
          interestCumulative.data.push(Math.ceil(cumulMoney));
          cumulMoney += calcMoney;
        }
      } else {
        //복리
        var calcRate = rate / 12 + 1;
        let cumulMoney = 0;
        for (var i = 1; i < dates.length; i++) {
          let calcMoney = (rate / 12) * amount;
          interest.data.push(Math.ceil(calcMoney));
          interestCumulative.data.push(Math.ceil(cumulMoney));

          cumulMoney += calcMoney;
          amount *= calcRate;
          console.log(amount);
        }
      }

      result.push(interest);
      result.push(interestCumulative);

      return { dates, result };
    },

    calculateSaving(start_date, amount, rate, simple, term) {
      let result = [];

      let start = start_date.split("-");
      let year = parseInt(start[0]);
      let month = parseInt(start[1]);
      let dates = [];

      for (let i = 0; i <= term; i++) {
        if (month == 13) {
          year += 1;
          month = 1;
        }
        dates.push([year, month].join("-"));
        month += 1;
      }

      //원금
      let original = {};
      original.name = "원금";
      let data = [];
      for (var i = 0; i < term; i++) {
        data.push(amount * (i + 1));
        if (i === term - 1) data.push(amount * (i - 1));
      }
      original.data = data;
      result.push(original);

      let interest = {};
      interest.name = "이번 달 이자";
      interest.data = [];

      let interestCumulative = {};
      interestCumulative.name = "누적 이자";
      interestCumulative.data = [];

      //첫번째 달 이자 없음
      interest.data.push(0);
      interestCumulative.data.push(0);

      if (simple) {
        //단리
        console.log("적금단리");
        let cumulMoney = 0;

        for (var i = 0; i < term; i++) {
          let calcMoney = (amount * rate * 0.01 * (term - i)) / term;
          interest.data.push(Math.ceil(calcMoney));
          cumulMoney += calcMoney;
          interestCumulative.data.push(Math.ceil(cumulMoney));
        }
      } else {
        //복리
        console.log("적금복리");
        let cumulMoney = 0;
        for (var i = 0; i < term; i++) {
          let calcMoney =
            amount * (1 + (rate * 0.01) / term) ** (term - i) - amount;
          interest.data.push(Math.ceil(calcMoney));
          cumulMoney += calcMoney;
          interestCumulative.data.push(Math.ceil(cumulMoney));
        }
      }

      result.push(interest);
      result.push(interestCumulative);

      return { dates, result };
    },
  },
  beforeCreate: function () {
    document.body.className = "home_body";
  },
  created() {
    this.fetchMyPortfolio();

    this.portfolio.forEach((item) => {
      let amount = [item.amount];
      let productName = item.product.name;
      let picture = item.product.picture;
      let rate = this.computeDBProductRate(item);

      const startDate = new Date(item.start_date);
      const endDate = new Date(item.end_date);
      let data = [];

      data.push({
        x: "saving",
        y: [item.start_date, item.end_date],
      });
      if (item.product.deposit) {
        this.depositTotalAmount += item.amount;
        this.depositTotalRate += rate;
      } else {
        this.savingTotalAmount += item.amount;
        this.savingTotalRate += rate;
      }

      if (item.product.deposit) {
        this.depositSeries.push({
          name: productName,
          data: amount,
          picture: picture,
          rate: rate,
          startDate: startDate,
          endDate: endDate,
        });

        let result = this.calculateDeposit(
          item.start_date,
          item.end_date,
          item.amount,
          rate / 100,
          item.option.rate_type
        );
        console.log(result);
        this.depositChart.push(result.result);
        this.depositCategory.push(result.dates);
      } else {
        this.savingSeries.push({
          name: productName,
          data: amount,
          picture: picture,
          rate: rate,
          startDate: startDate,
          endDate: endDate,
        });

        let result = this.calculateSaving(
          item.start_date,
          item.amount,
          rate,
          item.option.rate_type,
          item.option.save_term
        );
        console.log("**여기보세요**");
        console.log(result);
        this.savingChart.push(result.result);
        this.savingCategory.push(result.dates);
      }
    });

    // 커스텀 상품
    this.customProducts.forEach((item) => {
      let amount = [item.amount];
      let productName = item.product_name;
      let picture = "../assets/image/퀸트_로고.png";
      let rate = this.computeCustomRate(item);
      const startDate = new Date(item.start_date);
      const endDate = new Date(item.end_date);

      let data = [];
      data.push({
        x: "saving",
        y: [item.start_date, item.end_date],
      });

      if (item.deposit) {
        this.depositTotalAmount += item.amount;
        this.depositTotalRate += rate;
      } else {
        this.savingTotalAmount += item.amount;
        this.savingTotalRate += rate;
      }

      if (item.deposit) {
        this.depositSeries.push({
          name: productName,
          data: amount,
          picture: picture,
          rate: rate,
          startDate: startDate,
          endDate: endDate,
        });
      } else {
        this.savingSeries.push({
          name: productName,
          data: amount,
          picture: picture,
          rate: rate,
          startDate: startDate,
          endDate: endDate,
        });
      }
    });

    this.summarySeries.push(this.depositTotalAmount);
    this.summarySeries.push(this.savingTotalAmount);
  },
};
</script>

<style>
@import "../../assets/css/home.css";
</style>
