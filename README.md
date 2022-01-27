# angularChallenge

O front-end deste projeto de encontra neste repositório:

Com a permissão do instrutor Ubiratan Roberte Cardoso Passos, esta API foi feita em Jhipster para o desafio de Angular. Como tal ele roda e aceita comandos predefinidos como qualquer projeto de Jhipster. O mais “importante” para esta etapa de avaliação é o:

```
docker-compose -f src/main/docker/mysql.yml up -d
```

Este comando irá gerar um Docker MySql que o projeto irar usar. A possibilidade usar apenas o H2 também.

O CROS foi desabilitado no back-end.

Normalmente, o correto e deixar o backEnd reposnsavel por toda logica. Foi o que fiz aqui (a classe Sale por exemplo, que corresponde ao "carrinho" da atividade, esta quase toda a cargo do back), mas para ganhar tempo para o front que eh o foco destas semana de estudo, fiz uns codigos aqui que jamais faria rm um projeto que vai para producao. Fiz para ganhar tempo.

Consideracaoes: A classe "Carrinho de compra" eu transformei na classe "Sale". Na classe de itens comprados eu adicionei um campo boleano para vereificar se a compra ja foi concluida, titei o valor unitario e ja que esta classe ja aponta para a classe do produto que contem o mesmo dado e adicionei uma coluna de usurio para saber queme sta comprando.

para eu lembrar
nao aparece menssagem de erro no login

[comment]: <> (eu modificquei no back-end a forma de castro e esta no minimo estranha)

[comment]: <> (lembrar que escrever que cadrastrei a foto do produto da forma mais bizzara possivel, ja que esta nao era o objetivo)

[comment]: <> (o mesmo vale para a unidade de medida do prodito nao fiz um enum)

importante falar que a validade do token tambem eh vista

falta a imagem eu fiz um blop mais teria que descomentar

folta tratar todas as fotos

nao mostrei a entidade de compra finalizada mais ela esta la fazer isto aqui se tiver tempo

se existir token a pessoa sera logada na hora

escrever que o site foi feito em css apesar de nao muito bonito esta reponsivo.

escrever que o backend ficou responsavel por quase tudo na classe sale
