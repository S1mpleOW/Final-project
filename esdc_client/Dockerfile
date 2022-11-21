FROM node:16-alpine
WORKDIR /app/esdc/esdc_client
COPY . .
RUN npm install
EXPOSE 3000
CMD [ "npm", "start" ]
