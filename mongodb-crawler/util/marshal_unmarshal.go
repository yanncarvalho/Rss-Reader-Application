package util

import (
	"encoding/json"

	"encoding/xml"
)

func UnmarshalXml[T any](body []byte) (T, error) {
	var response T
	err := xml.Unmarshal(body, &response)
	return response, err
}

func UnmarshalJson[T any](body []byte) (T, error) {
	var response T
	err := json.Unmarshal(body, &response)
	return response, err
}
