FROM solr:6.6.0-alpine

ENV CORE_DIR /opt/solr/server/solr/ipni
ENV DATA_DIR /opt/solr/server/solr/ipni/data

COPY ipni $CORE_DIR

USER root
RUN mkdir $DATA_DIR && chown -R $SOLR_USER:$SOLR_USER $CORE_DIR $LIB_DIR
USER $SOLR_USER