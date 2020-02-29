config.optimization = {
    moduleIds: 'hashed',
    runtimeChunk: 'single',
    splitChunks: {
        chunks: 'all',
        minSize: 0,
        maxAsyncRequests: Infinity,
        maxInitialRequests: Infinity,
        cacheGroups: {
            kita: {
                test: /[\\/]packages[\\/]/,
                name: (module) =>
                    module.context.match(/[\\/]packages[\\/](.*?)([\\/]|$)/)[1]
            },
            gradle: {
                test: /[\\/]packages_imported[\\/]/,
                name: (module) =>
                    "gradle." + module.context.match(/[\\/]packages_imported[\\/](.*?)([\\/]|$)/)[1]
            },
            npm: {
                test: /[\\/]node_modules[\\/]/,
                name: (module) =>
                    "npm." + module.context.match(/[\\/]node_modules[\\/](.*?)([\\/]|$)/)[1]
            },
            other: {
                name: 'main',
                priority: -1
            }
        }
    }
};
