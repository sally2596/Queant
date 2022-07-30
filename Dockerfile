FROM node:10 as builder

WORKDIR /queant

# Copy the package.json and install dependencies
COPY ./front-end/queant/package*.json ./
RUN npm install

# Copy rest of the files
COPY ./front-end/queant .

# Build the project
RUN npm run build


FROM nginx:alpine as production-build
COPY ./nginx.conf /etc/nginx/nginx.conf

## Remove default nginx index page
RUN rm -rf /usr/share/nginx/html/*

# Copy from the stahg 1
COPY --from=builder /queant/dist /usr/share/nginx/html

EXPOSE 80
ENTRYPOINT ["nginx", "-g", "daemon off;"]