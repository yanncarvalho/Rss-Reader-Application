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

var collection, ctx, _ = factory.ConectMongo()

// It returns some information from the model.Rss and bool saying if the Rss is empty
func PushRssExpired() (result model.Rss, isEmpty bool) {

	match := bson.D{{Key: "$match",
		Value: bson.D{{Key: "lastUpdate",
			Value: bson.D{{Key: "$lte",
				Value: time.Now().UTC().Add(-time.Hour * 24)}}}}}}

	project := bson.D{{Key: "$project",
		Value: bson.D{{Key: "originalLink", Value: "$originalLink"},
			{Key: "lastUpdate", Value: "$lastUpdate"}}}}

	limit := bson.D{{Key: "$limit", Value: 1}}

	resp, err := collection.Aggregate(ctx, mongo.Pipeline{match, project, limit})

	if err != nil {
		log.Fatal(err)
	}

	var rss []model.Rss

	if err := resp.All(ctx, &rss); err != nil {
		log.Fatal(err)
	}



	if len(rss) == 0 {
		return model.Rss{}, true
	}

	Delete(rss[0].Id)
	log.Printf("Push and removed from mongodb rss with url: %s", rss[0].OriginalLink)
	return rss[0], false
}

// Delete a rss by its ID
func Delete(id primitive.ObjectID) {
	_, err := collection.DeleteOne(ctx, bson.M{"_id": id})

	if err != nil {
		log.Fatal(err)
	}

	log.Printf("Removed from mongodb rss with id: %s", id)
}

// Save a new rss
func Save(rss model.Rss) {

	_, err := collection.InsertOne(ctx, rss)
	if err != nil {
		log.Fatal(err)
	}
	log.Printf("Save in mongodb rss with url: %s", rss.OriginalLink)
}
