<script setup>
import { watch, ref } from 'vue'
import { useBookStore } from '../../stores/book'
import { useDebounce } from '../../utils/useDebounce'

const bookStore = useBookStore()

const searchTxt = ref(null)
const loading = ref(false)

//https://www.freecodecamp.org/news/deboucing-in-react-autocomplete-example/
const loadDataDebounced = useDebounce(bookStore.searchByTitle, 400)

watch(
  bookStore,
  (state) => {
    console.log('watch state', state)
    if (state.searchBooks) {
      loading.value = true
    }
  },
  { deep: true }
)

watch(
  searchTxt,
  (state) => {
    console.log('watch searchTxt state', state)
    if (state) {
      loadDataDebounced(state)
    }
  },
  { deep: true }
)
</script>
<template>
  <div class="border">
    <input type="text" v-model.trim="searchTxt" placeholder="search.." />

    <div
      class="searchList"
      v-if="
        searchTxt &&
        loading &&
        bookStore.searchBooks &&
        bookStore.searchBooks.length
      "
    >
      <div
        class="bookSearch p-2 border-solid border-4 border-light-blue-500"
        v-for="book of bookStore.searchBooks"
      >
        <RouterLink
          class="navbar-brand"
          :to="{ name: 'Book', params: { id: book.id } }"
        >
          <div class="flex flex-col">
            <h2 class="text-2xl">
              {{
                book.title.length > 60
                  ? book.title.toString().substring(0, 60) + '...'
                  : book.title
              }}
            </h2>
            <p class="font-xl">price: ${{ book.price }}</p>
          </div>
        </RouterLink>
      </div>
    </div>
  </div>
</template>

<style></style>
