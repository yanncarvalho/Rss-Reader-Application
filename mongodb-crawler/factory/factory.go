package factory

import (
	"context"
	"log"

	"github.com/yanncarvalho/rss-reader-application/mongodb-crawler/util"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

//Connect to mongo database
func ConectMongo() (*mongo.Collection, context.Context, context.CancelFunc) {
	uri, database, collection := util.LoadEnvMongoDB(".env")
	
	client, err := mongo.NewClient(options.Client().ApplyURI(uri))
	if err != nil {
		log.Fatal(err)
	}
	ctx, cancel := context.WithCancel(context.Background())
	err = client.Connect(ctx)

	if err != nil {
		log.Fatal(err)
	}

	return client.Database(database).Collection(collection), ctx, cancel
}
