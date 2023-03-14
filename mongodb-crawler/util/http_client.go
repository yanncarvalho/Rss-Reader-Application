package util

import (
	"io"
	"log"
	"net/http"
	"time"
)
//get GET request body by a url
func RequestGetBody(url string, timeDurationSeconds int) []byte {

	log.Printf("Requested from internet  url %s", url)
	client := http.Client{Timeout: time.Duration(timeDurationSeconds) * time.Second}

	resp, err := client.Get(url)
	if err != nil {
		return nil
	}
	body, err := io.ReadAll(resp.Body)

	if err != nil {
		return nil
	}

	defer resp.Body.Close()
	return body
}
