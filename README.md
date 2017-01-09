Elasticsearch Explain

Arguments (should be supplied in json format):
- hostname
- port
- scheme
- endpoint
- query

```
{
    "hostname": "172.28.128.101",
    "port": 9200,
    "scheme": "http",
    "endpoint": "foodpanda_rw_development_en/vendor",
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
    
