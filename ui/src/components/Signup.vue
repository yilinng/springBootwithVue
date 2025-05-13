<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '../stores/user'

const userStore = useUserStore()

const username = ref('')
const email = ref('')
const password = ref('')

function signup() {
  if (!username.value || !email.value || !password.value) {
    return
  }
  userStore.signup({
    username: username.value,
    email: email.value,
    password: password.value,
  })
}
</script>

<template>
  <div class="bg-gray-50 p-4 table md:block m-auto">
    <h2 class="flex justify-center text-3xl my-4 md:m-auto">signup</h2>
    <form
      @submit.prevent="signup"
      class="flex flex-col items-baseline md:items-center space-y-2"
    >
      <div class="username">
        <label for="username">username: </label>
        <input
          type="text"
          v-model="username"
          class="border border-transparent focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
        />
      </div>

      <div class="email">
        <label for="email">email: </label>
        <input
          type="text"
          v-model="email"
          class="border border-transparent focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
        />
      </div>

      <div class="password">
        <label for="password">password: </label>
        <input
          type="text"
          v-model="password"
          class="border border-transparent focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
        />
      </div>

      <div class="flex justify-center">
        <button
          class="border-2 border-lime-500 border-opacity-75 text-lime-800 hover:text-lime-500 w-48 font-bold text-xl focus:outline-none mx-4"
        >
          Sign up
        </button>

        <div class="login_url text-gray-400 hover:text-gray-900">
          <RouterLink class="navbar-brand text-2xl" to="/login"
            >login</RouterLink
          >
        </div>
      </div>

      <div class="flex flex-col">
        <p>userStore.message: {{ userStore.message }}</p>
        <p>admin: xxx@admin.edu</p>
        <p>employee: xxx@employee.edu</p>
      </div>
    </form>
  </div>
</template>

<style></style>
