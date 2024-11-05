FROM node:18 AS build

WORKDIR /client/solarwatch

COPY solarwatch/package*.json ./
RUN npm install

COPY solarwatch .

EXPOSE 3000

CMD ["npm", "run", "dev"]
