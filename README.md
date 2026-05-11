# QR Code Generator

Aplicação Spring Boot para gerar QR Codes e fazer upload para AWS S3.

## Visão geral

Este repositório contém um serviço REST que recebe texto via requisição HTTP POST, gera um QR Code em PNG e armazena o arquivo em um bucket S3. A resposta retorna a URL pública do arquivo gerado.

## Tecnologias

- Java 21
- Spring Boot 4
- Maven
- AWS SDK for Java v2
- ZXing para geração de QR Code
- Docker

## Requisitos

- Java 21
- Maven
- Docker (opcional, para criar container)
- Conta AWS com credenciais válidas e bucket S3 acessível

## Variáveis de ambiente

A aplicação usa as seguintes variáveis:

```env
AWS_REGION=us-east-1
AWS_S3_BUCKET_NAME=<seu-bucket>
AWS_ACCESS_KEY_ID=<sua-chave>
AWS_SECRET_ACCESS_KEY=<seu-secret>
```

> Use credenciais IAM válidas e verifique que o bucket existe na mesma região.

## Build local

```bash
./mvnw clean package -DskipTests
```

## Executar local

```bash
java -jar target/qrcode.generator-0.0.1-SNAPSHOT.jar
```

## Build Docker

```bash
docker build -t qrcode-generator:1.0 .
```

## Executar via Docker

```bash
docker run --env-file .env qrcode-generator:1.0
```

Se preferir definir as variáveis diretamente:

```bash
docker run -e AWS_REGION=us-east-1 \
  -e AWS_S3_BUCKET_NAME=<seu-bucket> \
  -e AWS_ACCESS_KEY_ID=<sua-chave> \
  -e AWS_SECRET_ACCESS_KEY=<seu-secret> \
  qrcode-generator:1.0
```

## Endpoint

- `POST /qrcode`

### Exemplo de payload

```json
{
  "text": "https://example.com"
}
```

### Exemplo de resposta

```json
{
  "url": "https://<bucket>.s3.<region>.amazonaws.com/<file>.png"
}
```

## Observações

- As credenciais AWS não devem ser commitadas no repositório.
- Use um bucket S3 existente e permissões adequadas para upload de objetos.
- Se ocorrer erro de autenticação, verifique as chaves AWS e a conta associada.
