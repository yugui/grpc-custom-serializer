package main

import (
	"flag"
	"net"

	"github.com/golang/glog"
	"golang.org/x/net/context"
	"google.golang.org/grpc"

	gpb "github.com/yugui/grpc-custom-serializer/go"
)

var (
	addr = flag.String("addr", "0.0.0.0:5000", "address to connect to")
)

type greeterServer struct{}

func (greeterServer) Greet(ctx context.Context, req *gpb.RequestProto) (*gpb.ResponseProto, error) {
	return &gpb.ResponseProto{
		Message: "Hello, " + req.GetName(),
	}, nil
}

func run(ctx context.Context) error {
	glog.V(1).Infof("starting listening %s", *addr)
	l, err := net.Listen("tcp", *addr)
	if err != nil {
		glog.Errorf("Failed to listen %s: %v", *addr, err)
		return err
	}

	s := grpc.NewServer()
	gpb.RegisterGreeterServer(s, new(greeterServer))

	glog.V(1).Infof("starting server")
	ch := make(chan error, 1)
	go func(ch chan<- error) {
		defer close(ch)
		ch <- s.Serve(l)
	}(ch)
	glog.Infof("Serving on %s", *addr)

	return <-ch
}

func main() {
	flag.Parse()
	defer glog.Flush()

	ctx := context.Background()
	if err := run(ctx); err != nil {
		glog.Exit(err)
	}
}
