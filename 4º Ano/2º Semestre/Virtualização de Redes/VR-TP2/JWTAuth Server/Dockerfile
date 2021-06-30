FROM node:14
WORKDIR .
COPY /package*.json ./
USER node
COPY . .
COPY --chown=node:node . .
USER node
EXPOSE 8002
ENTRYPOINT ["npm", "start"]