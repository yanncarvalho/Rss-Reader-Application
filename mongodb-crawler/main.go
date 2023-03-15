package main

import (
	"log"

	"github.com/yanncarvalho/rss-reader-application/mongodb-crawler/service"

	"time"
)

var timeSleepInMinutes time.Duration = 300

func main() {
	log.Println("Start Application")
	for {
		service.UpsertRss()
		log.Printf("End all Rss to update, sleep for %d minutes", timeSleepInMinutes)
		time.Sleep(timeSleepInMinutes * time.Minute)
	}

}
