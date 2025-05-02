<script setup>
import { watch } from 'vue'

import { useUserStore } from '../../stores/user'

const userStore = useUserStore()

watch(
  userStore,
  (state) => {
    console.log('watch state', state)
  },
  { deep: true }
)
</script>
<template>
  <div class="user_info flex flex-col my-4 text-xl" v-if="userStore.user">
    <div>username: {{ userStore.user.username }}</div>
    <div>email: {{ userStore.user.email }}</div>
  </div>
</template>

<style></style>
