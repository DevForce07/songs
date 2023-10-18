#songs

## Forma de instalação


<p>É necessário possuir uma instalação do node, v18.18.0 ou superior, e também deverá possuir o docker e docker-compose instalado. </p>
<p>A partir da raiz do projeto siga o seguinte script</p>

```bash
$ cd backend

$ docker compose up -d

$ ./gradlew clean bootRun

$ cd ../frontend

$ npm i

$ npm run dev

```

Após isso o sistema poderá ser acessado através da url http://localhost:3000
