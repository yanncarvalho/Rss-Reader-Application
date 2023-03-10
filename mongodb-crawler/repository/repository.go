package repository

import (
	"log"
	"time"

	"github.com/yanncarvalho/rss-reader-application/mongodb-crawler/factory"
	"github.com/yanncarvalho/rss-reader-application/mongodb-crawler/model"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
)

var collection, ctx = factory.ConectMongo("mongodb://localhost:27017/","rssreader_rss","rss")

func GetRssExpiried() model.Rss {
	match := bson.D{
		{Key: "$match",
			Value: bson.D{
				{Key: "lastUpdate",
					Value: bson.D{
						{
							Key:   "$lte",
							Value: time.Now().UTC().Add(-time.Hour * 24),
						},
					},
				},
			},
		},
	}

	project := bson.D{{Key: "$project", Value: bson.D{{Key: "originalLink", Value: "$originalLink"}, {Key: "lastUpdate", Value: "$lastUpdate"}}}}
	limit := bson.D{{Key: "$limit", Value: 1}}

	resp, err := collection.Aggregate(ctx, mongo.Pipeline{match, project, limit})

	if err != nil {
		log.Fatal(err)
	}

	var rss []model.Rss

	if err := resp.All(ctx, &rss); err != nil {
		panic(err)
	}

	//TODO verificar se tem amsi alfgum
	return rss[0]
}
func Delete(id primitive.ObjectID) {

	collection.DeleteOne(ctx, bson.M{"_id": id})
}

func Save(rss model.Rss) {

	_, err := collection.InsertOne(ctx, rss)
	if err != nil {
		log.Fatal(err)
	}
}
