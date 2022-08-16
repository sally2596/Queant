<template>
    <transition on name="modal">
   <div class="modal-mask">
    <div class="modal-wrapper">
     <div class="modal-container">

      <div class="modal-header">
       <slot name="header">
        <h4 style="font-family: 'jua'; margin-top: 1rem;">{{ modalData.name }}</h4>
       </slot>
      </div>
      <p class="h1 m-0"><b-icon-x-circle type="button" class="modal-close-button"
      @click="$emit('close')"/>
			</p>
      <hr>

      <div class="modal-body">
       <slot name="body">
          <label>납임금액(원)</label>
          <input
            v-model="payload.amount"
            type="number"
						class="box"
            required>
					<br><br>
          <button class="btn btn-outline-success btn-sm mx-1" @click="changeAmount(10000)">+1만원</button>
          <button class="btn btn-outline-success btn-sm mx-1" @click="changeAmount(50000)">+5만원</button>
          <button class="btn btn-outline-success btn-sm mx-1" @click="changeAmount(100000)">+10만원</button>
          <button class="btn btn-outline-danger btn-sm mx-1" @click="changeAmount(-10000)">-1만원</button>
          <button class="btn btn-outline-danger btn-sm mx-1" @click="changeAmount(-50000)">-5만원</button>
          <button class="btn btn-outline-danger btn-sm mx-1" @click="changeAmount(-100000)">-10만원</button>
					<br>
          <p>{{ error.amount }}</p>
          이자유형 & 개월수
          <select class="box" v-model="payload.option_id">
            <option disabled>선택</option>
            <option 
              v-for="option in product.options"
              :key="option"
              :value="option.option_id"
              required>
              <p v-if="option.rate_type">복리</p>
              <p v-else>단리</p>
              {{ option.save_term }}개월
              {{ option.base_rate }}%
            </option>
          </select>
          <hr>
					<div>우대사항</div>
					<div></div>
					<br>
					<table class="table table-hover">
						<thead>
							<tr class="text-center">
								<th scope="col">설명</th>
								<th scope="col">추가금리</th>
								<th scope="col">선택</th>
							</tr>
						</thead>
						<tbody v-for="condition in product.conditions" v-bind:key="condition">
							<tr scope="row">
								<td class="col-8 text-center">{{ condition.condition_info || condition.value }}</td>
								<td class="col-4 text-center">{{ condition.special_rate }}%</td>
								<td class="col-1 text-center">
									<input
										v-model="payload.condition_ids"
										type="checkbox"
										class="box"
										:value="condition.condition_id"
										:id="condition.condition_id">
								</td>
							</tr>
						</tbody>
					</table>
					<hr>
					<div>
						예상 가입일
						<input 
							v-model="payload.start_date"
							type="date"
							class="box"
							required>
					</div>
					<br>
					
       </slot>
      </div>

      <div class="modal-footer">
       <slot name="footer">
        <button class="btn btn-outline-success btn-sm mx-3" @click="$emit('close')">수정</button>
       </slot>
      </div>
     </div>
    </div>
   </div>
  </transition>
</template>

<script>
export default {
  name: 'UserModal',
  props: {
    modalData: Object
  },
  data() {
    return {
      credentials: {
        email: this.user.email,
        role_set: this.user.role_set,
      }
    }
  },
}
</script>

<style>

</style>