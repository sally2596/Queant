!<template>
  <div>
    <h2>Todo Form</h2>
    <input 
      type="text"
      v-model.trim="todoTitle"
      @keyup.enter="createTodo">
  </div>
</template>

<script>
export default {
  name: 'TodoForm',
  data() {
    return {
      todoTitle: '',
    }
  },
  methods: {
    createTodo() {
      const todoItem = {
        title: this.todoTitle,
        isCompleted: false,
        date: new Date().getTime()
      }
      if (todoItem.title) {
        this.$store.dispatch('createTodo', todoItem)
      }
    }
  }
}
</script>

<style>

</style>