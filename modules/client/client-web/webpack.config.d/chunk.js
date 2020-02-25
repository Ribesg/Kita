config.optimization = {
    moduleIds: 'hashed',
    runtimeChunk: 'single',
    splitChunks: {
        cacheGroups: {
            vendor: {
                test: /[\\/]node_modules[\\/]/,
                name: function (module) {
                    const packageName = module.context.match(/[\\/]node_modules[\\/](.*?)([\\/]|$)/)[1];
                    return `npm.${packageName.replace('@', '')}`;
                },
                chunks: 'all'
            }
        }
    }
};
