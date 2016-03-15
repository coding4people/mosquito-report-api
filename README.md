# MosquitoReportAPI

[![Build Status](https://travis-ci.org/coding4people/mosquito-report-api.svg)](https://travis-ci.org/coding4people/mosquito-report-api)
[![Coverage Status](https://coveralls.io/repos/github/coding4people/mosquito-report-api/badge.svg?branch=master)](https://coveralls.io/github/coding4people/mosquito-report-api?branch=master)

**MosquitoReport** is an app to report areas where mosquitoes could spread. Areas of stagnant water, such as flower vases, uncovered barrels, buckets, and discarded tires, can be reported. The main goal is to support the combat against diseases like [Dengue](https://en.wikipedia.org/wiki/Dengue_fever), [Zika](https://en.wikipedia.org/wiki/Zika_fever) and [Chikungunya](https://en.wikipedia.org/wiki/Chikungunya).

**MosquitoReportAPI** is the REST API of this project. Check the frontend source code at [https://github.com/coding4people/mosquito-report-www](https://github.com/coding4people/mosquito-report-www). Android and iOS clients are available at [https://github.com/coding4people/mosquito-report-droid](https://github.com/coding4people/mosquito-report-droid) and [https://github.com/coding4people/mosquito-report-ios](https://github.com/coding4people/mosquito-report-ios). Also check our [visual project](https://s3.amazonaws.com/coding4people.com/mosquito.report/mosquito.report.visual.png) and [wirefrimes](https://s3.amazonaws.com/coding4people.com/mosquito.report/mosquito.report.wireframe.png).

**Full documentation** at: [http://developer.mosquito.report](http://developer.mosquito.report)

<div>
  <img alt="Screenshot - Map" src="https://s3.amazonaws.com/coding4people.com/mosquito.report/mosquito.report.screenshot.map.png">
  <img alt="Screenshot - Details" src="https://s3.amazonaws.com/coding4people.com/mosquito.report/mosquito.report.screenshot.details.png">
</div>


## Instalation

**Docker**

```
docker run --rm -it --name coding4people/mosquito-report-api -p 80:9000 \
  -e "AWS_ACCESS_KEY_ID=..." \
  -e "AWS_SECRET_ACCESS_KEY=..." \
  -e "DYNAMODB_TABLE_PREFIX=localhost" \
  -e "CLOUDSEARCH_DOMAIN_PREFIX=localhost" \
  -e "BUCKET_NAME_PICTURE=localhost.mosquitoreport.pictures" \
  mosquito-report-api
```


**Running on your local machine**

```
AWS_ACCESS_KEY_ID=..." \
AWS_SECRET_ACCESS_KEY=..." \
DYNAMODB_TABLE_PREFIX=localhost" \
CLOUDSEARCH_DOMAIN_PREFIX=localhost" \
BUCKET_NAME_PICTURE=localhost.mosquitoreport.pictures" \
mvn clean exec:java
```

It will start the API using the port 9000. Java8 is required.


### AWS IAM Policies

This API uses some AWS resources (S3, DynamoDB and CloudSearch). Certify that your Role has the following Policies:

  * AmazonS3FullAccess
  * CloudSearchFullAccess
  * AmazonDynamoDBFullAccess
  
To read more about IAM Roles and Policies follow this link: [https://aws.amazon.com/documentation/iam/](https://aws.amazon.com/documentation/iam/).
  

## Authors

**Leonardo Navarro**

  * [https://www.behance.net/leonavarro](https://www.behance.net/leonavarro)
  * [https://github.com/leonavarro](https://github.com/leonavarro)
  
 
**Rogério Yokomizo**

  * [https://github.com/yokomizor](https://github.com/yokomizor)
  * [https://twitter.com/yokomizor](https://twitter.com/yokomizor)
  * [http://ro.ger.io](http://ro.ger.io)


**Vinícius Souza**
  
  * [https://github.com/infodark](https://github.com/infodark)
  * [https://twitter.com/_infodark](https://twitter.com/_infodark)

