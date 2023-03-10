package util

import (
	"encoding/json"

	"log"

	"encoding/xml"
)

func UnmarshalXml[T any](body []byte) T {
	var response T
	if err := xml.Unmarshal(body, &response); err != nil {
		log.Fatal(err)
	}

	return response
}

func UnmarshalJson[T any](body []byte) T {
	var response T
	if err := json.Unmarshal(body, &response); err != nil {
		log.Fatal(err)
	}

	return response
}
