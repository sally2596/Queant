<template>
    <div class="modal33">
        <div class="overlay33">
            <div class="modal-card33">
                <h2>사용자정의 상품 추가</h2>
                <div class="int-area">
                    <input type="text" v-model="custom_product_dto.product_name" id="name">
                    <label class="form-label" for="name" autocomplete="off">* 상품 이름</label>
                </div>
                <div class="int-area">
                    <input type="text" v-model="custom_product_dto.institution_name" id="institute">
                    <label class="form-label" for="institute" autocomplete="off">* 기관명</label>
                </div>
                <div class="choose-area">
                    <select @change="setDeposit($event)">
                    <option selected disabled>선택</option>
                    <option value="true">예금</option>
                    <option value="false">적금</option>
                    </select>
                    <label>* 예적금 분류</label>
                </div>
                <div class="choose-area" v-show="this.deposit">
                    <select @change="setReserve($event)">
                        <option selected disabled>선택</option>
                        <option value="true">정액적립</option>
                        <option value="false">자유적립</option>
                    </select>
                    <label>* 적립방식</label>
                </div>
                <div class="int-area">
                    <input type="number" v-model="custom_product_dto.amount" id="amount">
                    <label class="form-label" for="amount" autocomplete="off">* 금액</label>
                </div>
                <div class="int-area">
                    <input type="number" v-model="custom_product_dto.base_rate" id="baserate">
                    <label class="form-label" for="baserate" autocomplete="off">* 기본금리</label>
                </div>
                <div class="int-area">
                    <input type="number" v-model="custom_product_dto.special_rate" id="special">
                    <label class="form-label" for="special" autocomplete="off">* 우대금리</label>
                </div>
                <div class="date-area">
                    <input
                    v-model="custom_product_dto.start_date"
                    type="date"
                    id="startdate">
                    <label for="startdate">시작일</label>
                </div>
                <div class="date-area">
                    <input
                    v-model="custom_product_dto.end_date"
                    type="date"
                    id="enddate">
                    <label for="enddate">종료일</label>
                </div>
                <div class="btn-area">
                    <button @click="addCustomProduct()">등록</button>
                </div>
                <div class="btn-area">
                    <button @click="$emit('close-modal')">취소</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import spring from '@/api/spring'
import { mapGetters } from 'vuex'

export default {
    methods:{
        setDeposit(event){
            if(event.target.value === "true") this.custom_product_dto.deposit = true;
            else this.custom_product_dto.deposit = false;
        },
        setReserve(event){
            this.custom_product_dto.fixed_rsrv = event.target.value;
        },
        change(){
            this.custom_product_dto.deposit = !this.custom_product_dto.deposit;
        },
        addCustomProduct(){
            console.log(this.custom_product_dto)
            console.log(this.userInfo.member_id)
            axios({
                url: spring.portfolio.custom(),
                method: 'post',
                data: {
                    member_id: this.userInfo.member_id,
                    custom_product_dto: this.custom_product_dto
                }
            })
            .then(res => {
                console.log(res)
                alert("사용자정의 상품 추가 완료")
            })
            .catch(err => {
                console.log(err)
            })
        }

    },
    computed: {
        ...mapGetters(['userInfo']),
        deposit : function (){
            return !this.custom_product_dto.deposit;
        }
    },
    data(){
        return {
            custom_product_dto:{
                product_name: "",
                institution_name: "",
                deposit: true,
                amount:0,
                base_rate: 0,
                special_rate: 0,
                start_date: "",
                end_date: "",
                etc: "",
                fixed_rsrv: null,
            }
        }
    }
}
</script>

<style>
    .modal33, .overlay33 {
        width: 100%;
        height: 100%;
        position: fixed;
        left:0;
        top:0;
    }

    .overlay33{
        opacity: 0.5;
        background-color: black;
    }

    .modal-card33{
        position: relative;
        max-width:80%;
        margin: auto;
        margin-top: 20%;
        padding:20px;
        background-color: white;
        min-height: 500px;
        z-index:10;
    }
</style>