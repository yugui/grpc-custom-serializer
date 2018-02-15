package main

import (
	"context"
	"flag"

	"github.com/golang/glog"
	xcontext "golang.org/x/net/context"
	"google.golang.org/grpc"

	gpb "github.com/yugui/grpc-custom-serializer/go"
)

var (
	addr = flag.String("addr", "0.0.0.0:5000", "address to connect to")
	name = flag.String("name", "world", "target to greet to")
)

func greet(ctx context.Context, conn *grpc.ClientConn, name string) (string, error) {
	client := gpb.NewGreeterClient(conn)
	resp, err := client.Greet(ctx, &gpb.RequestProto{})
	if err != nil {
		glog.Errorf("Server returned an error: %v", err)
		return "", err
	}
	return resp.GetMessage(), nil
}

func run(ctx context.Context) error {
	conn, err := grpc.DialContext(ctx, *addr, grpc.WithInsecure())
	if err != nil {
		glog.Errorf("Failed to connect to the server %s: %v", *addr, err)
		return err
	}
	defer conn.Close()

	return greet(ctx, conn, *name)
}

func main() {
	flag.Parse()
	defer glog.Flush()

	ctx := context.Background()
	if err := run(ctx); err != nil {
		glog.Exit(err)
	}
}
