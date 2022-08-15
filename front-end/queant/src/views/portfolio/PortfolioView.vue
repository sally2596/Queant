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
            <h4>총 투자액</h4>
            <h2>{{ filtered(depositTotalAmount + savingTotalAmount) }}원</h2>
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
          <!-- <time-line-chart v-bind:series="savingTerm"></time-line-chart> -->
          <!-- <bar-chart v-bind:series="savingSeries"></bar-chart> -->
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
                  <button class="btn btn-outline-success">보기</button>
                </td>
              </tbody>
            </table>
            <column-chart
              v-bind:series="testSeries"
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
            <!-- <time-line-chart v-bind:series="depositTerm"></time-line-chart> -->
            <!-- <bar-chart v-bind:series="depositSeries" class="col-6"></bar-chart> -->
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

      <!-- <line-chart v-bind:series="testSeries"></line-chart> -->

      <div v-for="product in portfolio" :key="product">
        {{ product }}
      </div>
      <div v-for="customProduct in customProducts" :key="customProduct">
        {{ customProduct }}
      </div>
    </div>
  </div>
  <div>
    {{ depositCategory }}
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

    const filtered = (val) => String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    const computeDBProductRate = (item) => {
      let rate = 0;
      rate += item.option.base_rate;
      item.conditions.forEach((element) => {
        rate += element.special_rate;
      });
      rate = Math.round(rate * 1000) / 1000;
      return rate;
    };
    const computeCustomRate = (item) => {
      let rate = 0;
      rate = item.base_rate + item.special_rate;
      rate = Math.round(rate * 1000) / 1000;
      return rate;
    };
    const savingTerm = [];
    const depositTerm = [];
    const testSeries = [
      {
        name: "월 불입금",
        data: [
          [1484418600000, 200],
          [1484418600000 + 86400000, 2500],
          [1484418600000 + 86400000 + 86400000, 2000],
          [1484418600000 + 86400000 + 86400000 + 86400000, 2000],
          [1484418600000 + 86400000 + 86400000 + 86400000 + 86400000, 2000],
          [
            1484418600000 +
              86400000 +
              86400000 +
              86400000 +
              86400000 +
              86400000,
            2000,
          ],
        ],
      },
      {
        name: "이번달에 붙은 이자",
        data: [
          [1484418600000, 40],
          [1484418600000 + 86400000, 500],
          [1484418600000 + 86400000 + 86400000, 600],
          [1484418600000 + 86400000 + 86400000 + 86400000, 700],
          [1484418600000 + 86400000 + 86400000 + 86400000 + 86400000, 800],
          [
            1484418600000 +
              86400000 +
              86400000 +
              86400000 +
              86400000 +
              86400000,
            900,
          ],
        ],
      },
    ];

    const depositChart = [];
    const depositCategory = [];
    const nowDepositChart = [];
    const nowDepositCategory = [];
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

      savingTerm,
      depositTerm,

      depositChart,
      depositCategory,
      nowDepositChart,
      nowDepositCategory,
      //testing
      testSeries,
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
    TestcalculateDeposit(start_date, end_date, amount, rate, simple) {
      let result = [];

      let start = start_date.split("-");
      let end = end_date.split("-");
      let startYear = parseInt(start[0]);
      let endYear = parseInt(end[0]);
      let dates = [];
      let amounts = [];

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
    temp() {
      for (let i = 0; i < this.portfolio.length; i++) {
        let name = this.portfolio[i].product.name;
        let term = this.portfolio[i].option.save_term;
        let amount = this.portfolio[i].amount;
        //단복리
        let type = this.portfolio[i].option.rate_type;
        //예적금
        let dep = this.portfolio[i].product.deposit;

        let start = this.portfolio[i].start_date.split("-");
        let year = parseInt(start[0]);
        let month = parseInt(start[1]);

        let rate = this.portfolio[i].option.base_rate;

        if (this.portfolio[i].conditions) {
          for (let j = 0; j < this.portfolio[i].conditions.length; j++) {
            rate += this.portfolio[i].conditions[j].special_rate;
          }
        }

        const result = [
          {
            name: "원금",
            data: [],
          },
          {
            name: "이번달 이자",
            data: [],
          },
          {
            name: "누적 이자",
            data: [],
          },
        ];

        let sum = 0;

        if (type) {
          console.log("적금복리계산");
          for (let i = 0; i < term; i++) {
            if (month == 13) {
              year++;
              month = 1;
            }
            let date = new Date(year, month, 1);
            const msTime = date.getTime();
            let moneyOrigin = amount * (i + 1);
            result[0].data.push([msTime, moneyOrigin]);
            const moneyInterest = Math.ceil(
              amount * (1 + (rate * 0.01) / term) ** (term - i) - amount
            );
            result[1].data.push([msTime, moneyInterest]);
            sum += moneyInterest;
            result[2].data.push([msTime, sum]);
            //sum += amount*((1+(rate*0.01/term))**(term-i));

            month++;
          }
        } else {
          console.log("적금단리계산");
          for (let i = 0; i < term; i++) {
            if (month == 13) {
              year++;
              month = 1;
            }
            let date = new Date(year, month, 1);
            const msTime = date.getTime();
            let moneyOrigin = amount * (i + 1);
            result[0].data.push([msTime, moneyOrigin]);
            const moneyInterest = Math.ceil(
              (amount * rate * 0.01 * (term - i)) / term
            );
            result[1].data.push([msTime, moneyInterest]);
            sum += moneyInterest;
            result[2].data.push([msTime, sum]);

            month++;
          }
        }
        this.savingProduct.push({
          name: name,
          term: term,
          rate: rate,
          type: type,
          dep: dep,
          amount: amount,
          data: result,
        });
      }
      console.log(this.savingProduct);
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
        // 일할 계산 or 전체 계산?
        // 예금이면 이미 들어간 돈이고 이자가 붙는거니까 이자 일할 계산해서 붙여주기?
        this.depositTotalAmount += item.amount;
        this.depositTotalRate += rate;
      } else {
        // 적금은 한달에 들어가는 돈만 보여주는 걸로?
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

        this.depositTerm.push({ name: productName, data: data });

        let result = this.TestcalculateDeposit(
          item.start_date,
          item.end_date,
          item.amount,
          rate / 100,
          item.option.rate_type
        );
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

        this.savingTerm.push({ name: productName, data: data });
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

        this.depositTerm.push({ name: productName, data: data });
      } else {
        this.savingSeries.push({
          name: productName,
          data: amount,
          picture: picture,
          rate: rate,
          startDate: startDate,
          endDate: endDate,
        });
        this.savingTerm.push({ name: productName, data: data });
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
