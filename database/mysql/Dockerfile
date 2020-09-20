FROM mysql:5.7.24
LABEL maintainer="edwinqramos@gmail.com"

ENV MYSQL_USER=root
ENV MYSQL_ROOT_PASSWORD=12345678
ENV MYSQL_DATABASE=appchallengebd
ENV MYSQL_ROOT_HOST=%

COPY ./scripts/ /docker-entrypoint-initdb.d/
CMD ["--default-authentication-plugin=mysql_native_password"]