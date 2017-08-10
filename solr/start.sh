#!/bin/bash

# There are two cores, a "live" core and a "build" core. The indexer runs against the
# build core and then swaps them when it is done.
#
# This somewhate convoluted bootstrap process is to support persisting core swaps on
# Kubernetes. When the image is being _built_, the core.properties files are written
# to /tmp with the right access modifiers and soft links created. Then when the
# container is _started_, the core.properties files are copied into a persistent
# directory and the regular solr startup process resumed

echo "name=ipni_1" >> /tmp/core1.properties
echo "name=ipni_2" >> /tmp/core2.properties

if [ "$1" == "bootstrap" ]
then
  mkdir /coreconf
  chown -R $SOLR_USER:$SOLR_USER /coreconf
  chown $SOLR_USER:$SOLR_USER /tmp/core1.properties
  chown $SOLR_USER:$SOLR_USER /tmp/core2.properties
  ln -s /coreconf/core1.properties $CORE_DIR/ipni_1/core.properties
  ln -s /coreconf/core2.properties $CORE_DIR/ipni_2/core.properties
else
  [ ! -f /coreconf/core1.properties ] && cp /tmp/core1.properties /coreconf/
  [ ! -f /coreconf/core2.properties ] && cp /tmp/core2.properties /coreconf/

  exec solr-foreground
fi
