<script setup>
import { watch, ref, computed } from 'vue'
import { useBookStore } from '../stores/book'

import BookSearch from '../components/Book/BookSearch.vue'

const bookStore = useBookStore()

bookStore.getBooks()

const loading = ref(false)

//https://vuejs.org/guide/essentials/computed.html#computed-properties
const sliceBooks = computed(() => {
  if (bookStore.books.length) {
    return bookStore.books.slice(-5)
  }
})
</script>

<template>
  <div class="home p-2">
    <h2>Hot New Releases</h2>
    <div class="flex flex-wrap md:flex-nowrap my-4">
      <div
        class="book p-2 border-solid border-2 border-light-blue-500 w-full md:w-1/2"
        v-for="book of sliceBooks"
      >
        <RouterLink
          class="navbar-brand"
          :to="{ name: 'Book', params: { id: book.id } }"
        >
          <div class="flex flex-col">
            <h2 class="text-xl h-24">
              {{
                book.title.length > 60
                  ? book.title.toString().substring(0, 60) + '...'
                  : book.title
              }}
            </h2>
            <p class="font-xl self-end">price: ${{ book.price }}</p>
          </div>
        </RouterLink>
      </div>
    </div>
    <BookSearch />
  </div>
</template>

<style></style>
