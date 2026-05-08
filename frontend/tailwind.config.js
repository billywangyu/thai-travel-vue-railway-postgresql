export default {
  content: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        primary: { DEFAULT: '#FFD54F', foreground: '#1A1A1A' },
        secondary: { DEFAULT: '#B71C1C', foreground: '#FFFFFF' },
        accent: { DEFAULT: '#FF8A65', foreground: '#1A1A1A' },
        background: '#FFF8F0',
        foreground: '#2C1810',
        card: { DEFAULT: '#FFFFFF', foreground: '#2C1810' },
        muted: { DEFAULT: '#FFE0CC', foreground: '#8B5A3C' },
        border: '#FFCC99',
        ring: '#FFD54F'
      },
      fontFamily: { display: ['Playfair Display', 'serif'], body: ['Poppins', 'sans-serif'] }
    }
  },
  plugins: []
}
