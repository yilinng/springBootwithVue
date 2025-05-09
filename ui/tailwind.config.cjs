const colors = require('tailwindcss/colors')

module.exports = {
  theme: {
    purge: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
    container: {
      center: true,
    },
    colors: {
      gray: colors.coolGray,
      blue: colors.lightBlue,
      red: colors.rose,
      pink: colors.fuchsia,
      lime: colors.lime,
      green: colors.emerald,
      white: colors.white,
    },
    fontFamily: {
      sans: ['Graphik', 'sans-serif'],
      serif: ['Merriweather', 'serif'],
    },
    extend: {
      spacing: {
        128: '32rem',
        144: '36rem',
      },
      borderRadius: {
        '4xl': '2rem',
      },
      transitionProperty: { size: 'width, height' },
    },
  },
  variants: {
    extend: {
      borderColor: ['focus-visible'],
      opacity: ['disabled'],
    },
  },
}
