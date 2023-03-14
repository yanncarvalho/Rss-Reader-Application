package model

type propertySources struct{
	Name string
	Source map[string]string
}

//Entity to receive json from spring config server
type ConfigServer struct{
	Name string
	Profiles []string
	Label string
	Version string
	State string
	PropertySources []propertySources
}
