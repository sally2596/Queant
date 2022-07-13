!<template>
  <div>
    전체 할일 : {{ todosCount }}
    완료 : {{ completedTodosCount }}
    남은일: {{ notCompletedTodosCount }}
    <todo-list-item
      v-for="todo in todos"
      :key="todo.date"
      :todo="todo"
    >
    </todo-list-item>
    <todo-form>

    </todo-form>
  </div>
</template>

<script>
import TodoListItem from '@/components/TodoListItem.vue'
import TodoForm from '@/components/TodoForm.vue'
import { mapGetters } from 'vuex'

export default {
  name: 'TodoView',
  components: {
    TodoListItem,
    TodoForm,
  },
  computed: {
    ...mapGetters(['completedTodosCount', 'notCompletedTodosCount', 'todosCount', 'todos']),
    // todos() {
    //   return this.$store.state.todos
    // },
  },
}
</script>


    TodoListItem<style>

</style>