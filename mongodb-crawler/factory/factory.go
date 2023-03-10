package factory

import (
	"context"
	"fmt"
	"log"
	"time"

	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

/*
Connect to my cluster
*/
func ConectMongo(url string, database string, collection string) (*mongo.Collection, context.Context) {


	client, err := mongo.NewClient(options.Client().ApplyURI(url))
	if err != nil {
		log.Fatal(err)
	}
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	err = client.Connect(ctx)

	if err != nil {
		log.Fatal(err)
	}
	//TODO ver o que fazer com o cancel
	fmt.Println(cancel)

	return client.Database(database).Collection(collection), ctx
}
