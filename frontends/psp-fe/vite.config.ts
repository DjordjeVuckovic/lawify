import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        react()
    ],
    server: {
        https: {
            https: true,
            key: './certs/private-decrypted-key.pem',
            cert: './certs/dev-cert.pem'
        }
    }
})
