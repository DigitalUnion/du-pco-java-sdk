
## Installation
```go

go get github.com/DigitalUnion/du-pco-go-sdk/dupco

```

## Quickstart

```go

import (
	"github.com/DigitalUnion/du-pco-go-sdk/dupco"
	"github.com/goccy/go-json"
	"log"
)

func ExampleClient() {
	api := dupco.NewClient("cloud-test", "aa", "yDpDEihpUsF_RyWsCES1H")
	api.EnableTestMode()
	for i := 0; i < 10; i++ {
		r := api.IDMapQuery([]byte(`{"f":"mac,imei","k":"868862032205613","m":"0"}`))
		j, _ := json.Marshal(r)
		log.Println(string(j))
	}
}

```
