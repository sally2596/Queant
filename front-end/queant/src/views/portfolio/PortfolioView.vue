<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">내 포트폴리오</h1>
  </header>

  <div>
    <!-- 포트폴리오 없을 때 -->
    <div v-if="portfolio?.length === 0" class="portfolio-none">
      <img src="../../assets/image/물음표개미_none.png" alt="" style="width: 300px; height:280px;">
      <br><br>
      <h5>포트폴리오에 상품이 없습니다.</h5>
      <br><br>
      <router-link :to="{ name : 'portfolioEdit' }"><button class="btn btn-outline-success">포트폴리오에 상품 추가하기</button></router-link>
      
    </div>

    <!-- 포트폴리오 있을 때 -->
    <div v-else class="portfolio">
      <router-link :to="{ name: 'portfolioEdit' }"><button class="btn btn-outline-success">포트폴리오 관리하기</button></router-link><br>
      
      <!-- 포트폴리오 뭉텅이로 볼 때 -->
      <!-- {{ portfolio }} -->

      <!-- 포트폴리오의 상품을 개별로 볼 때 -->
      <!-- <div
        v-for="product in portfolio"
        :key="product">
				{{product}}
        상품이름 : {{ product.product.name }}
				시작일 : {{ product.start_date }}
				종료일 : {{ product.end_date }}
				amount : {{ product.amount }}
				기간 : {{ product.option.save_term }}
				기본이율 : {{ product.option.base_rate }}
				단복리 : {{ product.option.rate_type }}
				<div v-for="condition in product.conditions"
        :key="condition"> 추가이율 : {{ condition.special_rate }} </div> <br>
				<button class="btn btn-sm btn-outline-secondary" @click="saving(product.option.save_term,product.amount,product.option.base_rate,product.start_date,product.option.rate_type)">계산</button>
				<hr>
      </div> -->
			<div
        v-for="product in savingProduct"
        :key="product">
        상품이름 : {{ product.name }}
				amount : {{ product.amount }}
				기간 : {{ product.term }}
				이율 : {{ product.rate }}
				예적금 : {{ product.dep }}
				단복리 : {{ product.type }}
				<br>
				데이터셋 : {{ product.data }}
				<hr>
      </div>

      <!-- <div
        v-for="customProduct in customProducts"
        :key="customProduct">
        {{ customProduct }}
      </div> -->
    </div>

  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'PortfolioView',
  components: { Navbar },
	data() {
		return {
			savingProduct: [
			],
		}
	},
  computed: {
    ...mapGetters(['portfolio', 'customProducts'])
  },
  methods: {
    ...mapActions(['fetchMyPortfolio']),
		saving(term,amount,rate,st,type) {
			
		}
  },
  beforeCreate: function() {
    document.body.className = 'home_body'
  },
  created() {
    this.fetchMyPortfolio()
    // this.fetchCustomList()
		for(let i=0; i<this.portfolio.length; i++) {
			let name = this.portfolio[i].product.name;
			let term = this.portfolio[i].option.save_term;
			let amount = this.portfolio[i].amount;
			//단복리
			let type = this.portfolio[i].option.rate_type;
			//예적금
			let dep = this.portfolio[i].product.deposit;

			let start = this.portfolio[i].start_date.split('-');
			let year = parseInt(start[0]);
			let month = parseInt(start[1]);

			let rate = this.portfolio[i].option.base_rate;

			if(this.portfolio[i].conditions) {
				for(let j=0; j<this.portfolio[i].conditions.length; j++) {
					rate += this.portfolio[i].conditions[j].special_rate;
				}
			}

			const result = [
				{
					name: "원금",
					data: []
				},
				{
					name: "이번달 이자",
					data: []
				},
				{
					name: "누적 이자",
					data: []
				}
			]

			let sum = 0;

			if(type) {
				console.log("적금복리계산")
				for(let i=0; i<term; i++) {
					if (month==13) {
						year++;
						month=1;
					}
					let date = new Date(year,month,1);
					const msTime = date.getTime();
					let moneyOrigin = amount*(i+1);
					result[0].data.push([msTime,moneyOrigin]);
					const moneyInterest = Math.ceil(amount*((1+rate*0.01/term)**(term-i))-amount);
					result[1].data.push([msTime,moneyInterest]);
					sum += moneyInterest;
					result[2].data.push([msTime,sum]);
					//sum += amount*((1+(rate*0.01/term))**(term-i));

					month++;
				}
			} else {
			console.log("적금단리계산")
				for(let i=0; i<term; i++) {
					if (month==13) {
						year++;
						month=1;
					}
					let date = new Date(year,month,1);
					const msTime = date.getTime();
					let moneyOrigin = amount*(i+1);
					result[0].data.push([msTime,moneyOrigin]);
					const moneyInterest = Math.ceil(amount*rate*0.01*(term-i)/term);
					result[1].data.push([msTime,moneyInterest]);
					sum += moneyInterest;
					result[2].data.push([msTime,sum]);

					month++;
				}
			}		
			this.savingProduct.push({
				name:name,
				term:term,
				rate:rate,
				type:type,
				dep:dep,
				amount:amount,
				data: result
			});
		}
		console.log(this.savingProduct);	
  }
}
</script>

<style>
@import '../../assets/css/home.css';
</style>