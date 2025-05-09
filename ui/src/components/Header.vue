<script setup>
import { watch, ref } from 'vue'
import { useUserStore } from '../stores/user'
import { storeToRefs } from 'pinia'

const userStore = useUserStore()
const showMenu = ref(false)

function logout() {
  console.log('logout work')
  userStore.initState()
}

function clickMenu() {
  showMenu.value = !showMenu.value
}

function closeMenu() {
  showMenu.value = false
}

watch(
  userStore,
  (state) => {
    console.log('watch user state from Header.vue', state)
  },
  { deep: true }
)
</script>
<template>
  <nav class="flex justify-between mt-2 shadow-xl">
    <div class="flex left_nav flex-row">
      <div class="header">
        <RouterLink class="navbar-brand text-3xl mr-2" to="/"
          >Navbar</RouterLink
        >
      </div>
      <div class="flex flex-row hidden md:block">
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

      <div class="block md:hidden" @click="clickMenu">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          stroke="currentColor"
          class="size-6"
          width="40"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            d="M3.75 5.25h16.5m-16.5 4.5h16.5m-16.5 4.5h16.5m-16.5 4.5h16.5"
          />
        </svg>
      </div>

      <Transition :duration="550" name="slide-fade">
        <div
          class="block md:hidden absolute bg-white inset-0 transition-all duration-500 ease"
          v-if="showMenu"
        >
          <div class="flex justify-end m-2" @click="closeMenu">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              class="size-6"
              width="30"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M6 18 18 6M6 6l12 12"
              />
            </svg>
          </div>
          <ul class="flex list-none flex-col my-8 space-y-4">
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
      </Transition>
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
