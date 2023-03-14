package model

import (
	"time"

	"go.mongodb.org/mongo-driver/bson/primitive"
)

// Item Category element
type category struct {
	Domain   string `xml:"domain" bson:"domain"`
	Category string `xml:"category" bson:"category"`
}

// Item Guid element
type guid struct {
	IsPermaLink string `xml:"isPermaLink" bson:"isPermaLink"`
	Guid        string `xml:"guid" bson:"guid"`
}

// Item Enclosure element
type enclosure struct {
	Url    string `xml:"url" bson:"url"`
	Length string `xml:"length" bson:"length"`
	Type   string `xml:"type" bson:"type"`
}

// Channel Item element
type item struct {
	Title       string    `xml:"title" bson:"title"`
	Link        string    `xml:"link" bson:"link"`
	Descript    string    `xml:"descript" bson:"descript"`
	Author      string    `xml:"author" bson:"author"`
	Comments    string    `xml:"comments" bson:"comments"`
	Description string    `xml:"description" bson:"description"`
	PubDate     string    `xml:"pubDate" bson:"pubDate"`
	Source      string    `xml:"source" bson:"source"`
	Enclosure   enclosure `xml:"enclosure" bson:"enclosure"`
	Guid        guid      `xml:"guid" bson:"guid"`

	Category []category `xml:"category" bson:"category"`
}

// Channel TextInput element
type textInput struct {
	Title       string `xml:"title" bson:"title"`
	Description string `xml:"description" bson:"description"`
	Name        string `xml:"name" bson:"name"`
	Link        string `xml:"link" bson:"link"`
}

// Channel Cloud element
type cloud struct {
	Domain            string `xml:"domain" bson:"domain"`
	Port              string `xml:"port" bson:"port"`
	Path              string `xml:"path" bson:"path"`
	RegisterProcedure string `xml:"registerProcedure" bson:"registerProcedure"`
	Protocol          string `xml:"protocol" bson:"protocol"`
}

// Channel Image element
type image struct {
	Url         string `xml:"url" bson:"url"`
	Title       string `xml:"title" bson:"title"`
	Link        string `xml:"link" bson:"link"`
	Width       string `xml:"width" bson:"width"`
	Height      string `xml:"height" bson:"height"`
	Description string `xml:"description" bson:"description"`
}

// Rss Channel element
type channel struct {
	Title          string    `xml:"title" bson:"title"`
	Description    string    `xml:"description" bson:"description"`
	Link           string    `xml:"link" bson:"link"`
	Copyright      string    `xml:"copyright" bson:"copyright"`
	ManagingEditor string    `xml:"managingEditor" bson:"managingEditor"`
	WebMaster      string    `xml:"WebMaster" bson:"WebMaster"`
	PubDate        string    `xml:"pubDate" bson:"pubDate"`
	Language       string    `xml:"language" bson:"language"`
	LastBuildDate  string    `xml:"lastBuildDate" bson:"lastBuildDate"`
	Category       string    `xml:"category" bson:"category"`
	Docs           string    `xml:"docs" bson:"docs"`
	Ttl            string    `xml:"ttl" bson:"ttl"`
	Rating         string    `xml:"rating" bson:"rating"`
	SkipHours      string    `xml:"skipHours" bson:"skipHours"`
	SkipDays       string    `xml:"skipDays" bson:"skipDays"`
	Image          image     `xml:"image" bson:"image"`
	TextInput      textInput `xml:"textInput" bson:"textInput"`
	Cloud          cloud     `xml:"cloud" bson:"cloud"`
	Item           []item    `xml:"item" bson:"item"`
}

// Entity to represents Rss in mongo database
type Rss struct {
	Id           primitive.ObjectID `bson:"_id"`
	LastUpdate   time.Time          `bson:"lastUpdate"`
	OriginalLink string             `bson:"originalLink"`
	Channel      channel            `xml:"channel" bson:"channel"`
}
