FROM nginx
#RUN rm /etc/nginx/conf.d/default.conf
COPY dist/Dr-Krishi /usr/share/nginx/html/
COPY nginx.conf /etc/nginx/nginx.conf
COPY mime.types /etc/nginx/conf/mime.types
