module.exports = {
    　　　devServer: {
        proxy: {
          '/api': {
            target: 'http://monktiger.natapp1.cc',
            changeOrigin: true,
            ws: true,
            pathRewrite: {
              '^/api': ''
            }
          }
        }
      }
    }