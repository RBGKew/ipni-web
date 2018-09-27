#!/bin/bash

tar cvf .travis-secrets.tar secrets
travis encrypt-file .travis-secrets.tar | grep openssl > .decrypt.sh
rm .travis-secrets.tar
