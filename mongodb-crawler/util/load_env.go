package util

import (
	"log"
	"os"

	"github.com/joho/godotenv"
	"github.com/yanncarvalho/rss-reader-application/mongodb-crawler/model"
)

// Load MongoDB env variable
func LoadEnvMongoDB(envFile string) (string, string, string) {
	godotenv.Load(envFile)

	mongoURI := os.Getenv("MONGODB_URI")
	mongoDBName := os.Getenv("MONGODB_DATABASE")
	configServerURI := os.Getenv("CONFIG_SERVER_URI")

	if !IsEmpty(configServerURI) &&
		IsEmpty(mongoURI) || IsEmpty(mongoDBName) {
		getConfigServerInfo(&mongoURI, &mongoDBName, configServerURI)
	}
	
	mongoCollection := os.Getenv("MONGODB_DOCUMENT")
	return mongoURI, mongoDBName, mongoCollection
}

func getConfigServerInfo(mongoURI *string, mongoDBName *string, configServerURI string) {

	body := RequestGetBody(configServerURI, 60)

	resp, err := UnmarshalJson[model.ConfigServer](body)

	if err != nil {
		log.Fatal(err)
	}
	if len(resp.PropertySources) > 0 {

		if IsEmpty(*mongoURI) {
			mongoURIPath := os.Getenv("CONFIG_SERVER_VAR_MONGO_URI")
			*mongoURI = resp.PropertySources[0].Source[mongoURIPath]
		}

		if IsEmpty(*mongoDBName) {
			mongoDBPath := os.Getenv("CONFIG_SERVER_VAR_MONGO_DATABASE")
			*mongoDBName = resp.PropertySources[0].Source[mongoDBPath]
		}

	}

}
