FROM solr:6.6-alpine

ENV CORE_DIR /opt/solr/server/solr
ENV CORE_1 $CORE_DIR/ipni_1
ENV CORE_2 $CORE_DIR/ipni_2

USER root

COPY ipni $CORE_1
COPY ipni $CORE_2

# There are two cores, a "live" core and a "build" core. The indexer runs against the
# build core and then swaps them when it is done. the core.properties files that keep
# track or which core is live are persisted to /coreconf
RUN mkdir /coreconf \
    && /bin/echo "name=ipni_1" >> /coreconf/core1.properties \
	  && /bin/echo "name=ipni_2" >> /coreconf/core2.properties \
    && ln -s /coreconf/core1.properties $CORE_1/core.properties \
    && ln -s /coreconf/core2.properties $CORE_2/core.properties \
    && chown -R $SOLR_USER:$SOLR_USER $CORE_DIR \
    && chown -R $SOLR_USER:$SOLR_USER /coreconf

USER $SOLR_USER

VOLUME $CORE_1/data
VOLUME $CORE_2/data
VOLUME /coreconf
