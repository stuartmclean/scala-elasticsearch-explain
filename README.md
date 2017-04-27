# Elasticsearch Explain

Makes Elasticsearch "_explain" output much more readable for us mortals.

### Usage:
Just pass in connection data and your query and get a nice result string.

Arguments (should be supplied in json format):
- hostname
- port
- scheme
- endpoint
- query

### Example Input:

```
{
    "hostname": "127.0.0.1",
    "port": 9200,
    "scheme": "http",
    "endpoint": "foobar/baz",
    "query": {
        "bool": {
            "should": [
                {
                    "match": {
                        "name": {
                            "query": "pizza",
                            "boost": 4
                        }
                    }
                },
                {
                    "match": {
                        "cuisine.name": {
                            "query": "pizza",
                            "boost": 2
                        }
                    }
                }
            ]
        }
    }
}
```
    
### Example Output:
```
1: name.ngram: 9.3750007910499461: name.edgengram: 12.5000010547332631: name: 121.875010283649271: name.multilanguage: 56.25000474629965
2: name.ngram: 5.9579716700171571: name.edgengram: 7.9439622981730411: name: 66.388834849349421: name.multilanguage: 30.640997895347077
```

### docker
build with:
```sbt docker:publishLocal ```
run with:
```docker run -e "CONFIG=$(cat /path/to/config.json)" elasticsearchexplain:1.0```
