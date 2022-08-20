FROM nginx:stable-alpine

COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY ./F697343443DACE02267D6A80F5FC5C36.txt /front/F697343443DACE02267D6A80F5FC5C36.txt

EXPOSE 80

CMD ["nginx","-g","daemon off;"]