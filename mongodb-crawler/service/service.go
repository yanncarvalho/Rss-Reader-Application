package service

import (
	"log"
	"time"

	"github.com/yanncarvalho/rss-reader-application/mongodb-crawler/model"
	"github.com/yanncarvalho/rss-reader-application/mongodb-crawler/repository"
	"github.com/yanncarvalho/rss-reader-application/mongodb-crawler/util"
)

//Update a Rss from the database
func UpsertRss() {
	var isEnded bool = false

	for {
		var rssExpired model.Rss
		rssExpired, isEnded = repository.PushRssExpired()

		if isEnded {
			break
		}

		timeInSeconds := 60

		body := util.RequestGetBody(rssExpired.OriginalLink, timeInSeconds)

		if body == nil {
			log.Printf("rss with %s not found", rssExpired.OriginalLink)
			continue
		}

		rss, err := util.UnmarshalXml[model.Rss](body)
		if err != nil {
			log.Printf("%s is not a rss valid", rssExpired.OriginalLink)
			continue
		}
		rss.Id = rssExpired.Id
		rss.LastUpdate = time.Now()
		rss.OriginalLink = rssExpired.OriginalLink


		repository.Save(rss)
	}
}
