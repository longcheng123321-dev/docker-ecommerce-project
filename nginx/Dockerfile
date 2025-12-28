FROM nginx:alpine

# 复制自定义配置文件
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 健康检查（加分项）
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost/ || exit 1

EXPOSE 80