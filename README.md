# MosquitoReportAPI

[![Build Status](https://travis-ci.org/coding4people/mosquito-report-api.svg)](https://travis-ci.org/coding4people/mosquito-report-api)

**MosquitoReport** is an app to report areas where mosquitoes could spread. Areas of stagnant water, such as flower vases, uncovered barrels, buckets, and discarded tires, can be reported. The main goal is to support the combat against diseases like [Dengue](https://en.wikipedia.org/wiki/Dengue_fever), [Zika](https://en.wikipedia.org/wiki/Zika_fever) and [Chikungunya](https://en.wikipedia.org/wiki/Chikungunya).

**MosquitoReportAPI** is the REST API of this Project. Check the frontend source code at [https://github.com/coding4people/mosquito-report-www](https://github.com/coding4people/mosquito-report-www). Also check our [visual project](https://s3.amazonaws.com/coding4people.com/mosquito.report/mosquito.report.visual.png) and [wirefrimes](https://s3.amazonaws.com/coding4people.com/mosquito.report/mosquito.report.wireframe.png).

<div>
  <img alt="Screenshot - Map" src="https://s3.amazonaws.com/coding4people.com/mosquito.report/mosquito.report.screenshot.map.png">
  <img alt="Screenshot - Details" src="https://s3.amazonaws.com/coding4people.com/mosquito.report/mosquito.report.screenshot.details.png">
</div>

## Hack Summit Hackathon

This project was built during the [Hack Summit Hackathon 2016](https://www.koding.com/Hackathon). More about our team:

**Leonardo Navarro**

  * [https://www.behance.net/leonavarro](https://www.behance.net/leonavarro)
  * [https://github.com/leonavarro](https://github.com/leonavarro)
  
 
**Rogério Yokomizo**

  * [https://github.com/yokomizor](https://github.com/yokomizor)
  * [https://twitter.com/yokomizor](https://twitter.com/yokomizor)
  * [http://ro.ger.io](http://ro.ger.io)


**Vinícius Souza @infodark**
  
  * [https://github.com/infodark](https://github.com/infodark)
  * [https://twitter.com/_infodark](https://twitter.com/_infodark)
  
  
Special thanks to the guys of [Koding](https://www.koding.com/) for free hosting our app during the event.


## Instalation

**Koding VM**

```
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer

version="0.0.7" \
  && wget https://s3.amazonaws.com/com.coding4people.mosquito-report-api/release/com/coding4people/mosquito-report-api/$version/mosquito-report-api-$version-server.tar.gz \
  && tar -xzf mosquito-report-api-$version-server.tar.gz \
  && rm mosquito-report-api-$version-server.tar.gz
  
AWS_ACCESS_KEY_ID=... \
AWS_SECRET_ACCESS_KEY=... \
MOSQUITO_REPORT_DYNAMODB_TABLE_PREFIX=koding \
MOSQUITO_REPORT_CLOUDSEARCH_DOMAIN_PREFIX=koding \
MOSQUITO_REPORT_BUCKET_NAME_PICTURE=koding.mosquitoreport.pictures \
java -cp lib com.coding4people.mosquitoreport.api.Main
```


**Docker**

```
docker run --rm -it --name coding4people/mosquito-report-api -p 80:9000 \
  -e "AWS_ACCESS_KEY_ID=..." \
  -e "AWS_SECRET_ACCESS_KEY=..." \
  -e "MOSQUITO_REPORT_DYNAMODB_TABLE_PREFIX=koding" \
  -e "MOSQUITO_REPORT_CLOUDSEARCH_DOMAIN_PREFIX=koding" \
  -e "MOSQUITO_REPORT_BUCKET_NAME_PICTURE=koding.mosquitoreport.pictures" \
  mosquito-report-api
```


**Running on your local machine**

```
mvn clean exec:java
```

It will start the API using the port 9000. Java8 is required.


### AWS IAM Policies

This API uses some AWS resources (S3, DynamoDB and CloudSearch). Certify that your Role has the following Policies:

  * AmazonS3FullAccess
  * CloudSearchFullAccess
  * AmazonDynamoDBFullAccess
  
To read more about IAM Roles and Policies follow this link: [https://aws.amazon.com/documentation/iam/](https://aws.amazon.com/documentation/iam/).
  

## Usage (Endpoints)

**Sign Up**

```
curl -i http://api.mosquito.report/signup/email \
  -H 'Content-Type: application/json' \
  --data '{
    "email": "me@ro.ger.io",
    "firstname": "Rogério",
    "lastname": "Yokomizo",
    "password": "123456"
  }'
```


**Authentication**

```
curl -i http://api.mosquito.report/auth/email \
  -H 'Content-Type: application/json' \
  --data '{
    "email": "me@ro.ger.io",
    "password": "123456"
  }'
```


**Add new focus**

```
curl -i http://api.mosquito.report/focus \
  -H 'Content-Type: application/json' \
  --data '{
    "latlon": "35.628611,-120.694152"
  }'
```

**Geoseach focus**

```
curl -i http://api.mosquito.report/focus/query \
  -H 'Content-Type: application/json' \
  --data '{
    "latlonnw": "36.628611,-121.694152",
    "latlonse": "34.628611,-119.694152"
  }'
```


**Add picture to a focus**

```
$focusGuid='00000000-0000-0000-0000-000000000000'
$fileName='picture.jpg'

curl -i http://api.mosquito.report//picture/focus/$focusGuid \
  -H 'Content-Type: multipart/form-data' \
  -F file=$fileName
```


