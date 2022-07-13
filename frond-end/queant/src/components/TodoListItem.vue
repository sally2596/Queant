!<template>
  <div>
    <span @click="updateTodoStatus">
      {{ todo.title }}
    </span>
    <button @click="deleteTodo">
      Delete
    </button>
    <button
      :class="{'pointer': true, 'is-completed': todo.isCompleted }"
      @click="updateTodoStatus">
      {{ todo.isCompleted }}
    </button>
  </div>
</template>

<script>
export default {
  name: 'TodoListItem',
  props: {
    todo: Object,
  },
  methods: {
    deleteTodo() {
      this.$store.dispatch('deleteTodo', this.todo)
    },
    updateTodoStatus() {
      this.$store.dispatch('updateTodoStatus', this.todo)
    }
  }
}
</script>

<style>
.pointer {
  cursor: pointer;
}
.is-completed {
  text-decoration: line-through;
}
</style>