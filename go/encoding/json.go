// Package encoding defines the JSON codec.
package encoding

import (
	"bytes"
	"fmt"
	"github.com/golang/glog"

	"github.com/golang/protobuf/jsonpb"
	"github.com/golang/protobuf/proto"
	"google.golang.org/grpc/encoding"
)

const (
	// Name is the name registered for the json encoder.
	Name = "json"
)

// codec implements encoding.Codec to encode messages into JSON.
type codec struct {
	m jsonpb.Marshaler
	u jsonpb.Unmarshaler
}

// Marshal marshals "v" into JSON.
func (c *codec) Marshal(v interface{}) ([]byte, error) {
	glog.V(2).Infof("marshaling data: %#v", v)

	if m, ok := v.(jsonpb.JSONPBMarshaler); ok {
		glog.V(1).Infof("using custom marshaler for %T", v)
		return m.MarshalJSONPB(&c.m)
	}

	msg, ok := v.(proto.Message)
	if !ok {
		glog.V(1).Infof("Cannot marshal %v: not a proto message", v)
		return nil, fmt.Errorf("not a proto message but %T: %v", v, v)
	}

	var w bytes.Buffer
	if err := c.m.Marshal(&w, msg); err != nil {
		glog.V(1).Infof("Failed to marshal %v: %v", msg, err)
		return nil, err
	}
	glog.V(2).Infof("marshaled data: %s", w.Bytes())
	return w.Bytes(), nil
}

// Unmarshal unmarshals JSON-encoded data into "v".
func (c *codec) Unmarshal(data []byte, v interface{}) error {
	glog.V(2).Infof("unmarshaling data: %s", data)

	if u, ok := v.(jsonpb.JSONPBUnmarshaler); ok {
		glog.V(1).Infof("using custom unmarshaler for %v", v)
		return u.UnmarshalJSONPB(&c.u, data)
	}

	msg, ok := v.(proto.Message)
	if !ok {
		glog.V(1).Infof("Cannot unmarshal into %T: not a proto message", v)
		return fmt.Errorf("not a proto message but %T: %v", v, v)
	}
	return c.u.Unmarshal(bytes.NewReader(data), msg)
}

// Name returns the identifier of the codec.
func (c *codec) Name() string {
	return Name
}

func init() {
	encoding.RegisterCodec(new(codec))
}
