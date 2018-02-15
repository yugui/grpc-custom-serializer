// client is an example gRPC client application which encodes
// messages in JSON instead of protobuf.
package main

import (
	"flag"
	"fmt"
	"os"

	"github.com/golang/glog"
	"github.com/yugui/grpc-custom-serializer/go/encoding"
	"golang.org/x/net/context"
	"google.golang.org/grpc"

	gpb "github.com/yugui/grpc-custom-serializer/go"
)

var (
	addr = flag.String("addr", "0.0.0.0:5000", "address to connect to")
	name = flag.String("name", "world", "target to greet to")
)

func greet(ctx context.Context, conn *grpc.ClientConn, name string) (string, error) {
	client := gpb.NewGreeterClient(conn)
	resp, err := client.Greet(ctx, &gpb.RequestProto{
		Name: name,
	})
	if err != nil {
		glog.Errorf("Server returned an error: %v", err)
		return "", err
	}
	return resp.GetMessage(), nil
}

func run(ctx context.Context) error {
	opts := []grpc.DialOption{
		grpc.WithInsecure(),
		grpc.WithDefaultCallOptions(grpc.CallContentSubtype(encoding.Name)),
	}
	conn, err := grpc.DialContext(ctx, *addr, opts...)
	if err != nil {
		glog.Errorf("Failed to connect to the server %s: %v", *addr, err)
		return err
	}
	defer conn.Close()

	msg, err := greet(ctx, conn, *name)
	if err != nil {
		glog.Errorf("Failed to call greeter: %v", err)
		return err
	}
	_, err = fmt.Fprintln(os.Stdout, msg)
	return err
}

func main() {
	flag.Parse()
	defer glog.Flush()

	ctx := context.Background()
	if err := run(ctx); err != nil {
		glog.Exit(err)
	}
}
