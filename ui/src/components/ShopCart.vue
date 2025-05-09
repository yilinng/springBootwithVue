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
<template>shopcart</template>

<style></style>
