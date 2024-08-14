#!/bin/bash

curl -X 'POST' \
  'https://cooccurrence.transltr.io/query' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "message": {
    "query_graph": {
      "edges": {
        "e00": {
          "object": "n01",
          "predicates": [
            "biolink:occurs_together_in_literature_with"
          ],
          "subject": "n00"
        }
      },
      "nodes": {
        "n00": {
           "ids": [
            "MONDO:0009020"
          ]
        },
        "n01": {
          "ids": [
            "DRUGBANK:DB12411"
          ]
        }
      }
    }
  }
}' | jq > out.json

