export interface Login {
  email: string
  password: string
}

export interface Signup {
  username: string
  email: string
  password: string
}

export interface Role {
  id: number
  name: string
  description: string
}

export interface User {
  id: number
  username: string
  email: string
  roles: Role[]
  reservations: Reservation[]
  books: Book[]
}

export interface BaseBook {
  id: number
  title: string
  author: string
  create_date: string
  update_date: string
  price: number
  inventory: number
  reservations: Reservation[]
}

export interface Book extends BaseBook {
  user: User
}

export interface BookDto extends BaseBook {
  user_id: number
}

export interface BaseReservation {
  id: number
  title: string
  price: number
}

export interface Reservation extends BaseReservation {
  created_date: string
  update_date: string
  books: Book[]
  user: User
}

export interface ReservationDto extends BaseReservation {
  createDate: string
  updateDate: string
  books: Book[]
  user_id: number
}

export interface ReservationInfo {
  id: number
  title: string
  create_date: string
  update_date: string
  price: number
  books: Book[]
  user: User
}

export interface JWTAuthResponse {
  accessToken: string
  refreshToken: string
  message: string
  tokenType: string
  user: User
}

export interface BaseResponse {
  pageNo: number
  pageSize: number
  totalElements: number
  totalPages: number
  last: boolean
}

export interface UserResponse extends BaseResponse {
  content: User[]
}

export interface BooksResponse extends BaseResponse {
  content: BookDto[]
}

export interface ReservationsResponse extends BaseResponse {
  content: ReservationDto[]
}

export interface UserReservationsResponse {
  id: number
  username: string
  email: string
  reservations: ReservationInfo[]
}

export interface DeleteResponse {
  message: string
}

export type ReservationDefault = Omit<
  Reservation,
  'id' | 'price' | 'created_date' | 'update_date' | 'user'
>

export type BookDefault = Omit<
  Book,
  'id' | 'create_date' | 'update_date' | 'user' | 'reservations'
>
