FROM java:8-jre

WORKDIR /var/mosquito-report-api
  
EXPOSE 9000

RUN version="0.0.4" \
  && wget https://s3.amazonaws.com/com.coding4people.mosquito-report-api/release/com/coding4people/mosquito-report-api/$version/mosquito-report-api-$version-server.tar.gz \
  && tar -xzf mosquito-report-api-$version-server.tar.gz \
  && rm mosquito-report-api-$version-server.tar.gz

CMD ["java", "-cp", "lib", "com.coding4people.mosquitoreport.api.Main"]
