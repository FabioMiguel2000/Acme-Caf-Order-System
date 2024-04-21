# Acme-Caf-Order-System

<p align="center">
  <img height="300" src="https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/43adf4f2-69cf-4c31-b566-8962a2e98c79">

A cafeteria – the Acme Café – intends to implement a more efficient ordering and delivery system, supplying an Android app to its customers and incentivizing them to use it, through a loyalty campaign.

The idea is for the customers compose previously their orders in the app, choosing items from a menu and their quantities, and transmit them, together with possible vouchers and identification data, to a terminal inside the house. After that, the customers only need to collect the ordered items at the counter when they are ready.

## Demonstration Video 

https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/dc386684-2fd6-40fc-ac47-e690bd5ef79e

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

5. Install NodeJS dependencies
```bash
docker exec -it api npm install
```


6. Populate the database with seed, run:

```bash
docker exec -it api npm run data:import
```


7. If you've populated the database in step four, to destroy old structure and get new one, run the comand bellow and run again step four:
```bash
docker exec -it api npm run data:destroy
```

#### Run Application

1. Head inside the app's project directory `/coffee-order-application`, and add the server's base URL to the `local.properties`, accordingly:

If you are using a physical device:
```bash
apiBaseUrl=http://your_local_ip_address/api/
```

Example:
```bash
apiBaseUrl=http://192.168.1.97:3000/api/
```

If you are running on an emulator you can keep with the default base URL:
```bash
apiBaseUrl=http://10.0.2.2:3000/api/
```



