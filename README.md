# MosquitoReportAPI

[![Build Status](https://travis-ci.org/coding4people/mosquito-report-api.svg)](https://travis-ci.org/coding4people/mosquito-report-api)

[There is an official web client for this API. Check it out!](https://github.com/coding4people/mosquito-report-www).


## Endpoints

### Sign Up

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


### Authentication

```
    curl -i http://api.mosquito.report/auth/email \
      -H 'Content-Type: application/json' \
      --data '{
        "email": "me@ro.ger.io",
        "password": "123456"
      }'
```


### Add new focus

```
    curl -i http://api.mosquito.report/focus \
      -H 'Content-Type: application/json' \
      --data '{
        "latlon": "35.628611,-120.694152"
      }'
```

### Geoseach focus

```
    curl -i http://api.mosquito.report/focus/query \
      -H 'Content-Type: application/json' \
      --data '{
        "latlonnw": "36.628611,-121.694152",
        "latlonse": "34.628611,-119.694152"
      }'
```


### Add picture to a focus

```
    $focusGuid='00000000-0000-0000-0000-000000000000'
    $fileName='picture.jpg'
    
    curl -i http://api.mosquito.report//picture/focus/$focusGuid \
      -H 'Content-Type: multipart/form-data' \
      -F file=$fileName
```


