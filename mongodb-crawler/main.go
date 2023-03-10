package main

import (
	"fmt"

	"github.com/yanncarvalho/rss-reader-application/mongodb-crawler/model"
	"github.com/yanncarvalho/rss-reader-application/mongodb-crawler/util"
)

func main() {

	body := util.RequestGetBody("http://localhost:9091/rss/dev", 60)

	resp := util.UnmarshalJson[model.ConfigServer](body)

	fmt.Print(resp.PropertySources[0].Source["spring.data.mongodb.uri"])
}
