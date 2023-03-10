package model

type propertySources struct{
	Name string
	Source map[string]string
}

type ConfigServer struct{
	Name string
	Profiles []string
	Label string
	Version string
	State string
	PropertySources []propertySources

}
