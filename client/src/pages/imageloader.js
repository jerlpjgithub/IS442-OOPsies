function importAll(r) {
    return r.keys().map(r);
}

export const images = importAll(require.context('../assets/eventImages', false, /\.(png|jpe?g|svg)$/));