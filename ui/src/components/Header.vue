<script setup>
import { watch } from 'vue'
import { useUserStore } from '../stores/user'
import { storeToRefs } from 'pinia'

const userStore = useUserStore()

function logout() {
  console.log('logout work')
  userStore.initState()
}
</script>
<template>
  <nav class="flex justify-between mt-2 shadow-xl">
    <div class="flex left_nav flex-row">
      <div class="header">
        <RouterLink class="navbar-brand text-3xl mr-2" to="/"
          >Navbar</RouterLink
        >
      </div>
      <div class="flex flex-row">
        <ul class="flex list-none flex-row space-x-4">
          <li>
            <RouterLink class="navbar-brand text-2xl" to="/bookList"
              >bookList</RouterLink
            >
          </li>
          <li v-if="userStore.token">
            <RouterLink class="navbar-brand text-2xl" to="/userInfo"
              >userInfo</RouterLink
            >
          </li>
          <li v-if="userStore.haveAllAuthorize">
            <RouterLink class="navbar-brand text-2xl" to="/userList"
              >userList</RouterLink
            >
          </li>
          <li v-if="userStore.token">
            <RouterLink class="navbar-brand text-2xl" to="/reservations"
              >reservations</RouterLink
            >
          </li>
        </ul>
      </div>
    </div>

    <div class="right_nav">
      <ul class="flex list-none flex-row space-x-4">
        <li v-if="!userStore.token">
          <RouterLink class="navbar-brand text-2xl" to="/login"
            >login</RouterLink
          >
        </li>
        <li v-if="!userStore.token">
          <RouterLink class="navbar-brand text-2xl" to="/signup"
            >signup</RouterLink
          >
        </li>
        <li v-if="userStore.token">
          <button class="navbar-brand text-2xl" @click="logout">logout</button>
        </li>
      </ul>
    </div>
  </nav>
</template>

<style scoped></style>
