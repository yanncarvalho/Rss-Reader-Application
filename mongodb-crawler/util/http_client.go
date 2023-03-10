package util

import (
	"fmt"
	"io"
	"net/http"
	"time"
)

func RequestGetBody(url string, timeDurationSeconds int) []byte {
	client := http.Client{Timeout: time.Duration(timeDurationSeconds) * time.Second}

	resp, err := client.Get(url)
	if err != nil {
		return nil
	}
	defer resp.Body.Close()
	body, err := io.ReadAll(resp.Body)

	if err != nil {
		fmt.Printf("Error %s", err)
		return nil
	}
	defer resp.Body.Close()
	return body
}
