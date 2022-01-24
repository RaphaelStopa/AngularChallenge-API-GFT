# angularChallenge

O front-end deste projeto de encontra neste repositório:

Com a permissão do instrutor Ubiratan Roberte Cardoso Passos, esta API foi feita em Jhipster para o desafio de Angular. Como tal ele roda e aceita comandos predefinidos como qualquer projeto de Jhipster. O mais “importante” para esta etapa de avaliação é o:

```
docker-compose -f src/main/docker/mysql.yml up -d
```

Este comando irá gerar um Docker MySql que o projeto irar usar. A possibilidade usar apenas o H2 também.

O CROS foi desabilitado no back-end.

para eiu lembrar
precisa cliclcar duas vezes para entrar no app
nao parece menssagen, quanto mais de erro
eu modificquei no back-end a forma de castro e esta no minimo estranha

lembrar que escrever que cadrastrei a foto do produto da forma mais bizzara possivel, ja que esta nao era o objetivo
o mesmo vale para a unidade de medida do prodito nao fiz um enum

teria que cadrastar e redirecionar

importante falar que a validade do token tambem eh vista

carrinho vvirou sale

no itens da compra
is finished == bolean
add um boleano pq dai isto indica se a compra foi finalizada ou nao, ou mesmo retira do carrinho
id do user para buscar pelo usuario os itents == == @OneToOne
@JoinColumn(unique = true)
private User user;

falta a imagem eu fiz um blop mais teria que descomentar

tirei o valor unitario do itens da compra pq qle ja referencia um produto
