# Acme-Caf-Order-System

A cafeteria – the Acme Café – intends to implement a more efficient ordering and delivery system, supplying an Android app to its customers and incentivizing them to use it, through a loyalty campaign.

The idea is for the customers compose previously their orders in the app, choosing items from a menu and their quantities, and transmit them, together with possible vouchers and identification data, to a terminal inside the house. After that, the customers only need to collect the ordered items at the counter when they are ready.

## Installing the Software Dependencies

To prepare your computer for development you need to install [Docker Desktop](https://www.docker.com/products/docker-desktop/).

### Local Setup

#### Deploy Local Server 

1. Clone the repository

```bash
git clone https://github.com/FabioMiguel2000 Acme-Caf-Order-System
```

2. Head inside the server's project directory `/coffee-order-api`, and change the file `local.env` to `.env` with the appropriate environment variables.

```
MONGODB_URI=mongodb://mongo:27017/acme-coffee
```

4. There is a docker-compose file that builds and runs 3 containers, the node server, the MongoDB server and the Mongo-Express (admin interface):

```bash
docker-compose up -d
```

5. Populate the database with seed, run:

```bash
docker exec -it api npm run data:import
```
6. Install node js dependences [inside node.js server]
   ```
  npm install 
   ````
