# Use a Node image
FROM node:18 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the package.json and package-lock.json from solarwatch directory
COPY solarwatch/package*.json ./solarwatch/

# Install dependencies
RUN cd solarwatch && npm install

# Copy the rest of the application code from the solarwatch folder
COPY solarwatch ./solarwatch

# Expose Vite's default dev port
EXPOSE 3000

# Run the development server
CMD ["npm", "run", "dev", "--prefix", "solarwatch"]
