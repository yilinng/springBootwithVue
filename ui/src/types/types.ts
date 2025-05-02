export interface Customer {
  id: number
  name: string
  username: string
  email: string
  password: string
  books: Book[]
}

export interface Book {
  id: number
  title: string
  author: string
  price: number
  create_Date?: string
  update_Date?: string
}

export interface Login {
  email: string
  password: string
}

export interface Signup {
  username: string
  email: string
  password: string
}

export interface AuthResponse {
  accessToken: string | null
  refreshToken: string | null
  message: string | null
}

export type CustomerFromValue = Omit<Customer, 'id' | 'password'>
