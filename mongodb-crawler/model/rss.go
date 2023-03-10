package model

import (
	"time"

	"go.mongodb.org/mongo-driver/bson/primitive"
)

type category struct {
	Domain   string `xml:"domain"`
	Category string `xml:"category"`
}

type guid struct {
	IsPermaLink string `xml:"isPermaLink"`
	Guid        string `xml:"guid"`
}

type enclosure struct {
	Url    string `xml:"url"`
	Length string `xml:"length"`
	Type   string `xml:"type"`
}

type item struct {
	Title       string    `xml:"title"`
	Link        string    `xml:"link"`
	Descript    string    `xml:"descript"`
	Author      string    `xml:"author"`
	Comments    string    `xml:"comments"`
	Description string    `xml:"description"`
	PubDate     string    `xml:"pubDate"`
	Source      string    `xml:"source"`
	Enclosure   enclosure `xml:"enclosure"`
	Guid        guid      `xml:"guid"`

	Category []category `xml:"category"`
}

type textInput struct {
	Title       string `xml:"title"`
	Description string `xml:"description"`
	Name        string `xml:"name"`
	Link        string `xml:"link"`
}

type cloud struct {
	Domain            string `xml:"domain"`
	Port              string `xml:"port"`
	Path              string `xml:"path"`
	RegisterProcedure string `xml:"registerProcedure"`
	Protocol          string `xml:"protocol"`
}

type image struct {
	Url         string `xml:"url"`
	Title       string `xml:"title"`
	Link        string `xml:"link"`
	Width       string `xml:"width"`
	Height      string `xml:"height"`
	Description string `xml:"description"`
}
type channel struct {
	Title          string    `xml:"title"`
	Description    string    `xml:"description"`
	Link           string    `xml:"link"`
	Copyright      string    `xml:"copyright"`
	ManagingEditor string    `xml:"managingEditor"`
	WebMaster      string    `xml:"WebMaster"`
	PubDate        string    `xml:"pubDate"`
	Language       string    `xml:"language"`
	LastBuildDate  string    `xml:"lastBuildDate"`
	Category       string    `xml:"category"`
	Docs           string    `xml:"docs"`
	Ttl            string    `xml:"ttl"`
	Rating         string    `xml:"rating"`
	SkipHours      string    `xml:"skipHours"`
	SkipDays       string    `xml:"skipDays"`
	Image          image     `xml:"image"`
	TextInput      textInput `xml:"textInput"`
	Cloud          cloud     `xml:"cloud"`
	Item           []item    `xml:"item"`
}

type Rss struct {
	Id           primitive.ObjectID `bson:"_id"`
	LastUpdate   time.Time
	OriginalLink string
	Channel      channel `xml:"channel"`
}
