import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    host: true,
    proxy: {
      "/api": `http://${process.env.BACKEND_URL ?? 'localhost'}:8080/`
    } 
  }
})
