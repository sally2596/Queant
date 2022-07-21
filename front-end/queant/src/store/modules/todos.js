export default {
  state: {
    todos: [
      {
        title: '할일1',
        isCompleted: false,
        date: new Date().getTime(),
      },
      {
        title: '할일2',
        isCompleted: false,
        date: new Date().getTime(),
      }
    ]
  },
  getters: {
    todos: state => state.todos,
    completedTodosCount(state) {
      return state.todos.filter(todo => {
        return todo.isCompleted === true
      }).length
    },
    notCompletedTodosCount(state) {
      return state.todos.filter(todo => {
        return todo.isCompleted === false
      }).length
    },
    todosCount(state) {
      return state.todos.length
    }
  },
  mutations: {
    CREATE_TODO(state, todoItem) {
      state.todos.push(todoItem)
    },
    DELETE_TODO(state, todoItem) {
      const index = state.todos.indexOf(todoItem)
      state.todos.splice(index, 1)
    },
    UPDATE_TODO_STATUS(state, todoItem) {
      state.todos = state.todos.map(todo => {
        if (todo === todoItem) {
          todo.isCompleted = !todo.isCompleted
        }
        return todo
      })
    }
  },
  actions: {
    createTodo(context, todoItem) {
      context.commit('CREATE_TODO', todoItem)
    },
    deleteTodo(context, todoItem) {
      context.commit('DELETE_TODO', todoItem)
    },
    updateTodoStatus(context, todoItem) {
      context.commit('UPDATE_TODO_STATUS', todoItem)
    }
  },
}