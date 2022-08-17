<template>
  <Navbar />
  <header id="title-div">
    <h1 class="title" id="title">MY 포트폴리오</h1>
  </header>

  <div class="content">
    <!-- 포트폴리오 없을 때 -->
    <div
      v-if="portfolio?.length === 0 && customProducts?.length === 0"
      class="portfolio-none"
    >
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
        <!-- 포트폴리오 요약 -->
        <!-- 예금과 적금의 비율 + 총 투자액 -->
        <div id="Summary" class="container row justify">
          <div class="col-lg-6">
            <pie-chart
              v-bind:series="summarySeries"
              v-bind:chartOptionLabels="summaryChartOptionLabels"
            ></pie-chart>
          </div>
          <div class="col-lg-6">
            <div class="d-flex justify-content-between">
              <h4>현재까지 모은 금액</h4>
              <h2
                v-text="
                  filtered(
                    savingFinishAmount +
                      savingIngAmount +
                      depositIngAmount +
                      depositFinishAmount
                  )
                "
              ></h2>
            </div>

            <div class="d-flex justify-content-between">
              <h6>예금</h6>
              <div
                v-text="filtered(depositIngAmount + depositFinishAmount)"
              ></div>
            </div>
            <div class="d-flex justify-content-between">
              <h6>적금</h6>
              <div
                v-text="filtered(savingFinishAmount + savingIngAmount)"
              ></div>
            </div>
            <br /><br />
            <!-- 포트폴리오 관리하기 버튼 -->
            <div class="d-flex justify-content-end">
              <router-link :to="{ name: 'portfolioEdit' }"
                ><button class="btn btn-outline-success">
                  MY 포트폴리오 관리
                </button></router-link
              >
            </div>
            <br />
          </div>
        </div>

        <!-- 적금 saving -->
        <div
          v-if="savingSeries?.length != 0"
          id="saving-view"
          class="container row"
        >
          <div class="col-lg-6 m-4">
            <div class="d-flex justify-content-between">
              <h1>적금</h1>
              <h2 v-text="filtered(savingFinishAmount + savingIngAmount)"></h2>
            </div>
            <div class="d-flex justify-content-between">
              <h6>만기된 상품</h6>
              <h6 v-text="filtered(savingFinishAmount)"></h6>
            </div>
            <div class="d-flex justify-content-between">
              <h6>진행중 상품</h6>
              <h6 v-text="filtered(savingIngAmount)"></h6>
            </div>
          </div>
          <div
            class="col-lg-6 d-flex justify-content-between"
            style="height: 500px; overflow: auto"
          >
            <table class="table" style="height: 5rem">
              <thead>
                <tr>
                  <th>은행</th>
                  <th>상품명</th>
                  <th>투자금</th>
                  <th>금리</th>
                </tr>
              </thead>
              <tbody v-for="(saving, index) in savingSeries" :key="index">
                <tr
                  @click="changeSavingData(index)"
                  id="portfolio-saving-table-tr"
                >
                  <td>
                    <img :src="saving.picture" alt="" style="width: 40px" />
                  </td>
                  <td>{{ saving.name }}</td>
                  <td>{{ filtered(saving.total) }}</td>
                  <td>{{ saving.rate }}%</td>
                </tr>
              </tbody>
            </table>
          </div>
          <column-chart
            v-bind:series="nowSavingChart"
            v-bind:category="nowSavingCategory"
            class="col-lg-6"
          ></column-chart>
        </div>

        <!-- 예금 Deposit -->
        <div
          v-if="depositSeries?.length != 0"
          id="deposit-view"
          class="container row"
        >
          <div class="col-lg-6 m-4">
            <div class="d-flex justify-content-between">
              <h1>예금</h1>
              <h2
                v-text="filtered(depositIngAmount + depositFinishAmount)"
              ></h2>
            </div>
            <div class="d-flex justify-content-between">
              <h6>만기된 상품</h6>
              <h6 v-text="filtered(depositFinishAmount)"></h6>
            </div>
            <div class="d-flex justify-content-between">
              <h6>진행중 상품</h6>
              <h6 v-text="filtered(depositIngAmount)"></h6>
            </div>
          </div>
          <div
            class="col-lg-6 d-flex justify-content-between"
            style="height: 500px; overflow: auto"
          >
            <table class="table" style="height: 5rem">
              <thead>
                <tr>
                  <th>은행</th>
                  <th>상품명</th>
                  <th>투자금</th>
                  <th>금리</th>
                </tr>
              </thead>
              <tbody v-for="(deposit, index) in depositSeries" :key="index">
                <tr
                  @click="changeDepositData(index)"
                  id="portfolio-deposit-table-tr"
                >
                  <td>
                    <img :src="deposit.picture" alt="" style="width: 40px" />
                  </td>
                  <td>{{ deposit.name }}</td>
                  <td>{{ filtered(deposit.total) }}</td>
                  <td>{{ deposit.rate }}%</td>
                </tr>
              </tbody>
            </table>
          </div>
          <column-chart
            v-bind:series="nowDepositChart"
            v-bind:category="nowDepositCategory"
            class="col-lg-6"
          ></column-chart>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";
