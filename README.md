# [Java spring boot + Reactjs] This is the source code for the enterprise system development concept course.

## How to run this project

1. Install docker engine and docker desktop and docker compose following the instruction in the link below

```bash
    https://docs.docker.com/get-docker/
```

2. Go to the current working directory and open the Terminal or Command Prompt or PowerShell

3. Enter the command below and waiting until the process finished

```bash
    docker compose -f docker-compose.dev.yml up -d
```

4. After that, try to access

- http://localhost:3001 to check the client side is working or not
- http://localhost:8080 to check the server side is working or not

## Fix the errors when you need to reinstall from scratch

Move to the working directory and enter the command

```bash
  docker compose -f docker-compose.dev.yml down
```

Enter the command to show the list of available docker images

```bash
  docker images
```

Delete all the images which relevant on this project

```bash
  docker rmi esdc_client

  docker rmi esdc_server

  docker rmi esdc-mysql
```

Type the command below to rebuild the project

```bash
  docker compose -f docker-compose.dev.yml up -d
```

## Authors

- [@S1mpleOW](https://www.github.com/s1mpleow)
