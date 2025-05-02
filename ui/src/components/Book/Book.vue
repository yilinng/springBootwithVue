<script setup>
import { useRoute, useRouter } from 'vue-router'
import { useBookStore } from '../../stores/book'
import { useUserStore } from '../../stores/user'

const bookStore = useBookStore()
const userStore = useUserStore()
const route = useRoute()
const router = useRouter()

bookStore.getBook(route.params.id)
bookStore.checkAuthorize()

function deleteBook() {
  if (window.confirm('Do you want to remove this book?')) {
    bookStore.removeBook(route.params.id)
  }
}

function ItemOwner(user_id) {
  if (userStore.user) {
    return user_id === userStore.user.id
  }
  return false
}

function backAction() {
  router.push('/bookList')
}
</script>

<template>
  <div class="book_details" v-if="bookStore.book">
    <div>book id: {{ $route.params.id }}</div>

    <h1 class="text-3xl my-4">title: {{ bookStore.book.title }}</h1>
    <div class="text-xl">
      <p>author: {{ bookStore.book.author }}</p>
    </div>
    <div class="text-xl my-2">
      <p>price: {{ bookStore.book.price }}</p>
    </div>

    <div class="date flex flex-row space-x-4">
      <p>create date: {{ bookStore.book.create_date }}</p>
      <p>update_date: {{ bookStore.book.update_date }}</p>
    </div>

    <div>
      <p>user_id: {{ bookStore.book.user_id }}</p>
    </div>

    <div class="back_btn my-4 flex flex-row space-x-4">
      <button
        class="bg-green-600 font-bold hover:bg-green-700 text-white focus:text-gray-400 focus:outline-none focus:ring-2 focus:ring-green-600 focus:ring-opacity-50 p-2"
        @click="backAction"
      >
        back
      </button>

      <button
        class="bg-blue-600 font-bold hover:bg-blue-700 text-white focus:text-gray-400 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:ring-opacity-50 p-2"
        v-if="userStore.haveAllAuthorize || ItemOwner(bookStore.book.user_id)"
      >
        <RouterLink
          class="navbar-brand"
          :to="{ name: 'EditBook', params: { id: bookStore.book.id } }"
        >
          edit
        </RouterLink>
      </button>

      <button
        class="bg-red-600 font-bold hover:bg-red-700 text-white focus:text-gray-400 focus:outline-none focus:ring-2 focus:ring-red-600 focus:ring-opacity-50 p-2"
        v-if="userStore.haveAllAuthorize || ItemOwner(bookStore.book.user_id)"
        @click="deleteBook"
      >
        delete
      </button>
    </div>
  </div>
</template>

<style></style>