import { mapActions, mapGetters } from "vuex";
import PieChart from "@/components/PieChart.vue";
import ColumnChart from "@/components/ColumnChart.vue";
import { PerfectScrollbar } from "vue3-perfect-scrollbar";

// globally registered and available for all charts

export default {
  name: "PortfolioView",
  components: {
    Navbar,
    PieChart,
    ColumnChart,
    PerfectScrollbar,
  },
  data() {
    const summarySeries = [];
    const summaryChartOptionLabels = ["예금", "적금"];
    const savingSeries = [];
    const depositSeries = [];
    let savingIngAmount = 0;
    let depositIngAmount = 0;
    let savingFinishAmount = 0;
    let depositFinishAmount = 0;

    let RsavingFin = 0;
    let RsavingIng = 0;
    let RdepositFin = 0;
    let RdepositIng = 0;

    const depositChart = [];
    const depositCategory = [];
    const nowDepositChart = [];
    const nowDepositCategory = [];
    const savingChart = [];
    const savingCategory = [];
    const nowSavingChart = [];
    const nowSavingCategory = [];
    return {
      summarySeries,
      summaryChartOptionLabels,
      savingSeries,
      depositSeries,
      savingIngAmount,
      depositIngAmount,
      savingFinishAmount,
      depositFinishAmount,

      RsavingFin,
      RsavingIng,
      RdepositFin,
      RdepositIng,

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
  watch: {
    savingFinishAmount: function () {
      this.RsavingFin = this.savingFinishAmount;
    },
    portfolio: function () {
      console.log("제발좀..잘 좀 들어가봐");
      this.summarySeries = [];
      this.savingSeries = [];
      this.depositSeries = [];
      this.savingIngAmount = 0;
      this.depositIngAmount = 0;
      this.savingFinishAmount = 0;
      this.depositFinishAmount = 0;
      this.RsavingFin = 0;
      this.RsavingIng = 0;
      this.RdepositFin = 0;
      this.RdepositIng = 0;
      this.depositChart = [];
      this.depositCategory = [];
      this.nowDepositChart = [];
      this.nowDepositCategory = [];
      this.savingChart = [];
      this.savingCategory = [];
      this.nowSavingChart = [];
      this.nowSavingCategory = [];

      let today = new Date();
      this.portfolio.forEach((item) => {
        let productName = item.product.name;
        let picture = item.product.picture;
        let rate = this.sumDBProductRate(item);

        let result = this.calculate(
          item.start_date,
          item.amount,
          rate / 100,
          item.option.rate_type,
          item.option.save_term,
          item.product.deposit,
          today
        );
        if (item.product.deposit) {
          this.depositSeries.push({
            name: productName,
            picture: picture,
            total: result.total,
            rate: rate,
          });
          if (result.isFulled) this.depositFinishAmount += result.total;
          else this.depositIngAmount += result.total;
          this.depositChart.push(result.result);
          this.depositCategory.push(result.dates);
        } else {
          this.savingSeries.push({
            name: productName,
            picture: picture,
            total: result.total,
            rate: rate,
          });
          if (result.isFulled) this.savingFinishAmount += result.total;
          else this.savingIngAmount += result.total;
          this.savingChart.push(result.result);
          this.savingCategory.push(result.dates);
        }
      });

      // 커스텀 상품
      this.customProducts.forEach((item) => {
        let productName = item.product_name;
        let picture =
          "https://queant.s3.ap-northeast-2.amazonaws.com/images/Queant.png";
        let rate = this.sumCustomProductRate(item);
        let startDate = new Date(item.start_date);
        let endDate = new Date(item.end_date);
        let term =
          (endDate.getFullYear() - startDate.getFullYear()) * 12 +
          endDate.getMonth() -
          startDate.getMonth();
        let result = this.calculate(
          item.start_date,
          item.amount,
          rate / 100,
          item.fixed_rsrv,
          term,
          item.deposit,
          today
        );
        if (item.deposit) {
          this.depositSeries.push({
            name: productName,
            picture: picture,
            total: result.total,
            rate: rate,
          });
          if (result.isFulled) this.depositFinishAmount += result.total;
          else this.depositIngAmount += result.total;
          this.depositChart.push(result.result);
          this.depositCategory.push(result.dates);
        } else {
          this.savingSeries.push({
            name: productName,
            picture: picture,
            total: result.total,
            rate: rate,
          });
          if (result.isFulled) this.savingFinishAmount += result.total;
          else this.savingIngAmount += result.total;
          this.savingChart.push(result.result);
          this.savingCategory.push(result.dates);
        }
      });

      this.summarySeries.push(this.depositIngAmount + this.depositFinishAmount);
      this.summarySeries.push(this.savingFinishAmount + this.savingIngAmount);
    },
  },
  methods: {
    ...mapActions(["fetchMyPortfolio"]),
    sumDBProductRate(item) {
      let rate = 0;
      rate += item.option.base_rate;
      item.conditions.forEach((element) => {
        rate += element.special_rate;
      });
      rate = Math.round(rate * 1000) / 1000;
      return rate;
    },
    sumCustomProductRate(item) {
      let rate = 0;
      rate = item.base_rate + item.special_rate;
      rate = Math.round(rate * 1000) / 1000;
      return rate;
    },
    filtered(val) {
      return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원";
    },
    changeDepositData(index) {
      this.nowDepositChart = this.depositChart[index];
      this.nowDepositCategory = this.depositCategory[index];
    },
    changeSavingData(index) {
      this.nowSavingChart = this.savingChart[index];
      this.nowSavingCategory = this.savingCategory[index];
    },
    calculate(start_date, amount, rate, simple, term, deposit, today) {
      let result = [];

      let start = start_date.split("-");
      let year = parseInt(start[0]);
      let month = parseInt(start[1]);
      let dates = [];
      let todayYear = today.getFullYear();
      let todayMonth = today.getMonth();
      let todayIndex = -1;
      let isFulled = false;

      for (let i = 0; i <= term; i++) {
        if (month == 13) {
          year += 1;
          month = 1;
        }
        if (year <= todayYear && month <= todayMonth + 1) todayIndex = i;
        dates.push([year, month].join("-"));
        month += 1;
      }
      console.log(todayIndex);
      if (todayIndex == term) isFulled = true;

      //원금
      let original = {};
      original.name = "원금";
      let data = [];
      let savedMoney = 0;

      if (deposit)
        for (var i = 0; i <= term; i++) {
          data.push(amount);
        }
      else {
        for (var i = 0; i <= term; i++) {
          if (i === term) data.push(amount * i);
          else data.push(amount * (i + 1));
        }
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

      let cumulMoney = 0;

      //예금 복리에서만 사용
      var calcRate = rate / 12 + 1;

      for (var i = 0; i < term; i++) {
        let calcMoney = 0.0;

        if (deposit) {
          //예금
          if (!simple) calcMoney = (rate / 12) * amount; //단리
          else calcMoney = (rate / 12) * amount; //복리
        } else {
          //적금
          if (!simple) calcMoney = (amount * rate * (term - i)) / term; //단리
          else calcMoney = amount * (1 + rate / term) ** (term - i) - amount; //복리
        }

        interest.data.push(Math.ceil(calcMoney));

        interestCumulative.data.push(Math.ceil(cumulMoney));
        cumulMoney += calcMoney;
        //복리일 경우 amount 보정
        if (deposit && simple) amount *= calcRate;
      }

      let total = 0;
      if (todayIndex != -1) {
        if (deposit) {
          total +=
            original.data[todayIndex] +
            interest.data[todayIndex] +
            interestCumulative.data[todayIndex];
        } else {
          total +=
            original.data[todayIndex] +
            interest.data[todayIndex] +
            interestCumulative.data[todayIndex];
        }
      }

      result.push(interest);
      result.push(interestCumulative);

      return { dates, result, total, isFulled };
    },
  },
  beforeCreate: function () {
    document.body.className = "home_body";
  },
  created() {
    this.fetchMyPortfolio();
  },
};
</script>

<style scoped>
@import "../../assets/css/home.css";
</style>
