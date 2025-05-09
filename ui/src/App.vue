<script setup>
import Headers from './components/Header.vue'
import Footer from './components/Footer.vue'
import Main from './components/Main.vue'

import { useUserStore } from './stores/user'
import { computed, watch } from 'vue'

import { storeToRefs } from 'pinia'

const userStore = useUserStore()

userStore.checkAuthorize()

watch(
  userStore,
  (state) => {
    console.log('watch user state from app.vue', state)
  },
  { deep: true }
)
</script>

<template>
  <div class="container mx-auto">
    <Headers />
    <Main />
    <Footer />
  </div>
</template>

<style scoped></style>
