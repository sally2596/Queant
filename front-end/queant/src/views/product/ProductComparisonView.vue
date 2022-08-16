<template>
  <Navbar />
  <header id="title-div">
    <h1 class="title" id="title">상품 비교하기</h1>
  </header>
  <section class="product-detail-box">
    <div v-if="comparisonPortfolio.length === 0" class="cart-none">
      <img
        src="../../assets/image/물음표개미_none.png"
        alt="없음"
        style="width: 30%"
      />

      <br /><br />
      <h5>가상 포트폴리오가 없습니다.</h5>
      <br /><br />

      <h6>
        <strong
          ><router-link :to="{ name: 'productCart' }">상품 저장소</router-link
          >에서 포트폴리오를 추가해보세요.</strong
        >
      </h6>
    </div>

    <div v-else>
      <div class="product-detail d-flex flex-wrap justify-content-center">
        <div
          class="m-2 p-3 border border-1 d-grid gap-2"
          v-for="cportfolio in comparisonPortfolio"
          :key="cportfolio"
          id="cportfolio"
          style="height: 50vh"
        >
          <div
            class="border p-2"
            style="height: 50px; background-color: #92ce95; font-family: 'jua'"
          >
            <h5 class="text-center m-0">
              예상 포트폴리오 {{ cportfolio.cportfolio_cnt }}
            </h5>
          </div>

          <div v-if="cportfolio.products.length === 0">
            <div>아직 상품이 없습니다.</div>
          </div>

          <div v-else>
            <h2>상품 목록</h2>
            <div
              v-for="cproduct in cportfolio.products"
              :key="cproduct"
              class="d-flex"
              style="font-size: 15px"
            >
              {{ cproduct.product.name }}
              {{ cproduct.amount }}
              <button
                style="height: 1.2rem; font-size: 5px"
                class="d-flex p-0 btn btn-outline btn-sm"
                @click="
                  popProductFromCPortfolio([
                    cportfolio.cportfolio_cnt,
                    cproduct,
                  ])
                "
              >
                상품삭제
              </button>
            </div>
            <div>예상 이익금 :</div>
          </div>
        </div>
        <column-chart-comparison
          v-if="series.length > 0"
          v-bind:series="series"
          v-bind:category="categories"
          class="col-lg-6"
        ></column-chart-comparison>
        <div v-for="cportfolio in comparisonPortfolio" :key="cportfolio">
          {{ cportfolio }}
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import { useStore } from "vuex";
import { reactive, ref, watch } from "vue";
import Navbar from "@/components/Navbar.vue";
import { mapActions, mapGetters, mapMutations } from "vuex";
import ColumnChartComparison from "@/components/ColumnChartComparison.vue";
export default {
  name: "ProductComparisonView",
  components: { Navbar, ColumnChartComparison },
  computed: {
    ...mapGetters(["isLoggedIn", "userInfo", "comparisonPortfolio"]),
    total(number) {
      let sum_value = 0;
      for (let product of this.comparisonPortfolio[number].products) {
        sum_value += product.amount;
      }
    },
  },
  methods: {
    ...mapMutations([
      "POP_PRODUCT_FROM_CART",
      "ADD_COMPARISON_PORTFOLIO",
      "POP_CPORTFOLIO",
      "POP_PRODUCT_FROM_CPORTFOLIO",
    ]),
    ...mapActions(["saveDb", "getFromDb"]),
    popCPortfolioFromCPortfolios(portfolioIdx) {
      this.POP_CPORTFOLIO(portfolioIdx);
    },
    popProductFromCPortfolio(value) {
      this.POP_PRODUCT_FROM_CPORTFOLIO(value);
    },
    addcomparisonportfolio() {
      this.ADD_COMPARISON_PORTFOLIO();
    },
    renderChart() {
      this.isComputed = true;
    },
  },
  data() {
    const calculate2 = function (amount, rate, simple, term, deposit) {
      let original = 0;

      if (deposit) original = term * amount;
      else {
        for (var i = 0; i <= term; i++) {
          if (i === term) original += amount * i;
          else original += amount * (i + 1);
        }
      }

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
        cumulMoney += calcMoney;
        //복리일 경우 amount 보정
        if (deposit && simple) amount *= calcRate;
      }
      cumulMoney = Math.ceil(cumulMoney);
      return { original, cumulMoney };
    };

    // 전체 계산 값 넣어둘 배열

    const cPortfolioDepositMymoney = []; // 예금 예치금
    const cPortfolioDepositInterest = []; //예금 이자
    const cPortfolioSavingMymoney = []; //적금 총 불입금
    const cPortfolioSavingInterest = []; //적금 이자

    const store = useStore();
    watch(
      () => store.getters.comparisonPortfolio,
      function () {
        console.log("포트폴리오 바뀜 ==> 전체 애들 값 계산");
        series.splice(0);
        categories.splice(0);

        console.log(store.getters.comparisonPortfolio);
        store.getters.comparisonPortfolio.forEach((item) => {
          let depositMyMoney = 0;
          let savingMyMoney = 0;
          let depositInterest = 0;
          let savingInterest = 0;

          item.products.forEach((product) => {
            let result = calculate2(
              product.amount,
              product.total_rate,
              product.rate_type,
              product.term,
              product.isDeposit
            );
            let original = result.original;
            let interest = result.cumulMoney;
            if (product.isDeposit) {
              depositMyMoney += original;
              depositInterest += interest;
            } else {
              savingMyMoney += original;
              savingInterest += interest;
            }
          });
          categories.push(item.cportfolio_cnt + "번 포트폴리오");
          cPortfolioDepositMymoney.push(depositMyMoney);
          cPortfolioDepositInterest.push(depositInterest);
          cPortfolioSavingMymoney.push(savingMyMoney);
          cPortfolioSavingInterest.push(savingInterest);
        });
        series.push({
          name: "적금 총 불입금",
          data: cPortfolioDepositMymoney,
        });
        series.push({
          name: "적금 총 이자",
          data: cPortfolioDepositInterest,
        });
        series.push({ name: "예금 예치금", data: cPortfolioSavingMymoney });
        series.push({ name: "예금 총 이자", data: cPortfolioSavingInterest });
      }
    );

    let series = [];
    let categories = [];
    let realSeries = [];
    let realCategories = [];

    let isComputed = false;

    return {
      series,
      categories,
      isComputed,
      realSeries,
      realCategories,
    };
  },
  beforeCreate: function () {
    document.body.className = "home_body";
  },
  mounted() {
    this.getFromDb();
  },
};
</script>

<style>
@import "../../assets/css/home.css";
</style>
