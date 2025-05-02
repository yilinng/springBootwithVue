import { createWebHistory, createRouter } from 'vue-router'

import Home from '../components/Home.vue'
import Signup from '../components/Signup.vue'
import Login from '../components/Login.vue'
import BookList from '../components/Book/BookList.vue'
import Book from '../components/Book/Book.vue'
import BookForm from '../components/Book/BookForm.vue'
import UserInfo from '../components/User/UserInfo.vue'
import UserList from '../components/User/UserList.vue'
import ReservationList from '../components/Reservation/ReservationList.vue'
import Reservation from '../components/Reservation/Reservation.vue'
import ReservationForm from '../components/Reservation/ReservationForm.vue'

//https://www.reddit.com/r/vuejs/comments/1conr9i/why_vuerouter_is_not_routing_to_the_correct_page/
const routes = [
  { path: '/', component: Home },
  { path: '/signup', component: Signup, name: 'Signup' },
  { path: '/login', component: Login, name: 'Login' },
  { path: '/bookList', component: BookList, name: 'BookList' },
  { path: '/bookList/:id', component: Book, name: 'Book' },
  { path: '/bookList/create', component: BookForm, name: 'CreateBook' },
  { path: '/bookList/edit/:id', component: BookForm, name: 'EditBook' },
  {
    path: '/reservations',
    component: ReservationList,
    name: 'ReservationList',
  },
  { path: '/reservations/:id', component: Reservation, name: 'Reservation' },
  {
    path: '/reservations/create',
    component: ReservationForm,
    name: 'CreateReservation',
  },
  {
    path: '/reservations/edit/:id',
    component: ReservationForm,
    name: 'EditReservation',
  },
  { path: '/userInfo', component: UserInfo, name: 'UserInfo' },
  { path: '/userList', component: UserList, name: 'UserList' },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
