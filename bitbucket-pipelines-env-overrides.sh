#!/usr/bin/env bash

set -x

[[ -z "$1" ]] && { echo "usage: $0 {project} {test|prod}"; exit 1; }
[[ -z "$2" ]] && { echo "usage: $0 {project} {test|prod}"; exit 1; }

export AWS_TASK_CPU_UNIT=512
export AWS_TASK_MEMORY_UNIT=1024
export AWS_CONTAINER_PORT=8060
export AWS_CONTAINER_CPU_UNIT=512
export AWS_CONTAINER_MEMORY_UNIT=1024
export AWS_HEALTH_CHECK_PATH="/"
export AWS_HEALTH_CHECK_INTERVAL=30
export AWS_HEALTH_CHECK_HEALTHY_COUNT=5
export AWS_HEALTH_CHECK_UNHEALTHY_COUNT=5
