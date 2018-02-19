"use strict";

var gulp = require('gulp');
var shell = require('gulp-shell');

gulp.task('compile', function(){
  return gulp.src('../greeter.proto')
    .pipe(shell([
      'protoc', '-I..',
      '--js_out=import_style=commonjs,binary:proto',
      '--grpc_out=import_style=commonjs,binary:proto',
      '--plugin=protoc-gen-grpc=`which grpc_node_plugin`',
      '../greeter.proto',
    ].join(' ')))
    .pipe(gulp.dest('proto'));
})
