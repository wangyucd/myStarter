#!/usr/bin/env bash
set -euo pipefail

MAX_PATCH_LINES="${MAX_PATCH_LINES:-220}"

if ! git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
  echo "ERROR: current directory is not a git repository" >&2
  exit 2
fi

source_mode=""
if ! git diff --cached --quiet --exit-code; then
  source_mode="staged"
elif ! git diff --quiet --exit-code; then
  source_mode="working_tree"
else
  echo "DIFF_SOURCE: none"
  echo "SUMMARY: no changes available"
  exit 0
fi

run_diff() {
  if [ "${source_mode}" = "staged" ]; then
    git diff --cached "$@"
  else
    git diff "$@"
  fi
}

echo "DIFF_SOURCE: ${source_mode}"
echo "FILES:"
run_diff --name-status --no-color
echo
echo "STAT:"
run_diff --stat --no-color
echo
echo "PATCH:"
run_diff --no-color -U3 | awk -v max_lines="${MAX_PATCH_LINES}" '
  {
    if (count < max_lines) {
      print $0
    }
    count++
  }
  END {
    if (count > max_lines) {
      printf "\n[TRUNCATED] patch lines shown: %d / %d total\n", max_lines, count
    }
  }
'
