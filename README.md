# Acme-Caf-Order-System

![Kotlin](https://img.shields.io/badge/Kotlin-v1.9.0-purple?&color=7f52ff)
![Node](https://img.shields.io/badge/node-v20.11.1-green)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

<p align="center">
  <img height="300" src="https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/43adf4f2-69cf-4c31-b566-8962a2e98c79">

## Overview

A cafeteria – the Acme Café – intends to implement a more efficient ordering and delivery system, supplying an Android app to its customers and incentivizing them to use it, through a loyalty campaign.

The idea is for the customers compose previously their orders in the app, choosing items from a menu and their quantities, and transmit them, together with possible vouchers and identification data, to a terminal inside the house. After that, the customers only need to collect the ordered items at the counter when they are ready.

## Demonstration Video 

https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/dc386684-2fd6-40fc-ac47-e690bd5ef79e

## Screens
### Customer Application
| Login | Register | Home Menu |
|-------|----------|-----------|
| ![Login](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/3ae82c17-50c4-4ef0-93ef-cfeee63b817e) | ![Register](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/8f43db57-5500-4ca9-853c-c95dbf257ffc) | ![Home Menu](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/0bb34354-98e3-4c98-84ce-127d94e7ee17) 

| Hot Coffees | Cart Empty | Apply Coffee Voucher |
|-------|----------|-----------|
| ![Hot Coffees](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/53ae1032-aabe-4eac-9155-6006702d1915) | ![Cart Empty](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/3d096be2-16ec-4e2e-8f58-f8fa8b296517) | ![Apply Coffee Voucher](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/e188ddcb-15ce-4242-b058-7ad6410f1ab4) 

| Apply Discount Voucher | Cart Filled | Checkout |
|-------|----------|-----------|
| ![Apply Discount Voucher](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/33ee0d26-63df-437e-9fe4-547d5a1e1f5d) | ![Cart Filled](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/b8c0115a-4c18-4ec5-aade-c0b7210041bc) | ![Checkout](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/a0bbb99c-bdc8-4fe7-a9f1-daed26d4de32) 

| Order Successful | Profile | All Available Vouchers |
|-------|----------|-----------|
| ![Order Successful](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/ab25cbbd-65a2-4585-8795-6c4a1ff87b9c) | ![Profile](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/0049b612-12a3-4ea6-b9a2-e21f58b6cb24) | ![All Available Vouchers](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/ec80d7e7-840a-47a7-8c36-92254abfc67d) 

| All Receipts | Receipt Details | Receipt Details |
|-------|----------|-----------|
| ![All Receipts](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/b1605a33-4f5e-4fc7-968d-c20462b2a5e3) | ![Receipt Details](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/e838b72d-ab0a-4071-ae58-1661aa8590a8) | ![Screenshot_20240421_165529](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/b230c51b-d771-479b-8531-cd9961dd708b) |

<br/>

### Cafetaria Application
| QrCode Reader | Accept Order | Validate Order |
|-------|----------|-----------|
| ![QR Code Reader](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/d676d093-575b-41ca-afcb-e5341ede6cff) | ![Accept Order](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/501d2b60-c6e7-4536-9f7d-89aa77d4c25a) | ![Validate Order](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/255b6c4d-a635-4f59-a969-48123ee36d8f)

| Order Validated | All Orders | Order Detail |
|-------|----------|-----------|
| ![Order Validated](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/653a8d5b-77d1-4ed9-874f-83c87074909e) | ![All Orders](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/5b6341c5-fcd4-4bce-af26-7f5d3d1122ab) | ![Order Detail](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/2fdb0a16-1f70-4fee-b8b8-c8fe19119c4b) 


## Implementation Diagram
![Implementation Diagram](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/assets/50105554/33c09650-c209-41ad-bd66-fb5d40d4b9d2)


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

#### Run Client Application & Terminal Application

1. Head inside the app's project directory `/coffee-order-application` or `/coffee-order-terminal`, and add the server's base URL to the `local.properties`, accordingly:

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

## Authors
```
Adalberto Oliveira Filipe
Bruno de Sena Pereira
Fabio Huang
Gabriel de Sousa Gomes
Garcia Isaias Manuel
```


## License

This project is licensed under the Apache License, Version 2.0.

For the complete text of the Apache License, please refer to the ![Apache License](https://github.com/FabioMiguel2000/Acme-Caf-Order-System/blob/FabioMiguel2000-patch-1/LICENSE).

