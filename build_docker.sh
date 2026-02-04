#!/bin/bash
NAMESPACE="${1:-codebase_b1686_app}"
docker build -t "$NAMESPACE" .