# angularChallenge BackEnd

O Front-End deste projeto de encontra neste repositório:

Com a permissão do instrutor Ubiratan Roberte Cardoso Passos, esta API foi feita em Jhipster para o desafio de Angular. Como tal ele roda e aceita comandos predefinidos como qualquer projeto de Jhipster. O mais “importante” para esta etapa de avaliação é o:

```
docker-compose -f src/main/docker/mysql.yml up -d
```

Este comando irá gerar um Docker MySql que o projeto irar usar. A possibilidade usar apenas o H2 também.

O CROS foi desabilitado no back-end.

Normalmente, o correto e deixar o backEnd responsável por toda logica. Foi o que fiz aqui (a classe Sale por exemplo, que corresponde ao "carrinho" da atividade, está quase toda a cargo do back), mas para ganhar tempo para o front, que é o foco desta semana de estudo, fiz uns códigos aqui que jamais faria em um projeto que vai para produção (como o método que usei para salvar imagens). Fiz para ganhar tempo.

Considerações: A classe "Carrinho de compra" eu transformei na classe "Sale". Na classe de itens comprados eu adicionei um campo booleano para verificar se a compra já foi concluída, tirei o valor unitário do item, já que esta classe já aponta para a classe do produto que contém o mesmo dado. Adicionei também uma coluna de usuário para saber quem esta comprando.

# angularChallenge FrontEnd

O Back-End deste projeto de encontra neste repositório:

Quanto ao Front-End não tem muito que se falar. Ele roda como um projeto normal do Angular. Todavia, para facilitar a avalição segue uns adendos:

O projeto já vem com dois users cadastrados:

```
Login: user
Senha: user
---
Login: admin
Senha: admin
```

O projeto obviamente respeita, todo q qualquer dado do usuário. Assim, o usuário só poderá ver os dados que a ele pertence.

O token de acesso tem sua data de expiração validada. Se o projeto identifica o token, ele loga automaticamente. Ai clicar em sair o token é excluído

O excedente do desafio, a validação de CEP, é obviamente feita no BackEnd, por questões obvias de segurança.
