# ArtistaMusica
Java Maven project using Oracle database via docker compose and MaritacaAI API.

### Description

This project is a simple registration of artists and their songs, using the Oracle database via Docker compose where there must be 
the ./data folder in the project's root directory and also uses the MaritacaAI API (chat) to consult the songs of the artist being searched.
The Oracle database can be queried and managed by Oracle SQL Developer.

It is necessary to create a .env file in the project's local directory to maintain the MaritacaAI API url information and its API_KEY. Here's an example:

url=https://chat.maritaca.ai/api/chat/inference

API_KEY=649axxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
