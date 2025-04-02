# Backend Setup Guide

## Prerequisites

To run the backend successfully, ensure you have the following:

- **Two secret files**:
  - `local.env` located in `BackEnd/`
  - Firebase config file located in `BackEnd/src/main/resources`
- **PostgreSQL** running via Docker

## 1. Setting Up Secret Files

### 1.1 Locate Required Files

Ensure the following files are placed correctly:

- `local.env` → `BackEnd/`
- `firebase_authentication_config.json` → `BackEnd/src/main/resources/`

<p align="center">
  <img src="https://github.com/user-attachments/assets/6399baef-6c43-4048-91b4-5547b1729878" width="50%" alt="Location" />
</p>

## 2. Setting Up Environment Variables

### 2.1 Install `.env` File Plugin

To enable `.env` support, install the required plugin:

1. Open **IntelliJ IDEA**.
2. Go to `File` > `Settings` > `Plugins`.
3. Search for `Env File` and install it.

<p align="center">
  <img src="https://github.com/user-attachments/assets/e6e3d0cc-f6ef-4c10-8a70-bc7171e1b5e3" width="50%" alt="Plugin" />
</p>

4. Restart IntelliJ IDEA.

### 2.2 Enable `.env` File Support

1. Open IntelliJ IDEA.
2. Go to `Run` > `Edit Configurations`.
3. Enable `.env` support and add the `.env` file path.

<p align="center">
  <img src="https://github.com/user-attachments/assets/b22fbbf7-f855-4ae5-8721-6e264e266e8f" width="50%" alt="Env" />
</p>

## 3. Setting Up PostgreSQL and Data

### 3.1 Install Required Tools

Ensure you have:

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [JetBrains DataGrip](https://www.jetbrains.com/datagrip/)

### 3.2 Run PostgreSQL Database with Docker

1. Go to `Run` > `Edit Configurations`.
2. `Add New Configuration` > `Docker Compose`

<p align="center">
  <img src="https://github.com/user-attachments/assets/36b60c71-bd97-4fe5-be6b-cf6de7050f2a" width="50%" alt="Docker Compose" />
</p>

3. Search for `docker-compose.yml`

<p align="center">
  <img src="https://github.com/user-attachments/assets/53f9f90c-0a01-4157-b2b2-2d25074bf3c3" width="50%" alt="docker-compose.yml" />
</p>

4. Run `Unnamed`

<p align="center">
  <img src="https://github.com/user-attachments/assets/b7247e3a-240a-47c7-a714-2b0512e4c337" width="50%" alt="Unnamed" />
</p>

5. Wait for the images to download and start the database.
6. You can run the database either via IntelliJ IDEA or Docker Desktop.

### 3.3 Create Tables and Import Data

1. Open **DataGrip**.
2. Create a new PostgreSQL connection with:
   - **Username**: `postgres`
   - **Password**: `postgres`

<p align="center">
  <img src="https://github.com/user-attachments/assets/780cf46d-82b2-4a08-aa0d-5911507c0a8b" width="50%" alt="new PostgreSQL" />
  <img src="https://github.com/user-attachments/assets/57de728a-cbbd-4e05-a2cc-08535786cec8" width="50%" alt="UsernamePassword" />
</p>

3. Ensure PostgreSQL is running and test the connection.

<p align="center">
  <img src="https://github.com/user-attachments/assets/f5f7dd78-a0a2-4a50-9690-fd161aed8091" width="50%" alt="Ensure PostgreSQL is running" />
</p>

4. Copy the SQL script from `sql.txt`.
5. Open the SQL console, paste the script, press `Ctrl + A`, and click **Execute**.

<p align="center">
  <img src="https://github.com/user-attachments/assets/7d92a5f8-6452-43ea-9b7a-30168f9d6c55" width="50%" alt="paste the script" />
</p>

## Done!

Your backend should now be set up and ready to run 🚀.
