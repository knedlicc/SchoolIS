const production = !process.env.ROLLUP_WATCH;

module.exports = {
  future: {
    removeDeprecatedGapUtilities: true,
    purgeLayersByDefault: true,
  },
  purge: {
    content: ["./src/**/*.svelte", "./src/**/*.html"],
    enabled: production,
  },
  theme: {
    fontFamily: {
      "sans-serif": ["Poppins"],
      sans: ["Poppins"],
    },
    extend: {
      colors: {
        bg: "#FFF",
        "app-main": "#DEE1E4",
        error: "#e82c2c",
        primary: "#0065bd",
      },
    },
  },
  plugins: [require("@tailwindcss/forms")],
};
