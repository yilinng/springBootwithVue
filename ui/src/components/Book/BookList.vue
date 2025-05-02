<script setup>
import { watch } from 'vue'
import { useBookStore } from '../../stores/book'
import { useUserStore } from '../../stores/user'

const bookStore = useBookStore()
const userStore = useUserStore()

bookStore.getBooks()
bookStore.checkAuthorize()

userStore.checkAuthorize()

/*
watch(
  bookStore,
  (state) => {
    console.log('watch state', state)
  },
  { deep: true }
)
*/

function substringTxt(txt) {
  if (txt.length > 40) {
    return txt.subString(0, 40) + '...'
  }
  return txt
}
</script>

<template>
  <div class="flex flex-col">
    <div
      class="create_book_url bg-blue-500 text-white m-2 p-2 w-36"
      v-if="userStore.haveAuthorize"
    >
      <RouterLink class="navbar-brand text-2xl" to="/bookList/create"
        >create book</RouterLink
      >
    </div>
    <div
      class="book my-4 p-2 border-solid border-4 border-light-blue-500"
      v-for="book of bookStore.books"
    >
      <RouterLink
        class="navbar-brand"
        :to="{ name: 'Book', params: { id: book.id } }"
      >
        <div class="flex flex-col">
          <h2 class="text-2xl">{{ substringTxt(book.title) }}</h2>
          <p class="font-xl">create date: {{ book.create_date }}</p>
        </div>
      </RouterLink>
    </div>
  </div>
</template>

<style></style>
