<script setup>
import { watch } from 'vue'

import { useUserStore } from '../../stores/user'

const userStore = useUserStore()

userStore.checkAuthorize()

userStore.getAllUser()
</script>
<template>
  <div class="user_list" v-if="userStore.haveAllAuthorize">
    <div
      class="book my-4 p-2 border-solid border-4 border-light-blue-500"
      v-for="item of userStore.users"
    >
      <h3 class="username">{{ item.username }}</h3>
      <h4 class="email">{{ item.email }}</h4>
    </div>
  </div>
</template>

<style></style>
